/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import com.twilio.type.PhoneNumber;
import java.sql.Timestamp;
import java.util.Objects;

/**
 *
 * @author 21656
 */
public class MyMessage {

    

    private int id;
    private int id_user_emetteur;
    private int id_discussion;
    private String contenu;
    private Timestamp date_msg;

    public MyMessage() {

    }

    public int getId() {
        return id;
    }

    public int getUser() {
        return id_user_emetteur;
    }

    public int getDiscussion() {

        return id_discussion;
    }

    public String getContenu() {

        return contenu;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(int id_user_emetteur) {
        this.id_user_emetteur = id_user_emetteur;
    }

    public Timestamp getDate_msg() {
        return date_msg;
    }

    public void setDate_msg(Timestamp date_msg) {
        this.date_msg = date_msg;
    }

    @Override
    public String toString() {
        return "Message{" + "id=" + id + ", id_user_emetteur=" + id_user_emetteur + ", id_discussion=" + id_discussion + ", contenu=" + contenu + " , date = " + date_msg + "}";
    }

    public void setDiscussion(int id_discussion) {
        this.id_discussion = id_discussion;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MyMessage other = (MyMessage) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
