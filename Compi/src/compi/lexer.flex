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
"/=" | "&" | "^" | "|" | ">>" | "<<" | "~" | "%=" | "&=" | "^=" | "|=" | "<<=" | ">>=" | "->" {return Operador;}

"_" {return ERROR;}

{L}({L}|{N})* {lexeme=yytext(); return Identificador;}

0({O})+ {lexeme=yytext(); return Octal;}
0x({Hl}|{N})+ {lexeme=yytext(); return Hexadecimal;}

{char}|{string}|{F}|{N}+ {lexeme=yytext(); return Literal;}

 . {return ERROR;}
