public class StringFunctions {

    /**
     * Add 2 numbers as Strings
     * @param string1 : string 1
     * @param string2 : string 2
     * @return addition of string1 + string2
     */
    public static String add(String string1, String string2) {
        int length1 = string1.length();
        int length2 = string2.length();

        int max = Math.max(length1, length2);
        StringBuilder result = new StringBuilder();
        int carry = 0;
        for (int i = 0; i < max; i++) {
            int digit1 = (i < length1) ? string1.charAt(length1 - 1 - i) - '0' : 0;
            int digit2 = (i < length2) ? string2.charAt(length2 - 1 - i) - '0' : 0;
            int sum = digit1 + digit2 + carry;
            carry = sum / 10;
            result.append((char)(sum % 10 + '0'));
        }
        if (carry != 0) {
            result.append((char)(carry + '0'));
        }
        return result.reverse().toString();
    }
    public static String subtract(String string1, String string2) {
        if (string1.length() < string2.length() || (string1.length() == string2.length() && string1.compareTo(string2) < 0)) {
            String temp = string1;
            string1 = string2;
            string2 = temp;
        }
        int length1 = string1.length();
        int length2 = string2.length();
        int carry = 0;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length1; i++) {
            int digit1 = i < length1 ? string1.charAt(length1 - 1 - i) - '0' : 0;
            int digit2 = i < length2 ? string2.charAt(length2 - 1 - i) - '0' : 0;

            int difference = digit1 - digit2 - carry;
            if (difference < 0) {
                difference += 10;
                carry = 1;
            } else {
                carry = 0;
            }
            result.insert(0, difference);
        }
        while (result.length() > 1 && result.charAt(0) == '0') {
            result.deleteCharAt(0);
        }
        return result.toString();
    }

    /**
     * Remove leading zeros from result
     * @param string
     * @return
     */
    public static String removeZeros(String string) {
        int i = 0;
        int length = string.length();
        while (i < length && string.charAt(i) == '0') {
            i++;
        }
        if (i == length) {
            return "0";
        }
        return string.substring(i);
    }

}
