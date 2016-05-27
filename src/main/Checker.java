package main;

/**
 * Created by olivier on 19/05/2016.
 */
public class Checker {
    /* Operator */
    static String AND = "AND";
    static String OR = "OR";

    /* Negation */
    static char NOT = '!';

    static char P_LEFT = '(';
    static char P_RIGHT = ')';
    static char E = 'E';
    static char A = 'A';
    static char X = 'X';

    static final String atomic[] = {"a","b","c","d","e","f","g","h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};


    static int _nbP_LEFT = 0;
    static int _nbP_RIGHT = 0;

    static int _indexP_LEFT = 0;
    static int _indexP_RIGHT = 0;
    static boolean _firstP = false;

    static final String O_parOpen = "(";

    public static String label(String exp) throws Error {
        // If φ = p
        // Else if φ = ¬ φ′
        // Else if φ = φ′ ∧ φ′′
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
        for(int i = 0; i < _nbP_LEFT; i++)
            System.out.println("phi = " + phi(s));

        return true;
    }

    public static String phi(String s) {
        int i = 0;
        if (!_firstP) {
            /* Extraction du premier phi (i.e. le plus profond dans la chaine) */
            for (i = 0; i < s.length(); i++) {
                if (s.charAt(i) == P_LEFT && _indexP_LEFT != i)
                    _indexP_LEFT = i;
                else if (s.charAt(i) == P_RIGHT && _indexP_RIGHT != i) {
                    _indexP_RIGHT = i;
                    _firstP = true;
                    break;
                }
            }
        } else {
            /* On remonte vers la gauche apres la premiere parenthese gauche trouvee */
            for (i = _indexP_LEFT-1; i >= 0; i--) {
                if (s.charAt(i) == P_LEFT) {
                    _indexP_LEFT = i;
                    break;
                }
            }

            /* On remonte vers la droite apres la premiere parenthese droite trouvee */
            for (i = _indexP_RIGHT+1; i < s.length(); i++) {
                if (s.charAt(i) == P_RIGHT) {
                    _indexP_RIGHT = i;
                    break;
                }
            }
        }
        return s.substring(_indexP_LEFT, _indexP_RIGHT+1);
    }
}

