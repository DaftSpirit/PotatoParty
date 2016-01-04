package pp.nio.server;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import pp.game2048.model.Game2048Model;
import pp.nio.Protocol;

public class ServerWorker implements Runnable {

	private List<ServerDataEvent> queue = new LinkedList<ServerDataEvent>();
	private Map<Integer, Game2048Model> games = new HashMap<Integer, Game2048Model>();
	private CellGetting getter = new CellGetting();

	public void processData(Server server, SocketChannel socket,
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
			}
			// message received
			String[] message = new String(dataEvent.data).split(":");
			// IdClient
			int idClient = Integer.valueOf(message[0]);
			// command
			int command = Integer.valueOf(message[1]);
			
			Game2048Model clientGame;

			/**
			 * Arrival of command
			 */
			switch (command) {
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
			case Protocol.RESTART :
				System.out.println("Server received $> RESTART from " + idClient);
				clientGame = games.get(idClient);
				this.processRestart(clientGame, idClient, dataEvent);
			default:
				System.out.println("Server Received $> "
						+ new String(dataEvent.data));
				dataEvent.server.send(dataEvent.socket, dataEvent.data);
				break;
			}

		}
	}

	private void processRestart(Game2048Model clientGame, int idClient, ServerDataEvent dataEvent) {
		clientGame.initializeGrid();
		clientGame.addNewCell();
		clientGame.addNewCell();
		dataEvent.server.send(dataEvent.socket, (Protocol.RESTART_OK
				+ ":" + getter.getCells(clientGame)).getBytes());
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