package brickWall;

import ball.RubberBall;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.*;

class CrackTest {

    public static final int DEF_CRACK_DEPTH = 1;
    public static final int DEF_STEPS = 35;
    Point start = new Point(30,40);
    Point end = new Point(70,80);
    Crack crack = new Crack(DEF_CRACK_DEPTH, DEF_STEPS);

    private final Point point = new Point(200,400);
    private final Dimension size = new Dimension(450, 300);
    CementBrick cementBrick = new CementBrick(point, size);

    private final Point2D center = new Point2D.Double(300, 430 );
    RubberBall rubberBall = new RubberBall(center);

    @Test
    void makeCrack() {
        crack.makeCrack(rubberBall.getDown(), crack.UP, cementBrick.getBrickFace());
        assertNotNull(crack);
    }

    @Test
    void testMakeCrack() {
        crack.makeCrack(start, end);
        assertNotNull(crack);
    }

    @Test
    void draw() {
        GeneralPath gp = crack.draw();
        assertNotNull(gp);
    }

    @Test
    void reset() {
        crack.reset();
        assertNotNull(crack);
    }
}