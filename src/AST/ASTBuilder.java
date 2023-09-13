package AST;

import AST.DefNodes.*;
import AST.ExprNodes.*;
import AST.StmtNodes.*;
import Parser.MxBaseVisitor;
import Parser.MxParser;
import Utils.GlobalScope;
import Utils.Type;
import Utils.position;

public class ASTBuilder extends MxBaseVisitor<ASTNode> {
    private GlobalScope globalScope;

    public ASTBuilder(GlobalScope globalScope) {
        this.globalScope = globalScope;
    }

    ;
    public Type intType;
    public Type boolType;
    public Type stringType;

    public ASTNode visitProgram(MxParser.ProgramContext ctx) {
        RootNode root = new RootNode(new position(ctx));
        ctx.defination().forEach(t -> root.defs.add((DefNode) visit(t)));
        return root;
    }

    public ASTNode visitClassDef(MxParser.ClassDefContext ctx) {
        ClassDefNode classDef = new ClassDefNode(ctx.Identifier().toString(), new position(ctx));
        ctx.defination().forEach(t -> classDef.elements.add((DefNode) visit(t)));
        return classDef;
    }

    public ASTNode visitVarDef(MxParser.VarDefContext ctx) {
        VarDefNode varDef = new VarDefNode(new position(ctx));
        Type ty = new Type(ctx.type());
        for (int i = 0; i < ctx.Identifier().size(); ++i) {
            ExprNode expr = null;
            if (ctx.expression(i) != null) expr = (ExprNode) visit(ctx.expression(i));
            varDef.add(ctx.Identifier(i).toString(), ty, expr);
        }
        return varDef;
    }

    public ASTNode visitMainFn(MxParser.MainFnContext ctx) {
        FuncDefNode func = new FuncDefNode(new Type(ctx.Int().toString()), ctx.Main().toString(), new position(ctx));
        ctx.statement().forEach(t -> func.statements.add((StmtNode) visit(t)));
        return func;
    }

    public ASTNode visitConstructFn(MxParser.ConstructFnContext ctx) {
        FuncDefNode func = new FuncDefNode(null, ctx.Identifier().toString(), new position(ctx));
        ctx.statement().forEach(t -> func.statements.add((StmtNode) visit(t)));
        func.isConstructor=true;
        return func;
    }

    public ASTNode visitOtherFn(MxParser.OtherFnContext ctx) {
        FuncDefNode func = new FuncDefNode(new Type(ctx.type()), ctx.Identifier().toString(), new position(ctx));
        ctx.statement().forEach(t -> func.statements.add((StmtNode) visit(t)));
        if(ctx.parameterList()!=null){
            for (int i = 0; i < ctx.parameterList().Identifier().size(); ++i) {
                ExprNode expr = null;
                if (ctx.parameterList().expression(i) != null) expr = (ExprNode) visit(ctx.parameterList().expression(i));
                Type ty = new Type(ctx.parameterList().type(i));
                func.addParameter(ty, ctx.parameterList().Identifier(i).toString(), expr);
            }
        }

        return func;
    }

    public ASTNode visitSuite(MxParser.SuiteContext ctx) {
        BlockStmtNode blk = new BlockStmtNode(new position(ctx));
        ctx.statement().forEach(t -> blk.stmts.add((StmtNode) visit(t)));
        return blk;
    }

    public ASTNode visitDefStmt(MxParser.DefStmtContext ctx) {
        DefNode def = (DefNode) visit(ctx.defination());
        return new DefStmtNode(def, new position(ctx));
    }

    public ASTNode visitBreakStmt(MxParser.BreakStmtContext ctx) {
        return new BreakStmtNode(new position(ctx));
    }

    public ASTNode visitContinueStmt(MxParser.ContinueStmtContext ctx) {
        return new ContinueStmtNode(new position(ctx));
    }

    public ASTNode visitEmptyStmt(MxParser.EmptyStmtContext ctx) {
        return new EmptyStmtNode(new position(ctx));
    }

    public ASTNode visitForStmt(MxParser.ForStmtContext ctx) {
        StmtNode init = (StmtNode) visit(ctx.init);
        ExprNode condition=null;
        if(ctx.condition!=null)  condition = (ExprNode) visit(ctx.condition);
        ExprNode step=null;
        if(ctx.step!=null) step = (ExprNode) visit(ctx.step);
        StmtNode thenStmt = (StmtNode) visit(ctx.thenStmt);
        return new ForStmtNode(init, condition, step, thenStmt, new position(ctx));
    }

    public ASTNode visitIfStmt(MxParser.IfStmtContext ctx) {
        ExprNode condition = (ExprNode) visit(ctx.condition);
        StmtNode trueStmt = (StmtNode) visit(ctx.trueStmt);
        StmtNode falseStmt = null;
        if (ctx.falseStmt != null) falseStmt = (StmtNode) visit(ctx.falseStmt);
        return new IfStmtNode(condition, trueStmt, falseStmt, new position(ctx));
    }

