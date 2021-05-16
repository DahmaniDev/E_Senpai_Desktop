/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import database.Database;
import entities.Post;
import entities.User;
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

/**
 *
 * @author Methnani
 */
public class PostService {
    
    private static PostService instance;
    private Statement st;
    private ResultSet rs;
    
    public PostService() {
        Database cs=Database.getInstance();
        try {
            st=cs.getConnection().createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static PostService getInstance(){
        if(instance==null) 
            instance=new PostService();
        return instance;
    }
    
    
    public void ajouterPost(Post p){
        String req="INSERT INTO post (id_user,caption,image_post) VALUES ("+p.getId_user()+",'"+p.getCaption()+"','"+p.getImage_poste()+"')";
        try {
            st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
    
    public void modifierPost(Post p){
        String req="UPDATE post SET image_post='"+p.getImage_poste()+"', caption='"+p.getCaption()+"' WHERE id="+p.getId_post();
        try {
            st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
    
    public void supprimerPost(Post p){
        
        try {
            String req="DELETE FROM post WHERE id="+p.getId_post();
            
            //PreparedStatement pst=new Database().cnx.prepareStatement(req);
            st.executeUpdate(req);
            System.err.println("Post Supprimé");
        } catch (SQLException ex) {
            Logger.getLogger(PostService.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.getMessage());
            System.err.println("Post non supprimé");
            
        }
    }
    
    public List<Post> getPostsByUser(int id){
        String req="SELECT * FROM post WHERE id_user="+id;
        List<Post> list=new ArrayList<>();
        try {
            rs=st.executeQuery(req);
            while(rs.next()){
                Post p=new Post();
                p.setId_post(rs.getInt("id"));
                p.setId_user(rs.getInt("id_user"));
                p.setCaption(rs.getString("caption"));
                p.setImage_poste(rs.getString("image_post"));
                list.add(p);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    
    
}
