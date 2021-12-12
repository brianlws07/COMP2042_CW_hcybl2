/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2017  Filippo Ranza
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package brickWall;

import ball.Ball;
import ball.RubberBall;
import ball.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.*;
import java.nio.Buffer;
import java.util.Random;

public class Wall {

    private Rectangle area;

    private Brick[] bricks;
    private Ball ball;
    private Player player;

    //levels is 2D array of Brick, level is type int
    private Brick[][] levels;
    private int level;
    private Level levelcons;//Level class constructor

    private final Point startPoint;
    private int brickCount;

    private int ballCount; //3

    private boolean ballLost;

    private Random rnd;


    private int score = 0;
    private String highscore = "";

    //constructor class Wall

    /**
     * construct for class Wall which initialize and set values for startPoint, levels, ballCount, area
     * it also initializes a random speed for ball for every new level
     *
     * @param drawArea Rectangle GameFrame where game is rendered/drawn
     * @param brickCount number of bricks in wall(30)
     * @param lineCount lines of bricks in wall(3)
     * @param brickDimensionRatio width-to-height ratio of a single brick
     * @param ballPos position of ball(300, 430)
     */
    public Wall(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, Point ballPos){

        this.startPoint = new Point(ballPos);//startPoint on (300, 430)
        this.levelcons = new Level();
        levels = levelcons.makeLevels(drawArea,brickCount,lineCount,brickDimensionRatio);
        level = 0;
        ballCount = 3;
        rnd = new Random();

        if (highscore.equals("")){
            highscore = this.getHighscore();
        }

        makeBall(ballPos);//construct a new RubberBall on (300, 430)
        player = new Player((Point) ballPos.clone(),150,10, drawArea);
        int speedX,speedY;
        do{speedX = rnd.nextInt(5) - 2;//speedX is rnd between 0, 1, 2
        }while(speedX == 0);
        do{
            speedY = -rnd.nextInt(3);//speedY is rnd between -2, -1 ,0 (negative because the ball move upwards)
        }while(speedY == 0);
        //set the speed of the ball to the new speedX, speedY
        ball.setSpeed(speedX,speedY);
        ballLost = false;

        area = drawArea;//whole area of GameFrame
    }

    /**
     * Construct a new rubber ball
     * @param ballPos position of ball (300, 430)
     */
    private void makeBall(Point2D ballPos){ball = new RubberBall(ballPos);}

