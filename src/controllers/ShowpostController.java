/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Post;
import entities.User;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import services.PostService;

/**
 * FXML Controller class
 *
 * @author Methnani
 */
public class ShowpostController implements Initializable {

    @FXML
    private Button delet;
    @FXML
    private Button refresh;
    @FXML
    private Button update;
    @FXML
    private Button add;
    @FXML
    private VBox vboxPosts;
    
    private User currentUser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void deletpost(ActionEvent event) {
    }

    @FXML
    private void refresh(ActionEvent event) {
    }

    @FXML
    private void updatePost(ActionEvent event) {
    }

    @FXML
    private void ajouterPost(ActionEvent event) {
    }
    
    public void initData(User u){
        this.currentUser=u;
        PostService ps = PostService.getInstance();
        List<Post> posts = ps.getPostsByUser(currentUser.getId().get());
        for (Post p : posts) {
            ImageView imagepost = new ImageView(p.getImage_poste());
            Label caption = new Label(p.getCaption());
            vboxPosts.getChildren().add(imagepost);
            vboxPosts.getChildren().add(caption);
            
        }
    }
    
}
