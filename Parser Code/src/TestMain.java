public class TestMain {
    public static void main(String[] args) throws SyntaxErrorException, TokenizeErrorException {
        GeneticEvaluator g = GeneticEvaluator.getInstance();
        VirusDummy v = new VirusDummy("a=5 while(a) {move up a=a-1}");
        VirusDummy w = new VirusDummy("b=8 while(b-5) {move down move left b = b-1}");
        v.setProgram(g.evaluate(v));
        w.setProgram(g.evaluate(w));

        v.execute();
        w.execute();
    }
}
