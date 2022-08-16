public class RLE {

    /**
     * Run-Length Encoding
     * encodes a String of letters (value 65 <= x <= 90 for a char x)
     * @param s String to encode
     * @return encoded String
     */
    public static String encode(String s) {
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {

            if (i < s.length() - 1 && s.charAt(i) == s.charAt(i + 1)) {
                int duplicates = 0;

                for (int j = i + 1; j < s.length() && s.charAt(i) == s.charAt(j); j++) {
                    duplicates++;
                }

                output.append(1 + duplicates).append(s.charAt(i));
                i += duplicates;

            } else {
                output.append(s.charAt(i));
            }
        }
        return output.toString();
    }

    /**
     * decodes a Run-Length encoded String
     * required format: number of characters, character to duplicate
     * if there is no number in front of a letter, one is assumed
     * example: '3W2FC' decoded returns 'WWWFFC'
     * @param s String to decode
     * @return decoded string
     */
    public static String decode(String s) {
        StringBuilder output = new StringBuilder();
        StringBuilder digits;

        for (int i = 0; i < s.length(); i++) {
            digits = new StringBuilder();
            if (isNumber(s.charAt(i))) {
                for (int j = i; j < s.length() && isNumber(s.charAt(j)); j++) {
                    digits.append(s.charAt(j));
                }
                int num = Integer.parseInt(digits.toString());

                for (int k = 0; k < num; k++) {
                    output.append(s.charAt(i + digits.length()));
                }
                i += digits.length();
            } else {
                output.append(s.charAt(i));
            }
        }
        return output.toString();
    }

    private static boolean isNumber(char c) {
        return switch (c) {
            case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> true;
            default -> false;
        };
    }

    public static void main(String[] args) {
        test(generateTestcases(10, 50));
    }

    public static String[] generateTestcases(int caseNumber, int maxStringLength){
        // 65 <= x <= 90 for every character x
        String[] output = new String[caseNumber];

        for (int i = 0; i < output.length; i++) {
            StringBuilder s = new StringBuilder();
            int sLength = (int)(Math.random() * maxStringLength);

            for (int j = 0; j < sLength+1; j++) {
                if(Math.random() >= 0.7 || j == 0) {
                    char c = (char) (65 + (int) (Math.random() * 25));
                    s.append(c);
                }else {
                    s.append(s.charAt(j - 1));
                }
            }
            output[i] = s.toString();
        }
        return output;
    }

    public static void test(String[] test){
        try {
            for (String s : test) {
                test(s);
            }
        } catch (AssertionError e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void test(String t) {
        String encodedString = encode(t);
        String decodedString = decode(encodedString);
        System.out.println("String:" + '\t' + '\t' + t);
        System.out.println("encoded:" + '\t' + encodedString);
        System.out.println("decoded:" + '\t' + decodedString);

        boolean valid = (t.equals(decode(encodedString)));
        System.out.println("valid: " + '\t' + '\t' + valid + '\n');
        assert !valid;
    }
}
