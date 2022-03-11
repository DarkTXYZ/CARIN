import java.util.LinkedList;
import java.util.List;
import java.util.Map;

interface Executable {
    /**
     * Execute the Executable node
     */
    void execute();
}

class IfStatement implements Executable {
    Executable trueStatement;
    Executable falseStatement;
    Evaluable expression;

    public IfStatement(Executable trueStatement, Executable falseStatement, Evaluable expression) {
        this.trueStatement = trueStatement;
        this.falseStatement = falseStatement;
        this.expression = expression;
    }

    @Override
    public void execute() {
        if (expression.eval() > 0) {
            trueStatement.execute();
        } else {
            falseStatement.execute();
        }
    }
}

class AssignmentStatement implements Executable {

    String identifier;
    Evaluable expression;
    Map<String, Integer> bindings;

    public AssignmentStatement(String identifier, Evaluable expression, Map<String, Integer> bindings) {
        this.identifier = identifier;
        this.expression = expression;
        this.bindings = bindings;
    }

    @Override
    public void execute() {
//        System.out.println(identifier + " = " + expression.eval());
        if(!identifier.equals("random"))
            bindings.put(identifier, expression.eval());
    }
}

class WhileStatement implements Executable {
    Executable statement;
    Evaluable expression;

    public WhileStatement(Executable statement, Evaluable expression) {
        this.statement = statement;
        this.expression = expression;
    }

    @Override
    public void execute() {
        int cnt = 0;
        while (expression.eval() > 0 && cnt < 1000) {
            statement.execute();
            cnt++;
        }
    }

}

class BlockStatement implements Executable {

    List<Executable> statements;

    public BlockStatement() {
        statements = new LinkedList<>();
    }

    public void addStatement(Executable statement) {
        statements.add(statement);
    }

    @Override
    public void execute() {
        for (Executable statement : statements)
            statement.execute();
    }
}

class Program extends BlockStatement {

    Map<String, Integer> bindings;

    public Program(Map<String, Integer> bindings) {
        this.bindings = bindings;
        statements = new LinkedList<>();
    }

    @Override
    public void execute() {
        bindings.put("isActionTaken" , 0);
        for (Executable statement : statements)
            statement.execute();
    }
}



class AttackCommand implements Executable {

    Unit unit;
    String direction;
    Map<String, Integer> bindings;

    public AttackCommand(Unit unit, String direction , Map<String, Integer> bindings) {
        this.unit = unit;
        this.direction = direction;
        this.bindings = bindings;
    }

    @Override
    public void execute() {
        if(bindings.get("isActionTaken") == 0) {
            unit.shoot(direction);
            bindings.put("isActionTaken" , 1);
        }
    }
}

class MoveCommand implements Executable {

    Unit unit;
    String direction;
    Map<String, Integer> bindings;

    public MoveCommand(Unit unit, String direction, Map<String, Integer> bindings) {
        this.unit = unit;
        this.direction = direction;
        this.bindings = bindings;
    }

    @Override
    public void execute() {
        if(bindings.get("isActionTaken") == 0) {
            unit.move(direction);
            bindings.put("isActionTaken" , 1);
        }
    }
}