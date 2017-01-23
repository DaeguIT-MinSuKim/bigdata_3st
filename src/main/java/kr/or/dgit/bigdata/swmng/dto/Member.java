package kr.or.dgit.bigdata.swmng.dto;

public class Member {
	private String id;
	private String password;
	private String email;
	private byte[] pic;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte[] getPic() {
		return pic;
	}

	public void setPic(byte[] pic) {
		this.pic = pic;
	}



	public Member(String id, String password, String email, byte[] pic) {
		this.id = id;
		this.password = password;
		this.email = email;
		this.pic = pic;
	}

	public Member() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
