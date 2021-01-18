package group13.projectgame;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.awt.*;
import java.awt.image.BufferStrategy;

@DisplayName("Integration test cases for Game class")

public class GameTestIntegration {
    private Game game;
    @BeforeEach
    public void testSetUp(){
        game = new Game();
    }
    @Test
    public void gameConstructorTest(){
        Assertions.assertEquals(0,game.STATE);
    }

    @Test
    public void gameInitializationTest1(){
        game.initialize_game();
        int temp = game.enemyList.size();
        Assertions.assertEquals(3,temp);
    }

    @Test
    public void gameInitializationTest2(){
        game.initialize_game();
        int temp = game.movEnemyList.size();
        Assertions.assertEquals(2,temp);
    }

    @Test
    public void gameInitializationTest3(){
        game.initialize_game();
        Rewards currentA = new Rewards(256, 352);
        Rewards currentB = new Rewards(160, 32);
        Rewards currentC = new Rewards(512, 32);
        Rewards currentD = new Rewards(520, 416);
        Rewards currentE = new Rewards(32, 416);
        Assertions.assertEquals(currentA,game.mandRewardList[0]);
        Assertions.assertEquals(currentB,game.mandRewardList[1]);
        Assertions.assertEquals(currentC,game.mandRewardList[2]);
        Assertions.assertEquals(currentD,game.mandRewardList[3]);
        Assertions.assertEquals(currentE,game.mandRewardList[4]);
    }

    @Test
    public void gameInitializationTest4(){
        game.initialize_game();
        int temp = game.rewardCount;
        Assertions.assertEquals(5,temp);
    }

    @Test
    public void gameInitializationTest5(){
        game.initialize_game();
        int temp = game.bonusCount;
        Assertions.assertEquals(3,temp);
    }

    @Test
    public void gameStartTest(){
        game.start();
        Assertions.assertTrue(game.isRunning);
    }

    @Test
    public void gameStopTest(){
        game.stop();
        Assertions.assertFalse(game.isRunning);
    }

    @Test
    public void gameTickTest(){
        game.tick();
        Assertions.assertEquals(1,game.time);
    }

    @Test
    public void PlayerEnemyCollisionTest(){
        Enemy enemy = game.enemyList.get(0);
        int enemyX = enemy.x;
        int enemyY = enemy.y;
        Player player = game.getPlayer();
        assertFalse(enemy.visited);

        player.x = enemyX;
        player.y = enemyY;
        enemy.visited();
        assertTrue(enemy.visited);



        MovingEnemy movingEnemy = game.getMovingEnemy();
        player.x = movingEnemy.x;
        player.y = movingEnemy.y;
        game.STATE = 2;
        assertEquals(game.STATE, 2);

    }

    @Test
    public void PlayerRewardCollisionTest(){
        Player player = game.getPlayer();
        Rewards reward = game.mandRewardList[0];
        BonusReward bonusReward = game.bonusRewardList[0];

        assertEquals(player.PlayerScore, 0);
        player.x = reward.x;
        player.y = reward.y;
        player.scoreChange(reward.getValReward());
        assertEquals(player.PlayerScore, reward.getValReward());

        player.PlayerScore = 0;
        assertEquals(player.PlayerScore, 0);
        player.x = bonusReward.x;
        player.y = bonusReward.y;
        player.scoreChange(bonusReward.getValReward());
        assertEquals(player.PlayerScore, bonusReward.getValReward());
    }

}
