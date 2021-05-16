/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import controllers.AccueilController;
import java.net.URL;
import java.util.Objects;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

/**
 *
 * @author wajdi
 */
public class Formation {

    private SimpleIntegerProperty id;
    private SimpleIntegerProperty id_formateur;
    private SimpleStringProperty titre;
    private SimpleIntegerProperty note;
    private SimpleStringProperty description;

    public Formation() {
    }

    public Formation(int id, int id_formateur, String titre, int note, String description) {
        this.id = new SimpleIntegerProperty(id);
        this.id_formateur = new SimpleIntegerProperty(id_formateur);
        this.titre = new SimpleStringProperty(titre);
        this.note = new SimpleIntegerProperty(note);
        this.description = new SimpleStringProperty(description);
    }

    public Formation(int id_formateur, String titre, int note, String description) {
        this.id_formateur = new SimpleIntegerProperty(id_formateur);
        this.titre = new SimpleStringProperty(titre);
        this.note = new SimpleIntegerProperty(note);
        this.description = new SimpleStringProperty(description);
    }

    public SimpleIntegerProperty getId() {
        return id;
    }

    public SimpleIntegerProperty getId_formateur() {
        return id_formateur;
    }

    public SimpleStringProperty getTitre() {
        return titre;
    }

    public SimpleIntegerProperty getNote() {
        return note;
    }

    public SimpleStringProperty getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = new SimpleIntegerProperty(id);
    }

    public void setId_formateur(int id_formateur) {
        this.id_formateur = new SimpleIntegerProperty(id_formateur);
    }

    public void setTitre(String titre) {
        this.titre = new SimpleStringProperty(titre);
    }

    public void setNote(int note) {
        this.note = new SimpleIntegerProperty(note);
    }

    public void setDescription(String description) {
        this.description = new SimpleStringProperty(description);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Formation other = (Formation) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.id_formateur != other.id_formateur) {
            return false;
        }
        if (this.note != other.note) {
            return false;
        }
        if (!Objects.equals(this.titre, other.titre)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Formation{" + "id=" + id + ", id_formateur=" + id_formateur + ", titre=" + titre + ", note=" + note + ", description=" + description + '}';
    }
  

}
