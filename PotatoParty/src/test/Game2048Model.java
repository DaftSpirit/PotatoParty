package test;

import java.awt.Color;
import java.awt.Graphics;

public class Game2048Model {
	
	private final int GRID_WIDTH = 4;
	private final int SPACE = 15;
	
    private Cell[][] grid;

    
    public Game2048Model () {
        this.grid = new Cell[GRID_WIDTH][GRID_WIDTH];
        this.initializeGrid();
    }
    
    public void initializeGrid() {
        int xx = this.SPACE;
        for (int x = 0; x < GRID_WIDTH; x++) {
            int yy = this.SPACE;
            for (int y = 0; y < GRID_WIDTH; y++) {
                Cell cell = new Cell(xx,yy,0);
                grid[x][y] = cell;
                yy += SPACE + Cell.CELL_WIDTH;
            }
            xx += SPACE + Cell.CELL_WIDTH;
        }
    }
    
	public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(10, 10, 775, 750);
        
        System.out.println("hey");
        System.out.println(grid[0][0]);
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_WIDTH; y++) {
                grid[x][y].draw(g);
            }
        }
    }
}
