/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compi;

import java.util.ArrayList;

/**
 *
 * @author kevin
 */
public class TablaSimbolos {
    private ArrayList<RegistroTS> Simbolos;
    private static TablaSimbolos instance;
    private int UltimaFunc;

    private TablaSimbolos() {
        this.Simbolos = new ArrayList<RegistroTS>();
        this.UltimaFunc = 0;
    }
    
    public static TablaSimbolos getInstance() {
        if (instance == null) {
            instance = new TablaSimbolos();
        }
        return instance;
    }
    
    public void InsertSimbolo(String identificador, String tipo, String ambito, int line, int column){
        if(BuscarSimbolo(identificador,ambito) != null){
            System.out.println("\u001B[31mError semantico. Linea: " + line + " Columna: " + column +  " Ya ha sido previamente declarado el identificador \""+identificador+"\"\u001B[31m");
            if("Funcion".equals(ambito)){
                this.DeleteFunc();
            }
        }
        else{
            RegistroTS registro = new RegistroTS(identificador, tipo, ambito);
            if("Funcion".equals(ambito)){
                this.UltimaFunc=Simbolos.size();
            }
            Simbolos.add(registro);
        }
        
    }

    private RegistroTS BuscarSimbolo(String identificador, String ambito) {
        String currentAmbito;
        RegistroTS current;
        int cont = 0;
        if("Parametro".equals(ambito)){
            cont = this.UltimaFunc;
        }
        for (int i = cont; i < Simbolos.size(); i++) {
            current = Simbolos.get(i);
            if(current.getIdentificador().compareTo(identificador) == 0){
                currentAmbito = current.getAmbito();
                if(currentAmbito.equals(ambito)){
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
        cont = this.UltimaFunc+1;
        for (int i = cont; i < Simbolos.size(); i++) {
            current = Simbolos.get(i);
            if(current.getAmbito().compareTo("Global") != 0){
                Simbolos.remove(i);
                --i;
            }
        }
    }
    
}
