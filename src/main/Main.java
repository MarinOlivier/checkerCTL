package main;

public class Main {

	public static void main(String[] args) throws ParsingError {
		String CTLFormula = "(AX(a OR x)) AND ((!EX!p) AND p)";

        Checker.ParseExpression(CTLFormula);
	}
}
