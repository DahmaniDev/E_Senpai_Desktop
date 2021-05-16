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
import java.util.*;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import services.CaptureImage;
import services.HashCode;
import services.SmtpAuthenticator;
import services.UserService;

/**
 * FXML Controller class
 *
 * @author Dahmani
 */
public class AuthentificationController implements Initializable {

    @FXML
    private TextField loginInput;
    @FXML
    private TextField passwordInput;
    @FXML
    private Button authentifierBt;
    @FXML
    private Hyperlink inscrireBtn;
    @FXML
    private Label erreurLabel;

    private boolean connected = false;

    private int essais = 0;
    @FXML
    private Hyperlink mdpOublieBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("" + essais);

    }

    public void changeConnected() {
        this.connected = !this.connected;
    }

    @FXML
    private void handleAuthentifierBt(ActionEvent event) throws NoSuchAlgorithmException {

        UserService us = UserService.getInstance();
        List<User> users = us.getAllUsers();
        if (event.getSource() == authentifierBt) {
            String login = loginInput.getText();
            String passwd = passwordInput.getText();

            for (User u : users) {
                if (us.verifLogin(login)) {
                    erreurLabel.setText("Login n\'existe pas");
                } else {
                    if (u.getLogin().get().equals(login) && u.getPassword().get().equals(passwd)) {
                        if (u.getRole().get().toLowerCase().equals("admin")) {
                            System.out.println("Admin connecté");
                            connected = true;
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Dashboard.fxml"));
                                Stage stage = new Stage(StageStyle.DECORATED);
                                stage.setScene(
                                        new Scene(loader.load())
                                );
                                stage.setTitle("E-SENPAI | E-Learning Platform");
                                stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icon.png")));
                                stage.setResizable(false);

                                DashboardController controller = loader.getController();
                                controller.initData(u);

                                Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                oldStage.close();

                                stage.show();

                            } catch (IOException ex) {
                                Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            if (u.getRole().get().toLowerCase().equals("formateur")) {
                                if (u.getStatus().get().toLowerCase().equals("en attente")) {
                                    System.out.println("formateur en attente");
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
                                    System.out.println("formateur connecté");
                                    connected = true;
                                    try {
                                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Accueil.fxml"));
                                        Stage stage = new Stage(StageStyle.DECORATED);
                                        stage.setScene(
                                                new Scene(loader.load())
                                        );
                                        stage.setTitle("E-SENPAI | E-Learning Platform");
                                        stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icon.png")));
                                        stage.setResizable(false);

                                        AccueilController controller = loader.getController();
                                        controller.initData(u);

                                        Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                        oldStage.close();

                                        stage.show();

                                    } catch (IOException ex) {
                                        Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                
                            } else {
                                System.out.println("Etudiant connecté");
                                connected = true;
                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Accueil.fxml"));
                                    Stage stage = new Stage(StageStyle.DECORATED);
                                    stage.setScene(new Scene(loader.load()));
                                    stage.setTitle("E-SENPAI | E-Learning Platform");

                                    stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icon.png")));
                                    stage.setResizable(false);

                                    AccueilController controller = loader.getController();
                                    controller.initData(u);

                                    Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                    oldStage.close();

                                    stage.show();

                                } catch (IOException ex) {
                                    Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    } else {
                        erreurLabel.setText("Vérifier Login & Mot De Passe");
                    }
                }
            }
            if (!us.verifLogin(login) && connected==false) {
                this.essais = this.essais + 1;
            }

            System.out.println(essais);
            erreurLabel.setText("Vérifier Login & Mot De Passe");
            if (essais > 2 && connected==false) {
                CaptureImage ci = new CaptureImage();
                WritableImage writableImage = ci.captureSnapShot();
                ci.saveImage();
                File file = new File(System.getProperty("user.dir") + "\\src\\assets\\sanpshot.jpg");
                User u = us.getUsetByLogin(login);

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

                    message.setSubject("Accés susceptible à votre compte");

                    MimeBodyPart messageBodyPart1 = new MimeBodyPart();
                    messageBodyPart1.setText("Bonjour " + u.getNom().get() + "\r\nOn a détecté un accés susceptible à votre compte.\nCette tentative est fait par ce personne : \n");

                    MimeBodyPart messageBodyPart2 = new MimeBodyPart();
                    DataSource source = new FileDataSource(file);
                    messageBodyPart2.setDataHandler(new DataHandler(source));

                    Multipart multipart = new MimeMultipart();
                    multipart.addBodyPart(messageBodyPart1);
                    multipart.addBodyPart(messageBodyPart2);
                    
                    message.setContent(multipart);

                    Transport.send(message);
                    System.out.println("Sent message successfully....");
                } catch (MessagingException mex) {
                    mex.printStackTrace();
                }
            }
        }
    }

    @FXML
    private void handleInscrireAction(ActionEvent event) {
        CaptureImage ci = new CaptureImage();
        ci.deleteFile();
        try {
            Parent page1 = FXMLLoader.load(getClass().getResource("/views/Inscription.fxml"));
            Scene scene = new Scene(page1);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AuthentificationController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void handleMdpOublieBtn(ActionEvent event) {
        try {
            Parent page1 = FXMLLoader.load(getClass().getResource("/views/MotDePasseOublie.fxml"));
            Scene scene = new Scene(page1);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AuthentificationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
