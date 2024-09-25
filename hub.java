import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.UIManager.LookAndFeelInfo;

public class hub {
       public static void main(String[] args) {
        //changes l&f to windows classic because im a basic bitch like that
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows Classic".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            //error handler for l&f in case something doesnt work
            System.out.println("error with look and feel!");
            System.out.println("------DETAILS------");
            System.out.println(e.getMessage());

        }

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

        //button to run score calcr
        JButton scoreButton = new JButton("Score Calculator");
        scoreButton.setBounds(25, 25, 150, 25);
        panel.add(scoreButton);

        //runs the function to open the scorecalc program
        scoreButton.addActionListener(e -> scoreDisplay.init());

        //button to run ptt calcs
        JButton pttButton = new JButton("Play Rating Calculator");
        pttButton.setBounds(25, 50, 150, 25);
        panel.add(pttButton);
                
        //runs the function to open the pttcalc program
        pttButton.addActionListener(e -> pttDisplay.init());

        //button to run world calcs
        JButton worldButton = new JButton("World Mode Calculator");
        worldButton.setBounds(25, 75, 150, 25);
        panel.add(worldButton);
                        
        //runs the function to open the pttcalc program
        worldButton.addActionListener(e -> worldDisplay.init());
    }
}
