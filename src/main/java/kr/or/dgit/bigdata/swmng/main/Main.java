package kr.or.dgit.bigdata.swmng.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.jdesktop.swingx.JXCollapsiblePane;

import kr.or.dgit.bigdata.swmng.customer.list.BuyerList;
import kr.or.dgit.bigdata.swmng.customer.list.CompanyList;
import kr.or.dgit.bigdata.swmng.customer.list.SoftwareList;
import kr.or.dgit.bigdata.swmng.customer.member.LoggedIn;
import kr.or.dgit.bigdata.swmng.customer.member.LoginForm;
import kr.or.dgit.bigdata.swmng.customer.order.SoftwareOrder;
import kr.or.dgit.bigdata.swmng.customer.report.OrderStateGraph;
import kr.or.dgit.bigdata.swmng.customer.report.SaleStateReport;
import kr.or.dgit.bigdata.swmng.customer.salestatus.SaleStateByBuyer;
import kr.or.dgit.bigdata.swmng.customer.salestatus.SaleStateByDate;
import kr.or.dgit.bigdata.swmng.customer.salestatus.SaleStateBySoftware;

public class Main extends JFrame implements ActionListener, MouseListener {

	private JPanel contentPane;
	CompanyList cl;
	private JPanel mainPanel;
	private JLayeredPane menuPanel;
	private BufferedImage img = null;
	private JXCollapsiblePane cp1;
	private JPanel menu1Panel;
	private String[] imgSrc = { "/img/list1.png", "/img/list2.png", "/img/list3.png", "/img/sale1.png",
			"/img/sale2.png", "/img/sale3.png", "/img/report1.png", };
	private JButton[] subMenuBtns = new JButton[imgSrc.length + 3];
	private JPanel sidePanel;
	private JPanel showIdPanel;
	private JButton tfId;
	private JButton btnLogout;

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setIconImage(
							new ImageIcon(getClass().getClassLoader().getResource("img/icon.png")).getImage());
					UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel");
					SwingUtilities.updateComponentTreeUI(frame);
					frame.setVisible(true);
					frame.setTitle("소프트웨어 매니지먼트");
					new OrderStateGraph();
				} catch (Exception e) {

					e.printStackTrace();

				}
			}
		});

	}

	public Main() {

		try {
			img = ImageIO.read(getClass().getResource("/img/menu.png"));
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		ImgPanel imgPanel = new ImgPanel();
		mainPanel = new JPanel();
		contentPane = new JPanel();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPane.setBorder(null);
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		mainPanel.setLayout(new BorderLayout(0, 0));
		mainPanel.add(new LoginForm());

		getContentPane().setLayout(new BorderLayout());

		sidePanel = new JPanel();

		contentPane.add(sidePanel, BorderLayout.WEST);
		sidePanel.setLayout(new BorderLayout(0, 0));
		menuPanel = new JLayeredPane();
		sidePanel.add(menuPanel);
		menu1Panel = new JPanel(new GridLayout(0, 1));
		cp1 = new JXCollapsiblePane(JXCollapsiblePane.Direction.DOWN);
		menu1Panel.setBackground(new Color(27, 27, 27));

		cp1.setLayout(new BorderLayout());
		cp1.add(menu1Panel, BorderLayout.CENTER);
		cp1.setCollapsed(true);
		cp1.setBackground(new Color(255, 255, 255, 0));

		menuPanel.setPreferredSize(new Dimension(120, 280));
		menuPanel.setLayout(null);
		menuPanel.add(cp1);
		cp1.setBounds(0, 90, 120, 0);
		mainPanel.addMouseListener(this);
		getContentPane().add(mainPanel, BorderLayout.CENTER);

		// 사이즈 조절
		imgPanel.setBounds(0, 0, 120, 800);
		setBounds(650, 350, 405, 394);
		setResizable(false);

		createNaviMenu();
		createLoginInfo();

		menuPanel.add(imgPanel);
	}

	private void createLoginInfo() {
		showIdPanel = new JPanel();
		showIdPanel.setLayout(null);
		showIdPanel.setBackground(new Color(27, 27, 27));
		showIdPanel.setPreferredSize(new Dimension(120, 30));
		tfId = new JButton();
		tfId.setBounds(15, 5, 90, 21);
		tfId.setHorizontalAlignment(SwingConstants.CENTER);
		tfId.setForeground(new Color(255, 255, 255));
		tfId.setFont(new Font("맑은 고딕", Font.BOLD, 11));
		tfId.setBounds(0, 0, 60, 25);
		tfId.setCursor(new Cursor(Cursor.HAND_CURSOR));
		tfId.setBorder(BorderFactory.createEmptyBorder());
		tfId.setContentAreaFilled(false);
		tfId.addActionListener(this);

		btnLogout = new JButton("로그아웃");
		btnLogout.addActionListener(this);
		btnLogout.setForeground(Color.WHITE);
		btnLogout.setFont(new Font("맑은 고딕", Font.PLAIN, 10));
		btnLogout.setBounds(55, 2, 60, 25);
		btnLogout.setBorder(BorderFactory.createEmptyBorder());
		btnLogout.setContentAreaFilled(false);
		btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));

		showIdPanel.add(tfId);
		showIdPanel.add(btnLogout);
		sidePanel.add(showIdPanel, BorderLayout.SOUTH);
		sidePanel.setVisible(false);

	}

	public JButton getTxtId() {
		return tfId;
	}

	public JPanel getSidePanel() {
		return sidePanel;
	}

	private void createNaviMenu() {
		for (int i = 0; i < subMenuBtns.length; i++) {
			if (i != 9) {
				subMenuBtns[i] = new JButton();
				subMenuBtns[i].addActionListener(this);
				if (i < 7) {
					subMenuBtns[i].setIcon(new ImageIcon(getClass().getResource(imgSrc[i])));
					menu1Panel.add(subMenuBtns[i]);
				}
			} else if (i == 9) {
				subMenuBtns[i] = new JButton(cp1.getActionMap().get(JXCollapsiblePane.TOGGLE_ACTION));
				subMenuBtns[i].setText("");

			}
			if (i > 6 && i < 10) {
				subMenuBtns[i].addMouseListener(this);
				menuPanel.add(subMenuBtns[i]);
			}
			subMenuBtns[i].setContentAreaFilled(false);
			subMenuBtns[i].setBorder(BorderFactory.createEmptyBorder());
			subMenuBtns[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		subMenuBtns[7].setBounds(0, 0, 120, 30);
		subMenuBtns[8].setBounds(0, 30, 120, 30);
		subMenuBtns[9].setBounds(0, 59, 120, 30);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// 각 메뉴 클릭시 메인패널내용 삭제후 해당메뉴 패널 추가
		mainPanel.removeAll();
		if (e.getSource() == subMenuBtns[0]) {
			mainPanel.add(new CompanyList(), BorderLayout.CENTER);
			setSize(700, 394);
		} else if (e.getSource() == subMenuBtns[1]) {
			mainPanel.add(new SoftwareList(), BorderLayout.CENTER);
			setSize(750, 394);
		} else if (e.getSource() == subMenuBtns[2]) {
			mainPanel.add(new BuyerList(), BorderLayout.CENTER);
			setSize(700, 394);
		} else if (e.getSource() == subMenuBtns[3]) {
			mainPanel.add(new SaleStateByBuyer(), BorderLayout.CENTER);
			setSize(740, 394);
		} else if (e.getSource() == subMenuBtns[4]) {
			mainPanel.add(new SaleStateBySoftware(), BorderLayout.CENTER);
			setSize(800, 394);
		} else if (e.getSource() == subMenuBtns[5]) {
			mainPanel.add(new SaleStateByDate(), BorderLayout.CENTER);
			setSize(680, 394);
		} else if (e.getSource() == subMenuBtns[6]) {
			mainPanel.add(new SaleStateReport(), BorderLayout.CENTER);
			setSize(950, 394);
		} else if (e.getSource() == subMenuBtns[7]) {
			mainPanel.add(new OrderStateGraph(), BorderLayout.CENTER);
			setBounds(getX(), 150, 700, 845);
		} else if (e.getSource() == subMenuBtns[8]) {
			mainPanel.add(new SoftwareOrder(), BorderLayout.CENTER);
			setSize(500, 394);
		} else if (e.getSource() == btnLogout) {
			LoggedIn.getAdminThread().interrupt();
			executeLogOut();
			sidePanel.setVisible(false);
			mainPanel.add(new LoginForm(), BorderLayout.CENTER);
			setSize(405, 394);
		} else if (e.getSource() == tfId) {
			mainPanel.add(new LoggedIn(), BorderLayout.CENTER);
			setSize(500, 394);
		}

		revalidate();
		repaint();
	}

	public void executeLogOut() {
		tfId.setText("");

	}

	private class ImgPanel extends JPanel {
		public void paint(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource() == subMenuBtns[7]) {
			subMenuBtns[7].setIcon(new ImageIcon(getClass().getResource("/img/graphSelected.png")));

		} else if (e.getSource() == subMenuBtns[8]) {
			subMenuBtns[8].setIcon(new ImageIcon(getClass().getResource("/img/orderSelected.png")));

		} else if (e.getSource() == subMenuBtns[9]) {
			if (cp1.getSize().height == 0) {
				subMenuBtns[9].doClick();
			}
			subMenuBtns[9].setIcon(new ImageIcon(getClass().getResource("/img/menuSelected.png")));
		} else if (e.getSource() == mainPanel) {
			if (cp1.getSize().height > 0) {
				subMenuBtns[9].doClick();
			}
		}

	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource() == subMenuBtns[7]) {
			subMenuBtns[7].setIcon(null);
		} else if (e.getSource() == subMenuBtns[8]) {
			subMenuBtns[8].setIcon(null);
		} else if (e.getSource() == subMenuBtns[9]) {
			subMenuBtns[9].setIcon(null);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

}
