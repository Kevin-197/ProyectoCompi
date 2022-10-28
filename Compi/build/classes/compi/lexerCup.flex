package compi;
import java_cup.runtime.Symbol;
%%
%class LexerCup
%type java_cup.runtime.Symbol
%line
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

break {return new Symbol(sym.Break, yychar, yyline, yytext());}
case {return new Symbol(sym.Case, yychar, yyline, yytext());}
char {return new Symbol(sym.Char, yychar, yyline, yytext());}
const {return new Symbol(sym.Const, yychar, yyline, yytext());}
continue {return new Symbol(sym.Continue, yychar, yyline, yytext());}
default {return new Symbol(sym.Default, yychar, yyline, yytext());}
do {return new Symbol(sym.Do, yychar, yyline, yytext());}
else {return new Symbol(sym.Else, yychar, yyline, yytext());}
for {return new Symbol(sym.For, yychar, yyline, yytext());}
if {return new Symbol(sym.If, yychar, yyline, yytext());}
int {return new Symbol(sym.Int, yychar, yyline, yytext());}
long {return new Symbol(sym.Long, yychar, yyline, yytext());}
return {return new Symbol(sym.Return, yychar, yyline, yytext());}
short {return new Symbol(sym.Short, yychar, yyline, yytext());}
switch {return new Symbol(sym.Switch, yychar, yyline, yytext());}
void {return new Symbol(sym.Void, yychar, yyline, yytext());}
while {return new Symbol(sym.While, yychar, yyline, yytext());}

auto | double | enum | extern | float | goto | register | signed | sizeof | static | struct | typedef | union | unsigned |
volatile {return new Symbol(sym.Reservada, yychar, yyline, yytext());}

{espacio} {/*Ignore*/}

{multilinecommen} | {comment} {/*Ignore*/}

"\n" {return new Symbol(sym.Linea, yychar, yyline, yytext());}

"+" {return new Symbol(sym.OperadorSuma, yychar, yyline, yytext());}
"-" {return new Symbol(sym.OperadorResta, yychar, yyline, yytext());}
"*" {return new Symbol(sym.OperadorMulti, yychar, yyline, yytext());}
"/" {return new Symbol(sym.OperadorDiv, yychar, yyline, yytext());}
"%" {return new Symbol(sym.OperadorMod, yychar, yyline, yytext());}

"+=" {return new Symbol(sym.OperadorAsignacionSuma, yychar, yyline, yytext());}
"-=" {return new Symbol(sym.OperadorAsignacionResta, yychar, yyline, yytext());}
"*=" {return new Symbol(sym.OperadorAsignacionMul, yychar, yyline, yytext());}
"/=" {return new Symbol(sym.OperadorAsignacionDiv, yychar, yyline, yytext());}
"=" {return new Symbol(sym.OperadorAsignacion, yychar, yyline, yytext());}

"++" {return new Symbol(sym.OperadorIncremento, yychar, yyline, yytext());}
"--" {return new Symbol(sym.OperadorDecremento, yychar, yyline, yytext());}

"==" {return new Symbol(sym.OperadorComparacion, yychar, yyline, yytext());}
"<" {return new Symbol(sym.OperadorMenor, yychar, yyline, yytext());}
">" {return new Symbol(sym.OperadorMayor, yychar, yyline, yytext());}
">=" {return new Symbol(sym.OperadorMayorIgual, yychar, yyline, yytext());}
"<=" {return new Symbol(sym.OperadorMenorIgual, yychar, yyline, yytext());}
"!=" {return new Symbol(sym.OperadorDiferencia, yychar, yyline, yytext());}

"!" {return new Symbol(sym.OperadorNegacion, yychar, yyline, yytext());}
"&&" {return new Symbol(sym.OperadorAnd, yychar, yyline, yytext());}
"||" {return new Symbol(sym.OperadorOr, yychar, yyline, yytext());}

"<<" | ">>" | "~" | "&" | "|" | "^" | "%="  | "<<=" | ">>=" | "&=" | "|=" | "^=" | "->" {return new Symbol(sym.Operador, yychar, yyline, yytext());}

