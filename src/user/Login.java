package user;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class Login extends JFrame {
			private JPanel contentPane;
			private JTextField tfUsername, tfPassword;
			private JButton loginBtn, joinBtn;

			

		public static void main(String [] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						Login frame = new Login();
				
					} catch (Exception e) {
						e.printStackTrace();
					}
				}	
			});
		}
		public Login() {
			
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(400, 300);
			setLocationRelativeTo(null);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			setTitle("로그인");
			
			JLabel lblLogin = new JLabel("아이디");
			lblLogin.setBounds(41, 52, 69, 35);
			contentPane.add(lblLogin);
			
			JLabel lblPassword = new JLabel("비밀번호");
			lblPassword.setBounds(41, 103, 69, 35);
			contentPane.add(lblPassword);
			
			tfUsername = new JTextField();
			tfUsername.setBounds(157, 52, 176, 35);
			contentPane.add(tfUsername);
			tfUsername.setColumns(10);
			
			joinBtn = new JButton("회원가입");
			joinBtn.setBounds(229, 154, 104, 29);
			contentPane.add(joinBtn);
			
			loginBtn = new JButton("로그인");
			loginBtn.setBounds(108, 154, 106, 29);
			contentPane.add(loginBtn);
			
			tfPassword = new JTextField();
			tfPassword.setColumns(10);
			tfPassword.setBounds(157, 103, 176, 35);
			contentPane.add(tfPassword);
			
			setVisible(true);
			
			joinBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					Register frame = new Register();
				}
			});
			loginBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String username = tfUsername.getText();
					String password = tfPassword.getText();
					UserDAO dao = UserDAO.getInstance();
					int result = dao.findByUsernameAndPassword(username, password);
					if(result == 1) {
						//로그인 성공 메시지
						JOptionPane.showMessageDialog(null, "로그인 성공");
						//회원 정보 리스트 화면 이동과 동시에 username 세션값으로 넘김.
						//MemberListFrame mlf = new MemberListFrame(username);
						if(username.equals("admin")) {
						JPanelTest win = new JPanelTest(); 
						win.initial();
						//현재 화면 종료
						dispose();
						}else {
							JPanelUser user = new JPanelUser();
							user.initial(username);
							//현재 화면 종료
							dispose();
						}

					}else {
						JOptionPane.showMessageDialog(null, "로그인 실패");
					}
					
				}
			});
		}
}