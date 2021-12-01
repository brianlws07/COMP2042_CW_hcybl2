package brickWall;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;


public class CementBrick extends Brick {

    //CONSTANTS:
    //- Inner Color is Grey
    //- Border Color is Cream White
    //- Clay Strength is 2
    private static final String NAME = "Cement Brick";
    private static final Color DEF_INNER = new Color(147, 147, 147);
    private static final Color DEF_BORDER = new Color(217, 199, 175);
    private static final int CEMENT_STRENGTH = 2;

    private Crack crack;
    private Shape brickFace;

    //constructor of class CementBrick
    public CementBrick(Point point, Dimension size){
        super(NAME,point,size,DEF_BORDER,DEF_INNER,CEMENT_STRENGTH);
        crack = new Crack(DEF_CRACK_DEPTH,DEF_STEPS); //DEF_CRACK_DEPTH = 1, DEF_STEPS = 35
        brickFace = super.getBrickFace();
    }

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
            crack.makeCrack(point,dir);
            updateBrick();
            return false;
        }
        return true;
    }

    //update brick with crack
    private void updateBrick(){
        //if Brick is not broken
        if(!super.isBroken()){
            //draw crack on brick face
            GeneralPath gp = crack.draw();
            gp.append(super.getBrickFace(),false);
            brickFace = gp;
        }
    }

    public void repair(){
        //restore bricks's(broken, strength)
        super.repair();
        //remove crack path in bricks
        crack.reset();
        //set brickFace to superclass(Brick).brickFace
        brickFace = super.getBrickFace();
    }

    /*//overriden abstract method
    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {return new Rectangle(pos,size);}//return newly constructed Rectangle*/
    @Override
    public Shape getBrick() {return brickFace;}//return the brickFace of super class Brick
}
