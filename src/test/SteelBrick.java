/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2017  Filippo Ranza
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package test;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;


public class SteelBrick extends Brick {

    //CONSTANTS:
    //- Name is Steel Brick
    //- Inner Color is Light Gray
    //- Border Color is Black
    //- Steel Strength is 1
    //- Steel Probability is 0.4
    private static final String NAME = "Steel Brick";
    private static final Color DEF_INNER = new Color(203, 203, 201);
    private static final Color DEF_BORDER = Color.BLACK;
    private static final int STEEL_STRENGTH = 1;
    private static final double STEEL_PROBABILITY = 0.4;

    private Random rnd;
    private Shape brickFace;

    //constructor of class SteelBrick
    public SteelBrick(Point point, Dimension size){
        //initialize and set value for variable is superclass Brick
        super(NAME,point,size,DEF_BORDER,DEF_INNER,STEEL_STRENGTH);
        //initialize rnd (random number generator)
        rnd = new Random();
        //set the brickFace to superclass(Brick).brickFace
        brickFace = super.brickFace;
    }

    //return newly constructed Rectangle as BrickFace
    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos,size);
    }

    @Override
    public Shape getBrick() {
        return brickFace;
    }

    public  boolean setImpact(Point2D point , int dir){
        //if Brick is broken, then return false
        if(super.isBroken())
            return false;
        //decrement strength
        //set broken to true if strength is 0, false if not
        impact();
        //return boolean value of broken
        return  super.isBroken();
    }

    public void impact(){
        //if the random generated double is less than steel probability
        if(rnd.nextDouble() < STEEL_PROBABILITY){
            //decrement strength
            //set broken to true if strength is 0, false if not
            super.impact();
        }
    }

}
