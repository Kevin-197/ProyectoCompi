/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compi;

/**
 *
 * @author kevin
 */
public class RS_If extends RS{
    String Else_label;
    String Exit_label;

    public RS_If(String Else_label,String Exit_label,int Line){
        this.Else_label = Else_label + Integer.toString(Line);
        this.Exit_label = Exit_label + Integer.toString(Line);
    }
}