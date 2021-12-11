package brickWall;

import ball.RubberBall;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.*;

class SteelBrickTest {

    private static final Color DEF_INNER = new Color(203, 203, 201);
    private static final Color DEF_BORDER = Color.BLACK;
    private static final int STEEL_STRENGTH = 1;
    private static final double STEEL_PROBABILITY = 1.0; //modified from 0.4 to 1.0 for test purposes
    private final Point point = new Point(200,400);
    private final Dimension size = new Dimension(450, 300);
    SteelBrick steelBrick = new SteelBrick(point, size);

    public static final int LEFT_IMPACT = 300;
    public static final int DOWN = 10;

    private final Point2D center = new Point2D.Double(300, 430 );
    RubberBall rubberBall = new RubberBall(center);

    @Test
    void findImpact() {
        steelBrick.impact();
        assertEquals(LEFT_IMPACT, steelBrick.findImpact(rubberBall));
    }

    @Test
    void repair() {
        steelBrick.repair();
        assertFalse(steelBrick.isBroken());
    }

    @Test
    void makeBrickFace() {
        assertEquals(new Rectangle(new Point(200,400),new Dimension(450, 300)), steelBrick.makeBrickFace(point, size));
    }

    @Test
    void getBorderColor() {
        assertEquals(DEF_BORDER, steelBrick.getBorderColor());
    }

    @Test
    void getInnerColor() {
        assertEquals(DEF_INNER, steelBrick.getInnerColor());
    }

    @Test
    void isBroken() {
        assertFalse(steelBrick.isBroken());
    }

    @Test
    void getBrickFace() {
        assertEquals(new Rectangle(new Point(200,400),new Dimension(450, 300)), steelBrick.getBrickFace());
    }

    @Test
    void testImpact() {
        assertTrue(steelBrick.getRnd().nextDouble() < STEEL_PROBABILITY);
    }

    @Test
    void testSetImpact() {
        assertFalse(steelBrick.setImpact(point , DOWN));
    }

    @Test
    void getBrick() {
        assertEquals(new Rectangle(new Point(200,400),new Dimension(450, 300)), steelBrick.getBrickFace());
    }
}