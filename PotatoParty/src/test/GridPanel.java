package test;

import java.awt.Graphics;
import javax.swing.JPanel;

public class GridPanel extends JPanel {
	
	private static final long serialVersionUID = 18798798654654L;
	
	private Game2048Model model;
	
	/**
	 * The main panel, containing the model of the game.
	 */
	public GridPanel() {
		this.model = new Game2048Model();
    }
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        model.draw(g);
    }
	
	public Game2048Model getModel()
	{
		return this.model;
	}
	
	
}
