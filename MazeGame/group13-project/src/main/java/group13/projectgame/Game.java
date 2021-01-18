package group13.projectgame;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.*;


public class Game extends Canvas implements Runnable,KeyListener{


    private static final long serialVersionUID = 1L;

    public boolean isRunning = false;

    public static final int WIDTH = 1000, HEIGHT = 480;
    public static final String TITLE = "CMPT 276 Project Game";
    public static final String SCORE_HEADER = "SCORE";
    public static final String ROUND_HEADER = "ROUND";


    public Player getPlayer() {
        return player;
    }

    public Enemy getEnemy1() {
        return enemy1;
    }

    public Enemy getEnemy2() {
        return enemy2;
    }

    public Enemy getEnemy3() {
        return enemy3;
    }

    public MovingEnemy getMovingEnemy() {
        return movingEnemy;
    }

    public MovingEnemy getMovingEnemy1() {
        return movingEnemy1;
    }

    private Thread thread;

    public static Player player;
    public static Board board;
    public static SpriteSheet spritesheet;
    public static Enemy enemy1;
    public static Enemy enemy2;
    public static Enemy enemy3;
    public static MovingEnemy movingEnemy;
    public static MovingEnemy movingEnemy1;
    public static CopyOnWriteArrayList<Enemy> enemyList;
    public static CopyOnWriteArrayList<MovingEnemy> movEnemyList;

    public static void setPlayer(Player player) {
        Game.player = player;
    }

    public static void setEnemy1(Enemy enemy1) {
        Game.enemy1 = enemy1;
    }

    //STATE holding the current state of the game
    //START/GAME/END/PAUSE indicates specific game states
    public static final int START = 0, GAME = 1, END = 2, GUIDE = 3;
    public int STATE = -1;
    
    //variables used for flickering the text
    protected int time = 0;
    private int targetFrames = 35;
    private boolean showText = true;

    BonusReward current1 = new BonusReward(0,0);
    BonusReward current2 = new BonusReward(0,0);
    BonusReward current3 = new BonusReward(0,0);
    public  Rewards[] mandRewardList = new Rewards[10];
    public  BonusReward[] bonusRewardList = new BonusReward[5] ;
    public static int rewardCount;
    public static int bonusCount;
    int tempA = 0;
    int tempB = 0;

    public static int RoundCount;

    /**
     * Run the game. It will construct some necessary objects such as player, enemy and rewards.
     * The dimension will be set as pre-set width and height.
     *
     * @see     Graphics
     * @see     Rectangle
     * @see     Dimension
     */
    public Game() {
        Dimension dimension = new Dimension(Game.WIDTH,Game.HEIGHT);
        setPreferredSize(dimension);
        setMinimumSize(dimension);
        setMaximumSize(dimension);
        addKeyListener(this);
        
        //At beginning, the current game state equals to pause
        STATE = START;
        //Refactor the constructor for Game.class, for restarting the game later
        initialize_game();
    }

    /**
     * Creates and construct the whole game with wall/barrier, rewards and enemies. This method will
     * always get called each time a new game starts.
     * 
     * @see CopyOnWriteArrayList
     */
	public void initialize_game() {
		enemyList = new CopyOnWriteArrayList<Enemy>();
		movEnemyList = new CopyOnWriteArrayList<MovingEnemy>();
		player = new Player(320,224);
	    board = new Board("src/test/resources/map/ini_map.png");
        spritesheet = new SpriteSheet("src/test/resources/entity/entity_pic.png");
        enemy1 = new Enemy(192,64);
        enemy2 = new Enemy(32, 64);
        enemy3 = new Enemy(64, 128);
        movingEnemy = new MovingEnemy(448,256);
        movingEnemy1 = new MovingEnemy(576,160);
        enemyList.add(enemy1);
        enemyList.add(enemy2);
        enemyList.add(enemy3);
        movEnemyList.add(movingEnemy);
        movEnemyList.add(movingEnemy1);

        //for (int a = 0; a<=5; a++) {
        Rewards currentA = new Rewards(256, 352);
        Rewards currentB = new Rewards(160, 32);
        Rewards currentC = new Rewards(512, 32);
        Rewards currentD = new Rewards(520, 416);
        Rewards currentE = new Rewards(32, 416);
        //currentA.generation();
        mandRewardList[0] = currentA;
        mandRewardList[1] = currentB;
        mandRewardList[2] = currentC;
        mandRewardList[3] = currentD;
        mandRewardList[4] = currentE;
        rewardCount=5;
        //}

        //for (int b = 0; b<=2; b++){
        Random randomGen = new Random();
        int select = randomGen.nextInt(2);
        if (select==0) {
            current1.setLocation(448,256);
            current2.setLocation(205,32);
            current3.setLocation(352,416);
        }
        if (select==1) {
            current1.setLocation(32, 128);
            current2.setLocation(576,160);
            current3.setLocation(448,256);
        }
        if (select==2) {
            current1.setLocation(512, 32);
            current2.setLocation(576,160);
            current3.setLocation(160,416);
        }
        bonusRewardList[0] = current1;
        bonusRewardList[1] = current2;
        bonusRewardList[2] = current3;
        bonusCount=3;
	}

