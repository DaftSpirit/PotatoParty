package pp.nio.server;

import java.nio.channels.SocketChannel;

class ServerDataEvent {
	public Server server;
	public SocketChannel socket;
	public byte[] data;

	public ServerDataEvent(Server server, SocketChannel socket, byte[] data) {
		this.server = server;
		this.socket = socket;
		this.data = data;
	}
}
