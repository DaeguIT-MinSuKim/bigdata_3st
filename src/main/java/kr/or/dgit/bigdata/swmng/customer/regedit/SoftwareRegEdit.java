package kr.or.dgit.bigdata.swmng.customer.regedit;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Files;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import kr.or.dgit.bigdata.swmng.customer.list.SoftwareList;
import kr.or.dgit.bigdata.swmng.dto.Company;
import kr.or.dgit.bigdata.swmng.dto.Software;
import kr.or.dgit.bigdata.swmng.service.CompanyService;
import kr.or.dgit.bigdata.swmng.service.SoftwareService;

public class SoftwareRegEdit extends JPanel implements ActionListener, RegEditInterface {
	private JTextField tfSupPrice;
	private JTextField tfNo;
	private JTextField tfTitle;
	private JTextField tfSellPrice;
	private JComboBox cmbCategory;
	private JComboBox cmbCoName;
	private JButton btnAdd;
	private JButton btnCancel;
	private String selectedTitle;
	private List<Software> softwareList = SoftwareService.getInstance().selectCategory();
	private List<Company> companyList = CompanyService.getInstance().selectCoName();
	private JLabel lblTitle;
	private JLabel lblTitlePic;
	private JButton btnOpen;
	private JLabel lblPreview;
	private JLabel lblPicPreview;
	private JFileChooser jfc;
	private JPanel previewPanel;

