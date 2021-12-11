package brickWall;

import ball.RubberBall;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.*;

class CementBrickTest {
    private static final Color DEF_INNER = new Color(147, 147, 147);
    private static final Color DEF_BORDER = new Color(217, 199, 175);
    private static final int CEMENT_STRENGTH = 2;
    private final Point point = new Point(200,400);
    private final Dimension size = new Dimension(450, 300);
    CementBrick cementBrick = new CementBrick(point, size);

    public static final int LEFT_IMPACT = 300;
    public static final int RIGHT = 10;

    private final Point2D center = new Point2D.Double(300, 430 );
    RubberBall rubberBall = new RubberBall(center);

    @Test
    void impact() {
        cementBrick.impact();
        assertFalse(cementBrick.isBroken());
    }

    @Test
    void findImpact() {
        cementBrick.impact();
        assertEquals(LEFT_IMPACT, cementBrick.findImpact(rubberBall));
    }

    @Test
    void makeBrickFace() {
        assertEquals(new Rectangle(new Point(200,400),new Dimension(450, 300)), cementBrick.makeBrickFace(point, size));
    }

    @Test
    void getBorderColor() {
        assertEquals(DEF_BORDER, cementBrick.getBorderColor());
    }

    @Test
    void getInnerColor() {
        assertEquals(DEF_INNER, cementBrick.getInnerColor());
    }

    @Test
    void isBroken() {
        assertFalse(cementBrick.isBroken());
    }

    @Test
    void getBrickFace() {
        assertEquals(new Rectangle(new Point(200,400),new Dimension(450, 300)), cementBrick.getBrickFace());
    }

    @Test
    void testRepair() {
        cementBrick.repair();
        assertFalse(cementBrick.isBroken());
    }

    @Test
    void testSetImpact() {
        assertFalse(cementBrick.setImpact(point , RIGHT));
    }

    @Test
    void getBrick() {
        assertEquals(new Rectangle(new Point(200,400),new Dimension(450, 300)), cementBrick.getBrickFace());
    }
}