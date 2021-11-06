package user;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class MemberEdit extends JFrame {

	private JPanel contentPane;
	private JLabel lblJoin;
	private JButton joinCompleteBtn;
	private JTextField tfUsername;
	private JTextField tfPassword;
	private JTextField tfName;
	private JTextField tfEmail;
	private JTextField tfPhone;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MemberEdit frame = new MemberEdit(null);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MemberEdit(String[] Edit) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(430, 490);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		String id = Edit[0];
		DAO dao = new DAO();
		ArrayList<Data2>list=dao.Membersrc(id);
		
		System.out.println((list.get(0).getName()));
		System.out.println((list.get(0).getEmail()));
		System.out.println((list.get(0).getPhone()));
		
		lblJoin = new JLabel("회원정보수정");
		Font f1 = new Font("맑은 고딕", Font.BOLD, 20); //궁서 바탕 돋움
		lblJoin.setFont(f1); 
		lblJoin.setBounds(145, 41, 160, 25);
		contentPane.add(lblJoin);
		
		JLabel lblUserID = new JLabel("아이디");
		lblUserID.setBounds(69, 113, 69, 20);
		contentPane.add(lblUserID);
		
		JLabel label = new JLabel("비밀번호");
		label.setBounds(69, 163, 69, 20);
		contentPane.add(label);
		
		JLabel lblName = new JLabel("이름");
		lblName.setBounds(69, 210, 69, 20);
		contentPane.add(lblName);
		
		JLabel lblEmail = new JLabel("이메일");
		lblEmail.setBounds(69, 257, 69, 20);
		contentPane.add(lblEmail);
		
		JLabel lblPhone = new JLabel("전화번호");
		lblPhone.setBounds(69, 304, 69, 20);
		contentPane.add(lblPhone);
		
		tfUsername = new JTextField();
		tfUsername.setColumns(10);
		tfUsername.setBounds(159, 106, 186, 35);
		tfUsername.setEnabled(false);
		contentPane.add(tfUsername);
		
		tfPassword = new JTextField();
		tfPassword.setColumns(10);
		tfPassword.setBounds(159, 156, 186, 35);
		contentPane.add(tfPassword);
		
		tfName = new JTextField();
		tfName.setColumns(10);
		tfName.setBounds(159, 203, 186, 35);
		contentPane.add(tfName);
		
		tfEmail = new JTextField();
		tfEmail.setColumns(10);
		tfEmail.setBounds(159, 250, 186, 35);
		contentPane.add(tfEmail);
		
		tfPhone = new JTextField();
		tfPhone.setColumns(10);
		tfPhone.setBounds(159, 297, 186, 35);
		contentPane.add(tfPhone);
		
		tfUsername.setText(Edit[0]);
		tfName.setText((list.get(0).getName()));
		tfEmail.setText((list.get(0).getEmail()));
		tfPhone.setText((list.get(0).getPhone()));
		joinCompleteBtn = new JButton("수정");
		joinCompleteBtn.setBounds(206, 363, 139, 29);
		contentPane.add(joinCompleteBtn);
		
		setVisible(true);
		//회원가입완료 액션
		joinCompleteBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Member member = new Member();
				member.setUsername(tfUsername.getText());
				member.setPassword(tfPassword.getText());
				member.setName(tfName.getText());
				member.setEmail(tfEmail.getText());
				member.setPhone(tfPhone.getText());
				UserDAO dao = UserDAO.getInstance();
				int result = dao.Update(member);


				if(result == 1) {
					JOptionPane.showMessageDialog(null, "수정이 완료되었습니다.");
					dispose();
				}else {
					JOptionPane.showMessageDialog(null, "수정 실패");
					
				}
			}
		});

	}
}


