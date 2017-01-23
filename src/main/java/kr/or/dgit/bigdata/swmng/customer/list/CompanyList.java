package kr.or.dgit.bigdata.swmng.customer.list;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import kr.or.dgit.bigdata.swmng.customer.member.LoginForm;
import kr.or.dgit.bigdata.swmng.customer.regedit.CompanyRegEdit;
import kr.or.dgit.bigdata.swmng.dto.Company;
import kr.or.dgit.bigdata.swmng.service.CompanyService;
import kr.or.dgit.bigdata.swmng.util.ModelForTable;
import java.awt.Font;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

public class CompanyList extends JPanel implements ActionListener, ListInterface {
	private JButton btnAdd;
	private JButton btnUpdate;
	private JButton btnDel;
	private JTable companyList = new JTable();
	private JPanel listPanel;
	private JPanel btnPanel;

	public CompanyList() {

		setLayout(new BorderLayout(0, 0));

		JLabel listTitle = new JLabel("공급회사 목록");
		listTitle.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		listTitle.setHorizontalAlignment(SwingConstants.CENTER);
		add(listTitle, BorderLayout.NORTH);

		listPanel = new JPanel();
		listPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		add(listPanel, BorderLayout.CENTER);
		listPanel.setLayout(new BorderLayout(0, 0));

		btnPanel = new JPanel();
		add(btnPanel, BorderLayout.SOUTH);
		GridBagLayout gbl_BtnPanel = new GridBagLayout();
		gbl_BtnPanel.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_BtnPanel.rowHeights = new int[] { 0, 0 };
		gbl_BtnPanel.columnWeights = new double[] { 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_BtnPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		btnPanel.setLayout(gbl_BtnPanel);

		btnAdd = new JButton("등록");
		btnAdd.addActionListener(this);
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.anchor = GridBagConstraints.EAST;
		gbc_btnAdd.insets = new Insets(0, 0, 0, 5);
		gbc_btnAdd.gridx = 0;
		gbc_btnAdd.gridy = 0;
		btnPanel.add(btnAdd, gbc_btnAdd);

		btnUpdate = new JButton("수정");
		btnUpdate.addActionListener(this);
		GridBagConstraints gbc_btnUpdate = new GridBagConstraints();
		gbc_btnUpdate.insets = new Insets(0, 0, 0, 5);
		gbc_btnUpdate.gridx = 1;
		gbc_btnUpdate.gridy = 0;
		btnPanel.add(btnUpdate, gbc_btnUpdate);

		btnDel = new JButton("삭제");
		btnDel.addActionListener(this);
		GridBagConstraints gbc_btnDel = new GridBagConstraints();
		gbc_btnDel.insets = new Insets(0, 0, 0, 5);
		gbc_btnDel.anchor = GridBagConstraints.WEST;
		gbc_btnDel.gridx = 2;
		gbc_btnDel.gridy = 0;
		btnPanel.add(btnDel, gbc_btnDel);
		
		if (new LoginForm().getMemberId().equals("admin")) {
			btnPanel.setVisible(true);
		} else {
			btnPanel.setVisible(false);
		}

		createList();

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		switch (e.getActionCommand()) {
		case "등록":
			refresh(new CompanyRegEdit(e.getActionCommand(), 0));
			getTopLevelAncestor().setSize(560, 400);
			break;
		case "수정":
			if (companyList.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "수정 할 항목을 선택해주세요");
			} else {
				int flag = Integer.parseInt(companyList.getValueAt(companyList.getSelectedRow(), 0) + "");
				refresh(new CompanyRegEdit(e.getActionCommand(), flag));
				getTopLevelAncestor().setSize(560, 400);
			}
			break;
		case "삭제":

			if (companyList.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "삭제 할 항목을 선택해주세요");
			} else {
				deleteAction(Integer.parseInt(companyList.getValueAt(companyList.getSelectedRow(), 0) + ""));
			}
			break;
		}

	}

	@Override
	public void deleteAction(int no) {
		if (JOptionPane.showConfirmDialog(null, "선택한 항목을 삭제하시겠습니까?") == 0) {
			CompanyService.getInstance().deleteItem(no);
			JOptionPane.showMessageDialog(null, "삭제가 완료되었습니다");
			refresh(new CompanyList());
		} else {
			JOptionPane.showMessageDialog(null, "취소하였습니다");
		}
	}

	@Override
	public void refresh(JPanel p) {
		removeAll();
		add(p);
		revalidate();
		repaint();
	}

	@Override
	public void createList() {
		List<Company> list = CompanyService.getInstance().selectAll();
		String[] COL_NAMES = { "회사번호", "회사명", "주소", "전화번호" };
		String[][] data = new String[list.size()][COL_NAMES.length];
		int idx = 0;
		for (Company c : list) {
			data[idx][0] = c.getNo() + "";
			data[idx][1] = c.getCoName();
			data[idx][2] = c.getAddress();
			data[idx][3] = c.getTel();
			idx++;
		}
		ModelForTable mft = new ModelForTable(data, COL_NAMES);
		companyList.setModel(mft);
		mft.tableCellAlignment(companyList, SwingConstants.CENTER, 0, 3);
		companyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		mft.resizeColumnWidth(companyList);
		mft.tableHeaderAlignment(companyList);
		listPanel.add(new JScrollPane(companyList));
		companyList.setFont(new Font("돋움", Font.PLAIN, 12));
		companyList.getTableHeader().setReorderingAllowed(false);

	}

}
