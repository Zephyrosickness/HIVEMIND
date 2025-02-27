import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

public class Hub {
    final public static boolean DEBUG = false; ///debug for ui
    final public static boolean DEBUG_CALC = false; //debug for calc

    public static void main(String[] args) {
        //changes l&f to windows classic because im a basic bitch like that
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows Classic".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {System.out.println("error with look and feel!\n------DETAILS------\n"+e.getMessage());}

        new Database(); //inits chartlist

        // create a window
        JFrame frame = new JFrame("HIVEMIND");
        frame.setSize(200, 150);

        //main panel to hold components
        JPanel panel = new JPanel();

        //adds panel
        frame.add(panel);
        panel.setBounds(0, 0, 200, 75);
        frame.setResizable(false);
        buttons(panel);
        frame.setVisible(true);
    }

    //panel to hold components
    public static void buttons(JPanel panel) {

        // set the layout manager to null for absolute positioning
        panel.setLayout(null);

        //button to run score calc
        JButton scoreButton = new JButton("Score Calculator");
        scoreButton.setBounds(25, 25, 150, 25);
        panel.add(scoreButton);

        //runs the function to open the scorecard program
        scoreButton.addActionListener(e -> scoreDisplay.init());

        //button to run ptt calcs
        JButton pttButton = new JButton("Play Rating Calculator");
        pttButton.setBounds(25, 50, 150, 25);
        panel.add(pttButton);

        //runs the function to open the  pttcalc program
        pttButton.addActionListener(e -> pttDisplay.init());

    }
}
