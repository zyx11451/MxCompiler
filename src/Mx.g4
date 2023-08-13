grammar Mx;

program: defination*  EOF;


varDef
    : type (varname=Identifier ('=' expression)?',')*  varname=Identifier ('=' expression)?';'
    ;
classDef : Class classname=Identifier '{' (defination)* '}'';'?;
funcDef: type funcname=Identifier '('parameterList?')''{' (statement)* '}' ';'?#otherFn
       | funcname=Identifier'('')''{' (statement)* '}' ';'?#constructFn
       | Int Main '('')' '{' (statement)* '}' ';'?#mainFn
       ;
suite : '{' statement* '}';

statement
    : suite                                                 #block
    | defination                                            #defStmt
    | If '(' condition=expression ')' trueStmt=statement
        (Else falseStmt=statement)?                         #ifStmt
    | Return expression? ';'                                #returnStmt
    | Continue ';'                                          #continueStmt
    | Break ';'                                             #breakStmt
    | For '(' init=statement? condition=expression? ';' step=expression? ')' thenStmt=statement   #forStmt
    | While '(' expression ')' statement                    #whileStmt
    | expression ';'                                        #pureExprStmt
    | ';'                                                   #emptyStmt
    ;

expression
    : primary                                                             #atomExpr
    | expression op=('--'|'++')                                           #suffixExpr
    | array=expression '[' index=expression ']'                           #arrayElementExpr
    | Identifier '('(expression ',')* expression?')'                      #functionCallExpr
    | expression '.' Identifier                                           #memberExpr
    | className=expression '.' Identifier '('(expression ',')* expression?')'       #classMethodCallExpr
    | <assoc=right> op=('--'|'++') expression                             #prefixExpr
    | <assoc=right> op=('-'|'+') expression                               #unaryExpr
    | <assoc=right> op=('!'|'~') expression                               #unaryExpr
    | New type ('[' expression ']')* ('['']')+ ('[' expression ']')+        #errorArrayDefExpr
    | New type ('[' expression ']')+ ('['']')*                              #newArrayDefExpr
    | New Identifier ('('')')?                                            #newExpr
    | expression op=('*'|'/'|'%')  expression                             #binaryExpr
    | expression op=('+' | '-') expression                                #binaryExpr
    | expression op=('<<'|'>>') expression                                #binaryExpr
    | expression op=( '<' |'>'|'>='|'<=' ) expression                     #binaryExpr
    | expression op=('==' | '!=' ) expression                             #binaryExpr
    | expression op='&' expression                                        #binaryExpr
    | expression op='^' expression                                        #binaryExpr
    | expression op='|' expression                                        #binaryExpr
    | expression op=('&&'|'||') expression                                #binaryExpr
    | <assoc=right> expression '=' expression                             #assignExpr
    | <assoc=right> expression '?' expression ':' expression              #ternaryExpr
    ;


primary
    : '(' expression ')'
    | Identifier
    | This
    | True
    | False
    | Null
    | literal
    ;

literal
    : DecimalInteger
    | StringLiteral
    ;

type : Int
     | Bool
     | String
     | Void
     | Identifier
     | (Int| Bool| String | Identifier) (('[' ']'))+
     ;

defination
      :funcDef
      |varDef
      |classDef
      ;

parameterList
      : (type Identifier ('='expression)? ',')* (type Identifier ('='expression)?);


Int : 'int';
If : 'if';
Else : 'else';
Return : 'return';
For:'for';
Continue: 'continue';
Break:'break';
While:'while';
New:'new';
This:'this';
Class:'class';
True:'true';
False:'false';
Null:'null';
Bool:'bool';
String: 'string';
Void: 'void';
Main: 'main';

LeftParen : '(';
RightParen : ')';
LeftBracket : '[';
RightBracket : ']';
LeftBrace : '{';
RightBrace : '}';

Less : '<';
LessEqual : '<=';
Greater : '>';
GreaterEqual : '>=';
LeftShift : '<<';
RightShift : '>>';

PlusPlus: '++';
MinusMinus: '--';
Plus : '+';
Minus : '-';
Multipy: '*';
DivideBy: '/';
ModBy: '%';

And : '&';
Or : '|';
AndAnd : '&&';
OrOr : '||';
Caret : '^';
Not : '!';
Tilde : '~';

Question : '?';
Colon : ':';
Semi : ';';
Comma : ',';
Dot : '.';

Assign : '=';
Equal : '==';
NotEqual : '!=';

Identifier
    : [a-zA-Z] [a-zA-Z_0-9]*
    ;

DecimalInteger
    : [1-9] [0-9]*
    | '0'
    ;

StringLiteral
    : '"' (('\\n'|'\\\\'|'\\"'|.)*?) '"'
    ;

Whitespace
    :   [ \t\n]+
        -> skip
    ;

Newline
    :   (   '\r' '\n'?
        |   '\n'
        )
        -> skip
    ;

BlockComment
    :   '/*' .*? '*/'
        -> skip
    ;

LineComment
    :   '//' ~[\r\n]*
        -> skip
    ;