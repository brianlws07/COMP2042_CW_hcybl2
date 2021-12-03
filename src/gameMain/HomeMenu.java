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

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;


public class HomeMenu extends JComponent implements MouseListener, MouseMotionListener {

    //CONSTANTS:
    //-Text for Greetings, Game Title, Credit, Start button, Exit button
    private static final String GREETINGS = "Welcome to:";
    private static final String GAME_TITLE = "Brick Destroy";
    private static final String CREDITS = "Version 0.1";
    private static final String START_TEXT = "Start";
    private static final String MENU_TEXT = "Exit";
    private static final String INFO_TEXT = "Info";

    //CONSTANTS:
    //-Background color is darker green
    //-Border color is red
    //-Border small pattern color is yellow
    //-The text color is blue
    //-Clicked button color is light green
    //-Clicked button text color is white
    private static final Color BG_COLOR = Color.pink;
    private static final Color BORDER_COLOR = new Color(255,93,93);
    private static final Color DASH_BORDER_COLOR = new  Color(255, 229, 204);
    private static final Color TEXT_COLOR = new  Color(255, 51, 153);//egyptian blue
    private static final Color CLICKED_BUTTON_COLOR = BG_COLOR.brighter();
    private static final Color CLICKED_TEXT = Color.WHITE;
    private static final int BORDER_SIZE = 5;
    private static final float[] DASHES = {12,6};

    //menuFace, startButton, exitButton
    private Rectangle menuFace;
    private Rectangle startButton;
    private Rectangle menuButton;
    private Rectangle infoButton;

    //BasicStroke defines the rendering attributes for Graphics2D objects
    private BasicStroke borderStoke;
    private BasicStroke borderStoke_noDashes;

    //Font for greetings, gameTitle, credits, button text
    private Font greetingsFont;
    private Font gameTitleFont;
    private Font creditsFont;
    private Font buttonFont;

    //GameFrame is the owner
    private GameFrame owner;

    private boolean startClicked;
    private boolean menuClicked;
    private boolean infoClicked;

    private Image background;

    public HomeMenu(GameFrame owner,Dimension area){
        //so that the HomeMenu is being focused
        this.setFocusable(true);
        this.requestFocusInWindow();
        //so that HomeMenu listens to mouse actions
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        //GameFrame as the owner
        this.owner = owner;
        //set size of HomeMenu as specified by dimension area
        menuFace = new Rectangle(new Point(0,0),area);
        this.setPreferredSize(area);
        //set size of buttons based on the dimension area
        Dimension btnDim = new Dimension(area.width / 3, area.height / 12);
        startButton = new Rectangle(btnDim);
        menuButton = new Rectangle(btnDim);
        infoButton = new Rectangle(btnDim);
        //set the stroke size
        borderStoke = new BasicStroke(BORDER_SIZE,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,0,DASHES,0);
        borderStoke_noDashes = new BasicStroke(BORDER_SIZE,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);

        //set font size and font style for texts on HomeMenu
        greetingsFont = new Font("Noto Mono",Font.PLAIN,25);
        gameTitleFont = new Font("Noto Mono",Font.BOLD,40);
        creditsFont = new Font("Monospaced",Font.PLAIN,10);
        buttonFont = new Font("Monospaced",Font.PLAIN,startButton.height-2);
    }


    //this is a built in paint method, so paint() doesn't have to be called explicitly,
    //it will be called automatically everytime constructor for HomeMenu is called
    //paint method draws all component in HomeMenu
    //paint -> drawMenu -> drawContainer -> drawText -> drawButton
    public void paint(Graphics g){
        drawMenu((Graphics2D)g);
    }


