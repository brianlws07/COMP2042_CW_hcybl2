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
package test;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;

//panel that contains the button and slider in the debugconsole (alt+shift+f1)
public class DebugPanel extends JPanel {

    private static final Color DEF_BKG = Color.WHITE;

    //button skipLevel, resetBalls
    private JButton skipLevel;
    private JButton resetBalls;

    //slider ballXSpeed, ballYSpeed
    private JSlider ballXSpeed;
    private JSlider ballYSpeed;

    private Wall wall;

    public DebugPanel(Wall wall){

        this.wall = wall;

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

    private void initialize(){
        //set background to white
        this.setBackground(DEF_BKG);
        //set layout to fit 2 buttons, 2 columns
        this.setLayout(new GridLayout(2,2));
    }

    private JButton makeButton(String title, ActionListener e){
        //initialize the buttons with title
        JButton out = new JButton(title);
        out.addActionListener(e);
        return  out;
    }

    private JSlider makeSlider(int min, int max, ChangeListener e){
        JSlider out = new JSlider(min,max);
        //set sections with 1 space in the slider
        out.setMajorTickSpacing(1);
        //set the value of knob to the closest tick
        out.setSnapToTicks(true);
        //display the tick
        out.setPaintTicks(true);
        out.addChangeListener(e);
        return out;
    }

    //when debugpanel is opened, the knob of slider is automatically set to the value of the ball
    public void setValues(int x,int y){
        ballXSpeed.setValue(x);
        ballYSpeed.setValue(y);
    }

}
