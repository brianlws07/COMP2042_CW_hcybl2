package brickWall;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

/**
 * YellowBrick class which inherits from superclass, Brick
 */
public class YellowBrick extends Brick {

    //CONSTANTS:
    //- Inner Color is Yellow
    //- Border Color is Brownish Orange
    //- Yellow Strength is 2
    private static final Color DEF_INNER = Color.yellow;
    private static final Color DEF_BORDER = new Color(255, 128, 0);
    private static final int CEMENT_STRENGTH = 3;

    private Crack crack;
    private Shape brickFace;

    /**
     * constructor of class YellowBrick which set value for bricks' position, size, color, strength, brokenFlag in superclass, Brick
     * and set value for cracks' depth, steps
     *
     * @param point position of individual brick
     * @param size size of brick
     */
    public YellowBrick(Point point, Dimension size){
        super(point,size,DEF_BORDER,DEF_INNER,CEMENT_STRENGTH);
        crack = new Crack(DEF_CRACK_DEPTH,DEF_STEPS); //DEF_CRACK_DEPTH = 1, DEF_STEPS = 35
        brickFace = super.getBrickFace();
    }

    /**
     * update brick by drawing crack on brickFace
     */
    private void updateBrick(){
        //if Brick is not broken, draw crack on brick face
        if(!super.isBroken()){
            GeneralPath gp = crack.draw();
            gp.append(super.getBrickFace(),false);
            brickFace = gp;
        }
    }

    /**
     * Restore bricks' brokenFlag, strength, and remove crack on brickFace
     */
    public void repair(){
        super.repair();
        crack.reset();
        brickFace = super.getBrickFace();
    }

    /**
     * Overriden method from superclass, Brick
     * if brick is broken return False, then call impact method
     * if brick is still not broken, then draw crack on brickFace
     *
     * @param point (up, down, left, right) face of ball where it impacts brick
     * @param dir direction of crack
     * @return boolean True or False
     */
    @Override
    public boolean setImpact(Point2D point, int dir) {
        //if Brick is broken, then return false
        if(super.isBroken())
            return false;
        //decrement strength, and set value for broken based on strength
        super.impact();
        //if brick is not broken
        if(!super.isBroken()){
            //make Crack on the Brick
            crack.makeCrack(point,dir,brickFace);
            updateBrick();
            return false;
        }
        return true;
    }

    /**
     * Overriden method from superclass, Brick
     * getter for brickFace
     * @return brickFace
     */
    @Override
    public Shape getBrick() {return brickFace;}
}
