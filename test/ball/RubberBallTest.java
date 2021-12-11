package ball;

import java.awt.*;
import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.*;

class RubberBallTest {

    private static final int DEF_RADIUS = 10;
    private static final Color DEF_INNER_COLOR = new Color(255, 219, 88);
    private static final Color DEF_BORDER_COLOR = DEF_INNER_COLOR.darker().darker();
    private final Point2D center = new Point2D.Double(300, 430 );

    RubberBall rubberBall = new RubberBall(center);

    @org.junit.jupiter.api.Test
    void move() {
        rubberBall.setSpeed(4, 4);
        rubberBall.move();
        assertEquals(new Point2D.Double(304,434), rubberBall.getPosition());
    }

    @org.junit.jupiter.api.Test
    void moveTo() {
        rubberBall.moveTo(new Point(200, 400));
        assertEquals(new Point2D.Double(200, 400), rubberBall.getPosition());
    }

    @org.junit.jupiter.api.Test
    void setSpeed() {
        rubberBall.setSpeed(4, 4);
        assertEquals(4,rubberBall.getSpeedX());
        assertEquals(4,rubberBall.getSpeedY());
    }

    @org.junit.jupiter.api.Test
    void setXSpeed() {
        rubberBall.setXSpeed(4);
        assertEquals(4,rubberBall.getSpeedX());
    }

    @org.junit.jupiter.api.Test
    void setYSpeed() {
        rubberBall.setYSpeed(4);
        assertEquals(4,rubberBall.getSpeedY());
    }

    @org.junit.jupiter.api.Test
    void reverseX() {
        rubberBall.setXSpeed(4);
        rubberBall.reverseX();
        assertEquals(-4,rubberBall.getSpeedX());
    }

    @org.junit.jupiter.api.Test
    void reverseY() {
        rubberBall.setYSpeed(4);
        rubberBall.reverseY();
        assertEquals(-4,rubberBall.getSpeedY());
    }

    @org.junit.jupiter.api.Test
    void getBorderColor() {
        assertEquals(DEF_BORDER_COLOR, rubberBall.getBorderColor());
    }

    @org.junit.jupiter.api.Test
    void getInnerColor() {
        assertEquals(DEF_INNER_COLOR, rubberBall.getInnerColor());
    }

    @org.junit.jupiter.api.Test
    void getPosition() {
        assertEquals(center, rubberBall.getPosition());
    }

    @org.junit.jupiter.api.Test
    void getBallFace() {
        assertEquals(rubberBall.makeBall(center, DEF_RADIUS, DEF_RADIUS), rubberBall.getBallFace());
    }

    @org.junit.jupiter.api.Test
    void getSpeedX() {
        rubberBall.setXSpeed(4);
        assertEquals(4, rubberBall.getSpeedX());
    }

    @org.junit.jupiter.api.Test
    void getSpeedY() {
        rubberBall.setYSpeed(4);
        assertEquals(4, rubberBall.getSpeedY());
    }

    @org.junit.jupiter.api.Test
    void getUp() {
        assertEquals(new Point2D.Double(center.getX(),center.getY()-(DEF_RADIUS / 2)), rubberBall.getUp());
    }

    @org.junit.jupiter.api.Test
    void getDown() {
        assertEquals(new Point2D.Double(center.getX(),center.getY()+(DEF_RADIUS / 2)), rubberBall.getDown());
    }

    @org.junit.jupiter.api.Test
    void getLeft() {
        assertEquals(new Point2D.Double(center.getX()-(DEF_RADIUS /2),center.getY()), rubberBall.getLeft());
    }

    @org.junit.jupiter.api.Test
    void getRight() {
        assertEquals(new Point2D.Double(center.getX()+(DEF_RADIUS /2),center.getY()), rubberBall.getRight());
    }

    @org.junit.jupiter.api.Test
    void makeBall() {
        assertEquals(rubberBall.getBallFace(), rubberBall.makeBall(center, DEF_RADIUS, DEF_RADIUS));
    }
}