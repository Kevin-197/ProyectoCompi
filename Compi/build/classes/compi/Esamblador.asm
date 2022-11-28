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

jg else_label0
jmp exit_label0

else_label0:

exit_label0:
tt       dq        0

while_label1:
jmp exit_label1
jg else_label2
jmp exit_label2

else_label2:

exit_label2:
jmp while_label1

exit_label1:
ttt       dq        0
quepasa       dd        0
