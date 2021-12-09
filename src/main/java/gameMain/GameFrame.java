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
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

/**
 * GameFrame is a JFrame that holds multiple component which are homeMenu and gameBoard
 */
public class GameFrame extends JFrame implements WindowFocusListener {

    private static final String DEF_TITLE = "Brick Destroy";

    //Class gameBoard, homeMenu constructor
    private GameBoard gameBoard;
    private HomeMenu homeMenu;

    private boolean gaming;

    /**
     * constructor of class GameFrame which set layout of the frame to display gameBoard and homeMenu
     */
    public GameFrame(){
        //JFrame constructor that constructs a frame that is initially invisible
        super();

        gaming = false;
        //this set GameFrame to default border layout
        this.setLayout(new BorderLayout());
        //construct gameBoard and homeMenu
        gameBoard = new GameBoard(this);
        homeMenu = new HomeMenu(this,new Dimension(450,300));
        //so that homeMenu appears at center
        this.add(homeMenu,BorderLayout.CENTER);
        //so that homeMenu won't be decorated (which means it won't be windowed)
        this.setUndecorated(true);
    }

    /**
     * initialize GameFrame with a specific title
     */
    public void initialize(){
        //this set the title and visible of the frame to true
        this.setTitle(DEF_TITLE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.autoLocate();
        this.setVisible(true);
    }

    /**
     * this method will be invoked if start button is clicked in HomeMenu,
     * this method also removes homeMenu to display gameBoard
     */
    public void enableGameBoard(){
        //removes the HomeMenu
        this.dispose();
        this.remove(homeMenu);
        //this adds the gameBoard into the gameFrame
        this.add(gameBoard,BorderLayout.CENTER);
        //frame is now decorated, so the frame is now windowed
        this.setUndecorated(false);
        initialize();
        /*to avoid problems with graphics focus controller is added here*/
        this.addWindowFocusListener(this);
    }

    /**
     * set the location of gameFrame relative to screenSize regardless of any monitor
     */
    private void autoLocate(){
        //this get the screenSize of monitor
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (size.width - this.getWidth()) / 2;
        int y = (size.height - this.getHeight()) / 2;
        this.setLocation(x,y);
    }

    /**
     * if gameFrame is in use, then set gaming to true
     * @param windowEvent status of window
     */
    @Override
    public void windowGainedFocus(WindowEvent windowEvent) {
        /*
            the first time the frame loses focus is because
            it has been disposed to install the GameBoard,
            so went it regains the focus it's ready to play.
            of course calling a method such as 'onLostFocus'
            is useful only if the GameBoard as been displayed
            at least once
         */
        gaming = true;
    }

    /**
     * if gameFrame is not in use but gaming is still True,
     * stop timer and output message "Focus Lost" on screen
     * @param windowEvent status of window
     */
    @Override
    public void windowLostFocus(WindowEvent windowEvent) {
        if(gaming)
            gameBoard.onLostFocus();
    }
}