	@SuppressWarnings("unchecked")
	public SoftwareRegEdit(String e, int flag) {
		setLayout(new BorderLayout(0, 0));

		JPanel AddPanel = new JPanel();
		add(AddPanel, BorderLayout.CENTER);
		AddPanel.setBorder(new EmptyBorder(10, 5, 10, 5));
		GridBagLayout gbl_AddPanel = new GridBagLayout();
		gbl_AddPanel.columnWidths = new int[] { 36, -13, 14, -2, 0, -73 };
		gbl_AddPanel.rowHeights = new int[] { 38, 0, 0, 0, 0, 0, 90, 0, 0 };
		gbl_AddPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		gbl_AddPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		AddPanel.setLayout(gbl_AddPanel);

		lblTitle = new JLabel("소프트웨어 등록");
		lblTitle.setFont(new Font("돋움", Font.BOLD, 15));
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.gridwidth = 6;
		gbc_lblTitle.insets = new Insets(0, 0, 5, 0);
		gbc_lblTitle.gridx = 0;
		gbc_lblTitle.gridy = 0;
		AddPanel.add(lblTitle, gbc_lblTitle);

		JLabel lbNo = new JLabel("품목번호 :");
		GridBagConstraints gbc_lbNo = new GridBagConstraints();
		gbc_lbNo.anchor = GridBagConstraints.EAST;
		gbc_lbNo.insets = new Insets(0, 0, 5, 5);
		gbc_lbNo.gridx = 0;
		gbc_lbNo.gridy = 1;
		AddPanel.add(lbNo, gbc_lbNo);

		tfNo = new JTextField();
		tfNo.setEditable(false);
		tfNo.setColumns(10);
		GridBagConstraints gbc_tfNo = new GridBagConstraints();
		gbc_tfNo.anchor = GridBagConstraints.WEST;
		gbc_tfNo.insets = new Insets(0, 0, 5, 5);
		gbc_tfNo.gridx = 1;
		gbc_tfNo.gridy = 1;
		AddPanel.add(tfNo, gbc_tfNo);

		JLabel lblCoName_1 = new JLabel("공급회사명 :");
		GridBagConstraints gbc_lblCoName_1 = new GridBagConstraints();
		gbc_lblCoName_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblCoName_1.anchor = GridBagConstraints.EAST;
		gbc_lblCoName_1.gridx = 0;
		gbc_lblCoName_1.gridy = 2;
		AddPanel.add(lblCoName_1, gbc_lblCoName_1);

		cmbCoName = new JComboBox();
		GridBagConstraints gbc_cmbCoName = new GridBagConstraints();
		gbc_cmbCoName.anchor = GridBagConstraints.WEST;
		gbc_cmbCoName.gridwidth = 2;
		gbc_cmbCoName.insets = new Insets(0, 0, 5, 5);
		gbc_cmbCoName.gridx = 1;
		gbc_cmbCoName.gridy = 2;
		AddPanel.add(cmbCoName, gbc_cmbCoName);

		JLabel lblCategory = new JLabel("분 류 명 :");
		GridBagConstraints gbc_lblCategory = new GridBagConstraints();
		gbc_lblCategory.insets = new Insets(0, 0, 5, 5);
		gbc_lblCategory.anchor = GridBagConstraints.EAST;
		gbc_lblCategory.gridx = 3;
		gbc_lblCategory.gridy = 2;
		AddPanel.add(lblCategory, gbc_lblCategory);

		cmbCategory = new JComboBox();
		GridBagConstraints gbc_cmbCategory = new GridBagConstraints();
		gbc_cmbCategory.gridwidth = 2;
		gbc_cmbCategory.anchor = GridBagConstraints.WEST;
		gbc_cmbCategory.insets = new Insets(0, 0, 5, 0);
		gbc_cmbCategory.gridx = 4;
		gbc_cmbCategory.gridy = 2;
		AddPanel.add(cmbCategory, gbc_cmbCategory);

		JLabel lblSupPrice = new JLabel("공급가격 :");
		GridBagConstraints gbc_lblSupPrice = new GridBagConstraints();
		gbc_lblSupPrice.anchor = GridBagConstraints.EAST;
		gbc_lblSupPrice.insets = new Insets(0, 0, 5, 5);
		gbc_lblSupPrice.gridx = 0;
		gbc_lblSupPrice.gridy = 3;
		AddPanel.add(lblSupPrice, gbc_lblSupPrice);

		tfSupPrice = new JTextField();
		GridBagConstraints gbc_tfSupPrice = new GridBagConstraints();
		gbc_tfSupPrice.anchor = GridBagConstraints.WEST;
		gbc_tfSupPrice.gridwidth = 2;
		gbc_tfSupPrice.insets = new Insets(0, 0, 5, 5);
		gbc_tfSupPrice.gridx = 1;
		gbc_tfSupPrice.gridy = 3;
		AddPanel.add(tfSupPrice, gbc_tfSupPrice);
		tfSupPrice.setColumns(10);

		JLabel lblCoName = new JLabel("품 목 명 :");
		GridBagConstraints gbc_lblCoName = new GridBagConstraints();
		gbc_lblCoName.anchor = GridBagConstraints.EAST;
		gbc_lblCoName.insets = new Insets(0, 0, 5, 5);
		gbc_lblCoName.gridx = 3;
		gbc_lblCoName.gridy = 3;
		AddPanel.add(lblCoName, gbc_lblCoName);

		tfTitle = new JTextField();
		tfTitle.setColumns(10);
		GridBagConstraints gbc_tfCoName = new GridBagConstraints();
		gbc_tfCoName.gridwidth = 2;
		gbc_tfCoName.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfCoName.insets = new Insets(0, 0, 5, 0);
		gbc_tfCoName.gridx = 4;
		gbc_tfCoName.gridy = 3;
		AddPanel.add(tfTitle, gbc_tfCoName);

		lblTitlePic = new JLabel("제품사진 :");
		GridBagConstraints gbc_lblTitlePic = new GridBagConstraints();
		gbc_lblTitlePic.anchor = GridBagConstraints.EAST;
		gbc_lblTitlePic.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitlePic.gridx = 0;
		gbc_lblTitlePic.gridy = 4;
		AddPanel.add(lblTitlePic, gbc_lblTitlePic);

		btnOpen = new JButton("파일선택");
		btnOpen.addActionListener(this);
		GridBagConstraints gbc_btnAddPic = new GridBagConstraints();
		gbc_btnAddPic.gridwidth = 2;
		gbc_btnAddPic.anchor = GridBagConstraints.WEST;
		gbc_btnAddPic.insets = new Insets(0, 0, 5, 5);
		gbc_btnAddPic.gridx = 1;
		gbc_btnAddPic.gridy = 4;
		AddPanel.add(btnOpen, gbc_btnAddPic);

		JLabel lblSellPrice = new JLabel("판매가격 :");
		GridBagConstraints gbc_lblSellPrice = new GridBagConstraints();
		gbc_lblSellPrice.anchor = GridBagConstraints.EAST;
		gbc_lblSellPrice.insets = new Insets(0, 0, 5, 5);
		gbc_lblSellPrice.gridx = 3;
		gbc_lblSellPrice.gridy = 4;
		AddPanel.add(lblSellPrice, gbc_lblSellPrice);

		tfSellPrice = new JTextField();
		tfSellPrice.setColumns(10);
		GridBagConstraints gbc_tfSellPrice = new GridBagConstraints();
		gbc_tfSellPrice.gridwidth = 2;
		gbc_tfSellPrice.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfSellPrice.insets = new Insets(0, 0, 5, 0);
		gbc_tfSellPrice.gridx = 4;
		gbc_tfSellPrice.gridy = 4;
		AddPanel.add(tfSellPrice, gbc_tfSellPrice);

		tfSellPrice.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (tfSellPrice.getText().matches(".*\\D+.*")) {
					JOptionPane.showMessageDialog(null, "숫자만 입력해주세요");
					tfSellPrice.setText("");
					tfSellPrice.requestFocus();
				}
			}
		});
		tfSupPrice.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (tfSupPrice.getText().matches(".*\\D+.*")) {
					JOptionPane.showMessageDialog(null, "숫자만 입력해주세요");
					tfSupPrice.setText("");
					tfSupPrice.requestFocus();
				}
			}
		});

		lblPreview = new JLabel("미리보기 :");
		GridBagConstraints gbc_lblPreview = new GridBagConstraints();
		gbc_lblPreview.anchor = GridBagConstraints.EAST;
		gbc_lblPreview.insets = new Insets(0, 0, 5, 5);
		gbc_lblPreview.gridx = 0;
		gbc_lblPreview.gridy = 5;
		AddPanel.add(lblPreview, gbc_lblPreview);

		previewPanel = new JPanel();
		GridBagConstraints gbc_previewPanel = new GridBagConstraints();
		gbc_previewPanel.gridwidth = 5;
		gbc_previewPanel.gridheight = 2;
		gbc_previewPanel.insets = new Insets(0, 0, 5, 0);
		gbc_previewPanel.gridx = 1;
		gbc_previewPanel.gridy = 5;
		AddPanel.add(previewPanel, gbc_previewPanel);
		previewPanel.setLayout(new BorderLayout(0, 0));

		lblPicPreview = new JLabel("");
		previewPanel.add(lblPicPreview);

		JPanel BtnPanel = new JPanel();
		add(BtnPanel, BorderLayout.SOUTH);
		GridBagLayout gbl_BtnPanel = new GridBagLayout();
		gbl_BtnPanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_BtnPanel.rowHeights = new int[] { 0, 0 };
		gbl_BtnPanel.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_BtnPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		BtnPanel.setLayout(gbl_BtnPanel);

		btnAdd = new JButton("등록");
		btnAdd.addActionListener(this);
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.anchor = GridBagConstraints.EAST;
		gbc_btnAdd.insets = new Insets(0, 0, 0, 5);
		gbc_btnAdd.gridx = 0;
		gbc_btnAdd.gridy = 0;
		BtnPanel.add(btnAdd, gbc_btnAdd);

		btnCancel = new JButton("취소");
		btnCancel.addActionListener(this);
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.anchor = GridBagConstraints.WEST;
		gbc_btnCancel.gridx = 1;
		gbc_btnCancel.gridy = 0;
		BtnPanel.add(btnCancel, gbc_btnCancel);

		// 파일선택창 생성
		jfc = new JFileChooser();
		jfc.setFileFilter(new FileNameExtensionFilter("JPG & GIF & PNG Images", "jpg", "gif", "png"));
		jfc.setMultiSelectionEnabled(false);
		jfc.setDialogTitle("사진을 선택하세요");

		// 목록에서 등록 or 수정 버튼이 작동되었는지 여부에 따라 수행
		if (e.equals("등록")) {
			// 등록을 위한 카테고리와 공급회사 목록 콤보박스에 추가
			cmbCategory.addItem("게임");
			cmbCategory.addItem("사무");
			cmbCategory.addItem("그래픽");
			for (Company c : companyList) {
				cmbCoName.addItem(c.getCoName());
			}
			lblTitle.setText("소프트웨어 등록");
			try {
				tfNo.setText(SoftwareService.getInstance().selectMaxNo().getNo() + "");
			} catch (NullPointerException e2) {
				tfNo.setText("1");
			}

		} else if (e.equals("수정")) {
			lblTitle.setText("소프트웨어 정보 수정");
			updateAction(flag);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateAction(int no) {
		// 리스트에서 선택된 해당 항목에 대한 정보를 가져옴
		btnAdd.setText("수정");
		Software list = SoftwareService.getInstance().selectByNo(no);
		tfNo.setText(list.getNo() + "");
		tfTitle.setText(list.getTitle());
		tfSupPrice.setText(list.getSupPrice() + "");
		tfSellPrice.setText(list.getSellPrice() + "");
		selectedTitle = list.getTitle();
		// 선택된 해당항목의 이미지값이 있을시 이미지 미리보기 생성
		if (list.getPicPath() != null) {
			lblPicPreview.setIcon(new ImageIcon(new ImageIcon((byte[]) list.getPicPath()).getImage()
					.getScaledInstance(200, 150, java.awt.Image.SCALE_SMOOTH)));
		}

		for (Software s : softwareList) {
			cmbCategory.addItem(s.getCategory());
		}
		for (Company c : companyList) {
			cmbCoName.addItem(c.getCoName());
		}
		// 카테고리 콤보박스에 선택된 항목에 대한 공급회사를 가져옴
		cmbCategory.setSelectedItem(list.getCategory());
		cmbCoName.setSelectedItem(list.getCoName().getCoName());
		revalidate();
		repaint();
	}

	@Override
	public void refresh(JPanel p) {
		// 패널값을 가져와 새로고침
		removeAll();
		add(p);
		revalidate();
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnOpen) {
			// 파일선택후 확인을 누를시
			if (jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				// 선택한 파일의 확장자명을 가져와 비교
				String[] path = jfc.getSelectedFile().getPath().split("\\.");
				if (path[path.length - 1].equals("jpg") || path[path.length - 1].equals("gif")
						|| path[path.length - 1].equals("png")) {
					lblPicPreview.setIcon(new ImageIcon(new ImageIcon(jfc.getSelectedFile().getPath()).getImage()
							.getScaledInstance(200, 150, java.awt.Image.SCALE_SMOOTH)));
				} else {
					jfc.setSelectedFile(null);
					JOptionPane.showMessageDialog(null, "지원하지 않는 형식의 파일 입니다");
				}

			}
		}
		// 빈칸과 중복여부 통과후 제품 등록 및 수정
		switch (e.getActionCommand()) {
		case "등록":
			if (inputValidation() && duplicateValidation(e.getActionCommand())) {
				try {
					// 선택한 이미지 파일이 없을시 null 있을시 이미지값을 등록
					if (jfc.getSelectedFile() == null) {
						SoftwareService.getInstance().insertItem(new Software(Integer.parseInt(tfNo.getText()),
								cmbCategory.getSelectedItem() + "", tfTitle.getText().trim(), null,
								Integer.parseInt(tfSupPrice.getText().trim()),
								Integer.parseInt(tfSellPrice.getText().trim()), cmbCoName.getSelectedItem() + ""));
					} else {
						SoftwareService.getInstance().insertItem(new Software(Integer.parseInt(tfNo.getText()),
								cmbCategory.getSelectedItem() + "", tfTitle.getText().trim(),
								Files.readAllBytes(new File(jfc.getSelectedFile().toString()).toPath()),
								Integer.parseInt(tfSupPrice.getText().trim()),
								Integer.parseInt(tfSellPrice.getText().trim()), cmbCoName.getSelectedItem() + ""));
					}
					JOptionPane.showMessageDialog(null, "등록이 완료되었습니다.");

				} catch (Exception e2) {
					e2.printStackTrace();
				} finally {
					refresh(new SoftwareList());
					getTopLevelAncestor().setSize(750, 400);
				}
			}
			break;
		case "수정":
			if (inputValidation() && duplicateValidation(e.getActionCommand())) {
				btnAdd.setText("등록");
				try {
					// 수정할 데이터의 이미지값을 변경시 새로운 이미지로
					// 수정할 데이터의 이미지값을 변경하지 않을시 기존 이미지 보존
					// (SoftwareMapper.xml에 insert쿼리 참조)
					if (jfc.getSelectedFile() == null) {
						SoftwareService.getInstance().updateItem(new Software(Integer.parseInt(tfNo.getText()),
								cmbCategory.getSelectedItem() + "", tfTitle.getText().trim(),
								Integer.parseInt(tfSupPrice.getText().trim()),
								Integer.parseInt(tfSellPrice.getText().trim()), cmbCoName.getSelectedItem() + ""));
					} else {
						SoftwareService.getInstance().updateItem(new Software(Integer.parseInt(tfNo.getText()),
								cmbCategory.getSelectedItem() + "", tfTitle.getText().trim(),
								Files.readAllBytes(new File(jfc.getSelectedFile().toString()).toPath()),
								Integer.parseInt(tfSupPrice.getText().trim()),
								Integer.parseInt(tfSellPrice.getText().trim()), cmbCoName.getSelectedItem() + ""));
					}
					JOptionPane.showMessageDialog(null, "수정이 완료되었습니다.");
				} catch (Exception e2) {
					e2.printStackTrace();
				} finally {
					refresh(new SoftwareList());
					getTopLevelAncestor().setSize(750, 400);
				}
			}
			break;
		case "취소":
			refresh(new SoftwareList());
			getTopLevelAncestor().setSize(750, 400);
			break;
		}

	}

	@Override
	public boolean inputValidation() {
		// 빈칸 여부 검사
		if (tfNo.getText().equals("") || tfTitle.getText().trim().equals("") || tfSupPrice.getText().trim().equals("")
				|| tfSellPrice.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "빈칸없이 입력해 주세요");
			return false;
		} else if (tfSupPrice.getText().trim().matches(".*\\D+.*")
				|| tfSellPrice.getText().trim().matches(".*\\D+.*")) {
			JOptionPane.showMessageDialog(null, "공급가격 및 판매가격은 숫자만 입력해주세요");
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean duplicateValidation(String e) {
		// 중복 여부 검사 list에 모든 제품명을 가져옴
		List<Software> list = SoftwareService.getInstance().selectTitle();
		boolean flag = true;
		switch (e) {
		case "등록":
			for (Software s : list) {
				// 입력받은 제품명을 list에 가져온 제품명들과 중복검사
				if (tfTitle.getText().trim().equals(s.getTitle())) {
					flag = false;
					tfTitle.requestFocus();
					tfTitle.selectAll();
					JOptionPane.showMessageDialog(null, "입력하신 품목이름이 중복되었습니다");
				}
			}
			break;
		case "수정":
			// 선택한 항목, 자기자신의 제품명을 제외한 나머지 제품명을 담을 배열 생성
			String[] title = new String[list.size()];
			int idx = 0;
			// 자기자신을 제외한 제품명을 list에서 가져와 title배열에 담기
			for (Software s : list) {
				if (s.getTitle().equals(selectedTitle) == false) {
					title[idx] = s.getTitle();
					idx++;
				}
			}
			// title배열을 입력한 제품명과 비교하여 중복검사(최초선택된 자기자신의 제품명은 제외)
			for (int i = 0; i < list.size() - 1; i++) {
				if (title[i].equals(tfTitle.getText().trim())) {
					flag = false;
					tfTitle.requestFocus();
					tfTitle.selectAll();
					JOptionPane.showMessageDialog(null, "다른 품목이름과 중복되었습니다");
				}
			}
			break;
		}
		return flag;
	}

}
