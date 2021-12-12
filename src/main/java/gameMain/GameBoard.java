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
package gameMain;

import ball.Ball;
import brickWall.Brick;
import brickWall.Wall;
import debug.DebugConsole;
import ball.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;

/**
 * GameBoard is the renderer of the game itself where it draws the wall, ball, player
 * it also holds a pause menu, debugConsole
 */
public class GameBoard extends JComponent implements KeyListener,MouseListener,MouseMotionListener {

    //set the text, font size, font color for pause menu
    private static final String CONTINUE = "Continue";
    private static final String RESTART = "Restart";
    private static final String EXIT = "Exit";
    private static final String PAUSE = "Pause Menu";
    private static final int TEXT_SIZE = 30;
    private static final Color MENU_COLOR = new Color(0,255,0);
    //set width and height of the walls
    private static final int DEF_WIDTH = 600;
    private static final int DEF_HEIGHT = 450;
    //set background color to white
    //private static final Color BG_COLOR = Color.WHITE;

    private Timer gameTimer;

    private Wall wall;

    private String message;
    private String scoremessage;
    private String highscoremessage;

    private boolean showPauseMenu;

    private Font menuFont;
    //pause menu buttons
    private Rectangle continueButtonRect;
    private Rectangle exitButtonRect;
    private Rectangle restartButtonRect;
    private int strLen;

    private DebugConsole debugConsole;

    private Image background;

    /**
     * Constructor of GameBoard class which initialize pauseMenu, debugConsole, gameTimer, wall of bricks
     *
     * @param owner GameFrame
     */
    public GameBoard(JFrame owner){
        //constructor JComponent
        super();
        //pause menu text length
        strLen = 0;
        //pause menu is not shown
        showPauseMenu = false;
        //set font size and style for pause menu
        menuFont = new Font("Monospaced",Font.PLAIN,TEXT_SIZE);
        //initialize the GameBoard JComponent
        this.initialize();
        message = "";
        scoremessage = "";
        highscoremessage = "";
        //Rectangle parameter was passed as drawArea for the walls
        wall = new Wall(new Rectangle(0,0,DEF_WIDTH,DEF_HEIGHT),30,3,6/2,new Point(300,430));
        //construct the debugConsole
        debugConsole = new DebugConsole(owner,wall,this);
        //initialize the first level
        wall.nextLevel();

        gameTimer = new Timer(10,e ->{
            //for player and ball move
            wall.move();
            //walls are impacted
            wall.findImpacts();
            //display no. bricks, no.balls, score, highscore on screen
            message = String.format("Bricks: %d Balls %d",wall.getBrickCount(),wall.getBallCount());
            scoremessage = String.format("Score: %d",wall.getScore());
            highscoremessage = String.format("HighScore: %s",wall.getHighscore());
            //if a ball is lost,
            //then check if all ball are lost, if yes then restore the walls and output message "Game over"
            //then reset ball and player bar back to original position
            //stop timer
            if(wall.isBallLost()){
                if(wall.ballEnd()){
                    wall.wallReset();
                    message = "Game over";
                    wall.checkScore();
                }
                wall.ballReset();
                gameTimer.stop();
            }
            //if all the bricks are broken (one level is completed)
            //check if there is still next levels
            //(1)if yes,
            //   output message "Go to Next Level"
            //   stop the timer, reset ball and player bar back to original position, restore the walls
            //   go to the next level
            //(2)if no,
            //   output message "ALL WALLS DESTROYED"
            //   stop the timer
            else if(wall.isDone()){
                if(wall.hasLevel()){
                    message = "Go to Next Level";
                    gameTimer.stop();
                    wall.ballReset();
                    wall.wallReset();
                    wall.nextLevel();
                }
                else{
                    message = "ALL WALLS DESTROYED";
                    gameTimer.stop();
                    wall.checkScore();
                }
            }
            //repaint the GameBoard after that
            repaint();
        });

    }

