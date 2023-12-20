import java.util.ArrayList;
import java.util.List;

public class lcs {

    static void lcsDp(String s1 , String s2){

        int s1_length = s1.length();
        int s2_length = s2.length();
        String lcsString= "";

        List<List<Integer>> resultArray= new ArrayList<>();
        /**
         * Initialise the table to -1
         */
        for (int i = 0; i < s1_length+1; i++) {
            List<Integer> rowList = new ArrayList<>();
            for (int j = 0; j < s2_length+1; j++) {
                rowList.add(-1);
            }
            resultArray.add(rowList);
        }
        /**
         * Run through to update the mem table
         * update as per the recursion equation
         */
        for (int i=0; i<=s1_length;i++){
            for(int j=0;j<=s2_length;j++){
                if (i==0 || j==0){
                    resultArray.get(i).set(j,0);
                }
                else if(s1.charAt(i-1) == s2.charAt(j-1) &&(i>0 && j>0)){
                    int temp = resultArray.get(i-1).get(j-1) + 1;
                    resultArray.get(i).set(j,temp);
                }
                else {
                     resultArray.get(i).set(j,Math.max(resultArray.get(i-1).get(j), resultArray.get(i).get(j-1)));
                }
            }
        }
        /**
         * finding LCS string ,
         * traverse from last column , last row to up to find the LCS string in reversed order
         */
        int i = s1_length;
        int j= s2_length;
        for (;i>0 && j>0 ;){
            if (s1.charAt(i-1) == s2.charAt(j-1)){
                lcsString+=s1.charAt(i-1);
                i--; j--;
            }
            else if ( resultArray.get(i-1).get(j) > resultArray.get(i).get(j-1)){
                i--;
            }
            else j--;
        }
        StringBuilder reversedLcs = new StringBuilder(lcsString).reverse();

        /**
         * Printing the output of Length of LCS and LCS string
         */
        System.out.println("Length of LCS: " +  resultArray.get(s1_length).get(s2_length));
        System.out.println("LCS: "+ reversedLcs);
    }

    /**
     * Driver Main Function to read the cmd input
     * @param args
     */
     public static void main(String[] args) {

        try{
            if (args.length == 0){
                throw new Exception("Please pass 2 strings to evaluate LCS");
            }
            if(args.length != 2 ){
                throw new Exception("Please pass 2 strings to evaluate LCS ");
            }
            if(args.length >2){
                throw new Exception("Please pass only 2 strings to evaluate LCS ");
            }
            if (args[0].length() > 100 || args[1].length() > 100) {
                throw new Exception("Please pass strings with length at most 100 characters");
            }
            //read strings
            String s1 = args[0];
            String s2 = args[1];
            lcsDp(s1,s2);
        }
        catch (Exception e){
            System.out.println(e.getMessage());

        }



    }
}
