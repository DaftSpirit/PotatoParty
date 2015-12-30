package pp.nio;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import game2048.model.Game2048Model;

public class EchoWorker implements Runnable {
	
	private List<ServerDataEvent> queue = new LinkedList<ServerDataEvent>();
	
	private ArrayList<Game2048Model> games = new ArrayList<Game2048Model>();
	  
	  public void processData(NioServer server, SocketChannel socket, byte[] data, int count) {
	    byte[] dataCopy = new byte[count];
	    System.arraycopy(data, 0, dataCopy, 0, count);
	    synchronized(queue) {
	      queue.add(new ServerDataEvent(server, socket, dataCopy));
	      queue.notify();
	    }
	  }
	  
	  public void run() {
	    ServerDataEvent dataEvent;
	    
	    while(true) {
	      // Wait for data to become available
	      synchronized(queue) {
	        while(queue.isEmpty()) {
	          try {
	            queue.wait();
	          } catch (InterruptedException e) {
	          }
	        }
	        dataEvent = (ServerDataEvent) queue.remove(0);
	      }
	      
	      System.out.println("Server Received $> " + new String(dataEvent.data));
	      
	      // message received
	      String message[] = new String(dataEvent.data).split(":");
	      
	      //IdClient
	      String idCLient = message[0];
	      
	      //command
	      String command = message[1];
	      
	      
	      Game2048Model clientGame;
	      
	      /**
	       * Arrival of command
	       */
	      switch(Integer.valueOf(command)) {
	      case Protocol.INIT :
	    	  System.out.println("Server received $> " + command);
	    	  
	    	  games.add(Integer.valueOf(idCLient), new Game2048Model());
	    	  clientGame = games.get(Integer.valueOf(idCLient));
	    	  
	    	  dataEvent.server.send(dataEvent.socket, (Protocol.INIT_OK + ":" + command).getBytes());
	    	  break;
	      case Protocol.GAUCHE :
	    	  System.out.println("Server received $> " + command);
	    	  
	    	  clientGame = games.get(Integer.valueOf(idCLient));
	    	  clientGame.moveCellsLeft();
	    	  
	    	  System.out.println("cmd LEFT to game : " + clientGame);
	    	  
	    	  dataEvent.server.send(dataEvent.socket, (Protocol.GAUCHE_OK + ":" + command).getBytes());
	    	  break;
	      case Protocol.DROITE :
	    	  System.out.println("Server received $> " + command);
	    	  
	    	  clientGame = games.get(Integer.valueOf(idCLient));
	    	  clientGame.moveCellsRight();
	    	  System.out.println("cmd RIGHT to game : " + clientGame);
	    	  
	    	  dataEvent.server.send(dataEvent.socket, (Protocol.DROITE_OK + ":" + command).getBytes());
	    	  break;
	      case Protocol.HAUT :
	    	  System.out.println("Server received $> " + command);
	    	  
	    	  clientGame = games.get(Integer.valueOf(idCLient));
	    	  clientGame.moveCellsUp();
	    	  System.out.println("cmd UP to game : " + clientGame);
	    	  
	    	  dataEvent.server.send(dataEvent.socket, (Protocol.HAUT_OK + ":" + command).getBytes());
	    	  break;
	      case Protocol.BAS :
	    	  System.out.println("Server received $> " + command);
	    	  
	    	  clientGame = games.get(Integer.valueOf(idCLient));
	    	  clientGame.moveCellsDown();
	    	  System.out.println("cmd DOWN to game : " + clientGame);
	    	  
	    	  dataEvent.server.send(dataEvent.socket, (Protocol.BAS_OK + ":" + command).getBytes());
	    	  break;
	      }
	   
	      
	      dataEvent.server.send(dataEvent.socket, dataEvent.data);
	    }
	  }

}
