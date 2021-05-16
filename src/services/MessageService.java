/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import database.Database;
import entities.MyMessage;
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
public class MessageService {

    private static MessageService instance;
    private Statement st;
    private ResultSet rs;

    private MessageService() {
        Database cs = Database.getInstance();
        try {
            st = cs.getConnection().createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(MessageService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static MessageService getInstance() {
        if (instance == null) {
            instance = new MessageService();
        }
        return instance;
    }

    public List<MyMessage> getAllMessage(int id) {
        String req = "select * from message where id_discussion=" + id + " ORDER BY date_msg ";
        List<MyMessage> list = new ArrayList<MyMessage>();

        try {
            rs = st.executeQuery(req);
            while (rs.next()) {
                MyMessage m = new MyMessage();
                m.setId(rs.getInt("id"));
                m.setUser(rs.getInt("id_user_emetteur"));
                m.setDiscussion(rs.getInt("id_discussion"));
                m.setContenu(rs.getString("contenu"));
                m.setDate_msg(rs.getTimestamp("date_msg"));

                list.add(m);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void sendMsg(MyMessage m) {
        String req = "insert into message (id_user_emetteur,id_discussion,contenu) values (" + m.getUser() + ",'" + m.getDiscussion() + "','" + m.getContenu() + "')";
        try {
            st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(MessageService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void suppMsg(int id) {
        String req = "DELETE FROM message WHERE id =" + id;
        try {
            st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(MessageService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public MyMessage getLastMsg(int id) {
        String req = " Select * from message where id_discussion =" + id + " order by date_msg desc limit 1";
        MyMessage m = new MyMessage();
        try {
            rs = st.executeQuery(req);
            while (rs.next()) {
                m.setId(rs.getInt("id"));
                m.setUser(rs.getInt("id_user_emetteur"));
                m.setContenu(rs.getString("contenu"));
                m.setDiscussion(rs.getInt("id_discussion"));
                m.setDate_msg(rs.getTimestamp("date_msg"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MessageService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return m;
    }
//    

}
