package user;

public class UserDTO {
	String userID;
	String userPassword;
	String Msg = null;
	String Test = null;
	String tag = null;

	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getTag() {
		return tag;
	}
	public String getMsg() {
		return Msg;
	}
	public void  setMsg(String Msg) {
		this.Msg = Msg;
	}
	
	public String GetUserID() {
		return userID;
	}
	public void SetUserID(String userID) {
		this.userID = userID;
	}

	
	public String getUserPassWord() {
		return userPassword;
	}
	public void setUserPassWord(String userPassword) {
		this.userPassword = userPassword;
	}
	
	public String getTest() {
		return Test;
	}
	
	public void setTest(String Test) {
		this.Test = Test;
	}
	
}
