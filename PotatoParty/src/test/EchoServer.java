package test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;

public class EchoServer {

	public static final int DEFAULT_PORT = 8189;
	private Selector selector;
	private ServerSocketChannel server;
	private final int port;

	public EchoServer() {
		this(DEFAULT_PORT);
	}

	public EchoServer(int port) {
		this.port = port;
	}

	public void setup() throws IOException {
		this.selector = Selector.open();
		this.server = ServerSocketChannel.open();
		this.server.configureBlocking(false);
		InetAddress ia = InetAddress.getLocalHost();
		InetSocketAddress isa = new InetSocketAddress(ia, this.port);
		this.server.socket().bind(isa);
	}

	public void start() throws IOException {
		System.out.println("setting up server...");
		this.setup();
		System.out.println("server started...");
		SelectionKey acceptKey = this.server.register(this.selector, SelectionKey.OP_ACCEPT);
		
		while (acceptKey.selector().select() > 0) {
			for (Iterator<SelectionKey> it = this.selector.selectedKeys().iterator(); it.hasNext();) {
				SelectionKey key = it.next();
				it.remove();
				
				if (!key.isValid()) continue;
				
				if (key.isAcceptable()) {
					System.out.println("Key is Acceptable");
					ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
					SocketChannel socket = (SocketChannel) ssc.accept();
					socket.configureBlocking(false);
					socket.register(this.selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
					continue;
				}
				
				if (key.isReadable()) {
					SocketChannel clientChannel = (SocketChannel) key.channel();
					this.doEcho("readable", clientChannel);
					continue;
				}
				
				if (key.isWritable()) {
					SocketChannel clientChannel = (SocketChannel) key.channel();
					this.doEcho("writable", clientChannel);
					continue;
				}
			}
		}
	}

	public void doEcho(String evt, SocketChannel socket) throws IOException {
		String msg = this.readMessage(socket);
		if (msg.length() <= 0)
			return;
		if (msg.trim().equals("quit"))
			socket.close();
		else if (msg.length() > 0) {
			System.out.println("key is " + evt + " -> " + msg.trim());
			this.writeMessage(socket, msg);
		}
	}

	public String readMessage(SocketChannel socket) throws IOException {
		ByteBuffer rcvbuf = ByteBuffer.allocate(1024);
		int nBytes = socket.read(rcvbuf);
		rcvbuf.flip();
		Charset charset = Charset.forName("us-ascii");
		CharsetDecoder decoder = charset.newDecoder();
		String res = decoder.decode(rcvbuf).toString();
		return res;
	}

	public void writeMessage(SocketChannel socket, String msg)
			throws IOException {
		ByteBuffer buffer = ByteBuffer.wrap((msg.getBytes()));
		int nBytes = socket.write(buffer);
	}

	public static void main(String args[]) {
		EchoServer server = new EchoServer();
		try {
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}