package brickWall;

import ball.Ball;
import java.awt.*;
import java.awt.Point;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.Random;

/**
 * abstract class Brick which is inherited by subclasses, ClayBrick, CementBrick, SteelBrick, YellowBrick
 */
abstract public class Brick  {

    //CONSTANTS:
    public static final int DEF_CRACK_DEPTH = 1;
    public static final int DEF_STEPS = 35;

    //switch case condition for brick impact
    public static final int UP_IMPACT = 100;
    public static final int DOWN_IMPACT = 200;
    public static final int LEFT_IMPACT = 300;
    public static final int RIGHT_IMPACT = 400;

    //bricks' shape, color, strength, brokenFlag
    private Shape brickFace;
    private Color border;
    private Color inner;
    private int fullStrength;
    private int strength;
    private boolean broken;

    /**
     * constructor of Brick class which initializes and set value for bricks' position, size, color, strength, brokenFlag
     * and also construct Rectangle brick
     *
     * @param pos position of individual brick
     * @param size size of brick
     * @param border border color of brick
     * @param inner inner color of brick
     * @param strength strength of brick
     */
    public Brick(Point pos,Dimension size,Color border,Color inner,int strength){
        broken = false;
        brickFace = makeBrickFace(pos,size);
        this.border = border;
        this.inner = inner;
        this.fullStrength = this.strength = strength;//used to repair brick to original strength
    }

    /**
     * impact brick will decrement brick strength, if strength is 0 then brick is broken(true)
     */
    public void impact(){
        strength--;
        broken = (strength == 0);
    }

    /**
     * if brick is broken return False, then call impact method
     * and return brokenFlag
     *
     * @param point (up, down, left, right) face of ball where it impacts brick
     * @param dir direction of crack
     * @return boolean brokenFlag
     */
    public boolean setImpact(Point2D point , int dir){
        if(broken)
            return false;
        impact();
        return broken;
    }

    /**
     * To find out brick face is impacted in which direction and determine switch case condition for method impactWall
     *
     * @param b ball b
     * @return integer (UP, DOWN, LEFT, RIGHT)_IMPACT which is switch case condition for method impactWall
     */
    public final int findImpact(Ball b){
        if(broken)
            return 0;//if brick is broken, then return 0
        int out  = 0;
        if(brickFace.contains(b.getRight()))//left side of brick is impacted by right side of ball
            out = LEFT_IMPACT;
        else if(brickFace.contains(b.getLeft()))//right side of brick is impacted by left side of ball
            out = RIGHT_IMPACT;
        else if(brickFace.contains(b.getUp()))//down side of brick is impacted by up side of ball
            out = DOWN_IMPACT;
        else if(brickFace.contains(b.getDown()))//up side of brick is impacted by down side of ball
            out = UP_IMPACT;
        return out;//return (UP, DOWN, LEFT, RIGHT)_IMPACT
    }

    /**
     * Restore bricks' brokenFlag, and strength
     */
    public void repair() {
        broken = false;
        strength = fullStrength;
    }

    /**
     * Construct Rectangle shaped brickFace
     * @param pos position of individual brick
     * @param size size of brick
     * @return Rectangle shaped brick
     */
    protected Shape makeBrickFace(Point pos, Dimension size) {return new Rectangle(pos,size);}

    //abstract method (by subclass but same implementation):
    /**
     * abstract method: getter for brickFace
     * @return brickFace
     */
    public abstract Shape getBrick();

    /**
     * getter for brick border color
     * @return border color
     */
    public Color getBorderColor(){return  border;}

    /**
     * getter for brick inner color
     * @return inner color
     */
    public Color getInnerColor(){return inner;}

    /**
     * returns value of brokenFlag
     * @return boolean broken
     */
    public final boolean isBroken(){return broken;}

    /**
     * getter for brickFace
     * @return brickFace
     */
    public Shape getBrickFace() {return brickFace;}

}





