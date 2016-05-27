package main;

/**
 * Created by josetarsitano on 27/05/2016.
 */
public class ParsingError extends Exception {
    String _msg = "Parsing Error: ";

    @Override
    public String toString() {
        return _msg;
    }
}

class OperatorError extends ParsingError {
    String _msg = "Operator Error";

    @Override
    public String toString() {
        return super.toString() + _msg;
    }
}

class ExpressionError extends ParsingError {
    String _msg = "Expression Error";

    @Override
    public String toString() {
        return super.toString() + _msg;
    }
}

class ParenthesisError extends ParsingError {
    String _msg = "Parenthesis Error";

    @Override
    public String toString() {
        return super.toString() + _msg;
    }
}