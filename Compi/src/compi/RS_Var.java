/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compi;

/**
 *
 * @author kevin
 */
public class RS_Var extends RS{
    String Id;
    int Column;
    int Line;

    public RS_Var(String Id, int Line, int Column) {
        this.Id = Id;
        this.Line = Line;
        this.Column = Column;
    }
    
}
