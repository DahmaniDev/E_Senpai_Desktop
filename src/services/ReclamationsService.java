/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import database.Database;
import entities.Reclamation;
import entities.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Dahmani
 */
public class ReclamationsService {
    
    private static ReclamationsService instance;
    private Statement st;
    private ResultSet rs;
    
    private ReclamationsService() {
        Database cs=Database.getInstance();
        try {
            st=cs.getConnection().createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static ReclamationsService getInstance(){
        if(instance==null) 
            instance=new ReclamationsService();
        return instance;
    }
    
    public ObservableList<Reclamation> displayAll() {
        String req="select * from reclamation";
        ObservableList<Reclamation> list=FXCollections.observableArrayList();       
        
        try {
            rs=st.executeQuery(req);
            while(rs.next()){
                Reclamation p=new Reclamation();
                p.setId(rs.getInt(1));
                p.setIdUser(rs.getInt("id_user"));
                p.setStatut(rs.getString("statut"));
                p.setSujet(rs.getString("sujet_reclamation"));
                p.setIdAdmin(rs.getInt("admin_trait"));
                p.setContenu(rs.getString("contenu"));
                list.add(p);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public void insertReclamation(Reclamation r){
        String req="insert into RECLAMATION (id_user,statut,sujet_reclamation,admin_trait,contenu) values("+r.getIdUser().get()+",'En attente','"+r.getSujet().get()+"',0,'"+r.getContenu().get()+"')";
        try {
            st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
           
        }
    }
    
    public void updateReclamation(Reclamation r){
        String req="UPDATE reclamation SET statut='"+r.getStatut().get()+"',admin_trait='"+r.getIdAdminTrait().get()+"' WHERE id="+r.getId().get();
        try {
            st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
           
        }
    }
    
    public ObservableList<Reclamation> displayById(int id) {
        String req="select * from reclamation where id_user="+id;
        ObservableList<Reclamation> list=FXCollections.observableArrayList();       
        
        try {
            rs=st.executeQuery(req);
            while(rs.next()){
                Reclamation p=new Reclamation();
                p.setId(rs.getInt(1));
                p.setIdUser(rs.getInt("id_user"));
                p.setStatut(rs.getString("statut"));
                p.setSujet(rs.getString("sujet_reclamation"));
                p.setIdAdmin(rs.getInt("admin_trait"));
                p.setContenu(rs.getString("contenu"));
                list.add(p);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public Reclamation getReclamationById(int id){
        String req = "select * from reclamation where id='" + id + "'";

        Reclamation p = new Reclamation();
        try {
            rs = st.executeQuery(req);
            while (rs.next()) {

                p.setId(rs.getInt(1));
                p.setIdUser(rs.getInt("id_user"));
                p.setIdAdmin(rs.getInt("admin_trait"));
                p.setStatut(rs.getString("statut"));
                p.setSujet(rs.getString("sujet_reclamation"));
                p.setContenu(rs.getString("contenu"));
                
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }
    
    public int countReclamations(){
        String req = "SELECT COUNT(id) FROM reclamation";
        int sum=0;
        try {
            rs = st.executeQuery(req);
            while (rs.next()) {
                sum=rs.getInt(1);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sum;
    }
    
    
}
