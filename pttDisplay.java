import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.UIManager.LookAndFeelInfo;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

//contains most graphical data
//welcome to hell

public class pttDisplay {
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
        JFrame frame = new JFrame("Chronicles");
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


        //label for rating filter
        JLabel ratingLabel = new JLabel("Rating");
        ratingLabel.setBounds(25, 25, 150, 25);
        panel.add(ratingLabel);

        //label for cc filter
        JLabel ccLabel = new JLabel("Chart Constant");
        ccLabel.setBounds(25, 55, 150, 25);
        panel.add(ccLabel);

        //label for score
        JLabel scoreLabel = new JLabel("Score");
        scoreLabel.setBounds(25, 85, 80, 25);
        panel.add(scoreLabel);

        //label for sorter
        JLabel sortLabel = new JLabel("Sort by");
        sortLabel.setBounds(25, 115, 80, 25);
        panel.add(sortLabel);



        //DROPDOWNS



        //array vars (dropdown options)

        //for op dropdowns
        String[] operators = {"=","<", ">", "Any"};

        //for sort dropdown
        String[] sorts = {"Score","Rating", "Chart Constant"};


        //filter dropdowns


        //dropdown for operator on rating count
        final JComboBox<String> ratingOperator = new JComboBox<>(operators);
        ratingOperator.setBounds(70, 25,  50, 25);
        panel.add(ratingOperator);

        //dropdown for operator on cc count
        final JComboBox<String> ccOperator = new JComboBox<>(operators);
        ccOperator.setBounds(100, 55,  50, 25);
        panel.add(ccOperator);

        //dropdown for operator on score
        final JComboBox<String> scoreOperator = new JComboBox<>(operators);
        scoreOperator.setBounds(70, 85,  50, 25);
        panel.add(scoreOperator);

        //select sorting methodology dropdown
        final JComboBox<String> sorter = new JComboBox<>(sorts);
        sorter.setBounds(65, 115,  100, 25);
        panel.add(sorter);



        //TEXT FIELDS





        //filter fields


        //field for rating count
        JTextField ratingField = new JTextField("0");
        ratingField.setBounds(119, 25, 25, 25);
        panel.add(ratingField);

        //field for cc
        JTextField ccField = new JTextField("0");
        ccField.setBounds(149, 55, 25, 25);
        panel.add(ccField);

        //field for min score
        JTextField scoreField = new JTextField("0");
        scoreField.setBounds(119, 85, 25, 25);
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
        run.addActionListener(e -> {

            clear(ratingPanel);

            //update the scroll pane after adding components
            ratingPanel.revalidate();
            ratingPanel.repaint();

            //add ptt data

            pttCalc.calc(Double.parseDouble(ratingField.getText()), Double.parseDouble(ccField.getText()), scoreField.getText(), (String)ratingOperator.getSelectedItem(), (String)ccOperator.getSelectedItem(), (String)scoreOperator.getSelectedItem());

            //sort results based on the sort option
            switch((String) Objects.requireNonNull(sorter.getSelectedItem())) {
                case "Score":
                    scoreTextArray.sort(Comparator.comparingDouble(ScoreTextArea::getScore).reversed());
                    break;
                case "Chart Constant":
                    scoreTextArray.sort(Comparator.comparingDouble(ScoreTextArea::getCC).reversed());
                    break;
                case "Rating":
                    scoreTextArray.sort(Comparator.comparingDouble(ScoreTextArea::getRating).reversed());
                    break;
            }

            //removes all components from ratingPanel so scores dont stack
            ratingPanel.removeAll();

            //add sorted components to the ratingPanel
            for (ScoreTextArea textAreas : scoreTextArray) {
                ratingPanel.add(textAreas);
            }

            //update the scroll pane after adding components
            ratingPanel.revalidate();
            ratingPanel.repaint();
        });

        clear.addActionListener(e -> {

            ratingPanel.removeAll();

            ratingPanel.revalidate();
            ratingPanel.repaint();

        });
    }

    //holds data for each play listing

    //i dont remember what this does, genuinely
    public static class ScoreTextArea extends JTextArea {
        private final int score;
        private final double cc;
        private final double rating;

        public ScoreTextArea(String text, double cc, double rating, int score) {
            //variables for each score container
            super(text);
            this.score = score;
            this.rating = rating;
            this.cc = cc;
            setLineWrap(true);
            setWrapStyleWord(true);
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
            //setMaximumSize(new Dimension(300, 60));
        }

        public int getScore() {
            return score;
        }
        public double getCC() {
            return cc;
        }
        public double getRating() {
            return rating;
        }
    }

    public static void importComponent(int score, double rating, double cc){

        //adds judgement data and score to array
        String text = "Score: " + score + "\n Chart Constant: " + cc +"\n Play Rating: "+rating;
        ScoreTextArea textArea = new ScoreTextArea(text, cc, rating, score);
        scoreTextArray.add(textArea);

    }

    public static void clear(JPanel panel){
        panel.removeAll();

        panel.revalidate();
        panel.repaint();
    }
}
