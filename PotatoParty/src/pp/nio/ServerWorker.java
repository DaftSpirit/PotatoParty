package pp.nio;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import game2048.model.Game2048Model;

public class ServerWorker implements Runnable {

	private List<ServerDataEvent> queue = new LinkedList<ServerDataEvent>();
	private Map<Integer, Game2048Model> games = new HashMap<Integer, Game2048Model>();
	private CellGetting getter = new CellGetting();
	private String[] message;

	public void processData(NioServer server, SocketChannel socket,
			byte[] data, int count) {
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
				// message received
				this.message = new String(dataEvent.data).split(":");

				
			}
			// IdClient
			int idClient = Integer.getInteger(this.message[0]);
			// command
			String command = this.message[1];
			
			Game2048Model clientGame;

			/**
			 * Arrival of command
			 */
			switch (Integer.valueOf(command)) {
			case Protocol.INIT:
				System.out.println("Server received $> INIT");
				if (games.get(idClient) == null) {
					games.put(idClient, new Game2048Model());
					clientGame = games.get(idClient);
					clientGame.initializeGrid();
					clientGame.setArrowActive(true);
					clientGame.addNewCell();
					clientGame.addNewCell();

					dataEvent.server.send(dataEvent.socket, (Protocol.INIT_OK
							+ ":" + getter.getCells(clientGame)).getBytes());
				} else {
					dataEvent.server.send(dataEvent.socket, (Protocol.INIT_KO
							+ ":" + getter.get404()).getBytes());
				}
				break;
			case Protocol.GAUCHE:
				System.out.println("Server received $> LEFT " + "from : "
						+ idClient);
				clientGame = games.get(idClient);
				this.processLeft(clientGame, dataEvent,
						idClient);
				break;
			case Protocol.DROITE:
				System.out.println("Server received $> RIGHT " + "from : "
						+ idClient);
				clientGame = games.get(idClient);
				this.processRight(clientGame, dataEvent,
						idClient);
				break;
			case Protocol.HAUT:
				System.out.println("Server received $> UP " + "from : "
						+ idClient);
				clientGame = games.get(idClient);
				this.processUp(clientGame, dataEvent, idClient);
				break;
			case Protocol.BAS:
				System.out.println("Server received $> DOWN " + "from : "
						+ idClient);

				clientGame = games.get(idClient);
				this.processDown(clientGame, dataEvent,
						idClient);
				break;
			default:
				System.out.println("Server Received $> "
						+ new String(dataEvent.data));
				dataEvent.server.send(dataEvent.socket, dataEvent.data);
				break;
			}

		}
	}

	private void processDown(Game2048Model clientGame,
			ServerDataEvent dataEvent, int idClient) {
		if (clientGame.isArrowActive()) {

			if (clientGame.moveCellsDown()) {

				clientGame.addNewCell();
				dataEvent.server.send(dataEvent.socket,
						(Protocol.BAS_OK + ":" + getter.getCells(clientGame))
								.getBytes());

			} else {
				if (clientGame.isGameOver()) {
					clientGame.setArrowActive(false);
					dataEvent.server.send(dataEvent.socket, (Protocol.GAME_OVER
							+ ":" + getter.getCells(clientGame)).getBytes());
					games.remove(idClient);
				} else {
					dataEvent.server.send(dataEvent.socket, (Protocol.BAS_KO
							+ ":" + getter.getCells(clientGame)).getBytes());
				}
			}
		}
	}

	private void processUp(Game2048Model clientGame, ServerDataEvent dataEvent,
			int idClient) {
		if (clientGame.isArrowActive()) {

			if (clientGame.moveCellsUp()) {

				clientGame.addNewCell();
				dataEvent.server.send(dataEvent.socket,
						(Protocol.HAUT_OK + ":" + getter.getCells(clientGame))
								.getBytes());

			} else {
				if (clientGame.isGameOver()) {
					clientGame.setArrowActive(false);
					dataEvent.server.send(dataEvent.socket, (Protocol.GAME_OVER
							+ ":" + getter.getCells(clientGame)).getBytes());
					games.remove(idClient);
				} else {
					dataEvent.server.send(dataEvent.socket, (Protocol.HAUT_KO
							+ ":" + getter.getCells(clientGame)).getBytes());
				}
			}
		}
	}

	private void processLeft(Game2048Model clientGame,
			ServerDataEvent dataEvent, int idClient) {
		if (clientGame.isArrowActive()) {

			if (clientGame.moveCellsLeft()) {

				clientGame.addNewCell();
				dataEvent.server.send(dataEvent.socket, (Protocol.GAUCHE_OK
						+ ":" + getter.getCells(clientGame)).getBytes());

			} else {
				if (clientGame.isGameOver()) {
					clientGame.setArrowActive(false);
					dataEvent.server.send(dataEvent.socket, (Protocol.GAME_OVER
							+ ":" + getter.getCells(clientGame)).getBytes());
					games.remove(idClient);
				} else {
					dataEvent.server.send(dataEvent.socket, (Protocol.GAUCHE_KO
							+ ":" + getter.getCells(clientGame)).getBytes());
				}
			}
		}
	}

	private void processRight(Game2048Model clientGame,
			ServerDataEvent dataEvent, int idClient) {
		if (clientGame.isArrowActive()) {

			if (clientGame.moveCellsRight()) {

				clientGame.addNewCell();
				dataEvent.server.send(dataEvent.socket, (Protocol.DROITE_OK
						+ ":" + getter.getCells(clientGame)).getBytes());

			} else {
				if (clientGame.isGameOver()) {
					clientGame.setArrowActive(false);
					dataEvent.server.send(dataEvent.socket, (Protocol.GAME_OVER
							+ ":" + getter.getCells(clientGame)).getBytes());
					games.remove(idClient);
				} else {
					dataEvent.server.send(dataEvent.socket, (Protocol.DROITE_KO
							+ ":" + getter.getCells(clientGame)).getBytes());
				}
			}
		}
	}
}