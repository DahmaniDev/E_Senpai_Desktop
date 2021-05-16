/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author Methnani
 */
public class Blog {
    private int id_blog;
    private String titre;
    private String contenu;
    private String image_blog;
    private int id_user;
    
    public Blog()
    {
        
    }

    public Blog(int i, String User_id,String titre, String Caption, String image_blog) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getId_blog() {
        return id_blog;
    }

    public void setId_blog(int id_blog) {
        this.id_blog = id_blog;
    }

    public String gettitre() {
        return titre;
    }

    public void settitre(String titre) {
        this.titre = titre;
    }
    public String getcontenu() {
        return contenu;
    }

    public void setcontenu(String contenu) {
        this.contenu = contenu;
    }

    public String getImage_blog() {
        return image_blog;
    }

    public void setImage_blog(String image_blog) {
        this.image_blog = image_blog;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    @Override
    public String toString() {
        return "Blog{" + "id_blog=" + id_blog + ", titre=" + titre + ", contenu=" + contenu + ", image_blog=" + image_blog + ", id_user=" + id_user + '}';
    }

    
    
    
    
    
    
    
    


    
}
