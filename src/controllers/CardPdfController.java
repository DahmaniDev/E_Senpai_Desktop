/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Formation;
import entities.Slide;
import entities.User;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.Host;

/**
 * FXML Controller class
 *
 * @author wajdi
 */
public class CardPdfController implements Initializable {

    @FXML
    private VBox boxId;
    @FXML
    private Label IdNomPDF;
    Slide s;
    User currentUser;
    Formation f;
    @FXML
    private ImageView imageID;
    String path;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        IdNomPDF.setWrapText(true);
    }

    public void setData(Formation formation, User user, Slide slide) throws UnsupportedEncodingException, MalformedURLException {
        this.s = slide;
        this.f = formation;
        this.currentUser = user;
        IdNomPDF.setText(slide.getText_slide().get());

    }

    @FXML
    private void handleAfficherFormation(MouseEvent event) throws MalformedURLException {

       
        Host host = Host.getInstance();
            path = "src\\contenu\\" + s.getText_slide().get();
                   File file = new File(System.getProperty("user.dir") + "\\src\\Contenu\\" + s.getText_slide().get());

        host.getHostServices().showDocument(file.getAbsolutePath());
    }
/*private void handleVoirCvBtn(ActionEvent event) {
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
    }*/
}
