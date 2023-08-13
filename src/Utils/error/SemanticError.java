package Utils.error;

import Utils.position;

public class SemanticError extends error{
    public SemanticError(String msg, position pos) {
        super("Semantic Error: " + msg, pos);
    }
}
