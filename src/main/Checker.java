package main;

import sun.jvm.hotspot.asm.sparc.SPARCArgument;

/**
 * Created by olivier on 19/05/2016.
 */
public class Checker {
    /* Operators */
    static String AND = " ^ ";
    static String OR = " v ";
    static String U = " U ";

    /* Negation */
    static char NOT = '!';

    /* Brackets */
    static char P_LEFT = '(';
    static char P_RIGHT = ')';

    static String E = "E";
    static String A = "A";

    static String G = "G";
    static String F = "F";
    static String X = "X";

    static final String[] P = {"AG", "AF", "AX", "EG", "EF", "EX", "A", "E", "X"};

    static char SPACE = ' ';

    static final String atomic[] = {"a","b","c","d","e","f","g","h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};


    static int _nbP_LEFT = 0;
    static int _nbP_RIGHT = 0;

    static int _indexP_LEFT = 0;
    static int _indexP_RIGHT = 0;
    static boolean _firstP = false;

    static final String O_parOpen = "(";

    public static String label(String exp, Model M) throws Error {
        // If φ = p
        if(exp.equals("p")) {
            for (Node n: M._nodes) {
                if(n.getVal().equals(exp))
                    n.setPhi(true);
                else
                    n.setPhi(false);
            }
        }
        // Else if φ = ¬ φ′
        else if(exp.equals("!p")) {
            label(exp.substring(1), M);
            for (Node n: M._nodes) {
                n.setPhi(!n.getPhi());
            }
        }
        // Else if φ = φ′ ∧ φ′′
        else if(exp.equals("HAVE AND")){
            String op1 = exp.substring(0, exp.indexOf("AND")-1);
            String op2 = exp.substring(exp.indexOf("AND")+4, exp.length());

            label(op1, M);
            label(op2, M);


        }
        // Else if φ = EX φ′
        // Else if φ = Eφ′U φ′′
        // Else if φ = Aφ′U φ′′
        // Else if φ = EG φ′
        // Else problem
        return "Not recognized";
    }

    public static boolean ParseExpression(String s) throws ParsingError {
        /* Evaluation des parentheses */
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == P_LEFT)
                _nbP_LEFT++;
            else if (s.charAt(i) == P_RIGHT)
                _nbP_RIGHT++;
        }
        if (_nbP_LEFT != _nbP_RIGHT)
            throw new ParenthesisError();

        /* Extraction des phi */
        satisfy(s);

        return true;
    }

    public static String satisfy(String exp) throws ParsingError {
        if (startsWithAtomic(exp)) {
            System.out.println(exp.substring(1));
            return exp.substring(1);
        }

        /* AG, AF, AX, EG, EF, EX, A, E, X */
        for (int i = 0; i < P.length; i++) {
            if (exp.startsWith(P[i])) {
                System.out.println(exp.substring(P[i].length()));
                return satisfy(exp.substring(P[i].length()));
            }
        }

        /* (φ' * φ'') */
        if (exp.startsWith(String.valueOf(P_LEFT))) {
            System.out.println("left = " + exp.substring(1));
            String left = satisfy(exp.substring(1));

            String right = "";
            /* OR */
            if (left.startsWith(OR)) {
                System.out.println("right = " + left.substring(OR.length()));
                right = satisfy(left.substring(OR.length()));
            /* AND */
            } else if (left.startsWith(AND)) {
                System.out.println("right = " + left.substring(AND.length()));
                right = satisfy(left.substring(AND.length()));
            /* UNION */
            } else if (left.startsWith(U)) {  // UNION
                System.out.println("right = " + left.substring(U.length()));
                right = satisfy(left.substring(U.length()));
            }

            // )
            if (right.startsWith(String.valueOf(P_RIGHT)))
                return exp;
        }

        return exp;
    }

    public static boolean startsWithAtomic(String exp) {
        for (String s : atomic) {
            if (exp.startsWith(s))
                return true;
        }

        return false;
    }
}

