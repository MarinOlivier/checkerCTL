package main;

public class Main {

	public static void main(String[] args) throws ParsingError {
		//String CTLFormula = "AX(a v (!(EX(a ^ !p))))";
		String CTLFormula = "!(E(EX!p))";

		Model M = new Model();
		Checker.ParseExpression(CTLFormula);
	}
}
