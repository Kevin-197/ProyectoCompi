section .data
x       dd        0
c       dd        0
a       dd        0

section .text
global _start
_start:

mov rax, 0
mov [c], rax
mov rax, 0
mov [a], rax

while_label0:
mov rax, [c]
cmp rax, [a]

JNE exit_label0
mov rax, 48
mov [a], rax
mov rax, 4
mov [x], rax
mov rax, [x]
cmp rax, [a]

JNE else_label1
mov rax, 4
imul rax, [x]
mov rbx, rax
jmp exit_label1

else_label1:
mov rax, [x]
inc rax 
mov [x], rax

exit_label1:
jmp while_label0

exit_label0:
mov rax, 48
mov [a], rax
mov rax, 4
mov [x], rax
mov rax, [x]
cmp rax, [a]

JNE else_label2
mov rax, 4
imul rax, [x]
mov rbx, rax
jmp exit_label2

else_label2:
mov rax, [x]
inc rax 
mov [x], rax

exit_label2:
