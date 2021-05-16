/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import database.Database;
import entities.Abonnement;
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

/**
 *
 * @author wajdi
 */
public class AbonnementService {

    private static AbonnementService instance;
    private Statement st;
    private ResultSet rs;

    public AbonnementService() {
        Database cs = Database.getInstance();
        try {
            st = cs.getConnection().createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(AbonnementService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static AbonnementService getInstance() {
        if (instance == null) {
            instance = new AbonnementService();
        }
        return instance;
    }

    public void abonneFormation(Abonnement ab) {
        String req = "insert into abonnement (id_formation, id_etudiant ) values ('" + ab.getId_formation().get() + "','" + ab.getId_etudiant().get() + "')";
        try {
            st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(AbonnementService.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public void DeleteAbonnement(Abonnement ab) {
        String req = "DELETE FROM abonnement WHERE id_formation = " + ab.getId_formation().get() + "&& id_etudiant = " + ab.getId_etudiant().get();
        try {
            st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(AbonnementService.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public List<Formation> getAllaAbonFormationByIdUser(Abonnement ab, Formation fo) {
        String req = "select * from abonnement a , formation f where a.id_etudiant =" + ab.getId_etudiant() + "&& a.id_formation = " + ab.getId_formation() + "&& f.id =" + fo.getId();
        List<Formation> list = new ArrayList<>();

        try {
            rs = st.executeQuery(req);
            while (rs.next()) {
                Formation p = new Formation();
                p.setId(rs.getInt(1));
                p.setId_formateur(rs.getInt(2));
                p.setTitre(rs.getString("titre"));
                p.setNote(rs.getInt("note"));
                p.setDescription(rs.getString("description"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(AbonnementService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Abonnement> getAllaAbonByIdUser(int id) {
        String req = "select * from abonnement where id_etudiant =" + id;
        List<Abonnement> list = new ArrayList<>();

        try {
            rs = st.executeQuery(req);
            while (rs.next()) {
                Abonnement ab = new Abonnement();
                ab.setId_formation(rs.getInt(1));
                ab.setId_etudiant(rs.getInt(2));
                ab.setDate_abonnement(rs.getString("date_abonnement"));
                list.add(ab);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AbonnementService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public Abonnement getAllaAbon(int id) {
        String req = "select * from abonnement where id_formation  =" + id;
        Abonnement ab = new Abonnement();
        try {
            rs = st.executeQuery(req);
            rs.next();
            ab.setId_formation(rs.getInt(1));

        } catch (SQLException ex) {
            Logger.getLogger(FormationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ab;
    }

    public void insertAbonnement(Abonnement a) {
        String reqis = "insert into abonnement (id_formation,id_etudiant,Rated)values ('" + a.getId_formation().get() + "','" + a.getId_etudiant().get() + "','" + "1" + "')";
        try {
            st.executeUpdate(reqis);
        } catch (SQLException ex) {
            Logger.getLogger(SlideService.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public void DeleteAbonnementById(int id_formation, int id_etudiant) {
        String req = "DELETE FROM abonnement WHERE id_formation = " + id_formation + "&& id_etudiant  = " + id_etudiant;
        try {
            st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(SlideService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void DeleteAbonnementByIdFormation(int id_formation) {
        String req = "DELETE FROM abonnement WHERE id_formation = " + id_formation ;
        try {
            st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(SlideService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Abonnement getAbonnementById(int id_formation, int id_etudiant) {
        String req = "select * from abonnement where id_formation  = " + id_formation + "&& id_etudiant  = " + id_etudiant;
        Abonnement a = new Abonnement();
        try {
            rs = st.executeQuery(req);
            rs.next();
            a.setId_formation(rs.getInt(1));
            a.setId_etudiant(rs.getInt(2));
            a.setDate_abonnement(rs.getString("date_abonnement"));
        } catch (SQLException ex) {

        }
        return a;

    }
   public Abonnement getAbonnementByIdRated(int id_formation, int id_etudiant) {
        String req = "select * from abonnement where id_formation  = " + id_formation + "&& id_etudiant  = " + id_etudiant;
        Abonnement a = new Abonnement();
        try {
            rs = st.executeQuery(req);
            rs.next();
            a.setId_formation(rs.getInt(1));
            a.setId_etudiant(rs.getInt(2));
            a.setDate_abonnement(rs.getString("date_abonnement"));
            a.setRated(rs.getInt("rated"));
        } catch (SQLException ex) {

        }
        return a;

    }
    public List<Abonnement> getAllaAbonByIdFormation(int id) {
        String req = "select * from abonnement where id_formation =" + id;
        List<Abonnement> list = new ArrayList<>();

        try {
            rs = st.executeQuery(req);
            while (rs.next()) {
                Abonnement ab = new Abonnement();
                ab.setId_formation(rs.getInt(1));
                ab.setId_etudiant(rs.getInt(2));
                ab.setDate_abonnement(rs.getString("date_abonnement"));
                list.add(ab);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AbonnementService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void updateRated(int id, int id_user) {

        String req = "update abonnement set rated ='" + "0" + "'where id_formation ='" + id + "'&& id_etudiant  = '" + id_user + "'";

        try {
            st.executeUpdate(req);

        } catch (SQLException ex) {
            Logger.getLogger(AbonnementService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
