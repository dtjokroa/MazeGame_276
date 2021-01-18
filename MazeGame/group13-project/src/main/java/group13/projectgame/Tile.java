package group13.projectgame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Tile extends Rectangle{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a tile to be the size of 32*32 as one pixel.
	 * 
	 * @param x an integer value that indicates the x coordinate of the top-left corner of the tile
	 * @param y an integer value that indicates the y coordinate of the top-left corner of the tile
	 * @see     Component
	 */
	public Tile(int x, int y) {
		//set the size of one block/barrier to be one pixel
		setBounds(x, y, 32, 32);
	}

	/**
	 * Renders the cell of a wall/barrier to be black. This method will 
	 * set the color for the argument to be drawn, and then fill it 
	 * with the size according to the constructor of <code>Tile</code> class.
	 * 
	 * @param g a default {@link Graphics} object
	 * @see     Graphics
	 * @see     Rectangle
	 */
	public void render(Graphics g) {
		//set the color of the block to be black
		g.setColor(Color.black);
		g.fillRect(x, y, width, height);
	}

}
