import java.util.NoSuchElementException;
import java.util.Objects;

interface token {
    String peek();

    String consume() throws SyntaxErrorException;
}

public class Tokenizer implements token {

    private final String src;
    private String next;
    private int pos;

    public Tokenizer(String src) throws SyntaxErrorException {
        if (src.replace(" ", "").isEmpty())
            throw new NoSuchElementException();
        this.src = src;
        pos = 0;
        computeNext();
    }

    private void computeNext() throws SyntaxErrorException {
        StringBuilder s = new StringBuilder();
        while (pos < src.length()) {
            char c = src.charAt(pos);
            if (isAlphaNumeric(c)) {  // start of number
                for (; pos < src.length() && isAlphaNumeric(src.charAt(pos)); pos++)
                    s.append(src.charAt(pos));
                break;
            } else if (c == '+' || c == '-' || c == '*' ||
                    c == '/' || c == '%' || c == '(' || c == ')'
                    || c == '{' || c == '}' || c == '^' || c == '=') {
                s.append(c);
                pos++;
                break;
            } else if (isSpace(c))  // ignore whitespace
                pos++;
            else
                throw new SyntaxErrorException("Unknown character: " + c);
        }
        next = s.toString();
    }

    private boolean isSpace(char c) {
        return c == ' ';
    }

    private boolean isAlphaNumeric(char c) {
        return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'z')
                || (c >= 'A' && c <= 'Z');
    }

    public boolean hasNext() {
        return !Objects.equals(next, "");
    }

    @Override
    public String peek() {
        return next;
    }

    public boolean peek(String s) {
        return peek().equals(s);
    }

    @Override
    public String consume() throws SyntaxErrorException {
        String result = next;
        computeNext();
        return result;
    }

    public void consume(String s) throws SyntaxErrorException {
        if (peek(s)) {
            consume();
        } else {
            throw new SyntaxErrorException("Missing word: " + s);
        }
    }

}