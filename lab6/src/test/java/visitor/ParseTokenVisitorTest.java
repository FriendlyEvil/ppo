package visitor;

import org.junit.Assert;
import org.junit.Test;
import token.NumberToken;
import token.Token;

import java.util.List;

import static token.BiOperationToken.*;
import static token.BracketToken.LEFT;

public class ParseTokenVisitorTest {
    private final ParseTokenVisitor parseTokenVisitor = new ParseTokenVisitor();

    private void testParse(List<Token> inputTokens, List<Token> expected) {
        parseTokenVisitor.visit(inputTokens);
        List<Token> actual = parseTokenVisitor.getResult();
        Assert.assertArrayEquals(actual.toArray(), expected.toArray());
    }

    @Test
    public void emptyTest() {
        testParse(List.of(), List.of());
    }

    @Test
    public void simpleTest() {
        testParse(List.of(new NumberToken(42), ADD, new NumberToken(4), SUB, new NumberToken(1098)),
                List.of(new NumberToken(42), new NumberToken(4), new NumberToken(1098), SUB, ADD));
    }

    @Test
    public void priorityTest() {
        testParse(List.of(new NumberToken(42), MUL, new NumberToken(4), SUB, new NumberToken(1098)),
                List.of(new NumberToken(42), new NumberToken(4), MUL, new NumberToken(1098), SUB));
    }

    @Test(expected = VisitException.class)
    public void errorTest() {
        testParse(List.of(LEFT, new NumberToken(4)), List.of());
    }

}