package user;
import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import java.sql.DriverManager;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class DAO {

	// Connection은 데이터베이스와 연결하는 객체이다.
	Connection conn = null;
	// ResultSet : 실행한 쿼리문의 값을 받는 객체
	ResultSet rs = null;
	ResultSet rs2 = null;
	private PreparedStatement pstmt; //Query 작성 객체
	Statement st = null; // 그냥 가져오는거
	// PreparedStatement는 쿼리문에 ?를 사용해서 추가로 ?에 변수를 할당해 줄수 있도록 하는 객체
	PreparedStatement ps = null; // ?넣어서 집어넣는거

	// 생성자
	public DAO() {

		try {
			String user = "root";
			String pw = "dbswns1203";
			String url = "jdbc:mariadb://127.0.0.1:3306/food";

			// jdbc drive를 등록하는 과정
			// class.forName을 호출하면 Driver가 자기자신을 초기화하여 DriverManager에 등록한다.
			// 즉, 개발자가 따로 관리하지 않는 static 객체들이 알아서 DriverManager에 등록되는 것이다.
			// 그래서 Class.forName()을 호출하고 나서 어떤 인자로도 전달하지 않고 바로 getConnection()을 호출해도 드라이버가 찾아진다.
			
			// Driver Class를 로딩하면 객체가 생성되고, DriverManager에 등록된다.
			Class.forName("org.mariadb.jdbc.Driver");
			// connection으로 db와 연결 (객체 생성)
			conn = DriverManager.getConnection(url, user, pw);

		} catch (ClassNotFoundException cnfe) {
			System.out.println("DB 드라이버 로딩 실패 :" + cnfe.toString());
		} catch (SQLException sqle) {
			System.out.println("DB 접속실패 : " + sqle.toString());
		} catch (Exception e) {
			System.out.println("Unkonwn error");
			e.printStackTrace();
		}
	}

	// 사용하지 않는 자원이 유지 되기 때문에 자원이 낭비된다.
	public void dbClose() {
		try {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			if (ps != null)
				ps.close();
		} catch (Exception e) {
			System.out.println(e + "=> dbClose fail");
		}
	}

	// Create
	public String insertData(Model model) {
		try {
			st = conn.createStatement();
			String sql = "INSERT INTO foodinfo(id, Foodname, FoodRecipe, Foodtag) values(NEXTVAL(info_seq),?, ?, ?)";
			String sql2 = "SELECT * FROM foodinfo WHERE Foodname LIKE '%"+model.foodname+"%'";
			//rs:ResultSet은 실행한 쿼리문의 결과 값을 받아들이다.
			rs2 = st.executeQuery(sql2);
			String Ftag = "중복";
			// 받은 결과값을 출력
			while (rs2.next()) {
				Ftag = (rs2.getString(2));
			}
			if(Ftag.equals(model.foodname))
			{
				Ftag = "중복";
				return Ftag;
			}else {
			//INSERT INTO foodinfo(id, Foodname, FoodRecipe, Foodtag) values (NEXTVAL(info_seq), 'name',' recipe', 'tag');
			// PrparedStatment객체 생성, 인자로 sql문이 주어짐
			ps = conn.prepareStatement(sql);
			ps.setString(1, model.foodname);
			ps.setString(2, model.foodrecipe);
			ps.setString(3, model.foodtag);
			//ps.setInt(2, data.age);
			// executeUpdate : insert, delete, update와 같이 값을 받아오지 않는 쿼리문 실행
			ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbClose();
		}return "0";
	}

	// Read
	public ArrayList<Data> readData(String text) {
		ArrayList<Data> arr = new ArrayList<Data>();
		
		try {
			// 쿼리문을 db에 넘김, 온전한 문자열 대입
			st = conn.createStatement();
			
			//String sql = "SELECT * FROM foodtag where Foodname like '%"+text+"%'";
			//부분일치
			String sql = "select * from foodinfo WHERE Foodname IN('"+text+"')";
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
			dbClose();
		}
		return arr;
	}

	// Update
	public void updateData(Data data) {
		try {
			String sql = "UPDATE foodtag SET Foodname=? WHERE Foodname=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, data.name);
			ps.setString(2, data.age);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbClose();
		}
	}

	// Delete
	public void deleteData(String name) {
		try {
			//"SELECT * FROM foodtag WHERE Foodname LIKE '%"+text+"%'";
			String sql = " DELETE FROM foodtag WHERE Foodname = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbClose();
		}
	}
	public void userSelectAll(DefaultTableModel t_model, String FRecipe) {
        try {
            st = conn.createStatement();
            //rs = st.executeQuery("select * from foodinfo WHERE Foodname LIKE '%"+FRecipe+"%'");
            rs = st.executeQuery("select * from foodinfo WHERE Foodname IN('"+FRecipe+"')");
            // DefaultTableModel에 있는 기존 데이터 지우기
            for (int i = 0; i < t_model.getRowCount();) {
                t_model.removeRow(0);
            }
            
            while (rs.next()) {
          	 
                //Object data[] = { rs.getString(2), rs.getString(3) };
               // t_model.addRow(data); //DefaultTableModel에 레코드 추가
                String spi = rs.getString(4);
                String[] sp = spi.split(",");
                for (int i = 0; i < sp.length; i++) {
                	
                	 Object data[] = { sp[i] };
                     t_model.addRow(data); //DefaultTableModel에 레코드 추가
                }
                
            }
        } catch (SQLException e) {
            System.out.println(e + "=> userSelectAll fail");
        } finally {
            dbClose();
        }
    }public void Ftagsrc(DefaultTableModel t_model, String Ftag) {
        try {
        	String[] arStr= Ftag.split(" ");
        	// 스플릿해서 띄어쓰기기준 나누기해서 rs추가
            st = conn.createStatement();
     
            
            //rs = st.executeQuery("select * from foodinfo WHERE FoodTag LIKE '%"+arStr[0]+"%'"+ "AND FoodTag LIKE '%"+arStr[1]+"%'");
            rs = st.executeQuery("select * from foodinfo WHERE FoodTag LIKE '%"+arStr[0]+"%'");								
            //LIKE '%밥%' AND FoodcTag LIKE '%돼지고기%'
            // DefaultTableModel에 있는 기존 데이터 지우기
            for (int i = 0; i < t_model.getRowCount();) {
                t_model.removeRow(0);
            }
            
            while (rs.next()) {
                //Object data[] = { rs.getString(2), rs.getString(3) };
               // t_model.addRow(data); //DefaultTableModel에 레코드 추가
                String spi = rs.getString(2);
                System.out.println(spi);
                String[] sp = spi.split(",");
                for (int i = 0; i < sp.length; i++) {
                	
                	 Object data[] = { sp[i] };
                     t_model.addRow(data); //DefaultTableModel에 레코드 추가
                }
            }
        } catch (SQLException e) {
            System.out.println(e + "=> userSelectAll fail");
        } finally {
            dbClose();
        }
    }
    public void FList(DefaultTableModel t_model) {
        try {
            st = conn.createStatement();
            rs = st.executeQuery("select * from foodinfo");								
            for (int i = 0; i < t_model.getRowCount();) {
                t_model.removeRow(0);
            }
			while (rs.next()) {
	             //Object data[] = { rs.getString(2), rs.getString(3) };
				 Object data[] = {rs.getString(2), rs.getString(3),rs.getString(4)};
                 t_model.addRow(data); //DefaultTableModel에 레코드 추가
	        }
        } catch (SQLException e) {
            System.out.println(e + "=> userSelectAll fail");
        } finally {
            dbClose();
        }
    }
	public int FUpdate(Member member) {
		try {
			pstmt = conn.prepareStatement("UPDATE foodinfo SET FoodRecipe = '"+member.getPassword()+"', FoodTag = '"+member.getName()+"' WHERE Foodname = '"+member.getUsername()+"'");
			pstmt.executeUpdate(); //return값은 처리된 레코드의 개수
        			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	public void FSrc(DefaultTableModel t_model, String sort, String name ) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {	
			st = conn.createStatement();
			rs = st.executeQuery("select * from foodinfo WHERE "+sort+" LIKE '%"+name+"%'");
			for (int i = 0; i < t_model.getRowCount();) {
                t_model.removeRow(0);
            }
			while (rs.next()) {
	             //Object data[] = { rs.getString(2), rs.getString(3) };
				 Object data[] = {rs.getString(2), rs.getString(3), rs.getString(4)};
                 t_model.addRow(data); //DefaultTableModel에 레코드 추가
	                }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public ArrayList<Data2> Membersrc(String text) {
		ArrayList<Data2> arr = new ArrayList<Data2>();
		
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
				arr.add(new Data2(rs.getString(2), rs.getString(4),rs.getString(5), rs.getString(6)));
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