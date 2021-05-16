/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import database.Database;
import entities.Evenement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javax.swing.JOptionPane;

/**
 *
 * @author hiche
 */
public class EvenementService {
    
    private Statement st;
    private ResultSet rs;
    private static EvenementService instance;
    
    private EvenementService() {
        Database cs = Database.getInstance();
        try {
            st = cs.getConnection().createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(EvenementService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static EvenementService getInstance() {
        if (instance == null) {
            instance = new EvenementService();
        }
        return instance;
    }
    
    public List<Evenement> getAllEvenements() {
        String req = "select * from evenement";
        List<Evenement> list = new ArrayList<>();
        
        try {
            rs = st.executeQuery(req);
            while (rs.next()) {
                Evenement E = new Evenement();
                E.setId(rs.getInt("id"));
                E.setTitre(rs.getString("titre"));
                E.setEmplacement(rs.getString("emplacement"));
                E.setPrix(rs.getInt("prix"));
                E.setDate_event(rs.getString("date_event"));
                E.setImage_event(rs.getString("image_event"));
                E.setFondation(rs.getString("fondation"));
                E.setNbMaxParticipants(rs.getInt("nbMaxParticipants"));
                E.setDuree(rs.getString("duree"));
                list.add(E);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(EvenementService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public ObservableList<Evenement> getAllEvenementsByTitre(String event) {
        String req = "select * from evenement where titre='" + event + "'";
        ObservableList<Evenement> list = FXCollections.observableArrayList();
        
        try {
            rs = st.executeQuery(req);
            while (rs.next()) {
                Evenement E = new Evenement();
                E.setId(rs.getInt("id"));
                E.setTitre(rs.getString("titre"));
                E.setEmplacement(rs.getString("emplacement"));
                E.setPrix(rs.getInt("prix"));
                E.setDate_event(rs.getString("date_event"));
                E.setImage_event(rs.getString("image_event"));
                E.setFondation(rs.getString("fondation"));
                E.setNbMaxParticipants(rs.getInt("nbMaxParticipants"));
                E.setDuree(rs.getString("duree"));
                list.add(E);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(EvenementService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    
    
     public ObservableList<Evenement> getAllEvenementObservableUpdated() {
        String req = "select * from evenement where nbMaxParticipants > 0";
        
        ObservableList<Evenement> list = FXCollections.observableArrayList();
        
        try {
            rs = st.executeQuery(req);
            while (rs.next()) {
                Evenement E = new Evenement();
                E.setId(rs.getInt("id"));
                E.setTitre(rs.getString("titre"));
                E.setEmplacement(rs.getString("emplacement"));
                E.setPrix(rs.getInt("prix"));
                E.setDate_event(rs.getString("date_event"));
                E.setImage_event(rs.getString("image_event"));
                E.setFondation(rs.getString("fondation"));
                E.setNbMaxParticipants(rs.getInt("nbMaxParticipants"));
                E.setDuree(rs.getString("duree"));
                list.add(E);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(EvenementService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    
    public ObservableList<Evenement> getAllEvenementObservable() {
        String req = "select * from evenement";
        
        ObservableList<Evenement> list = FXCollections.observableArrayList();
        
        try {
            rs = st.executeQuery(req);
            while (rs.next()) {
                Evenement E = new Evenement();
                E.setId(rs.getInt("id"));
                E.setTitre(rs.getString("titre"));
                E.setEmplacement(rs.getString("emplacement"));
                E.setPrix(rs.getInt("prix"));
                E.setDate_event(rs.getString("date_event"));
                E.setImage_event(rs.getString("image_event"));
                E.setFondation(rs.getString("fondation"));
                E.setNbMaxParticipants(rs.getInt("nbMaxParticipants"));
                E.setDuree(rs.getString("duree"));
                list.add(E);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(EvenementService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public void insertEvenement(Evenement E) {
        String req = "insert into evenement (titre,emplacement,prix,date_event,image_event,fondation,nbMaxParticipants,duree) values ('" + E.getTitre().get() + "','" + E.getEmplacement().get() + "','" + E.getPrix().get() + "','" + E.getDate_event().get() + "','" + E.getImage_event().get() + "','" + E.getFondation().get() + "','" + E.getNbMaxParticipants().get() + "','" + E.getDuree().get() + "')";
      
        
        
        try {
            st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(EvenementService.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
    }
    
    public void deleteEvenement(Evenement E) {
        String req = "delete from evenement where id='" + E.getId() + "'";
        if (E != null) {
            try {
                
                int deleted = st.executeUpdate(req);                
                
                if (deleted > 0) {
                    JOptionPane.showMessageDialog(null, "Deleted successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "Nothing deleted");
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(EvenementService.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Evenement n'existe pas");
        }
        
    }
    
    
    
     public void deleteEvenementById(int id) {
        String req = "delete from evenement where id="+id;
        if (id >0) {
            try {
                
                int deleted = st.executeUpdate(req);                
                
                if (deleted > 0) {
                   
Alert alert = new Alert(AlertType.INFORMATION);
alert.setTitle("Evenement supprimé");
alert.setHeaderText("supprimé");
alert.setContentText("Vous avez supprimer l'evenement ");
alert.showAndWait();                } else {
                    JOptionPane.showMessageDialog(null, "Nothing deleted");
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(EvenementService.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Evenement n'existe pas");
        }
        
    }
    
    
  
    public boolean updateEvenement(Evenement E) {
       String req = "UPDATE evenement SET titre='"+E.getTitre().get()+"',emplacement='"+E.getEmplacement().get()+"',prix='"+E.getPrix().get()+"',date_event='"+E.getDate_event().get()+"',image_event='"+E.getImage_event().get()+"',fondation='"+E.getFondation().get()+"',nbMaxParticipants='"+E.getNbMaxParticipants().get()+"',duree='"+E.getDuree().get()+"' WHERE id=" +E.getId().get()+""; 
        try {
           int n=st.executeUpdate(req);
            if (n > 0) {
                return true;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(EvenementService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        
    }
    
    public Evenement getEvenementById(int id) {
        String req = "select * from evenement where id=" + id;
        Evenement E = new Evenement();
        try {
            rs = st.executeQuery(req);
            rs.next();
            
            E.setId(rs.getInt("id"));
            E.setTitre(rs.getString("titre"));
            E.setEmplacement(rs.getString("emplacement"));
            E.setPrix(rs.getInt("prix"));
            E.setDate_event(rs.getString("date_event"));
            E.setImage_event(rs.getString("image_event"));
            E.setFondation(rs.getString("fondation"));
            E.setNbMaxParticipants(rs.getInt("nbMaxParticipants"));
            E.setDuree(rs.getString("duree"));
            
        } catch (SQLException ex) {
            Logger.getLogger(EvenementService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return E;
        
    }
    
}
