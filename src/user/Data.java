package user;

public class Data {
	String name;
	String age;
	String tag;

    
	public Data(String name, String age) {
		this.age = age;
		this.name = name;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

}

