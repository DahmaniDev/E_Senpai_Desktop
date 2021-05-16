/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Evenement;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;
import services.EvenementService;

/**
 * FXML Controller class
 *
 * @author hiche
 */
public class AjoutEvenementController implements Initializable {

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
    private DatePicker textDate;
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
    @FXML
    private Button btnUpload;
    Evenement e = new Evenement();
    private String fcs;
    private String fileName;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
            Parent page1 = FXMLLoader.load(getClass().getResource("/views/Evenement.fxml"));
            Scene scene = new Scene(page1);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AuthentificationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void HandleAnnuler(ActionEvent event) {
        try {
            Parent page1 = FXMLLoader.load(getClass().getResource("/views/Evenement.fxml"));
            Scene scene = new Scene(page1);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AuthentificationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleAjout(ActionEvent event) {
        
        
        if (textTitre.getText().length()==0){
            JOptionPane.showMessageDialog(null, "Le titre est vide");
        }
        if (textEmplacement.getText().length()==0){
            JOptionPane.showMessageDialog(null, "L\'emplacement est vide");
        }
        try{
        Integer.parseInt(textPrix.getText());
            
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Vous devez donner un Entier dans le prix");
            }
        if(textPrix.getText().length()==0){
            JOptionPane.showMessageDialog(null, "Le prix est vide");
        }
        if(Integer.parseInt(textPrix.getText())<0)
        {
            JOptionPane.showMessageDialog(null, "Le prix doit étre positif");
        }
         if(textFondation.getText().length()==0){
            JOptionPane.showMessageDialog(null, "Le prix est vide");
        }
         if(textDuree.getText().length()==0){
            JOptionPane.showMessageDialog(null, "Le durée est vide");
        }
        try{
        Integer.parseInt(textNbMax.getText());
            
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Vous devez donner un Entier dans le nombre max de participants");
            }
        if(textNbMax.getText().length()==0){
            JOptionPane.showMessageDialog(null, "Le nombre max de participants est vide");
        } 
        if(Integer.parseInt(textPrix.getText())<0)
        {
            JOptionPane.showMessageDialog(null, "Le nombre max de participants doit étre positif");
        }
        
        
        
        EvenementService es = EvenementService.getInstance();

        e.setTitre(textTitre.getText());
        e.setEmplacement(textEmplacement.getText());
        e.setPrix(Integer.parseInt(textPrix.getText()));
        e.setFondation(textFondation.getText());
        e.setDate_event(textDate.getValue().toString());
        e.setDuree(textDuree.getText());
        e.setNbMaxParticipants(Integer.parseInt(textNbMax.getText()));
        File source = new File(fcs);
        File destination = new File(System.getProperty("user.dir") + "\\src\\assets\\" + fileName);
        String url = "/assets/" + fileName;
        if (!destination.exists()) {
            try {
                FileUtils.copyFile(source, destination);
            } catch (Exception E) {

            }

        }
        e.setImage_event(url);
        es.insertEvenement(e);

        try {
            Parent page1 = FXMLLoader.load(getClass().getResource("/views/Evenement.fxml"));
            Scene scene = new Scene(page1);
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
