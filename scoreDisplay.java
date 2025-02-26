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

public class scoreDisplay extends Database{
    final private static HashMap<String, Component> componentMap = new HashMap<>();

    protected static List<ScoreTextArea> scoreTextArray = new ArrayList<>();
    protected static JComboBox<String> songSelect;

    protected static String[] charts = (Database.chartNames).toArray(new String[Database.chartNames.size()]);

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
        //filler sizes
        final Dimension minFillerSize = new Dimension(frame.getWidth()/128, frame.getHeight()/128);
        final Dimension prefFilerSize = new Dimension(frame.getWidth()/64, frame.getHeight()/64);
        final Dimension maxFillerSize = new Dimension(frame.getWidth()/32, frame.getHeight()/32);
        //dropdown assets
        final String[] difficultyList = {"FTR/ETR", "BYD"};
        final String[] operators = {"Any", "=","<"};
        final String[] sorts = {"Score","FAR count", "LOST count"};
        //all the items

        //the panel that holds all chart data (top half of the ui)
        final JPanel chartPanel = new JPanel();
        chartPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.add(chartPanel);

        //panel for everything else
        final JPanel bottomPanel = new JPanel(new GridLayout(0,3));
        panel.add(bottomPanel);

        //chart panel items ----

        chartPanel.add(new JLabel("Chart"));

        //difficulty select dropdown
        final JComboBox<String> difficultySelect = new JComboBox<>(difficultyList);
        chartPanel.add(difficultySelect);
        componentMap.put("Difficulty", difficultySelect);

        //chart jacket
        final JLabel imageLabel = new JLabel();
        chartPanel.add(imageLabel);
        componentMap.put("Jacket", imageLabel);

        //chart select dropdown
        songSelect = new JComboBox<>(charts);
        chartPanel.add(songSelect);
        componentMap.put("Chart", songSelect);

        //-label for notecount
        final JLabel noteCount = new JLabel("Max Combo:");
        chartPanel.add(noteCount);
        componentMap.put("Max Combo", noteCount);

        //-label for chart constant
        final JLabel chartConstant = new JLabel("Chart Constant:");
        chartPanel.add(chartConstant);
        componentMap.put("Chart Constant", chartConstant);


        //bottom panel items ---


        //dropdown for operator on far count
        final JComboBox<String> farOperator = new JComboBox<>(operators);
        farOperator.setName("Far Operator");

        //dropdown for operator on miss count
        final JComboBox<String> lostOperator = new JComboBox<>(operators);
        lostOperator.setName("Lost Operator");

        //select sorting methodology dropdown
        final JComboBox<String> sortSelection = new JComboBox<>(sorts);
        sortSelection.setName("Sort Selection");

        //filter fields--

        //field for far count
        final JTextField farField = new JTextField("0");
        farField.setVisible(false);
        farField.setEnabled(false);
        farField.setName("Far Count");

        //field for lost count
        final JTextField lostField = new JTextField("0");
        lostField.setVisible(false);
        lostField.setEnabled(false);
        lostField.setName("Lost Count");

        //field for minimum score
        final JTextField scoreField = new JTextField("0");
        scoreField.setName("Minimum Score");

        //specify cc for random song
        final JTextField ccMin = new JTextField("Min CC");
        ccMin.setName("Minimum CC");

        final JTextField ccMax = new JTextField("Max CC");
        ccMax.setName("Maximum CC");

        //BUTTONS--

        //checkbox if ur using that bitch from spiders thread
        final JCheckBox toa = new JCheckBox("Partner is Toa");
        toa.setName("Toa");

        //button to run score calcs
        final JButton run = new JButton("Find scores");

        //button to select random song
        final JButton randomize = new JButton("Select Random");

        //refreshes on initalization
        refresh();

        //ACTIONS--

        //loads chart / refreshes song data (songSelect dropdown)

        songSelect.addActionListener(_ -> refresh());

        //we use an array here because it allows u to change the order of the components without having to have them on certain lines. to reorder u just reorder the arraylist here
        Component[] itemsInOrder = new Component[]{
                new JLabel("FAR Count"), farOperator, farField,

                new JLabel("LOST Count"), lostOperator, lostField,

                new JLabel("Minimum score"), scoreField, new Box.Filler(minFillerSize, prefFilerSize, maxFillerSize),

                new JLabel("Sort By: "), sortSelection, new Box.Filler(minFillerSize, prefFilerSize, maxFillerSize),

                randomize, ccMin, ccMax,

                toa, run
        };

