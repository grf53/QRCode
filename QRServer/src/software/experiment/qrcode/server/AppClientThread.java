package software.experiment.qrcode.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.JSONObject;

public class AppClientThread extends Thread {
	
	public static final int TYPE_SIGNUP_REQ = 0;
	public static final int TYPE_SIGNUP_CONF = 1;
	public static final int TYPE_LOGIN_REQ = 2;
	public static final int TYPE_LOGIN_CONF = 3;
	public static final int TYPE_ADD_ITEM_REQ = 4;
	public static final int TYPE_ADD_ITEM_CONF = 5;
	public static final int TYPE_QR_EVENT = 7;
	
	public static final String KEY_JSON_TYPE = "type";
	public static final String KEY_JSON_CONTENTS = "contents";
	public static final String KEY_JSON_SUCCESS = "success";
	public static final String KEY_JSON_ID = "id";
	public static final String KEY_JSON_PW = "pw";
	public static final String KEY_JSON_EMAIL = "email";
	public static final String KEY_JSON_PHONE = "phone";
	
	private BufferedReader in;
	private BufferedWriter out;
	private InetAddress address;
	private int port;
	
	public AppClientThread(Socket socket) {
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8")); 
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
			address = socket.getInetAddress();
			port = socket.getPort();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		System.out.println("The Client " + address + ":" + port + " is connected");
		
		try {
			for(;;){
				
				boolean success = false;
				int confirmType = -1;
//				String message = readString(in);
				String message = in.readLine();
				System.out.println("Received Message:"+message);
				JSONObject jsonMessage = new JSONObject(message);
				JSONObject jsonContents = jsonMessage.getJSONObject(KEY_JSON_CONTENTS);
				switch (jsonMessage.getInt(KEY_JSON_TYPE)) {
				case TYPE_SIGNUP_REQ:
					System.out.println("signup start");
					String id = jsonContents.getString(KEY_JSON_ID);
					String pw = jsonContents.getString(KEY_JSON_PW);
					String email = jsonContents.getString(KEY_JSON_EMAIL);
					String phone = jsonContents.getString(KEY_JSON_PHONE);
					UserInfo userDuplicated = getUserInfoById(id);
					if(userDuplicated == null){
						UserInfo newUser = new UserInfo(id,pw,email,phone, address);
						success = addUserInfo(newUser);
					}
					confirmType = 1;
					System.out.println("signup end "+String.valueOf(success));
					break;
				case TYPE_LOGIN_REQ:
					System.out.println("login start");
					String idReq = jsonContents.getString(KEY_JSON_ID);
					String pwReq = jsonContents.getString(KEY_JSON_PW);
					UserInfo userReqested = getUserInfoById(idReq);
					if(userReqested != null && userReqested.pw.equals(pwReq)){
						success = true;
					}
					confirmType = 3;
					System.out.println("login end "+String.valueOf(success));
					break;
				case TYPE_ADD_ITEM_REQ:
					//not implemented.
					break;
				default:
					//error.
					System.out.println(message);
					break;
				}
				sendConfirm(confirmType, success);
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void sendConfirm(int confirmType, boolean success) throws IOException {
		JSONObject jsonConfirm = new JSONObject();
		jsonConfirm.put(KEY_JSON_TYPE, confirmType)
		.put(KEY_JSON_SUCCESS, success);
		out.append(jsonConfirm.toString());
		out.newLine();
		out.flush();
		System.out.println(jsonConfirm.toString());
	}

	private static synchronized boolean addUserInfo(UserInfo newUser) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(Main.memberFile, true));
		writer.append("id=%"+newUser.id);
		writer.newLine();
		writer.append("pw=%"+newUser.pw);
		writer.newLine();
		writer.append("email=%"+newUser.email);
		writer.newLine();
		writer.append("phone=%"+newUser.phone);
		writer.newLine();
		writer.append("address=%"+newUser.address.getHostName());
		writer.newLine();
		writer.append("#");
		writer.newLine();
		
		writer.flush();
		writer.close();
		
		return true;
	}

	private static synchronized UserInfo getUserInfoById(String id) throws IOException {		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(Main.memberFile));
			String fileString = readString(reader);
			if(fileString != null && !fileString.equals("")){
				String[] userString = fileString.split("#");
				UserInfo[] users = parseUserArray(userString); 
				for(UserInfo user: users){
					if(user.id.equals(id)){
						return user;
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	private static UserInfo[] parseUserArray(String[] userString) throws UnknownHostException {
		int length = userString.length;
		UserInfo[] users = new UserInfo[length];
		for(int i = 0; i < length; i++){
			String us = userString[i];
			String id = us.substring(us.indexOf("id=%")+"id=%".length(), us.indexOf("pw=%"));
			String pw = us.substring(us.indexOf("pw=%")+"pw=%".length(), us.indexOf("email=%"));
			String email = us.substring(us.indexOf("email=%")+"email=%".length(), us.indexOf("phone=%"));
			String phone = us.substring(us.indexOf("phone=%")+"phone=%".length(), us.indexOf("address=%"));
			String addressString = us.substring(us.indexOf("address=%")+"address=%".length());
			InetAddress address = InetAddress.getByName(addressString);
			users[i] = new UserInfo(id, pw, email, phone, address);
		}
		return users;
	}

	private static String readString(BufferedReader in) throws IOException{
		String message = "";
		for(String temp="";(temp=in.readLine())!=null;){
			message += temp;
		}
		
		return message; 
	}
}