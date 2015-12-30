package pp.nio;

public class RspHandler {
	private byte[] rsp = null;

	public synchronized boolean handleResponse(byte[] rsp) {
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
		//String grid = res.replace("[0-9]", ""); REGEX qui sépare le protocole de la grille 
		int res = Integer.valueOf(str);
		System.out.println(res);
		
		switch(res) {
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
			this.init();
			break;
	
		case Protocol.HAUT_OK:
			System.out.println("Pressed Up");
			this.up(grid);
			break;
			
		case Protocol.BAS_OK:
			System.out.println("Pressed Down");
			this.down(grid);
			break;
			
		case Protocol.GAUCHE_OK:
			System.out.println("Pressed Left");
			this.left(grid);
			break;
			
		case Protocol.DROITE_OK:
			System.out.println("Pressed Right");
			this.right(grid);
			break;
			
			
		}
	}
	
	public void gameover()
	{
		
	}
	
	public void win()
	{
		
	}
	
	public void init()
	{
		
	}
	
	public void up(String grid)
	{
		//model.update(grid);
	}
	
	public void down(String grid)
	{
		
	}
	
	public void left(String grid)
	{
		
	}
	
	public void right(String grid)
	{
		
	}
}
