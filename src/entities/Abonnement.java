/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Objects;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author wajdi
 */
public class Abonnement {

    private SimpleIntegerProperty id_formation;
    private SimpleIntegerProperty id_etudiant;
    private SimpleStringProperty date_abonnement;
    private SimpleIntegerProperty rated;

    public Abonnement() {
    }

    public Abonnement(int id_formation, int id_etudiant) {
        this.id_formation = new SimpleIntegerProperty(id_formation);
        this.id_etudiant = new SimpleIntegerProperty(id_etudiant);
    }

    public void setId_formation(int id_formation) {
        this.id_formation = new SimpleIntegerProperty(id_formation);
    }

    public void setId_etudiant(int id_etudiant) {
        this.id_etudiant = new SimpleIntegerProperty(id_etudiant);
    }

    public SimpleIntegerProperty getId_formation() {
        return id_formation;
    }

    public SimpleIntegerProperty getId_etudiant() {
        return id_etudiant;
    }

    public SimpleIntegerProperty getRated() {
        return rated;
    }

    public SimpleStringProperty getDate_abonnement() {
        return date_abonnement;
    }

    public void setRated(int rated) {
          this.rated = new SimpleIntegerProperty(rated);
    }

    public void setDate_abonnement(String date_abonnement) {
        this.date_abonnement = new SimpleStringProperty(date_abonnement);
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
        final Abonnement other = (Abonnement) obj;
        if (this.id_formation != other.id_formation) {
            return false;
        }
        if (this.id_etudiant != other.id_etudiant) {
            return false;
        }
        if (!Objects.equals(this.date_abonnement, other.date_abonnement)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Abonnement{" + "id_formation=" + id_formation + ", id_etudiant=" + id_etudiant + ", date_abonnement=" + date_abonnement + '}';
    }

}
