/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Session;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.ReclamationsService;
import services.UserService;

/**
 * FXML Controller class
 *
 * @author Dahmani
 */
public class DashboardController implements Initializable {

    @FXML
    private SplitPane splitPane;
    @FXML
    private AnchorPane leftPane;
    @FXML
    private Button formationsBtn;
    @FXML
    private Button deconnectBtn;
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
    @FXML
    private AnchorPane rightPane;
    @FXML
    private ImageView userImage;
    @FXML
    private Label welcomeLabel;

    User currentUser;

    @FXML
    private Label usersNumberLabel;
    @FXML
    private Label formationsNumberLabel;
    @FXML
    private Label testsNumberLabel;
    @FXML
    private Label eventsNumberLabel;
    @FXML
    private Label reclamationsNumberLabel;
    @FXML
    private Button eventsBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UserService us = UserService.getInstance();
        int x = us.countUsers();
        usersNumberLabel.setText("" + x);
        ReclamationsService rs = ReclamationsService.getInstance();

    }

    @FXML
    private void handleFormationsBtn(ActionEvent event) {

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Reclamations.fxml"));

            Scene scene = new Scene(loader.load());

            ReclamationsController controller = loader.getController();
            controller.initData(currentUser);

            Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            oldStage.setScene(scene);
            oldStage.show();

        } catch (IOException ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void handleTestsBtn(ActionEvent event) {
        try {

            if (currentUser.getRole().get().equals("Etudiant")) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/TestEtudiant.fxml"));
                Stage stage = new Stage(StageStyle.DECORATED);
                stage.setScene(
                        new Scene(loader.load())
                );
                stage.setTitle("E-SENPAI | E-Learning Platform");
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icon.png")));
                stage.setResizable(false);

                TestEtudiantController controller = loader.getController();
                controller.initData(currentUser);

                Session s = new Session();
                Session.setUser(currentUser);

                Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                oldStage.close();

                stage.show();
            } else if (currentUser.getRole().get().toLowerCase().equals("admin")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/TestQuizAdmin.fxml"));
                Stage stage = new Stage(StageStyle.DECORATED);
                stage.setScene(
                        new Scene(loader.load())
                );
                stage.setTitle("E-SENPAI | E-Learning Platform");
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icon.png")));
                stage.setResizable(false);

                TestQuizAdminController controller = loader.getController();
                controller.initData(currentUser);

                Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                oldStage.close();

                stage.show();
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Test.fxml"));
                Stage stage = new Stage(StageStyle.DECORATED);
                stage.setScene(
                        new Scene(loader.load())
                );
                stage.setTitle("E-SENPAI | E-Learning Platform");
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icon.png")));
                stage.setResizable(false);

                TestController controller = loader.getController();
                controller.initData(currentUser);

                Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                oldStage.close();

                stage.show();
            }
            // loader = new FXMLLoader(getClass().getResource("/views/Test.fxml"));

        } catch (IOException ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handlePostsBtn(ActionEvent event) {
    }

    @FXML
    private void handleBlogsBtn(ActionEvent event) {
    }

    @FXML
    private void handleUsersBtn(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/GestionUsers.fxml"));

            Scene scene = new Scene(loader.load());

            GestionUsersController controller = loader.getController();
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
        welcomeLabel.setText("Bienvenue Mr " + currentUser.getNom().get());

    }

    @FXML
    private void handleEvents(ActionEvent event) {
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
