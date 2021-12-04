package gameMain;

import javax.swing.*;
import java.awt.*;

import static java.awt.font.TextAttribute.FONT;

public class InfoFrame extends JFrame {

    private static final int DEF_WIDTH = 550;
    private static final int DEF_HEIGHT = 450;
    private static final String DEF_TITLE = "Info Guide";
    private JLabel background;
    private ImageIcon kirbyicon;
    private JLabel label;
    private Font infoFont;

    //private Image background;

    public InfoFrame(){
        InfoGuideText();
        initialize();
        kirbyicon = new ImageIcon(getClass().getResource("/kirby_info.png"));
        background = new JLabel(kirbyicon);
        background.setSize(new Dimension(DEF_WIDTH,DEF_HEIGHT));
        //this.add(background);
        background.add(label);
        this.add(background);
    }

    public void initialize(){
        //this set the title and visible of the frame to true
        this.setTitle(DEF_TITLE);
        this.setPreferredSize(new Dimension(DEF_WIDTH,DEF_HEIGHT));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setResizable(false);
        this.setLayout(null);
        this.autoLocate();
        this.setVisible(true);
    }

    private void autoLocate(){
        //this get the screenSize of monitor
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (size.width - this.getWidth()) / 2;
        int y = (size.height - this.getHeight()) / 2;
        this.setLocation(x,y);
    }

    private void InfoGuideText(){
        label = new JLabel();
        label.setText("<html>" + "<h1>Kirby Brick Destroyer</h1>" +
                "<b>Player has one GOAL which is to destroy all bricks wall with a small ball</b><br/>" +
                "<b>Player mission is to make sure ball does not fall onto the ground</b><br/>" +
                "<b>Player only has 3 ball lives and player lose if he/she loses all balls</b><br/>" +
                "<br/><b>Control Commands:</b><br/>" +
                "<b>SPACE: </b> start/pause the game<br/>" +
                "<b>A: </b> move left<br/>" +
                "<b>D: </b> move right<br/>" +
                "<b>ESC: </b> enter/exit pause menu<br/>" +
                "<b>ALT+SHIFT+F1: </b> open debug console<br/>" +
                "<br/><h2>GOOD LUCK PLAYER !! ;)<h2>"
        );
        label.setFont(new Font(null, Font.PLAIN, 15));
        label.setBounds(30,30, 450,350);
        //label.setHorizontalTextPosition(JLabel.CENTER);
    }

    /*
    public void paint(Graphics g){drawBackground((Graphics2D)g);}

    private void drawBackground(Graphics2D g){
        background = new ImageIcon(getClass().getResource("/kirby_info.png")).getImage();
        g.drawImage(background,0,0,DEF_WIDTH,DEF_HEIGHT,null);
    }

     */
}
