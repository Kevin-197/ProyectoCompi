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
    
    public int Top(){
        if(Simbolos.size()>0){
            return Simbolos.size();
        }
        else{
            return 0;
        }
    }
    
    public void ValidarLlamadaFunc(String Id,int Line, int Column){
        RegistroTS a = this.tabla.BuscarSimboloDeclarado(Id, "Funcion");
        if(a==null){
            System.out.println("\u001B[31mError semantico. Linea: " + Line + " Columna: " + Column +  " No ha sido declarado el identificador \""+Id+"\"\u001B[31m");
        }
        else{
            int funcion = this.tabla.Simbolos.indexOf(a);
            RegistroTS current;
            RegistroTS currentParam;
            RS_Param param;
            int cont = this.getFirstParam();
            for (int i = cont; i <= this.Top(); i++) {
                current = this.tabla.Simbolos.get(++funcion);
                if("Parametro".equals(current.getAmbito()) && (i!=-1 && i!= this.Simbolos.size()) && "RS_Param".equals(Simbolos.get(i).nombre())){
                    param = (RS_Param) this.Simbolos.get(i);
                    currentParam = this.tabla.BuscarSimboloDeclarado(param.Id, "Local");
                    if(currentParam==null){
                        System.out.println("\u001B[31mError semantico. Linea: " + param.Line + " Columna: " + param.Colum +  " \""+param.Id+"\" es un parametro invalido\u001B[31m");
                    }
                    else{
                        if(currentParam.getTipo().equals(current.getTipo())){
                        }
                        else{
                            System.out.println("\u001B[31mError semantico. Linea: " + param.Line + " Columna: " + param.Colum +  " \""+param.Id+"\" es un parametro invalido\u001B[31m");
                        }
                    }
                }
                else{
                    if(!"Parametro".equals(current.getAmbito()) && (i!= this.Simbolos.size()) && "RS_Param".equals(Simbolos.get(i).nombre())){
                        System.out.println("\u001B[31mError semantico. Linea: " + Line + " Columna: " + Column +  " Cantidad de parametros invalida \u001B[31m");
                        break;
                    } 
                    if("Parametro".equals(current.getAmbito()) && (this.Simbolos.size()==i || i==-1)){
                        System.out.println("\u001B[31mError semantico. Linea: " + Line + " Columna: " + Column +  " Cantidad de parametros invalida \u001B[31m");
                        break;
                    } 
                }
            }
        }
        this.VaciarParams();
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
            if(Ambito.equals("Global")){
                String dato = ""; 
                if (tipo.Tipo == "int"){
                    dato = "dd";
                }
                if(tipo.Tipo == "long"){
                    dato = "dq";
                }
                if(tipo.Tipo == "char"){
                    dato = "db"; 
                }
                if(tipo.Tipo == "short"){
                    dato = "dw";
                }
                if(tipo.Tipo == "long int"){
                    dato = "dq";
                }
                if(tipo.Tipo == "short int"){
                    dato = "dw";
                }
                System.out.println(id.Id + "       " + dato + "        "+ "0");
            }
            this.tabla.InsertSimbolo(id.Id,tipo.Tipo,Ambito,id.Line,id.Column);

        }

        this.Pop();
        
        return 1;
    }

    private void VaciarParams() {
        if(Simbolos.size() == 0){
            return;
        }
        for (int i = this.Top(); i > 0; i--) {
            if ("RS_Param".equals(Simbolos.get(i-1).nombre())) {
                
                this.Pop();
            } 
        }
    }

    public RS_While BuscarRS_While(){
        for (int i = this.Simbolos.size()-1; i >= 0; i--) {
            if("RS_While".equals(Simbolos.get(i).nombre())){
                return (RS_While) Simbolos.get(i);
            }
        }
        return null;
    }
    
    public RS_If BuscarRSIf(){
        RS_If rsBuscado = null;
        int cont = 0;

        for(int i = this.Simbolos.size(); i > cont;i--){
            if("RS_If".equals(Simbolos.get(i-1).nombre())){

                rsBuscado = (RS_If) Simbolos.get(i-1);
                cont = i;
                break;
            }
        }
        return rsBuscado;
    }
    
    private int getFirstParam() {
        int cont = -1;
        if(Simbolos.size() == 0){
            return -1;
        }
        for (int i = this.Simbolos.size()-1; i >= 0; i--) {
            if(!"RS_Param".equals(Simbolos.get(i).nombre())){
                break;
            }
            cont = i;
        }
        return cont;
    }
}
