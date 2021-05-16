/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author 21656
 */
public class Discussion {

    private int id;
    private int id_user1;
    private int id_user2;
    private Timestamp date_discussion;

    public Discussion() {

    }

    public int getId() {
        return id;
    }

    public int getUser1() {
        return id_user1;
    }

    public int getUser2() {

        return id_user2;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser1(int id_user1) {
        this.id_user1 = id_user1;
    }

    public void setUser2(int id_user2) {
        this.id_user2 = id_user2;
    }

    public Timestamp getDate_discussion() {
        return date_discussion;
    }

    public void setDate_discussion(Timestamp date_discussion) {
        this.date_discussion = date_discussion;
    }

    @Override
    public String toString() {
        return "Discussion{" + "id=" + id + ", id_user1=" + id_user1 + ", id_user2=" + id_user2 + ", date=" + date_discussion + "}";
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Discussion other = (Discussion) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
