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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
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
public class CardGestionFormationController implements Initializable {

    @FXML
    private VBox boxId;
    @FXML
    private ImageView imageID;
    @FXML
    private Label TitreFormationId;
    @FXML
    private Label IdFormateur;
    @FXML
    private Label IdFormation;
    @FXML
    private Rating RateId;
    @FXML
    private ImageView ConsulterFormationID;
    @FXML
    private ImageView DeleteFormationId;
    User currentUser;
    Formation f;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setData(Formation formation, User user, User u) {
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
        RateId.setRating(formation.getNote().get());
        IdFormation.setText(formation.getId().get() + "");
        IdFormation.setVisible(false);
        IdFormateur.setText(user.getId().get() + "");
        IdFormateur.setVisible(false);

    }

    @FXML
    private void handleConsulterFormation(MouseEvent event) {
        FormationService fs = FormationService.getInstance();
        SlideService ss = SlideService.getInstance();
        UserService us = UserService.getInstance();
        System.out.println(currentUser.getRole().get());
        if (event.getSource() == ConsulterFormationID) {
            f = fs.getFormationById(f.getId().get());
            List<User> users = us.displayAllbyId(currentUser.getId().get());
              try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/views/GestionConsulterFormation.fxml"));
                Parent page2 = loader.load();
                GestionConsulterFormationController gestionConsulterFormationController = loader.getController();
                gestionConsulterFormationController.initDataa(currentUser, f);
                Scene scene = new Scene(page2);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);

                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(AuthentificationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @FXML
    private void handleDeleteFormation(MouseEvent event) {
        int idFormation = Integer.parseInt(IdFormation.getText());
        int idFormateur = Integer.parseInt(IdFormateur.getText());
        AbonnementService as =AbonnementService.getInstance();
        FormationService fs = FormationService.getInstance();
        SlideService ss = SlideService.getInstance();
        UserService us = UserService.getInstance();
        if (event.getSource() == DeleteFormationId) {
            ss.DeleteSlide(idFormation);
            fs.DeleteFormationCard(idFormation);
            List<Abonnement> ab = as.getAllaAbonByIdFormation(idFormation);
            for(Abonnement a: ab){
                as.DeleteAbonnementById(idFormation, a.getId_etudiant().get());
            }
            
            List<User> users = us.displayAllbyId(idFormateur);
            for (User u : users) {
                this.currentUser = u;
            }
            Image img = new Image("assets/delete_alert.png", 50, 50, false, false);
            Notifications notificationsBuilder = Notifications.create()
                    .title("Formation")
                    .text("Formation supprimer avec succée!!")
                    .graphic(new ImageView(img))
                    .hideAfter(Duration.seconds(6))
                    .position(Pos.TOP_LEFT);
            notificationsBuilder.darkStyle();
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/views/GestionFormations.fxml"));
                Parent page2 = loader.load();
                GestionFormationsController controller = loader.getController();
                controller.initDataa(currentUser);
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
