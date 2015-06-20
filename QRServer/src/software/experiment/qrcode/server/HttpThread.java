package software.experiment.qrcode.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

public class HttpThread extends Thread {

	private BufferedReader in;
	private BufferedWriter out;
	private InetAddress address;
	private int port;
	
	public HttpThread(Socket socket) {
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
		try {
			System.out.println(in.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
