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
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java_cup.runtime.Symbol;

/**
 *
 * @author kevin
 */
public class Main {

    public static void main(String[] args) throws Exception {
        String ruta1 = "D:/RepoVR/ProyectoCompi/Compi/src/compi/Lexer.flex";
        String ruta2 = "D:/RepoVR/ProyectoCompi/Compi/src/compi/LexerCup.flex";
        String[] rutaS = {"-parser", "Sintax", "D:/RepoVR/ProyectoCompi/Compi/src/compi/Sintax.cup"};
        generar(ruta1, ruta2, rutaS);
        AnalisisLexico();
        AnalisisSintactico();
    }
    
    public static void generar(String ruta1, String ruta2, String[] rutaS) throws IOException, Exception{
        File archivo;
        archivo = new File(ruta1);
        jflex.Main.generate(archivo);
        archivo = new File(ruta2);
        jflex.Main.generate(archivo);
        java_cup.Main.main(rutaS);
        
        Path rutaSym = Paths.get("D:/RepoVR/ProyectoCompi/Compi/src/compi/sym.java");
        if (Files.exists(rutaSym)) {
            Files.delete(rutaSym);
        }
        Files.move(
                Paths.get("D:/RepoVR/ProyectoCompi/Compi/sym.java"), 
                Paths.get("D:/RepoVR/ProyectoCompi/Compi/src/compi/sym.java")
        );
        Path rutaSin = Paths.get("D:/RepoVR/ProyectoCompi/Compi/src/compi/Sintax.java");
        if (Files.exists(rutaSin)) {
            Files.delete(rutaSin);
        }
        Files.move(
                Paths.get("D:/RepoVR/ProyectoCompi/Compi/Sintax.java"), 
                Paths.get("D:/RepoVR/ProyectoCompi/Compi/src/compi/Sintax.java")
        );
    }
    public static void AnalisisLexico() throws IOException{
        try {
            Reader lector = new BufferedReader(new FileReader("D:/RepoVR/ProyectoCompi/Compi/src/compi/archivoAnalisis.c"));
            Lexer lexer = new Lexer(lector);
            LinkedHashMap<String,ArrayList<String>> error = new LinkedHashMap<String,ArrayList<String>>();
            int cont = 1;
            while (true) {
                Tokens tokens = lexer.yylex();
                if (tokens == null) {
                    crearArchivoError(error);
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
                }
            }
            
        } catch (FileNotFoundException ex) {
            System.out.println("b");
        } catch (IOException ex) {
            System.out.println("c");
        }
    }
    
    public static void crearArchivoError(LinkedHashMap<String,ArrayList<String>> errores){
        String rutaErrores = "D:/RepoVR/ProyectoCompi/Compi/src/compi/Errores.txt";
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
    
    public static void AnalisisSintactico() throws IOException{
        File archivo = new File("D:/RepoVR/ProyectoCompi/Compi/src/compi/archivoAnalisis.c");
        String archivoAnalisis = new String(Files.readAllBytes(archivo.toPath()));
        Sintax s = new Sintax(new LexerCup(new StringReader(archivoAnalisis)));
        System.out.println("\n\n-----------------------------------\n\n");
        try {
            s.parse();
            ArrayList<Symbol> sym = s.getS();
            if(sym.isEmpty()){
                System.out.println("\u001B[32m"+"Analisis sintactico realizado correctamente"+"\u001B[32m");
            }
            else{
                for (int i = 0; i < s.getS().size(); i++) {
                    if(sym.get(i).value==null){
                        System.out.println("\u001B[31m"+"Error de sintaxis. Una de las funciones no tiene llave de cierre"+"\u001B[31m");
                    }
                        System.out.println("\u001B[31m"+"Error de sintaxis. Linea: " + (sym.get(i).right + 1) + " Columna: " + (sym.get(i).left + 1) + ", Caracter del error: \"" + sym.get(i).value + "\"\n"+"\u001B[31m");
                    
                }

            }
            
            TablaSimbolos.getInstance().printTabla();
            System.out.println(""+PilaSemantica.getInstance().Simbolos.size());

        } catch (Exception ex) {
            ArrayList<Symbol> sym = s.getS();
            for (int i = 0; i < s.getS().size(); i++) {
                if(sym.get(i).value==null){
                    System.out.println("\u001B[31m"+"Error de sintaxis. Una de las funciones no tiene llave de cierre"+"\u001B[31m");
                }
                System.out.println("\u001B[31m"+"Error de sintaxis. Linea: " + (sym.get(i).right + 1) + " Columna: " + (sym.get(i).left + 1) + ", Caracter del error: \"" + sym.get(i).value + "\"\n"+"\u001B[31m");
            }
        }
    }
    
}

