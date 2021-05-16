/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author damos
 */
public class Session { 
    private static User user;

    public Session() {
    }

    public  static User getUser() {
        return Session.user;
    }

    public  static void setUser(User u) {
        Session.user = u;
    }
}
