package test;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;


public class CementBrick extends Brick {

    //CONSTANTS:
    //- Name is Cement Brick
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
        //initialize and set value for variable is superclass Brick
        super(NAME,point,size,DEF_BORDER,DEF_INNER,CEMENT_STRENGTH);
        //construct crack based on DEF_CRACK_DEPTH(1), DEF_STEPS(35)
        crack = new Crack(DEF_CRACK_DEPTH,DEF_STEPS);
        //set the brickFace to superclass(Brick).brickFace
        brickFace = super.brickFace;
    }

    //return newly constructed Rectangle as BrickFace
    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos,size);
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

    @Override
    public Shape getBrick() {
        return brickFace;
    }

    //private method updateBrick
    private void updateBrick(){
        //if Brick is not broken
        if(!super.isBroken()){
            //set gp to the crack of Brick
            GeneralPath gp = crack.draw();
            //append the brickFace to the crack of Brick
            gp.append(super.brickFace,false);
            //brickFace is set to crack of Brick
            brickFace = gp;
        }
    }

    public void repair(){
        //set broken back to false
        //set strength back to fullStrength
        super.repair();
        //reset the value for crack
        crack.reset();
        //set brickFace to superclass(Brick).brickFace
        brickFace = super.brickFace;
    }
}
