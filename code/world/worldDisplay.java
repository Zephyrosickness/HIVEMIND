package code.world;

import javax.swing.*;
import java.awt.*;


//unfinished
//placeholder code copy pasted from scoredisplay class

public class worldDisplay{
    public static void init(){
        final int SIZE = 700;
        //changes l&f to windows classic because im a basic bitch like that
        try{for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){if("Windows Classic".equals(info.getName())){UIManager.setLookAndFeel(info.getClassName());break;}}
        }catch(Exception e){System.out.println("error with look and feel");}

        // create a window
        final JFrame frame = new JFrame("The Memory Archive");
        frame.setSize(SIZE, SIZE);
        frame.setLayout(new GridLayout(1,2));

        //score display

        //i feel like this can be made more efficient vvv
        //make new panel this will have the scores in it
        final JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));

        // Create a JScrollPane and add the content panel to it
        final JScrollPane scoreDisplay = new JScrollPane(scorePanel);


        //main panel to hold panels in the left half
        //make this panel vertical and then max a panel on the bottom and then inside that have 2 more f***ing panels for the labels and the actual ui items
        final JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel,BoxLayout.PAGE_AXIS));

        //adds panel
        frame.add(leftPanel);

        // Add the scroll pane to the frame
        frame.add(scoreDisplay);

        frame.setVisible(true);
    }
}
