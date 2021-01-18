package group13.projectgame;
import java.awt.*;
import java.util.Random;


public class Enemy extends Cell {
    protected int valPunish;
    public boolean visited;

    public Enemy(int x, int y) {
        Random random = new Random();
        setCellType("enemy");
        setBounds(x,y,32,32);
        this.valPunish = random.nextInt(3);
        this.visited = false;

    }

    @Override
    public void setCellType(String cellType) {
        super.setCellType(cellType);
    }

    @Override
    public boolean intersects(Rectangle r) {
        return super.intersects(r);
    }


    /**
     * Returns the value of the punishment from specific enemy object
     *
     */
    public int getValPunish() {
        return this.valPunish;
    }
    /**
     * Sets boolean visited value to be positive in order to hide the enemy
     *
     */
    public void visited(){
        this.visited = true;
        this.valPunish = 0;
    }


    /**
     * Render the cell of the stationary enemy to be the image located at the resource folder. 
     * This method will set the color scheme for the argument to be drawn, and then fill it 
     * with the size according to the constructor of <code>Enemy</code> class.
     * 
     * @param g a default {@link Graphics} object
     * @see     Graphics
     * @see     Image
     */
    public void render(Graphics g) {
        if (this.visited){
            g.setColor(new Color(0,0,0, 0));
            return;
        }
        SpriteSheet pic = Game.spritesheet;
        g.drawImage(pic.getPicture(16, 0),x,y,62,32,null);
    }
}
