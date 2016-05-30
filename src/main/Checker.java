package main;

import java.util.Arrays;
import java.util.Objects;

/**
 * Created by olivier on 19/05/2016.
 */
public class Checker {
    /* Operator */
    static char AND = '^';

    /* Negation */
    static char NOT = '!';

    static char P_LEFT = '(';
    static char P_RIGHT = ')';
    static String EG = "EG";
    static String EX = "EX";

    static final String atomic[] = {"a","b","c","d","e","f","g","h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};


    static int _nbP_LEFT = 0;
    static int _nbP_RIGHT = 0;

    static int _indexP_LEFT = 0;
    static int _indexP_RIGHT = 0;
    static boolean _firstP = false;

    public static Model label(String exp, Model M) throws Error {
        String[] expr = phi(exp);
        System.out.println("Labeling of : " + exp);

        // If φ = p
        if(Arrays.asList(atomic).contains(exp)) {
            System.out.println("IN Atomic");
            for (Node n: M._nodes) {
                if(n.getVal().equals(exp))
                    n.setPhi(true);
                else
                    n.setPhi(false);
            }
            return M;
        }
        // Else if φ = ¬ φ′
        else if(expr[1].equals(String.valueOf(NOT))) {
            Model Mp = label(expr[0], M);
            for (int i = 0 ; i < M.getNbNode() ; i++) {
                M.setNodeLabel(i, !Mp.getNodeLabel(i));
            }
            return M;
        }
        // Else if φ = φ′ ∧ φ′′
        else if(expr[1].equals(String.valueOf(AND))){
            Model Mp = label(expr[0], M);
            Model Mpp = label(expr[2], M);

            for (int i = 0 ; i < M.getNbNode() ; i++) {
                M.setNodeLabel(i, Mp.getNodeLabel(i) && Mpp.getNodeLabel(i));
            }

            return M;
        }
        // Else if φ = EX φ′
        else if(expr[1].equals(EX)){
            for(Node n : M._nodes) {
                n.setPhi(false);
            }

        }
        // Else if φ = Eφ′U φ′′
        // Else if φ = Aφ′U φ′′
        // Else if φ = EG φ′
        // Else problem
        throw new Error("Not recognize");
    }

    public static boolean ParseExpression(String s) throws ParsingError {
        /* Evaluation des parentheses */
        /*for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == P_LEFT)
                _nbP_LEFT++;
            else if (s.charAt(i) == P_RIGHT)
                _nbP_RIGHT++;
        }
        if (_nbP_LEFT != _nbP_RIGHT)
            throw new ParenthesisError();*/

        /* Extraction des phi */
        String lol[] = phi(s);
        for (String str : lol)
            System.out.println("str = " + str);

        return true;
    }

    public static String[] phi(String exp) {
        int i = 0;
        String expr[] = new String[3];
        // Si exp commence par '('
        if (exp.startsWith(String.valueOf(P_LEFT))) {
            for (i = 0; i < exp.length(); i++) {
                if (exp.charAt(i) == P_LEFT) {
                    _nbP_LEFT++;
                } else if (exp.charAt(i) == P_RIGHT) {
                    _nbP_RIGHT++;
                }
                if (_nbP_LEFT == _nbP_RIGHT) {
                    _indexP_RIGHT = i;
                /* (φ' * φ'') */
                    if (exp.substring(_indexP_LEFT, _indexP_RIGHT + 1).length() != exp.length()) {
                        if (exp.charAt(_indexP_RIGHT + 1) == AND) {
                            expr[0] = exp.substring(_indexP_LEFT+1, _indexP_RIGHT);
                            expr[1] = exp.substring(_indexP_RIGHT + 1, _indexP_RIGHT + 2);
                            expr[2] = exp.substring(_indexP_RIGHT + 3, exp.length()-1);
                            break;
                        }
                    /* (φ) */
                    } else {
                        expr[0] = exp;
                        expr[1] = null;
                        expr[2] = null;
                        break;
                    }
                }
            }
        /* !(φ') */
        } else if (exp.startsWith(String.valueOf(NOT))) {
            expr[0] = exp.substring(2, exp.length()-1);
            expr[1] = String.valueOf(NOT);
            expr[2] = null;
        /* EX(φ') */
        } else if (exp.startsWith(EX)) {
            expr[0] = exp.substring(EX.length());
            expr[1] = EX;
            expr[2] = null;
        /* EG(φ') */
        } else if (exp.startsWith(EG)) {
            expr[0] = exp.substring(EG.length());
            expr[1] = EG;
            expr[2] = null;
        /* E(φ' U φ'') */
        } else if (exp.startsWith("E(")) {
            i = 0;
            boolean found = false;
            while (!found) {
                i++;
                if (String.valueOf(exp.charAt(i)).equals("U"))
                    found = true;
            }
            expr[0] = exp.substring(2, i);
            expr[1] = "E(";
            expr[2] = exp.substring(i+1, exp.length()-1);
        /* A(φ' U φ'') */
        } else if (exp.startsWith("A(")) {
            i = 0;
            boolean found = false;
            while (!found) {
                i++;
                if (String.valueOf(exp.charAt(i)).equals("U"))
                    found = true;
            }
            expr[0] = exp.substring(2, i);
            expr[1] = "A(";
            expr[2] = exp.substring(i + 1, exp.length()-1);
        }
        return expr;
    }

    public static boolean isSatisfy(String CTL, Model M) {
        M = label(CTL, M);
        boolean isSat = false;
        for (Node n : M._nodes) {
            isSat = isSat || n.getPhi();
        }
        return isSat;
    }
}

