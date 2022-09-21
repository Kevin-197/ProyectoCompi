package compi;

import static compi.Tokens.*;
%%
%class Lexer
%type Tokens

L=[a-zA-Z_]+
N=[0-9]+
F=[0-9]+\.[0-9]+
O=[0-7]+
Hl=[a-fA-F]+
espacio=[ ,\t,\r,\n]+
comment = \/\/.*
multilinecommen = \/\*[\s\S]*?\*\/
char = \'.*\'
string = \".*\"


%{
    public String lexeme;
%}
%%
auto | break | case | char | const | continue | default | do | double | else | enum | extern | float | 
for | goto | if | int | long | register | return | short | signed | sizeof | static | struct | switch | 
typedef | union | unsigned | void | volatile | while {lexeme=yytext(); return Reservada;}

{espacio} {/*Ignore*/}

{multilinecommen} | {comment} {/*Ignore*/}

"," | ";" | "++" | "--" | "==" | ">=" | ">" | "?" | "<=" | "<" | "!=" | "||" | "&&" | "!" | "=" | 
"+"| "-" | "*" | "/" | "%" | "(" | ")" | "[" | "]" | "{" | "}" | ":" | "." | "+=" | "-=" | "*=" | 
"/=" | "&" | "^" | "|" | ">>" | "<<" | "~" | "%=" | "&=" | "^=" | "|=" | "<<=" | ">>=" | "->" {lexeme=yytext(); return Operador;}

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

{char}|{string}+ {lexeme=yytext(); return Literal;}

 . {lexeme=yytext(); return ERROR;}
