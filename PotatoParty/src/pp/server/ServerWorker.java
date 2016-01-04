package pp.server;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import pp.Protocol;
import pp.server.game2048.Game2048Model;

public class ServerWorker implements Runnable {

	private List<ServerDataEvent> queue = new LinkedList<ServerDataEvent>();
	private Map<Integer, Game2048Model> games = new HashMap<Integer, Game2048Model>();
	private CellGetting getter = new CellGetting();

	public void processData(Server server, SocketChannel socket, byte[] data,
			int count) {
		byte[] dataCopy = new byte[count];
		System.arraycopy(data, 0, dataCopy, 0, count);
		synchronized (queue) {
			queue.add(new ServerDataEvent(server, socket, dataCopy));
			queue.notify();
		}
	}

	public void run() {
		ServerDataEvent dataEvent;

		while (true) {
			// Wait for data to become available
			synchronized (queue) {
				while (queue.isEmpty()) {
					try {
						queue.wait();
					} catch (InterruptedException e) {
					}
				}
				dataEvent = (ServerDataEvent) queue.remove(0);
			}
			// message received
			String[] message = new String(dataEvent.data).split(":");
			// IdClient
			int idClient = Integer.valueOf(message[0]);
			// command
			int command = Integer.valueOf(message[1]);

			Game2048Model clientGame = games.get(idClient);
			if(clientGame != null)clientGame.setArrowActive(true);

			/**
			 * Arrival of command
			 */
			switch (command) {
			case Protocol.INIT:
				System.out.println("Server received $> INIT");
				this.processInit(clientGame, dataEvent, idClient);
				break;
			case Protocol.LEFT:
				System.out.println("Server received $> LEFT from : "
						+ idClient);
				this.processLeft(clientGame, dataEvent, idClient);
				break;
			case Protocol.RIGHT:
				System.out.println("Server received $> RIGHT from : "
						+ idClient);
				this.processRight(clientGame, dataEvent, idClient);
				break;
			case Protocol.UP:
				System.out.println("Server received $> UP from : "
						+ idClient);
				this.processUp(clientGame, dataEvent, idClient);
				break;
			case Protocol.DOWN:
				System.out.println("Server received $> DOWN from : "
						+ idClient);
				this.processDown(clientGame, dataEvent, idClient);
				break;
			case Protocol.RESTART:
				System.out.println("Server received $> RESTART from : "
						+ idClient);
				this.processRestart(clientGame, idClient, dataEvent);
				break;
			default:
				System.out.println("Server Received $> NULL : "
						+ new String(dataEvent.data));
				dataEvent.server.send(dataEvent.socket, dataEvent.data);
				break;
			}

		}
	}

	private void processInit(Game2048Model clientGame,
			ServerDataEvent dataEvent, int idClient) {
		if (games.get(idClient) == null) {
			games.put(idClient, new Game2048Model());
			clientGame = games.get(idClient);
			clientGame.initializeGrid();
			clientGame.setArrowActive(true);
			clientGame.addNewCell();
			clientGame.addNewCell();
			dataEvent.server.send(dataEvent.socket,
					(Protocol.INIT_OK + ":" + getter.getCells(clientGame))
							.getBytes());
		} else {
			dataEvent.server.send(dataEvent.socket,
					(Protocol.INIT_KO + ":" + getter.get404()).getBytes());
		}
	}

	private void processRestart(Game2048Model clientGame, int idClient,
			ServerDataEvent dataEvent) {
		clientGame.initializeGrid();
		clientGame.addNewCell();
		clientGame.addNewCell();
		dataEvent.server.send(dataEvent.socket,
				(Protocol.RESTART_OK + ":" + getter.getCells(clientGame))
						.getBytes());
	}

	private void processDown(Game2048Model clientGame,
			ServerDataEvent dataEvent, int idClient) {
		if (clientGame.isArrowActive() && !(clientGame == null)) {
			if (clientGame.moveCellsDown()) {
				clientGame.addNewCell();
				dataEvent.server.send(dataEvent.socket,
						(Protocol.DOWN_OK + ":" + getter.getCells(clientGame))
								.getBytes());
			} else {
				if (clientGame.isGameOver()) {
					clientGame.setArrowActive(false);
					dataEvent.server.send(dataEvent.socket, (Protocol.GAME_OVER
							+ ":" + getter.getCells(clientGame)).getBytes());
				} else {
					dataEvent.server.send(dataEvent.socket, (Protocol.DOWN_KO
							+ ":" + getter.getCells(clientGame)).getBytes());
				}
			}
		}
	}

	private void processUp(Game2048Model clientGame, ServerDataEvent dataEvent,
			int idClient) {
		if (clientGame.isArrowActive() && (clientGame != null)) {
			if (clientGame.moveCellsUp()) {
				clientGame.addNewCell();
				dataEvent.server.send(dataEvent.socket,
						(Protocol.UP_OK + ":" + getter.getCells(clientGame))
								.getBytes());
			} else {
				if (clientGame.isGameOver()) {
					clientGame.setArrowActive(false);
					dataEvent.server.send(dataEvent.socket, (Protocol.GAME_OVER
							+ ":" + getter.getCells(clientGame)).getBytes());
				} else {
					dataEvent.server.send(dataEvent.socket, (Protocol.UP_KO
							+ ":" + getter.getCells(clientGame)).getBytes());
				}
			}
		}
	}

	private void processLeft(Game2048Model clientGame,
			ServerDataEvent dataEvent, int idClient) {
		if (clientGame.isArrowActive() && (clientGame != null)) {
			if (clientGame.moveCellsLeft()) {
				clientGame.addNewCell();
				dataEvent.server.send(dataEvent.socket, (Protocol.LEFT_OK
						+ ":" + getter.getCells(clientGame)).getBytes());
			} else {
				if (clientGame.isGameOver()) {
					clientGame.setArrowActive(false);
					dataEvent.server.send(dataEvent.socket, (Protocol.GAME_OVER
							+ ":" + getter.getCells(clientGame)).getBytes());
				} else {
					dataEvent.server.send(dataEvent.socket, (Protocol.LEFT_KO
							+ ":" + getter.getCells(clientGame)).getBytes());
				}
			}
		}
	}

	private void processRight(Game2048Model clientGame,
			ServerDataEvent dataEvent, int idClient) {
		if (clientGame.isArrowActive() && (clientGame != null)) {
			if (clientGame.moveCellsRight()) {
				clientGame.addNewCell();
				dataEvent.server.send(dataEvent.socket, (Protocol.RIGHT_OK
						+ ":" + getter.getCells(clientGame)).getBytes());
			} else {
				if (clientGame.isGameOver()) {
					clientGame.setArrowActive(false);
					dataEvent.server.send(dataEvent.socket, (Protocol.GAME_OVER
							+ ":" + getter.getCells(clientGame)).getBytes());
				} else {
					dataEvent.server.send(dataEvent.socket, (Protocol.RIGHT_KO
							+ ":" + getter.getCells(clientGame)).getBytes());
				}
			}
		}
	}
}