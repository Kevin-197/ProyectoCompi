void main(){
    int input, stop;
    stop = 1;
    while ((input = getchar()) != EOF) {
        stop 1 =;
        if (input == '\t' ) {
            putchar('\\');
            putchar('t');
            stop = ;
        }
        if (input == ' ') {
            putchar('\\');
            putchar('b')
            stop = 0;
        }
        if (input == '\\') {
            putchar('\\');
            putchar('\\';
            stop = 0;
        }
        else {
            if (stop == 1) {
                printf('%c', input);
            }
        }
 
    }
}