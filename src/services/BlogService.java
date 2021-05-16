/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import database.Database;
import entities.Blog;
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
public class BlogService {
    
    private static BlogService instance;
    private Statement st;
    private ResultSet rs;
    
    public BlogService() {
        Database cs=Database.getInstance();
        try {
            st=cs.getConnection().createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static BlogService getInstance(){
        if(instance==null) 
            instance=new BlogService();
        return instance;
    }
    
    
    public void ajouterBlog(Blog b){
        String req="INSERT INTO blog (id_user,titre,contenu,image_blog) VALUES ("+b.getId_user()+",'"+b.gettitre()+"','"+b.getcontenu()+"','"+b.getImage_blog()+"')";
        try {
            st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
    
    public void modifierBlog(Blog b){
        String req="UPDATE blog SET image_blog='"+b.getImage_blog()+"', contenu='"+b.getcontenu()+"', titre='"+b.gettitre()+"' WHERE id="+b.getId_blog();
        try {
            st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
    
    public void supprimerBlog(Blog b){
        
        try {
            String req="DELETE FROM blog WHERE id="+b.getId_blog();
            
            //PreparedStatement pst=new Database().cnx.prepareStatement(req);
            st.executeUpdate(req);
            System.err.println("blog Supprimé");
        } catch (SQLException ex) {
            Logger.getLogger(BlogService.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.getMessage());
            System.err.println("Blog non supprimé");
            
        }
    }
    
    public List<Blog> getBlogsByUser(int id){
        String req="SELECT * FROM blog WHERE id_user="+id;
        List<Blog> list=new ArrayList<>();
        try {
            rs=st.executeQuery(req);
            while(rs.next()){
                Blog b=new Blog();
                b.setId_blog(rs.getInt("id"));
                b.setId_user(rs.getInt("id_user"));
                b.settitre(rs.getString("titre"));
                b.setcontenu(rs.getString("contenu"));
                b.setImage_blog(rs.getString("image_blog"));
                list.add(b);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    
    
}
