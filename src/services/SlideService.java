/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import database.Database;
import entities.Formation;
import entities.Slide;
import entities.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author wajdi
 */
public class SlideService {

    private static SlideService instance;
    private Statement st;
    private ResultSet rs;

    private SlideService() {
        Database cs = Database.getInstance();
        try {
            st = cs.getConnection().createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(SlideService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static SlideService getInstance() {
        if (instance == null) {
            instance = new SlideService();
        }
        return instance;
    }
    String req = "select * from slide s , formation f where f.id = s.id";

    public ObservableList<Slide> getAllSlideByFormation(int id) {
        String req = "select * from slide where id_formation =" + id;
        ObservableList<Slide> list = FXCollections.observableArrayList();
        try {
            rs = st.executeQuery(req);
            while (rs.next()) {
                Slide s = new Slide();
                s.setId(rs.getInt(1));
                s.setId_formation(rs.getInt(2));
                s.setVideo_slide(rs.getString("video_slide"));
                s.setImage_slide(rs.getString("image_slide"));
                s.setText_slide(rs.getString("text_slide"));
                s.setOrdre(rs.getInt("ordre"));
                list.add(s);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SlideService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Slide> getAllSlideByFormationlise(int id) {
        String req = "select * from slide where id_formation =" + id;
        List<Slide> list = new ArrayList<>();
        try {
            rs = st.executeQuery(req);
            while (rs.next()) {
                Slide s = new Slide();
                s.setId(rs.getInt(1));
                s.setId_formation(rs.getInt(2));
                s.setVideo_slide(rs.getString("video_slide"));
                s.setImage_slide(rs.getString("image_slide"));
                s.setText_slide(rs.getString("text_slide"));
                s.setOrdre(rs.getInt("ordre"));
                list.add(s);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SlideService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public Slide getSlidebyID(int id) {
        String req = "select * from slide where id = " + id;
        Slide s = new Slide();
        try {
            rs = st.executeQuery(req);
            rs.next();
            s.setId(rs.getInt(1));
            s.setId_formation(rs.getInt(2));
            s.setVideo_slide(rs.getString("video_slide"));
            s.setImage_slide(rs.getString("image_slide"));
            s.setText_slide(rs.getString("text_slide"));
            s.setOrdre(rs.getInt("ordre"));

        } catch (SQLException ex) {
            Logger.getLogger(FormationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }

    public void insertSlide(Slide s) {
        String reqis = "insert into slide (id_formation,video_slide,image_slide,text_slide,ordre)values ('" + s.getId_formation().get() + "','" + s.getVideo_slide().get() + "','" + s.getImage_slide().get() + "','" + s.getText_slide().get() + "','" + s.getOrdre().get() + "')";
        try {
            st.executeUpdate(reqis);
        } catch (SQLException ex) {
            Logger.getLogger(SlideService.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public void DeleteSlideByid(int id_slide) {
        String req = "DELETE FROM slide WHERE id = " + id_slide;
        try {
            st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(SlideService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void DeleteSlide(int id_formation) {
        String req = "DELETE FROM slide WHERE id_formation = " + id_formation;
        try {
            st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(SlideService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void UpdateSlide(Slide s) {
        String req = "update slide set id_formation  ='" + s.getId_formation().get() + "',video_slide ='" + s.getVideo_slide().get() + "',image_slide ='" + s.getImage_slide().get() + "',text_slide ='" + s.getText_slide().get() + "',ordre ='" + s.getOrdre().get() + "'where id='" + s.getId().get() + "'";

        try {
            st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(SlideService.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
}
