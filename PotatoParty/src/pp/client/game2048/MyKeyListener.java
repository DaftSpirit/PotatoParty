package pp.client.game2048;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyKeyListener implements KeyListener {

	private boolean upPressed;
	private boolean downPressed;
	private boolean leftPressed;
	private boolean rightPressed;
	
	
	public MyKeyListener() {
		this.upPressed = false;
		this.downPressed = false;
		this.leftPressed = false;
		this.rightPressed = false;
	}

	
	public void keyPressed(KeyEvent e) {
		//System.out.println("keyPressed="+KeyEvent.getKeyText(e.getKeyCode()));
		if(KeyEvent.getKeyText(e.getKeyCode()).equals("Haut")) setUpPressed(true); 
		if(KeyEvent.getKeyText(e.getKeyCode()).equals("Bas")) setDownPressed(true); 
		if(KeyEvent.getKeyText(e.getKeyCode()).equals("Droite")) setRightPressed(true); 
		if(KeyEvent.getKeyText(e.getKeyCode()).equals("Gauche")) setLeftPressed(true); 
	}
			   
	public void keyReleased(KeyEvent e) {
		//System.out.println("keyReleased="+KeyEvent.getKeyText(e.getKeyCode()));
		if(KeyEvent.getKeyText(e.getKeyCode()).equals("Haut")) setUpPressed(false);
		if(KeyEvent.getKeyText(e.getKeyCode()).equals("Bas")) setDownPressed(false);
		if(KeyEvent.getKeyText(e.getKeyCode()).equals("Droite")) setRightPressed(false);
		if(KeyEvent.getKeyText(e.getKeyCode()).equals("Gauche")) setLeftPressed(false);
	}

	public void keyTyped(KeyEvent e) {
		if(KeyEvent.getKeyText(e.getKeyCode()).equals("Haut")) {setUpPressed(true); }
		if(KeyEvent.getKeyText(e.getKeyCode()).equals("Bas")) setDownPressed(true);
		if(KeyEvent.getKeyText(e.getKeyCode()).equals("Droite")) setRightPressed(true);
		if(KeyEvent.getKeyText(e.getKeyCode()).equals("Gauche")) setLeftPressed(true);
	}



	public boolean isUpPressed() {
		return upPressed;
	}



	public void setUpPressed(boolean upPressed) {
		this.upPressed = upPressed;
	}



	public boolean isDownPressed() {
		return downPressed;
	}



	public void setDownPressed(boolean downPressed) {
		this.downPressed = downPressed;
	}



	public boolean isRightPressed() {
		return rightPressed;
	}



	public void setRightPressed(boolean rightPressed) {
		this.rightPressed = rightPressed;
	}



	public boolean isLeftPressed() {
		return leftPressed;
	}



	public void setLeftPressed(boolean leftPressed) {
		this.leftPressed = leftPressed;
	}

}
