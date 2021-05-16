/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Evenement;
import entities.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author hiche
 */
public class ConsulterEvenementController implements Initializable {

    @FXML
    private Button accueilBtn;
    @FXML
    private Button profilBtn;
    @FXML
    private Button formationsBtn;
    @FXML
    private Button test_quizBtn;
    @FXML
    private Button chatBtn;
    @FXML
    private Button deconnectBtn;
    @FXML
    private Button btnEvenement;
    @FXML
    private Label labelTitre;
    @FXML
    private Label labelEmplacement;
    @FXML
    private Label labelPrix;
    @FXML
    private Label labelDate;
    @FXML
    private Label labelFondation;
    @FXML
    private Label labelDuree;
    @FXML
    private Label labelParticipant;
    private ImageView imageViewEvent;

    private Evenement ev;
    @FXML
    private AnchorPane imagePane;
    User currentUser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void initData(Evenement E,User u) {
        this.currentUser=u;
        this.ev = E;
        labelTitre.setText(ev.getTitre().get());
        labelTitre.setFont(new Font(60));
        labelEmplacement.setText(ev.getEmplacement().get());
        labelPrix.setText("" + ev.getPrix().get());
        labelDate.setText(ev.getDate_event().get());
        labelFondation.setText(ev.getFondation().get());
        labelDuree.setText(ev.getDuree().get());
        labelParticipant.setText("" + ev.getNbMaxParticipants().get());
        Image img = new Image(ev.getImage_event().get());
        Circle c = new Circle(90, 90, 120);
        c.setFill(new ImagePattern(img));
        imagePane.getChildren().add(c);
    }

    @FXML
    private void handleAccueilBtn(ActionEvent event) {
    }

    @FXML
    private void HandleAccueilBtn(ActionEvent event) {
    }

    @FXML
    private void handleTestQuizBtn(ActionEvent event) {
    }

    @FXML
    private void handleChatBtn(ActionEvent event) {
    }

    @FXML
    private void handleDeconnectBtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Authentification.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(
                    new Scene(loader.load())
            );
            stage.setTitle("E-SENPAI | E-Learning Platform");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icon.png")));
            stage.setResizable(false);

            AuthentificationController controller = loader.getController();
            controller.changeConnected();

            Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            oldStage.close();

            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleEvenement(ActionEvent event) {
        
        try {
            if ("Admin".equals(this.currentUser.getRole().get())) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Evenement.fxml"));
                Scene scene = new Scene(loader.load());
                EvenementController controller = loader.getController();
                controller.initData(this.currentUser);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } else if ("Etudiant".equals(this.currentUser.getRole().get()) || "Formateur".equals(this.currentUser.getRole().get())) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Participation.fxml"));
                Scene scene = new Scene(loader.load());
                ParticipationController controller = loader.getController();
                controller.initData(this.currentUser);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
        } catch (IOException ex) {
            Logger.getLogger(AuthentificationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
