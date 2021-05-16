/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.Date;
import java.util.Objects;
import javafx.beans.property.*;

/**
 *
 * @author Dahmani
 */
public class User {
    
    private SimpleIntegerProperty id;
    private SimpleStringProperty nom;
    private SimpleStringProperty prenom;
    private SimpleStringProperty date_naissance;
    private SimpleStringProperty sexe;
    private SimpleStringProperty email;
    private SimpleStringProperty role;
    private SimpleStringProperty login;
    private SimpleStringProperty password;
    private SimpleStringProperty status;
    private SimpleStringProperty photo_profil;
    private SimpleStringProperty biography;
    private SimpleStringProperty cv;
    
    public User(){
        
    }

    public SimpleIntegerProperty getId() {
        return id;
    }

    public SimpleStringProperty getNom() {
        return nom;
    }

    public SimpleStringProperty getPrenom() {
        return prenom;
    }

    public SimpleStringProperty getDate_naissance() {
        return date_naissance;
    }

    public SimpleStringProperty getSexe() {
        return sexe;
    }

    public SimpleStringProperty getEmail() {
        return email;
    }

    public SimpleStringProperty getRole() {
        return role;
    }

    public SimpleStringProperty getLogin() {
        return login;
    }

    public SimpleStringProperty getPassword() {
        return password;
    }

    public SimpleStringProperty getStatus() {
        return status;
    }

    public SimpleStringProperty getPhoto_profil() {
        return photo_profil;
    }

    public SimpleStringProperty getBiography() {
        return biography;
    }
    
    public SimpleStringProperty getCv() {
        return cv;
    }
    
    public void setId(int id) {
        this.id = new SimpleIntegerProperty(id);
    }

    public void setNom(String nom) {
        this.nom = new SimpleStringProperty(nom);
    }

    public void setPrenom(String prenom) {
        this.prenom = new SimpleStringProperty(prenom);
    }

    public void setDate_naissance(String date_naissance) {
        this.date_naissance = new SimpleStringProperty(date_naissance);
    }

    public void setSexe(String sexe) {
        this.sexe = new SimpleStringProperty(sexe);
    }

    public void setEmail(String email) {
        this.email = new SimpleStringProperty(email);
    }

    public void setRole(String role) {
        this.role = new SimpleStringProperty(role);
    }

    public void setLogin(String login) {
        this.login = new SimpleStringProperty(login);
    }

    public void setPassword(String password) {
        this.password = new SimpleStringProperty(password);;
    }

    public void setStatus(String status) {
        this.status = new SimpleStringProperty(status);
    }

    public void setPhoto_profil(String photo_profil) {
        this.photo_profil = new SimpleStringProperty(photo_profil);
    }

    public void setBiography(String biography) {
        this.biography = new SimpleStringProperty(biography);
    }
    
    public void setCv(String cv) {
        this.cv = new SimpleStringProperty(cv);
    }
    
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    
    
    
}
