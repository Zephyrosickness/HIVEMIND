package code;

public class utilities {
    //checks if target meets operators
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
                System.out.println("error with checksum! invalid operator!\n------DETAILS------\n[OPERATOR] "+operator+"\n[TARGET FAR/LOST COUNT] "+target+"\n[INPUT COUNT] "+input);
                break;
        }
        if(input<0||target<0){System.out.println("error with checksum! invalid target/input!\n------DETAILS------\n[OPERATOR] "+operator+"\n[TARGET FAR/LOST COUNT] "+target+"\n[INPUT COUNT] "+input);}
        return result;
    }


    //rounds
    public static double round(double value){
        double scale = Math.pow(10, 3);
        return Math.round(value * scale) / scale;
    }

    //checks if is a valid arcaera score
    public static boolean legitimacyCheck(int pure, int far, int miss, int combo, double score, boolean toa, int impure){
        //init var
        int total = pure+far+miss;
        score = (int)score;
        boolean result = false;

        //checks if all the values are positive integers and that it all adds up to the total combo. both of these need to be true or the score doesn't display
        if(pure >= 0&&far >=0 &&miss>=0&&total==combo&&score<=10000000&&toaCheck(toa, impure)){
            result = true;
        }else{System.out.println("error with legitimacy checksum!\n------DETAILS------\n[PURE] Is PURE a valid positive number? "+(pure >= 0)+" PURE count: "+pure+"\n[FAR] Is FAR a valid positive number? "+(far >= 0)+" FAR count: "+far+"\n[LOST] Is LOST a valid positive number? "+(miss >= 0)+" LOST count: "+miss+"\n[Score] Is score equal/less than 10,000,000? "+(score<=10000000)+" Score: "+miss+"\n[Toa Kozukata] Is Toa enabled? "+toa+" FAR+LOST count: "+impure+" toaCheck status"+toaCheck(toa, impure));}
        return result;
    }

    //toa kozakuta is a partner from the chunithm collab who instantly ends the chart if you hit more than 60 notes for a non-pure judgement.
    //if you specify toa is the selected partner, it will make sure the non-pure notes are less than 60.
    private static boolean toaCheck(boolean toa, int impure){
        boolean result = true;
        if(toa){
            if(impure>60){
                result = false;
            }
        }return result;
    }

}
