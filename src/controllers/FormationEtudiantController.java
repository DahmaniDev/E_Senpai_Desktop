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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.AbonnementService;
import services.FormationService;
import services.UserService;

/**
 * FXML Controller class
 *
 * @author wajdi
 */
public class FormationEtudiantController implements Initializable {

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
    private AnchorPane rightPane;
    @FXML
    private AnchorPane gestionUserPane;
    @FXML
    private TextField formationInput;
    @FXML
    private Button chercherFormationBtn;
    @FXML
    private Button afficherBtn;
    @FXML
    private VBox Vfomation;
    @FXML
    private ScrollPane ScrollFormationId;
    @FXML
    private GridPane GridFormationId;
    @FXML
    private GridPane GridFormationsearchId;
    User currentUser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleAccueilBtn(ActionEvent event) {
          try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/Accueil.fxml"));
            Parent page2 = loader.load();
            AccueilController controller = loader.getController();
            controller.initData(currentUser);
            Scene scene = new Scene(page2);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);

            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AuthentificationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleProfilBtn(ActionEvent event) {
    }

    @FXML
    private void HandleFormationsBtn(ActionEvent event) {
         if (currentUser.getRole().get().toLowerCase().equals("formateur")) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/views/Formation.fxml"));
                Parent page2 = loader.load();
                FormationController controller = loader.getController();
                controller.initDataa(currentUser);
                Scene scene = new Scene(page2);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);

                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(AuthentificationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (currentUser.getRole().get().toLowerCase().equals("etudiant")) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/views/Formation.fxml"));
                Parent page2 = loader.load();
                FormationController controller = loader.getController();
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
    private void handleChercherBtn(ActionEvent event) {
    }

    @FXML
    private void handleAfficherBtn(ActionEvent event) {
    }

    public void initDataa(User u) {
        this.currentUser = u;
        FormationService fs = FormationService.getInstance();
        UserService us = UserService.getInstance();
        AbonnementService as = AbonnementService.getInstance();
        List<Abonnement> abonnements = as.getAllaAbonByIdUser(currentUser.getId().get());
        List<Formation> formations = fs.getAllFormation();
        List<User> users = us.getAllUsers();
        int column = 0;
        int row = 1;
        try {
            for (Abonnement a : abonnements) {
                for (Formation f : formations) {
                    if (a.getId_etudiant().get() == currentUser.getId().get() && a.getId_formation().get() == f.getId().get()) {
                        for (User ur : users) {
                            if (ur.getId().get() == f.getId_formateur().get()) {
                                FXMLLoader fXMLLoader = new FXMLLoader();
                                fXMLLoader.setLocation(getClass().getResource("/views/CardFormationsEtudiant.fxml"));
                                VBox vbox = fXMLLoader.load();
                                CardFormationsEtudiantController cardFormationsEtudiantController = fXMLLoader.getController();
                                cardFormationsEtudiantController.setData(f, ur , currentUser, a.getId_formation().get());
                                if (column == 3) {
                                    column = 0;
                                    ++row;
                                }
                                GridFormationId.add(vbox, column++, row);
                                GridPane.setMargin(vbox, new Insets(10));

                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
        }

    }
}
