package kr.or.dgit.bigdata.swmng.customer.list;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;

import kr.or.dgit.bigdata.swmng.customer.member.LoginForm;
import kr.or.dgit.bigdata.swmng.customer.regedit.BuyerRegEdit;
import kr.or.dgit.bigdata.swmng.dto.Buyer;
import kr.or.dgit.bigdata.swmng.service.BuyerService;
import kr.or.dgit.bigdata.swmng.util.ModelForTable;

public class BuyerList extends JPanel implements ActionListener, ListInterface {
	private JTable buyerList;
	private JPanel listPanel;
	private JLabel listTitle;

	public BuyerList() {

		setLayout(new BorderLayout(0, 0));

		listTitle = new JLabel("고객 목록");
		listTitle.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		listTitle.setHorizontalAlignment(SwingConstants.CENTER);
		add(listTitle, BorderLayout.NORTH);

		listPanel = new JPanel();
		listPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		add(listPanel, BorderLayout.CENTER);
		listPanel.setLayout(new BorderLayout(0, 0));

		JPanel btnPanel = new JPanel();
		add(btnPanel, BorderLayout.SOUTH);
		GridBagLayout gbl_BtnPanel = new GridBagLayout();
		gbl_BtnPanel.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_BtnPanel.rowHeights = new int[] { 0, 0 };
		gbl_BtnPanel.columnWeights = new double[] { 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_BtnPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		btnPanel.setLayout(gbl_BtnPanel);

		JButton btnAdd = new JButton("등록");
		btnAdd.addActionListener(this);
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.anchor = GridBagConstraints.EAST;
		gbc_btnAdd.insets = new Insets(0, 0, 0, 5);
		gbc_btnAdd.gridx = 0;
		gbc_btnAdd.gridy = 0;
		btnPanel.add(btnAdd, gbc_btnAdd);

		JButton btnUpdate = new JButton("수정");
		btnUpdate.addActionListener(this);
		GridBagConstraints gbc_btnUpdate = new GridBagConstraints();
		gbc_btnUpdate.insets = new Insets(0, 0, 0, 5);
		gbc_btnUpdate.gridx = 1;
		gbc_btnUpdate.gridy = 0;
		btnPanel.add(btnUpdate, gbc_btnUpdate);

		JButton btnDel = new JButton("삭제");
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
	public void refresh(JPanel p) {
		removeAll();
		add(p);
		revalidate();
		repaint();
	}

	@Override
	public void deleteAction(int no) {
		if (JOptionPane.showConfirmDialog(null, "선택한 항목을 삭제하시겠습니까?") == 0) {
			BuyerService.getInstance().deleteItem(no);
			JOptionPane.showMessageDialog(null, "삭제가 완료되었습니다");
			refresh(new BuyerList());
		} else {
			JOptionPane.showMessageDialog(null, "취소하였습니다");
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		switch (e.getActionCommand()) {
		case "등록":
			refresh(new BuyerRegEdit(e.getActionCommand(), 0));
			getTopLevelAncestor().setSize(500, 400);

			break;
		case "수정":
			if (buyerList.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "수정 할 항목을 선택해주세요");
			} else {
				int flag = Integer.parseInt(buyerList.getValueAt(buyerList.getSelectedRow(), 0) + "");
				refresh(new BuyerRegEdit(e.getActionCommand(), flag));
				getTopLevelAncestor().setSize(500, 400);
			}
			break;
		case "삭제":

			if (buyerList.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "삭제 할 항목을 선택해주세요");
			} else {
				deleteAction(Integer.parseInt(buyerList.getValueAt(buyerList.getSelectedRow(), 0) + ""));
			}
			break;
		}

	}

	@Override
	public void createList() {
		List<Buyer> list = BuyerService.getInstance().selectAll();
		String[] COL_NAMES = { "등록번호", "상호", "주소", "전화번호", "비고" };
		Object[][] data = new Object[list.size()][COL_NAMES.length];
		int idx = 0;
		for (Buyer b : list) {
			data[idx][0] = b.getNo() + "";
			data[idx][1] = b.getShopName();
			data[idx][2] = b.getAddress();
			data[idx][3] = b.getTel();
			if (b.getPicPath() == null) {
				data[idx][4] = "";
			} else {
				data[idx][4] = new ImageIcon(new ImageIcon((byte[]) b.getPicPath()).getImage().getScaledInstance(30, 30,
						java.awt.Image.SCALE_SMOOTH));
			}

			idx++;
		}

		ModelForTable mft = new ModelForTable(data, COL_NAMES);
		buyerList = new JTable(mft) {
			public Class getColumnClass(int column) {
				if (column == 4) {
					return ImageIcon.class;
				}
				return String.class;
			}
		};
		mft.resizeColumnHeight(buyerList);
		mft.resizeColumnWidth(buyerList);
		mft.tableHeaderAlignment(buyerList);
		mft.tableCellAlignment(buyerList, SwingConstants.CENTER, 0, 3);

		buyerList.setModel(mft);
		buyerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		buyerList.setFont(buyerList.getFont().deriveFont(12.0f));
		buyerList.getTableHeader().setReorderingAllowed(false);
		listPanel.add(new JScrollPane(buyerList));
	}

}
