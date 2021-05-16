/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Post;
import entities.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.MailingP;
import services.PostService;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Methnani
 */
public class PostController implements Initializable {

    @FXML
    private TextField caption;
    @FXML
    private TextField image;
    @FXML
    private Button addbutton;

    int User_id;
    
    int idPost;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void AddPost(ActionEvent event) throws Exception {

        String Caption = caption.getText();
        String image_post = image.getText();
        Post p = new Post();
        if(idPost!=0){
            p.setId_post(idPost);
        }
        p.setCaption(Caption);
        p.setImage_poste(image_post);
        p.setId_user(User_id);
        PostService ps = PostService.getInstance();
        if(addbutton.getText().toLowerCase().equals("ajouter")){
            ps.ajouterPost(p);
            Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                    oldStage.close();
                                    
               MailingP.sendMail("amani.methnani@esprit.tn");
                                    
               String title ="Post ajouté avec succés.\n Mail envoyé avec succées. ";
               TrayNotification notif=new TrayNotification();
                AnimationType Type =AnimationType.POPUP;
                
                notif.setAnimationType(Type);    
        notif.setTitle(title);
        notif.setNotificationType(NotificationType.SUCCESS);
        notif.showAndDismiss(javafx.util.Duration.millis(3000));
        }
        else{
            ps.modifierPost(p);
            
            String title ="Post modifié avec succés. ";
               TrayNotification notif=new TrayNotification();
                AnimationType Type =AnimationType.POPUP;
                
                notif.setAnimationType(Type);    
        notif.setTitle(title);
        notif.setNotificationType(NotificationType.NOTICE);
        notif.showAndDismiss(javafx.util.Duration.millis(3000));
            
            Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                    oldStage.close();
        }
        

//            Parent loader = FXMLLoader.load(getClass().getResource("Showpost.fxml"));
//            //  Parent root  = loader.load();
//            Scene  scene = new Scene(loader);
//            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            window.setTitle("Les Posts");
//            window.setScene(scene);
//            window.show();
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();

    }

    public void initData(User u, String caption, String url, String action, int idPost) {
          User_id=u.getId().get();
          this.caption.setText(caption);
          this.image.setText(url);
          addbutton.setText(action);
          this.idPost=idPost;
          
    }

}