        for(Component i:itemsInOrder){
            bottomPanel.add(i);
            if(i.getName()!=null){componentMap.put(i.getName(), i);}
        }

        //load byd/ftr charts (diffSelect dropdown)

        difficultySelect.addActionListener(_ -> {
            songSelect.setModel(loadSongList());
            refresh();
        });


        //selects random song
        randomize.addActionListener(_ -> {});

        farOperator.addActionListener(_ -> {
            if(Objects.requireNonNull(farOperator.getSelectedItem()).toString().equals("Any")){
                farField.setEnabled(false);
                farField.setVisible(false);
            }else{
                farField.setEnabled(true);
                farField.setVisible(true);
            }
        });

        lostOperator.addActionListener(_ -> {
            if(Objects.requireNonNull(lostOperator.getSelectedItem()).toString().equals("Any")){
                lostField.setEnabled(false);
                lostField.setVisible(false);
            }else{
                lostField.setEnabled(true);
                lostField.setVisible(true);
            }
        });

        //calculates scores (run button)
        run.addActionListener(_ -> {
            //clear the board before running new calcs
            scoreTextArray.clear();

            //init var (reading off fields and dropdowns)
            Chart chart = getChart(getComponentValue("Chart"), getComponentValue("Difficulty"));
            int far = Integer.parseInt(lostField.getText());
            int miss = Integer.parseInt(lostField.getText());
            String minScore = (scoreField.getText());
            String farOp = (String)farOperator.getSelectedItem();
            String missOp = (String)lostOperator.getSelectedItem();
            boolean toaStatus = toa.isSelected();

            if(Hub.DEBUG){
                System.out.println("run called\ninput chart name: "+getComponentValue("Chart")+"\ndiff: "+getComponentValue("Difficulty"));
                if(chart!=null){
                    System.out.println("chart: "+chart.name);
                }
            }

            if(chart==null){
                System.out.println("error!! chart is null!! calculations have been prematurely ended.");
                return;
            }

            scoreCalc.calcScore(minScore, far, miss, farOp, missOp, toaStatus, chart.cc, chart.combo);

            //sort results based on the sort option
            switch((String) Objects.requireNonNull(sortSelection.getSelectedItem())) {
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
            for(ScoreTextArea textAreas:scoreTextArray){
                if(Hub.DEBUG){System.out.println("arrays are being added :3");}
                scorePanel.add(textAreas);
            }

            //update the scroll pane after adding components
            scorePanel.revalidate();
            scorePanel.repaint();

        });


    }

    //private static void setRandomChart(){
        //init var
        //final Random rand = new Random();
            /*
            error handler. prevents from typing the minimum value as 10000 or smth
            value set to 11.1 bc it's the largest value that both future/beyond charts have
            could make it 12 for byd and 11.3 for ftr, but I don't care enough for an error handler
            minmax 0 is min and minmax 1 is max
             */
        /*final double[] minMax = clamp(new double[]{Double.parseDouble(getComponentValue("Minimum CC")), Double.parseDouble(getComponentValue("Maximum CC"))},11.1);

        //selects random number and sets chart index # to the random num. if not within the bounds of specified cc, reroll
        //songSelect.setSelectedIndex(rand.nextInt(selectedChart.length));
        int index = rand.nextInt(charts.length);
        double selectedCC = (chartAttributes.get(index)).cc;

        while(!(selectedCC<=minMax[1]&&selectedCC>=minMax[0])){
            index = rand.nextInt(charts.length);
            selectedCC = (chartAttributes.get(index)).cc;

            if(minMax[0]>minMax[1]){
                System.out.println("!! random gen quirked up rn... go check that out");
                break;
            }
        }
        songSelect.setSelectedIndex(index);
        refresh(imageLabel, noteCount, chartConstant);
    }*/

