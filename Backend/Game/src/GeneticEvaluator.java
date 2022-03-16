import java.util.*;

interface Evaluator {
    /**
     * Return the unit's executable program.
     *
     * @param unit the unit
     * @return the executable program of input unit
     * @throws SyntaxErrorException if an unidentified token type has been found
     */
    Executable evaluate(Unit unit) throws SyntaxErrorException, TokenizeErrorException;
}

public class GeneticEvaluator implements Evaluator {

    // Singleton
    private static GeneticEvaluator instance;
    // GeneticEvaluator field
    private final String[] reservedWordList = {"antibody", "down", "downleft", "downright",
            "else", "if", "left", "move", "nearby", "right",
            "shoot", "then", "up", "upleft", "upright", "virus", "while" , "isActionTaken"};
    private final Set<String> reservedWords = new HashSet<>(List.of(reservedWordList));
    private final String[] directionWordList = {"up", "left", "right", "down",
            "upleft", "upright", "downleft", "downright"};
    private final Set<String> directionWords = new HashSet<>(List.of(directionWordList));
    private final Tokenizer tkz;
    private Map<String, Integer> bindings;
    private Unit unit;
    private GeneticEvaluator() {
        this.tkz = Tokenizer.getInstance();
    }

    public static GeneticEvaluator getInstance() {
        if (GeneticEvaluator.instance == null)
            GeneticEvaluator.instance = new GeneticEvaluator();
        return GeneticEvaluator.instance;
    }

    @Override
    public Executable evaluate(Unit unit) throws TokenizeErrorException, SyntaxErrorException {

        tkz.initialize(unit.getGene());
        this.bindings = unit.getBindings();
        this.unit = unit;

        return parseProgram();

    }

    private Executable parseProgram() throws SyntaxErrorException, TokenizeErrorException {
        try {

            Program program = new Program(bindings);
            program.addStatement(parseStatement());
            while (tkz.hasNext())
                program.addStatement(parseStatement());
            return program;
        }catch (Exception e){
            return null;
        }
    }

    private Executable parseStatement() throws SyntaxErrorException, TokenizeErrorException {
        if (tkz.peek("{"))
            return parseBlockStatement();
        else if (tkz.peek("if"))
            return parseIfStatement();
        else if (tkz.peek("while"))
            return parseWhileStatement();
        else
            return parseCommand();
    }

    private Executable parseCommand() throws SyntaxErrorException, TokenizeErrorException {
        if (tkz.peek("move") || tkz.peek("shoot"))
            return parseActionCommand();
        else
            return parseAssignmentStatement();
    }

    private Executable parseAssignmentStatement() throws SyntaxErrorException, TokenizeErrorException {
        String identifier = tkz.consume();
        tkz.consume("=");
        Evaluable expression = parseExpression();
        if (reservedWords.contains(identifier))
            throw new SyntaxErrorException("Use reserved word as identifier: " + identifier);
        return new AssignmentStatement(identifier, expression, bindings);
    }

    private Executable parseActionCommand() throws SyntaxErrorException, TokenizeErrorException {
        if (tkz.peek("shoot"))
            return parseAttackCommand();
        else if (tkz.peek("move"))
            return parseMoveCommand();
        else
            return null;
    }

    private Executable parseMoveCommand() throws SyntaxErrorException, TokenizeErrorException {
        tkz.consume("move");
        String direction = tkz.consume();
        if (!directionWords.contains(direction))
            throw new SyntaxErrorException("Missing direction word");
        return new MoveCommand(unit, direction , bindings);
    }

    private Executable parseAttackCommand() throws SyntaxErrorException, TokenizeErrorException {
        tkz.consume("shoot");
        String direction = tkz.consume();
        if (!directionWords.contains(direction))
            throw new SyntaxErrorException("Missing direction word");
        return new AttackCommand(unit, direction , bindings);
    }

    private Executable parseBlockStatement() throws SyntaxErrorException, TokenizeErrorException {
        tkz.consume("{");
        BlockStatement block = new BlockStatement();
        while (!tkz.peek("}"))
            block.addStatement(parseStatement());
        tkz.consume("}");

        return block;
    }

    private Executable parseIfStatement() throws SyntaxErrorException, TokenizeErrorException {
        tkz.consume("if");
        tkz.consume("(");
        Evaluable expression = parseExpression();
        tkz.consume(")");
        tkz.consume("then");
        Executable trueStatement = parseStatement();
        tkz.consume("else");
        Executable falseStatement = parseStatement();

        return new IfStatement(trueStatement, falseStatement, expression);
    }

    private Executable parseWhileStatement() throws SyntaxErrorException, TokenizeErrorException {
        tkz.consume("while");
        tkz.consume("(");
        Evaluable expression = parseExpression();
        tkz.consume(")");
        Executable statement = parseStatement();

        return new WhileStatement(statement, expression);
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
        if (tkz.peek("^")) {
            String ops = tkz.consume();
            power = new BinaryArith(power, ops, parseFactor());
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
            return new SensorExpression(command, tkz.consume(),unit);
        } else
            return new SensorExpression(command,unit);
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
