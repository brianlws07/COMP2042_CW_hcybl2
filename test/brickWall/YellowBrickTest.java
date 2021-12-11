package brickWall;

import ball.RubberBall;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.*;

class YellowBrickTest {

    private static final Color DEF_INNER = Color.yellow;
    private static final Color DEF_BORDER = new Color(255, 128, 0);
    private static final int YELLOW_STRENGTH = 3;
    private final Point point = new Point(200,400);
    private final Dimension size = new Dimension(450, 300);
   YellowBrick yellowBrick = new YellowBrick(point, size);

    public static final int LEFT_IMPACT = 300;
    public static final int RIGHT = 10;

    private final Point2D center = new Point2D.Double(300, 430 );
    RubberBall rubberBall = new RubberBall(center);

    @Test
    void impact() {
        yellowBrick.impact();
        assertFalse(yellowBrick.isBroken());
    }

    @Test
    void findImpact() {
        yellowBrick.impact();
        assertEquals(LEFT_IMPACT, yellowBrick.findImpact(rubberBall));
    }

    @Test
    void makeBrickFace() {
        assertEquals(new Rectangle(new Point(200,400),new Dimension(450, 300)), yellowBrick.makeBrickFace(point, size));
    }

    @Test
    void getBorderColor() {
        assertEquals(DEF_BORDER, yellowBrick.getBorderColor());
    }

    @Test
    void getInnerColor() {
        assertEquals(DEF_INNER, yellowBrick.getInnerColor());
    }

    @Test
    void isBroken() {
        assertFalse(yellowBrick.isBroken());
    }

    @Test
    void getBrickFace() {
        assertEquals(new Rectangle(new Point(200,400),new Dimension(450, 300)), yellowBrick.getBrickFace());
    }

    @Test
    void testRepair() {
        yellowBrick.repair();
        assertFalse(yellowBrick.isBroken());
    }

    @Test
    void testSetImpact() {
        assertFalse(yellowBrick.setImpact(point , RIGHT));
    }

    @Test
    void getBrick() {
        assertEquals(new Rectangle(new Point(200,400),new Dimension(450, 300)), yellowBrick.getBrickFace());
    }
}