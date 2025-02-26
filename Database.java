import java.util.*;

public class Database {

    //chart lists
    //unfortunatrly u cant just grab all the attributes of items in an objectList so we have to have a list of names too :///
    protected static ArrayList<String> chartNames = new ArrayList<>(); //string values of non-byd chart names
    protected static ArrayList<String> chartNamesBYD = new ArrayList<>(); //string values of byd charts
    protected static ArrayList<Chart> allCharts = new ArrayList<>(); //all attributes for ftr
    protected static ArrayList<Chart> allChartsBYD = new ArrayList<>(); //all atributes for byd

    //allows searching of charts by name
    protected static Map<String,Chart> chartMapFTR = new HashMap<>();
    protected static Map<String,Chart> chartMapBYD = new HashMap<>();

    //constructor; creates a new instance of the chartclass to run its constructor
    //"why use an inner class and initialize method called by a method in superclass? that seems redundant"
    // I don't want the database class needing parameters to instantiate in other classes
    //and if I put this logic in the inner-db class then the add chart logic will be in the constructor too and I don't want to add some garbage-data chart to listings every time
    //and I cant run initialize directly because it is static context
    protected Database(){new Chart();}

    protected static final class Chart {
        String name;
        int combo;
        double cc;
        String tier; // applicable: FTR/ETR/BYD

        private Chart(String name, int combo, double cc, String tier){ //constructor for creating chart objects
            /*at first it may seem strange that these values are initialized in-class and then are parameterized via this constructor,
            but there may be instances where you need to pull the most recently selected chart's data (UNCONFIRMED)*/
            this.name = name;
            this.combo = combo;
            this.cc = cc;
            this.tier = tier;

            if(tier.equals("BYD")){
                allChartsBYD.add(this);
                chartNamesBYD.add(name);
            }else{
                allCharts.add(this);
                chartNames.add(name);
            }
        }

        //adds all charts to listings when an instance of class with no parameters is called
        private Chart(){
            initialize();
        }

