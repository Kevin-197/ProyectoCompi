/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compi;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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
        String ruta = "C:\\Users\\jenar\\OneDrive\\Documentos\\Compi\\scanner\\ProyectoCompi\\Compi\\src\\compi\\lexer.flex";
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
        String error = "";
        String token = "";
        String rutaErrores = "C:\\Users\\jenar\\OneDrive\\Documentos\\Compi\\scanner\\ProyectoCompi\\Compi\\src\\compi\\Errores.txt";
            String rutaTokens = "C:\\Users\\jenar\\OneDrive\\Documentos\\Compi\\scanner\\ProyectoCompi\\Compi\\src\\compi\\Tokens.txt";
        try{
            File errorFile = new File(rutaErrores);
            File tokenFile = new File(rutaTokens);
            if (errorFile.createNewFile() && tokenFile.createNewFile()){
                System.out.println("Archivos creados: " + errorFile.getName() + " " + tokenFile.getName());
            }
            else{
                System.out.println("Los archivos ya existen.");
                
            }
        }
        catch (IOException e){
            System.out.println("Error al crear los archivos");
            e.printStackTrace();
        }
        try {
            Reader lector = new BufferedReader(new FileReader("C:\\Users\\jenar\\OneDrive\\Documentos\\Compi\\scanner\\ProyectoCompi\\Compi\\src\\compi\\archivo.cpp"));
            Lexer lexer = new Lexer(lector);
            FileWriter escritorErrores = new FileWriter(rutaErrores,false);
            FileWriter escritorTokens = new FileWriter(rutaTokens,false);
            String resultado = "";
            while (true) {
                Tokens tokens = lexer.yylex();
                if (tokens == null) {
                    escritorErrores.close();
                    escritorTokens.close();
                    resultado += "FIN";
                    System.out.println(resultado);
                    return;
                }
                switch (tokens) {
                    case ERROR:
                        error = "Simbolo no definido: "+ lexer.lexeme +" en la linea "+lexer.GetLine()+"\n";
                        resultado += error;
                        escritorErrores.write(error);
                        break;
                    default:
                        token = lexer.lexeme + ": Es un " + tokens + " en la linea "+lexer.GetLine()+"\n";
                        resultado += token ;
                        escritorTokens.write(token);
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

