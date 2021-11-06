package user;

//MenuJTabaleExam.java
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import static javax.swing.JOptionPane.*;




	//음식 정보 패널
	class JPanelRecipe1 extends JPanel  { 
		
		DAO dao = new DAO();
		private JButton jButton1;
		private JButton jButton2;
		private JButton jButton3;
		private JButton jButton4;
	    private JScrollPane jScrollPane1;
	    private JTextArea jTextArea1;
	    JTextArea ja;
		private JPanelUser win;
		private String[] name = { "재료" };
		private DefaultTableModel dt = new DefaultTableModel(name, 0);
		private JTextArea infotext = new JTextArea();
		String FRecipe = null;
		JTable Foodingrediant;
		int chk = 0;
		  public JPanelRecipe1(JPanelUser win){
			  this.win = win;
			  setLayout(null);
			  
			  
			  //음식 재료
			  JTable Foodingrediant = new JTable(dt){ //요리 재료 목록 테이블 선언
				  		public boolean isCellEditable(int row, int column) { //테이블 셀 수정 불가하게만들기
				  		return false;
				  		}
					  };
			  JScrollPane jsp = new JScrollPane(Foodingrediant);
			 
			  jsp.setSize(100,461);
			  jsp.setLocation(0,0); 
			  add(jsp);
			  
			  // 음식 레시피
			  infotext.setBorder(new LineBorder(Color.BLACK));
			  infotext.setEditable(false);
			  JScrollPane info = new JScrollPane(infotext);
			  info.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			  info.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			  info.setSize(600, 420);
			  info.setLocation(100, 40);
			  add(info);
			  
		      
			  
			  //검색
			  String[] comboName = {" 음식 "," 태그 "};
			  JComboBox combo = new JComboBox(comboName);
			  combo.setSize(70, 24);
			  combo.setLocation(100, 10);
			  add(combo);
			  
			  JTextField Searchtext = new JTextField(20);
			  Searchtext.setSize(300,25);
			  Searchtext.setLocation(170,10);
			  add(Searchtext);
			  
			  

			  	JButton Srcbtn = new JButton("검색");
		        Srcbtn.setSize(70,24);
		        Srcbtn.setLocation(470,10);
		        add(Srcbtn);
		        
		        JButton btn = new JButton("검색");
		        btn.setSize(100,40);
		        btn.setLocation(700,0);
		        add(btn);
		        
		        
			  //화면이동
		       jButton1 = new JButton("음식 추가");
		       jButton1.setSize(100,40);        
		       jButton1.setLocation(700, 45);
		       add(jButton1);
		       
		       jButton1.addActionListener(new MoveFaddListener());
		       
		       
		       jButton2 = new JButton("정보 수정");
		       jButton2.setSize(100,40);        
		       jButton2.setLocation(700, 90);
		       add(jButton2);
		       jButton2.addActionListener(new MoveTsrcListener());
		       
		       
		       Srcbtn.addActionListener(new ActionListener() { //검색 버튼 리스너
					@Override
					public void actionPerformed(ActionEvent arg0) {
						String fieldName = combo.getSelectedItem().toString();
						if(fieldName.trim().equals("음식")) {
						 FRecipe = Searchtext.getText();
						 if(!FRecipe.equals("")) {
						 // textfield에있는 음식을 검색하여 DefaultTableModle에 올리기 
						  dao.userSelectAll(dt, FRecipe); 
					      ArrayList<Data> arr = new ArrayList<Data>(); 
						  ArrayList<Data>list=dao.readData(FRecipe);
						  try {
						  infotext.setText(list.get(0).getAge());
						  System.out.println("테스트"+list.get(0).getAge());
						  }catch(Exception e)
						  {
							  
							  showMessageDialog(null,"검색 결과가 없습니다");
						  }
						 } else {
							 showMessageDialog(null,"검색할 요리를 입력해주세요");
							 
						 }
						}else if(fieldName.trim().equals("태그")) {
								//if(!FRecipe.equals("")) {
									 FRecipe = Searchtext.getText();
									 dao.Ftagsrc(dt, FRecipe);
								//}else {
								//	 showMessageDialog(null,"검색할 태그를 입력해주세요");
								 	//  }
								} 
						}
				});	 
		        
		        Foodingrediant.addMouseListener(new java.awt.event.MouseAdapter() {
		            @Override
		            public void mouseClicked(java.awt.event.MouseEvent evt) {
		            	String fieldName = combo.getSelectedItem().toString();
		            	if(fieldName.trim().equals("태그")) {
		            		chk = 1;
		            	}else if(fieldName.trim().equals("음식"))
		            	{
		            		chk = 0;
		            	}
		            	int row = Foodingrediant.rowAtPoint(evt.getPoint());
		                int col = Foodingrediant.columnAtPoint(evt.getPoint());
		                
		                if (row >= 0 && col >= 0) {
		                	System.out.println(chk);
		                	if(chk == 0) {
		                	String aw = (String) Foodingrediant.getModel().getValueAt(row, col);
		                	Searchtext.setText(aw);
		                	combo.setSelectedIndex(1);
		                	Foodingrediant.getTableHeader().getColumnModel().getColumn(0).setHeaderValue("요리");
		                	Foodingrediant.getTableHeader().repaint();
		                	dao.Ftagsrc(dt, aw);
		                	infotext.setVisible(false);
		                	
		                	//win.change("panel02");
		                	}else if (chk == 1)
		                	{	
		                		
		                		int row1 = Foodingrediant.rowAtPoint(evt.getPoint());
				                int col1 = Foodingrediant.columnAtPoint(evt.getPoint());
				                if (row >= 0 && col1 >= 0) {
				                	String aw = (String) Foodingrediant.getModel().getValueAt(row, col1);
				                	Searchtext.setText(aw);
				                	FRecipe = Searchtext.getText();
				                	ArrayList<Data> arr = new ArrayList<Data>(); 
									ArrayList<Data>list=dao.readData(FRecipe);
				                	infotext.setText(list.get(0).getAge());
				                	combo.setSelectedIndex(0);
				                	Foodingrediant.getTableHeader().getColumnModel().getColumn(0).setHeaderValue("재료");
				                	Foodingrediant.getTableHeader().repaint();
				                	
				                	dao.userSelectAll(dt, aw);
				                	
				                }
		                		
		                		/*
								  ArrayList<Data>list=dao.readData(aw);
								  try {
								  infotext.setText(list.get(0).getAge());
								  }catch(Exception e)
								  {
									  showMessageDialog(null,"검색 결과가 없습니다");
								  }*/
		                		infotext.setVisible(true);
		                	}
		                }
		            }
		            
		        });
		        
		  }
			class MoveFaddListener implements ActionListener {         // 버튼 키 눌리면 패널 1번 호출
		        @Override
		        public void actionPerformed(ActionEvent e) {
		        	win.change("panel02");
		        }
		    }
		    class MoveTsrcListener implements ActionListener {         // 버튼 키 눌리면 패널 1번 호출
		        @Override
		        public void actionPerformed(ActionEvent e) {
		        	String id = win.getTitle(); 
		        	String[] idsp = id.split(" ");
		        	MemberEdit ME = new MemberEdit(idsp);
		        }
		    }
		    
	}
	class JPanelRecipeadd1 extends JPanel{ // 2번째 패널
		public DAO dao = new DAO();
	    private JPanelUser win;
	    public String ftest;
	    private JButton jButton1;
		private JButton jButton2;
		private JButton jButton3;
		private JButton jButton4;
	    String Ftag = null;
        String fname = null;
        String frecipe = null;
        String ftag = null;
	    String[] name = { "재료" };
	    UserDTO data = new UserDTO();
	    private DefaultTableModel dt2 = new DefaultTableModel(name, 0);
	    public JPanelRecipeadd1(JPanelUser win) {
	    	
	    	setLayout(null);
	        this.win = win;
	        // 테이블 선언
	        JTable Foodingrediant = new JTable(dt2){ //요리 재료 목록 테이블 선언
			  		public boolean isCellEditable(int row, int column) { //테이블 셀 수정 불가하게만들기
			  		return false;
			  		}
				  };
			JScrollPane jsp = new JScrollPane(Foodingrediant);
		 
		   	jsp.setSize(600, 320);
		   	jsp.setLocation(100, 90); 
		  // 	add(jsp);
		   
	       
	        
	        //검색
			  JLabel SearchLabel = new JLabel("음식 이름");
			  SearchLabel.setSize(100,25);
			  SearchLabel.setLocation(40, 10);
			  add(SearchLabel);
			  
			  JLabel fnameLabel = new JLabel("음식 정보");
			  fnameLabel.setSize(100,25);
			  fnameLabel.setLocation(40, 38);
			  add(fnameLabel);
			  
			  JLabel ftagLabel = new JLabel("음식 태그");
			  ftagLabel.setSize(100,25);
			  ftagLabel.setLocation(40, 375);
			  add(ftagLabel);
			  
			  JTextField Tnametext = new JTextField(20);
			  Tnametext.setSize(600,25);
			  Tnametext.setLocation(100,10);
			  add(Tnametext);
			  JTextArea Trecipetext = new JTextArea();
			  
			  JScrollPane jsp2 = new JScrollPane(Trecipetext);
			  jsp2.setSize(600, 320);
			  jsp2.setLocation(100, 40); 
			  add(jsp2);
			  jsp2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			  jsp2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			  JTextField Ttagtext = new JTextField();
			  Ttagtext.setSize(600,25);
			  Ttagtext.setLocation(100,375);
			  add(Ttagtext);
			  
			//패널 변경
		        JButton btn = new JButton("검색");
		        btn.setSize(100,40);
		        btn.setLocation(700,0);
		        add(btn);
		        btn.addActionListener(new MoveFsrcListener());
		        //패널 변경 끝
		        
		        JButton btn2 = new JButton("음식 추가");
		        btn2.setSize(100,40);
		        btn2.setLocation(700,45);
		        add(btn2);
		        
		        
		        jButton2 = new JButton("회원 조회");
			    jButton2.setSize(100,40);        
			    jButton2.setLocation(700, 90);
			    add(jButton2);
			    
			 
		        JButton TSrcbtn = new JButton("등록");
		        TSrcbtn.setSize(100,24);
		        TSrcbtn.setLocation(700,375);
		        add(TSrcbtn);
		        
	        //검색 끝 
		        TSrcbtn.addActionListener(new ActionListener() { //검색 버튼 리스너
				@Override
				public void actionPerformed(ActionEvent arg0) {
					fname = Tnametext.getText();
					frecipe = Trecipetext.getText();
					ftag = Ttagtext.getText();
					String duple = null;
					//System.out.println(Ftag);
					if(!ftag.equals("")) {
						 duple = dao.insertData(new Model(fname, frecipe, ftag));
						 if(duple.equals("0")) {
							 showMessageDialog(null,"등록 완료");
							 Tnametext.setText("");
							 Trecipetext.setText("");
							 Ttagtext.setText("");
						 }else if (ftag.equals("중복")){
							 showMessageDialog(null,"중복입니다!");
						 }
					}else {
						 showMessageDialog(null,"빈칸을 채워주세요");
					 }
					
				}
			});	 
	    }

		class MoveFsrcListener implements ActionListener {         // 버튼 키 눌리면 패널 1번 호출
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	win.change("panel01");
	        }
	    }
	    class MoveFaddListener implements ActionListener {         // 버튼 키 눌리면 패널 1번 호출
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	win.change("panel02");
	        }
	    }
	}
	
	

	class JPanelUser extends JFrame{
	    
	    public JPanelRecipe1 jpanel01 = null;
	    public JPanelRecipeadd1  jpanel02 = null;
	    
	    public void change(String panelName){        // 패널 1번과 2번 변경 후 재설정
	        
	        if(panelName.equals("panel01")){
	            getContentPane().removeAll();
	            getContentPane().add(jpanel01);
	            revalidate();
	            repaint();
	        }else if(panelName.equals("panel02")){
	            getContentPane().removeAll();
	            getContentPane().add(jpanel02);
	            revalidate();
	            repaint();
	        }
	        
 
	        }
	    public void initial(String id) {
	    		JPanelUser win = new JPanelUser();            
		    	win.setTitle(id+" 님 안녕하세요 요리추천프로그램입니다.");
		    	win.jpanel01 = new JPanelRecipe1(win);
		    	win.jpanel02 = new JPanelRecipeadd1(win);
		    	
		    	JLabel idname = new JLabel(id);
		    	idname.setBounds(0, 0, 0, 0);
				add(idname);
				
		    	win.add(win.jpanel01);
		    	win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    	win.setSize(817,500);
		    	win.setLocationRelativeTo(null);
		    	win.setVisible(true);    
		    	
	    }
	    public static void main(String[] args) {
	    	JPanelUser win = new JPanelUser();            
	    	win.setTitle("요리 추천 프로그램");
	    	win.jpanel01 = new JPanelRecipe1(win);
	    	win.jpanel02 = new JPanelRecipeadd1(win);
	    	
	    	win.add(win.jpanel01);
	    	win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    	win.setSize(817,500);
	    	win.setLocationRelativeTo(null);
	    	win.setVisible(true);    
	    }	
		
	   
	}
