package main;

/**
 * Created by olivier on 19/05/2016.
 */
public class Checker {

    static final String top = "1";
    static final String bot = "0";
    static final String atomic[] = {"a","b","c","d","e","f","g","h","i","j","k","l",
                                    "m","n","o","p","q","r","s","t","u","v","w","x","y","z"};

    static final String op_parOpen = "(";
    static final String op_parClose = ")";
    static final String op_not = "-";
    static final String op_and = "*";
    static final String op_or = "+";
    static final String op_equiv = "<=>";
    static final String op_imply = "=>";

    static final String at_all = "A";
    static final String at_exist = "E";
    static final String at_next = "X";
    static final String at_globally = "G";
    static final String at_finally = "F";
    static final String at_until = "U";
    static final String at_space = " ";
    static final String at_crOpen = "[";
    static final String at_crClose = "]";

    public static boolean isCTL(String exp) {
        String res = null;
        try {
            res = check(exp);
        } catch (Exception e) {
            System.out.println("Exception " + e.getMessage());
        }

        return res != null && res.length() >= 0;
    }

    private static String check(String exp) throws Error {

        return "Not recognized";
    }
}
