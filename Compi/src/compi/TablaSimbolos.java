/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compi;

import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author kevin
 */
public class TablaSimbolos {
    public ArrayList<RegistroTS> Simbolos;
    private static TablaSimbolos instance;
    private boolean ErrorFunc;
    private Stack<Integer> UltimaFunc;
    private int TerminaFunc;


    private TablaSimbolos() {
        this.Simbolos = new ArrayList<RegistroTS>();
        this.UltimaFunc = new Stack<Integer>();
        this.UltimaFunc.push(0);
        this.TerminaFunc = 0;
    }
    
    public static TablaSimbolos getInstance() {
        if (instance == null) {
            instance = new TablaSimbolos();
        }
        return instance;
    }
    
    public void TerminaFuncion(){
        if(this.ErrorFunc){
            this.DeleteFunc();
        }
        this.TerminaFunc = this.Simbolos.size();
    }
    
    public void InsertSimbolo(String identificador, String tipo, String ambito, int line, int column){
        if(BuscarSimbolo(identificador,ambito) != null){
            System.out.println("\u001B[31mError semantico. Linea: " + line + " Columna: " + column +  " Ya ha sido previamente declarado el identificador \""+identificador+"\"\u001B[31m");
            if("Funcion".equals(ambito)){
                this.ErrorFunc = true;
            }
            if("Parametro".equals(ambito)){
                this.ErrorFunc = true;
                
            }
        }
        else{
            RegistroTS registro = new RegistroTS(identificador, tipo, ambito);
            
            if("Funcion".equals(ambito)){
                this.ErrorFunc = false;
                this.UltimaFunc.push(Simbolos.size());
            }
            Simbolos.add(registro);
            
        }
        
    }

    private RegistroTS BuscarSimbolo(String identificador, String ambito) {
        String currentAmbito;
        RegistroTS current;
        int cont = 0;
        if("Parametro".equals(ambito) || "Local".equals(ambito)){
            cont = this.TerminaFunc;
        }
        for (int i = cont; i < Simbolos.size(); i++) {
            current = Simbolos.get(i);
            if(current.getIdentificador().compareTo(identificador) == 0){
                currentAmbito = current.getAmbito();
                if(currentAmbito.equals(ambito)){
                    return current;
                }
                else if(null != ambito) switch (ambito) {
                    case "Funcion":
                        if("Global".equals(currentAmbito)){
                            return current;
                        }
                        break;
                    case "Global":
                        if("Funcion".equals(currentAmbito)){
                            return current;
                        }
                        break;
                    case "Local":
                        if("Parametro".equals(currentAmbito)){
                            return current;
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        return null;
    }
    
    public RegistroTS BuscarSimboloDeclarado(String identificador, String ambito) {
        String currentAmbito;
        RegistroTS current;
        int cont = this.TerminaFunc;
        
        for (int i = cont; i < Simbolos.size(); i++) {
            current = Simbolos.get(i);
            if(current.getIdentificador().compareTo(identificador) == 0){
                currentAmbito = current.getAmbito();
                if(currentAmbito.equals(ambito)){
                    return current;
                }
                else if(null != ambito) switch (ambito) {
                    case "Local":
                        if("Parametro".equals(currentAmbito)){
                            return current;
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        
        for (int i = 0; i < Simbolos.size(); i++) {
            current = Simbolos.get(i);
            if(current.getIdentificador().compareTo(identificador) == 0){
                currentAmbito = current.getAmbito();
                if(currentAmbito.equals("Global") && ambito.equals("Local")){
                    return current;
                }
                if(currentAmbito.equals("Funcion") && ambito.equals("Funcion")){
                    return current;
                }
            }
        }
        return null;
    }
    
    public void printTabla(){
        System.out.println("\nId  |   Tipo    |   Ambito");
        for (int i = 0; i < Simbolos.size(); i++) {
            RegistroTS current = Simbolos.get(i);
            System.out.println("\n"+current.getIdentificador()+" | "+current.getTipo()+" | "+current.getAmbito());
        }
        
    }

    private void DeleteFunc() {
        RegistroTS current;
        int cont = 0;
        cont = this.TerminaFunc;
        for (int i = cont; i < Simbolos.size(); i++) {
            current = Simbolos.get(i);
            if(current.getAmbito().compareTo("Global") != 0){
                Simbolos.remove(i);
                --i;
            }
        }
    }
    
}
