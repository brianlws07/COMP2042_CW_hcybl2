package brickWall;

import java.awt.*;

public class Level {

    private static final int LEVELS_COUNT = 5;// 5 levels
    private static final int CLAY = 1;
    private static final int STEEL = 2;
    private static final int CEMENT = 3;
    private static final int YELLOWBRICK = 4;

    /**
     * makeLevels constructs levels with respective bricks and store it into a 2D array of type Brick
     *
     * @param drawArea Rectangle GameFrame where game is rendered/drawn
     * @param brickCount number of bricks in wall(30)
     * @param lineCount lines of bricks in wall(3)
     * @param brickDimensionRatio width-to-height ratio of a single brick
     * @return tmp array which stores individual levels of bricks
     */
    public Brick[][] makeLevels(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio){
        //construct a tmp variable of type 2D array of Bricks, LEVELS_COUNT is 5
        Brick[][] tmp = new Brick[LEVELS_COUNT][];
        //it stores the object of bricks created in the inner array, and there are 31 objects for 31 bricks
        tmp[0] = makeSingleTypeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY);
        tmp[1] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY,CEMENT);
        tmp[2] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY,STEEL);
        tmp[3] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,STEEL,CEMENT);
        tmp[4] = makefinalLevel(drawArea,brickCount,lineCount,brickDimensionRatio,YELLOWBRICK,CEMENT);
        return tmp;
    }

    /**
     * make wall of clay bricks for the first level
     *
     * @param drawArea Rectangle GameFrame where game is rendered/drawn
     * @param brickCnt number of bricks in wall(30)
     * @param lineCnt lines of bricks in wall(3)
     * @param brickSizeRatio width-to-height ratio of a single brick
     * @param type types of brick
     * @return array with 31 bricks
     */
    private Brick[] makeSingleTypeLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int type){
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */
        //so that the number of Bricks are able to fill in the JPanel
        //31bricks, 3 lineCount, so brickCount = 31 - 31%3
        //                                     = 30
        //we always have 31 bricks to fill up the JPanel or JFrame
        brickCnt -= brickCnt % lineCnt;

        int brickOnLine = brickCnt / lineCnt;//no. of bricks per line (10)
        double brickLen = drawArea.getWidth() / brickOnLine;//length of a single brick
        double brickHgt = brickLen / brickSizeRatio;//height of a single brick
        brickCnt += lineCnt / 2;//set the brickCount from 30 back to 31 again

        Brick[] tmp  = new Brick[brickCnt];//construct tmp as array of Brick with size 31
        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt);//construct brickSize as type Dimension with specified brickLen, brickHgt
        Point p = new Point();//construct new Point p

        int i;
        //for loop executes for 31 times for all bricks
        for(i = 0; i < tmp.length; i++){
            int line = i / brickOnLine;//there is only 3 lines, so line = 0, 1, 2
            if(line == lineCnt)//it will only break if it has executed for all 3 lines of bricks
                break;
            //this sections sets the x and y coordinate for the bricks
            //this set x to the x coordinate of every brick's upper left corner
            double x = (i % brickOnLine) * brickLen;
            //even number line(0, 2) will set the x coordinate for brick normally
            //odd number line(1) will start the line with half the length of the brick
            x =(line % 2 == 0) ? x : (x - (brickLen / 2));
            //to set the y coordinate for every brick
            double y = (line) * brickHgt;
            p.setLocation(x,y);
            tmp[i] = makeBrick(p,brickSize,type);
        }
        _2ndlinelastbrick(brickHgt, tmp, brickOnLine, brickLen, p, i, brickSize, type);
        return tmp;
    }

    /**
     * make a wall with 2 types of bricks arranged in a chessboard pattern for level 2, 3, 4
     *
     * @param drawArea Rectangle GameFrame where game is rendered/drawn
     * @param brickCnt number of bricks in wall(30)
     * @param lineCnt lines of bricks in wall(3)
     * @param brickSizeRatio width-to-height ratio of a single brick
     * @param typeA first type of brick
     * @param typeB second type of brick
     * @return array with 31 bricks
     */
    private Brick[] makeChessboardLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int typeA, int typeB){
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */
        brickCnt -= brickCnt % lineCnt; //set brickCnt to 30
        int brickOnLine = brickCnt / lineCnt; //set brickOnLine to 10

        //needed for setting the brick pattern in 2nd line (line 1)
        int centerLeft = brickOnLine / 2 - 1; //centerLeft is 4
        int centerRight = brickOnLine / 2 + 1; //centerRight is 6

        double brickLen = drawArea.getWidth() / brickOnLine; //length of single brick
        double brickHgt = brickLen / brickSizeRatio; //height of single brick
        brickCnt += lineCnt / 2;// set brickCnt back to 31
        Brick[] tmp  = new Brick[brickCnt]; // set tmp as Brick[31]
        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt); // initialize Dimension brickSize for a single brick
        Point p = new Point(); //construct new Point p

        int i;
        for(i = 0; i < tmp.length; i++){
            int line = i / brickOnLine;
            if(line == lineCnt)
                break;
            //this sections sets the x and y coordinate for the bricks
            int posX = i % brickOnLine;
            double x = posX * brickLen;
            x =(line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p.setLocation(x,y);

            //set pattern for the bricks
            //for line 0, 2 the pattern of every brick will alternate each other
            //for line 1 only brick 5 and 6 will be different pattern than the rest in line
            boolean b = ((line % 2 == 0 && i % 2 == 0) || (line % 2 != 0 && posX > centerLeft && posX <= centerRight));
            tmp[i] = b ?  makeBrick(p,brickSize,typeA) : makeBrick(p,brickSize,typeB);
        }
        _2ndlinelastbrick(brickHgt, tmp, brickOnLine, brickLen, p, i, brickSize, typeA);
        return tmp;
    }

    /**
     * make a wall with 2 types of bricks arranged in a AB pattern for final level
     *
     * @param drawArea Rectangle GameFrame where game is rendered/drawn
     * @param brickCnt number of bricks in wall(30)
     * @param lineCnt lines of bricks in wall(3)
     * @param brickSizeRatio width-to-height ratio of a single brick
     * @param typeA first type of brick
     * @param typeB second type of brick
     * @return array with 31 bricks
     */
    private Brick[] makefinalLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int typeA, int typeB){
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */
        brickCnt -= brickCnt % lineCnt; //set brickCnt to 30
        int brickOnLine = brickCnt / lineCnt; //set brickOnLine to 10
        double brickLen = drawArea.getWidth() / brickOnLine; //length of single brick
        double brickHgt = brickLen / brickSizeRatio; //height of single brick
        brickCnt += lineCnt / 2;// set brickCnt back to 31
        Brick[] tmp  = new Brick[brickCnt]; // set tmp as Brick[31]
        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt); // initialize Dimension brickSize for a single brick
        Point p = new Point();//construct new Point p

        int i;
        for(i = 0; i < tmp.length; i++){
            int line = i / brickOnLine;
            if(line == lineCnt)
                break;
            //this sections sets the x and y coordinate for the bricks
            int posX = i % brickOnLine;
            double x = posX * brickLen;
            x =(line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p.setLocation(x,y);

            boolean b = i % 2 == 0;
            tmp[i] = b ?  makeBrick(p,brickSize,typeA) : makeBrick(p,brickSize,typeB);
        }
        _2ndlinelastbrick(brickHgt, tmp, brickOnLine, brickLen, p, i, brickSize, typeA);
        return tmp;
    }

    /**
     * Make a brick at the location of 2nd line last brick
     *
     * @param brickHgt height of brick
     * @param tmp tmp array of brick
     * @param brickOnLine number of brick in a line(10)
     * @param brickLen length of brick
     * @param p position of brick
     * @param i index for for loop
     * @param brickSize size of a single brick
     * @param typeA type of brick
     */
    private void _2ndlinelastbrick(double brickHgt, Brick[] tmp, int brickOnLine, double brickLen, Point p, int i, Dimension brickSize, int typeA){
        for(double y = brickHgt; i < tmp.length; i++, y += 2*brickHgt){
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x,y);
            tmp[i] = makeBrick(p,brickSize,typeA);
        }
    }

    /**
     * creates a brick of specified type at the exact location point
     *
     * @param point location where brick is constructed
     * @param size Size of a single brick
     * @param type type of bricks
     * @return
     */
    private Brick makeBrick(Point point, Dimension size, int type){
        Brick out;
        switch(type){
            case CLAY:
                out = new ClayBrick(point,size);
                break;
            case STEEL:
                out = new SteelBrick(point,size);
                break;
            case CEMENT:
                out = new CementBrick(point, size);
                break;
            case YELLOWBRICK:
                out = new YellowBrick(point, size);
                break;
            default:
                throw  new IllegalArgumentException(String.format("Unknown Type:%d\n",type));
        }
        return  out;
    }
}
