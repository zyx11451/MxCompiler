package AST;

import AST.DefNodes.ClassDefNode;
import AST.DefNodes.FuncDefNode;
import AST.DefNodes.RootNode;
import AST.DefNodes.VarDefNode;
import AST.ExprNodes.*;
import AST.StmtNodes.*;

public interface ASTVisitor {
    void visit(ClassDefNode it);

    void visit(FuncDefNode it);

    void visit(VarDefNode it);

    void visit(ArrayElementExprNode it);

    void visit(AssignExprNode it);

    void visit(BinaryExprNode it);

    void visit(ErrorArrayExprNode it);

    void visit(FunctionCallExprNode it);

    void visit(MemberExprNode it);

    void visit(NewArrayExprNode it);

    void visit(NewExprNode it);

    void visit(PrefixExprNode it);

    void visit(SuffixExprNode it);

    void visit(TernaryExprNode it);

    void visit(UnaryExprNode it);

    void visit(BlockStmtNode it);

    void visit(BreakStmtNode it);

    void visit(ContinueStmtNode it);

    void visit(DefStmtNode it);

    void visit(EmptyStmtNode it);

    void visit(ForStmtNode it);

    void visit(IfStmtNode it);

    void visit(PureExprStmtNode it);

    void visit(ReturnStmtNode it);

    void visit(WhileStmtNode it);

    void visit(RootNode it);

    void visit(VarExprNode it);

    void visit(StringLiteralNode it);

    void visit(IntLiteralNode it);

    void visit(BoolLiteralNode it);

    void visit(ThisNode it);

    void visit(NullNode it);
    void visit(ClassMethodCallExprNode it);
}