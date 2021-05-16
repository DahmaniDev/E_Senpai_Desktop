/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Abonnement;
import entities.Formation;
import entities.Slide;
import entities.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.Rating;
import services.AbonnementService;
import services.FormationService;
import services.SlideService;
import services.UserService;

/**
 * FXML Controller class
 *
 * @author wajdi
 */
public class ConsulterFormationController implements Initializable {

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
    User currentUser;
    Formation formation;
    @FXML
    private GridPane GridPaneVideo;
    @FXML
    private GridPane GridPanePdf;
    @FXML
    private GridPane GridPaneimg;
    @FXML
    private Label TitreFormatioIn;
    @FXML
    private ImageView subID;
    @FXML
    private ImageView DsubId;
    @FXML
    private Rating RateId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleAccueilBtn(ActionEvent event) {
          try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/Accueil.fxml"));
            Parent page2 = loader.load();
            AccueilController controller = loader.getController();
            controller.initData(currentUser);
            Scene scene = new Scene(page2);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);

            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AuthentificationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleProfilBtn(ActionEvent event) {
    }

    @FXML
    private void HandleFormationsBtn(ActionEvent event) {
        if (currentUser.getRole().get().toLowerCase().equals("formateur")) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/views/Formation.fxml"));
                Parent page2 = loader.load();
                FormationController controller = loader.getController();
                controller.initDataa(currentUser);
                Scene scene = new Scene(page2);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);

                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(AuthentificationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (currentUser.getRole().get().toLowerCase().equals("etudiant")) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/views/Formation.fxml"));
                Parent page2 = loader.load();
                FormationController controller = loader.getController();
                controller.initDataa(currentUser);
                Scene scene = new Scene(page2);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);

                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(AuthentificationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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

    public void initDataa(User u, Formation f) {

        this.currentUser = u;
        this.formation = f;
        AbonnementService as = AbonnementService.getInstance();

        if (currentUser.getRole().get().toLowerCase().equals("formateur")) {
            subID.setVisible(false);
            DsubId.setVisible(false);
            RateId.setVisible(false);

        } else {

            try {

                Abonnement ab = as.getAbonnementByIdRated(formation.getId().get(), currentUser.getId().get());

                if (ab.getId_formation().get() == f.getId().get() && ab.getId_etudiant().get() == currentUser.getId().get()) {
                    subID.setVisible(false);
                    DsubId.setVisible(true);
                    RateId.setRating(formation.getNote().get());
                    if (ab.getRated().get() == 0) {
                        RateId.setDisable(true);
                    } else {
                        RateId.setDisable(false);

                    }
                }
            } catch (Exception e) {
                RateId.setVisible(false);
                subID.setVisible(true);
                DsubId.setVisible(false);

            }

        }
        TitreFormatioIn.setText(f.getTitre().get());
        FormationService fs = FormationService.getInstance();
        UserService us = UserService.getInstance();
        Formation formations = fs.getFormationById(f.getId().get());
        List<User> users = us.getAllUsers();

        SlideService ss = SlideService.getInstance();
        List<Slide> slides = ss.getAllSlideByFormation(formations.getId().get());
        int column = 0;
        int row = 1;
        int row1 = 1;
        int row2 = 1;
        int column1 = 0;
        int column2 = 0;

        try {

            for (Slide s : slides) {
                if (s.getUrl().get().endsWith("4")) {

                    FXMLLoader fXMLLoader = new FXMLLoader();
                    fXMLLoader.setLocation(getClass().getResource("/views/CardVideo.fxml"));
                    VBox vbox = fXMLLoader.load();
                    CardVideoController cardVideoController = fXMLLoader.getController();
                    cardVideoController.setData(f, u, s);
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
                    cardPdfController.setData(f, u, s);
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
                    cardImageController.setData(f, u, s);
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

    @FXML
    private void handlefollow(MouseEvent event) {
        AbonnementService as = AbonnementService.getInstance();
        Abonnement ab = new Abonnement();
        if (event.getSource() == subID) {
            System.out.println("hello");

            ab.setId_etudiant(currentUser.getId().get());
            ab.setId_formation(formation.getId().get());
            ab.setDate_abonnement(LocalDateTime.now().toString());
            as.insertAbonnement(ab);
        }
        Image img = new Image("assets/green_tick.png", 50, 50, false, false);
        Notifications notificationsBuilder = Notifications.create()
                .title("Formation")
                .text("vous êtes abonnée")
                .graphic(new ImageView(img))
                .hideAfter(Duration.seconds(6))
                .position(Pos.TOP_LEFT);
        notificationsBuilder.darkStyle();
        notificationsBuilder.show();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/ConsulterFormation.fxml"));
            Parent page2 = loader.load();
            ConsulterFormationController controller = loader.getController();
            controller.initDataa(currentUser, formation);
            Scene scene = new Scene(page2);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);

            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AuthentificationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleUnfollow(MouseEvent event) {
        AbonnementService as = AbonnementService.getInstance();
        as.DeleteAbonnementById(formation.getId().get(), currentUser.getId().get());
        Image img = new Image("assets/cancel.png", 50, 50, false, false);
        Notifications notificationsBuilder = Notifications.create()
                .title("Formation")
                .text("vous êtes désabonnée")
                .graphic(new ImageView(img))
                .hideAfter(Duration.seconds(6))
                .position(Pos.TOP_LEFT);
        notificationsBuilder.darkStyle();
        notificationsBuilder.show();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/ConsulterFormation.fxml"));
            Parent page2 = loader.load();
            ConsulterFormationController controller = loader.getController();
            controller.initDataa(currentUser, formation);
            Scene scene = new Scene(page2);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);

            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AuthentificationController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void handleChoisirRate(MouseEvent event) {
        FormationService fs = FormationService.getInstance();
        AbonnementService as = AbonnementService.getInstance();
        List<Abonnement> abonnements = as.getAllaAbonByIdFormation(formation.getId().get());
        int i = 0;
        for (Abonnement a : abonnements) {
            i = +1;
        }
        int R = (int) RateId.getRating();
        int rate = (R + formation.getNote().get()) / i;
        fs.updateRating(formation.getId().get(), rate);
        as.updateRated(formation.getId().get(), currentUser.getId().get());
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/ConsulterFormation.fxml"));
            Parent page2 = loader.load();
            ConsulterFormationController controller = loader.getController();
            controller.initDataa(currentUser, formation);
            Scene scene = new Scene(page2);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);

            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AuthentificationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
