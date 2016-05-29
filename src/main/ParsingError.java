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

class ParenthesisError extends ParsingError {
    String _msg = "expected parenthesis";

    @Override
    public String toString() {
        return super.toString() + _msg;
    }
}

class ExpressionError extends ParsingError {
    String _msg = "unrecognized expression at ";

    ExpressionError(String err) {
        _msg += err + "...";
    }

    @Override
    public String toString() {
        return super.toString() + _msg;
    }
}