    //used when changing from ftr to byd
    private static ComboBoxModel<String> loadSongList(){
        ArrayList<String> chartsTemp;
        final String selected = getComponentValue("Difficulty");

        if(selected.equals("BYD")){
            chartsTemp = Database.chartNamesBYD;
            chartMap = Database.chartMapBYD;
        }else{
            chartsTemp = Database.chartNames;
            chartMap = Database.chartMapFTR;
        }

        int length = chartsTemp.size();
        charts = chartsTemp.toArray(new String[length]);
        return new JComboBox<>(charts).getModel();
    }
    //refreshes song data
    private static void refresh(){

        //gets the string of jacket file location
        final String difficulty = getComponentValue("Difficulty");
        final Chart chart = getChart(getComponentValue("Chart"), difficulty);
        final JLabel imageLabel = (JLabel)componentMap.get("Jacket"); //this is the label that holds the icon that you are changing
        final JLabel noteCountLabel = (JLabel)componentMap.get("Max Combo"); //this is the label that holds cc data that you are changing
        final JLabel chartConstantLabel = (JLabel)componentMap.get("Chart Constant"); //this is the label that holds cc data that you are changing


        //sets up jacket
        String check = jacketCheck(chart.name);
        Path path = Paths.get("./assets/"+check +".jpg");
        //appends _byd to the target jacket if the difficulty is byd
        if(Objects.equals(chart.tier, "BYD")){path = Paths.get("./assets/"+check +"_byd.jpg");}

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

            //fix this later the height shouldnt be hardcoded
            int scaledWidth = 325;
            int scaledHeight = -1;  //set to -1 so aspect ratio will be preserved
            Image resizedImg = img.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

            //replaces jacket
            ImageIcon imageIcon = new ImageIcon(resizedImg);
            imageLabel.setIcon(imageIcon);
            //error handler in case of invalid url
        }catch (IOException e){System.out.println("Error reading image!\n------DETAILS------\nERROR DETAILS: "+e.getMessage()+"JACKET: "+jacket);}

        //update constant/combo
        noteCountLabel.setText("Max Combo: " + chart.combo);
        chartConstantLabel.setText("Chart Constant: " + chart.cc);


    }

    private static String getComponentValue(String name){
        final Component component = componentMap.get(name);
        if(component==null){System.out.println("ur piece of shit component is null. make piece of shit error handler later "+name);}

        String tempString;
        assert component!=null;

        //if component is a string
        if(component.getClass()==JComboBox.class){
            tempString = Objects.requireNonNull(((JComboBox<String>)component).getSelectedItem()).toString();
        }else{
            //if component is a num then make sure its positive
            tempString = ((JTextField) component).getText();
            double toDouble = 0;
            try{
                toDouble = Double.parseDouble(tempString);
                if(toDouble <0){
                    toDouble = 1;}
            }catch(Exception _){}
            tempString = Double.toString(toDouble);
        }
        return tempString;
    }

    //this is so dumb but it has to be done to make exactly ONE function more efficient by removing TWO lines of code
    private static double[] clamp(double[] targets, double max){
        //use a regular for loop here instead of an enchanced one to keep the index intact
        for(int i = 0; i<targets.length;i++){if(targets[i]>max){targets[i]=max;}}
        if(targets[targets.length-1]==0){targets[targets.length-1]=max;}
        return targets;
    }

    protected static class ScoreTextArea extends JTextArea{
        double score;
        double far;
        double miss;

        private ScoreTextArea(String text, double score, int far, int miss) {
            //variables for each score container
            super(text);
            this.score = score;
            this.far = far;
            this.miss = miss;

            setEditable(false);
            setBorder(BorderFactory.createLineBorder(Color.BLACK));

            if(Hub.DEBUG){System.out.println("[debug] scoretextarray constructor call");}
        }

        private double getScore() {return score;}
        private int getFar(){return (int)far;}
        private int getMiss(){return (int)miss;}
    }

    protected static void importComponent(double score, int pure, int far, int miss, double rating){
        //adds judgement data and score to array
        String text = "Score: " + (int) score + "\nPURE: " + pure + "\nFAR: " + far + "\nLOST: " + miss +"\nPlay Rating: "+rating;
        ScoreTextArea textArea = new ScoreTextArea(text, score, far, miss);
        scoreTextArray.add(textArea);
    }
}
