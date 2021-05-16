/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import javafx.application.HostServices;

/**
 *
 * @author Dahmani
 */
public class Host {
    
    private HostServices hostServices;
    private static Host instance;
    
    private Host(){
        
    }
    
    public static Host getInstance(){
        if(instance==null){
            instance=new Host();
        }
        return instance;
    }
    
    public void setHostServices(HostServices x){
        this.hostServices=x;
    }
    
    public HostServices getHostServices(){
        return this.hostServices;
    }
    
}
