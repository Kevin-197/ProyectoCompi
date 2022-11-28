/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compi;

/**
 *
 * @author kevin
 */
public class RS_Param extends RS {
    String Id;
    int Colum;
    int Line;

    public RS_Param(String Id, int Line, int Colum) {
        this.Id = Id;
        this.Line = Line;
        this.Colum = Colum;
    }
}
