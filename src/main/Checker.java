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

    /* Symbols */
    static String E = "E";
    static String A = "A";

    static String G = "G";
    static String F = "F";
    static String X = "X";

    /* Errors */
    static String[] Err = {"!!", "! ", "(v", "(^"};

    static final String[] P = {"AG", "AF", "AX", "EG", "EF", "EX", "A", "E", "X"};

    static final String atomic[] = {"a","b","c","d","e","f","g","h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

    static int _nbP_LEFT = 0;
    static int _nbP_RIGHT = 0;

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

    public static boolean ParseExpression(String exp) throws ParsingError {
        /* Evaluation des parentheses */
        for (int i = 0; i < exp.length(); i++) {
            if (exp.charAt(i) == P_LEFT)
                _nbP_LEFT++;
            else if (exp.charAt(i) == P_RIGHT)
                _nbP_RIGHT++;
        }
        if (_nbP_LEFT != _nbP_RIGHT)
            throw new ParenthesisError();

        System.out.println("exp = " + exp);
        /* Extraction des phi */
        satisfy(exp);

        return true;
    }

    public static String satisfy(String exp) throws ParsingError {
        /* Exceptions */
        for (String err : Err) {
            if (exp.startsWith(err))
                throw new ExpressionError(exp.substring(0, 2));
        }

        // TODO Supprimer ')' dans exp pour pouvoir la traiter
        System.out.println(exp);

        if (startsWithAtomic(exp)) {
            System.out.println(exp.substring(1));
            return exp.substring(1);
        }

        /* AG, AF, AX, EG, EF, EX, A, E, X */
        for (int i = 0; i < P.length; i++) {
            if (exp.startsWith(P[i])) {
                //System.out.println(exp.substring(P[i].length()));
                return satisfy(exp.substring(P[i].length()));
            }
        }

        /* (φ' * φ''), !(φ) */
        char phi = exp.charAt(0);
        if (phi == P_LEFT || phi == NOT) {
            /* ( */
            String phi1;
            if (phi == P_LEFT)
                phi1 = satisfy(exp.substring(1));
            else
                phi1 = satisfy(exp.substring(2));

            String phi2 = "";
            /* OR */
            if (phi1.startsWith(OR))
                phi2 = satisfy(phi1.substring(OR.length()));
            /* AND */
            else if (phi1.startsWith(AND))
                phi2 = satisfy(phi1.substring(AND.length()));
            /* UNION */
            else if (phi1.startsWith(U))
                phi2 = satisfy(phi1.substring(U.length()));
            /* ( */
            else if (phi2.startsWith(String.valueOf(P_LEFT)))
                return phi1.substring(1);
            /* ) */
            else
                return "";
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

