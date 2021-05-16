/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.AccueilController;
import controllers.AuthentificationController;
import entities.User;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.ListData;
import services.UserService;

/**
 * FXML Controller class
 *
 * @author Dahmani
 */
public class GestionUsersController implements Initializable {

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
    private TextField userInput;
    @FXML
    private Button chercherUserBtn;
    @FXML
    private Button ajouterAdminBtn;
    @FXML
    private Button suppUserBtn;
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, String> idColumn;
    @FXML
    private TableColumn<User, String> nomColumn;
    @FXML
    private TableColumn<User, String> prenomColumn;
    @FXML
    private TableColumn<User, String> dateColumn;
    @FXML
    private TableColumn<User, String> sexeColumn;
    @FXML
    private TableColumn<User, String> emailColumn;
    @FXML
    private TableColumn<User, String> loginColumn;
    @FXML
    private TableColumn<User, String> statutColumn;

    String sexe = "Homme";

    private ListData list = new ListData();
    @FXML
    private Button afficherBtn;
    @FXML
    private AnchorPane addAdminPane;
    @FXML
    private RadioButton hommeRb;
    @FXML
    private ToggleGroup gender;
    @FXML
    private RadioButton femmeRb;
    @FXML
    private AnchorPane gestionUserPane;
    @FXML
    private Hyperlink annulerBtn;
    @FXML
    private TextField nomTextField;
    @FXML
    private TextField prenomTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private DatePicker dateTextField;
    @FXML
    private TextField loginTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private PasswordField verfiPasswordTextField;
    @FXML
    private Button ajoutOk;
    @FXML
    private Label erreurLabel;

    int idSelected;

    String nomSelected;
    @FXML
    private TableColumn<User, String> roleColumn;
    @FXML
    private Label suppLabel;
    @FXML
    private Button demandesBtn;
    
