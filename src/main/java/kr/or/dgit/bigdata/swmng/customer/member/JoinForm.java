package kr.or.dgit.bigdata.swmng.customer.member;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jdesktop.swingx.JXComboBox;
import org.jdesktop.swingx.prompt.PromptSupport;

import kr.or.dgit.bigdata.swmng.dto.Member;
import kr.or.dgit.bigdata.swmng.main.Main;
import kr.or.dgit.bigdata.swmng.service.MemberService;

public class JoinForm extends JLayeredPane implements ActionListener, FocusListener {
	private JTextField tfId;
	private JPasswordField tfPw;
	private JButton btnJoin;
	private JButton btnCancel;
	private JPasswordField tfPwCk;
	private JLabel ckPw;
	private JLabel ckPwMsg;
	private JLabel ckId;
	private JLabel ckIdMsg;
	private JTextField tfEmail;
	private BufferedImage img = null;
	private JButton selectPic;
	private JFileChooser jfc;
	private JXComboBox cmbEmail;
	private boolean pwConfirmationCheck = false;
	private boolean idDuplicationCheck = true;
	private String[] email = { "nate.com", "naver.com", "daum.net", "gmail.com", "yahoo.com" };

	/**
	 * Create the panel.
	 */
	public JoinForm() {
		setLayout(null);
		try {
			img = ImageIO.read(getClass().getResource("/img/join.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ImgPanel imgPanel = new ImgPanel();
		imgPanel.setBounds(0, 0, 339, 496);

		tfId = new JTextField();
		tfId.addFocusListener(this);

		tfPw = new JPasswordField();
		tfPw.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				checkPwConfirmation();
			}
		});
		ckId = new JLabel();
		ckIdMsg = new JLabel();
		ckPw = new JLabel();
		ckPwMsg = new JLabel();
		ckPwMsg.setHorizontalAlignment(SwingConstants.RIGHT);
		ckIdMsg.setHorizontalAlignment(SwingConstants.RIGHT);

		ckPwMsg.setFont(new Font("맑은 고딕", Font.BOLD, 9));
		ckIdMsg.setFont(new Font("맑은 고딕", Font.BOLD, 9));

		tfPwCk = new JPasswordField();
		tfPwCk.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				checkPwConfirmation();
			}

		});

		tfEmail = new JTextField();

		cmbEmail = new JXComboBox();
		for (int i = 0; i < email.length; i++) {
			cmbEmail.addItem(email[i]);
		}

		selectPic = new JButton();
		selectPic.addActionListener(this);

		btnCancel = new JButton();
		btnCancel.addActionListener(this);

		btnJoin = new JButton();
		btnJoin.addActionListener(this);

		jfc = new JFileChooser();
		jfc.setFileFilter(new FileNameExtensionFilter("JPG & GIF & PNG Images", "jpg", "gif", "png"));
		jfc.setMultiSelectionEnabled(false);
		jfc.setDialogTitle("사진을 선택하세요");

		// 텍스트필드 버튼 설정
		add(tfId);
		add(tfPw);
		add(tfPwCk);
		add(tfEmail);

		add(selectPic);
		add(btnJoin);
		add(btnCancel);
		add(ckPw);
		add(ckPwMsg);
		add(ckId);
		add(ckIdMsg);
		add(cmbEmail);

		tfId.setOpaque(false);
		tfId.setBorder(BorderFactory.createEmptyBorder());

		tfId.setBackground(new Color(255, 255, 255, 0));
		tfId.setFont(new Font("맑은 고딕", Font.BOLD, 12));

		tfPw.setOpaque(false);
		tfPw.setBorder(BorderFactory.createEmptyBorder());

		tfPw.setBackground(new Color(255, 255, 255, 0));
		tfPw.setFont(new Font("맑은 고딕", Font.BOLD, 12));

		tfPwCk.setOpaque(false);
		tfPwCk.setBorder(BorderFactory.createEmptyBorder());

		tfPwCk.setBackground(new Color(255, 255, 255, 0));
		tfPwCk.setFont(new Font("맑은 고딕", Font.BOLD, 12));

		tfEmail.setOpaque(false);
		tfEmail.setBorder(BorderFactory.createEmptyBorder());

		tfEmail.setBackground(new Color(255, 255, 255, 0));
		tfEmail.setFont(new Font("맑은 고딕", Font.BOLD, 12));

		selectPic.setIcon(new ImageIcon(getClass().getResource("/img/add.png")));
		selectPic.setContentAreaFilled(false);

		btnJoin.setIcon(new ImageIcon(getClass().getResource("/img/btnAdd.png")));
		btnJoin.setContentAreaFilled(false);

		btnCancel.setIcon(new ImageIcon(getClass().getResource("/img/btnCancel.png")));
		btnCancel.setContentAreaFilled(false);

		selectPic.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnJoin.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));

		add(imgPanel);

		PromptSupport.setPrompt("8자 이내 영어/숫자", tfId);
		PromptSupport.setPrompt("비밀번호", tfPw);
		PromptSupport.setPrompt("비밀번호 확인", tfPwCk);
		PromptSupport.setPrompt("이메일 입력", tfEmail);
		PromptSupport.setForeground(Color.gray, tfEmail);
		PromptSupport.setForeground(Color.gray, tfPwCk);
		PromptSupport.setForeground(Color.gray, tfPw);
		PromptSupport.setForeground(Color.gray, tfId);
		PromptSupport.setFontStyle(Font.ITALIC, tfId);
		PromptSupport.setFontStyle(Font.ITALIC, tfPw);
		PromptSupport.setFontStyle(Font.ITALIC, tfPwCk);
		PromptSupport.setFontStyle(Font.ITALIC, tfEmail);

		tfId.setBounds(60, 173, 180, 30);
		tfPw.setBounds(60, 244, 180, 30);
		tfPwCk.setBounds(60, 317, 180, 30);
		tfEmail.setBounds(60, 390, 80, 30);
		cmbEmail.setBounds(167, 393, 85, 25);

		selectPic.setBounds(120, 30, 92, 92);
		btnJoin.setBounds(70, 445, 87, 32);
		btnCancel.setBounds(180, 445, 87, 32);

		ckIdMsg.setBounds(100, 148, 170, 16);
		ckId.setBounds(275, 148, 15, 16);

		ckPwMsg.setBounds(100, 218, 170, 16);
		ckPw.setBounds(275, 218, 15, 16);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == selectPic) {
			selectImage();
		} else if (e.getSource() == btnCancel) {
			removeAll();
			setLayout(new BorderLayout());
			add(new Main().getContentPane().getComponent(1), BorderLayout.CENTER);
			getTopLevelAncestor().setSize(405, 375);
			revalidate();
			repaint();
		} else if (e.getSource() == btnJoin) {
			if (pwConfirmationCheck == true) {
				joinMemberAction();
			} else {
				JOptionPane.showMessageDialog(null, "비밀번호를 확인해 주세요");
			}
		}
	}

	private void selectImage() {
		if (jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			String[] path = jfc.getSelectedFile().getPath().split("\\.");
			if (path[path.length - 1].equals("jpg") || path[path.length - 1].equals("gif")
					|| path[path.length - 1].equals("png")) {
				selectPic.setIcon(new ImageIcon(new ImageIcon(jfc.getSelectedFile().getPath()).getImage()
						.getScaledInstance(selectPic.getWidth(), selectPic.getHeight(), java.awt.Image.SCALE_SMOOTH)));
			} else {
				jfc.setSelectedFile(null);
				JOptionPane.showMessageDialog(null, "지원하지 않는 형식의 파일 입니다");
			}
		}
	}

	private void joinMemberAction() {
		if (idDuplicationCheck == true && pwConfirmationCheck == true && tfId.getText().trim() != null
				&& tfId.getText().trim().matches("^[A-Za-z]{1}[A-Za-z0-9]{3,7}$")) {
			try {
				if (jfc.getSelectedFile() == null) {
					MemberService.getInstance().insertMember(new Member(tfId.getText().trim(), tfPw.getText(),
							tfEmail.getText().trim() + "@" + cmbEmail.getSelectedItem(), null));
				} else {
					MemberService.getInstance()
							.insertMember(new Member(tfId.getText().trim(), tfPw.getText(),
									tfEmail.getText().trim() + "@" + cmbEmail.getSelectedItem(),
									Files.readAllBytes(new File(jfc.getSelectedFile().toString()).toPath())));
				}
				new LoginForm().setMemberId(tfId.getText());
				Main mainFrame = (Main) getTopLevelAncestor();
				mainFrame.getTxtId().setText(tfId.getText());
				mainFrame.getSidePanel().setVisible(true);
				removeAll();
				setLayout(new BorderLayout());
				add(new LoggedIn(), BorderLayout.CENTER);
				getTopLevelAncestor().setSize(500, 394);
				revalidate();
				repaint();
				JOptionPane.showMessageDialog(null, "가입이 완료되었습니다.");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(null, "입력한 양식이 올바르지 않습니다");
		}
	}

	private void checkPwConfirmation() {
		if (tfPw.getText().equals(tfPwCk.getText()) && !tfPw.getText().equals("")) {
			ckPw.setIcon(new ImageIcon(getClass().getResource("/img/success.png")));
			ckPwMsg.setForeground(Color.GREEN);
			ckPwMsg.setText("비밀번호가 일치합니다");
			pwConfirmationCheck = true;
		} else {
			ckPw.setIcon(new ImageIcon(getClass().getResource("/img/fail.png")));
			ckPwMsg.setForeground(Color.RED);
			ckPwMsg.setText("비밀번호가 일치하지 않습니다");
			pwConfirmationCheck = false;
		}
	}

	private class ImgPanel extends JPanel {
		public void paint(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		if (e.getSource() == tfId) {
			ckId.setIcon(null);
			ckIdMsg.setText("");
			ckIdMsg.setForeground(Color.RED);
			idDuplicationCheck = true;
		}

	}

	@Override
	public void focusLost(FocusEvent e) {
		if (e.getSource() == tfId) {
			List<Member> list = MemberService.getInstance().selectAllId();
			for (Member m : list) {
				if (tfId.getText().trim().equals(m.getId())) {
					idDuplicationCheck = false;
				}
			}
			if (tfId.getText().trim().equals("")) {
				ckId.setIcon(new ImageIcon(getClass().getResource("/img/fail.png")));
				ckIdMsg.setText("아이디를 입력해주세요");
				ckIdMsg.setForeground(Color.RED);
			} else if (!tfId.getText().trim().matches("^[A-Za-z]{1}[A-Za-z0-9]{3,7}$")) {
				ckId.setIcon(new ImageIcon(getClass().getResource("/img/fail.png")));
				ckIdMsg.setText("아이디는 4-8자 영문/숫자만 가능합니다");
				ckIdMsg.setForeground(Color.RED);
			} else {
				if (idDuplicationCheck == false) {
					ckId.setIcon(new ImageIcon(getClass().getResource("/img/fail.png")));
					ckIdMsg.setText("중복된 아이디 입니다");
					ckIdMsg.setForeground(Color.RED);
				} else if (idDuplicationCheck == true) {
					ckId.setIcon(new ImageIcon(getClass().getResource("/img/success.png")));
					ckIdMsg.setText("사용가능한 아이디 입니다");
					ckIdMsg.setForeground(Color.green);
				}
			}

		}
	}

}
