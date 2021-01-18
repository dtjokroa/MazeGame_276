package group13.projectgame;
import java.lang.Math;

public class BonusReward extends Rewards {
    /**
     * BonusReward constructor; Constructs a BonusReward object, with coordinates (x,y) = user entered (xVal,yVal) on
     * the board, CellType "reward", size 24x24 (rectangle), diff set to "B" for BonusReward, and Val updated.
     *
     * @param xVal an int value representing the x coordinate of the BonusRewards object on the board
     * @param yVal an int value representing the y coordinate of the BonusRewards object on the board
     * @see Rectangle, Rewards
     */
    public BonusReward(int xVal, int yVal) {
        super(xVal, yVal);
        this.setDiff("B");
        this.setValReward();
    }

}