    public ASTNode visitPureExprStmt(MxParser.PureExprStmtContext ctx) {
        ExprNode expression = (ExprNode) visit(ctx.expression());
        return new PureExprStmtNode(expression, new position(ctx));
    }

    public ASTNode visitReturnStmt(MxParser.ReturnStmtContext ctx) {
        ExprNode returnExpr = null;
        if (ctx.expression() != null) returnExpr = (ExprNode) visit(ctx.expression());
        return new ReturnStmtNode(returnExpr, new position(ctx));
    }

    public ASTNode visitWhileStmt(MxParser.WhileStmtContext ctx) {
        ExprNode condition = (ExprNode) visit(ctx.expression());
        StmtNode thenStmt = (StmtNode) visit(ctx.statement());
        return new WhileStmtNode(condition, thenStmt, new position(ctx));
    }

    public ASTNode visitAtomExpr(MxParser.AtomExprContext ctx) {
        if (ctx.primary().expression() != null) {
            return visit(ctx.primary().expression());
        } else if (ctx.primary().True() != null) {
            return new BoolLiteralNode(true, new position(ctx));
        } else if (ctx.primary().False() != null) {
            return new BoolLiteralNode(false, new position(ctx));
        } else if (ctx.primary().Identifier() != null) {
            return new VarExprNode(ctx.primary().Identifier().toString(), new position(ctx));
        } else if (ctx.primary().This() != null) {
            return new ThisNode(new position(ctx));
        } else if (ctx.primary().Null() != null) {
            return new NullNode(new position(ctx));
        } else if (ctx.primary().literal() != null) {
            return visit(ctx.primary().literal());
        }
        return null;
    }

    public ASTNode visitLiteral(MxParser.LiteralContext ctx) {
        if (ctx.DecimalInteger() != null) {
            return new IntLiteralNode(Long.parseLong(ctx.DecimalInteger().toString()), new position(ctx));
        } else if (ctx.StringLiteral() != null) {
            String str = ctx.StringLiteral().toString();
            str = str.substring(1, str.length() - 1);
            return new StringLiteralNode(str, new position(ctx));
        }
        return null;
    }

    public ASTNode visitSuffixExpr(MxParser.SuffixExprContext ctx) {
        ExprNode expression = (ExprNode) visit(ctx.expression());
        SuffixExprNode.suffixOpType op;
        if (ctx.PlusPlus() != null) {
            op = SuffixExprNode.suffixOpType.addadd;
        } else if (ctx.MinusMinus() != null) {
            op = SuffixExprNode.suffixOpType.subsub;
        } else {
            op = null;
        }
        return new SuffixExprNode(expression, op, new position(ctx));
    }

    public ASTNode visitArrayElementExpr(MxParser.ArrayElementExprContext ctx) {
        ExprNode array = (ExprNode) visit(ctx.array);
        ExprNode index = (ExprNode) visit(ctx.index);
        return new ArrayElementExprNode(array, index, new position(ctx));
    }

    public ASTNode visitFunctionCallExpr(MxParser.FunctionCallExprContext ctx) {
        FunctionCallExprNode funcCall = new FunctionCallExprNode(ctx.Identifier().toString(), new position(ctx));
        ctx.expression().forEach(t -> funcCall.parameters.add((ExprNode) visit(t)));
        return funcCall;
    }

    public ASTNode visitMemberExpr(MxParser.MemberExprContext ctx) {
        ExprNode cls = (ExprNode) visit(ctx.expression());
        return new MemberExprNode(cls, ctx.Identifier().toString(), new position(ctx));
    }

    public ASTNode visitPrefixExpr(MxParser.PrefixExprContext ctx) {
        ExprNode expression = (ExprNode) visit(ctx.expression());
        PrefixExprNode.prefixOpType op;
        if (ctx.PlusPlus() != null) {
            op = PrefixExprNode.prefixOpType.addadd;
        } else if (ctx.MinusMinus() != null) {
            op = PrefixExprNode.prefixOpType.subsub;
        } else {
            op = null;
        }
        return new PrefixExprNode(expression, op, new position(ctx));
    }

    public ASTNode visitUnaryExpr(MxParser.UnaryExprContext ctx) {
        ExprNode expression = (ExprNode) visit(ctx.expression());
        UnaryExprNode.unaryOpType op;
        if (ctx.Minus() != null) {
            op = UnaryExprNode.unaryOpType.minus;
        } else if (ctx.Plus() != null) {
            op = UnaryExprNode.unaryOpType.plus;
        } else if (ctx.Not() != null) {
            op = UnaryExprNode.unaryOpType.not;
        } else if (ctx.Tilde() != null) {
            op = UnaryExprNode.unaryOpType.tilde;
        } else {
            op = null;
        }
        return new UnaryExprNode(expression, op, new position(ctx));
    }

