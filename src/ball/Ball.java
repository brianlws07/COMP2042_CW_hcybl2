package ball;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;

//abstract class Ball which is to be inherited by Rubber Ball
abstract public class Ball {

    //outline of ballface
    private Shape ballFace;

    //center, up, down, left, right coordinate of ball
    private Point2D center;
    private Point2D up;
    private Point2D down;
    private Point2D left;
    private Point2D right;

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

        //initialize and set value for up, down, left, right coordinate of ball
        up = new Point2D.Double();
        down = new Point2D.Double();
        left = new Point2D.Double();
        right = new Point2D.Double();
        up.setLocation(center.getX(),center.getY()-(radiusB / 2));
        down.setLocation(center.getX(),center.getY()+(radiusB / 2));
        left.setLocation(center.getX()-(radiusA /2),center.getY());
        right.setLocation(center.getX()+(radiusA /2),center.getY());

        //return an ellipse ball
        ballFace = makeBall(center,radiusA,radiusB);
        //set border, inner color and speed of ball
        this.border = border;
        this.inner  = inner;
        speedX = 0;
        speedY = 0;
    }

    //makeBall(abstract method) to be implemented by the subclass (RubberBall)
    protected abstract Shape makeBall(Point2D center,int radiusA,int radiusB);

    //move the ball to new location based on speedX and speedY
    public void move(){
        //Typecast ballFace to Rectangular frame
        //reset center coordinate of ball with speedX and speedY
        RectangularShape tmp = (RectangularShape) ballFace;
        center.setLocation((center.getX() + speedX),(center.getY() + speedY));

        //w is width, h is height of rectangular frame
        //move rectangular frame to new location
        //reset up, down, left, right coordinate of ball
        double w = tmp.getWidth();
        double h = tmp.getHeight();
        tmp.setFrame((center.getX() -(w / 2)),(center.getY() - (h / 2)),w,h);
        setPoints(w,h);

        //first we surround the circle with a rectangular frame
        //we move the rectangular frame first
        //only then we move the circle
        ballFace = tmp;
    }

    //move the ballFace to point P
    public void moveTo(Point p){
        center.setLocation(p);
        RectangularShape tmp = (RectangularShape) ballFace;
        double w = tmp.getWidth();
        double h = tmp.getHeight();
        tmp.setFrame((center.getX() -(w / 2)),(center.getY() - (h / 2)),w,h);
        ballFace = tmp;
    }

    //reset up, down, left, right coordinate of ball based on width and height of rectangular frame
    private void setPoints(double width,double height){
        up.setLocation(center.getX(),center.getY()-(height / 2));
        down.setLocation(center.getX(),center.getY()+(height / 2));
        left.setLocation(center.getX()-(width / 2),center.getY());
        right.setLocation(center.getX()+(width / 2),center.getY());
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

    public void reverseX(){
        speedX *= -1;
    }//set ball speed to left

    public void reverseY(){
        speedY *= -1;
    }//set ball speed to up

    public Color getBorderColor(){
        return border;
    }

    public Color getInnerColor(){
        return inner;
    }

    public Point2D getPosition(){return center;}

    public Shape getBallFace(){
        return ballFace;
    }

    public int getSpeedX(){
        return speedX;
    }

    public int getSpeedY(){
        return speedY;
    }

    public Point2D getUp() {
        return up;
    }

    public void setUp(Point2D up) {
        this.up = up;
    }

    public Point2D getDown() {
        return down;
    }

    public void setDown(Point2D down) {
        this.down = down;
    }

    public Point2D getLeft() {
        return left;
    }

    public void setLeft(Point2D left) {
        this.left = left;
    }

    public Point2D getRight() {
        return right;
    }

    public void setRight(Point2D right) {
        this.right = right;
    }
}
