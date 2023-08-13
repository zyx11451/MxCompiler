package Utils;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

public class position {
    private int row;
    private int column;

    public position(int r,int c){
        this.row=r;
        this.column=c;
    }
    public position(Token token){
        this.row=token.getLine();
        this.column=token.getCharPositionInLine();
    }
    public position(ParserRuleContext ctx){
        this(ctx.getStart());
    }

    public int row(){
        return row;
    }

    public int column(){
        return column;
    }

    public String toString(){
        return row + "," + column;
    }
}
