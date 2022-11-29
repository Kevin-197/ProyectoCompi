section .data
x       dd        0
x       dd        0
a       dd        0
asdasd       dd        0
c       db        0
t       dq        0
c       dw        0

section .text
global _start
_start:


while_label0:
mov rax, [a]
comp rax, [a]

JNE exit_label0
mov rax, 0
mov [a], rax
mov rax, [x]
comp rax, [x]

JL else_label1
mov rax, 4
imul rax, [ttt]
mov rbx, rax
jmp exit_label1

else_label1:

exit_label1:
jmp while_label0

exit_label0:
mov rax, [x]
comp rax, [x]

JNE else_label2
jmp exit_label2

else_label2:

exit_label2:
