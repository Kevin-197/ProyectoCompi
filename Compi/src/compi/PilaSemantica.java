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
public class PilaSemantica {
    public ArrayList<RS> Simbolos;
    private static PilaSemantica instance; 
    private TablaSimbolos tabla = TablaSimbolos.getInstance();
    private PilaSemantica() {
        this.Simbolos = new ArrayList<RS>();
    }
    
    public static PilaSemantica getInstance() {
        if (instance == null) {
            instance = new PilaSemantica();
        }
        return instance;
    }

    public void Push(RS registro){
        Simbolos.add(registro);
    }
    
    public RS Pop(){
        RS pop = Simbolos.get(Simbolos.size()-1);
        Simbolos.remove(Simbolos.size()-1);
        return pop;
    }
    
    public int GuardarVariable(String Ambito){
        RS_Tipo tipo = null;
        int cont = 0;
        for (int i = this.Simbolos.size()-1; i >= 0; i--) {
            if("RS_Tipo".equals(Simbolos.get(i).nombre())){
                tipo = (RS_Tipo) Simbolos.get(i);
                cont = i;
                break;
            }
        }
        for (int i = this.Simbolos.size()-1; i > cont; i--) {
            RS_Var id = (RS_Var) this.Pop();
            this.tabla.InsertSimbolo(id.Id,tipo.Tipo,Ambito,id.Line,id.Column);

        }

        this.Pop();
        
        return 1;
    }
}
