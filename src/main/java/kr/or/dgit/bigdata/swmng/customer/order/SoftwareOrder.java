package kr.or.dgit.bigdata.swmng.customer.order;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.sun.javafx.iio.common.SmoothMinifier;
import com.sun.prism.Image;

import kr.or.dgit.bigdata.swmng.dto.Buyer;
import kr.or.dgit.bigdata.swmng.dto.Sale;
import kr.or.dgit.bigdata.swmng.dto.Software;
import kr.or.dgit.bigdata.swmng.service.BuyerService;
import kr.or.dgit.bigdata.swmng.service.SaleService;
import kr.or.dgit.bigdata.swmng.service.SoftwareService;
import kr.or.dgit.bigdata.swmng.util.DateFomatter;

@SuppressWarnings("serial")
public class SoftwareOrder extends JPanel {
	private JTextField tfNo;
	private JTextField tfcount;
	private JLabel lblcoName;
	private JComboBox cbcoName;
	private JComboBox cbtitle;
	private JCheckBox chchck;
	private List<Buyer> list;
	private List<Software> list2;
	private JDatePickerImpl datePicker1;
	private UtilDateModel model;
	private JLabel lblTitlePic;
	private JLabel TitlePic;
	private Date today;

	public SoftwareOrder() {
		model = new UtilDateModel();
		today = new Date();
		model.setValue(today);
		model.setSelected(true);
		Properties p1 = new Properties();
		p1.put("text.today", "오늘");
		JDatePanelImpl datePanel1 = new JDatePanelImpl(model, p1);
		datePanel1.getComponent(0).setPreferredSize(new Dimension(320, 200));
		setLayout(new BorderLayout(0, 0));

		JPanel mainpanel = new JPanel();
		add(mainpanel, BorderLayout.CENTER);
		GridBagLayout gbl_mainpanel = new GridBagLayout();
		gbl_mainpanel.columnWidths = new int[] { 94, 58, 109, 83, 83, 0 };
		gbl_mainpanel.rowHeights = new int[] { 46, 25, 25, 25, 25, 25, 25, 0, 0, 0, 0 };
		gbl_mainpanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_mainpanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		mainpanel.setLayout(gbl_mainpanel);

		JLabel lblNewLabel = new JLabel("소프트웨어 주문");
		lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 5;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		mainpanel.add(lblNewLabel, gbc_lblNewLabel);

		JLabel lblNo = new JLabel("주문번호");
		lblNo.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblNo = new GridBagConstraints();
		gbc_lblNo.anchor = GridBagConstraints.EAST;
		gbc_lblNo.fill = GridBagConstraints.VERTICAL;
		gbc_lblNo.insets = new Insets(0, 0, 5, 5);
		gbc_lblNo.gridx = 1;
		gbc_lblNo.gridy = 1;
		mainpanel.add(lblNo, gbc_lblNo);

		tfNo = new JTextField();
		tfNo.setEnabled(false);
		tfNo.setText(SaleService.getInstance().selectMaxNo().getNo() + "");
		GridBagConstraints gbc_tfNo = new GridBagConstraints();
		gbc_tfNo.fill = GridBagConstraints.BOTH;
		gbc_tfNo.insets = new Insets(0, 0, 5, 5);
		gbc_tfNo.gridx = 2;
		gbc_tfNo.gridy = 1;
		mainpanel.add(tfNo, gbc_tfNo);
		tfNo.setColumns(10);

		lblcoName = new JLabel("고객상호명");
		lblcoName.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblcoName = new GridBagConstraints();
		gbc_lblcoName.anchor = GridBagConstraints.EAST;
		gbc_lblcoName.insets = new Insets(0, 0, 5, 5);
		gbc_lblcoName.gridx = 1;
		gbc_lblcoName.gridy = 2;
		mainpanel.add(lblcoName, gbc_lblcoName);

		cbcoName = new JComboBox<>();
		GridBagConstraints gbc_cbcoName = new GridBagConstraints();
		gbc_cbcoName.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbcoName.gridwidth = 2;
		gbc_cbcoName.insets = new Insets(0, 0, 5, 5);
		gbc_cbcoName.gridx = 2;
		gbc_cbcoName.gridy = 2;
		mainpanel.add(cbcoName, gbc_cbcoName);

		JLabel lbltitle = new JLabel("품목명");
		lbltitle.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbltitle = new GridBagConstraints();
		gbc_lbltitle.anchor = GridBagConstraints.EAST;
		gbc_lbltitle.insets = new Insets(0, 0, 5, 5);
		gbc_lbltitle.gridx = 1;
		gbc_lbltitle.gridy = 3;
		mainpanel.add(lbltitle, gbc_lbltitle);

		cbtitle = new JComboBox<>();

		cbtitle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTitleImg();
			}
		});
		GridBagConstraints gbc_cbtitle = new GridBagConstraints();
		gbc_cbtitle.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbtitle.gridwidth = 2;
		gbc_cbtitle.insets = new Insets(0, 0, 5, 5);
		gbc_cbtitle.gridx = 2;
		gbc_cbtitle.gridy = 3;
		mainpanel.add(cbtitle, gbc_cbtitle);

		JLabel lblcount = new JLabel("주문수량");
		lblcount.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblcount = new GridBagConstraints();
		gbc_lblcount.anchor = GridBagConstraints.EAST;
		gbc_lblcount.insets = new Insets(0, 0, 5, 5);
		gbc_lblcount.gridx = 1;
		gbc_lblcount.gridy = 4;
		mainpanel.add(lblcount, gbc_lblcount);

		tfcount = new JTextField();
		GridBagConstraints gbc_tfcount = new GridBagConstraints();
		gbc_tfcount.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfcount.insets = new Insets(0, 0, 5, 5);
		gbc_tfcount.gridx = 2;
		gbc_tfcount.gridy = 4;
		mainpanel.add(tfcount, gbc_tfcount);
		tfcount.setColumns(10);

		JLabel lblchck = new JLabel("입금여부");
		lblchck.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblchck = new GridBagConstraints();
		gbc_lblchck.anchor = GridBagConstraints.EAST;
		gbc_lblchck.insets = new Insets(0, 0, 5, 5);
		gbc_lblchck.gridx = 1;
		gbc_lblchck.gridy = 5;
		mainpanel.add(lblchck, gbc_lblchck);

		JPanel paypanel = new JPanel();
		GridBagConstraints gbc_paypanel = new GridBagConstraints();
		gbc_paypanel.anchor = GridBagConstraints.WEST;
		gbc_paypanel.insets = new Insets(0, 0, 5, 5);
		gbc_paypanel.gridx = 2;
		gbc_paypanel.gridy = 5;
		mainpanel.add(paypanel, gbc_paypanel);

		chchck = new JCheckBox();
		chchck.setText("미입금");
		chchck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				payCheck();
			}
		});
		paypanel.setLayout(new GridLayout(0, 2, 0, 0));
		paypanel.add(chchck);
		chchck.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel lbldate = new JLabel("주문날짜");
		lbldate.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lbldate = new GridBagConstraints();
		gbc_lbldate.anchor = GridBagConstraints.EAST;
		gbc_lbldate.fill = GridBagConstraints.VERTICAL;
		gbc_lbldate.insets = new Insets(0, 0, 5, 5);
		gbc_lbldate.gridx = 1;
		gbc_lbldate.gridy = 6;
		mainpanel.add(lbldate, gbc_lbldate);
