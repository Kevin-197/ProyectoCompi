package compi;

import static compi.Tokens.*;
%%
%class Lexer
%type Tokens
%line
%{
    public int GetLine() { return yyline + 1; }
    // ...

%}

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
    public String lexeme;
%}
%%
break {lexeme=yytext(); return Break;}
case {lexeme=yytext(); return Case;}
char {lexeme=yytext(); return Char;}
const {lexeme=yytext(); return Const;}
continue {lexeme=yytext(); return Continue;}
default {lexeme=yytext(); return Default;}
do {lexeme=yytext(); return Do;}
else {lexeme=yytext(); return Else;}
for {lexeme=yytext(); return For;}
if {lexeme=yytext(); return If;}
int {lexeme=yytext(); return Int;}
long {lexeme=yytext(); return Long;}
return {lexeme=yytext(); return Return;}
short {lexeme=yytext(); return Short;}
switch {lexeme=yytext(); return Switch;}
void {lexeme=yytext(); return Void;}
while {lexeme=yytext(); return While;}

auto | double | enum | extern | float | goto | register | signed | sizeof | static | struct | typedef | union | unsigned |
volatile {lexeme=yytext(); return Reservada;}

{espacio} {/*Ignore*/}

{multilinecommen} | {comment} {/*Ignore*/}

"\n" {lexeme=yytext(); return Linea;}

"+" | "-" | "*" | "/" | "%" {lexeme=yytext(); return OperadorAritmetico;}

"+=" | "-=" | "*=" | "/=" | "%=" | "=" | "<<=" | ">>=" | "&=" | "|=" | "^=" {lexeme=yytext(); return OperadorAsignacion;}

"++" | "--" {lexeme=yytext(); return OperadorIncremento;}

"==" | "<" | ">" | ">=" | "<=" | "!=" {lexeme=yytext(); return OperadorComparacion;}

"!" | "&&" | "||" {lexeme=yytext(); return OperadorLogico;}

"<<" | ">>" | "~" | "&" | "|" | "^" {lexeme=yytext(); return OperadorDeBit;}

"(" {lexeme=yytext(); return ParentesisA;}
")" {lexeme=yytext(); return ParentesisC;}

"[" {lexeme=yytext(); return ParentesisCuadradoA;}
"]" {lexeme=yytext(); return ParentesisCuadradoC;}

"{" {lexeme=yytext(); return LlaveA;}
"}" {lexeme=yytext(); return LlaveC;}

"->" {lexeme=yytext(); return Flecha;}

"," {lexeme=yytext(); return Coma;}

"main" {lexeme=yytext(); return Main;}

";" {lexeme=yytext(); return PuntoComa;}

"?" {lexeme=yytext(); return Interrogacion;}

":" {lexeme=yytext(); return DosPuntos;}

"." {lexeme=yytext(); return Punto;}

"_" {lexeme=yytext(); return ERROR;}

0({O})+ {lexeme=yytext(); return Octal;}
0({O})+("u"|"U") {lexeme=yytext(); return OctalU;}
0({O})+("l"|"L") {lexeme=yytext(); return OctalL;}
0({O})+("ll"|"LL") {lexeme=yytext(); return OctalLL;}
0({O})+("ul"|"UL") {lexeme=yytext(); return OctalUL;}
0({O})+("ull"|"ULL") {lexeme=yytext(); return OctalULL;}

0x({Hl}|{N})+ {lexeme=yytext(); return Hexadecimal;}
0x({Hl}|{N})+("u"|"U") {lexeme=yytext(); return HexadecimalU;}
0x({Hl}|{N})+("l"|"L") {lexeme=yytext(); return HexadecimalL;}
0x({Hl}|{N})+("ll"|"LL") {lexeme=yytext(); return HexadecimalLL;}
0x({Hl}|{N})+("ul"|"UL") {lexeme=yytext(); return HexadecimalUL;}
0x({Hl}|{N})+("uLL"|"ULL") {lexeme=yytext(); return HexadecimalULL;}

{N}+ {lexeme=yytext(); return Int;}
{N}+("u"|"U") {lexeme=yytext(); return IntU;}
{N}+("l"|"L") {lexeme=yytext(); return IntL;}
{N}+("ll"|"LL") {lexeme=yytext(); return IntLL;}
{N}+("ul"|"UL") {lexeme=yytext(); return IntUL;}
{N}+("ull"|"ULL") {lexeme=yytext(); return IntULL;}

{F}+ {lexeme=yytext(); return Double;}
{F}+("f"|"F") {lexeme=yytext(); return Float;}
{F}+("l"|"L") {lexeme=yytext(); return DoubleL;}

(([0-9]*\.[0-9]+)|{N}+)("e"|"E")("-"{N}+|{N}+) {lexeme=yytext(); return PuntoFlotante;}

{L}({L}|{N})* {lexeme=yytext(); return Identificador;}

{char}|{string} {lexeme=yytext(); return Literal;}

 . {lexeme=yytext(); return ERROR;}
