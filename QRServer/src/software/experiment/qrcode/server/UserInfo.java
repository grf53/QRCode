package software.experiment.qrcode.server;

import java.net.InetAddress;

public class UserInfo {
	String id;
	String pw;
	String email;
	String phone;
	InetAddress address;
	
	public UserInfo(String id, String pw, String email, String phone, InetAddress address) {
		super();
		this.id = id;
		this.pw = pw;
		this.email = email;
		this.phone = phone;
		this.address = address;
	}
}
