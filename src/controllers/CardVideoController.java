/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Formation;
import entities.Slide;
import entities.User;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.SlideService;

/**
 * FXML Controller class
 *
 * @author wajdi
 */
public class CardVideoController implements Initializable {

    @FXML
    private VBox boxId;
    @FXML
    private ImageView imageID;
    @FXML
    private Label IdNomVideo;
    Slide s;
    User  currentUser;
    Formation f ;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                IdNomVideo.setWrapText(true);

    }    
      public void setData(Formation formation, User user, Slide slide) throws UnsupportedEncodingException, MalformedURLException {
        this.s = slide;
        this.f = formation;
        this.currentUser = user;
          IdNomVideo.setText(slide.getVideo_slide().get());
              
    }

    @FXML
    private void handleAfficherFormation(MouseEvent event) {
        System.out.println(currentUser.getRole().get());
          try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/views/Video.fxml"));
                Parent page2 = loader.load();
                VideoController videoController = loader.getController();
                videoController.setData(f, currentUser, s);
                Scene scene = new Scene(page2);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);

                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(AuthentificationController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}
