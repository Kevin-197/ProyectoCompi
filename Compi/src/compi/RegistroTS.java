/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compi;

/**
 *
 * @author kevin
 */
public class RegistroTS {
    private String Identificador;
    private String Tipo;
    private String Ambito;

    public RegistroTS(String Identificador, String Tipo, String Ambito) {
        this.Identificador = Identificador;
        this.Tipo = Tipo;
        this.Ambito = Ambito;
    }
    

    public String getIdentificador() {
        return Identificador;
    }

    public void setIdentificador(String Identificador) {
        this.Identificador = Identificador;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }

    public String getAmbito() {
        return Ambito;
    }

    public void setAmbito(String Ambito) {
        this.Ambito = Ambito;
    }
    
    
}
