package brickWall;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.Random;

public class Crack{

    //CONSTANTS:
    //switch case condition for wall impact and crack
    public static final int LEFT = 10;
    public static final int RIGHT = 20;
    public static final int UP = 30;
    public static final int DOWN = 40;
    public static final int VERTICAL = 100;
    public static final int HORIZONTAL = 200;

    //crack is a GeneralPath which represents geometric path constructed from straight lines or curves
    private GeneralPath crack;
    private int crackDepth; //1
    private int steps; //35
    private static Random rnd;

    /**
     * constructor of class Crack
     * initialize and set value for crackPath, crackDepth, steps
     *
     * @param crackDepth default int value of 1
     * @param steps default int value of 35
     */
    public Crack(int crackDepth, int steps){
        crack = new GeneralPath();
        this.crackDepth = crackDepth;
        this.steps = steps;
        rnd = new Random();
    }

    /**
     * first makeCrack(Overload) method
     * To determine the start and end point of where crackPath will be drawn
     * based on position and direction of where ball impacted brickFace
     *
     * @param point (up, down, left, right) face of ball where it impacts brick
     * @param direction direction of crack
     * @param brickFace brickFace where cracks are drawn onto
     */
    protected void makeCrack(Point2D point, int direction, Shape brickFace){
        //bounds is Rectangle that encloses the shape of brickFace
        Rectangle bounds = brickFace.getBounds();

        //impact point is where ball impacted the brick
        Point impact = new Point((int)point.getX(),(int)point.getY());
        Point start = new Point();
        Point end = new Point();

        switch(direction){
            case LEFT:
                //start is set to upper right corner of Rectangle bound
                start.setLocation(bounds.x + bounds.width, bounds.y);
                //end is set to lower right corner of Rectangle bound
                end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                Point tmp = makeRandomPoint(start,end,VERTICAL);
                makeCrack(impact,tmp);

                break;
            case RIGHT:
                //start is set to the upper left corner of Rectangle bound
                start.setLocation(bounds.getLocation());
                //end is set to the lower left corner of Rectangle bound
                end.setLocation(bounds.x, bounds.y + bounds.height);
                tmp = makeRandomPoint(start,end,VERTICAL);
                makeCrack(impact,tmp);

                break;
            case UP:
                //start is set to the lower left corner of Rectangle bound
                start.setLocation(bounds.x, bounds.y + bounds.height);
                //end is set to the lower right corner of Rectangle bound
                end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                tmp = makeRandomPoint(start,end,HORIZONTAL);
                makeCrack(impact,tmp);
                break;
            case DOWN:
                //start is set to the upper left corner of Rectangle bound
                start.setLocation(bounds.getLocation());
                //end is set to the upper right corner of Rectangle bound
                end.setLocation(bounds.x + bounds.width, bounds.y);
                tmp = makeRandomPoint(start,end,HORIZONTAL);
                makeCrack(impact,tmp);

                break;

        }
    }

    /**
     * second makeCrack(Overload method)
     * draw crackPath on brickFace from specified start point to end point
     *
     * @param start location of brick where it is impacted by ball
     * @param end a random end point for crackPath
     */
    protected void makeCrack(Point start, Point end){

        //path starts from location of brick where it is impacted by ball(start)
        GeneralPath path = new GeneralPath();
        path.moveTo(start.x,start.y);

        double w = divideSteps(end.x, start.x);
        double h = divideSteps(end.y, start.y);

        int bound = crackDepth; //1

        double x,y;

        for(int i = 1; i < steps;i++){

            //x = w * i from start.x
            x = (i * w) + start.x;
            //y = h * i from start.y + (random number between 0 and 1)
            y = (i * h) + start.y + randomInBounds(bound); //bound is 1

            //connect path to new (x, y)
            path.lineTo(x,y);

        }
        //connect path to the end(x, y)
        path.lineTo(end.x,end.y);
        //connect the new path to crack path
        crack.append(path,true);
    }

    /**
     * to find width and height of crack
     *
     * @param a x, y coordinate of end point
     * @param b x, y coordinate of start point
     * @return distance between start and end point
     */
    private double divideSteps(double a, double b){
        return (a - b) / (double)steps;
    }

    /**
     * return a random number
     * @param bound int value of 1
     * @return random number between 0 and 1
     */
    private int randomInBounds(int bound){ //bound is 1
        int n = (bound * 2) + 1; //3
        return rnd.nextInt(n) - bound;
    }

    /**
     * return a random coordinate along x-axis or y-axis,
     * which acts as end point for crackPath
     *
     * @param from start point
     * @param to end point
     * @param direction Horizontal or Vertical
     * @return random coordinate
     */
    private Point makeRandomPoint(Point from,Point to, int direction){

        Point out = new Point();
        int pos;

        switch(direction){
            case HORIZONTAL:
                //return random coordinate along x-axis
                pos = rnd.nextInt(to.x - from.x) + from.x;
                out.setLocation(pos,to.y);
                break;
            case VERTICAL:
                //return random coordinate along y-axis
                pos = rnd.nextInt(to.y - from.y) + from.y;
                out.setLocation(to.x,pos);
                break;
        }
        return out;
    }

    /**
     * getter for crackPath
     * @return crackPath
     */
    public GeneralPath draw(){return crack;}

    /**
     * remove crackPath in bricks
     */
    public void reset(){crack.reset();}
}
