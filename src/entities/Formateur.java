/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Dahmani
 */
public class Formateur extends User{
    
    private SimpleStringProperty curriculum_vitae;
    
    public Formateur(){
        super();
    }
    
    public SimpleStringProperty getCv(){
        return this.curriculum_vitae;
    }
    
    public void setCv(String url){
        this.curriculum_vitae=new SimpleStringProperty(url);
    }
    
}
