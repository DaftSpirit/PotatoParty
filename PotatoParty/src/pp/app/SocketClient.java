package pp.app;

import java.io.*;
import java.net.*;

public class SocketClient implements Runnable {
	public void run() {
		try {
			while (true) {
				Socket socket = new Socket("localhost", 9999);
				ObjectOutputStream output = new ObjectOutputStream(
						socket.getOutputStream());

				BufferedReader keyboard = new BufferedReader(
						new InputStreamReader(System.in));
				System.out.print("client -> string to send : ");
				String strSend = keyboard.readLine();

				output.writeObject(strSend);
				socket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
