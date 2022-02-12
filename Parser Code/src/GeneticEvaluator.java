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

    private static GeneticEvaluator instance;
    private GeneticEvaluator() {

    }

    public static GeneticEvaluator getInstance() {
        if(GeneticEvaluator.instance == null)
            GeneticEvaluator.instance = new GeneticEvaluator();
        return GeneticEvaluator.instance;
    }

    @Override
    public Executable evaluate(Unit unit) {
        return null;
    }
}
