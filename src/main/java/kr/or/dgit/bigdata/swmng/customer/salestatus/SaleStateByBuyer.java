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
import kr.or.dgit.bigdata.swmng.service.SaleService;
import kr.or.dgit.bigdata.swmng.util.ModelForTable;

@SuppressWarnings("serial")
public class SaleStateByBuyer extends JPanel  {
	private ModelForTable mft;
	private final boolean CHECK = true;
	private final boolean UNCHECK = false;

	private JTable table;
	private JComboBox comboBox;
	private JPanel pnForControl;
	private JPanel subPnForControl;
	private JScrollPane scrollPane;
	private JCheckBox checkBox;
	private JPanel pnForTable;
	private JPanel pnForResult;
	private JLabel lblTotalSales;
	private JTextField txtTotalSales;
	private JLabel lblTotalNotYet;
	private JTextField txtTotalNotYet;

	/**
	 * Create the panel.
	 */
	public SaleStateByBuyer()  {
		pnForControl = new JPanel();
		JLabel lblTitle = new JLabel("고객별 판매현황 조회");
		subPnForControl = new JPanel();
		JLabel lblCombo = new JLabel("고객상호명 :");
		comboBox = new JComboBox();
		checkBox = new JCheckBox("전체");
		
		pnForTable = new JPanel();
		scrollPane = new JScrollPane();

		table = new JTable();

		pnForResult = new JPanel();
		pnForResult.setBorder(new EmptyBorder(2, 20, 2, 20));
		lblTotalSales = new JLabel("매출액 합계 : ");
		lblTotalSales.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTotalSales = new JTextField();
		txtTotalSales.setEditable(false);
		txtTotalSales.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotalNotYet = new JLabel("미수금 합계 : ");
		lblTotalNotYet.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTotalNotYet = new JTextField();
		txtTotalNotYet.setEditable(false);
		txtTotalNotYet.setHorizontalAlignment(SwingConstants.RIGHT);

		
		setLayout(new BorderLayout(0, 0));
		pnForControl.setBorder(new EmptyBorder(10, 10, 10, 10));
		pnForControl.setLayout(new BorderLayout(0, 0));
		subPnForControl.setBorder(new LineBorder(Color.DARK_GRAY));
		pnForTable.setLayout(new BorderLayout(0, 0));
		pnForResult.setLayout(new GridLayout(0, 4, 0, 0));

		
		lblTitle.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		txtTotalNotYet.setColumns(10);
		txtTotalSales.setColumns(10);

		
		pnForControl.add(lblTitle, BorderLayout.NORTH);
		pnForControl.add(subPnForControl, BorderLayout.SOUTH);
		subPnForControl.add(lblCombo);
		subPnForControl.add(comboBox);
		subPnForControl.add(checkBox);
		add(pnForControl, BorderLayout.NORTH);
		
		pnForTable.add(scrollPane, BorderLayout.CENTER);
		add(pnForTable, BorderLayout.CENTER);

		pnForResult.add(lblTotalSales);
		pnForResult.add(txtTotalSales);
		pnForResult.add(lblTotalNotYet);
		pnForResult.add(txtTotalNotYet);
		add(pnForResult, BorderLayout.SOUTH);
		
		
		comboBox.addActionListener(new ActionListener() { // 콤보박스 클릭 시
			public void actionPerformed(ActionEvent e) {
				createList();
			}
		});
		
		checkBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createList();
				repaint();
			}
		});
		
		
		setComList();
		scrollPane.setViewportView(table);
		createList();
	}
	
	// 콤보박스 아이템
	private void setComList() {
				List<Sale> shopname = SaleService.getInstance().selectSaleshopName();
				for (Sale s : shopname) {
					comboBox.addItem(s.getShopName());
				}
	}

	private void createList() { // 고객상호명별 리스트
	
		List<Sale> list=getList();
		
		String[] COL_NAMES = { "고객상호명", "품목명", "주문수량", "입금여부", "판매가격", "매출금", "미수금" };
		String[][] data = new String[list.size()][COL_NAMES.length];
		
		int idx = 0;
		int rowCnt = 0;

		int sum1 = 0;
		int sum2 = 0;
		
		for (Sale c : list) {
			data = getRowData(data, c, idx);
			sum1 += c.getSales();
			sum2 += c.getNotmoney();
			idx++;
		}
		rowCnt = idx;
		
		//합계가 0인지 체크 
		//if 0 이면 '-'표시
		int[] temp = { sum1, sum2};
		String[] rArr = checkZero(temp);

		txtTotalSales.setText(rArr[0]);
		txtTotalNotYet.setText(rArr[1]);

		mft = new ModelForTable(data, COL_NAMES);
		table.setModel(mft);

		handleTableDesign();
		
	}
	
	private String[] checkZero(int[] temp) {
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
		if (!checkBox.isSelected()) {
			comboBox.setEnabled(CHECK);
			
			 Sale sale = new Sale();
			 sale.setShopName(comboBox.getSelectedItem() + "");
			 list = SaleService.getInstance().selectSaleshopNameByAll(sale);
			 
		}else{
			comboBox.setEnabled(UNCHECK);
			checkBox.setSelected(true);
			
			list = SaleService.getInstance().selectBuyerStateSales();
		}
		return list;
	}

	private void handleTableDesign() {
		mft.resizeColumnWidth(table);
		mft.tableCellAlignment(table, SwingConstants.CENTER, 0, 1, 2, 3);
		mft.tableCellAlignment(table, SwingConstants.RIGHT, 4, 5, 6);
		mft.tableHeaderAlignment(table);
		table.setFont(table.getFont().deriveFont(11.0f));
	}

	private String[][] getRowData(String[][] data, Sale c, int idx) {
		data[idx][0] = c.getShopName();
		data[idx][1] = c.getTitle();
		data[idx][2] = c.getOrderCount() + "";
		data[idx][3] = c.isPayment() + "";
		data[idx][4] = String.format("%,d", c.getSellPrice()) + "원";
		data[idx][5] = String.format("%,d", c.getSales()) + "원";
		data[idx][6] = String.format("%,d", c.getNotmoney()) + "원";
		return data;
	}
}