    public void drawMenu(Graphics2D g2d){

        drawContainer(g2d);

        /*
        all the following method calls need a relative
        painting directly into the HomeMenu rectangle,
        so the translation is made here so the other methods do not do that.
         */
        //to store the default color and font in previousColor,Font
        Color prevColor = g2d.getColor();
        Font prevFont = g2d.getFont();

        double x = menuFace.getX();
        double y = menuFace.getY();
        //to set the reference point in the top left corner of menuFace
        g2d.translate(x,y);

        //methods calls
        drawText(g2d);
        drawButton(g2d);
        //end of methods calls

        g2d.translate(-x,-y);
        g2d.setFont(prevFont);
        g2d.setColor(prevColor);
    }

    private void drawContainer(Graphics2D g2d){
        Color prev = g2d.getColor();

        //set and fill up background with Dark Green color
        g2d.setColor(BG_COLOR);
        g2d.fill(menuFace);

        Stroke tmp = g2d.getStroke();

        background = new ImageIcon(getClass().getResource("/kirby.jpg")).getImage();
        g2d.drawImage(background,0,0,450,300,null);

        //draw the red color border
        g2d.setStroke(borderStoke_noDashes);
        g2d.setColor(DASH_BORDER_COLOR);
        g2d.draw(menuFace);
        //draw the yellow color border small pattern
        g2d.setStroke(borderStoke);
        g2d.setColor(BORDER_COLOR);
        g2d.draw(menuFace);

        g2d.setStroke(tmp);

        g2d.setColor(prev);
    }

    private void drawText(Graphics2D g2d){

        //set the color of text
        g2d.setColor(TEXT_COLOR);

        FontRenderContext frc = g2d.getFontRenderContext();

        //get the bounding rectangle for the text of greetings, gameTitle, credits
        Rectangle2D greetingsRect = greetingsFont.getStringBounds(GREETINGS,frc);
        Rectangle2D gameTitleRect = gameTitleFont.getStringBounds(GAME_TITLE,frc);
        Rectangle2D creditsRect = creditsFont.getStringBounds(CREDITS,frc);

        int sX,sY;
        //order of the text in HomeMenu goes like this:
        //(1)greetings
        //(2)gameTitle
        //(3)credits
        //set location for greetings and draw the text
        sX = (int)(menuFace.getWidth() - greetingsRect.getWidth()) / 2;
        sY = (int)(menuFace.getHeight() / 5);
        g2d.setFont(greetingsFont);
        g2d.drawString(GREETINGS,sX,sY);

        //set location for gameTitle(which is below greetings) and draw the text
        sX = (int)(menuFace.getWidth() - gameTitleRect.getWidth()) / 2;
        sY += (int) gameTitleRect.getHeight() * 1.7;//add 10% of String height between the two strings
        g2d.setFont(gameTitleFont);
        g2d.drawString(GAME_TITLE,sX,sY);

        //set location for credits(which is below gameTitle) and draw the text
        sX = (int)(menuFace.getWidth() - creditsRect.getWidth()) / 2;
        sY += (int) creditsRect.getHeight() * 1.1;
        g2d.setFont(creditsFont);
        g2d.drawString(CREDITS,sX,sY);
    }