"(" {return new Symbol(sym.ParentesisA, yychar, yyline, yytext());}
")" {return new Symbol(sym.ParentesisC, yychar, yyline, yytext());}

"[" {return new Symbol(sym.ParentesisCuadradoA, yychar, yyline, yytext());}
"]" {return new Symbol(sym.ParentesisCuadradoC, yychar, yyline, yytext());}

"{" {return new Symbol(sym.LlaveA, yychar, yyline, yytext());}
"}" {return new Symbol(sym.LlaveC, yychar, yyline, yytext());}

"," {return new Symbol(sym.Coma, yychar, yyline, yytext());}

"main" {return new Symbol(sym.Main, yychar, yyline, yytext());}

";" {return new Symbol(sym.PuntoComa, yychar, yyline, yytext());}

"?" {return new Symbol(sym.Interrogacion, yychar, yyline, yytext());}

":" {return new Symbol(sym.DosPuntos, yychar, yyline, yytext());}

"." {return new Symbol(sym.Punto, yychar, yyline, yytext());}

"_" {return new Symbol(sym.ERROR, yychar, yyline, yytext());}

0({O})+ {return new Symbol(sym.Octal, yychar, yyline, yytext());}
0({O})+("u"|"U") {return new Symbol(sym.OctalU, yychar, yyline, yytext());}
0({O})+("l"|"L") {return new Symbol(sym.OctalL, yychar, yyline, yytext());}
0({O})+("ll"|"LL") {return new Symbol(sym.OctalLL, yychar, yyline, yytext());}
0({O})+("ul"|"UL") {return new Symbol(sym.OctalUL, yychar, yyline, yytext());}
0({O})+("ull"|"ULL") {return new Symbol(sym.OctalULL, yychar, yyline, yytext());}

0x({Hl}|{N})+ {return new Symbol(sym.Hexadecimal, yychar, yyline, yytext());}
0x({Hl}|{N})+("u"|"U") {return new Symbol(sym.HexadecimalU, yychar, yyline, yytext());}
0x({Hl}|{N})+("l"|"L") {return new Symbol(sym.HexadecimalL, yychar, yyline, yytext());}
0x({Hl}|{N})+("ll"|"LL") {return new Symbol(sym.HexadecimalLL, yychar, yyline, yytext());}
0x({Hl}|{N})+("ul"|"UL") {return new Symbol(sym.HexadecimalUL, yychar, yyline, yytext());}
0x({Hl}|{N})+("uLL"|"ULL") {return new Symbol(sym.HexadecimalULL, yychar, yyline, yytext());}

{N}+ {return new Symbol(sym.Int, yychar, yyline, yytext());}
{N}+("u"|"U") {return new Symbol(sym.IntU, yychar, yyline, yytext());}
{N}+("l"|"L") {return new Symbol(sym.IntL, yychar, yyline, yytext());}
{N}+("ll"|"LL") {return new Symbol(sym.IntLL, yychar, yyline, yytext());}
{N}+("ul"|"UL") {return new Symbol(sym.IntUL, yychar, yyline, yytext());}
{N}+("ull"|"ULL") {return new Symbol(sym.IntULL, yychar, yyline, yytext());}

{F}+ {return new Symbol(sym.Double, yychar, yyline, yytext());}
{F}+("f"|"F") {return new Symbol(sym.Float, yychar, yyline, yytext());}
{F}+("l"|"L") {return new Symbol(sym.DoubleL, yychar, yyline, yytext());}

(([0-9]*\.[0-9]+)|{N}+)("e"|"E")("-"{N}+|{N}+) {return new Symbol(sym.PuntoFlotante, yychar, yyline, yytext());}

{L}({L}|{N})* {return new Symbol(sym.Identificador, yychar, yyline, yytext());}

{char} {return new Symbol(sym.LiteralChar, yychar, yyline, yytext());}
{string} {return new Symbol(sym.LiteralString, yychar, yyline, yytext());}

 . {return new Symbol(sym.ERROR, yychar, yyline, yytext());}
