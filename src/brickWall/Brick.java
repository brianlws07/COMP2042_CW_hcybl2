package brickWall;

import ball.Ball;

import java.awt.*;
import java.awt.Point;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.Random;

abstract public class Brick  {

    //CONSTANTS:
    public static final int DEF_CRACK_DEPTH = 1;
    public static final int DEF_STEPS = 35;

    //switch case condition for brick impact
    public static final int UP_IMPACT = 100;
    public static final int DOWN_IMPACT = 200;
    public static final int LEFT_IMPACT = 300;
    public static final int RIGHT_IMPACT = 400;

    //private String name;// name is assigned but never accessed
    private Shape brickFace;

    private Color border;
    private Color inner;

    private int fullStrength;
    private int strength;

    private boolean broken;

    //constructor of Brick class
    public Brick(Point pos,Dimension size,Color border,Color inner,int strength){
        //rnd = new Random();//used in crack class
        broken = false;
        //this.name = name;
        brickFace = makeBrickFace(pos,size);//construct rectangle Brick
        this.border = border;//border color
        this.inner = inner;//inner color
        this.fullStrength = this.strength = strength;//used to repair brick to original strength
    }

    //impact brick will decrement brick strength, if strength is 0 then brick is broken(true)
    public void impact(){
        strength--;
        broken = (strength == 0);
    }

    //if brick is broken return True, else return False
    public boolean setImpact(Point2D point , int dir){
        if(broken)
            return false;
        impact();
        return  broken;
    }

    //output is switch case condition for impact wall
    public final int findImpact(Ball b){
        //if brick is broken, then return 0
        if(broken)
            return 0;
        int out  = 0;
        //left side of brick is impacted by right side of ball
        if(brickFace.contains(b.getRight()))
            out = LEFT_IMPACT;
        //right side of brick is impacted by left side of ball
        else if(brickFace.contains(b.getLeft()))
            out = RIGHT_IMPACT;
        //down side of brick is impacted by up side of ball
        else if(brickFace.contains(b.getUp()))
            out = DOWN_IMPACT;
        //up side of brick is impacted by down side of ball
        else if(brickFace.contains(b.getDown()))
            out = UP_IMPACT;
        //return out
        return out;
    }

    //restore bricks's(broken, strength)
    public void repair() {
        broken = false;
        strength = fullStrength;
    }

    protected Shape makeBrickFace(Point pos, Dimension size) {return new Rectangle(pos,size);}//return a constructed Rectangle
    //abstract method (by subclass but same implementation):
    public abstract Shape getBrick();

    public Color getBorderColor(){
        return  border;
    }

    public Color getInnerColor(){return inner;}

    public final boolean isBroken(){
        return broken;
    }

    public Shape getBrickFace() {return brickFace;}

    /*
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

        //constructor of class Crack
        //initialize and set value for crack, crackdepth, steps
        public Crack(int crackDepth, int steps){
            crack = new GeneralPath();
            this.crackDepth = crackDepth;
            this.steps = steps;
        }

        protected void makeCrack(Point2D point, int direction){
            //bounds is Rectangle that encloses the shape of brickFace
            Rectangle bounds = Brick.this.brickFace.getBounds();

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

        //this makeCrack is an Overloading method(same name, different parameter)
        //start - brick impact point with ball, end - (random) end point of crack
        protected void makeCrack(Point start, Point end){

            //path starts from brick impact point(start)
            GeneralPath path = new GeneralPath();
            path.moveTo(start.x,start.y);

            double w = divideSteps(end.x, start.x);
            double h = divideSteps(end.y, start.y);
            //x distance from start to end divided by 35
            //double w = (end.x - start.x) / (double)steps;
            //y distance from start to end divided by 35
            //double h = (end.y - start.y) / (double)steps;

            int bound = crackDepth; //1
            int jump  = bound * 5; //5

            double x,y;

            for(int i = 1; i < steps;i++){

                //x = w * i from start.x
                x = (i * w) + start.x;
                //y = h * i from start.y + (random number between 0 and 1)
                y = (i * h) + start.y + randomInBounds(bound); //bound is 1


                //to check whether i is larger than low & lower than up
                //y is set to y + random number based on jump
                //if(inMiddle(i,CRACK_SECTIONS,steps)) //CRACK_SECTIONS = 3, steps = 35
                    //y += jumps(jump,JUMP_PROBABILITY); //jump = 5, jump probability = 0.7




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

        //private boolean inMiddle(int i,int steps,int divisions){ //steps = 3, divisions = 35
        //    int low = (steps / divisions); // 3/35
        //    int up = low * (divisions - 1); // 102/35
        //    //check if i is between 3/35 and 102/35
        //    return  (i > low) && (i < up);
        //}

        //private int jumps(int bound,double probability){//bound = 5, jump probability = 0.7
        //    //if generated double number is larger than 0.7,
        //    //then return random number between 0 and 5
        //    if(rnd.nextDouble() > probability)
        //        return randomInBounds(bound);
        //    return  0;
        //}

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


     */

}





