/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import database.Database;
import entities.Discussion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 21656
 */
public class DiscussionService {

    private static DiscussionService instance;
    private Statement st;
    private ResultSet rs;

    private DiscussionService() {
        Database cs = Database.getInstance();
        try {
            st = cs.getConnection().createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(DiscussionService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static DiscussionService getInstance() {
        if (instance == null) {
            instance = new DiscussionService();
        }
        return instance;
    }

    public List<Discussion> getAllDiscussion(int id) {
        String req = "select * from discussion where id_user1 =" + id + " or id_user2=" + id + " ORDER BY date_discussion DESC";
        List<Discussion> list = new ArrayList<Discussion>();

        try {
            rs = st.executeQuery(req);
            while (rs.next()) {
                Discussion p = new Discussion();
                p.setId(rs.getInt("id"));
                p.setUser1(rs.getInt("id_user1"));
                p.setUser2(rs.getInt("id_user2"));
                p.setDate_discussion(rs.getTimestamp("date_discussion"));
                list.add(p);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public Discussion getDiscussionbytwo(int id, int id1) {
        String req = "select * from discussion where id_user1 =" + id + " and id_user2=" + id1 + " or id_user1=" + id1 + " and id_user2=" + id + " ORDER BY date_discussion DESC";
        Discussion p = new Discussion();

        try {
            rs = st.executeQuery(req);
            while (rs.next()) {

                p.setId(rs.getInt("id"));
                p.setUser1(rs.getInt("id_user1"));
                p.setUser2(rs.getInt("id_user2"));
                p.setDate_discussion(rs.getTimestamp("date_discussion"));
                return p;
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }

    public boolean verif(int x1, int x2) {
        String req = "select * from discussion where (id_user1 =" + x1 + " and id_user2=" + x2 + ") or (id_user1=" + x2 + " and id_user2=" + x1 + ")";
        List<Discussion> list = new ArrayList<Discussion>();

        try {
            rs = st.executeQuery(req);
            while (rs.next()) {
                return false;
            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void insertDiscussion(Discussion d) {
        if (verif(d.getUser1(), d.getUser2())) {
            String req = "insert into Discussion (id_user1,id_user2) values (" + d.getUser1() + "," + d.getUser2() + ")";
            try {
                st.executeUpdate(req);
            } catch (SQLException ex) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);

            }
        }

    }

    public boolean messagesById(int id) {
        String req = "Select * from message where id_discussion=" + id;
        try {
            rs=st.executeQuery(req);
            while (rs.next()) {
                return true;
            }
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);

        }
        return true;
    }
    
    public void deleteDiscussion(int id){
        String req="DELETE FROM discussion WHERE id="+id;
        try {
                st.executeUpdate(req);
            } catch (SQLException ex) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

}
