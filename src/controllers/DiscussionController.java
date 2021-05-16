/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Discussion;
import entities.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.DiscussionService;
import services.UserService;

/**
 * FXML Controller class
 *
 * @author 21656
 */
public class DiscussionController implements Initializable {

    @FXML
    private VBox vbox_discussion;
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
    private AnchorPane rightPane;
    @FXML
    private AnchorPane accueilPane;
    User currentUser;
    private TextField inputUser;
    @FXML
    private TextField search_bar;
    @FXML
    private VBox vbox_utilisateur;
    @FXML
    private Button search;
    @FXML
    private Button button_chatbot;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void handleButton(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ChatBot.fxml"));
            Scene scene = new Scene(loader.load());
            ChatBotController cb = loader.getController();
            DiscussionService ds = DiscussionService.getInstance();
            if (ds.verif(currentUser.getId().get(), 666)) {
                Discussion d = new Discussion();
                d.setUser1(currentUser.getId().get());
                d.setUser2(666);
                
                ds.insertDiscussion(d);
                d.setId(ds.getDiscussionbytwo(d.getUser1(), d.getUser2()).getId());
                cb.current(d, currentUser);

            } else {
                Discussion d = new Discussion();
                d = ds.getDiscussionbytwo(currentUser.getId().get(), 666);
                cb.current(d, currentUser);
            }
            Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            oldStage.setScene(scene);
            oldStage.show();

        } catch (IOException ex) {
            Logger.getLogger(AuthentificationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void handleAjoutDicussion(ActionEvent event) {
        DiscussionService ds = DiscussionService.getInstance();
        Discussion d = new Discussion();
        d.setUser1(currentUser.getId().get());
        d.setUser2(Integer.parseInt(inputUser.getText()));
        ds.insertDiscussion(d);

    }

    public void current(User u) {
        DiscussionService ds = DiscussionService.getInstance();
        UserService us = UserService.getInstance();
        List<Discussion> list = new ArrayList<Discussion>();
        list = ds.getAllDiscussion(u.getId().get());
        for (Discussion d : list) {
            if (ds.messagesById(d.getId())) {
                HBox hbox = new HBox(8);
                Button btn = new Button();
                if (u.getId().get() == d.getUser1()) {
                    btn.setText("" + us.getUserById(d.getUser2()).getNom().get() + " " + us.getUserById(d.getUser2()).getPrenom().get());
                }
                if (u.getId().get() == d.getUser2()) {
                    btn.setText("" + us.getUserById(d.getUser1()).getNom().get() + " " + us.getUserById(d.getUser1()).getPrenom().get());
                }
                btn.setOnAction(event -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Message.fxml"));

                        Scene scene = new Scene(loader.load());

                        MessageController ms = loader.getController();
                        ms.current(d, currentUser);

                        Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        oldStage.setScene(scene);
                        oldStage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(AuthentificationController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                });
                Label lbl = new Label();
                lbl.setText("" + d.getDate_discussion());
                hbox.getChildren().add(btn);
                hbox.getChildren().add(lbl);
                vbox_discussion.getChildren().add(hbox);

            } else {
                ds.deleteDiscussion(d.getId());
            }
        }
        this.currentUser = u;
        System.out.println("controllers.DiscussionController.current()" + u.toString());

    }

    @FXML
    private void handleAccueilBtn(ActionEvent event) {
    }

    @FXML
    private void handleProfilBtn(ActionEvent event) {
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

    private void handleSearchBar(ActionEvent event) {

    }

    @FXML
    private void handleSearchbtn(ActionEvent event) {
        UserService us = UserService.getInstance();
        List<User> l = us.searchAllUsers(search_bar.getText());
        for (User x : l) {
            HBox hbox = new HBox(8);
            Button btn = new Button();
            btn.setOnAction(action -> {
                try {
                    DiscussionService ds = DiscussionService.getInstance();
                    Discussion d = new Discussion();
                    d.setUser1(currentUser.getId().get());
                    d.setUser2(x.getId().get());
                    ds.insertDiscussion(d);
                    d = ds.getDiscussionbytwo(currentUser.getId().get(), x.getId().get());
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Message.fxml"));

                    Scene scene = new Scene(loader.load());

                    MessageController ms = loader.getController();
                    ms.current(d, currentUser);

                    Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    oldStage.setScene(scene);
                    oldStage.show();
                } catch (IOException ex) {
                    Logger.getLogger(AuthentificationController.class.getName()).log(Level.SEVERE, null, ex);
                }

            });
            Label lbl = new Label();
            lbl.setText(" " + x.getNom().get() + " " + x.getPrenom().get() + " ");
            hbox.getChildren().add(lbl);
            hbox.getChildren().add(btn);
            vbox_utilisateur.getChildren().add(hbox);

//            vbox_utilisateur
        }
    }

}
