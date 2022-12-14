/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compi;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author kevin
 */
public class PilaSemantica {
    
    public String rutaEnsamblador = "D:/RepoVR/ProyectoCompi/Compi/src/compi/archivoAnalisis.asm"; ;
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
                if((funcion+1)>=this.tabla.Simbolos.size()){
                    current = null;
                }
                else{
                    current = this.tabla.Simbolos.get(++funcion);
                }
                if(current != null && "Parametro".equals(current.getAmbito()) && (i!=-1 && i!= this.Simbolos.size()) && "RS_Param".equals(Simbolos.get(i).nombre())){
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
                    if((current == null || !"Parametro".equals(current.getAmbito())) && (i!= this.Simbolos.size()) && (i!=-1 && "RS_Param".equals(Simbolos.get(i).nombre()))){
                        System.out.println("\u001B[31mError semantico. Linea: " + Line + " Columna: " + Column +  " Cantidad de parametros invalida \u001B[31m");
                        break;
                    } 
                    if((current != null && "Parametro".equals(current.getAmbito())) && (this.Simbolos.size()==i || i==-1)){
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
            if("RS_Var".equals(Simbolos.get(i).nombre())){
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
                    try{
                        FileWriter escritorEnsamblador = new FileWriter(rutaEnsamblador,true);
                        escritorEnsamblador.write(id.Id + "       " + dato + "        "+ "0"+  "\n");
                        escritorEnsamblador.close();
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                }
                this.tabla.InsertSimbolo(id.Id,tipo.Tipo,Ambito,id.Line,id.Column);
            }
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
    
    public void EndIf() {
        if(Simbolos.size() == 0){
            return;
        }
        for (int i = this.Top(); i > 0; i--) {
            if (!"RS_If".equals(Simbolos.get(i-1).nombre())) {
                
                this.Pop();
            }
            else{
                this.Pop();
                break;
            }
        }
    }
    
    public void EndWhile() {
        if(Simbolos.size() == 0){
            return;
        }
        for (int i = this.Top(); i > 0; i--) {
            if (!"RS_While".equals(Simbolos.get(i-1).nombre())) {
                this.Pop();
            }
            else{
                this.Pop();
                break;
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
    
    public void AddPrefix(String Val){
        RS_DO rs_do = (RS_DO) this.Pop();
        String temp="";
        int valor = 0;
        if("+".equals(Val)){
            temp = rs_do.Valor;
            rs_do.setValor(temp);
        }
        else if ("-".equals(Val)){
           temp = "-".concat(rs_do.Valor);
           rs_do.setValor(temp);
        }
        else if ("!".equals(Val)){
            valor = rs_do.getValor();
            if(valor>0){
                rs_do.setValor("0");
            }
            else if(valor<=0){
                rs_do.setValor("1");
            }
        }
        this.Push(rs_do);
    }
    
    public void EvalOP(){ 
        RS_DO rs_do2 = (RS_DO) this.Pop();
        RS_Operador rs_op = (RS_Operador) this.Pop();
        RS_DO rs_do1 = (RS_DO) this.Pop();
        RS_DO rs_doResult;
        int constantFold = 0;
        String direccion = "";
        String operacion = "";
        boolean constantFoldbool = false;
        if(!rs_do2.Tipo.equals("Direccion") && !rs_do1.Tipo.equals("Direccion")){
            switch (rs_op.Id) {
                case "+":
                    constantFold = rs_do1.getValor() + rs_do2.getValor();
                    break;
                case "-":
                    constantFold = rs_do1.getValor() - rs_do2.getValor();
                    break;
                case "*":
                    constantFold = rs_do1.getValor() * rs_do2.getValor();
                    break;
                case "/":
                    constantFold = rs_do1.getValor() / rs_do2.getValor();
                    break;
                case "%":
                    constantFold = rs_do1.getValor() % rs_do2.getValor();
                    break;
                case "==":
                    constantFoldbool = rs_do1.getValor() == rs_do2.getValor();
                    if(constantFoldbool){
                        constantFold = 1;
                    }
                    else{
                        constantFold = 0;
                    }   break;
                case "<":
                    constantFoldbool = rs_do1.getValor() < rs_do2.getValor();
                    if(constantFoldbool){
                        constantFold = 1;
                    }
                    else{
                        constantFold = 0;
                    }   break;
                case ">":
                    constantFoldbool = rs_do1.getValor() > rs_do2.getValor();
                    if(constantFoldbool){
                        constantFold = 1;
                    }
                    else{
                        constantFold = 0;
                    }   break;
                case ">=":
                    constantFoldbool = rs_do1.getValor() >= rs_do2.getValor();
                    if(constantFoldbool){
                        constantFold = 1;
                    }
                    else{
                        constantFold = 0;
                    }   break;
                case "<=":
                    constantFoldbool = rs_do1.getValor() <= rs_do2.getValor();
                    if(constantFoldbool){
                        constantFold = 1;
                    }
                    else{
                        constantFold = 0;
                    }   break;
                case "!=":
                    constantFoldbool = rs_do1.getValor() != rs_do2.getValor();
                    if(constantFoldbool){
                        constantFold = 1;
                    }
                    else{
                        constantFold = 0;
                    }   break;
                default:
                    break;
            }
            rs_doResult = new RS_DO("Numero",constantFold+"");
        }
        else{
            switch (rs_op.Id) {
                case "+":
                    operacion = "mov rax, "+rs_do1.Valor+"\n"+
                    "add rax, "+rs_do2.Valor+"\n"+
                    "mov rbx, rax";
                    direccion = "rbx";
                    break;
                case "-":
                    operacion = "mov rax, "+rs_do1.Valor+"\n"+
                    "sub rax, "+rs_do2.Valor+"\n"+
                    "mov rbx, rax";
                    direccion = "rbx";
                    break;
                case "*":
                    operacion = "mov rax, "+rs_do1.Valor+"\n"+
                    "imul rax, "+rs_do2.Valor+"\n"+
                    "mov rbx, rax";
                    direccion = "rbx";
                    break;
                case "/":
                    operacion = "mov rax, "+rs_do1.Valor+"\n"+
                    "mov r8b, "+rs_do2.Valor+"\n"+
                    "idiv r8b\n"+
                    "mov rbx, rax";
                    direccion = "rbx";
                    break;
                case "%":
                    operacion = "mov rax, "+rs_do1.Valor+"\n"+
                    "mov r8b, "+rs_do2.Valor+"\n"+
                    "idiv r8b\n"+
                    "mov rbx, rdx";
                    direccion = "rbx";
                    break;
                case "==":
                    operacion = "mov rax, "+rs_do1.Valor+"\n"+
                    "cmp rax, "+rs_do2.Valor+"\n";
                    direccion = "JNE";
                    break;
                case "<":
                    operacion = "mov rax, "+rs_do1.Valor+"\n"+
                    "cmp rax, "+rs_do2.Valor+"\n";
                    direccion = "JNLE";
                    break;
                case ">":
                    operacion = "mov rax, "+rs_do1.Valor+"\n"+
                    "cmp rax, "+rs_do2.Valor+"\n";
                    direccion = "JNGE";
                    break;
                case ">=":
                    operacion = "mov rax, "+rs_do1.Valor+"\n"+
                    "cmp rax, "+rs_do2.Valor+"\n";
                    direccion = "JL";
                    break;
                case "<=":
                    operacion = "mov rax, "+rs_do1.Valor+"\n"+
                    "cmp rax, "+rs_do2.Valor+"\n";
                    direccion = "JG";
                    break;
                case "!=":
                    operacion = "mov rax, "+rs_do1.Valor+"\n"+
                    "cmp rax, "+rs_do2.Valor+"\n";
                    direccion = "JE";
                    break;
                case "+=":
                    operacion = "mov rax, "+rs_do1.Valor+"\n"+
                    "add rax, "+rs_do2.Valor+"\n"+
                    "mov "+rs_do1.Valor+", rax";
                    direccion = rs_do1.Valor;
                    break;
                case "-=":
                    operacion = "mov rax, "+rs_do1.Valor+"\n"+
                    "sub rax, "+rs_do2.Valor+"\n"+
                    "mov "+rs_do1.Valor+", rax";
                    direccion = rs_do1.Valor;
                    break;
                case "*=":
                    operacion = "mov rax, "+rs_do1.Valor+"\n"+
                    "imul rax, "+rs_do2.Valor+"\n"+
                    "mov "+rs_do1.Valor+", rax";
                    direccion = rs_do1.Valor;
                    break;
                case "/=":
                    operacion = "mov rax, "+rs_do1.Valor+"\n"+
                    "mov r8b, "+rs_do2.Valor+"\n"+
                    "idiv r8b\n"+
                    "mov "+rs_do1.Valor+", rax";
                    direccion = rs_do1.Valor;
                    break;
                case "=":
                    operacion = "mov rax, "+rs_do2.Valor+"\n"+
                    "mov "+rs_do1.Valor+", rax";
                    direccion = rs_do1.Valor;
                    break;
                default:
                    break;
            }
            try{
                FileWriter escritorEnsamblador = new FileWriter(rutaEnsamblador,true);
                escritorEnsamblador.write(operacion+"\n");
                escritorEnsamblador.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }
            rs_doResult = new RS_DO("Direccion",direccion+"");
        
        }
        this.Push(rs_doResult);
    }
    public void EvalOPUnaria(int op){ 
        RS_DO rs_do1 = (RS_DO) this.Pop();
        RS_DO rs_doResult;
        String direccion = "";
        String operacion = "";
        boolean constantFoldbool = false;
        switch (op) {
            case 1:
                operacion = "mov rax, "+rs_do1.Valor+"\n"+
                "inc rax \n"+
                "mov "+rs_do1.Valor+", rax";
                direccion = rs_do1.Valor;
                break;
            case 2:
                operacion = "mov rax, "+rs_do1.Valor+"\n"+
                "dec rax \n"+
                "mov "+rs_do1.Valor+", rax";
                direccion = rs_do1.Valor;
                break;
        }
        try{
            FileWriter escritorEnsamblador = new FileWriter(rutaEnsamblador,true);
            escritorEnsamblador.write(operacion+"\n");
            escritorEnsamblador.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        rs_doResult = new RS_DO("Direccion",direccion+"");
        this.Push(rs_doResult);
    }
}
