package token;

import visitor.TokenVisitor;

public interface Token {
    default void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}
