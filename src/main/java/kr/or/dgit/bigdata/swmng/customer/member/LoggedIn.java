package kr.or.dgit.bigdata.swmng.customer.member;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import kr.or.dgit.bigdata.swmng.dto.Member;
import kr.or.dgit.bigdata.swmng.service.MemberService;
import kr.or.dgit.bigdata.swmng.util.CreateDatabaseAuto;

public class LoggedIn extends JLayeredPane implements ActionListener {
	private JLabel welcomeMsg;
	private JLabel memberPic;
	private BufferedImage img = null;
	private JButton btnBackup;
	private JButton btnReset;
	private JButton btnRestore;
	private String id = new LoginForm().getMemberId();
	private static Thread adminThread;
	private boolean flag = true;

	public static Thread getAdminThread() {
		return adminThread;
	}

	public static void setAdminThread(Thread adminThread) {
		LoggedIn.adminThread = adminThread;
	}

	public LoggedIn() {
		setLayout(null);
		memberPic = new JLabel();
		welcomeMsg = new JLabel();

		add(memberPic);
		add(welcomeMsg);

		memberPic.setBounds(120, 60, 120, 150);
		memberPic.setBorder(BorderFactory.createLineBorder(Color.white, 2, true));
		welcomeMsg.setBounds(115, 200, 200, 50);
		welcomeMsg.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		welcomeMsg.setForeground(Color.white);

		try {
			img = ImageIO.read(getClass().getResource("/img/loggedin.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		createWelcome();
		if (id.equals("admin")) {
			creataeAdminMenu();

			adminThread = new Thread() {
				@Override
				public void run() {
					int i = 300000;
					int count = 1;

					while (flag == true) {
						try {
							adminThread.sleep(i);
							new CreateDatabaseAuto().backupFromDB();
							JOptionPane.showMessageDialog(null, count + " 번째 자동백업이 되었습니다(" + i / 1000 + "초 주기)");
							count++;
						} catch (InterruptedException e) {
							flag = false;
						}
					}
				}
			};
			adminThread.setDaemon(true);
			adminThread.start();
		}

		ImgPanel imgPanel = new ImgPanel();
		imgPanel.setBounds(0, 0, 400, 365);
		add(imgPanel);

	}

	private void creataeAdminMenu() {
		btnBackup = new JButton("DB백업");
		btnReset = new JButton("DB초기화");
		btnRestore = new JButton("DB복원");
		add(btnBackup);
		add(btnReset);
		add(btnRestore);
		btnBackup.setBounds(79, 300, 70, 25);
		btnBackup.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		btnBackup.setContentAreaFilled(false);
		btnBackup.setForeground(Color.white);
		btnBackup.setBorder(BorderFactory.createEmptyBorder());
		btnReset.setBounds(146, 300, 70, 25);
		btnReset.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		btnReset.setContentAreaFilled(false);
		btnReset.setForeground(Color.white);
		btnReset.setBorder(BorderFactory.createEmptyBorder());
		btnRestore.setBounds(214, 300, 70, 25);
		btnRestore.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		btnRestore.setContentAreaFilled(false);
		btnRestore.setForeground(Color.white);
		btnRestore.setBorder(BorderFactory.createEmptyBorder());

		btnBackup.addActionListener(this);
		btnReset.addActionListener(this);
		btnRestore.addActionListener(this);
		btnBackup.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnReset.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnRestore.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	private void createWelcome() {

		List<Member> list = MemberService.getInstance().selecyByID(id);
		for (Member m : list) {
			if (m.getPic() == null) {
				memberPic.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/img/default.png")).getImage()
						.getScaledInstance(memberPic.getWidth(), memberPic.getHeight(), Image.SCALE_SMOOTH)));
			} else {
				memberPic.setIcon(new ImageIcon(new ImageIcon((byte[]) m.getPic()).getImage()
						.getScaledInstance(memberPic.getWidth(), memberPic.getHeight(), java.awt.Image.SCALE_SMOOTH)));
			}
		}
		welcomeMsg.setText(id + "님 반갑습니다");

	}

	private class ImgPanel extends JPanel {
		public void paint(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "DB백업":
			if (JOptionPane.showConfirmDialog(null, "데이터베이스 백업을 진행하시겠습니까?") == 0) {
				new CreateDatabaseAuto().backupFromDB();
			}
			JOptionPane.showMessageDialog(null, "src/backUpFolder에 백업하였습니다");
			break;
		case "DB초기화":
			if (JOptionPane.showConfirmDialog(null, "데이터베이스를 초기화 하시겠습니까") == 0) {
				new CreateDatabaseAuto().resetDB();
			}
			break;
		case "DB복원":
			if (JOptionPane.showConfirmDialog(null, "데이터베이스를 복원 하시겠습니까") == 0) {
				new CreateDatabaseAuto().restoreBackupToDB();
			}
			break;

		}
	}

}
