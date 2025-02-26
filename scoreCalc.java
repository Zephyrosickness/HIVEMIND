public class scoreCalc extends scoreDisplay{
    protected static void calcScore(String scoreString, int inputFar, int inputMiss, String farOp, String missOp, boolean toa, double cc, int combo){

        //init var

        int missFinal;
        int farFinal;
        double pureRaw = 10000000.0 / combo;
        double farRaw = pureRaw / 2.0;
        int pureFinal;
        int minScore = Integer.parseInt(scoreString);
        double scoreFinal;
        //these threshold variables exist because I mean I don't think anyone is going to care about your score if you get like 500 fars or whatever
        //it just eliminates unecessary scores and speeds up the process
        int farThreshold = combo /10;
        int missThreshold = combo /5;

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
        //oh my god. this code used to be OVER 200 LINES LONG. it was the most UNREADABLE peice of SHIT I ever wrote. now it's 20 lines. im god. im a genius. greatest programmer ever
            for (farFinal = 0; farFinal<farThreshold; farFinal++) {
                for (missFinal = 0; missFinal<missThreshold; missFinal++){
                    if(Hub.DEBUG){System.out.println("[debug] forloop called in score calc\n combo: "+combo+"\ncc: "+cc);}
                    //calcs score. each PURE is 10,000,000 divided by max combo, and FAR is half of PURE
                    pureFinal = combo - (farFinal + missFinal);
                    scoreFinal = pureRaw * pureFinal + farRaw * farFinal;

                    //checks if all values meet operator criteria
                    boolean farCheck = utilities.check(farFinal, inputFar, farOp);
                    boolean missCheck = utilities.check(missFinal, inputMiss, missOp);
                    boolean scoreCheck = scoreFinal >= minScore;


                    //makes sure no invalid values
                    boolean legit = utilities.legitimacyCheck(pureFinal, farFinal, missFinal, combo,scoreFinal, toa, combo-pureFinal);

                    //if all criteria is met, imports into scroll panel
                    if(farCheck&&missCheck&&scoreCheck&&legit){
                        importComponent(scoreFinal, pureFinal, farFinal, missFinal, pttCalc.calcPTT(scoreFinal, cc));
                        if(Hub.DEBUG){System.out.println("imported!!!!!!!!!!!!");}
                    }
                }
            }
        }
}