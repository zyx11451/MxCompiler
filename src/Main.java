
import ASM.ASMModule;
import ASM.InstSelector;
import ASM.RegAllocator;
import ASM.InsertSpOffsetInst;
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

import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception{
        boolean online=true;
        InputStream input;
        String name = "test.mx";
        if(online) input=System.in;
        else{
            input = new FileInputStream(name);
        }
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
            ASMModule asmModule=new ASMModule();
            new InstSelector(asmModule).visit(irRoot);
            if(online) new RegAllocator().visit(asmModule);
            //new RegAllocator().visit(asmModule);
            new InsertSpOffsetInst().visit(asmModule);
            if(online) {
                System.out.print(asmModule.toString());
                //System.out.print(irRoot.toString());
            }
            else{
                new PrintStream(new FileOutputStream("test.ll")).print(irRoot.toString());
                new PrintStream(new FileOutputStream("test.s")).print(asmModule.toString());
            }

        }catch (error er) {
            System.err.println(er.toString());
            throw new RuntimeException();
        }

    }
}