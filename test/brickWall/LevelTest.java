package brickWall;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class LevelTest {

    private static final int DEF_WIDTH = 600;
    private static final int DEF_HEIGHT = 450;
    private Rectangle drawArea = new Rectangle(0,0,DEF_WIDTH,DEF_HEIGHT);
    private int brickCount = 30;
    private int lineCount = 3;
    private double brickDimensionRatio = 6/2;

    Level level = new Level();

    @Test
    void makeLevels() {

        Brick[][] tmp = level.makeLevels(drawArea, brickCount, lineCount, brickDimensionRatio);
        assertNotNull(tmp);
    }
}