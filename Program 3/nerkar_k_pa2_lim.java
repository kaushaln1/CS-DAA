import java.util.*;
public class nerkar_k_pa2_lim {

    /**
     * Recursive Function to find the multiplication when a number is divided into 2 parts
     * @param u String
     * @param v String
     * @return u*v using Long Integer Multiplication.
     */
        public static String prod(String u, String v) {
            if (u.length() > v.length()) {
                u = u + v;
                v = u.substring(0, u.length() - v.length());
                u = u.substring(v.length());
            }

            int n1 = u.length(), n2 = v.length();
            while (n2 > n1) {
                u = "0" + u;
                n1++;
            }

            if (n1 <= 1) {
                return Integer.toString(Integer.parseInt(u) * Integer.parseInt(v));
            }
            else{
                if (n1 % 2 == 1) {
                    n1++;
                    u = "0" + u;
                    v = "0" + v;
                }

                String x = "", y = "", w = "", z = "";

                String[] split_string = splitStrings(u, v, 1);
                 x = split_string[0];
                 w = split_string[1];
                 y = split_string[2];
                 z = split_string[3];

                String p1 = prod(x, w);
                String p2 = prod(y, z);
                String p3 = prod(StringFunctions.add(x, y), StringFunctions.add(w, z));

                String c0 = p2;
                String c2 = p1;
                String c1 = StringFunctions.subtract(p3, StringFunctions.add(c0,c2));
                c1 = addZerosToEnd(c1, n1/2);
                c2 = addZerosToEnd(c2,n1);

                String result = StringFunctions.add(c0, StringFunctions.add(c1,c2));
                return StringFunctions.removeZeros(result);

            }


        }

    /**
     * Split Strings into 2/3 parts based on flag var
     * @param u : String with number 1
     * @param v : String with number 2
     * @param flag : 1 for 2 splits , 2 for 3 splits
     * @return List of Strings
     */
    public static String[] splitStrings(String u, String v , int flag  ) {
       if (flag == 1){
           int n1 = u.length();
           int m = n1 / 2;

           String x = "";
           String w = "";
           String y = "";
           String z = "";
           for (int i = 0; i < m; ++i) {
               x += u.charAt(i);
               w += v.charAt(i);
               y += u.charAt(m + i);
               z += v.charAt(m + i);
           }
           return new String[] { x, w, y, z };
       }
        int n1 = u.length();
        int m = n1 / 3;

        String p = "";
        String q = "";
        String r = "";
        String s = "";
        String t = "";
        String w = "";

        for (int i = 0; i < m; ++i) {
            p += u.charAt(i);
            s += v.charAt(i);

            q += u.charAt(m + i);
            t += v.charAt(m+i);

            r += u.charAt(2*m + i);
            w += v.charAt(2*m + i);
        }
        return new String[] { p, s, q, t, r, w };

    }

    /**
     *Recursive Function to find the multiplication when a number is divided into 3 parts
     *  @param u String
     *  @param v String
     *  @return u*v using Long Integer Multiplication.
     */
    public static String prod_3_split(String u, String v) {
        if (u.length() > v.length()) {
            u = u + v;
            v = u.substring(0, u.length() - v.length());
            u = u.substring(v.length());
        }

        int n1 = u.length(), n2 = v.length();
        if (n1 <=2) {
            int ans = Integer.parseInt(u) * Integer.parseInt(v);
            return Integer.toString(ans);
        } else {

            if (n1 % 3 == 1) {
                n1=n1+2;
                u = "00" + u;
                v = "00" + v;
            }
            if (n1 % 3 == 2){
                n1++;
                u = "0" + u;
                v = "0" + v;
            }
            int m = n1/3;
            String[] split_string = splitStrings(u, v, 2);
            String first_digit_1 = split_string[0];
            String first_digit_2 = split_string[1];
            String second_digit_1 = split_string[2];
            String second_digit_2 = split_string[3];
            String third_digit_1 = split_string[4];
            String third_digit_2 = split_string[5];

            /*
                find Coefficients
             */
            String c4 = prod_3_split(first_digit_1, first_digit_2);
            String c3 = StringFunctions.add(prod_3_split(first_digit_1,second_digit_2),prod_3_split(first_digit_2,second_digit_1));
            String c2 = StringFunctions.add(StringFunctions.add(prod_3_split(first_digit_1,third_digit_2),prod_3_split(first_digit_2,third_digit_1)),prod_3_split(second_digit_1,second_digit_2));
            String c1 = StringFunctions.add(prod_3_split(second_digit_1,third_digit_2),prod_3_split(second_digit_2,third_digit_1));
            String c0 = prod_3_split(third_digit_2,third_digit_1);
            /*
                addZerosToEnd
             */
            c1 = addZerosToEnd(c1, m);
            c2 = addZerosToEnd(c2,2*m);
            c3 = addZerosToEnd(c3, 3*m);
            c4= addZerosToEnd(c4,4*m);

            String result = StringFunctions.add(StringFunctions.add(StringFunctions.add(StringFunctions.add(c0,c1),c2),c3),c4);

            return StringFunctions.removeZeros(result);
        }
    }


    /**
     * Add zeros to the end of string
     * @param str
     * @param count
     * @return
     */
    public static String addZerosToEnd(String str, int count) {
        StringBuilder result = new StringBuilder(str);
        for (int i = 0; i < count; i++) {
            result.append('0');
        }
        return result.toString();
    }

    /**
     * Generate a string with number of digits as input
     * @param size : num of digits
     * @return : randomly generated number
     */
    static  String generateNumber(int size){
        StringBuffer numberString= new StringBuffer();
        Random rng= new Random();
        numberString.append(rng.nextLong(9)+1);
        while (size >1){
            numberString.append(rng.nextLong(9));
            size--;
        }
        return String.valueOf(numberString);
    }

    /**
     * Main Method accepts argument as number of digit.
     * @param args args[0] : n
     */
        public static void main(String[] args) {

            try {
            int n = Integer.parseInt(args[0]);
            //check for constraint n = 6*k ,for integer k
            if (n % 6 == 0 ){
                String a = generateNumber(n);
                String b = generateNumber(n);
                System.out.println("For input (number of digits) n : " + n + "\nA= "+ a + "\n" + "B= "+ b);
                System.out.println("The large integer multiplication from the division of two smaller integers is");
                System.out.println("A * B= "+ prod(a,b));
                System.out.println("The large integer multiplication from the division of three smaller integers is");
                System.out.println("A * B= " + prod_3_split(a,b));

            }
            else throw new Exception("Passed 'n'(number of digits) is not a multiple of 6");
        }
        catch (Exception e){
            System.out.println("Pass value of n(number of digits) as multiple of 6, \n"+ e.getMessage() );
           //System.out.println(e.getMessage() + e.getStackTrace());
        }

    }


}