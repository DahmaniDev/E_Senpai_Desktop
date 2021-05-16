/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.AuthentificationController;
import entities.Reclamation;
import entities.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.ReclamationsData;
import services.ReclamationsService;
import services.UserService;

/**
 * FXML Controller class
 *
 * @author Dahmani
 */
public class ReclamationsController implements Initializable {

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
    private TableView<Reclamation> reclamationsTable;
    @FXML
    private TableColumn<Reclamation, String> columnSujet;
    @FXML
    private TableColumn<Reclamation, String> columnContenu;
    @FXML
    private Button repondreButt;

    private User currentAdmin;

    int idSelected;

    private ReclamationsData list = new ReclamationsData();
    @FXML
    private TableColumn<Reclamation, String> columnStatut;
    @FXML
    private TableColumn<Reclamation, String> columnAdminTrait;
    @FXML
    private TableColumn<Reclamation, String> columnUser;
    @FXML
    private Label erreurLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UserService us = UserService.getInstance();
        reclamationsTable.setItems(list.getUsers());
        columnUser.setCellValueFactory(cell -> us.getUserById(cell.getValue().getIdUser().get()).getNom());
        columnSujet.setCellValueFactory(cell -> cell.getValue().getSujet());
        columnContenu.setCellValueFactory(cell -> cell.getValue().getContenu());
        columnStatut.setCellValueFactory(cell -> cell.getValue().getStatut());
        columnAdminTrait.setCellValueFactory(cell -> cell.getValue().getIdAdminTrait().asString());
    }

    @FXML
    private void handleFormationsBtn(ActionEvent event) {
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
            controller.initData(currentAdmin);

            Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            oldStage.setScene(scene);
            oldStage.show();

        } catch (IOException ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/GestionUsers.fxml"));

            Scene scene = new Scene(loader.load());

            GestionUsersController controller = loader.getController();
            controller.initData(currentAdmin);

            Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            oldStage.setScene(scene);
            oldStage.show();

        } catch (IOException ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleReclamationTableMouseClicked(MouseEvent event) {
        idSelected = list.getUsers().get(reclamationsTable.getSelectionModel().getSelectedIndex()).getId().get();
    }

    @FXML
    private void handleRepondreButt(ActionEvent event) {
        if (idSelected != 0) {
            ReclamationsService rs = ReclamationsService.getInstance();
            Reclamation r = rs.getReclamationById(idSelected);
            if (r.getStatut().get().toLowerCase().equals("traitée")) {
                erreurLabel.setText("Réclamation déja traitée");
            } else {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ReplyReclamation.fxml"));

                    Scene scene = new Scene(loader.load());

                    ReplyReclamationController controller = loader.getController();
                    controller.initData(currentAdmin, r);

                    Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    oldStage.setScene(scene);
                    oldStage.show();

                } catch (IOException ex) {
                    Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } else {
            erreurLabel.setText("Sélectionner une réclamation");
        }

    }

    public void initData(User u) {
        this.currentAdmin = u;
    }

}
