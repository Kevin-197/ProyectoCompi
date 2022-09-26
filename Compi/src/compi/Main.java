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
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 *
 * @author kevin
 */
public class Main {
    public static void main(String[] args) throws IOException {
        String ruta = "D:\\NetBeansProjects\\Compi\\src\\compi\\lexer.flex";
        generarLexer(ruta);
        probarLexer();
    }
    
    public static void generarLexer(String ruta){
        File archivo = new File(ruta);
        jflex.Main.generate(archivo);
    }
    
    public static void probarLexer() throws IOException{
        try {
            Reader lector = new BufferedReader(new FileReader("D:\\NetBeansProjects\\Compi\\src\\compi\\archivoAnalisis.c"));
            Lexer lexer = new Lexer(lector);
            LinkedHashMap<String,ArrayList<String>> token = new LinkedHashMap<String,ArrayList<String>>();
            LinkedHashMap<String,ArrayList<String>> error = new LinkedHashMap<String,ArrayList<String>>();
            int cont = 1;
            while (true) {
                Tokens tokens = lexer.yylex();
                if (tokens == null) {
                    crearArchivoError(error);
                    crearArchivoToken(token);
                    return;
                }
                switch (tokens) {
                    case ERROR:
                         if (error.containsKey(lexer.lexeme)){
                            error.get(lexer.lexeme).add(String.valueOf(lexer.GetLine()));
                        }
                        else{
                            error.put(lexer.lexeme, new ArrayList<String>());
                            error.get(lexer.lexeme).add(String.valueOf("Simbolo no definido"));
                            error.get(lexer.lexeme).add(String.valueOf(lexer.GetLine()));
                        }
                        break;
                    default:
                        if (token.containsKey(lexer.lexeme)){
                            token.get(lexer.lexeme).add(String.valueOf(lexer.GetLine()));
                        }
                        else{
                            token.put(lexer.lexeme, new ArrayList<String>());
                            token.get(lexer.lexeme).add(String.valueOf(tokens));
                            token.get(lexer.lexeme).add(String.valueOf(lexer.GetLine()));
                        }
                        break;
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("b");
        } catch (IOException ex) {
            System.out.println("c");
        }
    }
    
    public static void crearArchivoError(LinkedHashMap<String,ArrayList<String>> errores){
        String rutaErrores = "D:\\NetBeansProjects\\Compi\\src\\compi\\Errores.txt";
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
                LinkedHashMap<String, Integer> lineas = new LinkedHashMap<>();

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
    
    public static void crearArchivoToken(LinkedHashMap<String,ArrayList<String>> tokens){
        String rutaTokens = "D:\\NetBeansProjects\\Compi\\src\\compi\\Tokens.txt";
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
               LinkedHashMap<String, Integer> lineas = new LinkedHashMap<>();

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

