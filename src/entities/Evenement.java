/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import javafx.beans.property.*;

/**
 *
 * @author hiche
 */
public class Evenement {

    private SimpleIntegerProperty id;
    private SimpleStringProperty titre;
    private SimpleStringProperty emplacement;
    private SimpleIntegerProperty prix;
    private SimpleStringProperty date_event;
    private SimpleStringProperty image_event;
    private SimpleStringProperty fondation;
    private SimpleIntegerProperty nbMaxParticipants;
    private SimpleStringProperty duree;

    public Evenement() {
    }

    public SimpleIntegerProperty getId() {
        return id;
    }

    public void setId(int id) {
        this.id = new SimpleIntegerProperty(id);
    }

    public SimpleStringProperty getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = new SimpleStringProperty(titre);
    }

    public SimpleStringProperty getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement = new SimpleStringProperty(emplacement);
    }

    public SimpleIntegerProperty getPrix() {
        return prix;
    }

    public void setPrix(Integer prix) {
        this.prix = new SimpleIntegerProperty(prix);
    }

    public SimpleStringProperty getDate_event() {
        return date_event;
    }

    public void setDate_event(String date_event) {
        this.date_event = new SimpleStringProperty(date_event);
    }

    public SimpleStringProperty getImage_event() {
        return image_event;
    }

    public void setImage_event(String image_event) {
        this.image_event = new SimpleStringProperty(image_event);
    }

    public SimpleStringProperty getFondation() {
        return fondation;
    }

    public void setFondation(String fondation) {
        this.fondation = new SimpleStringProperty(fondation);
    }

    public SimpleIntegerProperty getNbMaxParticipants() {
        return nbMaxParticipants;
    }

    public void setNbMaxParticipants(int nbMaxParticipants) {
        this.nbMaxParticipants = new SimpleIntegerProperty(nbMaxParticipants);
    }

    public SimpleStringProperty getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = new SimpleStringProperty(duree);
    }

}
