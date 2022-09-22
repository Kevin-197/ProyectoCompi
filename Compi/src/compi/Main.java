/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compi;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Scanner;

/**
 *
 * @author kevin
 */
public class Main {
    public static void main(String[] args) throws IOException {
        String ruta = "D:/NetBeansProjects/Compi/src/compi/lexer.flex";
        generarLexer(ruta);
        probarLexer();
    }
    public static void generarLexer(String ruta){
        File archivo = new File(ruta);
        jflex.Main.generate(archivo);
    }
    
    public static void probarLexer() throws IOException{
//        File archivo = new File("archivo.txt");
//        PrintWriter escribir;
//        try {
//            escribir = new PrintWriter(archivo);
//            escribir.print(" as /*ads\nad*/ \n adad");
//            escribir.close();
//        } catch (FileNotFoundException ex) {
//            System.out.println("a");
//        }
        
        try {
            Reader lector = new BufferedReader(new FileReader("D:\\NetBeansProjects\\Compi\\src\\compi\\archivo.cpp"));
            Lexer lexer = new Lexer(lector);
            String resultado = "";
            while (true) {
                Tokens tokens = lexer.yylex();
                if (tokens == null) {
                    resultado += "FIN";
                    System.out.println(resultado);
                    return;
                }
                switch (tokens) {
                    case ERROR:
                        resultado += "Simbolo no definido: "+ lexer.lexeme +" en la linea "+lexer.GetLine()+"\n";
                        break;
                    default:
                        resultado += lexer.lexeme + ": Es un " + tokens + " en la linea "+lexer.GetLine()+"\n";
                        break;
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("b");
        } catch (IOException ex) {
            System.out.println("c");
        }

    }
}