    /**
     * Start the game on the thread
     *
     * @see     Thread
     */
    public synchronized void start() {
        if(isRunning) return;
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    /**
     * Stop the game and it will throw exception when the thread is not synchronized
     */
    public synchronized void stop() {
        if(!isRunning) return;
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
    }
//    public synchronized void Restart() {
//        isRunning = false;
//        System.out.println("HEllo");
//        isRunning = true;
//    }
    

    /**
     * tick the period of the game. keep running the player and enemy to make them detect each
     * other
     *
     * @see     Player
     * @see     Enemy
     */
    protected void tick() {
    	//only if the current game state is 1, then enter the loop to start activities
        //of the entity
    	if(STATE == GAME) {
	        player.tick();
	        movingEnemy.tick();
	        movingEnemy1.tick();
    	}
    	else if(STATE == START || STATE == GUIDE || STATE ==END) {
    		time++;
    		if(time == targetFrames) {
    			time = 0;
    			if(showText) 
    				showText = false;
    			else
    				showText = true;
    		}
    	}
    } 

 

    /**
     * Display method that renders the correct color and opacity for each different objects.
     * The board will be gray and player will be yellow. It set enemy into red color and if the Reward is a
     * BonusReward it will be rendered in pink, if the Reward is a MandatoryReward
     *
     * @see     Graphics
     * @see     Rectangle
     * @see     Dimension
     * @see     Player
     * @see     Board
     * @see     Enemy
     * @see     Rewards
     */
    protected void render() {
        BufferStrategy bs = getBufferStrategy();
        if(bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.gray);
        g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

        //game starts, render everything
        if(STATE == GAME){
	        player.render(g);
	        board.render(g);
	        enemy1.render(g);
	        enemy2.render(g);
	        enemy3.render(g);
	        movingEnemy.render(g);
	        movingEnemy1.render(g);
	        Font scoreFont = new Font("Dialog", 0, 50);
	        g.setColor(Color.white);
	        g.setFont(scoreFont);
	        g.drawString(SCORE_HEADER,760, 50);
	        String score = Integer.toString(player.PlayerScore);
	        g.drawString(score, 825, 100);
	        g.drawString(ROUND_HEADER,750,300);
	        String round = Integer.toString(player.RoundCount);
	        g.drawString(round, 825, 350);
	        g.setColor(Color.gray);
	        for (int i=0; i<=rewardCount-1; i++){
	            Rewards rewards = mandRewardList[i];
	            rewards.render(g);
        }
            for (int j=0; j<=bonusCount-1; j++){
                BonusReward bonusReward = bonusRewardList[j];
                bonusReward.render(g);
            }
        }
        //if game pauses, then show the pause screen
        //for now, work as the starting screen
        else if(STATE == START) {
        	//set the color/size/position of the pause screen
        	int screenWidth = 900;
        	int screenHeight = 400;
        	int x = Game.WIDTH/2 - screenWidth/2;
        	int y = Game.HEIGHT/2 - screenHeight/2;
        	g.setColor(new Color(0,0,100));
        	g.fillRect(x, y, screenWidth, screenHeight); 
        	
        	//set the font attributes in the pause screen
        	g.setColor(Color.yellow);
        	g.setFont(new Font(Font.DIALOG, Font.BOLD, 56));
        	g.drawString("Welcome to our game" , x+150, y+120);
        	
        	g.setColor(Color.white);
        	g.setFont(new Font(Font.DIALOG, Font.ITALIC, 36));
        	if(showText) g.drawString("Press ENTER to start the game" , x+200, y+220);
        	
        	g.setColor(Color.white);
        	g.setFont(new Font(Font.DIALOG, Font.ITALIC, 36));
        	if(showText) g.drawString("Press SPACE for the insturctions" , x+180, y+310);
        }
        // implementation for the instruction screen
        else if(STATE == GUIDE) {
        	//set the color/size/position of the instruction screen
        	int screenWidth = 900;
        	int screenHeight = 400;
        	int x = Game.WIDTH/2 - screenWidth/2;
        	int y = Game.HEIGHT/2 - screenHeight/2;
        	g.setColor(new Color(0,0,100));
        	g.fillRect(x, y, screenWidth, screenHeight); 
        	
        	//set the font attributes & entity icons
        	//different explanation texts, since drawString() does not support \n
        	//have to manually set the positions and cut the paragraphs into sentences
        	g.setColor(Color.yellow);
        	g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
        	g.drawString("How to play" , x+320, y+35);
        	
        	g.setColor(Color.white);
        	g.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        	g.drawString("In this game, you will need to navigate the main character to collect all the regular", x+25, y+60);
        	
        	g.setColor(Color.white);
        	g.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        	g.drawString("rewards, and at the same time, avoid all the moving enemies as well as keep your" , x+25, y+80);
        	
        	g.setColor(Color.white);
        	g.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        	g.drawString("total score positive achieve victory by using four arrow keys." , x+25, y+100);
        	
        	g.setColor(Color.white);
        	g.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        	g.drawString("the main character that you will control throughout the whole game", x+150, y+140);
        	
        	g.setColor(Color.white);
        	g.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        	g.drawString("moving enemies that will constantly chase you" , x+150, y+190);

        	g.setColor(Color.white);
        	g.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        	g.drawString("punishment/traps that will deduct yout score by 1" , x+150, y+240);
        	
        	g.setColor(Color.white);
        	g.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        	g.drawString("The little blue rectangles are regular rewards, and they will increase your score" , x+25, y+280);

        	g.setColor(Color.white);
        	g.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        	g.drawString("by 5. The pink ones are bonus rewards. They will increase your total score by 1," , x+25, y+300);

           	g.setColor(Color.white);
        	g.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        	g.drawString("and will show up and disappear in a certain amount of time." , x+25, y+320);
        	
           	g.setColor(Color.white);
        	g.setFont(new Font(Font.DIALOG, Font.ITALIC, 30));
        	if(showText) g.drawString("Press Enter to start" , x+300, y+370);
        	//read in the icon images
        	g.drawImage(spritesheet.getPicture(0, 0), 120, 155, 32, 32, null);
        	g.drawImage(spritesheet.getPicture(0, 16), 120, 205, 32, 52, null);
        	g.drawImage(spritesheet.getPicture(16, 0), 120, 255, 62, 32, null);

        }   
        //implementation of the end screen
        else if(STATE == END) {
            //set the color/size/position of the end screen
            int screenWidth = 900;
            int screenHeight = 400;
            int x = Game.WIDTH/2 - screenWidth/2;
            int y = Game.HEIGHT/2 - screenHeight/2;
            g.setColor(new Color(0,0,100));
            g.fillRect(x, y, screenWidth, screenHeight);

            g.setColor(Color.yellow);
            g.setFont(new Font(Font.DIALOG, Font.BOLD, 50));
            g.drawString("GAME OVER" , x+290, y+120);
            
            g.setColor(Color.white);
        	g.setFont(new Font(Font.DIALOG, Font.ITALIC, 36));
        	if(showText) g.drawString("Press ENTER to start a new round" , x+180, y+200);
        }
        g.dispose();
        bs.show();
    }

    public void run() {
        requestFocus();
        int fps = 0;
        double timerA = System.currentTimeMillis();
        double timerB = System.currentTimeMillis();
        long lastTime = System.nanoTime();
        double targetTick = 60.0;
        double delta = 0;
        double ns = 1e9/targetTick;

        
        while ( isRunning ) {
        	
            long now = System.nanoTime();
            delta+=(now-lastTime) / ns;
            lastTime = now;

            while(delta >= 1) {
                tick();
                render();
                fps++;
                delta--;
            }
     

            for (Enemy enemy : enemyList){

                if(player.intersects(enemy)){
                    player.scoreDown(enemy.getValPunish());
                    enemy.visited();
                    System.out.println("enemy encountered. Current score:" + player.getPlayerScore());
                }
            }
            for (MovingEnemy enemy : movEnemyList){ ;
                Rectangle bounds = new Rectangle(enemy.x+20, enemy.y+20, enemy.width-10, enemy.height-10);
                if(player.intersects(bounds)){
                	System.out.println("You Lose!");
            		STATE = END;
         
                }
            }
            
            for (int i = 0; i <= rewardCount - 1; i++) {
                Rewards mandreward = mandRewardList[i];
                Rectangle check = new Rectangle(mandreward.x + 10, mandreward.y + 10, mandreward.width - 20, mandreward.height - 20);
                if (player.intersects(check) && mandreward.getDiff() == "M") {
                    player.scoreChange(mandRewardList[i].getValReward());
                    System.out.println("reward encountered. Current score:" + player.getPlayerScore());
                    mandRewardList[i].setDiff("D");
                    mandRewardList[i].setValReward();
                    tempA++;
                    render();
                    if (tempA % 5 == 0) { // Win Condition met, thus start new round
                        System.out.println("All mandatory rewards collected! Next Round!");
                        player.roundChange();
                        player.setLocation(320, 224);
                        for (int a = 0; a <= rewardCount - 1; a++) {
                            mandRewardList[a].setDiff("M");
                        }
                        rewardCount = 5;
                        Random randomGen = new Random();
                        int select = randomGen.nextInt(3);
                        current1.setDiff("B");
                        current2.setDiff("B");
                        current3.setDiff("B");
                        if (select == 0) {
                            System.out.println("In 0");
                            current1.setLocation(32, 128);
                            current2.setLocation(416, 32);
                            current3.setLocation(352, 416);
                        }
                        if (select == 1) {
                            System.out.println("In 1");
                            current1.setLocation(32, 128);
                            current2.setLocation(576, 160);
                            current3.setLocation(448, 256);
                        }
                        if (select == 2) {
                            System.out.println("In 2");
                            current1.setLocation(512, 32);
                            current2.setLocation(576, 160);
                            current3.setLocation(160, 416);
                        }
                        bonusRewardList[0] = current1;
                        bonusRewardList[1] = current2;
                        bonusRewardList[2] = current3;
                        bonusCount = 3;
                        render();
                    }
                }
            }

            for (int j = 0; j <= bonusCount - 1; j++) {
                BonusReward bonusReward = bonusRewardList[j];
                Rectangle check = new Rectangle(bonusReward.x + 10, bonusReward.y + 10, bonusReward.width - 20, bonusReward.height - 20);
                if (player.intersects(check) && bonusReward.getDiff() == "B") {
                    player.scoreChange(bonusRewardList[j].getValReward());
                    System.out.println("reward encountered. Current score:" + player.getPlayerScore());
                    bonusRewardList[j].setDiff("D");
                    bonusRewardList[j].setValReward();
                    tempB++;
                }
                if (System.currentTimeMillis() - timerA >= 240/targetTick) { // Random spawn of new bonus rewards when there are less than max on the board and time met
                    Random randomGen = new Random();
                    int select = randomGen.nextInt(3);
                    if (select == 0) {
                        System.out.println("In 0");
                        current1.setLocation(32, 128);
                        current2.setLocation(416, 32);
                        current3.setLocation(352, 416);
                    }
                    if (select == 1) {
                        System.out.println("In 1");
                        current1.setLocation(32, 128);
                        current2.setLocation(576, 160);
                        current3.setLocation(448, 256);
                    }
                    if (select == 2) {
                        System.out.println("In 2");
                        current1.setLocation(512, 32);
                        current2.setLocation(576, 160);
                        current3.setLocation(160, 416);
                    }
                    bonusRewardList[0] = current1;
                    bonusRewardList[1] = current2;
                    bonusRewardList[2] = current3;
                    bonusCount = 3;
                    render();
                    timerA += 1000;
                }
            }


            if(System.currentTimeMillis() - timerB >= 1000) {
                System.out.println(fps);
                System.out.println("Enemy1 coords " + enemy1.x + "," + enemy1.y);
                System.out.println("Player1 coords " + player.x + "," + player.y);
                fps = 0;
                timerB += 1000;
            }

        }

        stop();
    }


    public static void main(String[] args) {
        Game game = new Game();
        JFrame frame = new JFrame();
        frame.setTitle(Game.TITLE);
        frame.add(game);
        frame.setResizable(false);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        game.start();
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }


    @Override
    public void keyPressed(KeyEvent e) {
    	 
    	if(STATE == GAME) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) player.right = true;
        if(e.getKeyCode() == KeyEvent.VK_LEFT) player.left = true;
        if(e.getKeyCode() == KeyEvent.VK_UP) player.up = true;
        if(e.getKeyCode() == KeyEvent.VK_DOWN) player.down = true;
    	}
    	
    	//if game is in starting/end screen, then player has to press enter key 
    	//to start the game/a new round
    	else if(STATE == START|| STATE == END) {
    		if(e.getKeyCode()== KeyEvent.VK_ENTER) {
    			STATE = GAME;
    			//if the player loses, reinitialize the whole game after pressing the key
    			initialize_game();
    		}
    		else if(e.getKeyCode()== KeyEvent.VK_SPACE)
    			STATE = GUIDE;
//    		else if(e.getKeyCode()== KeyEvent.VK_ESCAPE) {
//    			
//    	} 
    	}
    	//start the game from the instruction screen
    	else if(STATE == GUIDE){
    		if(e.getKeyCode()== KeyEvent.VK_ENTER){
    			STATE = GAME;
    			initialize_game();
    		}
    	}	
    }


    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) player.right = false;
        if(e.getKeyCode() == KeyEvent.VK_LEFT) player.left = false;
        if(e.getKeyCode() == KeyEvent.VK_UP) player.up = false;
        if(e.getKeyCode() == KeyEvent.VK_DOWN) player.down = false;

    }
}