    /**
     * initialize gameBoard as a JComponent with a specific size,
     * also adds (key,mouse,mouseMotion)Listener to gameBoard
     */
    private void initialize(){
        this.setPreferredSize(new Dimension(DEF_WIDTH,DEF_HEIGHT));
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    //order of methods to be called after paint():
    //paint -> drawImage -> drawString -> drawScore -> drawBall -> drawBrick -> drawPlayer
    //-> drawMenu -> obscureGameBoard -> drawPauseMenu

    /**
     * this is a built-in paint method, so paint() doesn't have to be called explicitly,
     * it will be called automatically everytime constructor for GameBoard is called
     * paint method draws all component in GameBoard
     * @param g graphics object
     */
    public void paint(Graphics g){

        Graphics2D g2d = (Graphics2D) g;

        //fill up white background color
        //clear(g2d);

        //draw background image in gameBoard
        background = new ImageIcon(getClass().getResource("/kirby_bg.png")).getImage();
        g2d.drawImage(background,0,0,DEF_WIDTH,DEF_HEIGHT,null);

        //draw the message text on screen with green color
        g2d.setColor(Color.GREEN);
        g2d.drawString(message,250,225);

        //draw high score and score
        g2d.drawString(scoremessage,250,250);
        g2d.drawString(highscoremessage,250,275);

        drawBall(wall.getBall(),g2d);//draw the ball on screen
        //for all the bricks that are not broken, draw the bricks on screen
        for(Brick b : wall.getBricks())
            if(!b.isBroken())
                drawBrick(b,g2d);
        drawPlayer(wall.getPlayer(),g2d);//draw the player bar
        //if showPauseMenu is true, draw the PauseMenu
        if(showPauseMenu)
            drawMenu(g2d);

        //sync method to ensure the display is up to date
        Toolkit.getDefaultToolkit().sync();
    }

    /*
    private void clear(Graphics2D g2d){
        //store g2d color in tmp
        Color tmp = g2d.getColor();
        //fill up the background color of GameBoard with white
        g2d.setColor(BG_COLOR);
        g2d.fillRect(0,0,getWidth(),getHeight());
        //restore the g2d color
        g2d.setColor(tmp);
    }

     */

    /**
     * draw bricks and fill it up with inner and border color
     * @param brick bricks
     * @param g2d graphics2D object
     */
    private void drawBrick(Brick brick,Graphics2D g2d){
        Color tmp = g2d.getColor();

        //fill the inner color of the brick
        g2d.setColor(brick.getInnerColor());
        g2d.fill(brick.getBrick());
        //fill the border color of the brick
        g2d.setColor(brick.getBorderColor());
        g2d.draw(brick.getBrick());

        g2d.setColor(tmp);
    }

    /**
     * draw the ball and fill it up with inner and border color
     * @param ball ball
     * @param g2d graphics2D object
     */
    private void drawBall(Ball ball, Graphics2D g2d){
        Color tmp = g2d.getColor();

        Shape s = ball.getBallFace();
        //fill the inner color of the ball
        g2d.setColor(ball.getInnerColor());
        g2d.fill(s);
        //fill the border color of the ball
        g2d.setColor(ball.getBorderColor());
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    /**
     * draw the player bar and fill it up with inner and border color
     * @param p Player
     * @param g2d graphics2D object
     */
    private void drawPlayer(Player p, Graphics2D g2d){
        Color tmp = g2d.getColor();

        Shape s = p.getPlayerFace();
        //fill the inner color of the player bar
        g2d.setColor(Player.INNER_COLOR);
        g2d.fill(s);
        //fill the border color of the player bar
        g2d.setColor(Player.BORDER_COLOR);
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    /**
     * make an opaque black screen
     * then display PauseMenu
     * @param g2d graphics2D object
     */
    private void drawMenu(Graphics2D g2d){
        obscureGameBoard(g2d);
        drawPauseMenu(g2d);
    }

    /**
     * it sets the screen to opaque black
     * @param g2d graphics2D object
     */
    private void obscureGameBoard(Graphics2D g2d){
        //store g2d.composite, color in a tmp variable
        Composite tmp = g2d.getComposite();
        Color tmpColor = g2d.getColor();
        //set the opacity of the screen, then fill up the screen with opaque black
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.55f);
        g2d.setComposite(ac);
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0,DEF_WIDTH,DEF_HEIGHT);
        //restore g2d.composite, color
        g2d.setComposite(tmp);
        g2d.setColor(tmpColor);
    }

    /**
     * it displays pauseMenu which holds 3 buttons, continue, restart, exit
     * @param g2d graphics2D object
     */
    private void drawPauseMenu(Graphics2D g2d){
        //store g2d.font, color in a tmp variable
        Font tmpFont = g2d.getFont();
        Color tmpColor = g2d.getColor();
        //set the font and color for pause menu
        g2d.setFont(menuFont);
        g2d.setColor(MENU_COLOR);

        if(strLen == 0){
            //returns the Rectangle bounding box for pause text
            //strLen stores the width of the Rectangle
            FontRenderContext frc = g2d.getFontRenderContext();
            strLen = menuFont.getStringBounds(PAUSE,frc).getBounds().width;
        }
        //set location for "Pause Menu" and display it
        int x = (this.getWidth() - strLen) / 2;
        int y = this.getHeight() / 10;
        g2d.drawString(PAUSE,x,y);

        //set location for "Continue" button and display it
        x = this.getWidth() / 8;
        y = this.getHeight() / 4;
        if(continueButtonRect == null){
            FontRenderContext frc = g2d.getFontRenderContext();
            //get the Rectangle bounding box for Continue text
            continueButtonRect = menuFont.getStringBounds(CONTINUE,frc).getBounds();
            continueButtonRect.setLocation(x,y-continueButtonRect.height);
        }
        g2d.drawString(CONTINUE,x,y);

        //set location for "restart" button and display it
        //pushes restart button downwards from continue
        y *= 2;
        if(restartButtonRect == null){
            restartButtonRect = (Rectangle) continueButtonRect.clone();
            restartButtonRect.setLocation(x,y-restartButtonRect.height);
        }
        g2d.drawString(RESTART,x,y);

        //set location for "exit" button and display it
        //pushes exit button downwards from restart
        y *= 3.0/2;
        if(exitButtonRect == null){
            exitButtonRect = (Rectangle) continueButtonRect.clone();
            exitButtonRect.setLocation(x,y-exitButtonRect.height);
        }
        g2d.drawString(EXIT,x,y);

        //restore g2d.font, color
        g2d.setFont(tmpFont);
        g2d.setColor(tmpColor);
    }

    /**
     * A, D key: move the player bar left and right
     * ESCAPE key: show PauseMenu
     * SPACE key: continue the game
     * ALT+SHIFT+F1 key: debugConsole
     * @param keyEvent keystroke
     */
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch(keyEvent.getKeyCode()){
            case KeyEvent.VK_A:
                wall.getPlayer().moveLeft();
                break;
            case KeyEvent.VK_D:
                wall.getPlayer().movRight();
                break;
            case KeyEvent.VK_ESCAPE:
                showPauseMenu = !showPauseMenu;
                repaint();
                gameTimer.stop();
                break;
            case KeyEvent.VK_SPACE:
                if(!showPauseMenu)
                    if(gameTimer.isRunning())
                        gameTimer.stop();
                    else
                        gameTimer.start();
                break;
            case KeyEvent.VK_F1:
                if(keyEvent.isAltDown() && keyEvent.isShiftDown())
                    debugConsole.setVisible(true);
            default:
                wall.getPlayer().stop();
        }
    }

