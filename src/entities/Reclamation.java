/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Objects;
import javafx.beans.property.*;


/**
 *
 * @author Dahmani
 */
public class Reclamation {
    
    private SimpleIntegerProperty id;
    private SimpleIntegerProperty id_user;
    private SimpleStringProperty statut;
    private SimpleStringProperty sujet;
    private SimpleIntegerProperty id_admin_trait;
    private SimpleStringProperty contenu;
    
    public Reclamation(){
        
    }
    
    public SimpleIntegerProperty getId() {
        return id;
    }
    
    public SimpleIntegerProperty getIdUser() {
        return id_user;
    }
    
    public SimpleStringProperty getStatut() {
        return statut;
    }
    
    public SimpleStringProperty getSujet() {
        return sujet;
    }
    
    public SimpleIntegerProperty getIdAdminTrait() {
        return id_admin_trait;
    }
    
    public SimpleStringProperty getContenu() {
        return contenu;
    }
    
    public void setId(int id) {
        this.id = new SimpleIntegerProperty(id);
    }
    
    public void setIdUser(int id) {
        this.id_user = new SimpleIntegerProperty(id);
    }
    
    public void setStatut(String statut) {
        this.statut = new SimpleStringProperty(statut);
    }
    
    public void setSujet(String sujet) {
        this.sujet = new SimpleStringProperty(sujet);
    }
    
    public void setIdAdmin(int id) {
        this.id_admin_trait = new SimpleIntegerProperty(id);
    }
    
    public void setContenu(String contenu) {
        this.contenu = new SimpleStringProperty(contenu);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Reclamation other = (Reclamation) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
}
