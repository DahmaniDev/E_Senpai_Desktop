/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Reclamation;
import entities.User;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.UserService;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import services.ReclamationsService;
import services.SmtpAuthenticator;

/**
 * FXML Controller class
 *
 * @author Dahmani
 */
public class ReplyReclamationController implements Initializable {

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
    private Button repondreButt;
    @FXML
    private TextArea reclamationContenu;
    @FXML
    private TextField userEmail;
    @FXML
    private TextField reclamationSujet;
    @FXML
    private TextArea answer;

    private User currentAdmin;

    private Reclamation currentRec;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    private void handleRepondreButt(ActionEvent event) {
        ReclamationsService rs = ReclamationsService.getInstance();
        currentRec.setStatut("Traitée");
        currentRec.setIdAdmin(currentAdmin.getId().get());
        rs.updateReclamation(currentRec);
        
        UserService us = UserService.getInstance();
        User u = us.getUserById(currentRec.getIdUser().get());
        User admin = us.getUserById(currentAdmin.getId().get());
        
        String to = userEmail.getText();
        String from = "esenpai.devnation@gmail.com";
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        
        
        
        try {
            SmtpAuthenticator authentication = new SmtpAuthenticator();
            MimeMessage message = new MimeMessage(Session.getDefaultInstance(properties, authentication));
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject("Traitement Réclamation");

            message.setText("Bonjour "+u.getNom().get()+"\r\nVous êtes traité par l'admin : "+admin.getNom().get()+".\t\n"+answer.getText());

            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
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

    public void initData(User u, Reclamation r) {
        this.currentAdmin = u;
        this.currentRec = r;
        UserService us = UserService.getInstance();
        User sender = us.getUserById(r.getIdUser().get());
        userEmail.setText(sender.getEmail().get());
        reclamationSujet.setText(r.getSujet().get());
        reclamationContenu.setText(r.getContenu().get());
    }

}
