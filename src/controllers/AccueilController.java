/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Session;
import entities.User;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.UserService;

/**
 * FXML Controller class
 *
 * @author Dahmani
 */
public class AccueilController implements Initializable {

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
    private Button deconnectBtn;
    @FXML
    private Label welcomeLabel;

    private User currentUser;

    @FXML
    private Button blogBtn;
    @FXML
    private SplitPane splitPane;
    @FXML
    private AnchorPane leftPane;
    @FXML
    private HBox hboxHeader;

    @FXML
    private AnchorPane accueilPane;
    @FXML
    private Button reclamationsBtn;
    @FXML
    private TextField searchInput;
    @FXML
    private VBox searchVbox;
    @FXML
    private Button chercherBtn;
    @FXML
    private Button voirFormationsBtn;
    @FXML
    private Button voirQuizsBtn;
    @FXML
    private Button btnEvent;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ImageView x = new ImageView("assets/loop.png");
        x.setFitHeight(10);
        x.setFitWidth(10);
        chercherBtn.setGraphic(x);
        searchInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    String input = searchInput.getText();
                    UserService us = UserService.getInstance();
                    List<User> list = new ArrayList<User>();
                    if (!input.equals("")) {
                        list = us.searchUser(input);
                    }
                    searchVbox.getChildren().clear();