    /**
     * Read a line from highscore.txt and insert into variable highscore
     * @return String highscore
     */
    public String getHighscore(){
        //format: Brandon:100
        FileReader readFile;
        BufferedReader reader = null;
        try {
            readFile = new FileReader("highscore.txt");
            reader = new BufferedReader(readFile);
            return reader.readLine();
        } catch (Exception e) {
            return "Nobody:0";
        }
        finally{
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * to check whether player's score is higher than current highscore
     */
    public void checkScore(){
        //format: Brandon/:/100
        if (score > Integer.parseInt(highscore.split(":")[1])){
            String name = JOptionPane.showInputDialog("You set a new highscore!!! Enter your name");
            highscore = name + ":" + score;

            File scoreFile = new File("highscore.txt");

            if(!scoreFile.exists()){
                try {
                    scoreFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            FileWriter writeFile;
            BufferedWriter writer;
            try {
                writeFile = new FileWriter(scoreFile);
                writer = new BufferedWriter(writeFile);
                System.out.println(this.highscore);
                writer.write(this.highscore);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    /**
     * it checks all sorts of impacts, and carry out subsequent action
     * checks if player green bar is impacted by ball
     * check if brick are impacted until broken
     * checks if ball impacted the border of gameFrame
     * check if ball is lost by going out of bounds
     */
    public void findImpacts(){
        //if ball impacts player bar, rebound the ball upwards
        if(player.impact(ball)){
            ball.reverseY();
        }
        //if the brick is broken, then decrement no. of brick by 1
        else if(impactWall()){
            /*for efficiency reverse is done into method impactWall
            * because for every brick program checks for horizontal and vertical impacts
            */
            brickCount--;
            score++;
        }
        //if ball reaches the left and right edge of the GameFrame, then rebound the ball in the reverse direction from the direction it came
        else if(impactBorder()) {
            ball.reverseX();
        }
        //if the ball goes beyond the upper edge of the GameFrame, then rebound the ball downwards
        else if(ball.getPosition().getY() < area.getY()){
            ball.reverseY();
        }
        //if the ball goes beyond the lower edge of the GameFrame, then a ball is lost
        else if(ball.getPosition().getY() > area.getY() + area.getHeight()){
            ballCount--;
            ballLost = true;
        }
    }

    /**
     * for switch case condition, it finds the direction of the brick where the ball impacts it
     * then it rebounds the ball in reverse direction
     *
     * @return False if the brick is broken, then call impact method and return brokenFlag
     */
    private boolean impactWall(){
        for(Brick b : bricks){
            switch(b.findImpact(ball)) {
                //Vertical Impact
                case Brick.UP_IMPACT:
                    ball.reverseY();
                    return b.setImpact(ball.getDown(), Crack.UP);
                case Brick.DOWN_IMPACT:
                    ball.reverseY();
                    return b.setImpact(ball.getUp(), Crack.DOWN);

                //Horizontal Impact
                case Brick.LEFT_IMPACT:
                    ball.reverseX();
                    return b.setImpact(ball.getRight(), Crack.RIGHT);
                case Brick.RIGHT_IMPACT:
                    ball.reverseX();
                    return b.setImpact(ball.getLeft(), Crack.LEFT);
            }
        }
        return false;
    }

    /**
     * check if ball impacts border of gameFrame
     * @return True if the ball goes beyond the left or right edge of the gameFrame
     */
    private boolean impactBorder(){
        Point2D p = ball.getPosition();
        return ((p.getX() < area.getX()) ||(p.getX() > (area.getX() + area.getWidth())));
    }

    /**
     * it resets the position of player bar and ball
     * it then resets the direction of ball movement, and ballLost to false
     */
    public void ballReset(){
        player.moveTo(startPoint);
        ball.moveTo(startPoint);
        int speedX, speedY;
        do{
            speedX = rnd.nextInt(5) - 2; //0, 1, 2
        }while(speedX == 0);
        do{
            speedY = -rnd.nextInt(3); //-2, -1, 0
        }while(speedY == 0);
        ball.setSpeed(speedX,speedY);
        ballLost = false;
    }

    /**
     * it restores all the brick
     * it also reset the ballCount to 3 again
     */
    public void wallReset(){
        for(Brick b : bricks)
            b.repair();
        brickCount = bricks.length;
        ballCount = 3;
    }

    /**
     * it goes to the next level, and it also restores the brickCount back to 31
     */
    public void nextLevel(){
        bricks = levels[level++];
        this.brickCount = bricks.length;
    }

    /**
     * player and ball move
     */
    public void move(){
        player.move();
        ball.move();
    }

    /**
     * returns True if there is level remaining (4 levels in total)
     * @return boolean True or False
     */
    public boolean hasLevel(){return level < levels.length;}

    /**
     * setter for ball's speed X
     * @param s integer value for speedX
     */
    public void setBallXSpeed(int s){ball.setXSpeed(s);}

    /**
     * setter for ball's speed Y
     * @param s integer value for speedY
     */
    public void setBallYSpeed(int s){ball.setYSpeed(s);}

    /**
     * reset number of balls to 3
     */
    public void resetBallCount(){ballCount = 3;}

    /**
     * @return true if number of balls is 0
     */
    public boolean ballEnd(){return ballCount == 0;}

    /**
     * @return true if number of bricks is 0
     */
    public boolean isDone(){return brickCount == 0;}

    /**
     * getter for brickCount
     * @return number of bricks
     */
    public int getBrickCount(){return brickCount;}

    /**
     * getter for ballCount
     * @return number of balls
     */
    public int getBallCount(){return ballCount;}

    /**
     * setter for ballCount
     * @param ballCount number of balls
     */
    public void setBallCount(int ballCount) {this.ballCount = ballCount;}

    /**
     * getter for ballLost
     * @return boolean value of ballLost
     */
    public boolean isBallLost(){return ballLost;}

    /**
     * setter for ballLost
     * @param ballLost boolean value of ballLost
     */
    public void setBallLost(boolean ballLost) {this.ballLost = ballLost;}

    /**
     * getter for Bricks
     * @return array of bricks
     */
    public Brick[] getBricks() {return bricks;}

    /**
     * getter for ball
     * @return ball
     */
    public Ball getBall() {return ball;}

    /**
     * getter for player
     * @return player green bar
     */
    public Player getPlayer() {return player;}

    public int getScore() {return score;}
}
