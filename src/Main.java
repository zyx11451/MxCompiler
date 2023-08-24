
import AST.ASTBuilder;
import AST.DefNodes.RootNode;
import IR.IRBuilder;
import IR.IRDefs.IRRoot;
import Parser.MxLexer;
import Parser.MxParser;
import SemanticChecker.SemanticChecker;
import SemanticChecker.SymbolCollector;
import Utils.GlobalScope;
import Utils.MxErrorListener;
import Utils.error.error;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileInputStream;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws Exception{
        InputStream input=System.in;
        String name = "test.mx";
        //String name = System.in.toString();
        //InputStream input = new FileInputStream(name);
        try {
            GlobalScope gScope = new GlobalScope(null);
            MxLexer lexer = new MxLexer(CharStreams.fromStream(input));
            lexer.removeErrorListeners();
            lexer.addErrorListener(new MxErrorListener());
            MxParser parser=new MxParser(new CommonTokenStream(lexer));
            parser.removeErrorListeners();
            parser.addErrorListener(new MxErrorListener());
            ParseTree parseTreeRoot = parser.program();
            ASTBuilder astBuilder=new ASTBuilder(gScope);
            RootNode ASTRoot=(RootNode) astBuilder.visit(parseTreeRoot);
            new SymbolCollector(gScope).visit(ASTRoot);
            new SemanticChecker(gScope).visit(ASTRoot);
            IRRoot irRoot=new IRRoot();
            new IRBuilder(irRoot,gScope).visit(ASTRoot);
            System.out.print(irRoot.toString());
        }catch (error er) {
            System.err.println(er.toString());
            throw new RuntimeException();
        }

    }
}
//todo  目前的问题：构造函数的特判。