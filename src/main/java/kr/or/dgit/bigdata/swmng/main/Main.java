package kr.or.dgit.bigdata.swmng.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
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
import kr.or.dgit.bigdata.swmng.customer.member.LoginInfo;
import kr.or.dgit.bigdata.swmng.customer.member.LoginForm;
import kr.or.dgit.bigdata.swmng.customer.order.SoftwareOrder;
import kr.or.dgit.bigdata.swmng.customer.report.OrderStateGraph;
import kr.or.dgit.bigdata.swmng.customer.report.SaleStateReport;
import kr.or.dgit.bigdata.swmng.customer.salestatus.SaleStateByBuyer;
import kr.or.dgit.bigdata.swmng.customer.salestatus.SaleStateByDate;
import kr.or.dgit.bigdata.swmng.customer.salestatus.SaleStateBySoftware;

public class Main extends JFrame implements ActionListener, MouseListener {

	private JPanel contentPane;
	private CompanyList cl;
	private JPanel mainPanel;
	private JLayeredPane menuPanel;
	private BufferedImage img = null;
	private JXCollapsiblePane cp1;
	private JPanel menu1Panel;
	private String[] imgSrc = { "/img/list1.png", "/img/list2.png", "/img/list3.png", "/img/sale1.png",
			"/img/sale2.png", "/img/sale3.png", "/img/report1.png" };
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
					// 프레임 아이콘 변경
					frame.setIconImage(
							new ImageIcon(getClass().getClassLoader().getResource("img/icon.png")).getImage());
					// 룩앤필 테마 적용
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
			// 네비메뉴 배경
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

		// 네비메뉴 배경 위치 및 사이즈
		imgPanel.setBounds(0, 0, 120, 800);
		setBounds(650, 350, 405, 394);
		setResizable(false);

		// 네비메뉴 생성
		createNaviMenu();

		// 네비메뉴아래 로그인 정보 생성
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

