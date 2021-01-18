package group13.projectgame;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Board {
	public int width;
	public int height;
	public Tile[][] gametiles;
	
	/**
	 * Builds the actual maze for the game board. This constructor will first read in the map
	 * picture located in the resource folder, and then iterate over the whole board pixel by pixel 
	 * to see if there should be a tile (wall/barrier) to be shown according to the layout from the
	 * map picture.
	 * 
	 * @param path a string that indicates the path of the map picture for creating the maze
	 * @see        Image
	 * @see        FileInputStream
	 */
	public Board(String path) {
		try {
			//read in the map image from the resource folder
			BufferedImage map = ImageIO.read(new FileInputStream(path));
			//match the dimensions of the game board with those of the map image
			this.width = map.getWidth();
			this.height = map.getHeight();
			int[] gamepixels = new int[width * height];
			gametiles = new Tile[width][height];
			map.getRGB(0, 0, width, height, gamepixels, 0, width);
			/* use 2 loops to iterate over all the pixels of the map image, and check whether 
			the color of a pixel is black or not. If such a pixel is black, then create a tile/barrier
			in the "gametiles" 2D array. */
			for(int x = 0; x < width; x++) {
				for(int y = 0; y < height; y++) {
					int val = gamepixels[x + (y * width)];
					if(val == 0xFF000000) {
						//got a barrier here
						gametiles[x][y] = new Tile(x * 32, y * 32);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Changes the pixel(s) of the game board to be a tile(wall/barrier). This method will compare the 
	 * whole game board against the predefined map picture pixel by pixel first, and then renders accordingly
	 * 
	 * @param g a default {@link Graphics} object
	 * @see     Graphics
	 */
	public void render(Graphics g) {
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				//for each grid/pixel of the game board, if it contains a tile(barrier), 
				//then render its color to be black
				if(gametiles[x][y] != null) gametiles[x][y].render(g);
			}
		}		
	}

}
