package visitor;

import org.junit.Assert;
import org.junit.Test;
import token.NumberToken;
import token.Token;

import java.util.List;

import static token.BiOperationToken.*;
import static token.BiOperationToken.SUB;
import static token.BracketToken.LEFT;

public class CalculateTokenVisitorTest {
    private final CalculateTokenVisitor calculateTokenVisitor = new CalculateTokenVisitor();

    private void testCalculate(List<Token> inputTokens, int excepted) {
        calculateTokenVisitor.visit(inputTokens);
        Assert.assertEquals(calculateTokenVisitor.getResult(), excepted);
    }

    @Test(expected = VisitException.class)
    public void emptyTest() {
        testCalculate(List.of(), 0);
    }

    @Test
    public void numberTest() {
        testCalculate(List.of(new NumberToken(42)), 42);
    }

    @Test
    public void simpleTest() {
        testCalculate(List.of(new NumberToken(42), new NumberToken(4), new NumberToken(1098), SUB, ADD), 1136);
    }

    @Test
    public void priorityTest() {
        testCalculate(List.of(new NumberToken(42), new NumberToken(4), MUL, new NumberToken(1098), SUB), 930);
    }

    @Test(expected = VisitException.class)
    public void errorTest() {
        testCalculate(List.of(LEFT, new NumberToken(4)), 0);
    }
}