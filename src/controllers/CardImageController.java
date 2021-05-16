/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Formation;
import entities.Slide;
import entities.User;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author wajdi
 */
public class CardImageController implements Initializable {

    User currentUser;
    Formation f;
    Slide s;
    String path;
    @FXML
    private VBox boxId;
    @FXML
    private ImageView ImageId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setData(Formation formation, User user, Slide slide) throws MalformedURLException {
        this.s = slide;
        this.f = formation;
        this.currentUser = user;
        path = "src\\contenu\\" + slide.getImage_slide().get();
        ImageView image = new ImageView(new File(path).toURI().toURL().toExternalForm());

        ImageId.setImage(image.getImage());
    }

    @FXML
    private void handleAfficherImage(MouseEvent event) throws IOException {
        System.out.println(s.getId().get());
        
            FXMLLoader laoder = new FXMLLoader(getClass().getResource("/views/ImageSlide.fxml"));
            Parent root = laoder.load();
            
            Stage stage = new Stage(StageStyle.DECORATED);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            ImageSlideController imageSlideController = laoder.getController();
            imageSlideController.setData(s);
            stage.setTitle(s.getImage_slide().get());
            stage.show();
          
         

       

    }

}
