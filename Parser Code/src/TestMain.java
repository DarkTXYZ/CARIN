public class TestMain {
    public static void main(String[] args) {
        try {
            Tokenizer tkz = new Tokenizer("");
            while(tkz.hasNext())
                System.out.println(tkz.consume());

        } catch (TokenizeErrorException e) {
            System.out.println("[TokenizeError]: " + e.getMessage());
        }

    }
}
