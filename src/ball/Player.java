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
package ball;

import ball.Ball;

import java.awt.*;


//Player class represents the green bar below that we are controlling
public class Player {

    //CONSTANTS:
    //border color of player bar = darker.darker(green)
    //inner color of player bar = green
    //default move amount of player bar = 5
    public static final Color BORDER_COLOR = Color.GREEN.darker().darker();
    public static final Color INNER_COLOR = Color.GREEN;
    private static final int DEF_MOVE_AMOUNT = 5;

    //player bar is rectangle shape
    private Rectangle playerFace;
    //point coordinate of ball
    private Point ballPoint;
    private int moveAmount;
    private int min;
    private int max;

    //ballPoint is ball position(300,430)
    //container is gameFrame
    //construct a rectangle player bar
    public Player(Point ballPoint,int width,int height,Rectangle container) {
        this.ballPoint = ballPoint;
        stop();
        playerFace = makeRectangle(width, height);
        //min, max is the range which player bar can move
        min = container.x + (width / 2);
        max = min + container.width - width;
    }

    //constructing Rectangle player bar at default location p
    private Rectangle makeRectangle(int width,int height){
        Point p = new Point((int)(ballPoint.getX() - (width / 2)),(int)ballPoint.getY());//so that ball is in middle of player bar when program starts
        return new Rectangle(p,new Dimension(width,height));
    }

    //move player bar
    public void move(){
        //check if ball is within range of gameFrame
        //if yes then move the ball and move player bar
        double x = ballPoint.getX() + moveAmount;
        if(x < min || x > max)
            return;
        ballPoint.setLocation(x,ballPoint.getY());
        playerFace.setLocation(ballPoint.x - (int)playerFace.getWidth()/2,ballPoint.y);
    }

    //move player bar based on point P
    public void moveTo(Point p){
        //move ball to point P, then move the player bar
        ballPoint.setLocation(p);
        playerFace.setLocation(ballPoint.x - (int)playerFace.getWidth()/2,ballPoint.y);
    }

    //returns True if ball impacted player bar (down face of ball touches player bar)
    public boolean impact(Ball b){return playerFace.contains(b.getPosition()) && playerFace.contains(b.getDown()) ;}

    public void moveLeft(){moveAmount = -DEF_MOVE_AMOUNT;}//move player bar to left
    public void movRight(){moveAmount = DEF_MOVE_AMOUNT;}//move player bar to left
    public void stop(){
        moveAmount = 0;
    }//stop player bar

    public Shape getPlayerFace(){
        return  playerFace;
    }
}
