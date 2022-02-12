public class TestMain {
    public static void main(String[] args) {
        GeneticEvaluator g = GeneticEvaluator.getInstance();
        System.out.println(g);
        g = GeneticEvaluator.getInstance();
        System.out.println(g);
    }
}
