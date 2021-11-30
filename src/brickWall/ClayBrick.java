package brickWall;

import brickWall.Brick;

import java.awt.*;
import java.awt.Point;

public class ClayBrick extends Brick {

    //CONSTANTS:
    //- Name is Clay Brick
    //- Inner Color is Red
    //- Border Color is Gray
    //- Clay Strength is 1
    private static final String NAME = "Clay Brick";
    private static final Color DEF_INNER = new Color(178, 34, 34).darker();
    private static final Color DEF_BORDER = Color.GRAY;
    private static final int CLAY_STRENGTH = 1;

    //constructor of class ClayBrick
    //initialize and set value for variable is superclass Brick
    public ClayBrick(Point point, Dimension size){
        super(NAME,point,size,DEF_BORDER,DEF_INNER,CLAY_STRENGTH);
    }

    //method makeBrickFace
    //return a constructed Rectangle
    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos,size);
    }

    //method getBrick
    //return the brickFace of super class Brick
    @Override
    public Shape getBrick() {
        return super.brickFace;
    }


}
