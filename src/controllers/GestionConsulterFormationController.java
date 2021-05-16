/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Formation;
import entities.Slide;
import entities.User;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.FormationService;
import services.SlideService;
import services.UserService;

/**
 * FXML Controller class
 *
 * @author wajdi
 */
public class GestionConsulterFormationController implements Initializable {

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
    private GridPane GridPanePdf;
    @FXML
    private GridPane GridPaneVideo;
    @FXML
    private GridPane GridPaneimg;
    @FXML
    private Label TitreFormatioIn;
    User currentUser;
    Formation formation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleFormationsBtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/GestionFormations.fxml"));
            Parent page2 = loader.load();
            GestionFormationsController controller = loader.getController();
            controller.initDataa(currentUser);
            Scene scene = new Scene(page2);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);

            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AuthentificationController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    }

    public void initDataa(User u, Formation f) {
        this.currentUser = u;
        this.formation = f;
        TitreFormatioIn.setText(f.getTitre().get());
        FormationService fs = FormationService.getInstance();
        UserService us = UserService.getInstance();
        Formation formations = fs.getFormationById(f.getId().get());
        List<User> users = us.getAllUsers();
        int column = 0;
        int row = 1;
         int column1 = 0;
        int row1 = 1;
         int column2 = 0;
        int row2= 1;
        SlideService ss = SlideService.getInstance();
        List<Slide> slides = ss.getAllSlideByFormation(formations.getId().get());

        try {

            for (Slide s : slides) {
                if (s.getUrl().get().endsWith("4")) {
                    FXMLLoader fXMLLoader = new FXMLLoader();
                    fXMLLoader.setLocation(getClass().getResource("/views/CardVideo.fxml"));
                    VBox vbox = fXMLLoader.load();
                    CardVideoController cardVideoController = fXMLLoader.getController();
                    cardVideoController.setData(f, currentUser, s);
                    if (column == 3) {
                        column = 0;
                        ++row;
                    }
                    GridPaneVideo.add(vbox, column++, row);
                    GridPane.setMargin(vbox, new Insets(10));
                } else if (s.getUrl().get().endsWith("f")) {
                    FXMLLoader fXMLLoader = new FXMLLoader();
                    fXMLLoader.setLocation(getClass().getResource("/views/CardPdf.fxml"));
                    VBox vbox = fXMLLoader.load();
                    CardPdfController cardPdfController = fXMLLoader.getController();
                    cardPdfController.setData(f, currentUser, s);
                    if (column1 == 3) {
                        column1 = 0;
                        ++row1;
                    }
                    GridPanePdf.add(vbox, column1++, row1);
                    GridPane.setMargin(vbox, new Insets(10));
                } else {
                    FXMLLoader fXMLLoader = new FXMLLoader();
                    fXMLLoader.setLocation(getClass().getResource("/views/CardImage.fxml"));
                    VBox vbox = fXMLLoader.load();
                    CardImageController cardImageController = fXMLLoader.getController();
                    cardImageController.setData(f, currentUser, s);
                    if (column2 == 3) {
                        column2 = 0;
                        ++row2;
                    }
                    GridPaneimg.add(vbox, column2++, row2);
                    GridPane.setMargin(vbox, new Insets(10));
                }
            }
        } catch (IOException e) {

        }

    }
}
