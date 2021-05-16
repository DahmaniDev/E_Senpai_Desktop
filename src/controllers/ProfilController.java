/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.User;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.UserService;

/**
 * FXML Controller class
 *
 * @author Dahmani
 */
public class ProfilController implements Initializable {

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
    private AnchorPane profilPane;
    @FXML
    private Label nameLabel;
    @FXML
    private Label bioLabel;
    @FXML
    private ImageView editBtn;

    private User currentUser;

    @FXML
    private Button reclamationsBtn;
    @FXML
    private AnchorPane MyProfil;
    @FXML
    private Button mesPostsBtn;
    @FXML
    private AnchorPane mesPostsPane;
    @FXML
    private VBox postsVbox;
    @FXML
    private Button ajouterPostBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MyProfil.setVisible(true);
        mesPostsPane.setVisible(false);
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
    private void handleEditBtn(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EditProfil.fxml"));

            Scene scene = new Scene(loader.load());

            EditProfilController controller = loader.getController();
            controller.initData(currentUser);

            Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            oldStage.setScene(scene);
            oldStage.show();

        } catch (IOException ex) {
            Logger.getLogger(ProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initData(User u) throws FileNotFoundException {
        this.currentUser = u;
        UserService us = UserService.getInstance();
        User ur = us.getUserById(u.getId().get());
        nameLabel.setText(currentUser.getNom().get().toUpperCase());
        bioLabel.setText(currentUser.getBiography().get());
        if (!ur.getPhoto_profil().get().equals("")) {
            //Image img = new Image(ur.getPhoto_profil().get());
            int index = ur.getPhoto_profil().get().lastIndexOf("/");
            String filename = "";
            if(index>0){
                filename = ur.getPhoto_profil().get().substring(index+1);
            }
            File img = new File(System.getProperty("user.dir") + "\\src\\assets\\" +filename);
            Circle clip = new Circle(90, 90, 50);
            InputStream isImage = (InputStream) new FileInputStream(img);
            clip.setFill(new ImagePattern(new Image(isImage)));
            profilPane.getChildren().add(clip);
        }
        else{
            Image img = new Image("/assets/userRandom.png");
            Circle clip = new Circle(90, 90, 50);
            clip.setFill(new ImagePattern(img));
            profilPane.getChildren().add(clip);
        }
        this.currentUser=ur;

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
    private void handleMesPostsBtn(ActionEvent event) {
        MyProfil.setVisible(false);
        mesPostsPane.setVisible(true);
    }

    @FXML
    private void handleAjouterPost(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/post.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(
                new Scene(loader.load())
        );
        stage.setTitle("E-SENPAI | E-Learning Platform");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icon.png")));
        stage.setResizable(false);

        PostController controller = loader.getController();
        controller.initData(currentUser,"","","Ajouter",0);

        stage.show();
    }

}
