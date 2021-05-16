/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Evenement;
import entities.Participation;
import entities.User;
import java.io.File;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.io.FileUtils;
import services.EvenementService;
import services.Mailing;
import services.ParticipationService;
import services.UserService;

/**
 * FXML Controller class
 *
 * @author hiche
 */
public class ModifierEvenementController implements Initializable {

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
    private TextField textPrix;
    @FXML
    private TextField textEmplacement;
    @FXML
    private TextField textTitre;
    @FXML
    private TextField textDate;
    @FXML
    private TextField textFondation;
    @FXML
    private TextField textNbMax;
    @FXML
    private TextField textDuree;
    @FXML
    private Button btnAnnuler;
    @FXML
    private Button btnAjout;

    private String fcs;
    private String fileName;
    private Evenement ev;
    private User currentUser;
    ObservableList<Participation> list = FXCollections.observableArrayList();
    ParticipationService ps = ParticipationService.getInstance();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void initData(Evenement E) {

        this.ev = E;
        textTitre.setText(this.ev.getTitre().get());
        textEmplacement.setText(this.ev.getEmplacement().get());
        textFondation.setText(this.ev.getFondation().get());
        textNbMax.setText("" + this.ev.getNbMaxParticipants().get());
        textDate.setText(this.ev.getDate_event().get());
        textDuree.setText(this.ev.getDuree().get());
        textPrix.setText("" + this.ev.getPrix().get());

    }

    public void initDataUser(User u) {
        this.currentUser = u;
        this.list = ps.getAllParticipationByEvenementId(this.ev);

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
    }

    @FXML
    private void HandleAnnuler(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Evenement.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AuthentificationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleModifier(ActionEvent event) throws Exception {
        EvenementService es = EvenementService.getInstance();
        Evenement e = new Evenement();
        e.setId(this.ev.getId().get());
        e.setTitre(textTitre.getText());
        e.setEmplacement(textEmplacement.getText());
        e.setPrix(Integer.parseInt(textPrix.getText()));
        e.setFondation(textFondation.getText());
        e.setDate_event(textDate.getText());

        e.setDuree(textDuree.getText());
        e.setNbMaxParticipants(Integer.parseInt(textNbMax.getText()));
        File source = new File(fcs);
        File destination = new File(System.getProperty("user.dir") + "\\src\\assets\\" + fileName);
        String url = "/assets/" + fileName;
        if (!destination.exists()) {
            try {
                FileUtils.copyFile(source, destination);
            } catch (IOException ex) {
                Logger.getLogger(ModifierEvenementController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        UserService us = UserService.getInstance();

        e.setImage_event(url);

        if (this.ev.getDate_event().toString().equals(textDate.getText())) {
        } else {

            for (int i = 0; i < this.list.size(); i++) {
                User u = us.getUserById(this.list.get(i).getId_user());
                Mailing.sendMail(u.getEmail().get(), e);
            }
        }
        es.updateEvenement(e);
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Evenement.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AuthentificationController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void handleUpload(ActionEvent event) {
        FileChooser fc = new FileChooser();
        File SelectedFile = fc.showOpenDialog(null);
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", ".jpg", ".png"));
        //  e.setImage_event(SelectedFile.getAbsolutePath());
        if (SelectedFile != null) {
            fcs = SelectedFile.toString();
            int index = fcs.lastIndexOf('\\');
            if (index > 0) {
                fileName = fcs.substring(index + 1);
                //System.out.println(fileName);
            }

        }
    }

}