    /**
     * if key is released, player bar stop moving
     * @param keyEvent keystroke
     */
    @Override
    public void keyReleased(KeyEvent keyEvent) {
        wall.getPlayer().stop();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    /**
     * to check if mouse click any buttons in PauseMenu
     * @param mouseEvent mouse action
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(!showPauseMenu)
            return;
        if(continueButtonRect.contains(p)){
            showPauseMenu = false;
            repaint();
        }
        else if(restartButtonRect.contains(p)){
            message = "Restarting Game...";
            wall.ballReset();
            wall.wallReset();
            showPauseMenu = false;
            repaint();
        }
        else if(exitButtonRect.contains(p)){
            System.exit(0);
        }
    }

    /**
     * if mouse cursor hover over any buttons in pause menu, then mouse cursor turns into a hand cursor
     * @param mouseEvent mouse action
     */
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(exitButtonRect != null && showPauseMenu) {
            if (exitButtonRect.contains(p) || continueButtonRect.contains(p) || restartButtonRect.contains(p))
                this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            else
                this.setCursor(Cursor.getDefaultCursor());
        }
        else{
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    /**
     * if gameFrame is not in use but gaming is still True,
     * stop timer and output message "Focus Lost" on screen
     */
    public void onLostFocus(){
        gameTimer.stop();
        message = "Focus Lost";
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {}

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {}

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {}

    @Override
    public void mouseExited(MouseEvent mouseEvent) {}

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {}
}
