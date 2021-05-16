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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author wajdi
 */
public class ImageSlideController implements Initializable {
    User currentUser;
    Formation f;
    Slide s;
    String path;
    @FXML
    private ImageView ImageId;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
   public void setData( Slide slide)   {
       System.out.println(slide.getId().get());
         path = "src\\contenu\\" + slide.getImage_slide().get();
        ImageView image = new ImageView(path);

        ImageId.setImage(image.getImage());
    }  
}
