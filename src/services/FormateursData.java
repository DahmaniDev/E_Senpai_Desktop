/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Dahmani
 */
public class FormateursData {
    
    private ObservableList<User> users=FXCollections.observableArrayList();
    
    public FormateursData() {
        
        UserService us=UserService.getInstance();
        users= us.displayFormateursDemandes();
    }
    
    public ObservableList<User> getUsers(){
        return users;
    }
    
}
