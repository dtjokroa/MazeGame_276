package group13.projectgame;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	
	private BufferedImage picture;
	
	/**
	 * Constructs the sprite picture from reading in the entity picture located in 
	 * the resource folder.
	 * 
	 * @param path a string that indicates the path of the entity picture for creating the icons
	 * @see        Image
	 * @see        FileInputStream
	 */
	public SpriteSheet(String path) {
		try {
			picture = ImageIO.read(new FileInputStream(path));
		}catch(IOException e) {
			System.out.println("failed to load the image");
		}
	}
	
	/**
	 * Returns an icon image for the entities in the game.
	 * 
	 * @param x an integer that indicates the x coordinate of the top-left corner of the entity(icon) picture
	 * @param y an integer that indicates the y coordinate of the top-left corner of the entity(icon) picture
	 * @return  a standard image at the resource folder
	 */
	public BufferedImage getPicture(int x, int y){
		return picture.getSubimage(x, y, x+16, y+16);
	}
	
}
