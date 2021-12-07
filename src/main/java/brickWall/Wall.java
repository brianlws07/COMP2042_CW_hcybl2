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

    //CONSTANTS:
    //private static final int LEVELS_COUNT = 4;// 4 levels
    //private static final int CLAY = 1;
    //private static final int STEEL = 2;
    //private static final int CEMENT = 3;

    //private Random rnd;
    private Rectangle area;

    private Brick[] bricks;
    private Ball ball;
    private Player player;
    //private Crack crack;

    //levels is 2D array of Brick, level is type int
    private Brick[][] levels;
    private int level;
    private Level levelcons;

    private final Point startPoint;
    private int brickCount;
    private int ballCount; //3
    private boolean ballLost;

    private int score;
    private String highscore = "";

    //constructor class Wall
    public Wall(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, Point ballPos){

        this.startPoint = new Point(ballPos);//startPoint on (300, 430)
        this.levelcons = new Level();

        levels = levelcons.makeLevels(drawArea,brickCount,lineCount,brickDimensionRatio);
        level = 0;
        ballCount = 3;

        //rnd = new Random();

        score = 0;
        if (highscore.equals("")){
            highscore = this.getHighscore();
        }

        makeBall(ballPos);//construct a new RubberBall on (300, 430)
        player = new Player((Point) ballPos.clone(),150,10, drawArea);
        ballspeedReset();
        /*int speedX,speedY;
        do{speedX = rnd.nextInt(5) - 2;//speedX is rnd between 0, 1, 2
        }while(speedX == 0);
        do{
            speedY = -rnd.nextInt(3);//speedY is rnd between -2, -1 ,0 (negative because the ball move upwards)
        }while(speedY == 0);
        //set the speed of the ball to the new speedX, speedY
        ball.setSpeed(speedX,speedY);
        ballLost = false;*/

        area = drawArea;//it is the whole area of JFrame not inclusive of the top bar
    }

    private void makeBall(Point2D ballPos){ball = new RubberBall(ballPos);}//construct a new RubberBall

    public String getHighscore(){
        //format: Brandon:100
        FileReader readFile = null;
        BufferedReader reader = null;
        try {
            readFile = new FileReader("highscore.dat");
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

    public void checkScore(){

        if (highscore.equals(""))
            return;

        //format: Brandon/:/100
        if (score > Integer.parseInt(highscore.split(":")[1])){
            String name = JOptionPane.showInputDialog("You set a new highscore!!! Enter your name");
            highscore = name + ":" + score;

            File scoreFile = new File("highscore.dat");

            if(!scoreFile.exists()){
                try {
                    scoreFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            //FileWriter writeFile = null;
            //BufferedWriter writer = null;
            try {
                FileWriter writeFile = new FileWriter(scoreFile);
                BufferedWriter writer = new BufferedWriter(writeFile);
                writer.write(this.getHighscore());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    private Brick[][] makeLevels(Rectangle drawArea,int brickCount,int lineCount,double brickDimensionRatio){
        //construct a tmp variable of type 2D array of Bricks, LEVELS_COUNT is 4
        Brick[][] tmp = new Brick[LEVELS_COUNT][];
        //it stores the object of bricks created in the inner array, and there are 31 objects for 31 bricks
        tmp[0] = makeSingleTypeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY);
        tmp[1] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY,CEMENT);
        tmp[2] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY,STEEL);
        tmp[3] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,STEEL,CEMENT);
        return tmp;
    }

    private Brick[] makeSingleTypeLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int type){

          //if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          //multiple of lineCount smaller then brickCount

        //so that the number of Bricks are able to fill in the JPanel
        //31bricks, 3 lineCount, so brickCount = 31 - 31%3
        //                                     = 30
        //we always have 31 bricks to fill up the JPanel or JFrame
        brickCnt -= brickCnt % lineCnt;

        //no. of bricks per line (10)
        int brickOnLine = brickCnt / lineCnt;

        //length of a single brick
        double brickLen = drawArea.getWidth() / brickOnLine;
        //height of a single brick
        //brickSizeRation is w:h
        double brickHgt = brickLen / brickSizeRatio;

        //set the brickCount from 30 back to 31 again
        brickCnt += lineCnt / 2;

        //construct tmp as array of Brick with size 31
        Brick[] tmp  = new Brick[brickCnt];

        //construct brickSize as type Dimension with specified brickLen, brickHgt
        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt);
        //construct new Point p
        Point p = new Point();

        int i;
        //for loop executes for 31 times for all bricks
        for(i = 0; i < tmp.length; i++){
            //there is only 3 lines, so line = 0, 1, 2
            int line = i / brickOnLine;
            //it will only break if it has executed for all 3 lines of bricks
            if(line == lineCnt)
                break;
            //this sections sets the x and y coordinate for the bricks
            //this set x to the x coordinate of every brick's upper left corner
            double x = (i % brickOnLine) * brickLen;
            //even number line(0, 2) will set the x coordinate for brick normally
            //odd number line(1) will start the line with half the length of the brick
            x =(line % 2 == 0) ? x : (x - (brickLen / 2));
            //to set the y coordinate for every brick
            double y = (line) * brickHgt;
            p.setLocation(x,y);
            tmp[i] = makeBrick(p,brickSize,type);
        }

        //this is specifically for the last brick in the 2nd line (line 1)
        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt){
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x,y);
            tmp[i] = new ClayBrick(p,brickSize);
        }
        return tmp;

    }

    //most implementation here are similar to makeSingleTypeLevel
    private Brick[] makeChessboardLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int typeA, int typeB){

          //if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          //multiple of lineCount smaller then brickCount

        brickCnt -= brickCnt % lineCnt; //set brickCnt to 30

        int brickOnLine = brickCnt / lineCnt; //set brickOnLine to 10

        //needed for setting the brick pattern in 2nd line (line 1)
        int centerLeft = brickOnLine / 2 - 1; //centerLeft is 4
        int centerRight = brickOnLine / 2 + 1; //centerRight is 6

        double brickLen = drawArea.getWidth() / brickOnLine; //length of single brick
        double brickHgt = brickLen / brickSizeRatio; //height of single brick

        brickCnt += lineCnt / 2;// set brickCnt back to 31

        Brick[] tmp  = new Brick[brickCnt]; // set tmp as Brick[31]

        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt); // initialize Dimension brickSize for a single brick
        Point p = new Point();

        int i;
        for(i = 0; i < tmp.length; i++){
            int line = i / brickOnLine;
            if(line == lineCnt)
                break;
            //this sections sets the x and y coordinate for the bricks
            int posX = i % brickOnLine;
            double x = posX * brickLen;
            x =(line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p.setLocation(x,y);

            //set pattern for the bricks
            //for line 0, 2 the pattern of every brick will alternate each other
            //for line 1 only brick 5 and 6 will be different pattern than the rest in line
            boolean b = ((line % 2 == 0 && i % 2 == 0) || (line % 2 != 0 && posX > centerLeft && posX <= centerRight));
            tmp[i] = b ?  makeBrick(p,brickSize,typeA) : makeBrick(p,brickSize,typeB);
        }

        //this is specifically for the last brick in the 2nd line (line 1)
        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt){
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x,y);
            tmp[i] = makeBrick(p,brickSize,typeA);
        }
        return tmp;
    }

    //method makeBrick
    //creates a brick object based on type at the exact location point
    private Brick makeBrick(Point point, Dimension size, int type){
        Brick out;
        switch(type){
            case CLAY:
                out = new ClayBrick(point,size);
                break;
            case STEEL:
                out = new SteelBrick(point,size);
                break;
            case CEMENT:
                out = new CementBrick(point, size);
                break;
            default:
                throw  new IllegalArgumentException(String.format("Unknown Type:%d\n",type));
        }
        return  out;
    }


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

    //for switch case condition, it finds the direction of the brick where the ball impacts it
    //then it rebounds the ball
    //then it returns True if the brick is broken, else it returns False if brick is not broken
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

    //returns True if the ball goes beyond the left or right edge of the Game Frame
    private boolean impactBorder(){
        Point2D p = ball.getPosition();
        return ((p.getX() < area.getX()) ||(p.getX() > (area.getX() + area.getWidth())));
    }


    //it resets the position of player bar and ball
    //it then resets the direction of ball movement
    //it also resets ballLost to false
    public void ballReset(){
        player.moveTo(startPoint);
        ball.moveTo(startPoint);
        ballspeedReset();
    }

    public void ballspeedReset(){
        int speedX = 2,speedY = -3;
        /*do{
            speedX = rnd.nextInt(5) - 2; //0, 1, 2
        }while(speedX == 0);
        do{
            speedY = -rnd.nextInt(3); //-2, -1, 0
        }while(speedY == 0);*/
        ball.setSpeed(speedX,speedY);
        ballLost = false;
    }

    //it restores all the brick
    //it also reset the ballCount to 3 again
    public void wallReset(){
        for(Brick b : bricks)
            b.repair();
        brickCount = bricks.length;
        ballCount = 3;
    }

    //it goes to the next level, and it also restores the brickCount back to 31
    public void nextLevel(){
        bricks = levels[level++];
        this.brickCount = bricks.length;
    }


    public boolean hasLevel(){return level < levels.length;} //returns True if there is level remaining (4 levels in total)

    public void setBallXSpeed(int s){ball.setXSpeed(s);}

    public void setBallYSpeed(int s){ball.setYSpeed(s);}

    public void resetBallCount(){ballCount = 3;}

    public void move(){//player and ball move
        player.move();
        ball.move();
    }

    public boolean ballEnd(){return ballCount == 0;}

    public boolean isDone(){return brickCount == 0;}

    public int getBrickCount(){return brickCount;}

    public int getBallCount(){return ballCount;}

    public boolean isBallLost(){return ballLost;}

    public Brick[] getBricks() {return bricks;}

    public Ball getBall() {return ball;}

    public Player getPlayer() {return player;}
}
