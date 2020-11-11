package token.state;

import token.Token;
import token.Tokenizer;

import static token.BiOperationToken.*;
import static token.BracketToken.LEFT;
import static token.BracketToken.RIGHT;

public class MainState extends State {

    public MainState(Tokenizer tokenizer) {
        super(tokenizer);
    }

    @Override
    public void process(char ch) {
        Token token = getToken(ch);
        if (token != null) {
            tokenizer.addToken(token);
            return;
        }
        if (Character.isWhitespace(ch)) {
            return;
        }
        if (Character.isDigit(ch)) {
            tokenizer.setState(new NumberState(tokenizer));
            tokenizer.getState().process(ch);
            return;
        }
        throw new StateProcessException("Unexpected character " + ch);
    }

    private Token getToken(char ch) {
        switch (ch) {
            case '+':
                return ADD;
            case '-':
                return SUB;
            case '*':
                return MUL;
            case '/':
                return DIV;
            case '(':
                return LEFT;
            case ')':
                return RIGHT;
            default:
                return null;
        }
    }
}
