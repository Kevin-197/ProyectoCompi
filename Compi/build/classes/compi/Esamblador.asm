section .data

section .text
global _start
_start:


while_label0:
JNE exit_label0
JL else_label1
jmp exit_label1

else_label1:

exit_label1:
jmp while_label0

exit_label0:
JNE else_label2
jmp exit_label2

else_label2:

exit_label2:
