class SyntaxErrorException extends Exception {
    public SyntaxErrorException(String message) {
        super(message);
    }
}

class TokenizeErrorException extends Exception {
    public TokenizeErrorException(String message) {
        super(message);
    }
}

public class ExceptionLibrary {
}
