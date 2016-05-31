package main;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws ParsingError {
		//String CTLFormula = "E(pUq)";
		//String CTLFormula = "A(pUq)";
		//String CTLFormula = "EG(a^p)";
		//String CTLFormula = "(!(EX(!(p))))^(p)";
		//String CTLFormula = "(p)^(!(q))";
        String CTLFormula = "E(pU!(p))";
		Model M = new Model();

        if (Checker.isSatisfy(CTLFormula, M)) {
            System.out.println(CTLFormula + " is satisfied by the model.");
        } else {
            System.out.println(CTLFormula + " is NOT satisfied by the model.");
        }
	}
}
