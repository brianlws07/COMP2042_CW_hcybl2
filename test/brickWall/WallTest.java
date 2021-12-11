package brickWall;

import ball.Ball;
import ball.Player;
import ball.RubberBall;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.*;

class WallTest {
    private static final int DEF_WIDTH = 600;
    private static final int DEF_HEIGHT = 450;
    private Rectangle drawArea = new Rectangle(0,0,DEF_WIDTH,DEF_HEIGHT);
    private int brickCount = 30;
    private int lineCount = 3;
    private double brickDimensionRatio = 6/2;
    private Point ballPos = new Point(300,430);
    Wall wall = new Wall(drawArea, brickCount, lineCount, brickDimensionRatio, ballPos);

    @Test
    void findImpacts() {
        wall.getBall().setYSpeed(5);
        wall.getPlayer().impact(wall.getBall());
        wall.findImpacts();
        assertEquals(-5, wall.getBall().getSpeedY());
    }

    @Test
    void ballReset() {
        wall.setBallLost(true);
        wall.ballReset();
        assertFalse(wall.isBallLost());
    }


    @Test
    void wallReset() {
        Point point = new Point(200,400);
        Dimension size = new Dimension(450, 300);
        ClayBrick clayBrick = new ClayBrick(point, size);
        if (clayBrick.isBroken())
            wall.wallReset();
        assertEquals(3, wall.getBallCount());
    }

    @Test
    void move() {
        wall.getBall().setSpeed(4, 4);
        wall.move();
        assertEquals(new Point2D.Double(304,434), wall.getBall().getPosition());
        assertEquals(229,ballPos.x - 150/2);
    }

    @Test
    void hasLevel() {
        assertTrue(wall.hasLevel());
    }

    @Test
    void setBallXSpeed() {
        wall.setBallXSpeed(4);
        assertEquals(4,wall.getBall().getSpeedX());
    }

    @Test
    void setBallYSpeed() {
        wall.setBallYSpeed(4);
        assertEquals(4,wall.getBall().getSpeedY());
    }

    @Test
    void resetBallCount() {
        wall.setBallCount(1);
        wall.resetBallCount();
        assertEquals(3, wall.getBallCount());
    }

    @Test
    void ballEnd() {
        assertFalse(wall.ballEnd());
    }

    @Test
    void isDone() {
        assertTrue(wall.isDone());
    }

    @Test
    void getBrickCount() {
        assertEquals(0, wall.getBrickCount());
    }

    @Test
    void getBallCount() {
        assertEquals(3, wall.getBallCount());
    }

    @Test
    void isBallLost() {
        assertFalse(wall.isBallLost());
    }

    @Test
    void getBricks() {
        Brick brick[] = wall.getBricks();
        assertNull(brick);
    }

    @Test
    void getBall() {
        Ball ball = wall.getBall();
        assertNotNull(ball);
    }

    @Test
    void getPlayer() {
        Player player = wall.getPlayer();
        assertNotNull(player);
    }
}