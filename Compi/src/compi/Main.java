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
import java.util.ArrayList;
import java.util.HashMap;
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
        try {
            Reader lector = new BufferedReader(new FileReader("C:\\Users\\jenar\\OneDrive\\Documentos\\Compi\\scanner\\ProyectoCompi\\Compi\\src\\compi\\archivo.cpp"));
            Lexer lexer = new Lexer(lector);
            HashMap<String,ArrayList<String>> token = new HashMap<String,ArrayList<String>>();
            HashMap<String,ArrayList<String>> error = new HashMap<String,ArrayList<String>>();
            String resultado = "";
            int cont = 1;
            while (true) {
                Tokens tokens = lexer.yylex();
                if (tokens == null) {
                    resultado += "FIN";
                    System.out.println(resultado);
                    System.out.println(token);
                    System.out.println(error);
                    crearArchivoError(error);
                    crearArchivoToken(token);
                    return;
                }
                switch (tokens) {
                    case ERROR:
                        resultado += "Simbolo no definido: "+ lexer.lexeme +" en la linea "+lexer.GetLine()+"\n";
                         if (error.containsKey(lexer.lexeme)){
                            error.get(lexer.lexeme).add(String.valueOf(lexer.GetLine()));
                        }
                        else{
                            error.put(lexer.lexeme, new ArrayList<String>());
                            error.get(lexer.lexeme).add(String.valueOf("Simbolo no definido"));
                            error.get(lexer.lexeme).add(String.valueOf(lexer.GetLine()));
                         }
                        //escritorErrores.write(lexer.lexeme +"    |      "+"Simbolo no definido"+"    |      "+lexer.GetLine()+"\n");
                        break;
                    default:
                        resultado += lexer.lexeme + ": Es un " + tokens + " en la linea "+lexer.GetLine()+"\n";
                        if (token.containsKey(lexer.lexeme)){
                            token.get(lexer.lexeme).add(String.valueOf(lexer.GetLine()));
                        }
                        else{
                            token.put(lexer.lexeme, new ArrayList<String>());
                            token.get(lexer.lexeme).add(String.valueOf(tokens));
                            token.get(lexer.lexeme).add(String.valueOf(lexer.GetLine()));
                        }
                        //escritorTokens.write(lexer.lexeme + "    |      " + tokens + "    |      "+lexer.GetLine()+"\n");
                        break;
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("b");
        } catch (IOException ex) {
            System.out.println("c");
        }

    }
    
    public static void crearArchivoError(HashMap<String,ArrayList<String>> errores){
        String rutaErrores = "C:\\Users\\jenar\\OneDrive\\Documentos\\Compi\\scanner\\ProyectoCompi\\Compi\\src\\compi\\Errores.txt";
        try{
            File errorFile = new File(rutaErrores);
            if (errorFile.createNewFile()){
                System.out.println("Archivo creado: " + errorFile.getName());
            }
            else{
                System.out.println("El archivo de errores ya existe.");
                
            }
        }
        catch (IOException e){
            System.out.println("Error al crear los archivos");
            e.printStackTrace();
        }
        try{
            FileWriter escritorErrores = new FileWriter(rutaErrores,false);
            escritorErrores.write("Token    |      Tipo de Error    |     Linea" + "\n");
            for (String error:errores.keySet()){
                ArrayList<String> valor = errores.get(error);
                escritorErrores.write(error + "    |      ");
                escritorErrores.write(valor.get(0) + "    |      ");
                HashMap<String, Integer> lineas = new HashMap<>();

                for(int i = 1; i<valor.size(); i++) {
                    String numLinea = valor.get(i);
                    if(lineas.containsKey(numLinea)) {
                        lineas.put(numLinea, lineas.get(numLinea)+1);
                    } else {
                        lineas.put(numLinea, 1);
                    }
                    
                }
                for (String linea:lineas.keySet()){
                    if (lineas.get(linea) > 1)
                        escritorErrores.write(linea+"(" + lineas.get(linea) + ")" + " ");
                    else
                        escritorErrores.write(linea + " ");
                }
                escritorErrores.write("\n");
            }
            
            
            
            escritorErrores.close();
            
        }
        catch (IOException e){
            System.out.println("Error al escribir los archivos");
            e.printStackTrace();
        }
    }
    
    public static void crearArchivoToken(HashMap<String,ArrayList<String>> tokens){
        String rutaTokens = "C:\\Users\\jenar\\OneDrive\\Documentos\\Compi\\scanner\\ProyectoCompi\\Compi\\src\\compi\\Tokens.txt";
        try{
            File tokenFile = new File(rutaTokens);
            if (tokenFile.createNewFile()){
                System.out.println("Archivo creado: " + tokenFile.getName());
            }
            else{
                System.out.println("El archivo tokens ya existe.");
                
            }
        }
        catch (IOException e){
            System.out.println("Error al crear los archivos");
            e.printStackTrace();
        }
        try{
            FileWriter escritorTokens = new FileWriter(rutaTokens,false);
            escritorTokens.write("Token    |      Tipo de Token    |      Linea" + "\n");
            for (String token:tokens.keySet()){
                ArrayList<String> valor = tokens.get(token);
                escritorTokens.write(token + "    |      ");
                escritorTokens.write(valor.get(0) + "    |      ");
               HashMap<String, Integer> lineas = new HashMap<>();

                for(int i = 1; i<valor.size(); i++) {
                    String numLinea = valor.get(i);
                    if(lineas.containsKey(numLinea)) {
                        lineas.put(numLinea, lineas.get(numLinea)+1);
                    } else {
                        lineas.put(numLinea, 1);
                    }
                    
                }
                for (String linea:lineas.keySet()){
                    if (lineas.get(linea) > 1)
                        escritorTokens.write(linea +"(" + lineas.get(linea) + ")" + " ");
                    else
                        escritorTokens.write(linea + " ");
                }
               escritorTokens.write("\n");
            }
            
            escritorTokens.close();
            
        }
        catch (IOException e){
            System.out.println("Error al escribir los archivos");
            e.printStackTrace();
        }
    }
    
    
}

