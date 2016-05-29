package main;

public class Main {

	public static void main(String[] args) throws ParsingError {
		//String CTLFormula = "(!(EX!p))^p";
		//String CTLFormula = "!(EX!p)";
		//String CTLFormula = "E(pUq)";
		//String CTLFormula = "A(pUq)";
		//String CTLFormula = "EG(a^p)";
		String CTLFormula = "EX(pUq)";

		Model M = new Model();
		Checker.ParseExpression(CTLFormula);
	}
}
