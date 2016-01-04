package pp.client;

import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import pp.Protocol;
import pp.client.game2048.Game2048Model;
import pp.client.game2048.GridPanel;

public class ClientWorker {
	private byte[] rsp = null;
	private Game2048Model gm = null;
	private GridPanel gp = null;

	public synchronized boolean handleResponse(byte[] rsp, Game2048Model gm, GridPanel gp) {
		this.gp = gp;
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
		String cmd[]=str.split(":");
		String cells[] = cmd[1].split("-");

		int type = Integer.valueOf(cmd[0]);
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
			
		case Protocol.INIT_KO:
			System.out.println("Initialized FAILED");
			this.init(cells);
			break;
	
		case Protocol.UP_OK:
			System.out.println("Pressed Up");
			this.up(cells);
			break;
			
		case Protocol.UP_KO:
			System.out.println("Pressed Up, but nothing happens");
			break;
			
		case Protocol.DOWN_OK:
			System.out.println("Pressed Down");
			this.down(cells);
			break;
			
		case Protocol.DOWN_KO:
			System.out.println("Pressed Down but nothing happens");
			break;
			
		case Protocol.LEFT_OK:
			System.out.println("Pressed Left");
			this.left(cells);
			break;
			
		case Protocol.LEFT_KO:
			System.out.println("Pressed Left but nothing happens");
			break;
			
		case Protocol.RIGHT_OK:
			System.out.println("Pressed Right");
			this.right(cells);
			break;
			
		case Protocol.RIGHT_KO:
			System.out.println("Pressed Right but nothing happens");
			break;
			
		case Protocol.RESTART_OK:
			System.out.println("Game Restarted");
			this.restart(cells);
			break;
		}
		
		
		this.gp.repaint(); 
		this.rsp = null;
	}
	
	public void restart(String[] cells) {
		int i = 0;
		for (int x = 0; x < gm.getSize(); x++) {
            for (int y = 0; y < gm.getSize(); y++) {
            	this.gm.getCell(x, y).setValue(Integer.valueOf(cells[i]));
            	i++;
            }
		}  
	}

	public void gameover()
	{
		this.gp.add(new JLabel(new ImageIcon("res/keepo.png")));
		System.out.println("YOU\'VE LOST THE GAME");
		System.out.println("YOU\'VE LOST THE GAME");
		System.out.println("YOU\'VE LOST THE GAME");
		System.out.println("YOU\'VE LOST THE GAME");
		System.out.println("YOU\'VE LOST THE GAME");
	}
	
	public void win()
	{
		System.out.println("YOU\'VE WIN \\o/");
		System.out.println("YOU\'VE WIN \\o/");
		System.out.println("YOU\'VE WIN \\o/");
		System.out.println("YOU\'VE WIN \\o/");
		System.out.println("YOU\'VE WIN \\o/");
	}
	
	public void init(String[] cells)
	{
		int i = 0;
		for (int x = 0; x < gm.getSize(); x++) {
            for (int y = 0; y < gm.getSize(); y++) {
            	this.gm.getCell(x, y).setValue(Integer.valueOf(cells[i]));
            	i++;
            }
		}   

	}
	
	public void up(String[] cells)
	{
		int i = 0;
		for (int x = 0; x < gm.getSize(); x++) {
            for (int y = 0; y < gm.getSize(); y++) {
            	this.gm.getCell(x, y).setValue(Integer.valueOf(cells[i]));
            	i++;
            }
		}   
	}
	
	public void down(String[] cells)
	{
		int i = 0;
		for (int x = 0; x < gm.getSize(); x++) {
            for (int y = 0; y < gm.getSize(); y++) {
            	this.gm.getCell(x, y).setValue(Integer.valueOf(cells[i]));
            	i++;
            }
		}   
	}
	
	public void left(String[] cells)
	{
		int i = 0;
		for (int x = 0; x < gm.getSize(); x++) {
            for (int y = 0; y < gm.getSize(); y++) {
            	this.gm.getCell(x, y).setValue(Integer.valueOf(cells[i]));
            	i++;
            }
		}   
	}
	
	public void right(String[] cells)
	{
		int i = 0;
		for (int x = 0; x < gm.getSize(); x++) {
            for (int y = 0; y < gm.getSize(); y++) {
            	this.gm.getCell(x, y).setValue(Integer.valueOf(cells[i]));
            	i++;
            }
		}   
	}
}
