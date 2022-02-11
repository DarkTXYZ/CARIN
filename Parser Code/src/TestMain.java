public class TestMain {
    public static void main(String[] args) {
        try {
            Tokenizer tkz = new Tokenizer("a = 5 while(a) {move up a = a - 1}");
            while(tkz.hasNext()) {
                System.out.println(tkz.consume());
            }
        } catch (SyntaxErrorException e) {
            System.out.println("[SyntaxError]: " + e.getMessage());
        }

    }
}
