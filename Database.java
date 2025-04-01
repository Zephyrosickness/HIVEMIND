import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Database {

    //chart lists
    protected static Map<String,Chart> chartMapFTR = new HashMap<>();
    protected static Map<String,Chart> chartMapBYD = new HashMap<>();
    protected static Map<String,Chart> chartMapELSEPLACEHOLDER = new HashMap<>(); //this is for present and past i dont give a fuck....

    //constructor; creates a new instance of the chart class to run its constructor
    //"why use an inner class and initialize method called by a method in superclass? that seems redundant"
    // I don't want the database class needing parameters to instantiate in other classes
    //and if I put this logic in the inner-db class then the add chart logic will be in the constructor too and I don't want to add some garbage-data chart to listings every time
    //and I cant run initialize directly because it is static context
    //edit 4/1/2025 ^^^ why the fuck does it being static or not matter.
    //i changed init to static and initialized directly but ill keep those earlier comments because idc
    protected Database() throws ParserConfigurationException {Chart.initialize();}

    protected static final class Chart {
        String name;
        int combo;
        double cc;
        String tier; // applicable: FTR/ETR/BYD

        Chart(String name, int combo, double cc, String tier){ //constructor for creating chart objects
            /*at first it may seem strange that these values are initialized in-class and then are parameterized via this constructor,
            but there may be instances where you need to pull the most recently selected chart's data (UNCONFIRMED)*/
            this.name = name;
            this.combo = combo;
            this.cc = cc;
            this.tier = tier;


            if(tier.equals("BYD")){
                if(Hub.DEBUG){System.out.printf("[DEBUG: CHART OBJECT ADDED TO MAP]\n\n[TIER]: %s\n---[END.]---\n", tier);}
                chartMapBYD.put(name,this);
            }else if(tier.equals("FTR")){
                if(Hub.DEBUG){System.out.printf("[DEBUG: CHART OBJECT ADDED TO MAP]\n[TIER]: %s\n---[END.]---\n",tier);}
                chartMapFTR.put(name,this);
            }else{
                chartMapELSEPLACEHOLDER.put(name,this);
            }
        }

        //adds data for every chart currently in-game to the list variables; cc and combo values ripped from arcaea wiki
        private static void initialize() throws ParserConfigurationException {
            chartMapELSEPLACEHOLDER.clear(); chartMapFTR.clear(); chartMapBYD.clear(); //clear all listings to prevent stacking

            XMLReader.initReader();

        }
    }

    public static Chart getChart(String name, String difficulty){
        if(Hub.DEBUG){System.out.println("\n[DEBUG // GETCHART]\ndifficulty: "+difficulty+"\nname: "+name);}

        if(difficulty.equals("BYD")){
            if(Hub.DEBUG){System.out.println("[DEBUG // GETCHART] resulting chart: "+chartMapBYD.get(name).name);}
            return chartMapBYD.get(name);
        }else{
            if(Hub.DEBUG){System.out.println("[DEBUG // GETCHART] resulting chart: "+chartMapFTR.get(name).name);}
            return chartMapFTR.get(name);
        }
    }

    /*the way this script works is that it requires the selected variable name to be the exact same as the jacket file name.
     *unfortunately, sometimes some characters either cannot be used in filenames or im just too lazy to change it. so this "corrects"
     *any differences between filenames and string values without either field being compromised*/

    public static String jacketCheck(String target){

        if(target == null){
            target = "placeholder";
        }

        if(getChart(target,"FTR/ETR").tier.equals("ETR")){target = target.substring(0, target.length()-10);}

        return switch(target){

            case "Altair (feat. *spiLa*)" -> "Altair";

            case "Can I friend you on Bassbook? lol" -> "Bassbook";

            case "cocoro*cosmetic" -> "cocoro";

            case " ͟͝͞Ⅱ́̕" -> "Ii";

            case "carmine:scythe" -> "carmine scythe";

            case "To: Alice Liddell" -> "To Alice Liddell";

            case "Valhalla:0" -> "valhalla0";

            case "Last | Moment" -> "Last Moment";

            case "Last | Eternity" -> "Last Eternity";

            case "Twilight Concerto" -> "Tasogare";

            case"Hidden Rainbows of Epicurus" -> "Epicurus";

            case"world.execute(me)," -> "worldexecuteme";

            default -> target;
        };
    }

    private class XMLReader {
        private static final String databasePath = "database.xml";
        private static final File databaseFile = new File(databasePath);

        public static void initReader() throws ParserConfigurationException {
            try {
                final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance(); //this method allows you to make document builders
                final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder(); //this method parses xml files
                final Document database = dBuilder.parse(databaseFile); //this is the parsed xml file
                database.getDocumentElement().normalize();

                initAllCharts(database);
            }catch(IOException | SAXException e){throw new RuntimeException(e);}
        }

        private static void initAllCharts(Document database){
            final NodeList allCharts = database.getElementsByTagName("chart");

            for(int temp = 0; temp< allCharts.getLength(); temp++){
                final Element currentChart = (Element) allCharts.item(temp);

                final String name = getItem("title", currentChart);
                final int combo = Integer.parseInt(getItem("combo", currentChart));
                final double cc = Double.parseDouble(getItem("cc", currentChart));
                final String tier = getItem("tier", currentChart);

                if(Hub.DEBUG){System.out.printf("[DEBUG: CHART INITIALIZED IN XML READER]\n\n[NAME]: %s\n[COMBO]: %d\n[CC]: %f\n[TIER]: %s\n---[END.]---\n",name,combo,cc,tier);}

                new Database.Chart(name,combo,cc,tier);

            }
        }

        private static String getItem(String item, Element element){return element.getElementsByTagName(item.toLowerCase()).item(0).getTextContent();}
    }
}
