package pp.nio;

import test.Game2048Model;

public class RspHandler {
	private byte[] rsp = null;
	private Game2048Model gm = null;

	public synchronized boolean handleResponse(byte[] rsp, Game2048Model gm) {
		this.gm = gm;
		this.rsp = rsp;
		this.notify();
		return true;
	}

	public synchronized void waitForResponse() {
		while (this.rsp == null) {
			try {
				this.wait();
			} catch (InterruptedException e) {
			}
		}
		
		/* TRAITEMENT DE LA REPONSE DU SERVEUR */
		
		String str = new String(this.rsp);
		System.out.println(str);
		
		String cmd[]=str.split(":");
		System.out.println(cmd[0]);
		System.out.println(cmd[1]);
		int type = Integer.valueOf(cmd[0]);
		
		String cells[] = cmd[1].split("|");

		switch(type) {
		case Protocol.GAME_OVER:
			System.out.println("You've Lost");
			this.gameover();
			break;
		
		case Protocol.WIN:
			System.out.println("You've Win");
			this.win();
			break;
			
		case Protocol.INIT_OK:
			System.out.println("Initialized..");
			this.init(cells);
			break;
	
		case Protocol.HAUT_OK:
			System.out.println("Pressed Up");
			this.up(cells);
			break;
			
		case Protocol.BAS_OK:
			System.out.println("Pressed Down");
			this.down(cells);
			break;
			
		case Protocol.GAUCHE_OK:
			System.out.println("Pressed Left");
			this.left(cells);
			break;
			
		case Protocol.DROITE_OK:
			System.out.println("Pressed Right");
			this.right(cells);
			break;
			
			
		}
	}
	
	public void gameover()
	{
		
	}
	
	public void win()
	{
		
	}
	
	public void init(String[] cells)
	{
		for(int i = 0; i < cells.length ; i = i +2)
		{
				System.out.println(cells[i]);
			
			for (int x = 0; x < gm.getSize(); x++) {
	            for (int y = 0; y < gm.getSize(); y++) {
	            	this.gm.getCell(x, y).setValue(Integer.valueOf(cells[i]));
	            }
			}   
		}
		System.out.println("---------------------------");
		for (int x = 0; x < gm.getSize(); x++) {
            for (int y = 0; y < gm.getSize(); y++) {
            	System.out.println(this.gm.getCell(x, y).getValue());
            }
		}   
	
		
		
		
	}
	
	public void up(String[] cells)
	{
	}
	
	public void down(String[] cells)
	{
		
	}
	
	public void left(String[] cells)
	{
		
	}
	
	public void right(String[] cells)
	{
		
	}
}
