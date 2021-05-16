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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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
public class EditProfilController implements Initializable {

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
    private TextField inputNom;
    @FXML
    private TextField inputPrenom;
    @FXML
    private DatePicker inputDateN;
    @FXML
    private RadioButton rbHomme;
    @FXML
    private ToggleGroup rbGender;
    @FXML
    private RadioButton rbFemme;
    @FXML
    private TextField inputEmail;
    @FXML
    private TextField inputLogin;
    @FXML
    private PasswordField inputPassword;
    @FXML
    private PasswordField inputVPassword;
    @FXML
    private Button modifierBtn;

    private User currentUser;

    String sexe;
    @FXML
    private AnchorPane modifierSucces;
    @FXML
    private Hyperlink retourBtn;
    @FXML
    private AnchorPane updateprofilPane;
    @FXML
    private Label erreurLabel;
    @FXML
    private TextArea bioInput;
    @FXML
    private TextField imageUrl;
    @FXML
    private Button importBtn;

    String fileName = "";
    String fcs = "";
    @FXML
    private Button reclamationsBtn;
    @FXML
    private TextField cvUrl;
    @FXML
    private Button importCvBtn;
    @FXML
    private Label cvLabel;

    String fileNameCv = "";
    String fcsCv = "";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rbFemme.setSelected(false);
        rbHomme.setSelected(false);
        ImageView x = new ImageView("assets/import.png");
        x.setFitHeight(10);
        x.setFitWidth(10);
        importBtn.setGraphic(x);
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
            UserService us = UserService.getInstance();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Profil.fxml"));

            Scene scene = new Scene(loader.load());

            ProfilController controller = loader.getController();
            User u = us.getUserById(currentUser.getId().get());
            controller.initData(u);

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
    private void handleModifierBtn(ActionEvent event) {
        if (inputNom.getText().equals("") || inputPrenom.getText().equals("") || inputDateN.toString().equals("") || inputEmail.getText().equals("") || inputLogin.getText().equals("") || inputPassword.getText().equals("") || inputVPassword.getText().equals("")) {
            erreurLabel.setText("Vérifier que tous les champs sont remplis !");
        } else {
            UserService us = UserService.getInstance();
            if (!us.isValid(inputEmail.getText())) {
                erreurLabel.setText("Vérifier votre adresse Mail !");
            } else {
                if (inputPassword.getText().equals(inputVPassword.getText())) {
                    currentUser.setNom(inputNom.getText());
                    currentUser.setPrenom(inputPrenom.getText());
                    currentUser.setLogin(inputLogin.getText());
                    currentUser.setDate_naissance(inputDateN.getValue().toString());
                    currentUser.setEmail(inputEmail.getText());
                    if (rbHomme.isSelected()) {
                        currentUser.setSexe("Homme");
                    } else {
                        currentUser.setSexe("Femme");
                    }
                    currentUser.setPassword(inputPassword.getText());
                    currentUser.setBiography(bioInput.getText());
                }
            }
            if (!fcs.equals("")) {
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
                currentUser.setPhoto_profil(url);
            }

            if (!fcsCv.equals("")) {
                if (currentUser.getRole().get().toLowerCase().equals("formateur")) {
                    File sourceCv = new File(fcsCv);
                    File destCv = new File(System.getProperty("user.dir") + "\\src\\assets\\" + fileNameCv);
                    String urlCv = "/assets/" + fileNameCv;
                    if (!destCv.exists()) {
                        try {
                            FileUtils.copyFile(sourceCv, destCv);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    currentUser.setCv(urlCv);
                }
            }

            us.modifierUser(currentUser);
            modifierSucces.setVisible(true);
            updateprofilPane.setVisible(false);
        }
    }

    public void initData(User u) {
        this.currentUser = u;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        inputNom.setText(currentUser.getNom().get());
        inputPrenom.setText(currentUser.getPrenom().get());
        inputDateN.setValue(LocalDate.parse(currentUser.getDate_naissance().get(), formatter));
        inputEmail.setText(currentUser.getEmail().get());
        inputLogin.setText(currentUser.getLogin().get());
        inputPassword.setText(currentUser.getPassword().get());
        inputVPassword.setText(currentUser.getPassword().get());
        bioInput.setText(currentUser.getBiography().get());
        int index = currentUser.getPhoto_profil().get().lastIndexOf('/');
        String xImg="";
        if (index > 0) {
            xImg = currentUser.getPhoto_profil().get().substring(index + 1);
        }
        imageUrl.setText(xImg);
        
        int indexCv = currentUser.getCv().get().lastIndexOf('/');
        String xCv="";
        if (indexCv > 0) {
            xCv = currentUser.getCv().get().substring(indexCv + 1);
        }
        cvUrl.setText(xCv);
        if (currentUser.getSexe().get().toLowerCase().equals("femme")) {
            rbFemme.setSelected(true);

        } else {

            rbHomme.setSelected(true);
        }
        ImageView x = new ImageView("assets/import.png");
        x.setFitHeight(10);
        x.setFitWidth(10);
        importCvBtn.setGraphic(x);
        if (currentUser.getRole().get().toLowerCase().equals("formateur")) {
            cvLabel.setVisible(true);
            cvUrl.setVisible(true);
            importCvBtn.setVisible(true);
        } else {
            cvLabel.setVisible(false);
            cvUrl.setVisible(false);
            importCvBtn.setVisible(false);
        }
    }

    @FXML
    private void hommeSelected(ActionEvent event) {
        rbFemme.setSelected(false);
    }

    @FXML
    private void femmeSelected(ActionEvent event) {
        rbHomme.setSelected(false);
    }

    @FXML
    private void handleRetourBtn(ActionEvent event) {

        try {
            UserService us = UserService.getInstance();
            User u = us.getUserById(currentUser.getId().get());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Profil.fxml"));

            Scene scene = new Scene(loader.load());

            ProfilController controller = loader.getController();
            controller.initData(u);

            Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            oldStage.setScene(scene);
            oldStage.show();

        } catch (IOException ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void handleImportBtn(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png"));
        File SelectedFile = fc.showOpenDialog(null);
        if (SelectedFile != null) {

            fcs = SelectedFile.toString();
            int index = fcs.lastIndexOf('\\');
            if (index > 0) {
                fileName = fcs.substring(index + 1);
            }
            imageUrl.setText(fileName);

        }

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
    private void handleImportCvBtn(ActionEvent event) {

        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Fichier PDF", "*.pdf"));
        File SelectedFile = fc.showOpenDialog(null);
        if (SelectedFile != null) {

            fcsCv = SelectedFile.toString();
            int index = fcsCv.lastIndexOf('\\');
            if (index > 0) {
                fileNameCv = fcsCv.substring(index + 1);
            }
            cvUrl.setText(fileNameCv);
        }
    }

}
