import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.UIManager.LookAndFeelInfo;
import java.util.ArrayList;
import java.util.List;

//contains most graphical data
//welcome to hell

public class worldDisplay {
    //init var
    public static List<ScoreTextArea> scoreTextArray = new ArrayList<>();
    public static double cc;
    public static JPanel ratingPanel;
   //public static List<ScoreTextArea> pttTextArray = new ArrayList<>();

    //ppl keep saying static vars are bad and i mean thats probably true because of something something nerd stuff idk about but um im bad at coding sorry
    public static void init() {
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
            System.out.println("error with look and feel");
        }


        // create a window
        JFrame frame = new JFrame("Babaroque");
        frame.setSize(700, 700);

        //score display

        //make new panel this will have the ratings in it
        ratingPanel = new JPanel();
        ratingPanel.setLayout(new BoxLayout(ratingPanel, BoxLayout.Y_AXIS));

        // Create a JScrollPane and add the content panel to it
        JScrollPane ratingDisplay = new JScrollPane(ratingPanel);
        ratingDisplay.setBounds(350, 0, 325, 650);

        // Add the scroll pane to the frame
        frame.add(ratingDisplay);

        //main panel to hold components
        JPanel panel = new JPanel();
        panel.setLayout(null);

        //adds panel
        panel.setBounds(0, 0, 350, 700);
        frame.add(panel);

        frame.setResizable(false);
        panelAdd(panel, ratingPanel);
        frame.setVisible(true);

    }


    public static void panelAdd(JPanel panel, JPanel ratingPanel) {


        ///LABELS



        //label for cc filter
        JLabel ccLabel = new JLabel("Chart Constant");
        ccLabel.setBounds(25, 55, 150, 25);
        panel.add(ccLabel);

        //label for steps
        JLabel stepLabel = new JLabel("Steps");
        stepLabel.setBounds(25, 85, 80, 25);
        panel.add(stepLabel);

        //label for score
        JLabel scoreLabel = new JLabel("Score");
        scoreLabel.setBounds(25, 115, 80, 25);
        panel.add(scoreLabel);




        //DROPDOWNS



        //array vars (dropdown options)

        //for op dropdowns
        String[] operators = {"Any","<", ">"};



        //filter dropdowns




        //dropdown for operator on cc count
        final JComboBox<String> ccOperator = new JComboBox<>(operators);
        ccOperator.setBounds(100, 55,  50, 25);
        panel.add(ccOperator);

        //dropdown for operator on steps
        final JComboBox<String> stepOperator = new JComboBox<>(operators);
        stepOperator.setBounds(70, 85,  50, 25);
        panel.add(stepOperator);

        //dropdown for operator on score
        final JComboBox<String> scoreOperator = new JComboBox<>(operators);
        scoreOperator.setBounds(70, 115,  50, 25);
        panel.add(scoreOperator);



        //TEXT FIELDS





        //filter fields

        //field for cc
        JTextField ccField = new JTextField("0");
        ccField.setBounds(149, 55, 25, 25);
        panel.add(ccField);

        //field for steps
        JTextField stepField = new JTextField("0");
        stepField.setBounds(119, 85, 25, 25);
        panel.add(stepField);

        //field for min score
        JTextField scoreField = new JTextField("0");
        scoreField.setBounds(119, 115, 25, 25);
        panel.add(scoreField);

        ///BUTTONS

        //button to run score calcs
        JButton run = new JButton("Find scores");
        run.setBounds(25, 190, 100, 25);
        panel.add(run);

        //button to clear
        JButton clear = new JButton("clear");
        clear.setBounds(25, 220, 100, 25);
        panel.add(clear);

                //ACTIONS

        //load chart (songSelect dropdown)
        run.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


                double targetCC = Double.parseDouble(ccField.getText());
                String scoreString =(String)scoreField.getText();
                double targetStep = Double.parseDouble(stepField.getText());
                String ccOp = (String)ccOperator.getSelectedItem();
                String scoreOp = (String)scoreOperator.getSelectedItem();
                String stepOp = (String)stepOperator.getSelectedItem();

                //add ptt data

                worldCalc.calc(targetCC, scoreString, targetStep, ccOp, scoreOp, stepOp);

                //add sorted components to the ratingPanel
                for (ScoreTextArea textAreas : scoreTextArray) {
                    ratingPanel.add(textAreas);
                    System.out.println("WE STILL HERE");
                }

                //update the scroll pane after adding components
                ratingPanel.revalidate();
                ratingPanel.repaint();
            }
        });

        clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                ratingPanel.removeAll();

                ratingPanel.revalidate();
                ratingPanel.repaint();

            }
        });
    }

    //holds data for each play listing
    //i still dont know how this works :D

    public static class ScoreTextArea extends JTextArea {
        public ScoreTextArea(String text) {
            //variables for each score container
            super(text);
            setLineWrap(true);
            setWrapStyleWord(true);
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
            //setMaximumSize(new Dimension(300, 60));
        }
    }

        //i still dont know how this works :D
    public static void importComponent(int score, double cc, double step, int stat){

        //adds judgement data and score to array
        String text = "Score: " + score + "\n Chart Constant: " + cc +"\n Steps: "+step+"\n STEP stat: "+stat;
        ScoreTextArea textArea = new ScoreTextArea(text);
        scoreTextArray.add(textArea);
    }

    public static void clear(JPanel panel){
        panel.removeAll();

        panel.revalidate();
        panel.repaint();
    }
}
