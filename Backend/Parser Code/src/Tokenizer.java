import java.util.Objects;

/**
 * Interface for Tokenizer
 */
interface token {
    /**
     * Return true if the tokenizer has the next token
     *
     * @return Return true if the tokenizer has the next token
     */
    boolean hasNext();

    /**
     * Return the next token in input stream.
     *
     * @return the next token
     */
    String peek();

    /**
     * Return true if the next token is equal to the specified token or not.
     *
     * @param s the specified token
     * @return true if next token is the same as the specified token.
     * Otherwise, false.
     */
    boolean peek(String s);

    /**
     * Consumes one token and return it.
     *
     * @return the consumed token
     * @throws TokenizeErrorException if an unidentified token type has been found
     */
    String consume() throws TokenizeErrorException;

    /**
     * Consumes the specified token.
     *
     * @param s the specified token
     * @throws TokenizeErrorException if the token specified cannot be found or consumed
     */
    void consume(String s) throws TokenizeErrorException;
}

/**
 * The Tokenizer class is used to read tokens one at a time
 * from the input stream and pass the tokens to the parser.
 */
public class Tokenizer implements token {

    private static Tokenizer instance;
    private String src;
    private String next;
    private int pos;

    private Tokenizer() {

    }

    public static Tokenizer getInstance() {
        if (Tokenizer.instance == null)
            Tokenizer.instance = new Tokenizer();
        return Tokenizer.instance;
    }

    /**
     * Construct a tokenizer of the specified input stream
     *
     * @param src the input stream
     * @throws TokenizeErrorException if an unidentified token type has been found
     */
    public void initialize(String src) throws TokenizeErrorException {
        this.src = src;
        pos = 0;
        computeNext();
    }

    private void computeNext() throws TokenizeErrorException {
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
            } else if (isSpace(c))
                pos++;
            else
                throw new TokenizeErrorException("Unknown character: " + c);
        }
        next = s.toString();
    }

    private boolean isSpace(char c) {
        return c == ' ';
    }

    private boolean isAlphaNumeric(char c) {
        return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'z')
                || (c >= 'A' && c <= 'Z') || c == '_';
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
    public String consume() throws TokenizeErrorException {
        String result = next;
        computeNext();
        return result;
    }

    public void consume(String s) throws TokenizeErrorException {
        if (peek(s))
            consume();
        else
            throw new TokenizeErrorException("Missing word: " + s);
    }
}