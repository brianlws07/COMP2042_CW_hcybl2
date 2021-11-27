package test;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;

//abstract class Ball which is to be inherited by Rubber Ball
abstract public class Ball {

    //Outline of the shape of the ball
    private Shape ballFace;

    //Variable of type Point2D stores the (x,y) coordinate of points
    private Point2D center;

    Point2D up;
    Point2D down;
    Point2D left;
    Point2D right;

    //Color of both the border and the inner of the ball
    private Color border;
    private Color inner;

    //Speed of the ball
    private int speedX;
    private int speedY;

    //Constructor of the class Ball
    //initialize and set value for center, up, down, left, right, border, inner, speedX, speedY
    //construct the Ellipse2D of ballFace
    //radius A is the horizontal radius
    //radius B is the vertical radius
    public Ball(Point2D center,int radiusA,int radiusB,Color inner,Color border){
        this.center = center;

        //initialize a point with the coordinates of double precision
        up = new Point2D.Double();
        down = new Point2D.Double();
        left = new Point2D.Double();
        right = new Point2D.Double();

        //set the up and down coordinates on the Y vertical line based on radius B
        up.setLocation(center.getX(),center.getY()-(radiusB / 2));
        down.setLocation(center.getX(),center.getY()+(radiusB / 2));

        //set the left and right coordinates on the X horizontal line based on radius A
        left.setLocation(center.getX()-(radiusA /2),center.getY());
        right.setLocation(center.getX()+(radiusA /2),center.getY());

        //makeBall implementation at subclass Rubber Ball
        ballFace = makeBall(center,radiusA,radiusB);
        this.border = border;
        this.inner  = inner;
        //set value of speedX and speedY to 0
        speedX = 0;
        speedY = 0;
    }

    //makeBall(abstract method) to be implemented by the subclass (RubberBall)
    protected abstract Shape makeBall(Point2D center,int radiusA,int radiusB);

    //method move for the ball
    //set the new Location for ballFace and coordinates(up, down, left, right)
    public void move(){
        //Typecast ballFace to RectangularShape and store it into tmp
        //RectangularShape provides manipulation methods which can be used to modify rectangular frame
        RectangularShape tmp = (RectangularShape) ballFace;
        //reset the location of the ball based on speedX and speedY
        center.setLocation((center.getX() + speedX),(center.getY() + speedY));
        //get the width and height for ballFace frame
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        //set the location and size of ballFace frame
        tmp.setFrame((center.getX() -(w / 2)),(center.getY() - (h / 2)),w,h);
        //set the new coordinates of the ball based on w, h
        setPoints(w,h);

        //Convert tmp of type RectangularShape back to ballFace of type Shape
        //first we surround the circle with a rectangular frame
        //we move the rectangular frame first
        //only then we move the circle
        ballFace = tmp;
    }

    public void setSpeed(int x,int y){
        speedX = x;
        speedY = y;
    }

    public void setXSpeed(int s){
        speedX = s;
    }

    public void setYSpeed(int s){
        speedY = s;
    }

    //set speedX to the left (negative -1)
    public void reverseX(){
        speedX *= -1;
    }

    //set speedY to the upper (negative -1)
    public void reverseY(){
        speedY *= -1;
    }

    public Color getBorderColor(){
        return border;
    }

    public Color getInnerColor(){
        return inner;
    }

    public Point2D getPosition(){
        return center;
    }

    public Shape getBallFace(){
        return ballFace;
    }

    //method moveTo
    //move the ballFace to a specific location based on point P
    public void moveTo(Point p){
        center.setLocation(p);

        RectangularShape tmp = (RectangularShape) ballFace;
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((center.getX() -(w / 2)),(center.getY() - (h / 2)),w,h);
        ballFace = tmp;
    }

    //private method setPoints
    //set the new coordinates for up, down, left, right based on the width and height passed in
    private void setPoints(double width,double height){
        up.setLocation(center.getX(),center.getY()-(height / 2));
        down.setLocation(center.getX(),center.getY()+(height / 2));

        left.setLocation(center.getX()-(width / 2),center.getY());
        right.setLocation(center.getX()+(width / 2),center.getY());
    }

    public int getSpeedX(){
        return speedX;
    }

    public int getSpeedY(){
        return speedY;
    }


}
