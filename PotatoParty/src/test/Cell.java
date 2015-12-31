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
	private Color ValueColor = Color.BLACK;

	private Point position;

	public static final int CELL_WIDTH = 170;
	
	/**
	 * Constructor which is called on instanciation of the grid.
	 * @param x
	 * @param y
	 * @param value
	 */
	public Cell(int x, int y, int value)
	{
		this.position = new Point(x,y);
		this.value = value;
	}
	
	/**
	 * Draws the cell, if value == 0 , empty grey box is drawn. 
	 * Else the value as a string is drawn, plus the color coresponding
	 * @param g
	 */
	public void draw(Graphics g) {
		if (value == 0) {
            g.setColor(Color.GRAY);
            g.fillRect(position.x, position.y, CELL_WIDTH, CELL_WIDTH);
        } else {
	        String s = Integer.toString(value);
	        Font font = g.getFont();
	        FontRenderContext frc =  new FontRenderContext(null, true, true);
	        BufferedImage image = createImage(font, frc, CELL_WIDTH, s); 
	        g.drawImage(image, position.x, position.y, null);
        }
    }

	/**
	 *  Creates the image inside the cell (color + value)
	 *  s: value as a string
	 *  
	 * @param font
	 * @param frc
	 * @param width
	 * @param s
	 * @return
	 */
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
        gg.setColor(this.ValueColor);
        gg.drawString(s, x, y);
        gg.dispose();
        return image;
    }
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Color getValueColor() {
		return ValueColor;
	}

	public void setValueColor(Color valueColor) {
		ValueColor = valueColor;
	}
	
	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}
}
	

