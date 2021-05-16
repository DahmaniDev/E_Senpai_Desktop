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
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
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
public class AjoutBlogController implements Initializable {

    @FXML
    private Button ajoutOk;
    @FXML
    private TextField inputContenu;
    @FXML
    private TextField inputTitre;
    @FXML
    private TextField inputImage;
    
    int User_id;
    
    int idBlog;
    
    private User currentUser;


    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleAjoutOk(ActionEvent event) throws IOException {
        String titre = inputTitre.getText();
        String contenu = inputContenu.getText();
        String image_post = inputImage.getText();
        Blog p = new Blog();
        if(idBlog!=0){
            p.setId_blog(idBlog);
        }
        p.setcontenu(contenu);
        p.settitre(titre);
        p.setImage_blog(image_post);
        p.setId_user(User_id);
        BlogService ps = BlogService.getInstance();
        
        if(ajoutOk.getText().toLowerCase().equals("ajouter")){
            ps.ajouterBlog(p);
            
            String title ="Blog ajouté avec succés. ";
               TrayNotification notif=new TrayNotification();
                AnimationType Type =AnimationType.POPUP;
                
                notif.setAnimationType(Type);    
        notif.setTitle(title);
        notif.setNotificationType(NotificationType.SUCCESS);
        notif.showAndDismiss(javafx.util.Duration.millis(3000));
            
            Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                    oldStage.close();
                                    
               
        
        
        
        
        Parent loader = FXMLLoader.load(getClass().getResource("Accueil.fxml"));
            //  Parent root  = loader.load();
            Scene  scene = new Scene(loader);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("E-SENPAI | E-Learning Platform");
            window.setScene(scene);
            window.close();
        }
        
        
        
        
//Aa
//        
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Accueil.fxml"));
//        Stage stage = new Stage(StageStyle.DECORATED);
//                try {
//                    stage.setScene(
//                            new Scene(loader.load())
//                    );      } catch (IOException ex) {
//                    Logger.getLogger(AjoutBlogController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//        stage.setTitle("E-SENPAI | E-Learning Platform");
//        stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icon.png")));
//        stage.setResizable(false);
//
//        AccueilController controller = loader.getController();
//        controller.initData(currentUser, "", "","","Ajouter",0);
//
//        stage.show();
//        }
//        );
    
    
            
        
        else {
            ps.modifierBlog(p);
            
            String title ="Blog modifié avec succés. ";
               TrayNotification notif=new TrayNotification();
                AnimationType Type =AnimationType.POPUP;
                
                notif.setAnimationType(Type);    
        notif.setTitle(title);
        notif.setNotificationType(NotificationType.NOTICE);
        notif.showAndDismiss(javafx.util.Duration.millis(3000));
        
        Parent loader = FXMLLoader.load(getClass().getResource("Accueil.fxml"));
            //  Parent root  = loader.load();
            Scene  scene = new Scene(loader);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("E-SENPAI | E-Learning Platform");
            window.setScene(scene);
            window.close();
        
        
            
            
        }
    }
    
    public void initData(User u, String titre, String contenu,String url, String action, int idBlog) {
          User_id=u.getId().get();
          this.inputTitre.setText(titre);
          this.inputContenu.setText(contenu);
          this.inputImage.setText(url);
          ajoutOk.setText(action);
          this.idBlog=idBlog;
    }

}
