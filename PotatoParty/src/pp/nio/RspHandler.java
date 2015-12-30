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
		int res = Integer.valueOf(str);
		System.out.println(res);
		//String cmd = res.replaceAll("[0-9]", ""); Pas besoin de regex ?
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
			this.up();
			break;
			
		case Protocol.BAS_OK:
			System.out.println("Pressed Down");
			this.down();
			break;
			
		case Protocol.GAUCHE_OK:
			System.out.println("Pressed Left");
			this.left();
			break;
			
		case Protocol.DROITE_OK:
			System.out.println("Pressed Right");
			this.right();
			break;
			
			
		}
	}
}
