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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import services.AbonnementService;
import services.FormationService;
import services.ListData;
import services.UserService;

/**
 * FXML Controller class
 *
 * @author wajdi
 */
public class FormationController implements Initializable {

    private ListData list = new ListData();
    private ListData lists = new ListData();
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
    private SplitPane splitPane;
    @FXML
    private AnchorPane leftPane;
    @FXML
    private Button blogBtn;
    @FXML
    private AnchorPane rightPane;
    @FXML
    private TextField formationInput;
    User currentUser;
    @FXML
    private Label welcomeLabel;
    @FXML
    private GridPane GridFormationId;
    @FXML
    private AnchorPane gestionUserPane;
    @FXML
    private Button chercherFormationBtn;
    @FXML
    private Button afficherBtn;
    @FXML
    private ImageView mesformationsbtn;
    @FXML
    private VBox Vfomation;
    @FXML
    private ScrollPane ScrollFormationId;
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
    private void handleProfilBtn(ActionEvent event) {
    }

    @FXML
    private void HandleFormationsBtn(ActionEvent event) {
    }

    @FXML
    private void handleBlogBtn(ActionEvent event) {
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
            if (formations.size() == 0) {
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
                                fXMLLoader.setLocation(getClass().getResource("/views/CardFormation.fxml"));
                                VBox vbox1 = fXMLLoader.load();
                                CardFormationController cardFormationController = fXMLLoader.getController();
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
                        fXMLLoader.setLocation(getClass().getResource("/views/CardFormation.fxml"));
                        VBox vbox = fXMLLoader.load();
                        CardFormationController cardFormationController = fXMLLoader.getController();
                        cardFormationController.setData(f, u);
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
    private void handleMesFormation(MouseEvent event) {
        if (currentUser.getRole().get().toLowerCase().equals("formateur")) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/views/FormationFormateur.fxml"));
                Parent page2 = loader.load();
                FormationFormateurController controller = loader.getController();
                controller.initDataa(currentUser);
                Scene scene = new Scene(page2);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);

                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(AuthentificationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/views/FormationEtudiant.fxml"));
                Parent page2 = loader.load();
                FormationEtudiantController controller = loader.getController();
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

        
    public void initDataa(User ur) {
        this.currentUser = ur;
        welcomeLabel.setText("Bienvenue Mr " + currentUser.getNom().get());
        FormationService fs = FormationService.getInstance();
        List<Formation> formations = fs.getAllFormation();
        UserService us = UserService.getInstance();
        List<User> users = us.getAllUsers();
        int column = 0;
        int row = 1;
        if (currentUser.getRole().get().toLowerCase().equals("formateur")) {
            try {

                for (Formation f : formations) {
                    for (User u : users) {
                        if (u.getId().get() == f.getId_formateur().get()) {
                            FXMLLoader fXMLLoader = new FXMLLoader();
                            fXMLLoader.setLocation(getClass().getResource("/views/CardFormation.fxml"));
                            VBox vbox = fXMLLoader.load();
                            CardFormationController cardFormationController = fXMLLoader.getController();
                            cardFormationController.setData(f, u);
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
        } else {
            try {
                for (Formation f : formations) {
                    for (User u : users) {
                        if (u.getId().get() == f.getId_formateur().get()) {
                            int i = 0;
                            
                            FXMLLoader fXMLLoader = new FXMLLoader();
                            fXMLLoader.setLocation(getClass().getResource("/views/CardFormationsEtudiant.fxml"));
                            VBox vbox = fXMLLoader.load();
                            CardFormationsEtudiantController cardFormationsEtudiantController = fXMLLoader.getController();
                            cardFormationsEtudiantController.setData(f, u, currentUser, i);
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
    }

}
