/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Reclamation;
import entities.User;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.HostServices;
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
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import services.FormateursData;
import services.Host;
import services.ReclamationsService;
import services.SmtpAuthenticator;
import services.UserService;

/**
 * FXML Controller class
 *
 * @author Dahmani
 */
public class DemandesFormateursController implements Initializable {

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
    private AnchorPane gestionUserPane;
    @FXML
    private TableView<User> formateursTable;
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
    private TableColumn<User, String> roleColumn;
    @FXML
    private Button retourBtn;

    int idSelected;

    private User currentAdmin;

    @FXML
    private Button approuverBtn;
    @FXML
    private Label erreurLabel;

    private FormateursData list = new FormateursData();
    @FXML
    private TableColumn<User, String> statutColumn;
    @FXML
    private Button voirCvBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        formateursTable.setItems(list.getUsers());
        nomColumn.setCellValueFactory(cell -> cell.getValue().getNom());
        prenomColumn.setCellValueFactory(cell -> cell.getValue().getPrenom());
        dateColumn.setCellValueFactory(cell -> cell.getValue().getDate_naissance());
        sexeColumn.setCellValueFactory(cell -> cell.getValue().getSexe());
        emailColumn.setCellValueFactory(cell -> cell.getValue().getEmail());
        loginColumn.setCellValueFactory(cell -> cell.getValue().getLogin());
        idColumn.setCellValueFactory(cell -> cell.getValue().getId().asString());
        roleColumn.setCellValueFactory(cell -> cell.getValue().getRole());
        statutColumn.setCellValueFactory(cell -> cell.getValue().getStatus());
        idSelected = 0;
        erreurLabel.setVisible(false);
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
    private void handleformateursTableMouseClicked(MouseEvent event) {
        idSelected = list.getUsers().get(formateursTable.getSelectionModel().getSelectedIndex()).getId().get();
    }

    @FXML
    private void handleRetourBtn(ActionEvent event) {
        try {
            Parent page1 = FXMLLoader.load(getClass().getResource("/views/GestionUsers.fxml"));
            Scene scene = new Scene(page1);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AuthentificationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleApprouverBtn(ActionEvent event) {

        if (idSelected == 0) {
            erreurLabel.setVisible(true);
        } else {
            UserService us = UserService.getInstance();
            User u = us.getUserById(idSelected);
            us.updateStatus(idSelected);
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

                message.setSubject("Demande Approuvée!");

                message.setText("Bonjour " + u.getNom().get() + "\r\nVotre demande d\'inscription en tant que formateur a été acceptée par l\'équipe de E-Senpai. \r\nNous attendons que vous partagez vos connaissances avec nos étudiants. Merci pour votre inscription.");

                Transport.send(message);
                System.out.println("Sent message successfully....");
            } catch (MessagingException mex) {
                mex.printStackTrace();
            }
            ObservableList<User> newList = FXCollections.observableArrayList();
            newList = us.displayFormateursDemandes();
            formateursTable.setItems(newList);
            nomColumn.setCellValueFactory(cell -> cell.getValue().getNom());
            prenomColumn.setCellValueFactory(cell -> cell.getValue().getPrenom());
            dateColumn.setCellValueFactory(cell -> cell.getValue().getDate_naissance());
            sexeColumn.setCellValueFactory(cell -> cell.getValue().getSexe());
            emailColumn.setCellValueFactory(cell -> cell.getValue().getEmail());
            loginColumn.setCellValueFactory(cell -> cell.getValue().getLogin());
            idColumn.setCellValueFactory(cell -> cell.getValue().getId().asString());
            roleColumn.setCellValueFactory(cell -> cell.getValue().getRole());
            statutColumn.setCellValueFactory(cell -> cell.getValue().getStatus());
            idSelected = 0;
            erreurLabel.setVisible(false);
        }
    }

    public void initData(User u) {
        this.currentAdmin = u;
    }

    @FXML
    private void handleVoirCvBtn(ActionEvent event) {
        if (idSelected == 0) {
            erreurLabel.setVisible(true);
        } else {
            erreurLabel.setVisible(false);
            UserService us = UserService.getInstance();
            User u = us.getUserById(idSelected);
            Host host = Host.getInstance();
            String url = u.getCv().get();
            String fileName = "";
            int index = url.lastIndexOf("/");
            if (index > 0) {
                fileName = url.substring(index + 1);
            }
            File file = new File(System.getProperty("user.dir") + "\\src\\assets\\" + fileName);
            host.getHostServices().showDocument(file.getAbsolutePath());
        }
    }

}
