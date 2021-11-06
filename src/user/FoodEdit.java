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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class FoodEdit extends JFrame {

	private JPanel contentPane;
	private JLabel lblJoin;
	private JButton joinCompleteBtn;
	private JTextField tfFood;
	private JTextArea tfRecipe;
	private JTextField tfTag;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FoodEdit frame = new FoodEdit(null);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FoodEdit(String[] Edit) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(660, 500);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		lblJoin = new JLabel("요리정보수정");
		Font f1 = new Font("맑은 고딕", Font.BOLD, 20); //궁서 바탕 돋움
		lblJoin.setFont(f1); 
		lblJoin.setBounds(10, 0, 160, 25);
		contentPane.add(lblJoin);
		
		JLabel lblFood = new JLabel("요리");
		lblFood.setBounds(20, 25, 69, 20);
		contentPane.add(lblFood);
		
		JLabel labelRecipe = new JLabel("정보");
		labelRecipe.setBounds(20, 90, 69, 20);
		contentPane.add(labelRecipe);
		
		JLabel lblTag = new JLabel("태그");
		lblTag.setBounds(20, 340, 69, 20);
		contentPane.add(lblTag);
		
		tfFood = new JTextField();
		tfFood.setColumns(10);
		tfFood.setBounds(20, 50, 186, 35);
		tfFood.setEnabled(false);
		contentPane.add(tfFood);
		
		tfRecipe = new JTextArea();
		
		JScrollPane info = new JScrollPane(tfRecipe);
		info.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		info.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		info.setSize(600, 220);
		info.setLocation(20, 115);
		contentPane.add(info);
		
		//System.out.println(list.get(0).getAge());
		
		tfTag = new JTextField();
		tfTag.setColumns(10);
		tfTag.setBounds(20, 365, 250, 35);
		contentPane.add(tfTag);
		
		tfFood.setText(Edit[0]);
		tfRecipe.setText(Edit[1]);
		tfTag.setText(Edit[2]);
		joinCompleteBtn = new JButton("수정");
		joinCompleteBtn.setBounds(480, 365, 139, 29);
		contentPane.add(joinCompleteBtn);
		
		setVisible(true);
		//회원가입완료 액션
		joinCompleteBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Member member = new Member();
				member.setUsername(tfFood.getText());
				member.setPassword(tfRecipe.getText());
				member.setName(tfTag.getText());
				DAO dao = new DAO();
				int result = dao.FUpdate(member);


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


