package group13.projectgame;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTestUnit {
    Player player;

    @Test
    public void ConstructorTest(){
        player = new Player(32,32);
        assertEquals(32, player.x);
        assertEquals(32 ,player.y);
        assertEquals(0, player.PlayerScore);
        assertEquals(1, player.RoundCount);
    }

    @Test
    public void ScoreChangeTest(){
        player = new Player(32,32);
        Random rand = new Random();
        int randNum = rand.nextInt(5);
        int score = randNum;
        player.scoreChange(score);
        assertEquals(player.PlayerScore, score);

        randNum = rand.nextInt(5);
        score = score - randNum;
        player.scoreDown(randNum);
        assertEquals(player.PlayerScore, score);
    }




}
