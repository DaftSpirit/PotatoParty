package test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;


public class Cell {
	
	private int value;
	private Point pos;
	
	public static final int CELL_WIDTH = 170;
	
	public Cell(int xx, int yy, int value)
	{
		this.pos = new Point(xx,yy);
		this.value = value;
	}
	public void draw(Graphics g) {
		g.setColor(Color.GRAY);
        g.fillRect(pos.x, pos.y, CELL_WIDTH, CELL_WIDTH);
        
        String s = Integer.toString(value);
        Font font = g.getFont();
        FontRenderContext frc =  new FontRenderContext(null, true, true);
        BufferedImage image = createImage(font, frc, CELL_WIDTH, s); 
        g.drawImage(image, pos.x, pos.y, null);
    }

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	private BufferedImage createImage(Font font, FontRenderContext frc,
            int width, String s) {
 
        Font largeFont = font.deriveFont((float) (width / 4));
        Rectangle2D r = largeFont.getStringBounds(s, frc);
        int rWidth = (int) Math.round(r.getWidth());
        int rHeight = (int) Math.round(r.getHeight());
        int rX = (int) Math.round(r.getX());
        int rY = (int) Math.round(r.getY());
 
        BufferedImage image = new BufferedImage(width, width,
                BufferedImage.TYPE_INT_RGB);
         
        Graphics gg = image.getGraphics();
        gg.fillRect(0, 0, image.getWidth(), image.getHeight());
 
        int x = (width / 2) - (rWidth / 2) - rX;
        int y = (width / 2) - (rHeight / 2) - rY;
         
        gg.setFont(largeFont);
        gg.setColor(Color.BLACK);
        gg.drawString(s, x, y);
        gg.dispose();
        return image;
    }
}
	

