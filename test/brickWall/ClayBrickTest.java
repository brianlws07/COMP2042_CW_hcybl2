package brickWall;

import ball.RubberBall;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.*;

class ClayBrickTest {

    private static final Color DEF_INNER = new Color(178, 34, 34).darker();
    private static final Color DEF_BORDER = Color.GRAY;
    private static final int CLAY_STRENGTH = 1;

    public static final int LEFT = 10;

    private Point point = new Point(200,400);
    private Dimension size = new Dimension(450, 300);
    ClayBrick clayBrick = new ClayBrick(point, size);

    private final Point2D center = new Point2D.Double(300, 430 );
    RubberBall rubberBall = new RubberBall(center);

    @Test
    void impact() {
        clayBrick.impact();
        assertTrue(clayBrick.isBroken());
    }

    @Test
    void setImpact() {
        assertTrue(clayBrick.setImpact(point , LEFT));
    }

    @Test
    void findImpact() {
        clayBrick.impact();
        assertEquals(0, clayBrick.findImpact(rubberBall));
    }

    @Test
    void repair() {
        clayBrick.repair();
        assertFalse(clayBrick.isBroken());
    }

    @Test
    void makeBrickFace() {
        assertEquals(new Rectangle(new Point(200,400),new Dimension(450, 300)), clayBrick.makeBrickFace(point, size));
    }

    @Test
    void getBorderColor() {
        assertEquals(DEF_BORDER, clayBrick.getBorderColor());
    }

    @Test
    void getInnerColor() {
        assertEquals(DEF_INNER, clayBrick.getInnerColor());
    }

    @Test
    void isBroken() {
        assertFalse(clayBrick.isBroken());
    }

    @Test
    void getBrickFace() {
        assertEquals(new Rectangle(new Point(200,400),new Dimension(450, 300)), clayBrick.getBrickFace());
    }

    @Test
    void getBrick() {
        assertEquals(new Rectangle(new Point(200,400),new Dimension(450, 300)), clayBrick.getBrickFace());
    }
}