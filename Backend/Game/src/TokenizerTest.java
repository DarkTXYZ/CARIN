import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class TokenizerTest {

    Tokenizer tkz = Tokenizer.getInstance();

    String[] unknownCharacter = {
            "a = 5$",
            "b = 9 while(b) {move up b = b-1} // this is comment ^-^!!",
            "if(a == 9) move left; else a += 1;",
            "12345WE_LOVE_CPE67890"
    };

    @Test
    public void testUnknownCharacter() throws TokenizeErrorException {
        for (String program : unknownCharacter) {
            tkz.initialize(program);
            assertThrows(TokenizeErrorException.class,
                    () -> {
                        while (tkz.hasNext())
                            tkz.consume();
                    },
                    "Expected Errors, but didn't thrown");
        }

    }
}