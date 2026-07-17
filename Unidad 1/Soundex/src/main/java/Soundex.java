public class Soundex {

    public static void main(String[] args) {
        System.out.println(soundex("LUxuRY"));          // L260
        System.out.println(soundex("SZLLOYDTIRUL"));    // S436
        System.out.println(soundex("phone"));           // P500
        System.out.println(soundex("foun"));            // F500
        System.out.println(soundex("h4ppy c0d1ng"));    // H152
    }

    public static String soundex(String s) {
        char[] in = to_upper(s);

        if (in.length == 0) {
            return "0000";
        }

        char[] out = {'0', '0', '0', '0'};

        out[0] = in[0]; // el primero se deja como esta
        int count = 1;

        char last = getMapping(in[0]);
        char current;

        for (int i = 1; i < in.length && count < 4; i++) {
            current = getMapping(in[i]);

            if (current != '0' && current != last) {
                out[count++] = current;
            }

            last = current;
        }

        return new String(out);
    }

    public static char[] to_upper(String s) {
        char[] aux = new char[s.length()];
        int j = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 'a' && c <= 'z') {
                aux[j++] = (char)(c - ('a' - 'A'));
            }
            else if (c >= 'A' && c <= 'Z') {
                aux[j++] = c;
            }
        }

        char[] res = new char[j];
        for (int i = 0; i < j; i++) {
            res[i] = aux[i];
        }

        return res;
    }

    public static char getMapping(char c) {
        switch (c) {
            case 'A': case 'E': case 'I': case 'O':
            case 'U': case 'Y': case 'W': case 'H':
                return '0';

            case 'B': case 'F': case 'P': case 'V':
                return '1';

            case 'C': case 'G': case 'J': case 'K':
            case 'Q': case 'S': case 'X': case 'Z':
                return '2';

            case 'D': case 'T':
                return '3';

            case 'L':
                return '4';

            case 'M': case 'N':
                return '5';

            case 'R':
                return '6';

            default:
                return '0';
        }
    }
}