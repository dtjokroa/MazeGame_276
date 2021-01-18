package group13.projectgame;
//package com.company;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Rewards extends Cell{
    private int valReward = 5;
    private String diff = "M"; //M for mandatory, B for bonus, D for dead

    /**
     * Reward constructor; Constructs a Rewards object, with coordinates (x,y) = user entered (width,length) on
     * the board, CellType "reward", and size 24x24 (rectangle).
     *
     * @param width an int value representing the x coordinate of the Rewards object on the board
     * @param length an int value representing the y coordinate of the Rewards object on the board
     * @see Rectangle
     */
    public Rewards(int width, int length) {
        setCellType("reward");
        setBounds(width,length,32,32);
    }
    /**
     * Reward object diff setter; Sets this Reward object's "diff" as user entered adiff.
     *
     * @param adiff a String value entered by the user; For this game, it could be one of three values: "B" for
     *              BonusReward, "M" for MandatoryReward, or "D" for dead.
     * @see BonusReward
     */
    public void setDiff(String adiff){ this.diff = adiff; }
    /**
     * Reward object value setter; Updates this Reward object's value, such that: if it was a BonusReward it would
     * hold a value of 1, if it was a MandatoryReward it would hold a value of 5, and if it was dead it would hold a
     * value of 0.
     */
    public void setValReward() {
        if (this.diff == "B"){
            this.valReward = 1;
        }
        if (this.diff == "D"){
            this.valReward = 0;
        }
    }
    /**
     * Reward object location setter; Changes existing coordinates of this Reward object to user entered values.
     *
     * @param xVal a int value for the new x coordinate.
     * @param yVal a int value for the new y coordinate.
     * @see Rectangle
     */
    public void setLocation(int xVal, int yVal){ this.setBounds(xVal, yVal, 32,32);}
    /**
     * Reward object diff getter; Returns this Reward object's "diff" value.
     * @return  a String value of this Reward object's "diff".
     */
    public String getDiff(){ return this.diff; }
    /**
     * Reward object value getter; Returns this Reward object's "value" value.
     * @return a int value of this Reward object's "value".
     */
    public int getValReward(){return this.valReward; }
    /**
     * Checks this current Reward object's coordinates and size to see if it has encountered the Player.
     *
     * @param r a Rectangle object; in this game it is the user controlled object.
     * @return a boolean value of whether or not the player has been encountered.
     */
    @Override
    public boolean intersects(Rectangle r) {
        return super.intersects(r);
    }
    /**
     * The method of creating Reward objects. Implemented with random generation, the method selects a random number
     * from the range of 0 to the Board's width/height, then checks if the Tile is occupied. The method repeats until a
     * free Tile is located.
     *
     * @param gameboard a Board object in order to calculate the correct range of random numbers.
     * @param enemyList a List object in order to detect if an enemy exists in said Tile
     */
    public void generation() {
        Random randomGen = new Random();
        int randX = randomGen.nextInt(19)*32; //640 wide with 32 wide blocks
        int randY = randomGen.nextInt(14)*32; //480 height with 32 wide blocks
        Rectangle bounds  = new Rectangle(randX-12, randY-12, 32, 32);
        Board board = Game.board;
        //loop over the whole map to check if the next movement of the player will collide with the barrier/wall
        //can be improved by just checking the adjacent pixels/tiles
        for(int x = 0; x < board.gametiles.length; x++) {
            for(int y = 0; y < board.gametiles[0].length; y++) {
                if (bounds.intersects(board.gametiles[x][y])|| (randX-12 == x&&randY-12==y  )){

                        this.generation();

                }
                else {
                    this.setLocation(randX, randY);
                    return;
                }
            }
        }
        return;
    }
    /**
     * Display method that renders the correct colour and opacity for each different Reward. If the Reward is a
     * BonusReward it will be rendered in pink, if the Reward is a MandatoryReward, it will be rendered in blue, if the
     * Reward is dead it will be redered transparent.
     *
     * @param g Graphics object that allows for coloring of object.
     * @see Graphics
     */
    public void render(Graphics g) {
        if (this.getDiff()=="M"){
            g.setColor(Color.blue);
        }
        else if (this.getDiff()=="D"){
            g.setColor(new Color(0,0,0, 0));
        }
        else {
            g.setColor(Color.pink);
        }
        g.fillRect(x+12,y+12, 8, 8);
    }

}