    public ASTNode visitErrorArrayDefExpr(MxParser.ErrorArrayDefExprContext ctx) {
        return new ErrorArrayExprNode(new position(ctx));
    }

    public ASTNode visitNewArrayDefExpr(MxParser.NewArrayDefExprContext ctx) {
        Type ty = new Type(ctx.type());
        int dimension = 0;
        dimension =  ctx.LeftBracket().size();
        ty.dimension = dimension;
        NewArrayExprNode newArray = new NewArrayExprNode(new position(ctx));
        newArray.type = ty;
        ctx.expression().forEach(t -> newArray.bounds.add((ExprNode) visit(t)));
        return newArray;
    }

    public ASTNode visitNewExpr(MxParser.NewExprContext ctx) {
        NewExprNode newExpr = new NewExprNode(new position(ctx));
        newExpr.type = new Type(ctx.Identifier().toString());
        return newExpr;
    }

    public ASTNode visitBinaryExpr(MxParser.BinaryExprContext ctx) {
        ExprNode lhs = (ExprNode) visit(ctx.expression(0));
        ExprNode rhs = (ExprNode) visit(ctx.expression(1));
        BinaryExprNode.binaryOpType op;
        if (ctx.And() != null) {
            op = BinaryExprNode.binaryOpType.and;
        } else if (ctx.Or() != null) {
            op = BinaryExprNode.binaryOpType.or;
        } else if (ctx.Caret() != null) {
            op = BinaryExprNode.binaryOpType.caret;
        } else if (ctx.DivideBy() != null) {
            op = BinaryExprNode.binaryOpType.divide;
        } else if (ctx.ModBy() != null) {
            op = BinaryExprNode.binaryOpType.mod;
        } else if (ctx.Multipy() != null) {
            op = BinaryExprNode.binaryOpType.multiply;
        } else if (ctx.Plus() != null) {
            op = BinaryExprNode.binaryOpType.plus;
        } else if (ctx.Minus() != null) {
            op = BinaryExprNode.binaryOpType.minus;
        } else if (ctx.Greater() != null) {
            op = BinaryExprNode.binaryOpType.greater;
        } else if (ctx.GreaterEqual() != null) {
            op = BinaryExprNode.binaryOpType.greaterEqual;
        } else if (ctx.Less() != null) {
            op = BinaryExprNode.binaryOpType.less;
        } else if (ctx.LessEqual() != null) {
            op = BinaryExprNode.binaryOpType.lessEqual;
        } else if (ctx.Equal() != null) {
            op = BinaryExprNode.binaryOpType.equal;
        } else if (ctx.NotEqual() != null) {
            op = BinaryExprNode.binaryOpType.notEqual;
        } else if (ctx.AndAnd() != null) {
            op = BinaryExprNode.binaryOpType.andand;
        } else if (ctx.OrOr() != null) {
            op = BinaryExprNode.binaryOpType.oror;
        } else if (ctx.LeftShift() != null) {
            op = BinaryExprNode.binaryOpType.leftShift;
        } else if (ctx.RightShift() != null) {
            op = BinaryExprNode.binaryOpType.rightShift;
        } else {
            op = null;
        }
        return new BinaryExprNode(lhs, rhs, op, new position(ctx));
    }

    public ASTNode visitAssignExpr(MxParser.AssignExprContext ctx) {
        ExprNode lhs = (ExprNode) visit(ctx.expression(0));
        ExprNode rhs = (ExprNode) visit(ctx.expression(1));
        return new AssignExprNode(lhs, rhs, new position(ctx));
    }

    public ASTNode visitTernaryExpr(MxParser.TernaryExprContext ctx) {
        ExprNode judgeExpr = (ExprNode) visit(ctx.expression(0));
        ExprNode trueExpr = (ExprNode) visit(ctx.expression(1));
        ExprNode falseExpr = (ExprNode) visit(ctx.expression(2));
        return new TernaryExprNode(judgeExpr, trueExpr, falseExpr, new position(ctx));
    }
    public ASTNode visitClassMethodCallExpr(MxParser.ClassMethodCallExprContext ctx){
        ExprNode classExpr = (ExprNode) visit(ctx.className);
        String methodName;
        if(ctx.Main()!=null) methodName="main";
        else methodName=ctx.Identifier().toString();
        ClassMethodCallExprNode methodCall = new ClassMethodCallExprNode(classExpr,methodName, new position(ctx));
        for(int i=1;i<ctx.expression().size();++i){
            methodCall.parameters.add((ExprNode) visit(ctx.expression(i)));
        }
        return methodCall;
    }
}