        //adds data for every chart currently in-game to the list variables; cc and combo values ripped from arcaea wiki
        private void initialize() {
            chartNames.clear(); chartNamesBYD.clear(); allCharts.clear(); allChartsBYD.clear(); //clear all listings to prevent stacking
            
            //#

            new Chart("10pt8ion", 906, 9.7, "FTR");
            new Chart("7thSense", 925, 9.9, "FTR");
            new Chart("99 Glooms", 1294, 10.3, "FTR");

            //A

            new Chart("A Wandering Melody of Love", 931, 9.6, "FTR");
            new Chart("Abstruse Dilemma", 1467, 11.1, "FTR");
            new Chart("Aegleseeker", 1568, 11.1, "FTR");
            new Chart("Ai Drew", 1066, 9.8, "FTR");
            new Chart("AI[UE]OON", 989, 9.4, "FTR");
            new Chart("Aleph-0", 919, 10.5, "FTR");
            new Chart("Alexandrite", 1040, 10, "FTR");
            new Chart("Alice à la mode", 872, 9.2, "FTR");
            new Chart("Alice's Suitcase", 999, 9.1, "FTR");
            new Chart("Alone & Lorn", 970, 9.6, "FTR");
            new Chart("Altair (feat. *spiLa*)", 830, 8.5, "FTR");
            new Chart("Altale", 690, 9.7, "FTR");
            new Chart("ALTER EGO", 1466, 10.5, "FTR");
            new Chart("AlterAle", 1277, 9.7, "FTR");
            new Chart("AMAZING MIGHTYYYY!!!!", 1249, 10.7, "FTR");
            new Chart("Amekagura", 1076, 9.9, "FTR");
            new Chart("amygdata", 1199, 9.7, "FTR");
            new Chart("Anökumene", 851, 9.2, "FTR");
            new Chart("Antagonism", 1142, 9.9, "FTR");
            new Chart("Antithese", 877, 8.8, "FTR");
            new Chart("April showers", 697, 8.6, "FTR");
            new Chart("Arcahv", 1065, 9.9, "FTR");
            new Chart("Arcana Eden", 1792, 10.5, "FTR");
            new Chart("Arghena", 1444, 11.3, "FTR");
            new Chart("Ascent", 1023, 9.8, "FTR");
            new Chart("Ashen 6oundary", 1183, 9.9, "FTR");
            new Chart("Astra walkthrough", 1191, 9.9, "FTR");
            new Chart("Astral tale", 884, 9.6, "FTR");
            new Chart("AttraqtiA", 1433, 10.6, "FTR");
            new Chart("Aurgelmir", 1100, 10.5, "FTR");
            new Chart("Auxesia", 1000, 9.3, "FTR");
            new Chart("Avant Raze", 1125, 9.6, "FTR");
            new Chart("Ävril -Flicka i krans-", 851, 8.3, "FTR");
            new Chart("Awaken In Ruins", 754, 9.9, "FTR");
            new Chart("Axium Crisis", 1094, 10.7, "FTR");

            //B

            new Chart("B.B.K.K.B.K.K.", 976, 9.7, "FTR");
            new Chart("Babaroque", 808, 8.5, "FTR");
            new Chart("Back to Basics", 1544, 10.5, "FTR");
            new Chart("BADTEK", 916, 9.7, "FTR");
            new Chart("Bamboo", 1050, 10, "FTR");
            new Chart("BATTLE NO.1", 1042, 9.7, "FTR");
            new Chart("Be There", 982, 9.4, "FTR");
            new Chart("Beautiful Dreamer", 1139, 9.9, "FTR");
            new Chart("Beside You", 703, 8.7, "FTR");
            new Chart("Black Lotus", 965, 9.7, "FTR");
            new Chart("Black MInD", 1274, 10.8, "FTR");
            new Chart("Black Territory", 1195, 9.8, "FTR");
            new Chart("Blaster", 1002, 9.3, "FTR");
            new Chart("Blocked Library", 850, 9.3, "FTR");
            new Chart("Blossoms", 655, 7, "FTR");
            new Chart("BLRINK", 1015, 9.7, "FTR");
            new Chart("blue comet", 776, 8.2, "FTR");
            new Chart("Blue Rose", 955, 9.1, "FTR");
            new Chart("Bookmaker (2D Version)", 1124, 8.3, "FTR");
            new Chart("Brand new world", 787, 7.8, "FTR");
            new Chart("BUCHiGiRE Berserker", 1412, 10.9, "FTR");
            new Chart("Bullet Waiting for Me (James Landino remix)", 701, 8.1, "FTR");

            //C

            new Chart("Call My Name feat. Yukacco", 921, 8.7, "FTR");
            new Chart("Callima Karma", 1222, 9.8, "FTR");
            new Chart("Can I friend you on Bassbook? lol", 861, 9.3, "FTR");
            new Chart("Capella", 1159, 10.2, "FTR");
            new Chart("carmine:scythe", 1164, 9.6, "FTR");
            new Chart("CHAOS", 1369, 10.9, "FTR");
            new Chart("Chelsea", 650, 8.9, "FTR");
            new Chart("Chromafill", 1130, 10, "FTR");
            new Chart("Chronicle", 1264, 10.4, "FTR");
            new Chart("Chronostasis", 916, 8.9, "FTR");
            new Chart("Climax", 1367, 10.4, "FTR");
            new Chart("Clotho and the stargazer", 1021, 7.8, "FTR");
            new Chart("Coastal Highway", 732, 9, "FTR");
            new Chart("cocoro*cosmetic", 1025, 9.2, "FTR");
            new Chart("conflict", 1056, 10.2, "FTR");
            new Chart("corps-sans-organes", 1077, 10.6, "FTR");
            new Chart("Corruption", 1293, 9.8, "FTR");
            new Chart("Cosmica", 773, 8.8, "FTR");
            new Chart("Cosmo Pop Funclub", 809, 8.8, "FTR");
            new Chart("Crimson Throne", 1313, 10.4, "FTR");
            new Chart("CROSS†OVER", 1094, 9.4, "FTR");
            new Chart("CROSS†SOUL", 1081, 9.4, "FTR");
            new Chart("cry of viyella", 791, 8.7, "FTR");
            new Chart("Crystal Gravity", 872, 9.4, "FTR");
            new Chart("Cyaegha", 1368, 10.7, "FTR");
            new Chart("cyanine", 1171, 10.6, "FTR");
            new Chart("Cybernecia Catharsis", 946, 9.5, "FTR");
            new Chart("CYCLES", 695, 8.8, "FTR");

            //D

            new Chart("Dancin' on a Cat's Paw", 891, 9.2, "FTR");
            new Chart("Dandelion", 921, 8.5, "FTR");
            new Chart("Dantalion", 1476, 10.9, "FTR");
            new Chart("DataErr0r", 955, 9.5, "FTR");
            new Chart("Dazzle hop", 1022, 9.7, "FTR");
            new Chart("DDD", 653, 8.5, "FTR");
            new Chart("Defection", 1141, 9.8, "FTR");
            new Chart("Dement ~after legend~", 970, 7.8, "FTR");
            new Chart("Désive", 1273, 9.9, "FTR");
            new Chart("Devillic Sphere", 1129, 9.9, "FTR");
            new Chart("Dialnote", 684, 8.8, "FTR");
            new Chart("Diode", 709, 8.1, "FTR");
            new Chart("Distorted Fate", 1172, 9.6, "FTR");
            new Chart("Distortion Human", 1317, 9.8, "FTR");
            new Chart("Divine Light of Myriad", 1021, 10.8, "FTR");
            new Chart("Dot to Dot feat. shully", 739, 8.3, "FTR");
            new Chart("Dreadnought", 1099, 9.7, "FTR");
            new Chart("Dream goes on", 719, 7.8, "FTR");
            new Chart("Dreamin' Attraction!!", 1129, 9.4, "FTR");
            new Chart("DRG", 872, 9.5, "FTR");
            new Chart("dropdead", 823, 9.1, "FTR");
            new Chart("DX Choseinou Full Metal Shojo", 808, 9.8, "FTR");
            new Chart("Dynitikós", 986, 9.5, "FTR");

            //E

            new Chart("Eccentric Tale", 732, 8.4, "FTR");
            new Chart("eden", 1365, 10.5, "FTR");
            new Chart("Ego Eimi", 1223, 10.5, "FTR");
            new Chart("Einherjar Joker", 1159, 9.8, "FTR");
            new Chart("Empire of Winter", 920, 9, "FTR");
            new Chart("enchanted love", 759, 8.6, "FTR");
            new Chart("ENERGY SYNERGY MATRIX", 922, 9.6, "FTR");
            new Chart("Equilibrium", 951, 9.4, "FTR");
            new Chart("Essence of Twilight", 1204, 9.1, "FTR");
            new Chart("Ether Strike", 1170, 10.3, "FTR");
            new Chart("Evening in Scarlet", 922, 9.5, "FTR");
            new Chart("Evoltex (poppi'n mix)", 775, 8.9, "FTR");

            //F

            new Chart("Faint Light (Arcaea Edit)", 809, 9.1, "FTR");
            new Chart("Fairytale", 782, 7, "FTR");
            new Chart("Fallensquare", 703, 9.6, "FTR");
            new Chart("False Embellishment", 969, 9.3, "FTR");
            new Chart("FANTA5Y", 965, 9.4, "FTR");
            new Chart("Far Away Light", 1322, 9.8, "FTR");
            new Chart("Feels So Right feat. Renko", 947, 9.3, "FTR");
            new Chart("Felis", 1153, 10.4, "FTR");
            new Chart("felys final remix", 1130, 9.5, "FTR");
            new Chart("Filament", 991, 9.7, "FTR");
            new Chart("Final Step!", 1056, 9.4, "FTR");
            new Chart("First Snow", 578, 7.8, "FTR");
            new Chart("Flashback", 856, 8.9, "FTR");
            new Chart("Floating World", 1047, 9.3, "FTR");
            new Chart("FLUFFY FLASH", 1329, 9.8, "FTR");
            new Chart("Flyburg and Endroll", 930, 9, "FTR");
            new Chart("Fracture Ray", 1279, 11.3, "FTR");
            new Chart("Free Myself", 1132, 10, "FTR");
            new Chart("FREEF4LL", 1023, 8.6, "FTR");

            //G

            new Chart("G e n g a o z o", 1353, 10.2, "FTR");
            new Chart("Galactic Love", 813, 9, "FTR");
            new Chart("Galaxy Friends", 1013, 9.8, "FTR");
            new Chart("Garakuta Doll Play", 1035, 10.4, "FTR");
            new Chart("Gekka (Short Version)", 817, 8.6, "FTR");
            new Chart("Genesis (CHUNITHM)", 713, 8.2, "FTR");
            new Chart("Genesis", 713, 8.2, "FTR");
            new Chart("GENOCIDER", 1483, 10.7, "FTR");
            new Chart("Gensou no Satellite", 992, 8.7, "FTR");
            new Chart("GIMME DA BLOOD", 1093, 10.4, "FTR");
            new Chart("GIMMICK", 733, 9.5, "FTR");
            new Chart("Give Me a Nightmare", 948, 8.9, "FTR");
            new Chart("GLORY：ROAD", 1479, 10.6, "FTR");
            new Chart("Glow", 916, 9.3, "FTR");
            new Chart("goldenslaughterer", 1326, 9.7, "FTR");
            new Chart("Good bye, Merry-Go-Round.", 1084, 10.5, "FTR");
            new Chart("GOODTEK (Arcaea Edit)", 968, 9.3, "FTR");
            new Chart("Got hive of Ra", 794, 9.8, "FTR");
            new Chart("Grievous Lady", 1450, 11.3, "FTR");
            new Chart("Grimheart", 959, 8.7, "FTR");

            //H

            new Chart("Halcyon", 1227, 10.7, "FTR");
            new Chart("Hall of Mirrors", 898, 8.2, "FTR");
            new Chart("Harutopia ~Utopia of Spring~", 1061, 8.5, "FTR");
            new Chart("Haze of Autumn", 1077, 9.8, "FTR");
            new Chart("Head BONK ache", 1061, 9.4, "FTR");
            new Chart("Heart Jackin'", 1112, 9.7, "FTR");
            new Chart("Heart", 872, 9.3, "FTR");
            new Chart("Heavenly caress", 1560, 9.8, "FTR");
            new Chart("Heavensdoor", 1101, 9.9, "FTR");
            new Chart("HELLOHELL", 673, 7.5, "FTR");
            new Chart("Hidden Rainbows of Epicurus", 783, 7.5, "FTR");
            new Chart("Hiiro Gekka, Kyoushou no Zetsu (nayuta 2017 ver.)", 1126, 10.3, "FTR");
            new Chart("Hikari", 684, 8.1, "FTR");
            new Chart("HIVEMIND", 1252, 10, "FTR");
            new Chart("Hotarubi no Yuki", 991, 9.7, "FTR");
            new Chart("Hybris (The one who shattered)", 1196, 9.8, "FTR");
            new Chart("HYPER VISION", 1040, 9.8, "FTR");
            new Chart("Hypnotize", 993, 8.9, "FTR");

            //I

            new Chart("I've heard it said", 864, 8.1, "FTR");
            new Chart("Iconoclast", 795, 9.1, "FTR");
            new Chart("Ignotus", 1225, 9.3, "FTR");
            new Chart("Ikazuchi", 1347, 10.4, "FTR");
            new Chart("Illegal Paradise", 1061, 9.5, "FTR");
            new Chart("IMPACT", 1231, 9.6, "FTR");
            new Chart("Impure Bird", 805, 9.4, "FTR");
            new Chart("Infinite Strife,", 1511, 9.9, "FTR");
            new Chart("Infinity Heaven", 853, 7.8, "FTR");
            new Chart("init()", 1204, 9.8, "FTR");
            new Chart("inkar-usi", 463, 7.8, "FTR");

            new Chart("Innocence", 1023, 8.5, "FTR");
            new Chart("INTERNET OVERDOSE", 657, 8.4, "FTR");
            new Chart("INTERNET YAMERO", 1222, 9.9, "FTR");
            new Chart("Inverted World", 940, 9.5, "FTR");
            new Chart("IONOSTREAM", 818, 8.7, "FTR");
            new Chart("IZANA", 976, 10.3, "FTR");

            //J

            new Chart("Jingle", 848, 7.8, "FTR");
            new Chart("Journey", 997, 9.1, "FTR");
            new Chart("Judgement", 1432, 10.4, "FTR");
            new Chart("Jump", 841, 9, "FTR");

            //K

            new Chart("Kanagawa Cyber Culvert", 1111, 9, "FTR");
            new Chart("Kanbu de Tomatte Sugu Tokeru", 1108, 10, "FTR");
            new Chart("Kanjou no Matenrou ～Arr.Demetori", 1294, 9.8, "FTR");
            new Chart("Kissing Lucifer", 1183, 10.4, "FTR");
            new Chart("KYOREN ROMANCE", 1519, 10.7, "FTR");

            //L

            new Chart("La'qryma of the Wasteland", 956, 9.1, "FTR");
            new Chart("LAMIA", 1385, 10.9, "FTR");
            new Chart("Lapis", 920, 9.4, "FTR");
            new Chart("Last Celebration", 1475, 10.5, "FTR");
            new Chart("Last", 831, 9, "FTR");
            new Chart("lastendconductor", 1209, 9.4, "FTR");
            new Chart("Lawless Point", 838, 9, "FTR");
            new Chart("Lazy Addiction", 1031, 9.6, "FTR");
            new Chart("Leave All Behind", 828, 9.2, "FTR");
            new Chart("Let you DIVE! (nitro rmx)", 1049, 9.4, "FTR");
            new Chart("Let's Rock (Arcaea mix)", 1177, 9.7, "FTR");
            new Chart("Lethaeus", 900, 9.7, "FTR");
            new Chart("Lethal Voltage", 1497, 10.4, "FTR");
            new Chart("Libertas", 947, 9.2, "FTR");
            new Chart("Life is PIANO", 674, 9.1, "FTR");
            new Chart("Lightning Screw", 1192, 10.5, "FTR");
            new Chart("Lights of Muse", 580, 8.9, "FTR");
            new Chart("Linear Accelerator", 905, 9.8, "FTR");
            new Chart("Live Fast Die Young", 1292, 10.6, "FTR");
            new Chart("LIVHT MY WΔY", 954, 9.8, "FTR");
            new Chart("Logos", 1040, 10.1, "FTR");
            new Chart("Löschen", 1235, 10.2, "FTR");
            new Chart("Lost Civilization", 986, 9.2, "FTR");
            new Chart("Lost Desire", 1154, 9.8, "FTR");
            new Chart("Lost Emotion feat. nomico", 1123, 9.3, "FTR");
            new Chart("Lost in the Abyss", 1179, 9.7, "FTR");
            new Chart("Loveless Dress", 850, 9.6, "FTR");
            new Chart("Lucid Traveler", 1341, 10.4, "FTR");
            new Chart("Lucifer", 861, 8.2, "FTR");
            new Chart("Lumia", 961, 8.4, "FTR");
            new Chart("Luna Rossa", 920, 9.7, "FTR");
            new Chart("LunarOrbit -believe in the Espebranch road-", 1058, 9.6, "FTR");

            //M

            new Chart("Macrocosmic Modulation", 1117, 9.8, "FTR");
            new Chart("Magnolia", 895, 10.2, "FTR");
            new Chart("MAHOROBA", 828, 9.5, "FTR");
            new Chart("Malicious Mischance", 1126, 10.2, "FTR");
            new Chart("Manic Jeer", 1286, 10.6, "FTR");
            new Chart("MANTIS (Arcaea Ultra-Bloodrush VIP)", 1014, 9.3, "FTR");
            new Chart("Masquerade Legion", 1064, 10, "FTR");
            new Chart("MAXRAGE", 1184, 9.9, "FTR");
            new Chart("Maze No.9", 775, 8.9, "FTR");
            new Chart("Mazy Metroplex", 952, 9.7, "FTR");
            new Chart("Medusa", 931, 10.2, "FTR");
            new Chart("Memory Forest", 978, 9.8, "FTR");
            new Chart("memoryfactory.lzh", 672, 8.9, "FTR");
            new Chart("MERLIN", 712, 8.9, "FTR");
            new Chart("Meta-Mysteria", 1309, 10.8, "FTR");
            new Chart("Metallic Punisher", 1238, 10.3, "FTR");
            new Chart("MIRINAE", 1277, 10.5, "FTR");
            new Chart("Mirzam", 1303, 10, "FTR");
            new Chart("Misdeed -la bonté de Dieu et l'origine du mal-", 1522, 10.9, "FTR");
            new Chart("Modelista", 1010, 10, "FTR");
            new Chart("Monochrome Princess", 974, 9.7, "FTR");
            new Chart("Moonheart", 947, 8.4, "FTR");
            new Chart("Moonlight of Sand Castle", 645, 7.8, "FTR");
            new Chart("MORNINGLOOM", 940, 8.8, "FTR");
            //N

            new Chart("Nameless Passion", 1223, 9.9, "FTR");
            new Chart("NEO WINGS", 1328, 10.2, "FTR");
            new Chart("New York Back Raise", 1091, 9.9, "FTR");
            new Chart("next to you", 824, 8.8, "FTR");
            new Chart("Nhelv", 1108, 9.9, "FTR");
            new Chart("Nirv lucE", 980, 10.3, "FTR");
            new Chart("NULCTRL", 715, 9.5, "FTR");
            new Chart("NULL APOPHENIA", 1299, 10.6, "FTR");
            new Chart("nέo κósmo", 979, 9.7, "FTR");

            //O

            new Chart("Oblivia", 956, 8.3, "FTR");
            new Chart("Old School Salvage", 1316, 9.8, "FTR");
            new Chart("OMAKENO Stroke", 869, 9.5, "FTR");
            new Chart("On And On!! feat. Jenga", 836, 9.5, "FTR");
            new Chart("One Last Drive", 885, 8.2, "FTR");
            new Chart("Oracle", 963, 9.3, "FTR");
            new Chart("Oshama Scramble!", 1073, 10, "FTR");
            new Chart("ouroboros -twin stroke of the end-", 1369, 10.7, "FTR");
            new Chart("Ouvertüre", 913, 9.8, "FTR");
            new Chart("Overwhelm", 1251, 10.6, "FTR");

            //P

            new Chart("Paper Witch", 793, 8.7, "FTR");
            new Chart("Paradise", 729, 7.8, "FTR");
            new Chart("Party Vinyl", 800, 9.4, "FTR");
            new Chart("Pentiment", 1345, 10.3, "FTR");
            new Chart("Phantasia", 952, 9.2, "FTR");
            new Chart("PICO-Pico-Translation!", 1049, 9.3, "FTR");
            new Chart("PRAGMATISM", 942, 10.1, "FTR");
            new Chart("Primeval Texture", 810, 9, "FTR");
            new Chart("PRIMITIVE LIGHTS", 1524, 10.7, "FTR");
            new Chart("Prism", 785, 8, "FTR");
            new Chart("Protoflicker", 1042, 9.8, "FTR");
            new Chart("PUPA", 1099, 10.4, "FTR");
            new Chart("Purgatorium", 983, 8.4, "FTR");
            new Chart("Purple Verse", 1023, 9.6, "FTR");

            //Q

            new Chart("Qovat", 1299, 10.6, "FTR");
            new Chart("qualia -ideaesthesia-", 1022, 9.1, "FTR");
            new Chart("Quon (Feryquitous)", 991, 9.6, "FTR");
            new Chart("Quon", 749, 8.7, "FTR");

            //R

            new Chart("Rabbit In The Black Room", 772, 8.4, "FTR");
            new Chart("Raven's Pride", 1030, 9.4, "FTR");
            new Chart("REconstruction", 825, 8.4, "FTR");
            new Chart("Red and Blue", 845, 9.4, "FTR");
            new Chart("Redolent Shape", 1088, 10.2, "FTR");
            new Chart("Redraw the Colorless World", 886, 9.2, "FTR");
            new Chart("Reinvent", 852, 8.5, "FTR");
            new Chart("REKKA RESONANCE", 1212, 10.7, "FTR");
            new Chart("Relentless", 1015, 8, "FTR");
            new Chart("Remind the Souls (Short Version)", 945, 9.5, "FTR");
            new Chart("ReviXy", 1047, 9, "FTR");
            new Chart("RGB", 1131, 9.7, "FTR");
            new Chart("Ringed Genesis", 1146, 10.8, "FTR");
            new Chart("Rise of the World", 1176, 10.4, "FTR");
            new Chart("Rise", 788, 7.8, "FTR");
            new Chart("Romance Wars", 641, 7.8, "FTR");
            new Chart("Rugie", 975, 9.2, "FTR");

            //S

            new Chart("SACRIFICE feat. ayame", 958, 9.7, "FTR");
            new Chart("SAIKYO STRONGER", 1384, 11, "FTR");
            new Chart("Sakura Fubuki", 837, 9.3, "FTR");
            new Chart("san skia", 1046, 8.3, "FTR");
            new Chart("Saint or Sinner", 906, 8.8, "FTR");
            new Chart("Sayonara Hatsukoi", 666, 7, "FTR");
            new Chart("Scarlet Cage", 1195, 9.8, "FTR");
            new Chart("Scarlet Lance", 1130, 10.1, "FTR");
            new Chart("Seclusion", 1132, 10.6, "FTR");
            new Chart("Senkyou", 964, 8.7, "FTR");
            new Chart("Shades of Light in a Transcendent Realm", 1067, 8.3, "FTR");
            new Chart("Sheriruth (Laur Remix)", 1134, 10.6, "FTR");
            new Chart("Sheriruth", 1151, 10.1, "FTR");
            new Chart("shrink", 939, 9.8, "FTR");
            new Chart("Silent Rush", 941, 8.6, "FTR");
            new Chart("Singularity", 1105, 10.7, "FTR");
            new Chart("Small Cloud Sugar Candy", 919, 9.1, "FTR");
            new Chart("Snow White", 978, 8.4, "FTR");
            new Chart("Solitary Dream", 972, 8.8, "FTR");
            new Chart("SOUNDWiTCH", 785, 9.9, "FTR");
            new Chart("Specta", 1096, 9.5, "FTR");
            new Chart("Spider's Thread", 1203, 10.8, "FTR");
            new Chart("STAGER (ALL STAGE CLEAR)", 1004, 9.5, "FTR");
            new Chart("STARGATE EXTREME", 724, 9.3, "FTR");
            new Chart("Stasis", 1521, 10.7, "FTR");
            new Chart("Stratoliner", 877, 9.6, "FTR");
            new Chart("Strongholds", 922, 9.2, "FTR");
            new Chart("Sulfur", 1045, 9.7, "FTR");
            new Chart("Summer Fireworks of Love", 1088, 9.9, "FTR");
            new Chart("Suomi", 818, 7.8, "FTR");
            new Chart("SUPERNOVA", 1123, 9.7, "FTR");
            new Chart("Surrender", 925, 8.8, "FTR");
            new Chart("Syro", 1150, 9.3, "FTR");
            new Chart("syūten", 592, 8.5, "FTR");

            //T

            new Chart("Technicolour", 1140, 9.8, "FTR");
            new Chart("Tempestissimo", 1254, 10.7, "FTR");
            new Chart("TEmPTaTiON", 1099, 10.9, "FTR");
            new Chart("TeraVolt", 1008, 10.9, "FTR");
            new Chart("Teriqma", 873, 9.4, "FTR");
            new Chart("Testify", 1766, 10.8, "FTR");
            new Chart("The Formula", 957, 9.3, "FTR");
            new Chart("The Message", 992, 9.7, "FTR");
            new Chart("The Survivor (Game Edit)", 1100, 9.9, "FTR");
            new Chart("THE ULTIMACY", 1304, 9.8, "FTR");
            new Chart("Tie me down gently", 724, 8.3, "FTR");
            new Chart("Tiferet", 1086, 10.4, "FTR");
            new Chart("To the Furthest Dream", 1102, 9.8, "FTR");
            new Chart("To the Milky Way", 1392, 10.5, "FTR");
            new Chart("To: Alice Liddell", 998, 10.3, "FTR");
            new Chart("Transient Space", 805, 9.5, "FTR");
            new Chart("Trap Crow", 1074, 9.9, "FTR");
            new Chart("trappola bewitching", 1044, 10, "FTR");
            new Chart("Trrricksters!!", 1183, 10.1, "FTR");
            new Chart("Tsuki ni Murakumo, Hana ni Kaze", 740, 8.7, "FTR");
            new Chart("Turbocharger", 979, 9, "FTR");
            new Chart("Twilight Concerto", 803, 9.5, "FTR");

            //U

            new Chart("Ultimate taste", 1405, 9.7, "FTR");
            new Chart("ultradiaxon-N3", 1228, 10.5, "FTR");
            new Chart("UNKNOWN LEVELS", 1149, 10.6, "FTR");
            new Chart("Used to be", 799, 9.2, "FTR");

            //V

            new Chart("Valhalla:0", 1173, 10.4, "FTR");
            new Chart("Vandalism", 1087, 9.7, "FTR");
            new Chart("VECTOЯ", 1299, 9.4, "FTR");
            new Chart("Vexaria", 734, 7, "FTR");
            new Chart("Vicious Heroism", 1150, 10, "FTR");
            new Chart("Vindication", 1075, 9.6, "FTR");
            new Chart("Vivid Theory", 885, 8.8, "FTR");
            new Chart("Viyella's Tears", 1403, 10.3, "FTR");
            new Chart("Vulcanus", 1542, 10.9, "FTR");

            //W

            new Chart("WAIT FOR DAWN", 861, 8.7, "FTR");
            new Chart("Wish Upon a Snow", 1309, 10.5, "FTR");
            new Chart("with U", 932, 9.4, "FTR");
            new Chart("World Ender", 1225, 9.9, "FTR");
            new Chart("World Fragments III(radio edit)", 1387, 9.8, "FTR");
            new Chart("World Vanquisher", 1452, 10.8, "FTR");
            new Chart("world.execute(me),", 851, 8, "FTR");

            //X

            new Chart("Xanatos", 1232, 10, "FTR");
            new Chart("Xeraphinite", 1048, 9.8, "FTR");
            new Chart("XTREME", 1258, 10.5, "FTR");

            //Y

            new Chart("Yosakura Fubuki", 931, 9.4, "FTR");
            new Chart("Your voice so... feat. Such", 1013, 9.4, "FTR");

            //SPECIAL CHARS

            new Chart("αterlβus", 1030, 10.2, "FTR");
            new Chart("γuarδina", 1120, 10.4, "FTR");
            new Chart("µ", 1256, 9.7, "FTR");
            new Chart("ΟΔΥΣΣΕΙΑ", 1092, 9.5, "FTR");
            new Chart("ω4", 1393, 10.8, "FTR");
            new Chart("〇、", 708, 9.5, "FTR");
            new Chart(" ͟͝͞Ⅱ́̕", 1051, 10.8, "FTR");
            new Chart("[X]", 1190, 10.4, "FTR");
            new Chart("#1f1e33", 1576, 10.9, "FTR");

            //ETR

            new Chart("10pt8ion (Eternal)", 1087, 10.4, "ETR");
            new Chart("ALTER EGO (Eternal)", 1644, 11.2, "ETR");
            new Chart("Clotho and the stargazer (Eternal)", 1031, 8.8, "ETR");
            new Chart("Désive (Eternal)", 1340, 10.8, "ETR");
            new Chart("Distorted Fate (Eternal)", 1402, 10.9, "ETR");
            new Chart("Gensou no Satellite (Eternal)", 1139, 10.1, "ETR");
            new Chart("HELLOHELL (Eternal)", 770, 9.4, "ETR");
            new Chart("Hidden Rainbows of Epicurus (Eternal)", 884, 8.8, "ETR");
            new Chart("Hypnotize (Eternal)", 1164, 9.9, "ETR");
            new Chart("Innocence (Eternal)", 1157, 9.7, "ETR");
            new Chart("Inverted World (Eternal)", 1065, 10.7, "ETR");
            new Chart("IONOSTREAM (Eternal)", 871, 9.7, "ETR");
            new Chart("Jingle (Eternal)", 1047, 9.5, "ETR");
            new Chart("MORNINGLOOM (Eternal)", 1035, 9.8, "ETR");
            new Chart("Sayonara Hatsukoi (Eternal)", 728, 8.5, "ETR");
            new Chart("STARGATE EXTREME (Eternal)", 915, 10.0, "ETR");
            new Chart("Suomi (Eternal)", 732, 8.9, "ETR");
            new Chart("Twilight Concerto (Eternal)", 962, 10.4, "ETR");

            //BYD

            new Chart("Antithese", 968, 9.5, "BYD");
            chartMaps();
        }
        private void chartMaps(){
            for(Chart currentChart:allCharts){
                chartMapFTR.put(currentChart.name, currentChart);
            }
            for(Chart currentChart:allChartsBYD){
                chartMapBYD.put(currentChart.name, currentChart);
            }
        }
    }

