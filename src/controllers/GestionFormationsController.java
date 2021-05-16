/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.FormationService;
import services.UserService;

/**
 * FXML Controller class
 *
 * @author Dahmani
 */
public class GestionFormationsController implements Initializable {

    @FXML
    private SplitPane splitPane;
    @FXML
    private AnchorPane leftPane;
    @FXML
    private Button formationsBtn;
    @FXML
    private Button deconnectBtn;
    User currentUser;
    @FXML
    private AnchorPane gestionUserPane;
    @FXML
    private TextField formationInput;
    @FXML
    private Button chercherFormationBtn;
    @FXML
    private Button afficherBtn;
    @FXML
    private Label welcomeLabel;
    @FXML
    private VBox Vfomation;
    @FXML
    private ScrollPane ScrollFormationId;
    @FXML
    private GridPane GridFormationId;
    @FXML
    private GridPane GridFormationsearchId;
    @FXML
    private Button reclamationsBtn;
    @FXML
    private Button testsBtn;
    @FXML
    private Button postsBtn;
    @FXML
    private Button blogsBtn;
    @FXML
    private Button usersBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

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
        String texte = formationInput.getText();
        if (texte.equals("")) {
            handleAfficherBtn(event);
        } else {
            FormationService fs = FormationService.getInstance();
            List<Formation> formations = fs.getFormationByTitre(texte);
            UserService us = UserService.getInstance();
            List<User> users = us.getAllUsers();
            if (formations.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("INCORRECT NOM FORMATION");
                alert.setHeaderText(null);
                alert.setContentText("PAS DU FORMATION AVEC CE NOM");
                alert.showAndWait();
            } else {
                int column = 0;
                int row = 1;
                try {
                    for (Formation f : formations) {
                        for (User u : users) {
                            if (u.getId().get() == f.getId_formateur().get()) {
                                FXMLLoader fXMLLoader = new FXMLLoader();
                                fXMLLoader.setLocation(getClass().getResource("/views/CardGestionFormation.fxml"));
                                VBox vbox1 = fXMLLoader.load();
                                CardGestionFormationController cardGestionFormationController = fXMLLoader.getController();
                                cardGestionFormationController.setData(f, u, currentUser);
                                if (column == 3) {
                                    column = 0;
                                    ++row;
                                }
                                GridFormationId.getChildren().clear();
                                GridFormationId.add(vbox1, column++, row);
                                GridPane.setMargin(vbox1, new Insets(10));

                            }
                        }
                    }
                } catch (IOException e) {
                    Logger.getLogger(AuthentificationController.class.getName()).log(Level.SEVERE, null, e);

                }
            }
        }
    }

    @FXML
    private void handleAfficherBtn(ActionEvent event) {
        FormationService fs = FormationService.getInstance();
        List<Formation> formations = fs.getAllFormation();
        UserService us = UserService.getInstance();
        List<User> users = us.getAllUsers();
        int column = 0;
        int row = 1;
        try {
            for (Formation f : formations) {
                for (User u : users) {
                    if (u.getId().get() == f.getId_formateur().get()) {
                        FXMLLoader fXMLLoader = new FXMLLoader();
                        fXMLLoader.setLocation(getClass().getResource("/views/CardGestionFormation.fxml"));
                        VBox vbox = fXMLLoader.load();
                        CardGestionFormationController cardGestionFormationController = fXMLLoader.getController();
                        cardGestionFormationController.setData(f, u, currentUser);
                        if (column == 3) {
                            column = 0;
                            ++row;
                        }
                        GridFormationId.add(vbox, column++, row);
                        GridPane.setMargin(vbox, new Insets(10));

                    }
                }
            }
        } catch (IOException e) {
        }
    }

    public void initDataa(User u) {
        this.currentUser = u;
        System.out.println(currentUser.getRole().get());
        
        FormationService fs = FormationService.getInstance();
        List<Formation> formations = fs.getAllFormation();
        UserService us = UserService.getInstance();
        List<User> users = us.getAllUsers();
        int column = 0;
        int row = 1;
        try {
            for (Formation f : formations) {
                for (User ur : users) {
                    if (ur.getId().get() == f.getId_formateur().get()) {
                        FXMLLoader fXMLLoader = new FXMLLoader();
                        fXMLLoader.setLocation(getClass().getResource("/views/CardGestionFormation.fxml"));
                        VBox vbox = fXMLLoader.load();
                        CardGestionFormationController cardGestionFormationController = fXMLLoader.getController();
                        cardGestionFormationController.setData(f, ur , currentUser);
                        if (column == 3) {
                            column = 0;
                            ++row;
                        }
                        GridFormationId.add(vbox, column++, row);
                        GridPane.setMargin(vbox, new Insets(10));

                    }
                }
            }
        } catch (IOException e) {
        }
        
    }

    @FXML
    private void handleFormationsBtn(ActionEvent event) {

    }

    @FXML
    private void handleReclamationsBtn(ActionEvent event) {
    }

    @FXML
    private void handleTestsBtn(ActionEvent event) {
    }

    @FXML
    private void handlePostsBtn(ActionEvent event) {
    }

    @FXML
    private void handleBlogsBtn(ActionEvent event) {
    }

    @FXML
    private void handleUsersBtn(ActionEvent event) {
    }
}
