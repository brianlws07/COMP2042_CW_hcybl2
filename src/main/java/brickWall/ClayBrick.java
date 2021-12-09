package brickWall;

import java.awt.*;
import java.awt.Point;

/**
 * ClayBrick class which inherits from superclass, Brick
 */
public class ClayBrick extends Brick {

    //CONSTANTS:
    //Inner Color = Dark Red
    //Border Color = Gray
    //Brick Strength = 1
    private static final Color DEF_INNER = new Color(178, 34, 34).darker();
    private static final Color DEF_BORDER = Color.GRAY;
    private static final int CLAY_STRENGTH = 1;

    private Shape brickFace;

    /**
     * constructor of class ClayBrick which set value for bricks' position, size, color, strength, brokenFlag in superclass, Brick
     * @param point position of individual brick
     * @param size size of brick
     */
    public ClayBrick(Point point, Dimension size){
        super(point,size,DEF_BORDER,DEF_INNER,CLAY_STRENGTH);
        brickFace = super.getBrickFace();
    }

    /**
     * Overriden method from superclass, Brick
     * getter for brickFace
     * @return brickFace
     */
    @Override
    public Shape getBrick() {return brickFace;}


}
