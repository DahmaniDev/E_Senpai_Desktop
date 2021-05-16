/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Abonnement;
import entities.Formation;
import entities.User;
import java.io.IOException;
import java.net.URL;
import java.util.List;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import services.AbonnementService;
import services.FormationService;
import services.SlideService;
import services.UserService;

/**
 * FXML Controller class
 *
 * @author wajdi
 */
public class CardFormationsEtudiantController implements Initializable {

    @FXML
    private VBox boxId;
    @FXML
    private ImageView imageID;
    @FXML
    private Label TitreFormationId;
    @FXML
    private Label NomFormateurId;
    @FXML
    private Rating RateId;
User currentUser;
Formation f;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setData(Formation formation, User user, User u, int i) {    
        this.currentUser = u;
        this.f = formation;
        if (user.getPhoto_profil().get().equals("")) {
            Image image = new Image(getClass().getResourceAsStream("/assets/formation.jpg"));
            imageID.setImage(image);
        } else {
            Image image = new Image(getClass().getResourceAsStream(user.getPhoto_profil().get()));
            imageID.setImage(image);
        }
        TitreFormationId.setText(formation.getTitre().get());
        NomFormateurId.setText(user.getNom().get());
        RateId.setRating(formation.getNote().get());
        AbonnementService as =AbonnementService.getInstance();
        if (i == formation.getId().get()) {
            boxId.setStyle("-fx-background-color:#66ff99");
            System.out.println("green = " + formation.getTitre().get());
        } else  {
            boxId.setStyle("-fx-background-color:#ff6666");
            System.out.println("red = " + formation.getTitre().get());
        }

    }

    @FXML
    private void handleAfficherFormation(MouseEvent event) {
          
        FormationService fs = FormationService.getInstance();
        SlideService ss = SlideService.getInstance();
        UserService us = UserService.getInstance();
        if (event.getSource() == boxId) {
            f = fs.getFormationById(f.getId().get());
            List<User> users = us.displayAllbyId(currentUser.getId().get());
            for (User u : users) {
                this.currentUser = u;
            }
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/views/ConsulterFormation.fxml"));
                Parent page2 = loader.load();
                ConsulterFormationController controller = loader.getController();
                controller.initDataa(currentUser, f);
                Scene scene = new Scene(page2);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);

                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(AuthentificationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    
    }

}
