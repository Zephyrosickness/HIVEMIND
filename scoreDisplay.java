import javax.swing.*;
import java.awt.*;
import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
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

    public static void init(){
        final int SIZE = 700;
        //changes l&f to windows classic because im a basic bitch like that
        try{for(LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){if("Windows Classic".equals(info.getName())){UIManager.setLookAndFeel(info.getClassName());break;}}
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
        songInfoComponents(leftPanel, scorePanel, frame);

        // Add the scroll pane to the frame
        frame.add(scoreDisplay);

        frame.setVisible(true);
    }

    //panel to hold components
    private static void songInfoComponents(JPanel panel, JPanel scorePanel, JFrame frame){
        final Dimension minFillerSize = new Dimension(frame.getWidth()/128, frame.getHeight()/128);
        final Dimension prefFilerSize = new Dimension(frame.getWidth()/64, frame.getHeight()/64);
        final Dimension maxFillerSize = new Dimension(frame.getWidth()/32, frame.getHeight()/32);

        //the panel that holds all chart data (top half of the ui)
        final JPanel chartPanel = new JPanel();
        chartPanel.setLayout(new BoxLayout(chartPanel,BoxLayout.PAGE_AXIS));
        panel.add(chartPanel);


        //-label for chart dropdown
        chartPanel.add(new JLabel("Chart"));

        chartPanel.add(new Box.Filler(minFillerSize,prefFilerSize,maxFillerSize));

        //label that displays the jacket
        JLabel imageLabel = new JLabel();

        chartPanel.add(imageLabel);

        //chart select dropdown
        songSelect = new JComboBox<>(charts);
        chartPanel.add(songSelect);

        //-label for notecount
        JLabel noteCount = new JLabel("Max Combo:");
        chartPanel.add(noteCount);

        //-label for chart constant
        JLabel chartConstant = new JLabel("Chart Constant:");
        chartPanel.add(chartConstant);

        //-label for perfect pure info
        //panel.add(new JLabel("*Note that this doesn't use perfect PUREs, so scores may be slightly off."));
        final JPanel labelPanel = new JPanel(new BorderLayout());
        panel.add(labelPanel);

        final JPanel buttonPanel = new JPanel(new BorderLayout());
        panel.add(buttonPanel);

        //filter option labels --

        //label for far filter
        JLabel farLabel = new JLabel("FAR count");
        labelPanel.add(farLabel);

        //label for lost filter
        JLabel lostLabel = new JLabel("LOST count");
        labelPanel.add(lostLabel);

        //label for min score
        JLabel scoreLabel = new JLabel("Minimum score");
        labelPanel.add(scoreLabel);

        //label for sorter
        JLabel sortLabel = new JLabel("Sort by");
        labelPanel.add(sortLabel);

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
        chartPanel.add(difficultySelect);

        //charts




        //filter dropdowns


        //dropdown for operator on far count
        final JComboBox<String> farOperator = new JComboBox<>(operators);
        buttonPanel.add(farOperator);

        //dropdown for operator on miss count
        final JComboBox<String> missOperator = new JComboBox<>(operators);
        buttonPanel.add(missOperator);

        //select sorting methodology dropdown
        final JComboBox<String> sorter = new JComboBox<>(sorts);
        buttonPanel.add(sorter);

        //filter fields--

        //field for far count
        JTextField farField = new JTextField("0");
        buttonPanel.add(farField);

        //field for lost count
        JTextField lostField = new JTextField("0");
        buttonPanel.add(lostField);

        //field for minimum score
        JTextField scoreField = new JTextField("0");
        buttonPanel.add(scoreField);

        //specify cc for random song
        JTextField ccMin = new JTextField("0");
        buttonPanel.add(ccMin);

        JTextField ccMax = new JTextField("0");
        buttonPanel.add(ccMax);

        //BUTTONS--

        //checkbox if ur using that bitch from spiders thread
        JCheckBox toa = new JCheckBox("Using Toa Kozukata");
        buttonPanel.add(toa);

        //button to run score calcs
        JButton run = new JButton("Find scores");
        buttonPanel.add(run);

        //button to select random song
        JButton randomize = new JButton("Select Random");
        buttonPanel.add(randomize);

        //refreshes on initalization
        refresh((String) songSelect.getSelectedItem(), imageLabel, noteCount, chartConstant, (String)Objects.requireNonNull(difficultySelect.getSelectedItem()));

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

            if(maximum>11.1){maximum=11.1;}

            if(minimum>11.1){minimum=11.1;}


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

            scoreCalc.calcScore(minScore, far, miss, farOp, missOp, toaStatus, cc, combo);

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
    private static void refresh(String selected, JLabel imageLabel, JLabel noteCount, JLabel chartConstant, String difficulty){
        //gets the string of jacket file location
            songAttributes(selected);

            //sets up jacket
            String check = jacketCheck(selected);
            Path path = Paths.get("./assets/"+check +".jpg");
            //appends _byd to the target jacket if the difficulty is byd
            if (difficulty.equals("BYD")) {path = Paths.get("./assets/"+check +"_byd.jpg");}

            File jacket = new File(path.toString());

            //System.out.println("\nimage checksum!!! [!DEBUG ONLY!] \n------DETAILS------\nJACKET: "+jacket+" EXISTS?: "+Files.exists(path));

        //if the jacket doesnt exist for whatever reason, (most of the time im just too lazy to add jackets for new songs) swap to a placeholder and print an error
            if (!Files.exists(path)) {
                System.out.println("error reading image!\n------DETAILS------\nJACKET: " + jacket + " EXISTS?: " + Files.exists(path));
                jacket = new File("./assets/placeholder.jpg");
            }

        //reads the jacket and displays it to ui
        try{
            BufferedImage img = ImageIO.read(jacket);

            int scaledWidth = 325;
            int scaledHeight = -1;  //set to -1 so aspect ratio will be preserved
            Image resizedImg = img.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

            //replaces jacket
            ImageIcon imageIcon = new ImageIcon(resizedImg);
            imageLabel.setIcon(imageIcon);
            //error handler in case of invalid url
        }catch (IOException e){System.out.println("Error reading image!\n------DETAILS------\nERROR DETAILS: "+e.getMessage()+"JACKET: "+jacket);}


        //update constant/combo
        noteCount.setText("Max Combo: " + combo);
        chartConstant.setText("Chart Constant: " + cc);


    }

    //i dont remember how this works
    //genuinely i dont
    protected static class ScoreTextArea extends JTextArea {
        double score;
        double far;
        double miss;

        private ScoreTextArea(String text, double score, int far, int miss) {
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

        private double getScore() {
            return score;
        }
        private int getFar() {
            return (int)far;
        }
        private int getMiss() {
            return (int)miss;
        }
    }

    protected static void importComponent(double score, int pure, int far, int miss, double rating){
        //adds judgement data and score to array
        String text = "Score: " + (int) score + "\nPURE: " + pure + "\nFAR: " + far + "\nLOST: " + miss +"\nPlay Rating: "+rating;
        ScoreTextArea textArea = new ScoreTextArea(text, score, far, miss);
        scoreTextArray.add(textArea);

    }

    private static void songAttributes(String selected){
        Chart selectedChartObject = chartMap.get(selected);

        cc = selectedChartObject.cc;
        combo = selectedChartObject.combo;

    }
}
