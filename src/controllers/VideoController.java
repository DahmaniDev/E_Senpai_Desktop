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
import java.io.FileFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;
import services.SlideService;

/**
 * FXML Controller class
 *
 * @author wajdi
 */
public class VideoController implements Initializable {

    @FXML
    private MediaView mediaView;
    String path;
    String pathh;

    private MediaPlayer mediaplayer;
    @FXML
    private SplitPane splitPane;
    private AnchorPane leftPane;
    @FXML
    private AnchorPane rightPane;
    @FXML
    private Slider ProgressBarID;
    @FXML
    private Slider VolumeID;
    User currentUser;
    Slide s;
    Formation f;
    @FXML
    private Button PlayID;
    @FXML
    private Button pauseId;
    @FXML
    private Button stopId;
    @FXML
    private Button fastRateId;
    @FXML
    private Label IDNomVideo;
    private AnchorPane leftPane1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setData(Formation formation, User user, Slide slide) throws UnsupportedEncodingException, MalformedURLException {
        this.currentUser = user;
        this.s = slide;
        this.f = formation;

        System.out.println(slide.getVideo_slide().get());
        SlideService ss = SlideService.getInstance();
        path = "src\\contenu\\" + slide.getVideo_slide().get();
        Media media = new Media(new File(path).toURI().toURL().toExternalForm());
        mediaplayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaplayer);
        mediaplayer.setAutoPlay(true);
        mediaplayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                ProgressBarID.setValue(newValue.toSeconds());
            }

        });
        ProgressBarID.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mediaplayer.seek(Duration.seconds(ProgressBarID.getValue()));
            }
        });
        ProgressBarID.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mediaplayer.seek(Duration.seconds(ProgressBarID.getValue()));
            }
        });
        mediaplayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                Duration total = media.getDuration();
                ProgressBarID.setMax(total.toSeconds());
            }
        });
        VolumeID.setValue((mediaplayer.getVolume() * 100));
        VolumeID.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                mediaplayer.setVolume(VolumeID.getValue() / 100);
            }
        });
    }

    @FXML
    private void handleplay(MouseEvent event) {
        mediaplayer.play();
        mediaplayer.setRate(1);
    }

    @FXML
    private void handlePause(MouseEvent event) {
        mediaplayer.pause();
    }

    @FXML
    private void handleStop(MouseEvent event) {
        mediaplayer.stop();
    }

    @FXML
    private void handleSlowRateID(MouseEvent event) {
        mediaplayer.setRate(0.5);
    }

    @FXML
    private void slowRateID(ActionEvent event) {
        mediaplayer.setRate(2);
    }

    @FXML
    private void handleRetour(MouseEvent event) {
        mediaplayer.stop();
        if (currentUser.getRole().get().toLowerCase().equals("formateur")) {
            try {
                System.out.println(currentUser.getRole().get());
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/views/FormationFormateur.fxml"));
                Parent page2 = loader.load();
                FormationFormateurController controller = loader.getController();
                controller.initDataa(currentUser);
                Scene scene = new Scene(page2);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);

                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(AuthentificationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (currentUser.getRole().get().toLowerCase().equals("admin")) {
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
        } else {
            try {
                System.out.println(currentUser.getRole().get());

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/views/FormationEtudiant.fxml"));
                Parent page2 = loader.load();
                FormationEtudiantController controller = loader.getController();
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
    private void handlefastRate(MouseEvent event) {
    }

}