//--------------------------------------------
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 2;
		gbc_panel.gridy = 6;
		mainpanel.add(panel, gbc_panel);
		datePicker1 = new JDatePickerImpl(datePanel1, new DateFomatter());
		JButton btn = (JButton) (datePicker1.getComponent(1));
		panel.setLayout(new BorderLayout(0, 0));
		panel.add(datePicker1);
		datePicker1.setPreferredSize(new Dimension(130, 27));
//--------------------------------------------
		lblTitlePic = new JLabel("제품사진");
		GridBagConstraints gbc_lblTitlePic = new GridBagConstraints();
		gbc_lblTitlePic.anchor = GridBagConstraints.EAST;
		gbc_lblTitlePic.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitlePic.gridx = 1;
		gbc_lblTitlePic.gridy = 7;
		mainpanel.add(lblTitlePic, gbc_lblTitlePic);

		TitlePic = new JLabel();
		GridBagConstraints gbc_TitlePic = new GridBagConstraints();
		gbc_TitlePic.anchor = GridBagConstraints.WEST;
		gbc_TitlePic.gridwidth = 2;
		gbc_TitlePic.gridheight = 3;
		gbc_TitlePic.insets = new Insets(0, 0, 0, 5);
		gbc_TitlePic.gridx = 2;
		gbc_TitlePic.gridy = 7;
		mainpanel.add(TitlePic, gbc_TitlePic);
		
		JPanel btnpanel = new JPanel();
		add(btnpanel, BorderLayout.SOUTH);
		GridBagLayout gbl_btnpanel = new GridBagLayout();
		gbl_btnpanel.columnWidths = new int[] { 150, 150, 0 };
		gbl_btnpanel.rowHeights = new int[] { 23, 0 };
		gbl_btnpanel.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_btnpanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		btnpanel.setLayout(gbl_btnpanel);

		JButton btnreset = new JButton("취소");
		btnreset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refresh();
			}
		});

		JButton btnadd = new JButton("등록");
		btnadd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addCheck();
			}
		});
		GridBagConstraints gbc_btnadd = new GridBagConstraints();
		gbc_btnadd.anchor = GridBagConstraints.EAST;
		gbc_btnadd.fill = GridBagConstraints.VERTICAL;
		gbc_btnadd.insets = new Insets(0, 0, 0, 5);
		gbc_btnadd.gridx = 0;
		gbc_btnadd.gridy = 0;
		btnpanel.add(btnadd, gbc_btnadd);
		GridBagConstraints gbc_btnreset = new GridBagConstraints();
		gbc_btnreset.anchor = GridBagConstraints.WEST;
		gbc_btnreset.fill = GridBagConstraints.VERTICAL;
		gbc_btnreset.gridx = 1;
		gbc_btnreset.gridy = 0;
		btnpanel.add(btnreset, gbc_btnreset);
		setComList();
	}
	//콤보박스 리스트
	private void setComList(){
		list = BuyerService.getInstance().selectShopName();
		for (Buyer b : list) {
			cbcoName.addItem(b.getShopName());
		}
		list2 = SoftwareService.getInstance().selectAll();
		for (Software s : list2) {
			cbtitle.addItem(s.getTitle());
		}
	}
	private void setTitleImg(){
		String title = cbtitle.getSelectedItem() + "";
		Software s = SoftwareService.getInstance().selectSWBytitle(title);
	
		try {
			TitlePic.setIcon(new ImageIcon(new ImageIcon(s.getPicPath()).getImage().getScaledInstance(160, 140,
					java.awt.Image.SCALE_SMOOTH)));
		} catch (NullPointerException e) {
			TitlePic.setIcon(null);
		}
	}
	//입금여부확인
	private void payCheck(){
		if (chchck.isSelected()) {
			chchck.setText("입금   ");
		} else {
			chchck.setText("미입금");
		}
	}
	//등록확인
	private void addCheck(){
			if (tfcount.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "주문수량을 입력해주세요");
			} else {
			JOptionPane.showMessageDialog(null, "등록완료");
			add();
			refresh();
		}
	}
	void add() {
		String[] strDate = datePicker1.getJFormattedTextField().getText().split("/");
		int[] numDateArr = new int[3];
		for (int i = 0; i < strDate.length; i++) {
			numDateArr[i] = Integer.parseInt(strDate[i]);
		}
		GregorianCalendar time = new GregorianCalendar(numDateArr[0], numDateArr[1] - 1, numDateArr[2]);

		int no = Integer.parseInt(tfNo.getText());
		String shopName = cbcoName.getSelectedItem() + "";
		String title = cbtitle.getSelectedItem() + "";
		int orderCount = Integer.parseInt(tfcount.getText());
		boolean payment;
		if (chchck.isSelected()) {
			payment = true;
		} else {
			payment = false;
		}
		Date date = time.getTime();
		Software sw = SoftwareService.getInstance().selectSWBytitle(title);

		Sale s = new Sale(no, shopName, title, orderCount, payment, date);
		SaleService.getInstance().insertItem(s);
	}
	private void refresh(){
		cbcoName.setSelectedIndex(0);
		cbtitle.setSelectedIndex(0);
		tfcount.setText("");
		chchck.setSelected(false);
		model.setValue(today);
	}
}
