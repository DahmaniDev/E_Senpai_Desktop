/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.User;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.io.FileUtils;
import services.UserService;

/**
 * FXML Controller class
 *
 * @author Dahmani
 */
public class InscriptionController implements Initializable {

    @FXML
    private Button inscrireBtn;
    @FXML
    private Hyperlink authentifierBtn;
    @FXML
    private RadioButton rbHomme;
    @FXML
    private ToggleGroup rbGender;
    @FXML
    private RadioButton rbFemme;

    String sexe = "Homme";

    @FXML
    private DatePicker inputDateN;
    @FXML
    private TextField inputNom;
    @FXML
    private TextField inputPrenom;
    @FXML
    private TextField inputEmail;
    @FXML
    private TextField inputLogin;
    @FXML
    private TextField inputPassword;
    @FXML
    private TextField inputVPassword;
    @FXML
    private CheckBox chkFormateur;
    @FXML
    private Label erreurLabel;
    @FXML
    private Button importCV;

    String fileName="";
    String fcs="";
    @FXML
    private Label urllabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        importCV.setDisable(true);
    }

    @FXML
    private void handleInscrireBt(ActionEvent event) throws NoSuchAlgorithmException {

        LocalDate dateN = inputDateN.getValue();

        if (inputNom.getText().equals("") || inputPrenom.getText().equals("") || dateN.toString().equals("") || inputEmail.getText().equals("") || inputLogin.getText().equals("") || inputPassword.getText().equals("") || inputVPassword.getText().equals("")) {
            erreurLabel.setText("Vérifier que tous les champs sont remplis !");
        } else {
            UserService us = UserService.getInstance();
            if (!us.isValid(inputEmail.getText())) {
                erreurLabel.setText("Vérifier votre adresse Mail !");
            } else {
                if (inputPassword.getText().equals(inputVPassword.getText())) {
                    User u = new User();

                    u.setNom(inputNom.getText());
                    u.setPrenom(inputPrenom.getText());
                    u.setSexe(sexe);
                    u.setDate_naissance(dateN.toString());
                    u.setEmail(inputEmail.getText());
                    u.setLogin(inputLogin.getText());
                    u.setPassword(inputPassword.getText());

                    if (chkFormateur.isSelected()) {
                        if (fcs.equals("")) {
                            erreurLabel.setText("Veuillez importer votre CV");
                        } else {
                            if (us.verifLogin(u.getLogin().get())) {
                                if (us.verifEmail(u.getEmail().get())) {
                                    File source = new File(fcs);
                                    File dest = new File(System.getProperty("user.dir") + "\\src\\assets\\" + fileName);
                                    String url = "/assets/" + fileName;
                                    if (!dest.exists()) {
                                        try {
                                            FileUtils.copyFile(source, dest);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    u.setCv(url);
                                    us.insertFormateur(u);
                                    try {
                                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/DialogWaitApproval.fxml"));
                                        Stage stage = new Stage(StageStyle.DECORATED);
                                        stage.setScene(
                                                new Scene(loader.load())
                                        );
                                        stage.setTitle("E-SENPAI | E-Learning Platform");
                                        stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icon.png")));
                                        stage.setResizable(false);

                                        stage.show();

                                    } catch (IOException ex) {
                                        Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                } else {
                                    erreurLabel.setText("E-mail existe déja");
                                }
                            } else {
                                erreurLabel.setText("Login existe déja");
                            }
                        }

                    } else {
                        if (us.verifLogin(u.getLogin().get())) {
                            if (us.verifEmail(u.getEmail().get())) {
                                us.insertEtudiant(u);
                                try {
                                    User userTmp = us.getUsetByLogin(u.getLogin().get());
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Accueil.fxml"));
                                    Stage stage = new Stage(StageStyle.DECORATED);
                                    stage.setScene(
                                            new Scene(loader.load())
                                    );
                                    stage.setTitle("E-SENPAI | E-Learning Platform");
                                    stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icon.png")));
                                    stage.setResizable(false);

                                    AccueilController controller = loader.getController();
                                    controller.initData(userTmp);

                                    Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                    oldStage.close();

                                    stage.show();

                                } catch (IOException ex) {
                                    Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else {
                                erreurLabel.setText("E-mail existe déja");
                            }
                        } else {
                            erreurLabel.setText("Login existe déja");
                        }
                    }
                } else {
                    erreurLabel.setText("Vérifier votre mot de passe !");
                }
            }
        }
    }

    @FXML
    private void handleAuthentifierBtn(ActionEvent event) {

        try {
            Parent page1 = FXMLLoader.load(getClass().getResource("/views/Authentification.fxml"));
            Scene scene = new Scene(page1);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(InscriptionController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void genderSelected(ActionEvent event) {
        if (rbHomme.isSelected()) {
            sexe = "Homme";
        }
        if (rbFemme.isSelected()) {
            sexe = "Femme";
        }
    }

    @FXML
    private void handleImportCv(ActionEvent event) {

        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Fichier PDF", "*.pdf"));
        File SelectedFile = fc.showOpenDialog(null);
        if (SelectedFile != null) {

            fcs = SelectedFile.toString();
            int index = fcs.lastIndexOf('\\');
            if (index > 0) {
                fileName = fcs.substring(index + 1);
            }
            urllabel.setText(fileName);

        }

    }

    @FXML
    private void formateurSelected(ActionEvent event) {
        if (chkFormateur.isSelected()) {
            importCV.setDisable(false);
        } else {
            importCV.setDisable(true);
        }
    }

}
