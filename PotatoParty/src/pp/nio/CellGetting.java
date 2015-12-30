package pp.nio;

import game2048.model.Game2048Model;

public class CellGetting {
				
	private final int GRID_SIZE = 4;
	
	public CellGetting() {
	}
	
	public String getCells(Game2048Model model) {
		
		String res = "";
		
        for (int x = 0; x < GRID_SIZE; x++) {
            for (int y = 0; y < GRID_SIZE; y++) {
                res += model.getCell(x, y).getValue();
                res += "|";
            }
        }
		
		return res;
	}
}
