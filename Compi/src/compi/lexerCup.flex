package compi;
import java_cup.runtime.Symbol;
%%
%class LexerCup
%type java_cup.runtime.Symbol
%cup
%full
%line
%char

L=[a-zA-Z_]+
N=[0-9]+
F=[0-9]+\.[0-9]+
O=[0-7]+
Hl=[a-fA-F]+
espacio=[ \t\r\n]+
comment = \/\/.*
multilinecommen = \/\*[\s\S]*?\*\/
char = \'([^\\\']|\\.)*\'
string = \"([^\\\"]|\\.)*\" 


%{
    private Symbol symbol(int type, Object value){
        return new Symbol(type, yyline, yycolumn, value);
    }
    private Symbol symbol(int type){
        return new Symbol(type, yyline, yycolumn);
    }
%}
%%

( "#"define ) {return new Symbol(sym.Define, yychar, yyline, yytext());}
( write ) {return new Symbol(sym.Write, yychar, yyline, yytext());}
( read ) {return new Symbol(sym.Read, yychar, yyline, yytext());}
( break ) {return new Symbol(sym.Break, yychar, yyline, yytext());}
( case ) {return new Symbol(sym.Case, yychar, yyline, yytext());}
( char ) {return new Symbol(sym.Char, yychar, yyline, yytext());}
( const ) {return new Symbol(sym.Const, yychar, yyline, yytext());}
( continue ) {return new Symbol(sym.Continue, yychar, yyline, yytext());}
( default ) {return new Symbol(sym.Default, yychar, yyline, yytext());}
( do ) {return new Symbol(sym.Do, yychar, yyline, yytext());}
( else ) {return new Symbol(sym.Else, yychar, yyline, yytext());}
( for ) {return new Symbol(sym.For, yychar, yyline, yytext());}
( if ) {return new Symbol(sym.If, yychar, yyline, yytext());}
( int ) {return new Symbol(sym.Integer, yychar, yyline, yytext());}
( long ) {return new Symbol(sym.Long, yychar, yyline, yytext());}
( return ) {return new Symbol(sym.Return, yychar, yyline, yytext());}
( short ) {return new Symbol(sym.Short, yychar, yyline, yytext());}
( switch ) {return new Symbol(sym.Switch, yychar, yyline, yytext());}
( void ) {return new Symbol(sym.Void, yychar, yyline, yytext());}
( while ) {return new Symbol(sym.While, yychar, yyline, yytext());}

{espacio} {/*Ignore*/}

{multilinecommen} | {comment} {/*Ignore*/}

( "+" ) {return new Symbol(sym.OperadorSuma, yychar, yyline, yytext());}
( "-" ) {return new Symbol(sym.OperadorResta, yychar, yyline, yytext());}
( "*" ) {return new Symbol(sym.OperadorMulti, yychar, yyline, yytext());}
( "/" ) {return new Symbol(sym.OperadorDiv, yychar, yyline, yytext());}
( "%" ) {return new Symbol(sym.OperadorMod, yychar, yyline, yytext());}

( "+=" ) {return new Symbol(sym.OperadorAsignacionSuma, yychar, yyline, yytext());}
( "-=" ) {return new Symbol(sym.OperadorAsignacionResta, yychar, yyline, yytext());}
( "*=" ) {return new Symbol(sym.OperadorAsignacionMul, yychar, yyline, yytext());}
( "/=" ) {return new Symbol(sym.OperadorAsignacionDiv, yychar, yyline, yytext());}
( "=" ) {return new Symbol(sym.OperadorAsignacion, yychar, yyline, yytext());}

( "++" ) {return new Symbol(sym.OperadorIncremento, yychar, yyline, yytext());}
( "--" ) {return new Symbol(sym.OperadorDecremento, yychar, yyline, yytext());}

( "==" ) {return new Symbol(sym.OperadorComparacion, yychar, yyline, yytext());}
( "<" ) {return new Symbol(sym.OperadorMenor, yychar, yyline, yytext());}
( ">" ) {return new Symbol(sym.OperadorMayor, yychar, yyline, yytext());}
( ">=" ) {return new Symbol(sym.OperadorMayorIgual, yychar, yyline, yytext());}
( "<=" ) {return new Symbol(sym.OperadorMenorIgual, yychar, yyline, yytext());}
( "!=" ) {return new Symbol(sym.OperadorDiferencia, yychar, yyline, yytext());}

( "!" ) {return new Symbol(sym.OperadorNegacion, yychar, yyline, yytext());}
( "&&" ) {return new Symbol(sym.OperadorAnd, yychar, yyline, yytext());}
( "||" ) {return new Symbol(sym.OperadorOr, yychar, yyline, yytext());}

( "(" ) {return new Symbol(sym.ParentesisA, yychar, yyline, yytext());}
( ")" ) {return new Symbol(sym.ParentesisC, yychar, yyline, yytext());}

( "{" ) {return new Symbol(sym.LlaveA, yychar, yyline, yytext());}
( "}" ) {return new Symbol(sym.LlaveC, yychar, yyline, yytext());}

( "," ) {return new Symbol(sym.Coma, yychar, yyline, yytext());}

( ";" ) {return new Symbol(sym.PuntoComa, yychar, yyline, yytext());}

( ":" ) {return new Symbol(sym.DosPuntos, yychar, yyline, yytext());}

{N}+ {return new Symbol(sym.Int, yychar, yyline, yytext());}

{L}({L}|{N})* {return new Symbol(sym.Identificador, yychar, yyline, yytext());}

{char} {return new Symbol(sym.LiteralChar, yychar, yyline, yytext());}

 . {return new Symbol(sym.ERROR, yychar, yyline, yytext());}
