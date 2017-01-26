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
import javax.swing.JCheckBox;
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
import kr.or.dgit.bigdata.swmng.customer.regedit.SoftwareRegEdit;
import kr.or.dgit.bigdata.swmng.dto.Software;
import kr.or.dgit.bigdata.swmng.service.SoftwareService;
import kr.or.dgit.bigdata.swmng.util.ModelForTable;

public class SoftwareList extends JPanel implements ActionListener, ListInterface {
	private JButton btnAdd;
	private JButton btnUpdate;
	private JButton btnDel;
	private JTable softwareList;
	private JPanel listPanel;

	public SoftwareList() {

		setLayout(new BorderLayout(0, 0));

		JLabel listTitle = new JLabel("소프트웨어 목록");
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
		gbc_btnUpdate.anchor = GridBagConstraints.EAST;
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

		// 리스트 생성
		createList();

	}

	@Override
	public void refresh(JPanel p) {
		// 페이지 새로고침을 위한 인터페이스 오버라이드
		removeAll();
		add(p);
		revalidate();
		repaint();

	}



	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "등록":
			// 등록버튼 클릭시 등록화면 호출
			refresh(new SoftwareRegEdit(e.getActionCommand(), 0));
			getTopLevelAncestor().setSize(600, 400);
			break;
		case "수정":
			// 테이블리스트에서 선택한 항목 있을시 수정화면 호출
			if (softwareList.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "수정 할 항목을 선택해주세요");
			} else {
				int flag = Integer.parseInt(softwareList.getValueAt(softwareList.getSelectedRow(), 0) + "");
				refresh(new SoftwareRegEdit(e.getActionCommand(), flag));
				getTopLevelAncestor().setSize(600, 400);
			}
			break;
		case "삭제":
			// 테이블리스트에서 선택한 항목 있을시 선택한 데이터 삭제 컨펌후 삭제
			if (softwareList.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "삭제 할 항목을 선택해주세요");
			} else {
				//리스트에서 선택한 항목의 첫번째 컬럼의 value 값(소프트웨어 번호)를 넘겨줌
				deleteAction(Integer.parseInt(softwareList.getValueAt(softwareList.getSelectedRow(), 0) + ""));
			}
			break;
		}
	}
	
	@Override
	public void deleteAction(int no) {
		// 선택한 컬럼의 소프트웨어의 DB순번을 받아와 삭제
		if (JOptionPane.showConfirmDialog(null, "선택한 항목을 삭제하시겠습니까?") == 0) {
			SoftwareService.getInstance().deleteItem(no);
			JOptionPane.showMessageDialog(null, "삭제가 완료되었습니다");
			refresh(new SoftwareList());
		} else {
			JOptionPane.showMessageDialog(null, "취소하였습니다");
		}

	}

	@Override
	public void createList() {
		// 리스트 생성
		List<Software> list = SoftwareService.getInstance().selectAll();
		String[] COL_NAMES = { "품번", "분류명", "품목명", "공급가격", "판매가격", "공급회사명", "비고" };
		Object[][] data = new Object[list.size()][COL_NAMES.length];
		int idx = 0;
		for (Software s : list) {
			data[idx][0] = s.getNo() + "";
			data[idx][1] = s.getCategory();
			data[idx][2] = s.getTitle();
			data[idx][3] = String.format("%,d", s.getSupPrice()) + "원";
			data[idx][4] = String.format("%,d", s.getSellPrice()) + "원";
			data[idx][5] = s.getCoName().getCoName();
			// 데이터베이스 이미지값이 없을시 공백으로 입력
			if (s.getPicPath() == null) {
				data[idx][6] = "";
			} else {
				// 이미지 있을시 30x30사이즈로 테이블 삽입
				data[idx][6] = new ImageIcon(new ImageIcon((byte[]) s.getPicPath()).getImage().getScaledInstance(30, 30,
						java.awt.Image.SCALE_SMOOTH));
			}
			idx++;
		}
		// 테이블 모델 생성
		ModelForTable mft = new ModelForTable(data, COL_NAMES);
		// 리스트별 내용에 따른 cell클래스 지정
		softwareList = new JTable(mft) {
			public Class getColumnClass(int column) {
				// 이미지 컬럼인 6번째 cell을 이미지아이콘으로 렌더링
				if (column == 6) {
					return ImageIcon.class;
				}
				return String.class;
			}
		};

		//리스트 세부설정
		mft.resizeColumnHeight(softwareList);
		mft.resizeColumnWidth(softwareList);
		mft.tableHeaderAlignment(softwareList);
		mft.tableCellAlignment(softwareList, SwingConstants.CENTER, 0, 1, 2, 5);
		mft.tableCellAlignment(softwareList, SwingConstants.RIGHT, 3, 4);
		softwareList.setModel(mft);
		softwareList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		softwareList.setFont(softwareList.getFont().deriveFont(12.0f));
		softwareList.getTableHeader().setReorderingAllowed(false);
		listPanel.add(new JScrollPane(softwareList));
	}

}
