package group13.projectgame;
import group13.projectgame.Rewards;
import group13.projectgame.BonusReward;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
@DisplayName("Test cases for Rewards classes")


public class RewardsTest {
    public Rewards rewards;
    public BonusReward bonus;

    @Test
    public void ConstructorTest(){
        rewards = new Rewards(10,10);
        assertEquals(10,rewards.x);
        assertEquals(10, rewards.y);
    }
    @Test
    public void BonusConstructorTest(){
        bonus = new BonusReward(10,10);
        assertEquals(10,bonus.x);
        assertEquals(10, bonus.y);
        assertEquals("B", bonus.getDiff());
        assertEquals(1, bonus.getValReward());
    }

    @Test
    public void GettersTest(){
        rewards = new Rewards(10,10);
        assertEquals("M",rewards.getDiff()); //Base Case
        assertEquals(5, rewards.getValReward()); //Base Case
    }

    @Test
    public void BonusSettingTest(){
        rewards = new Rewards(10,10);
        rewards.setDiff("B");
        rewards.setValReward();
        assertEquals("B", rewards.getDiff());
        assertEquals(1, rewards.getValReward());
    }

    @Test
    public void DeadSettingTest(){
        rewards = new Rewards(10,10);
        rewards.setDiff("D");
        rewards.setValReward();
        assertEquals("D", rewards.getDiff());
        assertEquals(0, rewards.getValReward());
    }

    //TEST INTERSECT
    //TEST RENDER
}