    private User currentAdmin;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userTable.setItems(list.getUsers());
        nomColumn.setCellValueFactory(cell -> cell.getValue().getNom());
        prenomColumn.setCellValueFactory(cell -> cell.getValue().getPrenom());
        dateColumn.setCellValueFactory(cell -> cell.getValue().getDate_naissance());
        sexeColumn.setCellValueFactory(cell -> cell.getValue().getSexe());
        emailColumn.setCellValueFactory(cell -> cell.getValue().getEmail());
        loginColumn.setCellValueFactory(cell -> cell.getValue().getLogin());
        statutColumn.setCellValueFactory(cell -> cell.getValue().getStatus());
        idColumn.setCellValueFactory(cell -> cell.getValue().getId().asString());
        roleColumn.setCellValueFactory(cell -> cell.getValue().getRole());
        gestionUserPane.setVisible(true);
        addAdminPane.setVisible(false);
        idSelected=0;
        nomSelected="";
    }

    @FXML
    private void handleFormationsBtn(ActionEvent event) {
        
        try {
            Parent page1 = FXMLLoader.load(getClass().getResource("/views/GestionFormations.fxml"));
            Scene scene = new Scene(page1);
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
        try{
            Parent page1 = FXMLLoader.load(getClass().getResource("/views/Reclamations.fxml"));
            Scene scene = new Scene(page1);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }catch (IOException ex) {
            Logger.getLogger(AuthentificationController.class.getName()).log(Level.SEVERE, null, ex);
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
        gestionUserPane.setVisible(true);
        addAdminPane.setVisible(false);
    }

    @FXML
    private void handleChercherBtn(ActionEvent event) {
        String name = userInput.getText();
        ObservableList<User> usersFilter = FXCollections.observableArrayList();
        UserService us = UserService.getInstance();
        usersFilter = us.getUsersByNameObservable(name);
        userTable.getItems().clear();
        userTable.setItems(usersFilter);
        nomColumn.setCellValueFactory(cell -> cell.getValue().getNom());
        prenomColumn.setCellValueFactory(cell -> cell.getValue().getPrenom());
        dateColumn.setCellValueFactory(cell -> cell.getValue().getDate_naissance());
        sexeColumn.setCellValueFactory(cell -> cell.getValue().getSexe());
        emailColumn.setCellValueFactory(cell -> cell.getValue().getEmail());
        loginColumn.setCellValueFactory(cell -> cell.getValue().getLogin());
        statutColumn.setCellValueFactory(cell -> cell.getValue().getStatus());
        idColumn.setCellValueFactory(cell -> cell.getValue().getId().asString());
        roleColumn.setCellValueFactory(cell -> cell.getValue().getRole());

    }

    @FXML
    private void handleAjouterAdminBtn(ActionEvent event) {
        gestionUserPane.setVisible(false);
        addAdminPane.setVisible(true);
    }

    @FXML
    private void handleSuppUserBtn(ActionEvent event) {
        if (idSelected == 0 && nomSelected.equals("")) {
            suppLabel.setText("Sélectionner une ligne!");
            
        } else {
            User u = new User();
            u.setId(idSelected);
            u.setNom(nomSelected);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/SupprimerDialogBox.fxml"));
                Stage stage = new Stage(StageStyle.DECORATED);
                stage.setScene(
                        new Scene(loader.load())
                );
                stage.setTitle("E-SENPAI | E-Learning Platform");
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icon.png")));
                stage.setResizable(false);

                SupprimerDialogBoxController controller = loader.getController();
                controller.initData(u);

                stage.show();
                suppLabel.setText("");

            } catch (IOException ex) {
                Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
                suppLabel.setText("Sélectionner une ligne!");
            }
        }
    }

    @FXML
    private void handleAfficherBtn(ActionEvent event) {
        ObservableList<User> users=FXCollections.observableArrayList();
        UserService us=UserService.getInstance();
        users= us.displayAll();
        userTable.setItems(users);
        nomColumn.setCellValueFactory(cell -> cell.getValue().getNom());
        prenomColumn.setCellValueFactory(cell -> cell.getValue().getPrenom());
        dateColumn.setCellValueFactory(cell -> cell.getValue().getDate_naissance());
        sexeColumn.setCellValueFactory(cell -> cell.getValue().getSexe());
        emailColumn.setCellValueFactory(cell -> cell.getValue().getEmail());
        loginColumn.setCellValueFactory(cell -> cell.getValue().getLogin());
        statutColumn.setCellValueFactory(cell -> cell.getValue().getStatus());
        idColumn.setCellValueFactory(cell -> cell.getValue().getId().asString());
        userInput.setText("");
    }

    @FXML
    private void handleAnnulerBtn(ActionEvent event) {
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
        gestionUserPane.setVisible(true);
        addAdminPane.setVisible(false);
    }

    @FXML
    private void handleAjoutOk(ActionEvent event) throws NoSuchAlgorithmException {
        LocalDate dateN = dateTextField.getValue();

        if (nomTextField.getText().equals("") || prenomTextField.getText().equals("") || dateN.toString().equals("") || emailTextField.getText().equals("") || loginTextField.getText().equals("") || passwordTextField.getText().equals("") || verfiPasswordTextField.getText().equals("")) {
            erreurLabel.setText("Vérifier que tous les champs sont remplis !");
        } else {
            UserService us = UserService.getInstance();
            if (!us.isValid(emailTextField.getText())) {
                erreurLabel.setText("Vérifier l'adresse Mail !");
            } else {
                if (passwordTextField.getText().equals(verfiPasswordTextField.getText())) {
                    User u = new User();

                    u.setNom(nomTextField.getText());
                    u.setPrenom(prenomTextField.getText());
                    u.setSexe(sexe);
                    u.setDate_naissance(dateN.toString());
                    u.setEmail(emailTextField.getText());
                    u.setLogin(loginTextField.getText());
                    u.setPassword(passwordTextField.getText());
                    us.insertAdmin(u);
                    erreurLabel.setText("");
                } else {
                    erreurLabel.setText("Vérifier le mot de passe !");
                }
            }
        }
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
        gestionUserPane.setVisible(true);
        addAdminPane.setVisible(false);
    }

    @FXML
    private void genderSelected(ActionEvent event) {
        if (hommeRb.isSelected()) {
            sexe = "Homme";
        }
        if (femmeRb.isSelected()) {
            sexe = "Femme";
        }
    }

    @FXML
    private void handleUserTableMouseClicked(MouseEvent event) {
        idSelected=0;
        idSelected = list.getUsers().get(userTable.getSelectionModel().getSelectedIndex()).getId().get();
        nomSelected="";
        nomSelected = list.getUsers().get(userTable.getSelectionModel().getSelectedIndex()).getNom().get();
        
    }
    
    public void initData(User u){
        this.currentAdmin=u;
        userTable.setItems(list.getUsers());
        nomColumn.setCellValueFactory(cell -> cell.getValue().getNom());
        prenomColumn.setCellValueFactory(cell -> cell.getValue().getPrenom());
        dateColumn.setCellValueFactory(cell -> cell.getValue().getDate_naissance());
        sexeColumn.setCellValueFactory(cell -> cell.getValue().getSexe());
        emailColumn.setCellValueFactory(cell -> cell.getValue().getEmail());
        loginColumn.setCellValueFactory(cell -> cell.getValue().getLogin());
        statutColumn.setCellValueFactory(cell -> cell.getValue().getStatus());
        idColumn.setCellValueFactory(cell -> cell.getValue().getId().asString());
        roleColumn.setCellValueFactory(cell -> cell.getValue().getRole());
        gestionUserPane.setVisible(true);
        addAdminPane.setVisible(false);
    }

    @FXML
    private void handleDemandesbtn(ActionEvent event) {
        try{
            Parent page1 = FXMLLoader.load(getClass().getResource("/views/DemandesFormateurs.fxml"));
            Scene scene = new Scene(page1);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }catch (IOException ex) {
            Logger.getLogger(AuthentificationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
