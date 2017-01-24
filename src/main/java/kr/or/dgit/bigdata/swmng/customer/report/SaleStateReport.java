package kr.or.dgit.bigdata.swmng.customer.report;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

import kr.or.dgit.bigdata.swmng.dto.Sale;
import kr.or.dgit.bigdata.swmng.service.SaleService;
import kr.or.dgit.bigdata.swmng.util.ModelForTable;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class SaleStateReport extends JPanel implements ActionListener {
	
	private JRadioButton rdTotalSale;
	private JRadioButton rdTradeDetail;
	private JPanel panelForTable;
	private JTable table;
	private JScrollPane scrollPane;
	private ModelForTable mft;
	private JPanel pnForResult;
	private JLabel lblResult;
	private JTextField txtResult;

	public SaleStateReport() {
		try {
			UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/* 컴포넌트 생성 */
		JPanel pnForControl = new JPanel();
		JLabel title = new JLabel("판매현황 보고서");
		JPanel subPnForControl = new JPanel();
		rdTotalSale = new JRadioButton("S/W 전체 판매현황");
		rdTradeDetail = new JRadioButton("거래명세서");
		ButtonGroup group = new ButtonGroup(); // 라디오버튼 그룹화를 위한 버튼그룹 설정
		group.add(rdTotalSale);
		group.add(rdTradeDetail);
		panelForTable = new JPanel();
		table = new JTable();
		scrollPane = new JScrollPane();

		pnForResult = new JPanel();
		lblResult = new JLabel("합계 : ");
		txtResult = new JTextField();


		/* 컴포넌트 레이아웃 */
		setLayout(new BorderLayout(0, 0));
		pnForControl.setLayout(new BorderLayout(0, 0));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("굴림", Font.BOLD, 18));
		subPnForControl.setBorder(new TitledBorder(new LineBorder(new Color(64, 64, 64)),
				"\uBCF4\uACE0\uC11C \uC120\uD0DD", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelForTable.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelForTable.setLayout(new BorderLayout(0, 0));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		rdTotalSale.setSelected(true);
		scrollPane.setViewportView(table);
		pnForResult.setBorder(new EmptyBorder(0, 20, 2, 20));
		pnForResult.setLayout(new BorderLayout(0, 0));
		lblResult.setFont(new Font("굴림", Font.PLAIN, 11));
		lblResult.setHorizontalAlignment(SwingConstants.RIGHT);
		txtResult.setFont(new Font("굴림", Font.BOLD, 11));
		txtResult.setHorizontalAlignment(SwingConstants.RIGHT);
		txtResult.setEditable(false);
		txtResult.setColumns(10);


		/* 컴퍼넌트 삽입 */
		add(pnForControl, BorderLayout.NORTH);
		pnForControl.add(title, BorderLayout.NORTH);
		pnForControl.add(subPnForControl, BorderLayout.CENTER);
		subPnForControl.add(rdTotalSale);
		subPnForControl.add(rdTradeDetail);
		add(panelForTable, BorderLayout.CENTER);
		panelForTable.add(scrollPane, BorderLayout.CENTER);
		add(pnForResult, BorderLayout.SOUTH);
		pnForResult.add(lblResult, BorderLayout.CENTER);
		pnForResult.add(txtResult, BorderLayout.EAST);

		/* 라디오버튼 이벤트처리 */
		rdTotalSale.addActionListener(this);
		rdTradeDetail.addActionListener(this);

		/* 테이블 데이터 삽입 */
		showSoftwareSaleList();

	}


	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == rdTradeDetail) {
			showTradeDetailList();
		} else if (e.getSource() == rdTotalSale) {
			showSoftwareSaleList();
		}
	}

	private void showSoftwareSaleList() {
		
		List<Sale> reportlist = SaleService.getInstance().selectAllSortDate();
		
		String[] COL_NAMES = { "주문일자", "분류", "품목명", "주문번호", "주문수량", "판매금액" };
		String[][] data = new String[reportlist.size()][COL_NAMES.length];
		
		int idx = 0;
		int total = 0;
		
		String beforeDate = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyy년MM월dd");
		
		for (Sale c : reportlist) {

			String date = format.format(c.getDate());
			if (beforeDate.equals(date)) {
				data[idx][0] = "-";
			} else {
				data[idx][0] = date;
				beforeDate = date;
			}
			data[idx][1] = c.getCategory();
			data[idx][2] = c.getTitle();
			data[idx][3] = c.getNo() + "";
			data[idx][4] = String.format("%,d", c.getOrderCount());
			data[idx][5] = String.format("%,d", (c.getOrderCount() * c.getSellPrice())) + " 원";
			total += c.getOrderCount() * c.getSellPrice();
			idx++;
		}

		lblResult.setText("판매금액 합계 : ");
		txtResult.setText(String.format("%,d", total) + " 원");

		mft = new ModelForTable(data, COL_NAMES);
		table.setModel(mft);

		mft.tableCellAlignment(table, SwingConstants.CENTER, 0, 1, 2, 3, 4);
		mft.tableCellAlignment(table, SwingConstants.RIGHT, 5);

		handleTableDesign();
	}

	private void showTradeDetailList() {

		List<Sale> reportlist = SaleService.getInstance().selectAllSortSupplier();

		String[] COL_NAMES = { "공급회사명", "주문일자", "고객상호", "품명", "수량", "단가", "금액", "세금", "총납품금액" };
		String[][] data = new String[reportlist.size()][COL_NAMES.length];

		int idx = 0;
		int total = 0;

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String beforeConame = "";

		for (Sale c : reportlist) {
			int orderCnt = c.getOrderCount();
			int supPrice = c.getSupPrice();
			int cost = orderCnt * supPrice;
			int tax = (int) (cost * 0.1);

			String coname = c.getCoName();
			if (beforeConame.equals(coname)) {
				data[idx][0] = "-";
			} else {
				data[idx][0] = coname;
				beforeConame = coname;
			}
			data[idx][1] = format.format(c.getDate());
			data[idx][2] = c.getShopName();
			data[idx][3] = c.getTitle();
			data[idx][4] = String.format("%,d", orderCnt);
			data[idx][5] = String.format("%,d", supPrice) + " 원";
			data[idx][6] = String.format("%,d", (cost)) + " 원";
			data[idx][7] = String.format("%,d", (tax)) + " 원";
			data[idx][8] = String.format("%,d", (cost + tax)) + " 원";
			total += cost + tax;
			idx++;
		}

		lblResult.setText("총 납품금액  합계 : ");
		txtResult.setText(String.format("%,d", total) + " 원");

		ModelForTable mft = new ModelForTable(data, COL_NAMES);
		table.setModel(mft);

		mft.tableCellAlignment(table, SwingConstants.CENTER, 0, 1, 2, 3, 4);
		mft.tableCellAlignment(table, SwingConstants.RIGHT, 5, 6, 7, 8);

		handleTableDesign();
	}

	private void handleTableDesign() {
		mft.resizeColumnWidth(table);
		mft.tableHeaderAlignment(table);
		table.setFont(table.getFont().deriveFont(11.0f));
		table.getTableHeader().setReorderingAllowed(false);
	}
}
