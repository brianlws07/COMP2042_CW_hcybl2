package brickWall;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.Random;

public class Crack{

    //CONSTANTS:
    private static final int CRACK_SECTIONS = 3;
    private static final double JUMP_PROBABILITY = 0.7;
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

    //constructor of class Crack
    //initialize and set value for crack, crackdepth, steps
    public Crack(int crackDepth, int steps){
        crack = new GeneralPath();
        this.crackDepth = crackDepth;
        this.steps = steps;
        rnd = new Random();
    }

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
                makeCrack(impact,tmp,brickFace);

                break;
            case RIGHT:
                //start is set to the upper left corner of Rectangle bound
                start.setLocation(bounds.getLocation());
                //end is set to the lower left corner of Rectangle bound
                end.setLocation(bounds.x, bounds.y + bounds.height);
                tmp = makeRandomPoint(start,end,VERTICAL);
                makeCrack(impact,tmp,brickFace);

                break;
            case UP:
                //start is set to the lower left corner of Rectangle bound
                start.setLocation(bounds.x, bounds.y + bounds.height);
                //end is set to the lower right corner of Rectangle bound
                end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                tmp = makeRandomPoint(start,end,HORIZONTAL);
                makeCrack(impact,tmp,brickFace);
                break;
            case DOWN:
                //start is set to the upper left corner of Rectangle bound
                start.setLocation(bounds.getLocation());
                //end is set to the upper right corner of Rectangle bound
                end.setLocation(bounds.x + bounds.width, bounds.y);
                tmp = makeRandomPoint(start,end,HORIZONTAL);
                makeCrack(impact,tmp,brickFace);

                break;

        }
    }

    //this makeCrack is an Overloading method(same name, different parameter)
    //start - brick impact point with ball, end - (random) end point of crack
    protected void makeCrack(Point start, Point end, Shape brickFace){

        //path starts from brick impact point(start)
        GeneralPath path = new GeneralPath();
        path.moveTo(start.x,start.y);

        double w = divideSteps(end.x, start.x);
        double h = divideSteps(end.y, start.y);
            /*//x distance from start to end divided by 35
            double w = (end.x - start.x) / (double)steps;
            //y distance from start to end divided by 35
            double h = (end.y - start.y) / (double)steps;*/

        int bound = crackDepth; //1
        int jump  = bound * 5; //5

        double x,y;

        for(int i = 1; i < steps;i++){

            //x = w * i from start.x
            x = (i * w) + start.x;
            //y = h * i from start.y + (random number between 0 and 1)
            y = (i * h) + start.y + randomInBounds(bound); //bound is 1

                /*
                //to check whether i is larger than low & lower than up
                //y is set to y + random number based on jump
                if(inMiddle(i,CRACK_SECTIONS,steps)) //CRACK_SECTIONS = 3, steps = 35
                    y += jumps(jump,JUMP_PROBABILITY); //jump = 5, jump probability = 0.7
                 */

            //connect path to new (x, y)
            path.lineTo(x,y);

        }
        //connect path to the end(x, y)
        path.lineTo(end.x,end.y);
        //connect the new path to crack path
        crack.append(path,true);
    }

    private double divideSteps(double a, double b){
        return (a - b) / (double)steps;
    }

    //return a random number between 0 and 1
    private int randomInBounds(int bound){ //bound is 1
        int n = (bound * 2) + 1; //3
        return rnd.nextInt(n) - bound;
    }

        /*
        private boolean inMiddle(int i,int steps,int divisions){ //steps = 3, divisions = 35
            int low = (steps / divisions); // 3/35
            int up = low * (divisions - 1); // 102/35
            //check if i is between 3/35 and 102/35
            return  (i > low) && (i < up);
        }

        private int jumps(int bound,double probability){//bound = 5, jump probability = 0.7
            //if generated double number is larger than 0.7,
            //then return random number between 0 and 5
            if(rnd.nextDouble() > probability)
                return randomInBounds(bound);
            return  0;
        }

         */

    //private method makeRandomPoint
    //from - start, to - end, direction - HORIZONTAL, VERTICAL
    //return random coordinate as end point of crack
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

    //returns crack path
    public GeneralPath draw(){return crack;}

    //remove crack path in bricks
    public void reset(){crack.reset();}
}
