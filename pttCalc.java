
public class pttCalc extends pttDisplay {
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
                finalRating = reversePttCalc(finalScore, finalCC);
                finalCC = utilities.round(finalCC);

                //checks
                boolean ratingCheck = utilities.check(finalRating, targetRating, ratingOp);
                boolean scoreCheck = utilities.check(finalScore, targetScore, scoreOp);
                boolean ccCheck = utilities.check(finalCC, targetCC, ccOp);
                if(ccCheck&&scoreCheck&&ratingCheck){
                    pttDisplay.importComponent(finalScore, finalRating, finalCC);
                }
            }
        }
    }


    //more info: go to the arcaea wiki and look up "potential"
    protected static double reversePttCalc(double score, double CC){
        double modifier;
        if(score>=10000000){
            modifier = 2;
        }else if(score>=9800000&&score<=9999999){
            modifier = 1+((score-9800000)/200000);
        }else{
            modifier = (score-9500000)/300000;
        }

        //error check to make sure it's not a negative value and rounds if its positive.
        //raw is the un-rounded, raw play rating. the code rounds it to the 2nd decimal place because if I didn't do this then it would be like 500 lines long
        double raw = CC+modifier;

        if(raw<=0){
            return 0;
        }else{
            return utilities.round(raw);
        }
    }

    //calculates play rating and returns as double. not much to say besides if u need more info u can find it here https://arcaea.fandom.com/wiki/Potential
    protected static double calcPTT(double score, double cc){
        double modifier;
        if(score==10000000){
            modifier = 2;
        }else if(score>=9800000&&score<=9999999){
            modifier = 1+((score-9800000)/200000);
        }else{
            modifier = (score-9500000)/300000;
        }

        //error check to make sure it's not a negative value and rounds if its positive.
        //raw is the unrounded, raw play rating. the code rounds it to the 2nd decimal place because if I didn't do this then it would be like 500 lines long
        double raw = cc + modifier;
        if(raw<=0){
            return 0;
        }else{
            double scale = Math.pow(10, 2);
            return Math.round(raw * scale) / scale;
        }
    }
}