    private void drawButton(Graphics2D g2d){

        FontRenderContext frc = g2d.getFontRenderContext();

        //get the bounding rectangle for the buttons text
        Rectangle2D txtRect = buttonFont.getStringBounds(START_TEXT,frc);
        Rectangle2D mTxtRect = buttonFont.getStringBounds(MENU_TEXT,frc);
        Rectangle2D iTxtRect = buttonFont.getStringBounds(INFO_TEXT,frc);
        //set the font for button text
        g2d.setFont(buttonFont);

        //set the location for start button
        int x = (menuFace.width - startButton.width) / 2;
        int y =(int) ((menuFace.height - startButton.height) * 0.65);
        startButton.setLocation(x,y);

        //set the location for Start text inside start button
        x = (int)(startButton.getWidth() - txtRect.getWidth()) / 2;
        y = (int)(startButton.getHeight() - txtRect.getHeight()) / 2;
        x += startButton.x;
        y += startButton.y + (startButton.height * 0.9);

        //if startButton is clicked
        //this set the color of button to light green, and draw it out
        //also set color of Start text to white, and draw it out
        //else, just draw the button normally
        if(startClicked){
            Color tmp = g2d.getColor();
            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(startButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(START_TEXT,x,y);
            g2d.setColor(tmp);
        }
        else{
            g2d.draw(startButton);
            g2d.drawString(START_TEXT,x,y);
        }

        //x coordinate of startButton and menuButton remains same
        //but y coordinate of menuButton is pushed downwards from y coor of startButton
        x = startButton.x;
        y = startButton.y;
        y *= 1.2;
        menuButton.setLocation(x,y);

        //set the location for Exit text inside start button
        x = (int)(menuButton.getWidth() - mTxtRect.getWidth()) / 2;
        y = (int)(menuButton.getHeight() - mTxtRect.getHeight()) / 2;
        x += menuButton.x;
        y += menuButton.y + (startButton.height * 0.9);

        //if menuButton is clicked
        //this set the color of button to light green, and draw it out
        //also set color of Start text to white, and draw it out
        //else, just draw the button normally
        if(menuClicked){
            Color tmp = g2d.getColor();
            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(menuButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(MENU_TEXT,x,y);
            g2d.setColor(tmp);
        }
        else{
            g2d.draw(menuButton);
            g2d.drawString(MENU_TEXT,x,y);
        }

        x = menuButton.x;
        y = menuButton.y;
        y *= 1.16;
        infoButton.setLocation(x,y);

        //set the location for Exit text inside start button
        x = (int)(infoButton.getWidth() - iTxtRect.getWidth()) / 2;
        y = (int)(infoButton.getHeight() - iTxtRect.getHeight()) / 2;
        x += infoButton.x;
        y += infoButton.y + (menuButton.height * 0.9);

        //if menuButton is clicked
        //this set the color of button to light green, and draw it out
        //also set color of Start text to white, and draw it out
        //else, just draw the button normally
        if(infoClicked){
            Color tmp = g2d.getColor();
            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(infoButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(INFO_TEXT,x,y);
            g2d.setColor(tmp);
        }
        else{
            g2d.draw(infoButton);
            g2d.drawString(INFO_TEXT,x,y);
        }
    }

    //if startButton is clicked(pressed & released) then gameBoard is initialized
    //if menuButton is clicked(pressed & released) then quit program, and "Goodbye user" is printed
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p)){
           owner.enableGameBoard();

        }
        else if(menuButton.contains(p)){
            System.out.println("Goodbye " + System.getProperty("user.name"));
            System.exit(0);
        }
        else if(infoButton.contains(p)){
        }
    }

    //if either buttons are pressed, then set (start/menu)Clicked to true, then repaint the button to new color
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p)){
            startClicked = true;
            repaint(startButton.x,startButton.y,startButton.width+1,startButton.height+1);

        }
        else if(menuButton.contains(p)){
            menuClicked = true;
            repaint(menuButton.x,menuButton.y,menuButton.width+1,menuButton.height+1);
        }
        else if(infoButton.contains(p)){
            infoClicked = true;
            repaint(infoButton.x,infoButton.y,infoButton.width+1,infoButton.height+1);
        }
    }

    //if either button are pressed, and then released outside the button,
    //then set (start/menu)Clicked to false, then repaint the buttons to normal color
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if(startClicked){
            startClicked = false;
            repaint(startButton.x,startButton.y,startButton.width+1,startButton.height+1);
        }
        else if(menuClicked){
            menuClicked = false;
            repaint(menuButton.x,menuButton.y,menuButton.width+1,menuButton.height+1);
        }
        else if(infoClicked){
            infoClicked = false;
            repaint(infoButton.x,infoButton.y,infoButton.width+1,infoButton.height+1);
        }
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }


    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    //when mouse hover the buttons. mouse cursor will turn into hand cursor
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p) || menuButton.contains(p) || infoButton.contains(p))
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        else
            this.setCursor(Cursor.getDefaultCursor());

    }
}
