package token;

public enum BiOperationToken implements Token {
    ADD(0),
    SUB(0),
    MUL(1),
    DIV(1);

    private final int priority;

    BiOperationToken(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
