/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Reclamation;
import entities.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.ReclamationsService;

/**
 * FXML Controller class
 *
 * @author Dahmani
 */
public class AjouterReclamationController implements Initializable {

    @FXML
    private SplitPane splitPane;
    @FXML
    private AnchorPane leftPane;
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
    private Button blogBtn;
    @FXML
    private Button deconnectBtn;
    @FXML
    private Button reclamationsBtn;
    @FXML
    private AnchorPane accueilPane;
    @FXML
    private TextArea contenuInput;
    @FXML
    private ComboBox<String> categorieCB;
    @FXML
    private Button envoyerBtn;
    @FXML
    private Button listeReclamationsBtn;

    private User currentUser;

    private String sujetSelected;
    @FXML
    private AnchorPane reclamationSent;
    @FXML
    private Button listeReclamationsBtn1;
    @FXML
    private AnchorPane addReclamationPane;
    @FXML
    private Label erreurLabel;
    @FXML
    private Button btnEvent;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categorieCB.setItems(FXCollections.observableArrayList(
                "Technique",
                "Signaler un utilisateur",
                "Problème d'accés",
                "Autres"
        ));
        categorieCB.valueProperty().addListener((obs, oldVal, newVal) -> sujetSelected = newVal);
        addReclamationPane.setVisible(true);
        reclamationSent.setVisible(false);
        erreurLabel.setVisible(false);
    }

    @FXML
    private void handleAccueilBtn(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Accueil.fxml"));

            Scene scene = new Scene(loader.load());

            AccueilController controller = loader.getController();
            controller.initData(currentUser);

            Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            oldStage.setScene(scene);
            oldStage.show();

        } catch (IOException ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleProfilBtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Profil.fxml"));

            Scene scene = new Scene(loader.load());

            ProfilController controller = loader.getController();
            controller.initData(currentUser);

            Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            oldStage.setScene(scene);
            oldStage.show();

        } catch (IOException ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void HandleFormationsBtn(ActionEvent event) {
    }

    @FXML
    private void handleTestQuizBtn(ActionEvent event) {
    }

    @FXML
    private void handleChatBtn(ActionEvent event) {
    }

    @FXML
    private void handleBlogBtn(ActionEvent event) {
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
    private void handleReclamationsBtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AjouterReclamation.fxml"));

            Scene scene = new Scene(loader.load());

            AjouterReclamationController controller = loader.getController();
            controller.initData(currentUser);

            Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            oldStage.setScene(scene);
            oldStage.show();

        } catch (IOException ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleEnvoyerBtn(ActionEvent event) {

        if (sujetSelected == null || contenuInput.getText().equals("")) {
            erreurLabel.setVisible(true);
        } else {
            Reclamation r = new Reclamation();
            r.setSujet(sujetSelected);
            r.setIdUser(currentUser.getId().get());
            r.setContenu(contenuInput.getText());
            ReclamationsService rs = ReclamationsService.getInstance();
            rs.insertReclamation(r);
            addReclamationPane.setVisible(false);
            reclamationSent.setVisible(true);
        }

    }

    @FXML
    private void handleListeReclamationsBtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MesReclamations.fxml"));

            Scene scene = new Scene(loader.load());

            MesReclamationsController controller = loader.getController();
            controller.initData(currentUser);

            Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            oldStage.setScene(scene);
            oldStage.show();

        } catch (IOException ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initData(User u) {
        this.currentUser = u;
    }

    @FXML
    private void handleEvent(ActionEvent event) {
    }

}
