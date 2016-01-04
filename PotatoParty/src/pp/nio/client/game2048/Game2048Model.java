package pp.nio.client.game2048;

import java.awt.Color;
import java.awt.Graphics;

public class Game2048Model {
	
	private final int GRID_WIDTH = 535;
	private final int GRID_LENGTH = 535;
			
	private final int GRID_SIZE = 4;
	private final int SPACE = 15;
	
    private Cell[][] grid;

    /**
     * Instanciate the grid + all the cells in it, calling this.initializeGrid()
     */
    public Game2048Model () {
        this.grid = new Cell[GRID_SIZE][GRID_SIZE];
        this.initializeGrid();
    }
    
    /**
     * Initialize the cells with SPACE pixels between each of them
     */
    public void initializeGrid() {
        int xx = this.SPACE;
        for (int x = 0; x < GRID_SIZE; x++) {
            int yy = this.SPACE;
            for (int y = 0; y < GRID_SIZE; y++) {
                Cell cell = new Cell(xx,yy,1337);
                grid[x][y] = cell;
                yy += SPACE + Cell.CELL_WIDTH;
            }
            xx += SPACE + Cell.CELL_WIDTH;
        }
    }
    
    /**
     * Draws the grid, plus all the cells.
     * @param g
     */
	public void draw(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(10, 10, GRID_WIDTH, GRID_LENGTH);
        for (int x = 0; x < GRID_SIZE; x++) {
            for (int y = 0; y < GRID_SIZE; y++) {
                grid[x][y].draw(g);
            }
        }
    }
	
	
	public Cell getCell(int x,int y)
	{
		return grid[x][y];
	}
	
	public int getSize(){
		return this.GRID_SIZE;
	}
}
