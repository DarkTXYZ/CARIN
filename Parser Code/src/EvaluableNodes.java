import java.util.Map;
import java.util.Objects;
import java.util.Random;

interface Evaluable {
    /**
     * Evaluate and return the Evaluable node
     * @return the evaluation value
     */
    int eval();
}

class Number implements Evaluable {
    int value;

    public Number(int value) {
        this.value = value;
    }

    @Override
    public int eval() {
        return value;
    }
}

class Identifier implements Evaluable {

    Map<String, Integer> bindings;
    String variable;

    public Identifier(String variable, Map<String, Integer> bindings) throws SyntaxErrorException {
        this.variable = variable;
        this.bindings = bindings;
    }

    @Override
    public int eval() {
        return bindings.getOrDefault(variable, 0);
    }
}

class SensorExpression implements Evaluable {

    String command, direction = null;

    public SensorExpression(String command) {
        this.command = command;
    }

    public SensorExpression(String command, String direction) {
        this.command = command;
        this.direction = direction;
    }

    //left | right | up | down | upleft | upright | downleft | downright
    @Override
    public int eval() {
        if (Objects.equals(command, "virus")) {
            System.out.println("Finding Closest Virus");
            return 69;
        } else if (Objects.equals(command, "antibody")) {
            System.out.println("Finding Closest ATBD");
            return 420;
        } else {
            System.out.println("Finding Closest Unit in " + direction + " direction");
            return 748;
        }
    }
}

class RandomValue implements Evaluable {

    Random rand = new Random();

    @Override
    public int eval() {
        return rand.nextInt(100);
    }
}

class BinaryArith implements Evaluable {
    Evaluable leftEval, rightEval;
    String operation;

    public BinaryArith(Evaluable leftEval, String operation, Evaluable rightEval) {
        this.leftEval = leftEval;
        this.rightEval = rightEval;
        this.operation = operation;
    }

    @Override
    public int eval() {
        int x = leftEval.eval();
        int y = rightEval.eval();

        return switch (operation) {
            case "+" -> x + y;
            case "-" -> x - y;
            case "*" -> x * y;
            case "/" -> x / y;
            case "%" -> x % y;
            case "^" -> (int) Math.pow(x, y);
            default -> 0;
        };

    }
}