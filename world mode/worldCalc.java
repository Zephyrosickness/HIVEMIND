
public class worldCalc extends worldDisplay{
    public static void calc(double targetCC, String scoreString, double targetStep, String ccOp, String scoreOp, String stepOp){
//vars
        int finalScore = 0;
        double finalCC = 0;
        double finalRating = 0;
        double finalStep = 0;
        int finalStat = 0;
        int targetScore = Integer.parseInt(scoreString);

        //gets length of score. then adds 0's until it hits 7 digits (so 99 = 9,900,000)
        //if score starts with 1, then it be set to 10,000,000 (pure memory)
        int length = scoreString.length();
        if(scoreString.startsWith("1")){
            targetScore = 10000000;
        }else{
            for(; length<7; length++){
                scoreString = scoreString+"0";
                targetScore = Integer.parseInt(scoreString);
            }
        }

        //minimum CC is 7 (because nobody gives a shit about non-ftr charts)
        if(targetCC<7){
            targetCC = 7;
        }

        //runs through every combo of score/chart constant and calculates play rating. if the rating, cc, and score all fall within criteria, display on the scroll panel
        for(finalScore = 9000000; finalScore<=10000000; finalScore+=5000){
            for(finalCC=7; finalCC<=12; finalCC+=0.1){
                for(finalStat=0; finalStat<=105; finalStat++){
                    finalRating = reversePttCalc(finalScore, finalCC);
                    finalCC = round(finalCC);
                    finalStep = stepCalc(finalRating, finalCC, finalScore, finalStat);
    
                    //checks
                    boolean scoreCheck = check(finalScore, targetScore, scoreOp);
                    boolean ccCheck = check(finalCC, targetCC, ccOp);
                    boolean stepCheck = check(finalStep, targetStep, stepOp);
                    boolean legit = legitimacyCheck(finalScore, finalCC, finalRating, finalStep);
    
                    System.out.println("SCORE: "+finalScore);
                    System.out.println("CC: "+finalCC);
                    System.out.println("STEP stat: "+finalStat);
                    System.out.println("STEP count: "+finalStep);

                    if(ccCheck&&scoreCheck&&stepCheck&&legit){
                        worldDisplay.importComponent(finalScore, finalCC, finalStep, finalStat);
                    }
                }
            }
        }
    }


    //more info: go to the arcaea wiki and look up "potential"
    public static double reversePttCalc(double score, double CC){
        double modifier;
        if(score==10000000){
            modifier = 2;
        }else if(score>=9800000&&score<=9999999&&score!=10000000){
            modifier = 1+((score-9800000)/200000);
        }else{
            modifier = (score-9500000)/300000;
        }

        //error check to make sure its not a negative value and rounds if its positive.
        //raw is the unrounded, raw play rating. the code rounds it to the 2nd decimal place because if i didnt do this then it would be like 500 lines long
        double raw = CC+modifier;;

        if(raw<=0){
            return 0;
        }else{
            return round(raw);
        }
    }

    public static double round(double value){
        double scale = Math.pow(10, 3);
        double result = Math.round(value * scale) / scale;
        return result;
    }

    public static boolean legitimacyCheck(double score, double CC, double targetRating, double step){
        boolean result;

        //checks if all the values are positive integers and that it all adds up to the total combo. both of these need to be true or the score doesnt display
        if(score >= 0&&cc <=12 &&targetRating>=0&&score<=10000000&&step>=0){
            result = true;
        }else{
            result = false;
        }
        return result;
    }

    //checks input meets the operators
    public static boolean check(double input, double target, String operator){
        boolean result = false;
        switch(operator){
            case "=":
                if(input==target){
                    result = true;
                }
                break;
            case "<":
                if(input<target){
                    result = true;
                }
                break;
            case "Any":
                result = true;
                break;
            default:
                System.out.println("error with checksum! invalid operator!");
                System.out.println("------DETAILS------");
                System.out.println("OPERATOR: "+operator);
                System.out.println("TARGET FAR/LOST COUNT: "+target);
                System.out.println("INPUT COUNT: "+input);
                break;

        }
        if(input<0||target<0){
            System.out.println("error with checksum! invalid target/input!");
            System.out.println("------DETAILS------");
            System.out.println("OPERATOR: "+operator);
            System.out.println("TARGET FAR/LOST COUNT: "+target);
            System.out.println("INPUT COUNT: "+input);
        }
        return result;
    }

    public static double stepCalc(double rating, double cc, double score, int stepStat){
        double stepCount =0;

        for(stepStat =0; stepStat<=105;stepStat++){
            double playResult = 2.45*Math.sqrt(rating)+2.5;
            stepCount = playResult*(stepStat/50);
        }
        return stepCount;
    }
}
