package software.experiment.qrcode.server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

	protected static File memberFile;
	private static ServerSocket httpHandler;
	private static ServerSocket appClientHandler;

	public static void main(String[] args) {
		
		try {
			init();
			System.out.println("Server Waiting Connection on "+appClientHandler.getLocalSocketAddress().toString()
									+":"+appClientHandler.getLocalPort());
			while (true) {
				Socket appClientSocket = appClientHandler.accept();
				AppClientThread appThread = new AppClientThread(appClientSocket);
				appThread.start();
				
//				Socket httpSocket = httpHandler.accept();
//				HttpThread httpThread = new HttpThread(httpSocket);
//				httpThread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void init() throws IOException{
		memberFile = new File("membership.txt");
		appClientHandler = new ServerSocket(10000, 10);
		httpHandler = new ServerSocket(10001, 10);
	}

}
