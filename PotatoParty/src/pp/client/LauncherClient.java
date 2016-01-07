package pp.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import pp.Protocol;
import pp.client.game2048.GridPanel;
import pp.client.game2048.MyKeyListener;

public class LauncherClient {
	public static void main(String[] args) {

		try {

			/* Client */
			final Client client = new Client(
					InetAddress.getByName("localhost"), 9090);
			final ClientWorker handler = new ClientWorker();
			
			/* Key listener */
			MyKeyListener listener = new MyKeyListener();

			/* Frame */
			JFrame frame = new JFrame("Potato Party N° " + client.getID());

			frame.setSize(800, 585);
			frame.setResizable(false);
			frame.setLocation(100, 100);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			JButton restart = new JButton("RESTART");
			restart.setFocusable(false);
			restart.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						client.send((client.getID() + ":" + Protocol.RESTART).getBytes(), handler);
						handler.waitForResponse();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

			});


			GridPanel gridPanel = client.getGridPanel();
			JLabel potato = new JLabel(new ImageIcon("res/potato.gif"));
			gridPanel.add(potato);
			
			//JLabel string_id = new JLabel(String.valueOf("N°"+client.getID()));
			//string_id.setBounds(1500, 1500, 1500, 1500);
			
			frame.add(gridPanel);
			gridPanel.add(restart);
			//gridPanel.add(string_id);


			frame.addKeyListener(listener);
			frame.setVisible(true);

			Thread t = new Thread(client);
			t.setDaemon(true);
			t.start();
			System.out.println("Client nï¿½" + client.getID() + " is running....");
			client.send((client.getID() + ":20").getBytes(), handler);
			handler.waitForResponse();
			while (true) {
				Thread.sleep(70);
				if (listener.isUpPressed()) {
					client.send((client.getID() + ":" + Protocol.UP).getBytes(), handler);
					handler.waitForResponse();
				}
				if (listener.isDownPressed()) {
					client.send((client.getID() + ":" + Protocol.DOWN).getBytes(), handler);
					handler.waitForResponse();
				}
				if (listener.isLeftPressed()) {
					client.send((client.getID() + ":" + Protocol.LEFT).getBytes(), handler);
					handler.waitForResponse();
				}
				if (listener.isRightPressed()) {
					client.send((client.getID() + ":" + Protocol.RIGHT).getBytes(), handler);
					handler.waitForResponse();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
