package main;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by olivier on 19/05/2016.
 */
public class Checker {
    /* Operator */
    static char AND = '^';

    /* Negation */
    static char NOT = '!';

    /* Brackets */
    static char P_LEFT = '(';
    static char P_RIGHT = ')';

    /* Symbols */
    static String EG = "EG";
    static String EX = "EX";

    static String[] atomic = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

    static int _nbP_LEFT = 0;
    static int _nbP_RIGHT = 0;

    static int _indexP_LEFT = 0;
    static int _indexP_RIGHT = 0;

    public static Model label(String exp, Model M) throws Error {
        String[] expr = phi(exp);
        System.out.println("Labeling of : " + exp);

        // if φ = p
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
        // else if φ = ¬φ'
        else if(expr[1].equals(String.valueOf(NOT))) {
            Model Mp = new Model(label(expr[0], M));
            for (int i = 0 ; i < M.getNbNode() ; i++) {
                M.setNodeLabel(i, !Mp.getNodeLabel(i));
            }
            return M;
        }
        // else if φ = φ' ∧ φ''
        else if(expr[1].equals(String.valueOf(AND))){
            Model Mp = new Model(label(expr[0], M));
            Model Mpp = new Model(label(expr[2], M));

            for (int i = 0 ; i < M.getNbNode() ; i++) {
                M.setNodeLabel(i, Mp.getNodeLabel(i) && Mpp.getNodeLabel(i));
            }

            return M;
        }
        // else if φ = EX(φ')
        else if(expr[1].equals(EX)) {
            Model Mp = new Model(label(expr[0], M));
            Mp.printLabel();

            for(Node n : M._nodes) {
                n.setPhi(false);
            }

            Mp.printLabel();
            ArrayList<Neighbor> ngh = M.findNeighbor();
            for (Neighbor n: ngh) {
                Boolean phiP = Mp.getPhi(n.getD().getName());
                if (phiP)
                    n.getS().setPhi(true);
            }
            return M;
        }
        // else if φ = E(φ' U φ'')
        else if(expr[1].equals("E(")) {
            Model Mp = new Model(label(expr[0], M));
            Model Mpp = new Model(label(expr[2], M));
            for (Node n : M._nodes) {
                n.setPhi(false);
                n.setSeenBefore(false);
            }
            ArrayList<Node> l = new ArrayList<Node>();
            for (Node n : M._nodes) {
                if (Mpp.getPhi(n.getName()))
                    l.add(n);
            }
            while (!l.isEmpty()) {
                Node s = M.getNode(l.get(l.size()-1).getName());
                l.remove(l.get(l.size()-1));
                s.setPhi(true);
                ArrayList<Neighbor> ngh = M.findNeighbor();
                for (Neighbor n: ngh) {
                    if (!n.getS().getSeenBefore()) {
                        n.getS().setSeenBefore(true);
                        if (Mp.getNode(n.getS().getName()).getPhi())
                            l.add(n.getS());
                    }
                }
            }

            return M;
        }
        // else if φ = A(φ' U φ'')
        else if(expr[1].equals("A(")) {
            Model Mp = new Model(label(expr[0], M));
            Model Mpp = new Model(label(expr[2], M));
            ArrayList<Node> l = new ArrayList<Node>();
            for (Node n : M._nodes) {
                n.setNb(n.getDeg());
                M.getNode(n.getName()).setPhi(false);
                if (Mpp.getNode(n.getName()).getPhi())
                    l.add(n);
            }
            while (!l.isEmpty()) {
                Node s = M.getNode(l.get(l.size()-1).getName());
                l.remove(l.get(l.size()-1));
                s.setPhi(true);
                ArrayList<Neighbor> ngh = M.findNeighbor();
                for (Neighbor n: ngh) {
                    n.getS().setNb(n.getS().getNb()-1);
                    if (n.getS().getNb() == 0 && Mp.getNode(n.getS().getName()).getPhi() && !(M.getNode(n.getS().getName()).getPhi()))
                        l.add(n.getS());
                }
            }

            return M;
        }
        // else if φ = EG(φ')
        else if(expr[1].equals(EG)){
            Model Mp = new Model(label(expr[0], M));
            // ensemble d'états qui ne satisfont pas EG φ
            ArrayList<Node> E = new ArrayList<Node>();
            // ensemble d'états qui satisfont EG φ
            ArrayList<Node> T = new ArrayList<Node>();

            for (Node n: Mp._nodes) {
                if (n.getPhi())
                    E.add(n);
                else
                    T.add(n);
            }
//
//            System.out.println("E : ");
//            for (Node n: E) {
//                System.out.println("n = " + n);
//            }
//
//            System.out.println("T : ");
//            for (Node n: T) {
//                System.out.println("n = " + n);
//            }

            HashMap<String, Integer> C = new HashMap<String, Integer>();

            for (Node n : T)
                C.put(n.getName(), n.getDeg());

            while (!E.isEmpty()){
                Node s = E.get(E.size()-1);
                E.remove(E.get(E.size()-1));

                for(Node n : M.pre(s)) {
                    if(T.contains(n)){
                        System.out.println("HERE2");
                        C.put(n.getName(), C.get(n.getName())-1);
                        if(C.get(n.getName()) == 0) {
                            T.remove(n);
                            E.add(n);
                        }
                    }

                }
            }

            return M;
        }

        // Else problem
        throw new Error("Not recognize");
    }

    public static String[] phi(String exp) {
        int i;
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
            expr[0] = exp.substring(3, exp.length()-1);
            expr[1] = EX;
            expr[2] = null;
        /* EG(φ') */
        } else if (exp.startsWith(EG)) {
            expr[0] = exp.substring(3, exp.length()-1);
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

    public static boolean isSatisfy(String CTL, Model M) throws ParsingError {
        /* Evaluation des parentheses */
        for (int i = 0; i < CTL.length(); i++) {
            if (CTL.charAt(i) == P_LEFT)
                _nbP_LEFT++;
            else if (CTL.charAt(i) == P_RIGHT)
                _nbP_RIGHT++;
        }
        if (_nbP_LEFT != _nbP_RIGHT)
            throw new ParenthesisError();

        M = label(CTL, M);
        boolean isSat = false;
        for (Node n : M._nodes) {
            isSat = isSat || n.getPhi();
        }
        return isSat;
    }
}

