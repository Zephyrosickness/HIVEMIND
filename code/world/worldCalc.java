package code.world;

import code.Utilities;

public class worldCalc{
    public static void getAllPossibleRanges(int partnerThreshold, double stepThreshold, double CC, String partnerOp, String stepOp){
            for(int partnerStat = 12; partnerStat<=110;partnerStat+=2){
                for(int score = 9000000; score<=10000000; score+=50000){
                    final double steps = calcSteps(CC,score,partnerStat);

                    final boolean partnerCheck = Utilities.check(partnerStat,partnerThreshold,partnerOp);
                    final boolean stepCheck = Utilities.check(steps,stepThreshold,stepOp);

                    System.out.println("were still going");
                    if(partnerCheck&&stepCheck){worldDisplay.importComponent(score, steps,partnerStat);}
            }
        }
    }

    private static double calcSteps(double cc, int score, int stepStat){
        final double subscriptionBoost = 1.2;

        final double playRating = code.ptt.pttCalc.calcPTT(score,cc); //regular value used in ptt calcs


        final double playResult = 2.45*Math.sqrt(playRating)+2.5; //value before modifiers

        return Utilities.round(playResult*((double)stepStat/50)*subscriptionBoost, 1);
    }
}
