package group13.projectgame;
import java.awt.*;
import java.util.Random;

public class MovingEnemy extends Enemy{

    private int random = 0,smart = 1,find_path=2;
    private int state = smart;
    private int right = 0,left=1,up=2,down=3;
    private int dir = -1;
    public Random randomGen;
    private int time = 0;
    //targetTime is 30/60 s, which means only random 30/60 s.
    private int targetTime = 30;
    private int spd =2;
    private int lastDir = -1;
    
    /**
     * Constructs a MovingEnemy
     *
     * @param width an integer value that represents the width of enemy
     * @param length an integer value that represents the length of enemy
     */
    public MovingEnemy(int x, int y) {
        super(x, y);
        randomGen = new Random();
        dir = randomGen.nextInt(4);
    }

    public int getDir() {
        return dir;
    }

    @Override
    public int getValPunish() {
        return super.getValPunish();
    }
    
    /**
     * tick the period of the movement of enemy. keep running the enemy to make them detect
     *  other objects. The Moving enemy will get close to player when its state is smart and
     *  find_path. When the moving enemy get stuck in the corner, it will change to random
     *  mode to choose a way to turn the direction, after the random time is up, then it will
     *  change to smart or find_path mode.
     *
     * @see     Player
     * @see     Enemy
     */
    public void tick(){
        //move random, check every tick
        //if hero same x or y coord, check if the othr coord less
        //than certain distance from the other
        if(state == random){
            if(dir == right){
                if(canMove(x+spd,y)){
                    x+=spd;
                }else{
                    dir = randomGen.nextInt(4);
                }
            }else if(dir == left){
                if(canMove(x-spd,y)){
                    x-=spd;
                }else{
                    dir = randomGen.nextInt(4);
                }
            }else if(dir == up){
                if (canMove(x, y - spd)) {
                    y-=spd;

                }else{
                    dir = randomGen.nextInt(4);
                }
            }else if(dir == down){
                if (canMove(x, y + spd)) {
                    y+=spd;

                }else{
                    dir = randomGen.nextInt(4);
                }
            }
            time++;

            if(time == targetTime) {
                state = smart;
                time=0;
            }
        }else if(state == smart){
            // follow the player
            boolean move = false;
            if(x < Game.player.x){
                if(canMove(x+spd,y)){
                    x+=spd;
                    move = true;
                    lastDir = right;
                }
            }
            if(x > Game.player.x){
                if(canMove(x-spd,y)){
                    x-=spd;
                    move = true;
                    lastDir = left;
                }
            }
            if(y < Game.player.y){
                if(canMove(x,y+spd)){
                    y+=spd;
                    move = true;
                    lastDir = down;
                }
            }
            if(y > Game.player.y){
                if(canMove(x,y-spd)){
                    y-=spd;
                    move = true;
                    lastDir = up;
                }
            }

            if(x == Game.player.x && y == Game.player.y){
                move =true;
            }
            if(!move){
                state = find_path;
            }

        }else if( state == find_path){
            if(lastDir == right){
                if(y < Game.player.y){
                    if(canMove(x,y+spd)){
                        y+=spd;
                        state = smart;
                    }
                }else{
                    if(canMove(x,y-spd)){
                        y-=spd;
                        state = smart;
                    }
                }
                if(canMove(x+spd,y)){
                    x+=spd;
                }
                else{
                    state = random;
                }
            }
            else if(lastDir == left){
                if(y < Game.player.y){
                    if(canMove(x,y+spd)){
                        y+=spd;
                        state = smart;
                    }
                }else{
                    if(canMove(x,y-spd)){
                        y-=spd;
                        state = smart;
                    }
                }
                if(canMove(x-spd,y)){
                    x-=spd;
                }
                else{
                    state = random;
                }
            }
            else if(lastDir == up){
                if(x < Game.player.x){
                    if(canMove(x+spd,y)){
                        x+=spd;
                        state = smart;
                    }
                }else{
                    if(canMove(x-spd,y)){
                        x-=spd;
                        state = smart;
                    }
                }
                if(canMove(x,y-spd)){
                    y-=spd;
                }
                else{
                    state = random;
                }
            }
            else if(lastDir == down){
                if(x < Game.player.x){
                    if(canMove(x+spd,y)){
                        x+=spd;
                        state = smart;
                    }
                }else{
                    if(canMove(x-spd,y)){
                        x-=spd;
                        state = smart;
                    }
                }
                if(canMove(x,y+spd)){
                    y+=spd;
                }
                else{
                    state = random;
                }
            }
        }
    }

    /**
     * Returns a boolean value that determines whether the next move for the moving enemy is legal or
     * not. The two arguments indicate the x & y coordinates of the position resulting from the path-finding
     * algorithm for the moving enemy.
     * <p>
     * This method will always be called by <code>tick</code> method in <code>MovingEnemy</code> class and
     * return immediately each time the the enemy takes its next move depending on the different cases in
     * in the path-finding algorithm.
     *
     * @param nextx an integer value that represents x coordinates of enemy's next position
     * @param nexty an integer value that represents y coordinates of enemy's next position
     * @return      a boolean value for checking the legality of the next move for the moving enemy
     * @see         Rectangle
     */
    private boolean canMove(int nextx, int nexty) {
        Rectangle bounds  = new Rectangle(nextx, nexty, width, height);
        Board board = Game.board;
        //loop over the whole map to check if the next movement of the enemy will collide with the barrier/wall
        //can be improved by just checking the adjacent pixels/tiles
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
     * Render the cell of the moving enemy to be the image located at the resource folder. 
     * This method will set the color scheme for the argument to be drawn, and then fill it 
     * with the size according to the constructor of <code>MovingEnemy</code> class.
     * 
     * @param g a default {@link Graphics} object
     * @see     Graphics
     * @see     Image
     */
    @Override
    public void render(Graphics g) {
    	SpriteSheet pic = Game.spritesheet;
        g.drawImage(pic.getPicture(0, 16),x,y,32,52,null);
    }
}




