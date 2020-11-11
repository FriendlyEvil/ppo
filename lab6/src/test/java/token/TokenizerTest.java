package token;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import token.state.StateProcessException;

import java.util.List;

import static token.BiOperationToken.*;
import static token.BracketToken.LEFT;
import static token.BracketToken.RIGHT;

public class TokenizerTest {
    private Tokenizer tokenizer;

    @Before
    public void before() {
        tokenizer = new Tokenizer();
    }

    private void testTokenize(String str, List<Token> expected) {
        List<Token> actual = tokenizer.tokenize(str);
        Assert.assertArrayEquals(actual.toArray(), expected.toArray());
    }

    @Test
    public void emptyTest() {
        testTokenize("", List.of());
    }

    @Test
    public void numberTest() {
        testTokenize("42", List.of(new NumberToken(42)));
    }

    @Test
    public void simpleOperationTest() {
        testTokenize("42 + 4 - 1098", List.of(new NumberToken(42), ADD, new NumberToken(4), SUB, new NumberToken(1098)));
    }

    @Test
    public void bracketTest() {
        testTokenize("(42 * 4) / 1098", List.of(LEFT, new NumberToken(42), MUL, new NumberToken(4), RIGHT, DIV, new NumberToken(1098)));
    }

    @Test(expected = StateProcessException.class)
    public void errorTest() {
        testTokenize("abacaba", List.of());
    }

}