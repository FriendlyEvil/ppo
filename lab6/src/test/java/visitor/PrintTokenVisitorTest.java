package visitor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import token.NumberToken;
import token.Token;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import static token.BiOperationToken.*;
import static token.BiOperationToken.DIV;
import static token.BracketToken.LEFT;
import static token.BracketToken.RIGHT;


public class PrintTokenVisitorTest {
    private StringWriter stringWriter;
    private PrintTokenVisitor printTokenVisitor;

    @Before
    public void before() {
        stringWriter = new StringWriter();
        printTokenVisitor = new PrintTokenVisitor(new PrintWriter(stringWriter));
    }

    private void testPrint(List<Token> inputTokens, String excepted) {
        printTokenVisitor.visit(inputTokens);
        Assert.assertEquals(stringWriter.toString().strip(), excepted.strip());
    }

    @Test
    public void emptyTest() {
        testPrint(List.of(), "");
    }

    @Test
    public void numberTest() {
        testPrint(List.of(new NumberToken(42)), "42");
    }

    @Test
    public void simpleTest() {
        testPrint(List.of(new NumberToken(42), ADD, new NumberToken(4), SUB, new NumberToken(1098)), "42 ADD 4 SUB 1098");
    }

    @Test
    public void testWithBracket() {
        testPrint(List.of(LEFT, new NumberToken(42), MUL, new NumberToken(4), RIGHT, DIV, new NumberToken(1098)), "LEFT 42 MUL 4 RIGHT DIV 1098");
    }
}