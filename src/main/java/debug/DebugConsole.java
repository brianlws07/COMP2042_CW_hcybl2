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
package debug;

import ball.Ball;
import brickWall.Wall;
import gameMain.GameBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * DebugConsole is a JDialog that holds the debugpanel component which can be activated with key (alt+shift+f1)
 */
public class DebugConsole extends JDialog implements WindowListener{

    private static final String TITLE = "Debug Console";

    private JFrame owner;//GameFrame
    private DebugPanel debugPanel;
    private GameBoard gameBoard;//GameBoard
    private Wall wall;

    /**
     * constructor of class DebugConsole which set values for wall, owner, gameBoard
     * and also initialize DebugConsole
     *
     * @param owner GameFrame
     * @param wall Wall
     * @param gameBoard GameBoard
     */
    public DebugConsole(JFrame owner,Wall wall,GameBoard gameBoard){
        this.wall = wall;
        this.owner = owner;
        this.gameBoard = gameBoard;
        //initialize the DebugConsole
        initialize();

        //this adds the debugPanel to the debugConsole
        debugPanel = new DebugPanel(wall);
        this.add(debugPanel,BorderLayout.CENTER);

        //arrange so that all components fit together in the debugConsole
        this.pack();
    }

    /**
     * initialize the DebugConsole as a JDialog
     */
    private void initialize(){
        //so that debugConsole has to be closed to access the gameBoard(Brick Destroy) behind
        this.setModal(true);
        this.setTitle(TITLE);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        //so that window event can be carried out
        this.addWindowListener(this);
        this.setFocusable(true);
    }

    /**
     * set the location of the debugConsole when opened (which is roughly the middle based on gameFrame)
     */
    private void setLocation(){
        int x = ((owner.getWidth() - this.getWidth()) / 2) + owner.getX();
        int y = ((owner.getHeight() - this.getHeight()) / 2) + owner.getY();
        this.setLocation(x,y);
    }

    /**
     * when debugConsole is activated, it will be roughly in the middle relative to gameFrame
     * the knob of slider is automatically set to the value of the ball
     * @param windowEvent status of window
     */
    @Override
    public void windowActivated(WindowEvent windowEvent) {
        setLocation();
        Ball b = wall.getBall();
        debugPanel.setValues(b.getSpeedX(),b.getSpeedY());
    }

    /**
     * when DebugConsole is closed, the whole gameBoard is reset again
     * @param windowEvent status of window
     */
    @Override
    public void windowClosing(WindowEvent windowEvent) {gameBoard.repaint();}

    @Override
    public void windowOpened(WindowEvent windowEvent) {}

    @Override
    public void windowClosed(WindowEvent windowEvent) {}

    @Override
    public void windowIconified(WindowEvent windowEvent) {}

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {}

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {}
}
