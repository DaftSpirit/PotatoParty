package test;

import java.util.Random;

import javax.swing.JFrame;

public class Client2048 {

	private static GridPanel gridPanel;
	
	
	/**
	 * The main. Yolo.
	 * @param args
	 */
	public static void main(String[] args) {
		
		// RANDOM ID
		Random r = new Random();
		int Low = 1;
		int High = 10000;
		int result = r.nextInt(High-Low) + Low;
		System.out.println(result);
		
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
