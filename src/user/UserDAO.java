package user;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import java.sql.DriverManager;

public class UserDAO {
	public UserDAO() {}
	private static UserDAO instance=new UserDAO();
	public static UserDAO getInstance() {
		return instance;
	}
	private Connection conn; //DB 연결 객체
	private PreparedStatement pstmt; //Query 작성 객체
	private ResultSet rs; //Query 결과 커서
	private String url = "jdbc:mariadb://localhost:3306/food";
	private String did = "root";
	private String dpw = "dbswns1203";
	private Statement st = null;
	
	public Connection getConn() {
		Connection con = null;
		
		try {
			con = DriverManager.getConnection(url, did, dpw);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	public int findByUsernameAndPassword(String username, String password) {
		//1. DB 연결
		
		try {
			conn = DriverManager.getConnection(
			       url, did, dpw);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			//2. Query 작성
			pstmt = conn.prepareStatement("select * from member where username = ? and password = ?");
			//3. Query ? 완성 (index 1번 부터 시작)
			//setString, setInt, setDouble, setTimeStamp 등이 있음.
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			
			//4. Query 실행
			//(1) executeQuery() = select = ResultSet 리턴
			//(2) executeUpdate() = insert, update, delete = 리턴 없음.
			rs = pstmt.executeQuery();
			
			//5. rs는 query한 결과의 첫번째 행(레코드) 직전에 대기중
			//결과가 count(*) 그룹함수이기 때문에 1개의 행이 리턴됨. while문이 필요 없음.
			if(rs.next()) { //next()함수는 커서를 한칸 내리면서 해당 행에 데이터가 있으면 true, 없으면 false 반환
				//결과가 있다는 것은 해당 아이디와 비번에 매칭되는 값이 있다는 뜻.
				return 1; //로그인 성공
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return -1; //로그인 실패
	}
	
	//성공 1, 실패 -1, 
	public int save(Member member) {
		try {
			st = conn.createStatement();
			rs = st.executeQuery("select * from member");
            while (rs.next()) {
            	
                String spi = rs.getString(2);
                if(!spi.equals(member.getUsername()))
                {
                	pstmt = conn.prepareStatement("insert into member (id, username, password, name, email, phone) values(NEXTVAL(member_seq), ?,?,?,?,?)");
        			pstmt.setString(1, member.getUsername());
        			pstmt.setString(2, member.getPassword());
        			pstmt.setString(3, member.getName());
        			pstmt.setString(4, member.getEmail());
        			pstmt.setString(5, member.getPhone());
        			pstmt.executeUpdate(); //return값은 처리된 레코드의 개수
        			return 1;
                }else {
                	return -1;
                }
                
            }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	public int MemberList(DefaultTableModel t_model) {
		try {
			conn = DriverManager.getConnection(url, did, dpw);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			st = conn.createStatement();
			rs = st.executeQuery("select * from member");
			for (int i = 0; i < t_model.getRowCount();) {
                t_model.removeRow(0);
            }
			 while (rs.next()) {
	             //Object data[] = { rs.getString(2), rs.getString(3) };
				 Object data[] = {rs.getString(2), rs.getString(4),
						 rs.getString(5), rs.getString(6) };
                 t_model.addRow(data); //DefaultTableModel에 레코드 추가
	                }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	//성공 Vector<Member>, 실패 null
	public Vector<Member> findByAll(){
		try {
			conn = DriverManager.getConnection(url, did, dpw);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Vector<Member> members = new Vector<>();
		try {
			pstmt = conn.prepareStatement("select * from member");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Member member = new Member();
				member.setId(rs.getLong("id"));
				member.setUsername(rs.getString("username"));
				member.setPassword(rs.getString("password"));
				member.setName(rs.getString("name"));
				member.setEmail(rs.getString("email"));
				member.setPhone(rs.getString("phone"));
				members.add(member);
			}
			return members;
	
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	public int foodsave(Foodtag foodtag) {
		
		try {	
			String query = "select * from foodtag";
			conn = DriverManager.getConnection(url, did, dpw);
			st = conn.createStatement();
			rs = st.executeQuery(query);
			String fname = null;
			String sql = "SELECT * FROM foodtag WHERE Foodname LIKE '%"+foodtag+"%'";
			
			System.out.println(sql);
			if (sql.equals(foodtag))
				
			/*while (rs.next()) {
				fname = rs.getString("Foodname");
				if(fname.equals("감자")) {
				System.out.println("중복! 중복!");
				}
				else if(!fname.equals("감자")){*/
						pstmt = conn.prepareStatement("insert into foodtag (id, Foodname) values(NEXTVAL(foodtag_seq), ?)");
						pstmt.setString(1, foodtag.getFtag());
						pstmt.executeUpdate(); //return값은 처리된 레코드의 개수
						return 1;
				//}
				//}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public UserDTO Search(DefaultTableModel t_model, String sort, String Member ) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		UserDTO dto = new UserDTO();
		try {	
			st = conn.createStatement();
			rs = st.executeQuery("select * from member WHERE "+sort+" LIKE '%"+Member+"%'");
			for (int i = 0; i < t_model.getRowCount();) {
                t_model.removeRow(0);
            }
			while (rs.next()) {
	             //Object data[] = { rs.getString(2), rs.getString(3) };
				 Object data[] = {rs.getString(2), rs.getString(4),
						 rs.getString(5), rs.getString(6) };
                 t_model.addRow(data); //DefaultTableModel에 레코드 추가
	                }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
		
	}
	public int Update(Member member) {
		try {
			conn = DriverManager.getConnection(url, did, dpw);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {																		//UPDATE member SET name = 'kimbab', email = 'kim'  WHERE username = 'kim';
               // pstmt = conn.prepareStatement("UPDATE member set username ="+member.getUsername()+','+"name ='"+member.getName()+"',"+"email ="+member.getEmail()+"phone ="+member.getPhone()+"WHERE name ="+member.getName());
			pstmt = conn.prepareStatement("UPDATE member SET name = '"+member.getName()+"', email = '"+member.getEmail()+"', phone = '"+member.getPhone()+"' WHERE username = '"+member.getUsername()+"'");
											//"', '"+member.getEmail()+"', '"+member.getPhone()+"' "+"WHERE username = '"+member.getUsername()+"'");
			//UPDATE member SET name = 'kimba', username = 'kim'  WHERE name = 'kimbab';
        		pstmt.executeUpdate(); //return값은 처리된 레코드의 개수
        			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public ArrayList<Data> Membersrc(String text) {
		ArrayList<Data> arr = new ArrayList<Data>();
		
		try {
			// 쿼리문을 db에 넘김, 온전한 문자열 대입
			Statement st = null;
			st = conn.createStatement();
			
			//String sql = "SELECT * FROM foodtag where Foodname like '%"+text+"%'";
			//부분일치
			String sql = "select * from member WHERE username IN('"+text+"')";
			//rs:ResultSet은 실행한 쿼리문의 결과 값을 받아들이다.
			rs = st.executeQuery(sql);
			
			// 받은 결과값을 출력
			while (rs.next()) {
				arr.add(new Data(rs.getString(2), rs.getString(3)));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("실패");
		} finally {
			//dbClose();
		}
		return arr;
	}
}