    public static Chart getChart(String difficulty, String name){
        if(difficulty.equals("BYD")){return chartMapBYD.get(name);}
        else{return chartMapFTR.get(name);}
    }

    /*the way this script works is that it requires the selected variable name to be the exact same as the jacket file name.
     *unfortunately, sometimes some characters either cannot be used in filenames or im just too lazy to change it. so this "corrects"
     *any differences between filenames and string values without either field being compromised*/

    public static String jacketCheck(String target){

        if(target == null){
            target = "placeholder";
        }

        if(target.endsWith(" (Eternal)")){
            target = target.substring(0, target.length()-10);
        }

        switch(target){
            case "Select a chart":
                target = "placeholder";
                break;

            case "Altair (feat. *spiLa*)":
                target = "Altair";
                break;

            case "Can I friend you on Bassbook? lol":
                target ="Bassbook";
                break;

            case "cocoro*cosmetic":
                target = "cocoro";
                break;

            case " ͟͝͞Ⅱ́̕":
                target = "Ii";
                break;

            case "carmine:scythe":
                target="carmine scythe";
                break;

            case "To: Alice Liddell":
                target="To Alice Liddell";
                break;

            case "Valhalla:0":
                target="valhalla0";
                break;

            case "Last | Moment":
                target="Last Moment";
                break;

            case "Last | Eternity":
                target="Last Eternity";
                break;

            case "Twilight Concerto":
                target = "Tasogare";
                break;

            case"Hidden Rainbows of Epicurus":
                target = "Epicurus";
                break;

            default:
                break;
        }
        return target;
    }
}
