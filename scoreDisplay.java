import javax.swing.*;
import java.awt.*;
import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

//contains most graphical data
//welcome to hell

public class scoreDisplay extends Database {
    //init var
    protected static int combo;
    protected static double cc;

    protected static List<ScoreTextArea> scoreTextArray = new ArrayList<>();
    protected static JComboBox<String> songSelect;

    protected static String[] charts = (Database.chartNames).toArray(new String[Database.chartNames.size()]);

    protected static ArrayList<Chart> chartAttributes = Database.allCharts;

    protected static Map<String, Chart> chartMap = Database.chartMapFTR;

    /*ppl keep saying static vars are bad and I mean that's probably true because of something something nerd stuff IDK about but um im bad at coding and I keep
    having problems IDK how to fix without static vars idek why they're bad but um maybe one day ill be good at coding and it will be fixed*/

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
        JFrame frame = new JFrame("The Memory Archive");
        frame.setSize(700, 700);

        //score display

        //make new panel this will have the scores in it
        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));

        // Create a JScrollPane and add the content panel to it
        JScrollPane scoreDisplay = new JScrollPane(scorePanel);
        scoreDisplay.setBounds(350, 0, 325, 650);

        // Add the scroll pane to the frame
        frame.add(scoreDisplay);

        //main panel to hold components
        JPanel panel = new JPanel();

        //adds panel
        frame.add(panel);
        panel.setBounds(0, 0, 350, 700);
        frame.setResizable(false);
        songInfoComponents(panel, scorePanel);
        frame.setVisible(true);
    }

    //panel to hold components
    public static void songInfoComponents(JPanel panel, JPanel scorePanel) {

        // set the layout manager to null for absolute positioning
        panel.setLayout(null);

        ///LABELS

        //chart info/select labels --

        //-label for chart dropdown
        JLabel chartTitle = new JLabel("Chart");
        chartTitle.setBounds(112, 5, 80, 25);
        panel.add(chartTitle);

        //label that displays the jacket
        JLabel imageLabel = new JLabel();
        imageLabel.setBounds(25, 70, 200, 200);
        panel.add(imageLabel);


        //-label for notecount
        JLabel noteCount = new JLabel("Max Combo:");
        noteCount.setBounds(25, 410, 325, 25);
        panel.add(noteCount);

        //-label for chart constant
        JLabel chartConstant = new JLabel("Chart Constant:");
        chartConstant.setBounds(25, 430, 325, 25);
        panel.add(chartConstant);

        //-label for perfect pure info
        JLabel pureInfo = new JLabel("*Note that this doesn't use perfect PUREs, so scores may be slightly off.");
        pureInfo.setBounds(20, 630, 500, 25);
        panel.add(pureInfo);


        //filter option labels --

        //label for far filter
        JLabel farLabel = new JLabel("FAR count");
        farLabel.setBounds(25, 450, 150, 25);
        panel.add(farLabel);

        //label for lost filter
        JLabel lostLabel = new JLabel("LOST count");
        lostLabel.setBounds(25, 480, 150, 25);
        panel.add(lostLabel);

        //label for min score
        JLabel scoreLabel = new JLabel("Minimum score");
        scoreLabel.setBounds(25, 510, 150, 25);
        panel.add(scoreLabel);

        //label for sorter
        JLabel sortLabel = new JLabel("Sort by");
        sortLabel.setBounds(25, 540, 80, 25);
        panel.add(sortLabel);

        //DROPDOWNS

        //array vars (dropdown options) --

        //diff name
        String[] difficultyList = {"FTR/ETR", "BYD"};
        //for op dropdowns
        String[] operators = {"Any", "=","<"};
        //for sort dropdown
        String[] sorts = {"Score","FAR count", "LOST count"};

        //chart select dropdowns --

        //difficulty select dropdown
        JComboBox<String> difficultySelect = new JComboBox<>(difficultyList);
        difficultySelect.setBounds(250, 25, 75, 25);
        panel.add(difficultySelect);

        //charts

        //chart select dropdown
        songSelect = new JComboBox<>(charts);
        songSelect.setBounds(50, 25, 200, 25);
        panel.add(songSelect);


        //filter dropdowns


        //dropdown for operator on far count
        final JComboBox<String> farOperator = new JComboBox<>(operators);
        farOperator.setBounds(74, 450,  50, 25);
        panel.add(farOperator);

        //dropdown for operator on miss count
        final JComboBox<String> missOperator = new JComboBox<>(operators);
        missOperator.setBounds(80, 480,  50, 25);
        panel.add(missOperator);

        //select sorting methodology dropdown
        final JComboBox<String> sorter = new JComboBox<>(sorts);
        sorter.setBounds(65, 540,  100, 25);
        panel.add(sorter);

        //filter fields--

        //field for far count
        JTextField farField = new JTextField("0");
        farField.setBounds(120, 450, 25, 25);
        panel.add(farField);

        //field for lost count
        JTextField lostField = new JTextField("0");
        lostField.setBounds(128, 480, 25, 25);
        panel.add(lostField);

        //field for minimum score
        JTextField scoreField = new JTextField("0");
        scoreField.setBounds(95, 510, 75, 25);
        panel.add(scoreField);

        //specify cc for random song
        JTextField ccMin = new JTextField("0");
        ccMin.setBounds(200, 450, 25, 25);
        panel.add(ccMin);

        JTextField ccMax = new JTextField("0");
        ccMax.setBounds(235, 450, 25, 25);
        panel.add(ccMax);

        ///BUTTONS--

        //checkbox if ur using that bitch from spiders thread
        JCheckBox toa = new JCheckBox("Using Toa Kozukata");
        toa.setBounds (25,570,125,25);
        panel.add(toa);

        //button to run score calcs
        JButton run = new JButton("Find scores");
        run.setBounds(25, 600, 100, 25);
        panel.add(run);

        //button to select random song
        JButton randomize = new JButton("Select Random");
        randomize.setBounds(200, 410, 125, 25);
        panel.add(randomize);

        //refreshes on initalization
        refresh((String)songSelect.getSelectedItem(), imageLabel, noteCount, chartConstant, (String)difficultySelect.getSelectedItem());

        //ACTIONS--

        //loads chart / refreshes song data (songSelect dropdown)
        songSelect.addActionListener(e -> refresh((String) songSelect.getSelectedItem(), imageLabel, noteCount, chartConstant, (String)difficultySelect.getSelectedItem()));

        //load byd/ftr charts (diffSelect dropdown)
        difficultySelect.addActionListener(e -> {
            ArrayList<String> chartsTemp;
                if(difficultySelect.getSelectedItem()=="BYD"){
                    chartsTemp = Database.chartNamesBYD;
                    chartMap = Database.chartMapBYD;
                }else{
                    chartsTemp = Database.chartNames;
                    chartMap = Database.chartMapFTR;
                }
            int length = chartsTemp.size();
            charts = chartsTemp.toArray(new String[length]);
            songSelect.setModel(new JComboBox<>(charts).getModel());
        });

        //selects random song
        randomize.addActionListener(e -> {
            //init var
            Random rand = new Random();
            double minimum = Double.parseDouble(ccMin.getText());
            double maximum = Double.parseDouble(ccMax.getText());

            //error handler. prevents from typing the minimum value as 10000 or smth
            //value set to 11.1 bc it's the largest value that both future/beyond charts have
            //could make it 12 for byd and 11.3 for ftr, but I don't care enough for an error handler

            if(maximum>11.1){
                maximum=11.1;
            }

            if(minimum>11.1){
                minimum=11.1;
            }


            //selects random number and sets chart index # to the random num. if not within the bounds of specified cc, reroll
            //songSelect.setSelectedIndex(rand.nextInt(selectedChart.length));
            int index = rand.nextInt(charts.length);
            double selectedCC = (chartAttributes.get(index)).cc;

            while(!(selectedCC <= maximum && selectedCC >= minimum)){
                index = rand.nextInt(charts.length);
                selectedCC = (chartAttributes.get(index)).cc;

                if(minimum>maximum){
                    System.out.println("!! random gen quirked up rn... go check that out");
                    break;
                }
            }
            songSelect.setSelectedIndex(index);
            refresh((String)songSelect.getSelectedItem(), imageLabel, noteCount, chartConstant, (String)difficultySelect.getSelectedItem());
        });

        //calculates scores (run button)
        run.addActionListener(e -> {
            //clear the board before running new calcs
            scoreTextArray.clear();

            //init var (reading off fields and dropdowns)
            int far = Integer.parseInt(farField.getText());
            int miss = Integer.parseInt(lostField.getText());
            String minScore = (scoreField.getText());
            String farOp = (String)farOperator.getSelectedItem();
            String missOp = (String)missOperator.getSelectedItem();
            boolean toaStatus = toa.isSelected();

            //runs func to calc score
            /*THIS SHIT USED TO BE SO LONG AND AWFUL AND I HAD NO IDEA HOW TO GET IT INTO ITS OWN CLASS AND IT WAS OVER HALF THE ENTIRE PROGRAM BUT
             * I DID NOW IT'S WAY SHORTER AND IN ITS OWN CLASS I'M SUCH A GENIUS I'M LITERALLY A PRODIGY OH MY GOD
             */

            System.out.println("run");
            scoreCalc.calcScore(minScore, far, miss, farOp, missOp, toaStatus);

            //sort results based on the sort option
            switch((String) Objects.requireNonNull(sorter.getSelectedItem())) {
                case "Score":
                    scoreTextArray.sort(Comparator.comparingDouble(ScoreTextArea::getScore).reversed());
                    break;
                case "FAR count":
                    scoreTextArray.sort(Comparator.comparingInt(ScoreTextArea::getFar).reversed());
                    break;
                case "LOST count":
                    scoreTextArray.sort(Comparator.comparingInt(ScoreTextArea::getMiss).reversed());
                    break;
            }

            //removes all components from scorePanel so scores dont stack on next run
            scorePanel.removeAll();

            //add sorted components to the scorePanel
            for (ScoreTextArea textAreas : scoreTextArray) {
                scorePanel.add(textAreas);
            }

            //update the scroll pane after adding components
            scorePanel.revalidate();
            scorePanel.repaint();

        });


    }


    //refreshes song data
    public static void refresh(String selected, JLabel imageLabel, JLabel noteCount, JLabel chartConstant, String difficulty) {

        //sets up jacket
        String check = jacketCheck(selected);

        Path path = Paths.get("./assets/"+check+".jpg");
        Path pathBYD = Paths.get("./assets/"+check+"_byd"+".jpg");

        File jacket = new File(path.toString());

        if (!Files.exists(path)){
            System.out.println("error reading image!\n------DETAILS------\nJACKET: "+jacket+" EXISTS?: "+Files.exists(path));
            jacket = new File("./assets/placeholder.jpg");
        }else{
            if(difficulty.equals("BYD")&&Files.exists(pathBYD)){
                jacket = new File(pathBYD.toString());
            }
        }

        Chart selectedChartObject = chartMap.get(selected);

        double cc = selectedChartObject.cc;
        int combo = selectedChartObject.combo;


        try {
            BufferedImage img = ImageIO.read(jacket);

            int scaledWidth = 325;
            int scaledHeight = -1;  //set to -1 so aspect ratio will be preserved
            Image resizedImg = img.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

            //replaces jacket
            ImageIcon imageIcon = new ImageIcon(resizedImg);
            imageLabel.setIcon(imageIcon);
            imageLabel.setBounds(12, 70, scaledWidth, resizedImg.getHeight(null));
            //error handler in case of invalid url
        } catch (IOException e) {
            System.out.println("Error reading image!\n------DETAILS------\nERROR DETAILS: "+e.getMessage()+"JACKET: "+jacket);
        }

        //update constant/combo

        noteCount.setText("Max Combo: " + combo);
        chartConstant.setText("Chart Constant: " + cc);

    }

    //i dont remember how this works
    //genuinely i dont
    public static class ScoreTextArea extends JTextArea {
        double score;
        double far;
        double miss;

        public ScoreTextArea(String text, double score, int far, int miss) {
            //variables for each score container
            super(text);
            this.score = score;
            this.far = far;
            this.miss = miss;
            setLineWrap(true);
            setWrapStyleWord(true);
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
            //setMaximumSize(new Dimension(300, 60));
        }

        public double getScore() {
            return score;
        }
        public int getFar() {
            return (int)far;
        }
        public int getMiss() {
            return (int)miss;
        }
    }

    public static void importComponent(double score, int pure, int far, int miss, double rating){
        //adds judgement data and score to array
        String text = "Score: " + (int) score + "\nPURE: " + pure + "\nFAR: " + far + "\nLOST: " + miss +"\nPlay Rating: "+rating;
        ScoreTextArea textArea = new ScoreTextArea(text, score, far, miss);
        scoreTextArray.add(textArea);

    }
}
