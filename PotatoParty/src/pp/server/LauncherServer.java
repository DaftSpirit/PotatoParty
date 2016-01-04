package pp.server;

import java.io.IOException;
import java.net.InetAddress;

public class LauncherServer {
	public static void main(String[] args) {
		try {
			ServerWorker worker = new ServerWorker();
			new Thread(worker).start();
			new Thread(new Server(InetAddress.getByName("localhost"), 9090, worker)).start();
			System.out.println("Server running ....");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
