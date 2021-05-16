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
import static java.util.Collections.list;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import services.FormationService;
import services.ListData;
import services.UserService;

/**
 * FXML Controller class
 *
 * @author wajdi
 */
public class FormationFormateurController implements Initializable {

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
    private TextField formationInput;
    private TableView<Formation> formationTable;
    private TableColumn<Formation, String> idColumn;
    private TableColumn<Formation, String> NomFormateurColumn;
    private TableColumn<Formation, String> titreColumn;
    private TableColumn<Formation, String> noteColumn;
    private TableColumn<Formation, String> descriptionColumn;
    private TableColumn<Formation, String> actionColumn;
    User currentUser;
    private ListData list = new ListData();
    @FXML
    private AnchorPane gestionUserPane;
    @FXML
    private Button chercherFormationBtn;
    @FXML
    private Button afficherBtn;
    @FXML
    private Button AjouterFormationbtn;
    @FXML
    private VBox Vfomation;
    @FXML
    private ScrollPane ScrollFormationId;
    @FXML
    private GridPane GridFormationId;
    @FXML
    private GridPane GridFormationsearchId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

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
        String texte = formationInput.getText();

        if (texte.equals("")) {
            handleAfficherBtn(event);
        } else {
            FormationService fs = FormationService.getInstance();
            List<Formation> formations = fs.getFormationUserByTitreObservable(texte, currentUser.getId().get());
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
                                fXMLLoader.setLocation(getClass().getResource("/views/CardFormationFormateur.fxml"));
                                VBox vbox1 = fXMLLoader.load();
                                CardFormationFormateurController cardFormationController = fXMLLoader.getController();
                                cardFormationController.setData(f, u);
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

    public void initDataa(User u) {
        this.currentUser = u;
        FormationService fs = FormationService.getInstance();
        List<Formation> formations = fs.getFormationByIdFormateur(currentUser.getId().get());
        UserService us = UserService.getInstance();
        List<User> users = us.getAllUsers();
        int column = 0;
        int row = 1;
        try {
            for (Formation f : formations) {
                for (User ur : users) {
                    if (ur.getId().get() == f.getId_formateur().get()) {
                        FXMLLoader fXMLLoader = new FXMLLoader();
                        fXMLLoader.setLocation(getClass().getResource("/views/CardFormationFormateur.fxml"));
                        VBox vbox = fXMLLoader.load();
                        CardFormationFormateurController cardFormationController = fXMLLoader.getController();
                        cardFormationController.setData(f, ur);
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
    private void handleAfficherBtn(ActionEvent event) {
        FormationService fs = FormationService.getInstance();
        List<Formation> formations = fs.getFormationByIdFormateur(currentUser.getId().get());
        UserService us = UserService.getInstance();
        List<User> users = us.getAllUsers();
        int column = 0;
        int row = 1;
        try {
            for (Formation f : formations) {
                for (User ur : users) {
                    if (ur.getId().get() == f.getId_formateur().get()) {
                        FXMLLoader fXMLLoader = new FXMLLoader();
                        fXMLLoader.setLocation(getClass().getResource("/views/CardFormationFormateur.fxml"));
                        VBox vbox = fXMLLoader.load();
                        CardFormationFormateurController cardFormationController = fXMLLoader.getController();
                        cardFormationController.setData(f, ur);
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
    private void handleAjouterFormation(ActionEvent event) {
        try {
            FormationService fs = FormationService.getInstance();
            Formation f = new Formation();
            f.setId_formateur(currentUser.getId().get());
            f.setTitre("");
            f.setNote(0);
            f.setDescription("");
            fs.insertFormation(f);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/AjouterFormation.fxml"));
            Parent page2 = loader.load();
            AjouterFormationController controller = loader.getController();
            controller.initDataa(currentUser, f);
            Scene scene = new Scene(page2);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);

            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AuthentificationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
