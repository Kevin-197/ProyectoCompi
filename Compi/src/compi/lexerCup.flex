package compi;
import java_cup.runtime.Symbol;
%%
%class LexerCup
%type java_cup.runtime.Symbol
%cup
%full
%line
%column
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
        return new Symbol(type, yyline, yycolumn+1, value);
    }
    private Symbol symbol(int type){
        return new Symbol(type, yyline+1, yycolumn+1);
    }
%}
%%

( "#"define ) {return new Symbol(sym.Define, yycolumn, yyline, yytext());}
( write ) {return new Symbol(sym.Write, yycolumn, yyline, yytext());}
( read ) {return new Symbol(sym.Read, yycolumn, yyline, yytext());}
( break ) {return new Symbol(sym.Break, yycolumn, yyline, yytext());}
( case ) {return new Symbol(sym.Case, yycolumn, yyline, yytext());}
( char ) {return new Symbol(sym.Char, yycolumn, yyline, yytext());}
( const ) {return new Symbol(sym.Const, yycolumn, yyline, yytext());}
( continue ) {return new Symbol(sym.Continue, yycolumn, yyline, yytext());}
( default ) {return new Symbol(sym.Default, yycolumn, yyline, yytext());}
( do ) {return new Symbol(sym.Do, yycolumn, yyline, yytext());}
( else ) {return new Symbol(sym.Else, yycolumn, yyline, yytext());}
( for ) {return new Symbol(sym.For, yycolumn, yyline, yytext());}
( if ) {return new Symbol(sym.If, yycolumn, yyline, yytext());}
( int ) {return new Symbol(sym.Integer, yycolumn, yyline, yytext());}
( long ) {return new Symbol(sym.Long, yycolumn, yyline, yytext());}
( return ) {return new Symbol(sym.Return, yycolumn, yyline, yytext());}
( short ) {return new Symbol(sym.Short, yycolumn, yyline, yytext());}
( switch ) {return new Symbol(sym.Switch, yycolumn, yyline, yytext());}
( void ) {return new Symbol(sym.Void, yycolumn, yyline, yytext());}
( while ) {return new Symbol(sym.While, yycolumn, yyline, yytext());}

{espacio} {/*Ignore*/}

{multilinecommen} | {comment} {/*Ignore*/}

( "+" ) {return new Symbol(sym.OperadorSuma, yycolumn, yyline, yytext());}
( "-" ) {return new Symbol(sym.OperadorResta, yycolumn, yyline, yytext());}
( "*" ) {return new Symbol(sym.OperadorMulti, yycolumn, yyline, yytext());}
( "/" ) {return new Symbol(sym.OperadorDiv, yycolumn, yyline, yytext());}
( "%" ) {return new Symbol(sym.OperadorMod, yycolumn, yyline, yytext());}

( "+=" ) {return new Symbol(sym.OperadorAsignacionSuma, yycolumn, yyline, yytext());}
( "-=" ) {return new Symbol(sym.OperadorAsignacionResta, yycolumn, yyline, yytext());}
( "*=" ) {return new Symbol(sym.OperadorAsignacionMul, yycolumn, yyline, yytext());}
( "/=" ) {return new Symbol(sym.OperadorAsignacionDiv, yycolumn, yyline, yytext());}
( "=" ) {return new Symbol(sym.OperadorAsignacion, yycolumn, yyline, yytext());}

( "++" ) {return new Symbol(sym.OperadorIncremento, yycolumn, yyline, yytext());}
( "--" ) {return new Symbol(sym.OperadorDecremento, yycolumn, yyline, yytext());}

( "==" ) {return new Symbol(sym.OperadorComparacion, yycolumn, yyline, yytext());}
( "<" ) {return new Symbol(sym.OperadorMenor, yycolumn, yyline, yytext());}
( ">" ) {return new Symbol(sym.OperadorMayor, yycolumn, yyline, yytext());}
( ">=" ) {return new Symbol(sym.OperadorMayorIgual, yycolumn, yyline, yytext());}
( "<=" ) {return new Symbol(sym.OperadorMenorIgual, yycolumn, yyline, yytext());}
( "!=" ) {return new Symbol(sym.OperadorDiferencia, yycolumn, yyline, yytext());}

( "!" ) {return new Symbol(sym.OperadorNegacion, yycolumn, yyline, yytext());}
( "&&" ) {return new Symbol(sym.OperadorAnd, yycolumn, yyline, yytext());}
( "||" ) {return new Symbol(sym.OperadorOr, yycolumn, yyline, yytext());}

( "(" ) {return new Symbol(sym.ParentesisA, yycolumn, yyline, yytext());}
( ")" ) {return new Symbol(sym.ParentesisC, yycolumn, yyline, yytext());}

( "{" ) {return new Symbol(sym.LlaveA, yycolumn, yyline, yytext());}
( "}" ) {return new Symbol(sym.LlaveC, yycolumn, yyline, yytext());}

( "," ) {return new Symbol(sym.Coma, yycolumn, yyline, yytext());}

( ";" ) {return new Symbol(sym.PuntoComa, yycolumn, yyline, yytext());}

( ":" ) {return new Symbol(sym.DosPuntos, yycolumn, yyline, yytext());}

{N}+ {return new Symbol(sym.Int, yycolumn, yyline, yytext());}

{L}({L}|{N})* {return new Symbol(sym.Identificador, yycolumn, yyline, yytext());}

{char} {return new Symbol(sym.LiteralChar, yycolumn, yyline, yytext());}

 . {return new Symbol(sym.ERROR, yycolumn, yyline, yytext());}
