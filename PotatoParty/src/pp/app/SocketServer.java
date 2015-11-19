package pp.app;

import java.io.*;
import java.net.*;

public class SocketServer implements Runnable {
	public void run() {
		try {
			ServerSocket server = new ServerSocket(9999);
			while (true) {
				System.out.println("\nserver is listening...");
				Socket socket = server.accept();
				ObjectInputStream input = new ObjectInputStream(
						socket.getInputStream());
				String strToReceive = (String) input.readObject();
				System.out.println("server receives ->" + strToReceive);

				socket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
