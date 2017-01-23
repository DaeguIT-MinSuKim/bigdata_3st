package kr.or.dgit.bigdata.swmng.customer.salestatus;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import kr.or.dgit.bigdata.swmng.dto.Sale;
import kr.or.dgit.bigdata.swmng.dto.Software;
import kr.or.dgit.bigdata.swmng.service.SaleService;
import kr.or.dgit.bigdata.swmng.service.SoftwareService;
import kr.or.dgit.bigdata.swmng.util.ModelForTable;

public class SaleStateBySoftware extends JPanel {
	private ModelForTable mft;
	private final boolean CHECK = true;
	private final boolean UNCHECK = false;

	private JTable table;
	private JComboBox comboBox;
	private JPanel pnForControl;
	private JPanel subPnForControl;

	private JCheckBox checkBox;
	private JPanel pnForTable;
	private JLabel lblLeft;
	private JTextField txtLeft;
	private JLabel lblCenter;
	private JTextField txtCenter;
	private JLabel lblRight;
	private JTextField txtRight;
	private JScrollPane scrollPane;

	/**
	 * Create the panel.
	 */
	public SaleStateBySoftware() {
		pnForControl = new JPanel();
		subPnForControl = new JPanel();
		JLabel lblTitle = new JLabel("S/W 판매현황 조회");
		JLabel lblCombo = new JLabel("품 목 명 :");
		comboBox = new JComboBox();
		checkBox = new JCheckBox("전체");

		pnForTable = new JPanel();
		scrollPane = new JScrollPane();
		table = new JTable();

		JPanel pnForResult = new JPanel();
		lblLeft = new JLabel("공급금액 합계 :");
		lblLeft.setFont(new Font("굴림", Font.PLAIN, 11));
		txtLeft = new JTextField();
		txtLeft.setFont(new Font("굴림", Font.PLAIN, 11));
		txtCenter = new JTextField();
		txtCenter.setFont(new Font("굴림", Font.PLAIN, 11));
		lblCenter = new JLabel("판매금액 합계 :");
		lblCenter.setFont(new Font("굴림", Font.PLAIN, 11));
		lblRight = new JLabel("판매이윤 합계 :");
		lblRight.setFont(new Font("굴림", Font.PLAIN, 11));
		txtRight = new JTextField();
		txtRight.setFont(new Font("굴림", Font.PLAIN, 11));

		setLayout(new BorderLayout(0, 0));
		pnForControl.setBorder(new EmptyBorder(10, 10, 10, 10));
		pnForControl.setLayout(new BorderLayout(0, 0));
		subPnForControl.setBorder(new LineBorder(Color.DARK_GRAY));
		pnForTable.setLayout(new BorderLayout(0, 0));
		pnForResult.setBorder(new EmptyBorder(2, 10, 2, 10));
		pnForResult.setLayout(new GridLayout(1, 1, 0, 0));

		lblTitle.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblLeft.setHorizontalAlignment(SwingConstants.RIGHT);
		txtLeft.setHorizontalAlignment(SwingConstants.RIGHT);
		txtLeft.setColumns(10);
		txtCenter.setHorizontalAlignment(SwingConstants.RIGHT);
		txtCenter.setColumns(10);
		lblCenter.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRight.setHorizontalAlignment(SwingConstants.RIGHT);
		txtRight.setHorizontalAlignment(SwingConstants.RIGHT);
		txtRight.setColumns(10);

		add(pnForControl, BorderLayout.NORTH);
		pnForControl.add(lblTitle, BorderLayout.NORTH);
		pnForControl.add(subPnForControl, BorderLayout.SOUTH);
		subPnForControl.add(lblCombo);
		subPnForControl.add(comboBox);
		subPnForControl.add(checkBox);

		add(pnForTable, BorderLayout.CENTER);
		pnForTable.add(scrollPane, BorderLayout.CENTER);

		add(pnForResult, BorderLayout.SOUTH);
		pnForResult.add(lblLeft);
		pnForResult.add(txtLeft);
		pnForResult.add(lblCenter);
		pnForResult.add(txtCenter);
		pnForResult.add(lblRight);
		pnForResult.add(txtRight);

		comboBox.addActionListener(new ActionListener() {// 콤보박스 클릭 시
			public void actionPerformed(ActionEvent e) {
				createList();
			}
		});

		checkBox.addActionListener(new ActionListener() { // 체크박스 클릭 시
			public void actionPerformed(ActionEvent e) {
				createList();
				repaint();
			}
		});

		setComList();
		scrollPane.setViewportView(table);
		createList();
	}