	private void createNaviMenu() {
		// 코드가 길어져 네비메뉴 생성 및 설정을 반복문으로 대체
		// 배열을 만들어 메뉴 생성 및 이미지아이콘 생성, 총메뉴 10개
		for (int i = 0; i < subMenuBtns.length; i++) {
			if (i != 9) {
				subMenuBtns[i] = new JButton();
				subMenuBtns[i].addActionListener(this);
				if (i < 7) {
					// 슬라이드 다운 메뉴 따로 이미지 아이콘 설정
					subMenuBtns[i].setIcon(new ImageIcon(getClass().getResource(imgSrc[i])));
					menu1Panel.add(subMenuBtns[i]);
				}
			} else if (i == 9) {
				// 메인 네비 메뉴 3번째 슬라이드 가능하게 토글 추가
				subMenuBtns[i] = new JButton(cp1.getActionMap().get(JXCollapsiblePane.TOGGLE_ACTION));
				subMenuBtns[i].setText("");
			}
			if (i > 6 && i < 10) {
				// 메인 네비 메뉴 3개의 마우스 오버 효과를 위한 마우스 리스너 등록
				subMenuBtns[i].addMouseListener(this);
				menuPanel.add(subMenuBtns[i]);
			}

			// 전체적인 공통 설정, 투명 및 보더 삭제 그리고 커서 설정
			subMenuBtns[i].setContentAreaFilled(false);
			subMenuBtns[i].setBorder(BorderFactory.createEmptyBorder());
			subMenuBtns[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		// 메인 네비 메뉴 3개의 위치 및 크기 설정
		subMenuBtns[7].setBounds(0, 0, 120, 30);
		subMenuBtns[8].setBounds(0, 30, 120, 30);
		subMenuBtns[9].setBounds(0, 59, 120, 30);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// 각 메뉴 클릭시 메인패널내용 삭제
		mainPanel.removeAll();

		if (e.getSource() == subMenuBtns[0]) {
			// 공급회사 목록
			mainPanel.add(new CompanyList(), BorderLayout.CENTER);
			setSize(700, 394);
		} else if (e.getSource() == subMenuBtns[1]) {
			// 소프트웨어 목록
			mainPanel.add(new SoftwareList(), BorderLayout.CENTER);
			setSize(750, 394);
		} else if (e.getSource() == subMenuBtns[2]) {
			// 고객 목록
			mainPanel.add(new BuyerList(), BorderLayout.CENTER);
			setSize(700, 394);
		} else if (e.getSource() == subMenuBtns[3]) {
			// 고객별 판매현황
			mainPanel.add(new SaleStateByBuyer(), BorderLayout.CENTER);
			setSize(880, 394);
		} else if (e.getSource() == subMenuBtns[4]) {
			// 소프트웨어별 판매현황
			mainPanel.add(new SaleStateBySoftware(), BorderLayout.CENTER);
			setSize(800, 394);
		} else if (e.getSource() == subMenuBtns[5]) {
			// 날짜별 판매현황
			mainPanel.add(new SaleStateByDate(), BorderLayout.CENTER);
			setSize(680, 394);
		} else if (e.getSource() == subMenuBtns[6]) {
			// 판매 보고서
			mainPanel.add(new SaleStateReport(), BorderLayout.CENTER);
			setSize(860, 394);
		} else if (e.getSource() == subMenuBtns[7]) {
			// 판매현황 그래프
			mainPanel.add(new OrderStateGraph(), BorderLayout.CENTER);
			// 모니터화면의 해상도 얻기
			Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
			setBounds(screen.width / 5, getY(), 1250, 500);

		} else if (e.getSource() == subMenuBtns[8]) {
			// 소프트웨어 주문
			mainPanel.add(new SoftwareOrder(), BorderLayout.CENTER);
			setSize(500, 394);
		} else if (e.getSource() == btnLogout) {
			// 관리자 로그아웃시 자동백업 중지
			if (LoginInfo.getAdminThread() != null) {
				LoginInfo.getAdminThread().interrupt();
			}
			tfId.setText("");
			sidePanel.setVisible(false);
			mainPanel.add(new LoginForm(), BorderLayout.CENTER);
			setSize(405, 394);
		} else if (e.getSource() == tfId) {
			// 로그인시 로그인정보 화면 호출
			mainPanel.add(new LoginInfo(), BorderLayout.CENTER);
			setSize(500, 394);
		}

		revalidate();
		repaint();
	}

	// 네비 메뉴 배경을 위한 메소드
	private class ImgPanel extends JPanel {
		public void paint(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}

	}
	@Override
	public void mouseEntered(MouseEvent e) {

		if (e.getSource() == subMenuBtns[7]) {
			// 그래프 마우스 오버 아이콘
			subMenuBtns[7].setIcon(new ImageIcon(getClass().getResource("/img/graphSelected.png")));
		} else if (e.getSource() == subMenuBtns[8]) {
			// 소프트웨어 주문 마우스 오버 아이콘
			subMenuBtns[8].setIcon(new ImageIcon(getClass().getResource("/img/orderSelected.png")));
		} else if (e.getSource() == subMenuBtns[9]) {
			// 메뉴목록 마우스 오버시 슬라이드 다운메뉴 표시 및 아이콘
			if (cp1.getSize().height == 0) {
				subMenuBtns[9].doClick();
			}
			subMenuBtns[9].setIcon(new ImageIcon(getClass().getResource("/img/menuSelected.png")));
		} else if (e.getSource() == mainPanel) {
			// 메인패널에 마우스 오버시 슬라이드 높이가0이상이면 슬라이드 접기
			if (cp1.getSize().height > 0) {
				subMenuBtns[9].doClick();
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// 마우스 아웃시 아이콘 기본상태로
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
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	public JButton getTxtId() {
		return tfId;
	}

	public JPanel getSidePanel() {
		return sidePanel;
	}

}
