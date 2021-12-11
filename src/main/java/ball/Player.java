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

/**
 * Player class which represents the green bar that player are supposed to control
 */
public class Player {

    //CONSTANTS:
    //border color of player bar = darker.darker(green)
    //inner color of player bar = green
    //default move amount of player bar = 5
    public static final Color BORDER_COLOR = Color.GREEN.darker().darker();
    public static final Color INNER_COLOR = Color.GREEN;
    private static final int DEF_MOVE_AMOUNT = 5;

    private Rectangle playerFace;//player bar is rectangle shape
    private Point ballPoint;//point coordinate of ball
    private int moveAmount;
    private int min;
    private int max;

    /**
     * Constructor of Player class
     * initialize and set value for ballpoint, moveAmount, playerFace, min, max
     *
     * @param ballPoint Position of the ball (which is always the startPoint (300, 430))
     * @param width Width of player green bar
     * @param height Height of player green bar
     * @param container Rectangle GameFrame where game is rendered/drawn
     */
    public Player(Point ballPoint,int width,int height,Rectangle container) {
        this.ballPoint = ballPoint;
        stop();
        playerFace = makeRectangle(width, height);
        //min, max is the range which player bar can move
        min = container.x + (width / 2);
        max = min + container.width - width;
    }

    /**
     * Constructs a Rectangle player green bar at default location p(which is based on ballPoint),
     * so that ball is always at the middle of player green bar when program starts
     *
     * @param width Width of player green bar
     * @param height Height of player green bar
     * @return Rectangle shaped player green bar
     */
    //constructing Rectangle player bar at default location p
    public Rectangle makeRectangle(int width,int height){
        Point p = new Point((int)(ballPoint.getX() - (width / 2)),(int)ballPoint.getY());
        return new Rectangle(p,new Dimension(width,height));
    }

    /**
     * move player green bar
     */
    public void move(){
        //check if ball is within range of gameFrame
        //if yes then move the ball and move player bar
        double x = ballPoint.getX() + moveAmount;
        if(x < min || x > max)
            return;
        ballPoint.setLocation(x,ballPoint.getY());
        playerFace.setLocation(ballPoint.x - (int)playerFace.getWidth()/2,ballPoint.y);
    }

    /**
     * move player green bar to specified point P
     * @param p specified location Point p
     */
    public void moveTo(Point p){
        //move ball to point P, then move the player bar
        ballPoint.setLocation(p);
        playerFace.setLocation(ballPoint.x - (int)playerFace.getWidth()/2,ballPoint.y);
    }

    /**
     *returns True if ball impacted player bar (down face of ball touches player bar)
     * @param b ball, b
     * @return boolean True or False
     */
    public boolean impact(Ball b){return playerFace.contains(b.getPosition()) && playerFace.contains(b.getDown()) ;}

    /**
     * move player green bar towards left
     */
    public void moveLeft(){moveAmount = -DEF_MOVE_AMOUNT;}

    /**
     * move player green bar towards right
     */
    public void movRight(){moveAmount = DEF_MOVE_AMOUNT;}

    /**
     * stop player green bar
     */
    public void stop(){moveAmount = 0;}//stop player bar

    /**
     * getter for player green bar
     * @return Rectangle playerFace
     */
    public Shape getPlayerFace(){return playerFace;}
}
