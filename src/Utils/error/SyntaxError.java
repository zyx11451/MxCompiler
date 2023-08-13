package Utils.error;

import Utils.position;

public class SyntaxError extends error{
    public SyntaxError(String msg, position pos) {
        super("SyntaxError: " + msg, pos);
    }
}
