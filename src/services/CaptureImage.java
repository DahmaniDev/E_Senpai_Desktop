/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

/**
 *
 * @author Dahmani
 */
public class CaptureImage {
    
    Mat matrix = null; 
    
    public WritableImage captureSnapShot() { 
      WritableImage WritableImage = null; 

      //Loading the OpenCV core library  
      System.loadLibrary(Core.NATIVE_LIBRARY_NAME );        

      //Instantiating the VideoCapture class (camera:: 0) 
      VideoCapture capture = new VideoCapture(0);     

      //Reading the next video frame from the camera 
      Mat matrix = new Mat(); 
      capture.read(matrix); 

      //If camera is opened  
      if(capture.isOpened()) { 
         
         //If there is next video frame 
         if (capture.read(matrix)) {      
            
            //Creating BuffredImage from the matrix 
            BufferedImage image = new BufferedImage(matrix.width(), matrix.height(), 
               BufferedImage.TYPE_3BYTE_BGR); 
            
            WritableRaster raster = image.getRaster(); 
            DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer(); 
            byte[] data = dataBuffer.getData(); 
            matrix.get(0, 0, data); 
            this.matrix = matrix; 

            //Creating the Writable Image 
            WritableImage = SwingFXUtils.toFXImage(image, null); 
         } 
      }
      capture.release();
      return WritableImage; 
   }
    
   public void saveImage() {
      // Saving the Image
      String file = System.getProperty("user.dir") + "\\src\\assets\\sanpshot.jpg";

      // Instantiating the imgcodecs class
      Imgcodecs imageCodecs = new Imgcodecs();

      // Saving it again 
      imageCodecs.imwrite(file, matrix);
   }
   
   public void deleteFile(){
       String file = System.getProperty("user.dir") + "\\src\\assets\\sanpshot.jpg";
       
       File x = new File(file);
       
       if(x.delete())
        {
            System.out.println("File deleted successfully");
        }
        else
        {
            System.out.println("Failed to delete the file");
        }
   }
}
