package test;

import javax.swing.JFrame;

public class Client2048 {

	private static GridPanel gridPanel;
	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		
		MyKeyListener listener = new MyKeyListener();
		frame.addKeyListener(listener);

		frame.setSize(800, 800);
		frame.setResizable(false);
		frame.setLocation(100, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		gridPanel = new GridPanel();
        frame.add(gridPanel);
        
		
		frame.setVisible(true);
		
        

	}

}