package brickWall;

import ball.Ball;

import java.awt.*;
import java.awt.Point;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.Random;

abstract public class Brick  {

    //CONSTANTS:
    public static final int MIN_CRACK = 1;//(not used)
    public static final int DEF_CRACK_DEPTH = 1;
    public static final int DEF_STEPS = 35;

    //switch case condition for brick impact
    public static final int UP_IMPACT = 100;
    public static final int DOWN_IMPACT = 200;
    public static final int LEFT_IMPACT = 300;
    public static final int RIGHT_IMPACT = 400;


    private static Random rnd;

    private String name;// name is assigned but never accessed
    Shape brickFace;

    private Color border;
    private Color inner;

    private int fullStrength;
    private int strength;

    private boolean broken;

    //constructor of Brick class
    public Brick(String name, Point pos,Dimension size,Color border,Color inner,int strength){
        //initialize and set value for rnd, broken, name
        rnd = new Random();
        broken = false;
        this.name = name;
        //make Brick Face based on Point pos and Dimension size of subclass
        brickFace = makeBrickFace(pos,size);
        //set the values for border, inner, strength
        this.border = border;
        this.inner = inner;
        this.fullStrength = this.strength = strength;
    }

    //protected abstract method makeBrickFace to be implemented by subclasses
    //make Brick Face based on Point pos and Dimension size of subclass
    protected abstract Shape makeBrickFace(Point pos,Dimension size);

    //method setImpact return boolean
    public  boolean setImpact(Point2D point , int dir){
        //if broken is true, then return false
        if(broken)
            return false;
        //impact() set broken to true if strength = 0
        impact();
        //return broken
        return  broken;
    }

    //abstract method getBrick to be implemented by subclasses
    public abstract Shape getBrick();

    public Color getBorderColor(){
        return  border;
    }

    public Color getInnerColor(){
        return inner;
    }


    public final int findImpact(Ball b){
        //if brick is broken, then return 0
        if(broken)
            return 0;
        //initialize out to 0
        int out  = 0;
        //if ball.right is in the boundary of brickFace
        //set out to LEFT_IMPACT
        if(brickFace.contains(b.getRight()))
            out = LEFT_IMPACT;
            //if ball.left is in the boundary of brickFace
            //set out to RIGHT_IMPACT
        else if(brickFace.contains(b.getLeft()))
            out = RIGHT_IMPACT;
            //if ball.up is in the boundary of brickFace
            //set out to DOWN_IMPACT
        else if(brickFace.contains(b.getUp()))
            out = DOWN_IMPACT;
            //if ball.down is in the boundary of brickFace
            //set out to UP_IMPACT
        else if(brickFace.contains(b.getDown()))
            out = UP_IMPACT;
        //return out
        return out;
    }

    public final boolean isBroken(){
        return broken;
    }

    //method repair
    public void repair() {
        //set broken back to false
        //set strength back to fullStrength
        broken = false;
        strength = fullStrength;
    }

    public void impact(){
        //decrement strength
        strength--;
        //set broken to true if strength is 0, false if not
        broken = (strength == 0);
    }

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
        private int crackDepth;//1
        private int steps;//35

        //constructor of class Crack
        //initialize and set value for crack, crackdepth, steps
        public Crack(int crackDepth, int steps){
            crack = new GeneralPath();
            this.crackDepth = crackDepth;
            this.steps = steps;
        }

        //returns crack path
        public GeneralPath draw(){
            return crack;
        }

        //reset crack path to empty
        public void reset(){
            crack.reset();
        }

        //protected method makeCrack (can only be accessed by subclass)
        protected void makeCrack(Point2D point, int direction){
            //set the bounds to the bounds of brickFace
            //getBounds returns Rectangle that completely encloses the shape of brickFace
            Rectangle bounds = Brick.this.brickFace.getBounds();

            //set coordinate of impact to point(x, y)
            Point impact = new Point((int)point.getX(),(int)point.getY());
            //initialize start and end as type Point
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

        //protected method makeCrack (can only be accessed by subclass)
        //this makeCrack is an Overloading method(same name, different parameter)
        //start - impact
        //end - tmp
        protected void makeCrack(Point start, Point end){

            //initialize path as type GeneralPath
            GeneralPath path = new GeneralPath();

            //Adds a point to the path by moving to the coordinates of start
            path.moveTo(start.x,start.y);

            //w is steps taken for the x distance from start to end
            double w = (end.x - start.x) / (double)steps;
            //h is steps taken for the y distance from start to end
            double h = (end.y - start.y) / (double)steps;

            //bound is set to value of crackDepth
            int bound = crackDepth;
            //jump is set to value of bound * 5
            int jump  = bound * 5;

            double x,y;

            for(int i = 1; i < steps;i++){

                //x = (steps taken w) * i from start.x
                x = (i * w) + start.x;
                //y = (steps taken h) * i from start.y + random bound number
                y = (i * h) + start.y + randomInBounds(bound);

                //CRACK_SECTIONS = 5
                //to check whether i is larger than low & lower than up
                //y is set to y + random number based on jump
                if(inMiddle(i,CRACK_SECTIONS,steps))
                    y += jumps(jump,JUMP_PROBABILITY);

                //draw a line from original place to the (x, y) coordinates
                path.lineTo(x,y);

            }

            //draw a line from original place to the end(x, y) coordinates
            path.lineTo(end.x,end.y);
            //append the Shape to the path
            crack.append(path,true);
        }

        //private method randomInBounds
        //return a random number based on bound
        private int randomInBounds(int bound){
            //bound of nextInt, n is set to (bound*2)+1
            int n = (bound * 2) + 1;
            //return random number from bound to bound*2(inclusive)
            return rnd.nextInt(n) - bound;
        }

        //private method inMiddle
        //steps - CRACK_SECTIONS
        //divisions - steps
        //to check whether i is larger than low & lower than up
        private boolean inMiddle(int i,int steps,int divisions){
            //low is the steps taken for CRACK_SECTION
            int low = (steps / divisions);
            //up is low * (steps-1)
            int up = low * (divisions - 1);
            //return true if:
            //(1) i is larger than low
            //(2) i lower than up
            return  (i > low) && (i < up);
        }

        //private method jumps
        //bound - jump
        //probability - JUMP_PROBABILITY
        private int jumps(int bound,double probability){
            //if generated double number is larger than probability
            //return random number based on bound
            if(rnd.nextDouble() > probability)
                return randomInBounds(bound);
            return  0;

        }

        //private method makeRandomPoint
        //from - start
        //to - end
        //direction - HORIZONTAL, VERTICAL
        //return random coordinate for tmp in makeCrack()
        private Point makeRandomPoint(Point from,Point to, int direction){

            //initialize out of type Point
            Point out = new Point();
            //position of type int
            int pos;

            switch(direction){
                case HORIZONTAL:
                    //position is set to a random location between start.x to end.x
                    pos = rnd.nextInt(to.x - from.x) + from.x;
                    //out is set to random coordinate along x-axis
                    out.setLocation(pos,to.y);
                    break;
                case VERTICAL:
                    //position is set to a random location between start.y to end.y
                    pos = rnd.nextInt(to.y - from.y) + from.y;
                    //out is set to random coordinate along y-axis
                    out.setLocation(to.x,pos);
                    break;
            }
            //return out
            return out;
        }

    }


}