                    for (User u : list) {
                        HBox hbox = new HBox();
                        ImageView imageprofil;
                        Label name = new Label();
                        if (!u.getPhoto_profil().get().equals("")) {
                            imageprofil = new ImageView(u.getPhoto_profil().get());
                        } else {
                            imageprofil = new ImageView("/assets/userRandom.png");
                        }
                        imageprofil.setFitHeight(20.0);
                        imageprofil.setFitWidth(20.0);
                        name.setText(u.getNom().get() + " " + u.getPrenom().get());
                        hbox.getChildren().add(imageprofil);
                        hbox.getChildren().add(name);
                        hbox.setSpacing(20);
                        hbox.setPadding(new Insets(5));
                        hbox.setBorder(new Border(new BorderStroke(Color.DARKSALMON, BorderStrokeStyle.SOLID, null, null)));
                        hbox.setOnMouseClicked(e -> {
                            ButtonType myBtn = new ButtonType("Chat");
                            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", myBtn);
                            alert.setTitle("E-SENPAI | E-Learning Platform");
                            alert.setHeaderText("" + u.getNom().get() + " " + u.getPrenom().get());
                            ImageView img;
                            if (u.getPhoto_profil().get().equals("")) {
                                img = new ImageView("/assets/userRandom.png");
                            } else {
                                img = new ImageView(u.getPhoto_profil().get());
                            }
                            img.setFitHeight(40);
                            img.setFitWidth(40);
                            alert.setGraphic(img);

                            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                            stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icon.png")));
                            alert.setContentText("*" + u.getRole().get() + "\n" + "*" + u.getEmail().get());
                            alert.showAndWait().ifPresent(rs -> {
                                if (rs == myBtn) {
                                    System.out.println("Pressed OK.");
                                    //Redirect vers CHAT PAGE
                                }
                            });
                        });
                        searchVbox.getChildren().add(hbox);
                    }
                }
            }

        });
    }

    public void initData(User u) {
        ImageView imageprofil;
        this.currentUser = u;
        if (currentUser.getRole().get().toLowerCase().equals("formateur")) {
            if (currentUser.getSexe().get().toLowerCase().equals("homme")) {
                welcomeLabel.setText("Bienvenue Mr " + currentUser.getNom().get());
            } else {
                welcomeLabel.setText("Bienvenue Mme " + currentUser.getNom().get());
            }
        } else {
            welcomeLabel.setText("Bienvenue " + currentUser.getNom().get());
        }

        if (u.getPhoto_profil().get().equals("")) {
            imageprofil = new ImageView("/assets/userRandom.png");
        } else {
            imageprofil = new ImageView(u.getPhoto_profil().get());
        }
        imageprofil.setFitHeight(30.0);
        imageprofil.setFitWidth(30.0);
        hboxHeader.getChildren().add(imageprofil);
    }

    @FXML
    private void handleAccueilBtn(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Accueil.fxml"));

            Scene scene = new Scene(loader.load());

            AccueilController controller = loader.getController();
            controller.initData(currentUser);

            Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            oldStage.setScene(scene);
            oldStage.show();

        } catch (IOException ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void handleTestQuizBtn(ActionEvent event) {

        try {

            if (currentUser.getRole().get().equals("Etudiant")) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/TestEtudiant.fxml"));
                Stage stage = new Stage(StageStyle.DECORATED);
                stage.setScene(
                        new Scene(loader.load())
                );
                stage.setTitle("E-SENPAI | E-Learning Platform");
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icon.png")));
                stage.setResizable(false);

                TestEtudiantController controller = loader.getController();
                controller.initData(currentUser);

                Session s = new Session();
                Session.setUser(currentUser);

                Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                oldStage.close();

                stage.show();
            } else if (currentUser.getRole().get().toLowerCase().equals("admin")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/TestQuizAdmin.fxml"));
                Stage stage = new Stage(StageStyle.DECORATED);
                stage.setScene(
                        new Scene(loader.load())
                );
                stage.setTitle("E-SENPAI | E-Learning Platform");
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icon.png")));
                stage.setResizable(false);

                TestQuizAdminController controller = loader.getController();
                controller.initData(currentUser);

                Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                oldStage.close();

                stage.show();
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Test.fxml"));
                Stage stage = new Stage(StageStyle.DECORATED);
                stage.setScene(
                        new Scene(loader.load())
                );
                stage.setTitle("E-SENPAI | E-Learning Platform");
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icon.png")));
                stage.setResizable(false);

                TestController controller = loader.getController();
                controller.initData(currentUser);

                Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                oldStage.close();

                stage.show();
            }
            // loader = new FXMLLoader(getClass().getResource("/views/Test.fxml"));

        } catch (IOException ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void handleChatBtn(ActionEvent event) {
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
    private void handleBlogBtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/consulterBlog.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(
                    new Scene(loader.load())
            );
            stage.setTitle("E-SENPAI | E-Learning Platform");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icon.png")));
            stage.setResizable(false);

            ConsulterBlogController controller = loader.getController();
            controller.initData(currentUser);

            Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            oldStage.close();

            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleProfilBtn(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Profil.fxml"));

            Scene scene = new Scene(loader.load());

            ProfilController controller = loader.getController();
            controller.initData(currentUser);

            Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            oldStage.setScene(scene);
            oldStage.show();

        } catch (IOException ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
        }

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
    private void handleReclamationsBtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AjouterReclamation.fxml"));

            Scene scene = new Scene(loader.load());

            AjouterReclamationController controller = loader.getController();
            controller.initData(currentUser);

            Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            oldStage.setScene(scene);
            oldStage.show();

        } catch (IOException ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleChercherBtn(ActionEvent event) {
        String input = searchInput.getText();
        UserService us = UserService.getInstance();
        List<User> list = new ArrayList<User>();
        if (!input.equals("")) {
            list = us.searchUser(input);
        }
        searchVbox.getChildren().clear();

        for (User u : list) {
            HBox hbox = new HBox();
            ImageView imageprofil;
            Label name = new Label();
            if (!u.getPhoto_profil().get().equals("")) {
                imageprofil = new ImageView(u.getPhoto_profil().get());
            } else {
                imageprofil = new ImageView("/assets/userRandom.png");
            }
            imageprofil.setFitHeight(20.0);
            imageprofil.setFitWidth(20.0);
            name.setText(u.getNom().get() + " " + u.getPrenom().get());
            hbox.getChildren().add(imageprofil);
            hbox.getChildren().add(name);
            hbox.setSpacing(20);
            hbox.setPadding(new Insets(5));
            hbox.setBorder(new Border(new BorderStroke(Color.DARKSALMON, BorderStrokeStyle.SOLID, null, null)));
            hbox.setOnMouseClicked(e -> {
                ButtonType myBtn = new ButtonType("Chat");
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "", myBtn);
                alert.setTitle("E-SENPAI | E-Learning Platform");
                alert.setHeaderText("" + u.getNom().get() + " " + u.getPrenom().get());
                ImageView img;
                if (u.getPhoto_profil().get().equals("")) {
                    img = new ImageView("/assets/userRandom.png");
                } else {
                    img = new ImageView(u.getPhoto_profil().get());
                }
                img.setFitHeight(40);
                img.setFitWidth(40);
                alert.setGraphic(img);

                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icon.png")));
                alert.setContentText("*" + u.getRole().get() + "\n" + "*" + u.getEmail().get());
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == myBtn) {
                        System.out.println("Pressed OK.");
                        //Redirect vers CHAT PAGE
                    }
                });
            });
            searchVbox.getChildren().add(hbox);
        }
    }

    @FXML
    private void handleVoirFormationsBtn(ActionEvent event) {
        
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
    private void handleVoirQuizsBtn(ActionEvent event) {
        try {

            if (currentUser.getRole().get().equals("Etudiant")) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/TestEtudiant.fxml"));
                Stage stage = new Stage(StageStyle.DECORATED);
                stage.setScene(
                        new Scene(loader.load())
                );
                stage.setTitle("E-SENPAI | E-Learning Platform");
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icon.png")));
                stage.setResizable(false);

                TestEtudiantController controller = loader.getController();
                controller.initData(currentUser);

                Session s = new Session();
                Session.setUser(currentUser);

                Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                oldStage.close();

                stage.show();
            } else if (currentUser.getRole().get().toLowerCase().equals("admin")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/TestQuizAdmin.fxml"));
                Stage stage = new Stage(StageStyle.DECORATED);
                stage.setScene(
                        new Scene(loader.load())
                );
                stage.setTitle("E-SENPAI | E-Learning Platform");
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icon.png")));
                stage.setResizable(false);

                TestQuizAdminController controller = loader.getController();
                controller.initData(currentUser);

                Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                oldStage.close();

                stage.show();
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Test.fxml"));
                Stage stage = new Stage(StageStyle.DECORATED);
                stage.setScene(
                        new Scene(loader.load())
                );
                stage.setTitle("E-SENPAI | E-Learning Platform");
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icon.png")));
                stage.setResizable(false);

                TestController controller = loader.getController();
                controller.initData(currentUser);

                Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                oldStage.close();

                stage.show();
            }
            // loader = new FXMLLoader(getClass().getResource("/views/Test.fxml"));

        } catch (IOException ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleEvent(ActionEvent event) {
        System.out.println("duzgfzodnz");
        try {
            if ("Admin".equals(this.currentUser.getRole().get())) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Evenement.fxml"));
                Scene scene = new Scene(loader.load());
                EvenementController controller = loader.getController();
                controller.initData(this.currentUser);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } else if ("Etudiant".equals(this.currentUser.getRole().get()) || "Formateur".equals(this.currentUser.getRole().get())) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Participation.fxml"));
                Scene scene = new Scene(loader.load());
                ParticipationController controller = loader.getController();
                controller.initData(this.currentUser);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
        } catch (IOException ex) {
            Logger.getLogger(AuthentificationController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
