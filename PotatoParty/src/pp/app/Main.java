package pp.app;

public class Main {

	public static void main(String[] args) {
		new Thread(new SocketServer()).start();
		new Thread(new SocketClient()).start();

	}

}
