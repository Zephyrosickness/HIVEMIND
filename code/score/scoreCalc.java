package code.score;

import code.Hub;
import code.Utilities;

import javax.xml.parsers.ParserConfigurationException;

public class scoreCalc extends code.score.scoreDisplay{
    protected scoreCalc() throws ParserConfigurationException {}

    protected static void calcAllPossibleScores(String scoreString, int inputFar, int inputMiss, String farOp, String missOp, boolean toa, double cc, double combo){

        //init var
        int minScore = (int)Double.parseDouble(scoreString);
        scoreString = Integer.toString(minScore);

        //these threshold variables exist because I mean I don't think anyone is going to care about your score if you get like 500 fars or whatever
        //it just eliminates unecessary scores and speeds up the process
        int farThreshold = (int)(combo/10);
        int missThreshold = (int)(combo/5);

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
            for (int farCount = 0; farCount <farThreshold; farCount++) {
                for (int missCount = 0; missCount <missThreshold; missCount++){
                    if(Hub.DEBUG_CALC){System.out.println("[debug] forloop called in score calc\n combo: "+combo+"\ncc: "+cc);}
                    //calcs score. each PURE is 10,000,000 divided by max combo, and FAR is half of PURE
                    final int finalScore = calcScore((int)combo,farCount,missCount,toa);

                    //checks if all values meet operator criteria
                    final boolean farCheck = Utilities.check(farCount, inputFar, farOp);
                    final boolean missCheck = Utilities.check(missCount, inputMiss, missOp);
                    final boolean scoreCheck = finalScore >= minScore;

                    //if all criteria is met, imports into scroll panel
                    if(farCheck&&missCheck&&scoreCheck){
                        importComponent(finalScore, (int)(combo-farCount-missCount), farCount, missCount, code.ptt.pttCalc.calcPTT(finalScore, cc));
                        if(Hub.DEBUG_CALC){System.out.println("imported!!!!!!!!!!!!");}
                    }
                }
            }
        }

        public static int calcScore(int noteCount, int farCount, int missCount, boolean toa){
            final double pureRaw = 10000000.0/noteCount; //raw amount of score gained by one pure
            final double farRaw = pureRaw/2.0; //score from one far is half of one pure. lost is zero
            final int pureCount = noteCount-farCount-missCount;

            final double finalScore = (pureRaw*pureCount)+(farCount*farRaw);

            final boolean legit = Utilities.legitimacyCheck(pureCount, farCount, missCount, noteCount,finalScore, toa, noteCount-pureCount);

            if(legit){return (int)finalScore;
            }else{return -1;}
        }
}