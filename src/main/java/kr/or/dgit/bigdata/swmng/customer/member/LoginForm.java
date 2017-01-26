package kr.or.dgit.bigdata.swmng.customer.member;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.jdesktop.swingx.prompt.PromptSupport;

import kr.or.dgit.bigdata.swmng.dto.Member;
import kr.or.dgit.bigdata.swmng.main.Main;
import kr.or.dgit.bigdata.swmng.service.MemberService;

public class LoginForm extends JLayeredPane implements ActionListener {
	private JTextField tfId;
	private JPasswordField tfPw;
	private JButton btnLogin;
	private JButton btnJoin;
	static String memberId;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	private boolean idValidation = false;
	private boolean pwValidation = false;
	private BufferedImage img = null;

	/**
	 * Create the panel.
	 */
	public LoginForm() {
		setLayout(null);

		tfId = new JTextField(15);
		tfId.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		tfId.setForeground(Color.WHITE);
		add(tfId);
		tfId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfPw.requestFocus();
				}
			}
		});
		tfPw = new JPasswordField(15);
		tfPw.setForeground(Color.WHITE);
		tfPw.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		add(tfPw);

		tfPw.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					loginAction();
					loggedInAction();
				}
			}

		});

		try {
			// 로그인 화면 이미지
			img = ImageIO.read(getClass().getResource("/img/login.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ImgPanel imgPanel = new ImgPanel();
		imgPanel.setBounds(0, 0, 425, 425);

		btnLogin = new JButton();
		add(btnLogin);
		btnJoin = new JButton("회원가입");
		add(btnJoin);

		btnLogin.addActionListener(this);

		JLabel secret = new JLabel();
		add(secret);

		secret.setBounds(265, 320, 80, 20);
		secret.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		secret.addMouseListener(new MouseAdapter() {
			// 시크릿 로그인. 개발시 필요해서..
			@Override
			public void mouseClicked(MouseEvent e) {
				Main mainFrame = (Main) getTopLevelAncestor();
				mainFrame.getSidePanel().setVisible(true);
				mainFrame.getTxtId().setText("admin");
				setMemberId("admin");
				removeAll();
				setLayout(new BorderLayout());
				add(new LoginInfo(), BorderLayout.CENTER);
				getTopLevelAncestor().setSize(500, 394);
				revalidate();
				repaint();
			}

		});

		// 텍스트필드, 버튼 설정
		tfId.setOpaque(false);
		tfId.setBorder(BorderFactory.createEmptyBorder());
		tfId.setBackground(new Color(255, 255, 255, 0));
		tfPw.setOpaque(false);
		tfPw.setBorder(BorderFactory.createEmptyBorder());
		tfPw.setBackground(new Color(255, 255, 255, 0));
		btnLogin.setBorderPainted(false);
		btnLogin.setFocusPainted(false);
		btnLogin.setContentAreaFilled(false);
		btnJoin.setBorderPainted(false);
		btnJoin.setFocusPainted(false);
		btnJoin.setContentAreaFilled(false);

		tfId.setBounds(140, 155, 170, 30);
		tfPw.setBounds(140, 205, 170, 30);
		btnLogin.setBounds(75, 250, 250, 43);
		btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnJoin.setBorder(BorderFactory.createEmptyBorder());
		btnJoin.setForeground(Color.WHITE);
		btnJoin.setBounds(265, 297, 55, 15);
		btnJoin.setFont(new Font("맑은 고딕", Font.ITALIC, 10));
		btnJoin.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnJoin.addActionListener(this);
		add(imgPanel);
		PromptSupport.setPrompt("아이디를 입력해주세요", tfId);
		PromptSupport.setPrompt("비밀번호를 입력해주세요", tfPw);
		PromptSupport.setForeground(Color.darkGray, tfId);
		PromptSupport.setForeground(Color.darkGray, tfPw);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnLogin) {
			// 아이디 비번 빈칸여부 확인후 로그인
			if (tfId.getText().trim().equals("") || tfPw.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "빈칸없이 입력해 주세요");
			} else {
				loginAction();
				loggedInAction();
			}
		} else if (e.getSource() == btnJoin) {
			removeAll();
			setLayout(new BorderLayout());
			add(new JoinForm(), BorderLayout.CENTER);
			getTopLevelAncestor().setSize(345, 525);
			revalidate();
			repaint();
		}
	}

	private void loggedInAction() {
		if (idValidation == true && pwValidation == true) {
			setMemberId(tfId.getText());
			removeAll();
			setLayout(new BorderLayout());
			add(new LoginInfo(), BorderLayout.CENTER);
			getTopLevelAncestor().setSize(500, 394);
			revalidate();
			repaint();
			JOptionPane.showMessageDialog(null, tfId.getText() + "님 반갑습니다");

		} else if (idValidation == false) {
			JOptionPane.showMessageDialog(null, "존재하지 않는 아이디 입니다");
			tfId.requestFocus();
			tfId.selectAll();
		} else if (idValidation == true && pwValidation == false) {
			JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다");
			tfPw.requestFocus();
			tfPw.selectAll();
		}
	}

	private void loginAction() {
		List<Member> list = MemberService.getInstance().selecyByID(tfId.getText().trim());
		// 로그인클릭시 입력한 아이디를 가져와 DB에서 비교
		// 아이디와 비밀번호 일치여부 확인후 로그인 진행
		for (Member m : list) {
			if (m.getId().equals(tfId.getText().trim())) {
				idValidation = true;
			}
			if (m.getPassword().equals(tfPw.getText())) {
				pwValidation = true;
			}
		}
		// 로그인후 네비메뉴 생성
		if (idValidation && pwValidation) {
			Main mainFrame = (Main) getTopLevelAncestor();
			mainFrame.getSidePanel().setVisible(true);
			mainFrame.getTxtId().setText(tfId.getText());
		}

	}

	private class ImgPanel extends JPanel {
		public void paint(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}
	}

}
