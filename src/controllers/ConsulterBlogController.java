/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Blog;
import entities.Post;
import entities.User;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterAttributes;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.BlogService;
import services.PostService;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Methnani
 */
public class ConsulterBlogController implements Initializable {

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
    private AnchorPane profilPane;
    @FXML
    private VBox blogsVbox;
    @FXML
    private Button ajouterBlogBtn;
    
    private User currentUser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    }

    @FXML
    private void handleAjouterBlog(ActionEvent event) throws IOException {
                        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ajoutBlog.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
                try {
                    stage.setScene(
                            new Scene(loader.load())
                    );      } catch (IOException ex) {
                    Logger.getLogger(AjoutBlogController.class.getName()).log(Level.SEVERE, null, ex);
                }
        stage.setTitle("E-SENPAI | E-Learning Platform");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icon.png")));
        stage.setResizable(false);

        AjoutBlogController controller = loader.getController();
        controller.initData(currentUser, "", "","","Ajouter",0);

        stage.show();
        
        
        
        
    }
    
    public void initData(User u){
        this.currentUser=u;
        BlogService ps = BlogService.getInstance();
        List<Blog> blogs = ps.getBlogsByUser(currentUser.getId().get());
        for (Blog p : blogs) {
            HBox hbox = new HBox(15);
            ImageView imagepost = new ImageView(p.getImage_blog());
            imagepost.setFitHeight(250);
            imagepost.setFitWidth(600);
            Label titre = new Label(p.gettitre());
            Label contenu = new Label(p.getcontenu());
            contenu.setWrapText(true);
            contenu.setMaxHeight(200);
            Button modif = new Button("Modifier");
            Button supp = new Button("Supprimer");
            Button imprim = new Button("Imprimer");
            hbox.getChildren().add(contenu);
            
            blogsVbox.getChildren().add(imagepost);
            blogsVbox.getChildren().add(hbox);
            blogsVbox.getChildren().add(modif);
            blogsVbox.getChildren().add(supp);
            blogsVbox.getChildren().add(imprim);
            modif.setOnAction(e -> {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ajoutBlog.fxml"));
                Stage stage = new Stage(StageStyle.DECORATED);
                try {
                    stage.setScene(
                            new Scene(loader.load())
                    );
                } catch (IOException ex) {
                    Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
                }
                stage.setTitle("E-SENPAI | E-Learning Platform");
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icon.png")));
                stage.setResizable(false);

                AjoutBlogController controller = loader.getController();
                controller.initData(currentUser, p.gettitre(), p.getcontenu(),p.getImage_blog(),"Modifier",p.getId_blog());

                stage.show();
            }
            );
            supp.setOnAction(e -> {
                ps.supprimerBlog(p);
                
                String title ="Blog supprimé avec succés. ";
               TrayNotification notif=new TrayNotification();
                AnimationType Type =AnimationType.POPUP;
                
                notif.setAnimationType(Type);    
        notif.setTitle(title);
        notif.setNotificationType(NotificationType.WARNING);
        notif.showAndDismiss(javafx.util.Duration.millis(3000));
        
        
                
                
                
                
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/suppBlog.fxml"));
//                Stage stage = new Stage(StageStyle.DECORATED);
//                try {
//                    stage.setScene(
//                            new Scene(loader.load())
//                    );
//                } catch (IOException ex) {
//                    Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                stage.setTitle("E-SENPAI | E-Learning Platform");
//                stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icon.png")));
//                stage.setResizable(false);
//                stage.show();
            });
            
            imprim.setOnAction(e -> {
                try {
                    printNode(blogsVbox);
                    
                    
                    
                    
                    
                    
                    
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/suppBlog.fxml"));
//                Stage stage = new Stage(StageStyle.DECORATED);
//                try {
//                    stage.setScene(
//                            new Scene(loader.load())
//                    );
//                } catch (IOException ex) {
//                    Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                stage.setTitle("E-SENPAI | E-Learning Platform");
//                stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icon.png")));
//                stage.setResizable(false);
//                stage.show();
                } catch (NoSuchMethodException ex) {
                    Logger.getLogger(ConsulterBlogController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(ConsulterBlogController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(ConsulterBlogController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(ConsulterBlogController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

        }
//        for (Blog p : blogs) {
//            ImageView imageblog = new ImageView(p.getImage_blog());
//            Label titre = new Label(p.gettitre());
//            Label contenu = new Label(p.getcontenu());
//            blogsVbox.getChildren().add(titre);
//            blogsVbox.getChildren().add(contenu);
//            blogsVbox.getChildren().add(imageblog);
//            
//            
//        }
    }

    void initData(User currentUser, String string, String string0, String string1, String ajouter, int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static void printNode(final Node node) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
    Printer printer = Printer.getDefaultPrinter();
    PageLayout pageLayout
        = printer.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.DEFAULT);
    PrinterAttributes attr = printer.getPrinterAttributes();
    PrinterJob job = PrinterJob.createPrinterJob();
    double scaleX
        = pageLayout.getPrintableWidth() / node.getBoundsInParent().getWidth();
    double scaleY
        = pageLayout.getPrintableHeight() / node.getBoundsInParent().getHeight();
    Scale scale = new Scale(scaleX, scaleY);
    node.getTransforms().add(scale);

    if (job != null && job.showPrintDialog(node.getScene().getWindow())) {
      boolean success = job.printPage(pageLayout, node);
      if (success) {
        job.endJob();

      }
    }
    node.getTransforms().remove(scale);
    }
    
}