	private void setComList() { // 콤보박스 아이템  품목명으로 나열
		List<Software> title = SoftwareService.getInstance().selectSoftwareBytitle();
		for (Software s : title) {
			comboBox.addItem(s.getTitle());
		}

	}

	private void createList() { // 품목명별 리스트 
		List<Sale> list = getList();

		String[] COL_NAMES = { "품목명", "분류", "공급회사명", "공급금액", "판매금액", "판매이윤" };
		String[][] data = new String[list.size()][COL_NAMES.length];
		int idx = 0;
		int rowCnt = 0;

		int sum1 = 0;
		int sum2 = 0;
		int sum3 = 0;
		for (Sale s : list) {

			data = getRowData(data, s, idx);
			sum1 += s.getSupAmount();
			sum2 += s.getSaleAmount();
			sum3 += s.getSaleProfits();
			idx++;
		}
		rowCnt = idx;
		
		//합계가 0인지 체크 
		//if 0 이면 '-'표시
		int[] temp = { sum1, sum2, sum3 };
		String[] rArr = checkZero(temp); 

		txtLeft.setText(rArr[0]);
		txtCenter.setText(rArr[1]);
		txtRight.setText(rArr[2]);

		mft = new ModelForTable(data, COL_NAMES);
		table.setModel(mft);

		handleTableDesign();
	}

	private String[] checkZero(int[] temp) {  // 공금금액 합계,판매금액 합계,판매이윤 합계 가 '0'이면 -> '-'으로 출력 ,데이터 있으면 원본 출력
		String[] rArr = new String[3];
		for (int i = 0; i < temp.length; i++) {
			if (temp[i] == 0) {
				rArr[i] = "-";
			} else {
				rArr[i]= String.format("%,d", temp[i]) + "원";
			}
		}
		return rArr;
	}

	private List<Sale> getList() {
		List<Sale> list = null;
		if (!checkBox.isSelected()) { // 체크박스가 선택되지 않았을때 
			comboBox.setEnabled(CHECK); // 콤보박스 사용가능
			//↓ 콤보박스 선택되었는 품목별 출력
			list = SaleService.getInstance().selectAllForSWSalesTableByTitle(comboBox.getSelectedItem() + "");
		} else {
			comboBox.setEnabled(UNCHECK); // 콤보박스 사용불가
			checkBox.setSelected(true);// 체크박스 체크

			list = SaleService.getInstance().selectAllForSWSalesTable(); // SW별 판매현황 모두 출력
		}
		return list;
	}

	private void handleTableDesign() {
		mft.resizeColumnWidth(table);
		mft.tableCellAlignment(table, SwingConstants.CENTER, 0, 1, 2);
		mft.tableCellAlignment(table, SwingConstants.RIGHT, 3, 4, 5);
		mft.tableHeaderAlignment(table);
		table.setFont(table.getFont().deriveFont(11.0f));
		table.getTableHeader().setReorderingAllowed(false);
	}

	private String[][] getRowData(String[][] data, Sale s, int idx) { // 각 행 해당하는 데이터 가져오기
		data[idx][0] = s.getTitle();
		data[idx][1] = s.getCategory();
		data[idx][2] = s.getCoName();
		data[idx][3] = String.format("%,d", s.getSupAmount()) + "원";
		data[idx][4] = String.format("%,d", s.getSaleAmount()) + "원";
		data[idx][5] = String.format("%,d", s.getSaleProfits()) + "원";
		return data;
	}

}
