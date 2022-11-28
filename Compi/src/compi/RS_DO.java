/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compi;

/**
 *
 * @author kevin
 */
public class RS_DO extends RS {
    String Tipo;
    String Valor;

    public RS_DO(String Tipo, String Valor) {

        this.Tipo = Tipo;
        this.Valor = Valor;
    }

    public String getTipo() {
        return Tipo;
    }

    public int getValor() {
        return Integer.parseInt(Valor);
    }

    public void setValor(String Valor) {
        this.Valor = Valor;
    }
    
    
}
