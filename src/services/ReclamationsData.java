/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Reclamation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Dahmani
 */
public class ReclamationsData {
    
    private ObservableList<Reclamation> list=FXCollections.observableArrayList();
    
    public ReclamationsData() {
        
        ReclamationsService us=ReclamationsService.getInstance();
        list= us.displayAll();
    }
    
    public ObservableList<Reclamation> getUsers(){
        return list;
    }
    
}
