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
package test;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;


//Player class represents the green bar below that we are controlling
public class Player {

    //CONSTANTS:
    //- border color
    //- inner color
    //- move amount
    //the border color of ball is darker.darker green
    public static final Color BORDER_COLOR = Color.GREEN.darker().darker();
    //the inner color of ball is green
    public static final Color INNER_COLOR = Color.GREEN;
    //the default move amount for the ball is 5
    private static final int DEF_MOVE_AMOUNT = 5;

    //Rectangle is an area in the coordinates space that is enclosed by the Rectangle object's upper left corner(x, y)
    //x - width
    //y - height
    private Rectangle playerFace;
    //Point - representing a point in the coordinate space (x, y)
    private Point ballPoint;
    private int moveAmount;
    private int min;
    private int max;

    //constructor of class Player
    //set value for ballPoint, moveAmount, min, max
    //construct Rectangle playerFace
    public Player(Point ballPoint,int width,int height,Rectangle container) {
        this.ballPoint = ballPoint;
        moveAmount = 0;
        //construct playerFace as a Rectangle specified with (width, height)
        playerFace = makeRectangle(width, height);
        //min is x coordinate of container + half of width (which is to the right)
        min = container.x + (width / 2);
        //max is min + width of container - width
        max = min + container.width - width;
    }

    //private method makeRectangle
    //constructing Rectangle for playerFace
    private Rectangle makeRectangle(int width,int height){
        //initialize Point P(x, y) where x is x of ball point minus half of width, and y is y of ball point
        Point p = new Point((int)(ballPoint.getX() - (width / 2)),(int)ballPoint.getY());
        //construct a Rectangle where upper left corner is specified by Point p
        //whereas width and height is specified by argument Dimension
        return  new Rectangle(p,new Dimension(width,height));
    }

    //method impact
    //test impact of ball that returns boolean
    //to test 2 cases:
    //(1) - whether Point2D coordinates of Ball b is within boundary of playerFace
    //(2) - whether Point2D down coordinate of Ball b is within boundary of playerFace
    //return True if satisfies both cases
    public boolean impact(Ball b){
        return playerFace.contains(b.getPosition()) && playerFace.contains(b.down) ;
    }

    //method move
    //setLocation for ballPoint, playerFace
    public void move(){
        //x = x coordinate of ballPoint + moveAmount(5)
        double x = ballPoint.getX() + moveAmount;
        //if x is less than min (or) more than max
        //then return
        if(x < min || x > max)
            return;
        //else
        //set x coordinate of ballPoint to x
        //Point.setLocation(double x, double y) hence ballPoint.getY()
        ballPoint.setLocation(x,ballPoint.getY());
        //move Rectangle playerFace to specified location of (x, y)
        //x - (x coordinate of ballPoint) - (half of the width of playerFace) (to the left)
        //y - y coordinate of ballPoint
        //Rectangle.setLocation(int x, int y) hence ballPoint.y
        playerFace.setLocation(ballPoint.x - (int)playerFace.getWidth()/2,ballPoint.y);
    }

    //negative value so move towards left
    public void moveLeft(){
        moveAmount = -DEF_MOVE_AMOUNT;
    }
    //positive value so move towards left
    public void movRight(){
        moveAmount = DEF_MOVE_AMOUNT;
    }
    //moveAmount = 0
    public void stop(){
        moveAmount = 0;
    }

    public Shape getPlayerFace(){
        return  playerFace;
    }

    //method move
    //setLocation for ballPoint, playerFace based on Point p
    public void moveTo(Point p){
        //set ballPoint to specified coordinate of P
        ballPoint.setLocation(p);
        //move Rectangle playerFace to specified location of (x, y)
        //x - (x coordinate of ballPoint) - (half of the width of playerFace) (to the left)
        //y - y coordinate of ballPoint
        playerFace.setLocation(ballPoint.x - (int)playerFace.getWidth()/2,ballPoint.y);
    }
}
