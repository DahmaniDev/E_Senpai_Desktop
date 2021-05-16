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
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import services.FormationService;
import services.SlideService;

/**
 * FXML Controller class
 *
 * @author wajdi
 */
public class ModifierFormationController implements Initializable {

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
    private TableView<Slide> SlideTable;
    @FXML
    private TableColumn<Slide, String> UrlColumn;
    @FXML
    private TableColumn<Slide, Integer> OrdreColumn;
    @FXML
    private TableColumn<Slide, String> ActionColumn;
    @FXML
    private Button btnAjouterSlide;
    @FXML
    private Button btnChoisirSlide;
    @FXML
    private TextField UrlSlide;
    @FXML
    private Hyperlink annulerBtn;
    @FXML
    private TextField titreFormationId;
    @FXML
    private TextArea desreptionId;
    @FXML
    private Button ModifierFormationId;
    @FXML
    private ChoiceBox<Integer> OrdreId;
    User currentUser;
    Formation formation;
    File destinationFile = null;
    String nameoffile;
    String fcs;
    Formation f = new Formation();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        desreptionId.setWrapText(true);
        OrdreId.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);

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

    @FXML
    private void handleAjouterSlide(ActionEvent event) {
        FormationService fs = FormationService.getInstance();
        f = fs.getFormationById(formation.getId().get());
        SlideService ss = SlideService.getInstance();
        Slide s = new Slide();
        if (event.getSource() == btnAjouterSlide) {
            if (UrlSlide.getText().equals("") || OrdreId.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("INFORMATION INCOMPLETE");
                alert.setHeaderText(null);
                alert.setContentText("Compléter les informations pour ajouter un slide");
                alert.showAndWait();
            } else {
                int index = fcs.lastIndexOf('.');
                if (index > 0) {
                    String extension = fcs.substring(index + 1);

                    if (extension.equals("pdf")) {
                        s.setImage_slide("");
                        s.setVideo_slide("");
                        s.setText_slide(nameoffile);
                    } else if (extension.equals("mp4")) {
                        s.setImage_slide("");
                        s.setVideo_slide(nameoffile);
                        s.setText_slide("");
                    } else {
                        s.setImage_slide(nameoffile);
                        s.setVideo_slide("");
                        s.setText_slide("");
                    }
                    s.setId_formation(f.getId().get());
                    s.setOrdre(OrdreId.getValue());
                    ss.insertSlide(s);
                }
                afficheTable();
            }

        }
    }

    public void afficheTable() {
        ObservableList<Slide> slide = FXCollections.observableArrayList();
        SlideService ss = SlideService.getInstance();
        this.formation = f;

        slide = ss.getAllSlideByFormation(f.getId().get());
        SlideTable.setItems(slide);
        UrlColumn.setCellValueFactory(cell -> cell.getValue().getUrl());
        OrdreColumn.setCellValueFactory(cell -> cell.getValue().getOrdre().asObject());
    }

    @FXML
    private void handleChoisirSlide(ActionEvent event) {
        FileChooser fc = new FileChooser();
        String newpath = "src/contenu";
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF Files", " *.pdf"), new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png"), new FileChooser.ExtensionFilter("Videos", "*.mp4"));
        File SelectedFile = fc.showOpenDialog(null);
        SlideService ss = SlideService.getInstance();
        Slide s = new Slide();
        if (SelectedFile != null) {
            try {
                UrlSlide.setText(SelectedFile.getAbsolutePath());
                fcs = SelectedFile.toString();
                nameoffile = SelectedFile.getName();
                destinationFile = new File(newpath + "/" + SelectedFile.getName());
                Files.copy(SelectedFile.toPath(), destinationFile.toPath());
                fcs = destinationFile.toString();
            } catch (IOException ex) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Nom Invalide");
                alert.setHeaderText(null);
                alert.setContentText("Changer le nom du votre slide");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void handleAnnulerBtn(ActionEvent event) {
              try {
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
    }

    @FXML
    private void handleModifierFormation(MouseEvent event) {
        FormationService fs = FormationService.getInstance();
        f = fs.getFormationById(formation.getId().get());
        ObservableList<Slide> slide = FXCollections.observableArrayList();
        SlideService ss = SlideService.getInstance();
        slide = ss.getAllSlideByFormation(f.getId().get());
        if (event.getSource() == ModifierFormationId) {

            if (titreFormationId.getText().equals("") || desreptionId.getText().equals("") || slide.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("INFORMATION INCOMPLETE");
                alert.setHeaderText(null);
                alert.setContentText("Compléter les informations pour Modifier cette formation");
                alert.showAndWait();
            } else {
                f.setId(f.getId().get());
                f.setTitre(titreFormationId.getText());
                f.setDescription(desreptionId.getText());
                fs.UpdateFormation(f);
                Image img = new Image("assets/green_tick.png", 50, 50, false, false);
                Notifications notificationsBuilder = Notifications.create()
                        .title("Formation")
                        .text("Formation Modifier avec succée")
                        .graphic(new ImageView(img))
                        .hideAfter(Duration.seconds(6))
                        .position(Pos.TOP_LEFT);
                notificationsBuilder.darkStyle();
                notificationsBuilder.show();
                try {
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
            }
        }
    
}

public void initDataa(User u, Formation f) {
        this.currentUser = u;
        this.formation = f;
        SlideService ss = SlideService.getInstance();
        FormationService fs = FormationService.getInstance();
        ObservableList<Slide> slide = FXCollections.observableArrayList();
        slide = ss.getAllSlideByFormation(f.getId().get());
        SlideTable.setItems(slide);
        UrlColumn.setCellValueFactory(cell -> cell.getValue().getUrl());
        OrdreColumn.setCellValueFactory(cell -> cell.getValue().getOrdre().asObject());
        Callback<TableColumn<Slide, String>, TableCell<Slide, String>> cellFactory
                = (param) -> {
                    final TableCell<Slide, String> cell = new TableCell<Slide, String>() {

                @Override
        public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button supprimerbutton = new Button("Supprimer");
                        supprimerbutton.setOnAction(event -> {
                            Slide s = getTableView().getItems().get(getIndex());
                            ss.DeleteSlideByid(s.getId().get());
                            Update(currentUser, formation);
                        });
                        setGraphic(supprimerbutton);
                        setText(null);
                    }
                }
            };
                    return cell;
                };
        ActionColumn.setCellFactory(cellFactory);
        titreFormationId.setText(f.getTitre().get());
        desreptionId.setText(f.getDescription().get());
    }

    public void Update(User u, Formation f) {
        this.currentUser = u;
        this.formation = f;
        SlideService ss = SlideService.getInstance();
        FormationService fs = FormationService.getInstance();
        ObservableList<Slide> slide = FXCollections.observableArrayList();
        slide = ss.getAllSlideByFormation(f.getId().get());
        SlideTable.setItems(slide);
        UrlColumn.setCellValueFactory(cell -> cell.getValue().getUrl());
        OrdreColumn.setCellValueFactory(cell -> cell.getValue().getOrdre().asObject());
        Callback<TableColumn<Slide, String>, TableCell<Slide, String>> cellFactory
                = (param) -> {
                    final TableCell<Slide, String> cell = new TableCell<Slide, String>() {

                @Override
        public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button supprimerbutton = new Button("Supprimer");
                        supprimerbutton.setOnAction(event -> {
                            Slide s = getTableView().getItems().get(getIndex());
                            ss.DeleteSlideByid(s.getId().get());
                            initDataa(currentUser, formation);

                        });
                        setGraphic(supprimerbutton);
                        setText(null);
                    }
                }
            };
                    return cell;
                };
        ActionColumn.setCellFactory(cellFactory);
        titreFormationId.setText(f.getTitre().get());
        desreptionId.setText(f.getDescription().get());
    }
}
