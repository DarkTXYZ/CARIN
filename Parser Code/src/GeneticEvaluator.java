import java.util.*;

interface Evaluator {
    /**
     * Return the unit's executable program.
     *
     * @param unit the unit
     * @return the executable program of input unit
     * @throws SyntaxErrorException if an unidentified token type has been found
     */
    Executable evaluate(Unit unit) throws SyntaxErrorException;
}

public class GeneticEvaluator implements Evaluator {

    // Singleton
    private static GeneticEvaluator instance;
    private GeneticEvaluator() {

    }

    public static GeneticEvaluator getInstance() {
        if(GeneticEvaluator.instance == null)
            GeneticEvaluator.instance = new GeneticEvaluator();
        return GeneticEvaluator.instance;
    }

    private Tokenizer tkz;
    private Map<String, Integer> bindings;
    private Unit unit;

    private final String[] reservedWordList = {"antibody", "down", "downleft", "downright",
            "else", "if", "left", "move", "nearby", "right",
            "shoot", "then", "up", "upleft", "upright", "virus", "while"};
    private final Set<String> reservedWords = new HashSet<>(List.of(reservedWordList));

    private final String[] directionWordList = {"up", "left", "right", "down",
            "upleft", "upright", "downleft", "downright"};
    private final Set<String> directionWords = new HashSet<>(List.of(directionWordList));


    @Override
    public Executable evaluate(Unit unit) {
        return null;
    }

    private Evaluable parseExpression() throws SyntaxErrorException, TokenizeErrorException {
        Evaluable term = parseTerm();
        while (tkz.peek("+") || tkz.peek("-")) {
            String ops = tkz.consume();
            term = new BinaryArith(term, ops, parseTerm());
        }
        return term;
    }

    private Evaluable parseTerm() throws SyntaxErrorException, TokenizeErrorException {
        Evaluable factor = parseFactor();
        while (tkz.peek("*") || tkz.peek("/") || tkz.peek("%")) {
            String ops = tkz.consume();
            factor = new BinaryArith(factor, ops, parseFactor());
        }
        return factor;
    }

    private Evaluable parseFactor() throws SyntaxErrorException, TokenizeErrorException {
        Evaluable power = parsePower();
        while (tkz.peek("^")) {
            String ops = tkz.consume();
            power = new BinaryArith(power, ops, parsePower());
        }
        return power;
    }

    private Evaluable parsePower() throws SyntaxErrorException, TokenizeErrorException {
        if (isNumber(tkz.peek()))
            return new Number(Integer.parseInt(tkz.consume()));
        else if (tkz.peek("(")) {
            tkz.consume("(");
            Evaluable expression = parseExpression();
            tkz.consume(")");
            return expression;
        } else if (tkz.peek("virus") || tkz.peek("antibody") || tkz.peek("nearby"))
            return parseSensorExpression();
        else if (tkz.peek("random"))
            return parseRandom();
        else if (tkz.hasNext() && !tkz.peek(")")) {
            String identifier = tkz.consume();
            if (reservedWords.contains(identifier))
                throw new SyntaxErrorException("Use reserved word as variable name: " + identifier);
            return new Identifier(identifier, bindings);
        } else
            throw new SyntaxErrorException("Missing Number, Identifier, SensorExpression or RandomValue");
    }

    private Evaluable parseRandom() throws SyntaxErrorException, TokenizeErrorException {
        tkz.consume("random");
        return new RandomValue();
    }

    private Evaluable parseSensorExpression() throws SyntaxErrorException, TokenizeErrorException {
        String command = tkz.consume();
        if (Objects.equals(command, "nearby")) {
            if (!directionWords.contains(tkz.peek()))
                throw new SyntaxErrorException("Missing direction word");
            return new SensorExpression(command, tkz.consume());
        } else
            return new SensorExpression(command);
    }

    private boolean isNumber(String peek) {
        try {
            Integer.parseInt(peek);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
