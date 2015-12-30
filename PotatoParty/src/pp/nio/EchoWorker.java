package pp.nio;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import test.Game2048Model;

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
	      
	      // Return to sender
	      String message = new String(dataEvent.data);
	      //System.out.println("Server received $> " + message);
	      
	      String idCLient = message;
	      String command = message;
	      Game2048Model clientGame;
	      
	      switch(command) {
	      case "initialize" :
	    	  System.out.println("Server received $> " + command);
	    	  //games.add(Integer.valueOf(idCLient), new Game2048Model());
	    	  dataEvent.server.send(dataEvent.socket, ("What you did : " + command).getBytes());
	    	  break;
	      case "left" :
	    	  System.out.println("Server received $> " + command);
	    	  //clientGame = games.get(Integer.valueOf(idCLient));
	    	  clientGame = null;
	    	  System.out.println("cmd LEFT to game : " + clientGame);
	    	  dataEvent.server.send(dataEvent.socket, ("What you did : " + command).getBytes());
	    	  break;
	      case "right" :
	    	  System.out.println("Server received $> " + command);
	    	  //clientGame = games.get(Integer.valueOf(idCLient));
	    	  clientGame = null;
	    	  System.out.println("cmd RIGHT to game : " + clientGame);
	    	  dataEvent.server.send(dataEvent.socket, ("What you did : " + command).getBytes());
	    	  break;
	      case "up" :
	    	  System.out.println("Server received $> " + command);
	    	  //clientGame = games.get(Integer.valueOf(idCLient));
	    	  clientGame = null;
	    	  System.out.println("cmd UP to game : " + clientGame);
	    	  dataEvent.server.send(dataEvent.socket, ("What you did : " + command).getBytes());
	    	  break;
	      case "down" :
	    	  System.out.println("Server received $> " + command);
	    	  //clientGame = games.get(Integer.valueOf(idCLient));
	    	  clientGame = null;
	    	  System.out.println("cmd UP to game : " + clientGame);
	    	  dataEvent.server.send(dataEvent.socket, ("What you did : " + command).getBytes());
	    	  break;
	      }
	   
	      
	      //dataEvent.server.send(dataEvent.socket, "update GRID !".getBytes());
	    }
	  }

}
