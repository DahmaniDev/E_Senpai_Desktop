/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Evenement;
import entities.Participation;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.EvenementService;
import services.ParticipationService;

/**
 * FXML Controller class
 *
 * @author hiche
 */
public class ParticipationListController implements Initializable {

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
    private Button btnEvent;
    @FXML
    private TableView<Evenement> tableEvent;
    @FXML
    private TableColumn<Evenement, String> columnTitre;
    @FXML
    private TableColumn<Evenement, String> columnEmplacement;
    @FXML
    private TableColumn<Evenement, String> columnPrix;
    @FXML
    private TableColumn<Evenement, String> columnDate;
    @FXML
    private TableColumn<Evenement, String> columnFondation;
    @FXML
    private TableColumn<Evenement, String> columnNbMax;
    @FXML
    private TableColumn<Evenement, String> columnDuree;
    @FXML
    private TableColumn<Evenement, String> columnAction;
    private ObservableList<Participation> listp = FXCollections.observableArrayList();
    private ObservableList<Evenement> list = FXCollections.observableArrayList();

    private User currentUser;
    @FXML
    private Button btnAbondonner;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void initData(User u) {
        this.currentUser = u;
        EvenementService es = EvenementService.getInstance();
        ParticipationService ps = ParticipationService.getInstance();
        listp = ps.getAllParticipationObservableById(currentUser);
        for (int i = 0; i < listp.size(); i++) {
            Participation p = listp.get(i);
            list.add(es.getEvenementById(p.getId_event()));

        }

        tableEvent.setItems(list);
        columnTitre.setCellValueFactory(cell -> cell.getValue().getTitre());
        columnEmplacement.setCellValueFactory(cell -> cell.getValue().getEmplacement());
        columnPrix.setCellValueFactory(cell -> cell.getValue().getPrix().asString());
        columnDate.setCellValueFactory(cell -> cell.getValue().getDate_event());
        columnFondation.setCellValueFactory(cell -> cell.getValue().getFondation());
        columnNbMax.setCellValueFactory(cell -> cell.getValue().getNbMaxParticipants().asString());
        columnDuree.setCellValueFactory(cell -> cell.getValue().getDuree());

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
    private void handleEvent(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Participation.fxml"));
            Scene scene = new Scene(loader.load());
            ParticipationController controller = loader.getController();
            controller.initData(this.currentUser);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AuthentificationController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void handleAbondonner(ActionEvent event) {
        Evenement E = tableEvent.getSelectionModel().getSelectedItem();
        ParticipationService ps = ParticipationService.getInstance();
        Participation p = ps.getParticipationByIds(E, this.currentUser);
        ps.deleteParticipation(p);
    }

}
