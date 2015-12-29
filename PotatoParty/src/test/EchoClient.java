package test;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

class EchoClient implements Runnable{

	protected long endTime;
	protected SelectionKey key;
	
	
	public static void main(String args[]) {
		
	
	}
	
	static protected void blockUntil(SelectionKey key, long endTime)
			throws IOException {
		long timeout = endTime - System.currentTimeMillis();
		int nkeys = 0;
		if (timeout > 0) {
			nkeys = key.selector().select(timeout);
		} else if (timeout == 0) {
			nkeys = key.selector().selectNow();
		}
		if (nkeys == 0) {
			throw new SocketTimeoutException();
		}
	}



	protected EchoClient(SelectableChannel channel, long endTime)
			throws IOException {
		boolean done = false;
		Selector selector = null;
		this.endTime = endTime;
		try {
			selector = Selector.open();
			channel.configureBlocking(false);
			key = channel.register(selector, SelectionKey.OP_READ);
			done = true;
		} finally {
			if (!done && selector != null) {
				selector.close();
			}
			if (!done) {
				channel.close();
			}
		}
	}

	void cleanup() throws IOException {
		key.selector().close();
		key.channel().close();
	}

	public void run() {
		// TODO Auto-generated method stub
		
	}

}