package kr.or.dgit.bigdata.swmng.customer.regedit;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ProgressMonitor;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.prompt.PromptSupport;

import kr.or.dgit.bigdata.swmng.dto.Post;
import kr.or.dgit.bigdata.swmng.service.PostService;
import kr.or.dgit.bigdata.swmng.util.ModelForTable;
import javax.swing.JProgressBar;

public class ZipcodeSearch extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JButton btnOk;
	private JButton btnCancel;
	private JTextField tfStreet;
	private JButton btnSearch;
	private JComboBox cmbCity;
	private JTable addressListTable;
	private JPanel searchPanel;
	private JPanel listPanel;
	private JPanel btnPanel;
	private ProgressMonitor pm;
	private Thread threadForSearch;
	private boolean flag = true;
	private JScrollPane scrollPane;
	private JProgressBar progressBar;
	private JPanel ProgressPanel;
	private String regPanel = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ZipcodeSearch frame = new ZipcodeSearch("");
					frame.setVisible(true);
					UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel");
					SwingUtilities.updateComponentTreeUI(frame);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ZipcodeSearch(String parent) {
		regPanel = parent;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(700, 350, 450, 300);
		setTitle("우편번호 검색");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		addressListTable = new JTable();
		addressListTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					selectAddress(regPanel);
				}
			}

		});

		searchPanel = new JPanel();
		searchPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.add(searchPanel, BorderLayout.NORTH);
		GridBagLayout gbl_searchPanel = new GridBagLayout();
		gbl_searchPanel.columnWidths = new int[] { 27, 103, 0, 85, 0, 0, 0, 0, 0 };
		gbl_searchPanel.rowHeights = new int[] { 0, 0 };
		gbl_searchPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_searchPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		searchPanel.setLayout(gbl_searchPanel);

		cmbCity = new JComboBox();
		GridBagConstraints gbc_cmbCity = new GridBagConstraints();
		gbc_cmbCity.insets = new Insets(0, 0, 0, 5);
		gbc_cmbCity.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbCity.gridx = 1;
		gbc_cmbCity.gridy = 0;
		searchPanel.add(cmbCity, gbc_cmbCity);

		JLabel lblSearch = new JLabel("주소검색 : ");
		GridBagConstraints gbc_lblSearch = new GridBagConstraints();
		gbc_lblSearch.insets = new Insets(0, 0, 0, 5);
		gbc_lblSearch.gridx = 2;
		gbc_lblSearch.gridy = 0;
		searchPanel.add(lblSearch, gbc_lblSearch);

		tfStreet = new JTextField();
		tfStreet.setFont(new Font("맑은 고딕", Font.BOLD, 11));
		GridBagConstraints gbc_tfSearch = new GridBagConstraints();
		gbc_tfSearch.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfSearch.gridwidth = 4;
		gbc_tfSearch.insets = new Insets(0, 0, 0, 5);
		gbc_tfSearch.gridx = 3;
		gbc_tfSearch.gridy = 0;
		searchPanel.add(tfStreet, gbc_tfSearch);
		tfStreet.setBackground(new Color(255, 255, 255, 0));
		PromptSupport.setPrompt("도로명을 입력하세요", tfStreet);
		tfStreet.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (tfStreet.getText().length() < 2) {
						JOptionPane.showMessageDialog(null, "검색어는 2글자 이상으로 해주세요");
					} else {
						setCursor(new Cursor(Cursor.WAIT_CURSOR));
						progressBar.setVisible(true);
						scrollPane.setVisible(false);
						updateList();
						setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					}
				}
			}
		});

		btnSearch = new JButton("검색");
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.anchor = GridBagConstraints.WEST;
		gbc_btnSearch.gridx = 7;
		gbc_btnSearch.gridy = 0;
		searchPanel.add(btnSearch, gbc_btnSearch);
		btnSearch.addActionListener(this);

		listPanel = new JPanel();
		contentPane.add(listPanel, BorderLayout.CENTER);
		listPanel.setLayout(new BorderLayout(0, 0));
		scrollPane = new JScrollPane(addressListTable);
		listPanel.add(scrollPane, BorderLayout.CENTER);

		ProgressPanel = new JPanel();
		listPanel.add(ProgressPanel, BorderLayout.NORTH);

		progressBar = new JProgressBar();
		ProgressPanel.add(progressBar);
		progressBar.setStringPainted(true);
		progressBar.setIndeterminate(true);

		btnPanel = new JPanel();
		btnPanel.setBorder(new EmptyBorder(3, 10, 3, 10));
		contentPane.add(btnPanel, BorderLayout.SOUTH);
		GridBagLayout gbl_btnPanel = new GridBagLayout();
		gbl_btnPanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_btnPanel.rowHeights = new int[] { 0, 0 };
		gbl_btnPanel.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_btnPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		btnPanel.setLayout(gbl_btnPanel);

		btnOk = new JButton("선택");
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.anchor = GridBagConstraints.EAST;
		gbc_btnOk.insets = new Insets(0, 0, 0, 5);
		gbc_btnOk.gridx = 0;
		gbc_btnOk.gridy = 0;
		btnPanel.add(btnOk, gbc_btnOk);
		btnOk.addActionListener(this);

		btnCancel = new JButton("취소");
		btnCancel.addActionListener(this);
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.anchor = GridBagConstraints.WEST;
		gbc_btnCancel.gridx = 1;
		gbc_btnCancel.gridy = 0;
		btnPanel.add(btnCancel, gbc_btnCancel);

		List<Post> list = PostService.getInstance().selectCity();
		for (Post p : list) {
			cmbCity.addItem(p.getSido());
		}

		threadForSearch = new Thread() {
			@Override
			public void run() {
				getAddress();
			}
		};
		threadForSearch.start();

		scrollPane.setVisible(false);
		progressBar.setVisible(false);
		progressBar.setStringPainted(true);
		progressBar.setString("검색중입니다");
		progressBar.setFont(new Font("맑은 고딕", Font.ITALIC, 10));

	}

	private void updateList() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (!scrollPane.isVisible()) {
					setCursor(new Cursor(Cursor.WAIT_CURSOR));
					progressBar.setVisible(false);
					scrollPane.setVisible(true);
					getAddress();
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					revalidate();
					repaint();
				}
			}
		});
	}

	private void getAddress() {
		if (flag == false) {

			String[] COL_NAMES = { "우편번호", "주소" };
			String[][] data;
			int idx = 0;
			String b2;
			try {
				Post post = new Post(cmbCity.getSelectedItem() + "", tfStreet.getText().trim());
				List<Post> list = PostService.getInstance().searchStreet(post);
				if (list.isEmpty()) {
					scrollPane.setVisible(false);
					JOptionPane.showMessageDialog(null, "검색결과가 없습니다.");
				} else {
					data = new String[list.size()][COL_NAMES.length];
					for (Post p : list) {
						if (p.getBuilding2() != 0) {
							b2 = " - " + p.getBuilding2();
						} else {
							b2 = "";
						}
						data[idx][0] = p.getZipcode();
						data[idx][1] = p.getSido() + " " + p.getSigungu() + " " + p.getDoro() + " " + p.getBuilding1()
								+ b2;
						idx++;
					}

					ModelForTable mft = new ModelForTable(data, COL_NAMES);
					addressListTable.setModel(mft);
					addressListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					addressListTable.setFont(new Font("돋움", Font.PLAIN, 12));
					mft.tableCellAlignment(addressListTable, SwingConstants.CENTER, 0);
					mft.resizeColumnWidth(addressListTable);
					mft.tableHeaderAlignment(addressListTable);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (flag == true) {
			flag = false;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnSearch) {
			if (tfStreet.getText().length() < 3) {
				JOptionPane.showMessageDialog(null, "검색어는 3글자 이상으로 해주세요");
			} else {
				setCursor(new Cursor(Cursor.WAIT_CURSOR));
				progressBar.setVisible(true);
				scrollPane.setVisible(false);
				updateList();
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

		} else if (e.getSource() == btnOk) {
			selectAddress(regPanel);
		} else if (e.getSource() == btnCancel) {
			setVisible(false);
		}
	}

	protected void selectAddress(String parent) {
		String zipcode;
		String address;
		if (addressListTable.getSelectedColumn() == 0) {

			zipcode = addressListTable.getValueAt(addressListTable.getSelectedRow(),
					addressListTable.getSelectedColumn()) + "";

			address = addressListTable.getValueAt(addressListTable.getSelectedRow(),
					addressListTable.getSelectedColumn() + 1) + "";
		} else {
			zipcode = addressListTable.getValueAt(addressListTable.getSelectedRow(),
					addressListTable.getSelectedColumn() - 1) + "";
			address = addressListTable.getValueAt(addressListTable.getSelectedRow(),
					addressListTable.getSelectedColumn()) + "";
		}
		switch (parent) {
		case "Company":
			CompanyRegEdit.getTfZipcode().setText("[" + zipcode + "]");
			CompanyRegEdit.getTfAddress().setText(address);
			break;

		case "Buyer":
			BuyerRegEdit.getTfZipcode().setText("[" + zipcode + "]");
			BuyerRegEdit.getTfAddress().setText(address);
			break;
		}
		setVisible(false);
	}

}
