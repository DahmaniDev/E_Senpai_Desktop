/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.User;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import services.SmtpAuthenticator;
import services.UserService;

/**
 * FXML Controller class
 *
 * @author Dahmani
 */
public class MotDePasseOublieController implements Initializable {

    @FXML
    private TextField emailInput;
    @FXML
    private Button rechercherBtn;
    @FXML
    private Hyperlink annulerBtn;
    @FXML
    private Label erreurLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void handleRechercherBtn(ActionEvent event) {
        if (emailInput.getText().equals("")) {
            erreurLabel.setText("Vérifier Login");
        } else {
            erreurLabel.setText("");

            UserService us = UserService.getInstance();
            User u = us.getUsetByEmail(emailInput.getText());
            if (u.getEmail().get().equals("")) {
                erreurLabel.setText("Utilisateur n'existe pas");
            } else {
                erreurLabel.setText("");
                String to = u.getEmail().get();
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

                    message.setSubject("Récupération Mot De Passe");

                    message.setText("Bonjour " + u.getNom().get() + "\r\nCeci est votre mot de passe : " + u.getPassword().get());

                    Transport.send(message);
                    System.out.println("Sent message successfully....");
                } catch (MessagingException mex) {
                    mex.printStackTrace();
                }

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("E-SENPAI | E-Learning Platform");
                alert.setHeaderText("Récupération de mot de passe");
                ImageView img = new ImageView(this.getClass().getResource("/assets/icon.png").toString());
                img.setFitHeight(40);
                img.setFitWidth(40);
                alert.setGraphic(img);
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icon.png")));
                alert.setContentText("Si votre compte existe, un mail sera envoyé à votre adresse pour la récupération de votre mot de passe");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                        System.out.println("Pressed OK.");
                    }
                });
            }
        }
    }

    @FXML
    private void handleAnnulerBtn(ActionEvent event) {

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

}
