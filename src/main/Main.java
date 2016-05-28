package main;

public class Main {

	public static void main(String[] args) throws ParsingError {
		String CTLFormula = "(AG(!q OR f AND (a AND (EX(a OR x))a)) ET !p)";

		Model M = new Model();
		Checker.ParseExpression(CTLFormula);

	}
}
