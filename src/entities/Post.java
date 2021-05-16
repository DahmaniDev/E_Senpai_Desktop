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
public class Post {
    private int id_post;
    private String caption;
    private String image_poste;
    private int id_user;
    
    public Post()
    {
        
    }

    public Post(int i, String User_id, String Caption, String image_post) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getId_post() {
        return id_post;
    }

    public void setId_post(int id_post) {
        this.id_post = id_post;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImage_poste() {
        return image_poste;
    }

    public void setImage_poste(String image_poste) {
        this.image_poste = image_poste;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    @Override
    public String toString() {
        return "Post{" + "id_post=" + id_post + ", caption=" + caption + ", image_poste=" + image_poste + ", id_user=" + id_user + '}';
    }
    
    
    
    
    
    
    
    
}
