public class scoreCalc extends scoreDisplay{
    public static void calcScore(String scoreString, int inputFar, int inputMiss, String farOp, String missOp, boolean toa){

        //init var

        int missFinal;
        int farFinal;
        double pureRaw = 10000000.0 / combo;
        double farRaw = pureRaw / 2.0;
        int pureFinal;
        int minScore = Integer.parseInt(scoreString);
        double scoreFinal;
        //these threshold variables exist because i mean i dont think anyone is gonna care about your score if you get like 500 fars or whatever
        //it just eliminates unecessary scores and speeds up the process (i think)
        int farThreshold = Math.round(combo/10);
        int missThreshold = Math.round(combo/10);

        //gets length of score. then adds 0's until it hits 7 digits (so 99 = 9,900,000)
        //if score starts with 1, then it be set to 10,000,000 (pure memory)
        int length = scoreString.length();
        if(scoreString.startsWith("1")){
            minScore = 10000000;
        }else{
            for(; length<7; length++){
                scoreString = scoreString+"0";
                minScore = Integer.parseInt(scoreString);
            }
        }
        /*runs through every possible combination of far/lost notes, calculates the score and only prints out the ones that fufill the requirements*/

        //oh my god. this code used to be OVER 200 LINES LONG. it was the most UNREADABLE peice of SHIT i ever wrote. now it's 20 lines. im god. im a genius. greatest programmer ever
            for (farFinal = 0; farFinal<farThreshold; farFinal++) {
                for (missFinal = 0; missFinal<missThreshold; missFinal++) {
                    //calcs score. each PURE is 10,000,000 divided by max combo, and FAR is half of PURE
                    pureFinal = combo - (farFinal + missFinal);
                    scoreFinal = pureRaw * pureFinal + farRaw * farFinal;

                    //checks if all values meet operator criteria
                    boolean farCheck = check(farFinal, inputFar, farOp);
                    boolean missCheck = check(missFinal, inputMiss, missOp);
                    boolean scoreCheck = scoreFinal >= minScore;


                    //makes sure no invalid values
                    boolean legit = legitimacyCheck(pureFinal, farFinal, missFinal, combo,scoreFinal, toa, combo-pureFinal);

                    //if all criteria is met, imports into scroll panel
                    if(farCheck&&missCheck&&scoreCheck&&legit){
                        importComponent(scoreFinal, pureFinal, farFinal, missFinal, pttCalc(scoreFinal));
                    }
                }
            }
        }


    //calculates play rating and returns as double. not much to say besides if u need more info u can find it here https://arcaea.fandom.com/wiki/Potential
    public static double pttCalc(double score){
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
        double raw = cc + modifier;
        if(raw<=0){
            return 0;
        }else{
            double scale = Math.pow(10, 2);
            double rating = Math.round(raw * scale) / scale;
            return rating;
        }
    }
    
    //checks to make sure value meets criteria based on operator
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
                    System.out.println("pee pee");
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
    
    //toa kozakuta is a partner from the chunithm collab who instantly ends the chart if you hit more than 60 notes for a non-pure judgement.
    //if you specify toa is the selected partner, it will make sure the non-pure notes are less than 60.
    public static boolean toaCheck(boolean toa, int impure){
        boolean result = true;
        if(toa==true){
            if(impure>60){
                result = false;
            }
        }
        return result;
    }
    public static boolean legitimacyCheck(int pure, int far, int miss, int combo, double score, boolean toa, int impure){
        //init var
        int total = pure+far+miss;
        score = (int)score;
        boolean result = false;

        //checks if all the values are positive integers and that it all adds up to the total combo. both of these need to be true or the score doesnt display
        if(pure >= 0&&far >=0 &&miss>=0&&total==combo&&score<=10000000&&toaCheck(toa, impure)){
            result = true;
        }else{
            System.out.println("error with legitimacy checksum!");
            System.out.println("------DETAILS------");
            System.out.println("PURE: Is PURE a valid positive number? "+(pure >= 0)+" PURE count: "+pure);
            System.out.println("FAR: Is FAR a valid positive number? "+(far >= 0)+" FAR count: "+far);
            System.out.println("LOST: Is LOST a valid positive number? "+(miss >= 0)+" LOST count: "+miss);
            System.out.println("Score: Is score equal/less than 10,000,000? "+(score<=10000000)+" Score: "+miss);
            System.out.println("Toa Kozukata: Is Toa enabled? "+toa+" FAR+LOST count: "+impure+" toaCheck status"+toaCheck(toa, impure));
        }
        return result;
    }
}