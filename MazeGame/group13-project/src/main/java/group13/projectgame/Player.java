package group13.projectgame;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Player extends Rectangle{


    private static final long serialVersionUID = 1L;

    public boolean right,left,up,down;
    private int speed = 4;
    public int PlayerScore;
    public int RoundCount = 1;

    /**
     * Constructs the player to be the size of 30*30 as one pixel.
     *
     * @param x an integer value that indicates the x coordinate of the top-left corner of the player
     * @param y an integer value that indicates the y coordinate of the top-left corner of the player
     * @see     Rectangle
     */
    public Player(int x,int y) {
        setBounds(x,y,30,30);
    }
    
    /**
     * Sets the change to the player's current score. This method will be called each time the player
     * eats the reward/bonus reward or touches an stationary enemy/punishment.
     *
     * @param scoreChange
     */
    public void scoreChange(int scoreChange){
        this.PlayerScore = this.PlayerScore + scoreChange;
    }


    public void roundChange(){ this.RoundCount++; }


    public void scoreDown(int valPunish){
        this.PlayerScore = this.PlayerScore - valPunish;
    }

    /**
     * Retrieves the player's current score.
     *
     * @return an integer that indicates the player's current score
     */
    public int getPlayerScore(){return this.PlayerScore; }
    
    /**
     * Controls and realizes the player's movement based on the current time intervals.
     * At each time interval, this method will be called in <code>Game</code> class for
     * processing the input from the keyboard.
     *
     * @see Tick
     * @see Clock
     */
    //Can change to switch cases using enum to have cleaner code (less if statements)
    public void tick() {
        if(right && abletoMove(x+speed,y))x+=speed;
        if(left && abletoMove(x-speed,y))x-=speed;
        if(up && abletoMove(x,y-speed))y-=speed;
        if(down && abletoMove(x,y+speed))y+=speed;
    }



    /**
     * Returns a boolean value that determines whether the next move for the player is legal or not.
     * The two arguments indicate the x & y coordinates of the position resulting from the keyboard
     * inputs given by the player.
     * <p>
     * This method will always be called by <code>tick</code> method and return immediately each time
     * the player gives an keyboard input, for checking if the next move will collide with a wall/barrier
     * or enemy
     *
     * @param nextx an integer value that represents x coordinates of player's next position
     * @param nexty an integer value that represents y coordinates of player's next position
     * @return      a boolean value for checking the legality of the next move for the player
     * @see         Rectangle
     */
    private boolean abletoMove(int nextx, int nexty) {
        //Set the bound as the next position of the player, for checking collision later
        Rectangle bounds  = new Rectangle(nextx, nexty, width, height);
        Board board = Game.board;
        //loop over the whole map to check if the next movement of the player will collide with the
        //barrier/wall can be improved by just checking the adjacent pixels/tiles
        for(int x = 0; x < board.gametiles.length; x++) {
            for(int y = 0; y < board.gametiles[0].length; y++) {
                if (board.gametiles[x][y] != null) {
                    if(bounds.intersects(board.gametiles[x][y])) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Render the cell of the player to be the picture at the resource folder. This method 
     * will set the color scheme for the argument to be drawn, and then fill it with the size
     *  according to the constructor of <code>Player</code> class.
     *
     * @param g a default {@link Graphics} object
     * @see     Graphics
     * @see     Image
     */
    public void render(Graphics g) {
        SpriteSheet pic = Game.spritesheet;
        g.drawImage(pic.getPicture(0, 0), x, y, 24, 24, null);
    }
}
