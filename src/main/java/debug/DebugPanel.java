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

import brickWall.Wall;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;



/**
 * DebugPanel is a panel that contains the 2 buttons and 2 sliders
 */
public class DebugPanel extends JPanel {

    //button skipLevel, resetBalls
    private JButton skipLevel;
    private JButton resetBalls;

    //slider ballXSpeed, ballYSpeed
    private JSlider ballXSpeed;
    private JSlider ballYSpeed;

    public DebugPanel(Wall wall){
        //this initializes the DebugPanel components
        initialize();

        //make 2 buttons with implementation
        skipLevel = makeButton("Skip Level",e -> wall.nextLevel());
        resetBalls = makeButton("Reset Balls",e -> wall.resetBallCount());

        //make 2 sliders with implementation
        ballXSpeed = makeSlider(-4,4,e -> wall.setBallXSpeed(ballXSpeed.getValue()));
        ballYSpeed = makeSlider(-4,4,e -> wall.setBallYSpeed(ballYSpeed.getValue()));

        //this adds buttons component
        this.add(skipLevel);
        this.add(resetBalls);

        //this adds sliders component
        this.add(ballXSpeed);
        this.add(ballYSpeed);

    }

    /**
     * set layout of panel to fit 2 buttons, 2 columns
     */
    private void initialize(){
        this.setLayout(new GridLayout(2,2));
    }

    /**
     * Construct button with specified title and color
     *
     * @param title title of button
     * @param e action events
     * @return JButton
     */
    private JButton makeButton(String title, ActionListener e){
        JButton out = new JButton(title);
        out.addActionListener(e);
        out.setForeground(Color.WHITE);
        out.setBackground(new Color(255,153,153));
        return out;
    }

    /**
     * Construct slider with specified min, max value and color
     *
     * @param min minimum value of slider
     * @param max maximum value of slider
     * @param e change events
     * @return JSlider
     */
    private JSlider makeSlider(int min, int max, ChangeListener e){
        JSlider out = new JSlider(min,max);
        out.setMajorTickSpacing(1);//set sections with 1 space in the slider
        out.setSnapToTicks(true);//set the value of knob to the closest tick
        out.setPaintTicks(true);//display the tick
        out.addChangeListener(e);
        out.setForeground(Color.BLUE);
        out.setBackground(new Color(255,204,204));
        return out;
    }

    /**
     * when debugpanel is opened, the knob of slider is automatically set to the value of the ball
     *
     * @param x speedX
     * @param y speedY
     */
    public void setValues(int x,int y){
        ballXSpeed.setValue(x);
        ballYSpeed.setValue(y);
    }

}
