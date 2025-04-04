package code.ptt;

import code.Utilities;

public class pttCalc extends code.ptt.pttDisplay {
    protected static void calc(double targetRating, double targetCC, String scoreString, String ratingOp, String ccOp, String scoreOp){
//vars
        int finalScore;
        double finalCC;
        double finalRating;
        int targetScore = Integer.parseInt(scoreString);

        //gets length of score. then adds 0's until it hits 7 digits (so 99 = 9,900,000)
        //if score starts with 1, then it be set to 10,000,000 (pure memory)
        int length = scoreString.length();
        if(scoreString.startsWith("1")){
            targetScore = 10000000;
        }else{
            for(; length<7; length++){
                scoreString += "0";
                targetScore = Integer.parseInt(scoreString);
            }
        }

        //minimum CC is 7 (because nobody gives a shit about non-ftr charts)
        if(targetCC<7){
            targetCC = 7;
        }

        //runs through every combo of score/chart constant and calculates play rating. if the rating, cc, and score all fall within criteria, display on the scroll panel
        for(finalScore = 9000000; finalScore<=10000000; finalScore+=5000){
            for(finalCC=7; finalCC<=11.3; finalCC+=0.1){
                finalRating = calcPTT(finalScore, finalCC);
                finalCC = Utilities.round(finalCC, 1);

                //checks
                boolean ratingCheck = Utilities.check(finalRating, targetRating, ratingOp);
                boolean scoreCheck = Utilities.check(finalScore, targetScore, scoreOp);
                boolean ccCheck = Utilities.check(finalCC, targetCC, ccOp);
                if(ccCheck&&scoreCheck&&ratingCheck){
                    pttDisplay.importComponent(finalScore, finalRating, finalCC);
                }
            }
        }
    }

    //calculates play rating and returns as double. not much to say besides if u need more info u can find it here https://arcaea.fandom.com/wiki/Potential
    public static double calcPTT(double score, double cc){
        final double scoreRating = calcRatingScore(score);
        final double playRating = cc+ scoreRating;

        if(playRating<=0){return 0;
        }else{return Utilities.round(playRating,2);}
    }

    private static double calcRatingScore(double score){
        double playRating;

        if(score==10000000){playRating = 2;
        }else if(score>=9800000&&score<=9999999){playRating = 1+((score-9800000)/200000);
        }else{playRating = (score-9500000)/300000;}

        return playRating;
    }
}
