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
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class RubberBall extends Ball {

    //CONSTANTS:
    //- radius
    //- inner color
    //- border color
    //radius of rubber ball is 10
    private static final int DEF_RADIUS = 10;
    //the inner color of rubber ball is yellow
    private static final Color DEF_INNER_COLOR = new Color(255, 219, 88);
    //the border color of rubber ball is darker.darker of yellow
    private static final Color DEF_BORDER_COLOR = DEF_INNER_COLOR.darker().darker();

    //constructor of RubberBall where it passes the radius, inner color, border color to the constructor of superclass - ball
    public RubberBall(Point2D center){
        super(center,DEF_RADIUS,DEF_RADIUS,DEF_INNER_COLOR,DEF_BORDER_COLOR);
    }


    //Overriden method makeBall
    //construct an Ellipse2D ball based on radiusA,B
    @Override
    protected Shape makeBall(Point2D center, int radiusA, int radiusB) {

        //x is set to the left of the center by half the horizontal radius
        double x = center.getX() - (radiusA / 2);
        //y is set to the upper of the center by half the vertical radius
        double y = center.getY() - (radiusB / 2);

        //construct an ellipse that is defined by a framing rectangle
        //Ellipse2D.Double defines ellipse specified in Double precision
        //x - X coordinate upper left corner of the framing rectangle
        //y - Y coordinate upper left corner of the framing rectangle
        //radius A - width of framing rectangle
        //radius B - height of framing rectangle
        return new Ellipse2D.Double(x,y,radiusA,radiusB);
    }
}
