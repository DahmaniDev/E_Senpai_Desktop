/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Discussion;
import entities.MyMessage;
import entities.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import services.ChatBot;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import services.DiscussionService;
import services.MessageService;
import services.UserService;

/**
 * FXML Controller class
 *
 * @author 21656
 */
public class ChatBotController implements Initializable {

    @FXML
    private VBox vbox_msg;
    @FXML
    private TextField text_field;
    @FXML
    private Button retour_btn;
    @FXML
    private Button envoi_msg;

    private ChatBot ChatBot;
    private Discussion currentDiscussion;

    String[][] str;

    User currentUser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.ChatBot = new ChatBot();
        str = ChatBot.getChat();

    }

    public void current(Discussion d, User u) {
        MessageService ms = MessageService.getInstance();
        List<MyMessage> list = new ArrayList<MyMessage>();
        list = ms.getAllMessage(d.getId());
        for (MyMessage m : list) {
            HBox hbox = new HBox(8);
            Button btn = new Button();
            Image img = new Image("assets/annuler.png");
            ImageView view = new ImageView(img);
            view.setFitHeight(10);
            view.setPreserveRatio(true);
            btn.setGraphic(view);
            btn.setOnAction(actionEvent -> {
                ms.suppMsg(m.getId());
                vbox_msg.getChildren().remove(hbox);
            });
            Label lbl = new Label();
            lbl.setText("" + m.getContenu() + " " + m.getDate_msg());
            Label lbl_nom = new Label();
            if (u.getId().get() == m.getUser()) {
                lbl_nom.setText("Ena " + u.getPrenom().get() + " : ");
            } else {
                lbl_nom.setText("Walid : ");
            }
            hbox.getChildren().add(lbl_nom);
            hbox.getChildren().add(lbl);
            hbox.getChildren().add(btn);
            vbox_msg.getChildren().add(hbox);
        }
        this.currentDiscussion = d;
        this.currentUser = u;
        System.out.println("controllers.MessageController.current()" + d.toString() + u.getNom() + u.getPrenom());
    }

    public void initData(MyMessage m) {

        MessageService ms = MessageService.getInstance();
        UserService us = UserService.getInstance();
        HBox hbox = new HBox(8);
        Button btn = new Button();
        Image img = new Image("assets/annuler.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(10);
        view.setPreserveRatio(true);
        btn.setGraphic(view);
        btn.setOnAction(actionEvent -> {
            ms.suppMsg(m.getId());
            vbox_msg.getChildren().remove(hbox);
        });
        Label lbl = new Label();
        lbl.setText("" + m.getContenu() + " " + m.getDate_msg());
        Label lbl_nom = new Label();
        if (currentUser.getId().get() == m.getUser()) {
            lbl_nom.setText("Ena " + currentUser.getPrenom().get() + " : ");
        } else {
            lbl_nom.setText("Ena lekher : ");
        }
        hbox.getChildren().add(lbl_nom);
        hbox.getChildren().add(lbl);
        hbox.getChildren().add(btn);
        vbox_msg.getChildren().add(hbox);
    }

    @FXML
    private void handleRetourBtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Discussion.fxml"));
            Scene scene = new Scene(loader.load());
            DiscussionController ds = loader.getController();
            ds.current(currentUser);
            Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            oldStage.setScene(scene);
            oldStage.show();
        } catch (IOException ex) {
            Logger.getLogger(DiscussionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleEnvoiMsg(ActionEvent event) {
        String msg = text_field.getText();
        text_field.setText("");

        byte response = 0;

        int j = 0; //Le groupe en question
        while (response == 0) {
            if (ChatBot.in(msg.toLowerCase(), str[j * 2])) {

                response = 2;
                int r = (int) Math.floor(Math.random() * str[(j * 2) + 1].length);
                Label lbl = new Label("Walid : " + str[(j * 2) + 1][r]);
                vbox_msg.getChildren().add(lbl);
            }
            j++;
            if (j * 2 == str.length - 1 && response == 0) {
                response = 1;
            }
        }
//            Default
        if (response == 1) {
            int r = (int) Math.floor(Math.random() * str[str.length - 1].length);
            Label lbl = new Label("Walid : " + str[str.length - 1][r]);
            vbox_msg.getChildren().add(lbl);

        }

    }

    @FXML
    private void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String msg = text_field.getText();
            text_field.setText("");
            MessageService ms = MessageService.getInstance();
            byte response = 0;
            int j = 0; //Le groupe en question
            while (response == 0) {
                if (ChatBot.in(msg.toLowerCase(), str[j * 2])) {
                    response = 2;
                    int r = (int) Math.floor(Math.random() * str[(j * 2) + 1].length);
                    Label lbl = new Label("Walid : " + str[(j * 2) + 1][r]);
                    vbox_msg.getChildren().add(lbl);
                    MyMessage msg_bot = new MyMessage ();
                    msg_bot.setUser(666);
                    msg_bot.setDiscussion(currentDiscussion.getId());
                    msg_bot.setContenu( str[(j * 2) + 1][r]);
                    ms.sendMsg(msg_bot);
                }
                j++;
                if (j * 2 == str.length - 1 && response == 0) {
                    response = 1;
                }
            }
//            Default
            if (response == 1) {
                int r = (int) Math.floor(Math.random() * str[str.length - 1].length);
                Label lbl = new Label("Walid : " + str[str.length - 1][r]);
                vbox_msg.getChildren().add(lbl);
                MyMessage msg_bot = new MyMessage ();
                    msg_bot.setUser(666);
                    msg_bot.setDiscussion(currentDiscussion.getId());
                    msg_bot.setContenu( str[str.length - 1][r]);
                    ms.sendMsg(msg_bot);
            }
            DiscussionService ds = DiscussionService.getInstance();
            MyMessage m = new MyMessage();
            m.setContenu(msg);
            m.setDiscussion(currentDiscussion.getId());
            if (currentUser.getId().get() == currentDiscussion.getUser1()) {
                m.setUser(currentDiscussion.getUser1());
            } else {
                m.setUser(currentDiscussion.getUser2());
            }
            ms.sendMsg(m);
            m.setId(ms.getLastMsg(currentDiscussion.getId()).getId());
            m.setDate_msg(ms.getLastMsg(currentDiscussion.getId()).getDate_msg());
            this.initData(m);
        }
    }
}
