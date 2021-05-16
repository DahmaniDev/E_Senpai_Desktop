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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import services.EvenementService;

/**
 * FXML Controller class
 *
 * @author hiche
 */
public class EvenementController implements Initializable {

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
    private TableColumn<Evenement, Image> columnImage;
    @FXML
    private Button btnAfficherTous;
    @FXML
    private Button btnSupprimer;
    @FXML
    private Button btnAjouter;
    @FXML
    private Button btnModifier;
    @FXML
    private TextField eventInput;
    @FXML
    private Button btnChercher;
    private ObservableList<Evenement> list = FXCollections.observableArrayList();
    @FXML
    private Button btnEvenement;
    @FXML
    private TableColumn<Evenement, String> columnAction;
    private User currentUser;
    @FXML
    private Button btnConsulter;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        EvenementService es = EvenementService.getInstance();
        list = es.getAllEvenementObservable();
        tableEvent.setItems(list);
        columnTitre.setCellValueFactory(cell -> cell.getValue().getTitre());
        columnEmplacement.setCellValueFactory(cell -> cell.getValue().getEmplacement());
        columnPrix.setCellValueFactory(cell -> cell.getValue().getPrix().asString());
        columnDate.setCellValueFactory(cell -> cell.getValue().getDate_event());
        columnFondation.setCellValueFactory(cell -> cell.getValue().getFondation());
        columnNbMax.setCellValueFactory(cell -> cell.getValue().getNbMaxParticipants().asString());
        columnDuree.setCellValueFactory(cell -> cell.getValue().getDuree());
        /*  Callback<TableColumn<Evenement, String>, TableCell<Evenement, String>> cellFactory
                = (param) -> {
                    final TableCell<Evenement, String> cell = new TableCell<Evenement, String>() {

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button buttonMod = new Button("Modifier");
                        buttonMod.setOnAction(event -> {
                            Evenement f = getTableView().getItems().get(getIndex());
                        });
                        setGraphic(buttonMod);
                    }
                }

            };
                    return cell;
                };
        columnAction.setCellFactory(cellFactory);*/

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
    private void handleButtonChercher(ActionEvent event) {
        EvenementService es = EvenementService.getInstance();
        list = es.getAllEvenementsByTitre(eventInput.getText());
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
    private void handleAjouter(ActionEvent event) {
        try {

            Parent page1 = FXMLLoader.load(getClass().getResource("/views/AjoutEvenement.fxml"));
            Scene scene = new Scene(page1);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(EvenementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleSupprimer(ActionEvent event) {
        // int  idSelected = list.get(tableEvent.getSelectionModel().getSelectedIndex()).getId().get();
        Evenement E = tableEvent.getSelectionModel().getSelectedItem();
        EvenementService es = EvenementService.getInstance();
        //Evenement E=new Evenement();
        E = es.getEvenementById(E.getId().get());//idSelected);
        tableEvent.getItems().remove(E);
        es.deleteEvenementById(E.getId().get());

    }

    public void initData(User u) {
        this.currentUser = u;

    }

    @FXML
    private void handleAfficherTous(ActionEvent event) {
        EvenementService es = EvenementService.getInstance();
        list = es.getAllEvenementObservable();
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
    private void handleModifier(ActionEvent event) {
        Evenement E = tableEvent.getSelectionModel().getSelectedItem();
        EvenementService es = EvenementService.getInstance();
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ModifierEvenement.fxml"));
            Scene scene = new Scene(loader.load());
            ModifierEvenementController controller = loader.getController();
            controller.initData(E);
            controller.initDataUser(this.currentUser);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AuthentificationController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void handleConsulter(ActionEvent event) {
        Evenement E = tableEvent.getSelectionModel().getSelectedItem();
        EvenementService es = EvenementService.getInstance();
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ConsulterEvenement.fxml"));
            Scene scene = new Scene(loader.load());
            ConsulterEvenementController controller = loader.getController();
            controller.initData(E,this.currentUser);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AuthentificationController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
