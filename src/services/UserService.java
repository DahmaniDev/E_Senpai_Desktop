/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import database.Database;
import entities.User;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Dahmani
 */
public class UserService {

    private static UserService instance;
    private Statement st;
    private ResultSet rs;
    final String secretKey = "ssshhhhhhhhhhh!!!!";

    private UserService() {
        Database cs = Database.getInstance();
        try {
            st = cs.getConnection().createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    //méthodes de crud
    public List<User> getAllUsers() {
        String req = "select * from user";
        List<User> list = new ArrayList<>();

        try {
            rs = st.executeQuery(req);
            while (rs.next()) {
                User p = new User();
                p.setId(rs.getInt(1));
                p.setNom(rs.getString("nom"));
                p.setPrenom(rs.getString("prenom"));
                p.setLogin(rs.getString("login"));
                p.setPassword(AES.decrypt(rs.getString("password"), secretKey));
                p.setStatus(rs.getString("status"));
                p.setRole(rs.getString("role"));
                p.setPhoto_profil(rs.getString("photo_profil"));
                p.setDate_naissance(rs.getString("date_naissance"));
                p.setEmail(rs.getString("email"));
                p.setSexe(rs.getString("sexe"));
                p.setBiography(rs.getString("biography"));
                p.setCv(rs.getString("curriculum_vitae"));

                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    

    public void insertEtudiant(User o) throws NoSuchAlgorithmException {
        String pass = AES.encrypt(o.getPassword().get(), secretKey);

        String req = "insert into user (nom,prenom,date_naissance,sexe,email,role,login,password,status,photo_profil,biography,curriculum_vitae) values ('" + o.getNom().get() + "','" + o.getPrenom().get() + "','" + o.getDate_naissance().get() + "','" + o.getSexe().get() + "','" + o.getEmail().get() + "','Etudiant','" + o.getLogin().get() + "','" + pass + "','Approuvé','','','')";
        try {
            st.executeUpdate(req);

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public List<User> getUserNameByID(int id) {
        String req = "select nom from user where id =" + id;
        List<User> list = new ArrayList<>();

        try {
            rs = st.executeQuery(req);
            while (rs.next()) {
                User p = new User();
                p.setId(rs.getInt(1));
                p.setNom(rs.getString("nom"));
                list.add(p);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void insertFormateur(User o) throws NoSuchAlgorithmException {
        String pass = AES.encrypt(o.getPassword().get(), secretKey);
        String req = "insert into user (nom,prenom,date_naissance,sexe,email,role,login,password,status,photo_profil,biography,curriculum_vitae) values ('" + o.getNom().get() + "','" + o.getPrenom().get() + "','" + o.getDate_naissance().get() + "','" + o.getSexe().get() + "','" + o.getEmail().get() + "','Formateur','" + o.getLogin().get() + "','" + pass + "','En attente','','','" + o.getCv().get() + "')";
        try {
            st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public void insertAdmin(User o) throws NoSuchAlgorithmException {
        String pass = AES.encrypt(o.getPassword().get(), secretKey);
        String req = "insert into user (nom,prenom,date_naissance,sexe,email,role,login,password,status,photo_profil,biography,curriculum_vitae) values ('" + o.getNom().get() + "','" + o.getPrenom().get() + "','" + o.getDate_naissance().get() + "','" + o.getSexe().get() + "','" + o.getEmail().get() + "','Admin','" + o.getLogin().get() + "','" + pass + "','Approuvé','','','')";
        try {
            st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public boolean deleteUser(int o) {
        String req = "DELETE FROM user WHERE id=" + o;
        try {
            st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public boolean isValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."
                + "[a-zA-Z0-9_+&*-]+)*@"
                + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                + "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }
        return pat.matcher(email).matches();
    }

    public void modifierUser(User u) {
        String pass = AES.encrypt(u.getPassword().get(), secretKey);
        String req = "UPDATE user SET nom='" + u.getNom().get() + "',prenom='" + u.getPrenom().get() + "',date_naissance='" + u.getDate_naissance().get() + "',sexe='" + u.getSexe().get() + "',email='" + u.getEmail().get() + "',login='" + u.getLogin().get() + "',password='" + pass + "', photo_profil='" + u.getPhoto_profil().get() + "',biography='" + u.getBiography().get() + "',curriculum_vitae='" + u.getCv().get() + "' WHERE id=" + u.getId().get();
        try {
            st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    public void supprimerUser(User u) {
        String req = "DELETE FROM user WHERE id=" + u.getId();
        try {
            st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ObservableList<User> displayAll() {
        String req = "select * from user";
        ObservableList<User> list = FXCollections.observableArrayList();

        try {
            rs = st.executeQuery(req);
            while (rs.next()) {
                User p = new User();
                p.setId(rs.getInt(1));
                p.setNom(rs.getString("nom"));
                p.setPrenom(rs.getString("prenom"));
                p.setLogin(rs.getString("login"));
                p.setPassword(AES.decrypt(rs.getString("password"), secretKey));
                p.setStatus(rs.getString("status"));
                p.setRole(rs.getString("role"));
                p.setPhoto_profil(rs.getString("photo_profil"));
                p.setDate_naissance(rs.getString("date_naissance"));
                p.setEmail(rs.getString("email"));
                p.setSexe(rs.getString("sexe"));
                p.setCv(rs.getString("curriculum_vitae"));
                list.add(p);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ObservableList<User> getUsersByNameObservable(String name) {
        String req = "select * from user where nom LIKE '%" + name + "%'";
        ObservableList<User> list = FXCollections.observableArrayList();

        try {
            rs = st.executeQuery(req);
            while (rs.next()) {
                User p = new User();
                p.setId(rs.getInt(1));
                p.setNom(rs.getString("nom"));
                p.setPrenom(rs.getString("prenom"));
                p.setLogin(rs.getString("login"));
                p.setPassword(rs.getString("password"));
                p.setPassword(AES.decrypt(rs.getString("password"), secretKey));
                p.setStatus(rs.getString("status"));
                p.setRole(rs.getString("role"));
                p.setPhoto_profil(rs.getString("photo_profil"));
                p.setDate_naissance(rs.getString("date_naissance"));
                p.setEmail(rs.getString("email"));
                p.setSexe(rs.getString("sexe"));
                p.setCv(rs.getString("curriculum_vitae"));
                list.add(p);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<User> searchUser(String name) {
        String req = "select * from user where (nom LIKE '%" + name + "%' OR prenom LIKE '%" + name + "%') AND role NOT LIKE 'Admin' LIMIT 2";
        List<User> list = new ArrayList<>();

        try {
            rs = st.executeQuery(req);
            while (rs.next()) {
                User p = new User();
                p.setId(rs.getInt(1));
                p.setNom(rs.getString("nom"));
                p.setPrenom(rs.getString("prenom"));
                p.setLogin(rs.getString("login"));
                p.setPassword(AES.decrypt(rs.getString("password"), secretKey));
                p.setStatus(rs.getString("status"));
                p.setRole(rs.getString("role"));
                p.setPhoto_profil(rs.getString("photo_profil"));
                p.setDate_naissance(rs.getString("date_naissance"));
                p.setEmail(rs.getString("email"));
                p.setSexe(rs.getString("sexe"));
                p.setCv(rs.getString("curriculum_vitae"));
                list.add(p);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<User> getUsersByName(String name) {
        String req = "select * from user where nom='" + name + "'";
        List<User> list = new ArrayList<>();

        try {
            rs = st.executeQuery(req);
            while (rs.next()) {
                User p = new User();
                p.setId(rs.getInt(1));
                p.setNom(rs.getString("nom"));
                p.setPrenom(rs.getString("prenom"));
                p.setLogin(rs.getString("login"));
                p.setPassword(AES.decrypt(rs.getString("password"), secretKey));
                p.setStatus(rs.getString("status"));
                p.setRole(rs.getString("role"));
                p.setPhoto_profil(rs.getString("photo_profil"));
                p.setDate_naissance(rs.getString("date_naissance"));
                p.setEmail(rs.getString("email"));
                p.setSexe(rs.getString("sexe"));
                p.setCv(rs.getString("curriculum_vitae"));
                list.add(p);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public User getUserById(int id) {
        String req = "select * from user where id='" + id + "'";

        User p = new User();
        try {
            rs = st.executeQuery(req);
            while (rs.next()) {

                p.setId(rs.getInt(1));
                p.setNom(rs.getString("nom"));
                p.setPrenom(rs.getString("prenom"));
                p.setLogin(rs.getString("login"));
                p.setPassword(AES.decrypt(rs.getString("password"), secretKey));
                p.setStatus(rs.getString("status"));
                p.setRole(rs.getString("role"));
                p.setPhoto_profil(rs.getString("photo_profil"));
                p.setDate_naissance(rs.getString("date_naissance"));
                p.setEmail(rs.getString("email"));
                p.setSexe(rs.getString("sexe"));
                p.setBiography(rs.getString("biography"));
                p.setCv(rs.getString("curriculum_vitae"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }

    public User getUsetByLogin(String log) {
        String req = "select * from user where login='" + log + "'";

        User p = new User();
        try {
            rs = st.executeQuery(req);
            while (rs.next()) {

                p.setId(rs.getInt(1));
                p.setNom(rs.getString("nom"));
                p.setPrenom(rs.getString("prenom"));
                p.setLogin(rs.getString("login"));
                p.setPassword(AES.decrypt(rs.getString("password"), secretKey));
                p.setStatus(rs.getString("status"));
                p.setRole(rs.getString("role"));
                p.setPhoto_profil(rs.getString("photo_profil"));
                p.setDate_naissance(rs.getString("date_naissance"));
                p.setEmail(rs.getString("email"));
                p.setSexe(rs.getString("sexe"));
                p.setBiography(rs.getString("biography"));
                p.setCv(rs.getString("curriculum_vitae"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }

    public User getUsetByEmail(String log) {
        String req = "select * from user where email='" + log + "'";

        User p = new User();
        try {
            rs = st.executeQuery(req);
            while (rs.next()) {
                p.setId(rs.getInt(1));
                p.setNom(rs.getString("nom"));
                p.setPrenom(rs.getString("prenom"));
                p.setLogin(rs.getString("login"));
                p.setPassword(AES.decrypt(rs.getString("password"), secretKey));
                p.setStatus(rs.getString("status"));
                p.setRole(rs.getString("role"));
                p.setPhoto_profil(rs.getString("photo_profil"));
                p.setDate_naissance(rs.getString("date_naissance"));
                p.setEmail(rs.getString("email"));
                p.setSexe(rs.getString("sexe"));
                p.setBiography(rs.getString("biography"));
                p.setCv(rs.getString("curriculum_vitae"));
            }
            return p;
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        p.setNom("");
        p.setPrenom("");
        p.setLogin("");
        p.setPassword("");
        p.setStatus("");
        p.setRole("");
        p.setPhoto_profil("");
        p.setDate_naissance("");
        p.setEmail("");
        p.setSexe("");
        p.setBiography("");
        p.setCv("");
        return p;
    }

    public ObservableList<User> displayFormateursDemandes() {
        String req = "select * from user where lower(status) = 'en attente'";
        ObservableList<User> list = FXCollections.observableArrayList();

        try {
            rs = st.executeQuery(req);
            while (rs.next()) {
                User p = new User();
                p.setId(rs.getInt(1));
                p.setNom(rs.getString("nom"));
                p.setPrenom(rs.getString("prenom"));
                p.setLogin(rs.getString("login"));
                p.setPassword(AES.decrypt(rs.getString("password"), secretKey));
                p.setStatus(rs.getString("status"));
                p.setRole(rs.getString("role"));
                p.setPhoto_profil(rs.getString("photo_profil"));
                p.setDate_naissance(rs.getString("date_naissance"));
                p.setEmail(rs.getString("email"));
                p.setSexe(rs.getString("sexe"));
                p.setCv(rs.getString("curriculum_vitae"));
                list.add(p);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void updateStatus(int id) {
        String req = "UPDATE user SET status ='Approuvé' where id=" + id;
        try {
            st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    public boolean verifLogin(String login) {
        String req = "SELECT * FROM user WHERE login='" + login + "'";
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

    public boolean verifEmail(String login) {
        String req = "SELECT * FROM user WHERE email='" + login + "'";
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

    public int countUsers() {
        String req = "SELECT COUNT(id) FROM user";
        int sum = 0;
        try {
            rs = st.executeQuery(req);
            while (rs.next()) {
                sum = rs.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sum;
    }
    
    public ObservableList<User> displayAllbyId( int id) {
        String req="select * from user where id ="+id;
        ObservableList<User> list=FXCollections.observableArrayList();       
        
        try {
            rs=st.executeQuery(req);
            while(rs.next()){
                User p=new User();
                p.setId(rs.getInt(1));
                p.setNom(rs.getString("nom"));
                p.setPrenom(rs.getString("prenom"));
                p.setLogin(rs.getString("login"));
                p.setPassword(rs.getString("password"));
                p.setStatus(rs.getString("status"));
                p.setRole(rs.getString("role"));
                p.setPhoto_profil(rs.getString("photo_profil"));
                p.setDate_naissance(rs.getString("date_naissance"));
                p.setEmail(rs.getString("email"));
                p.setSexe(rs.getString("sexe"));
                list.add(p);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    
    public List<User> searchAllUsers(String s) {
        String req = "select * from user where nom like '%"+s+"%'";
        List<User> list = new ArrayList<>();

        try {
            rs = st.executeQuery(req);
            while (rs.next()) {
                User p = new User();
                p.setId(rs.getInt(1));
                p.setNom(rs.getString("nom"));
                p.setPrenom(rs.getString("prenom"));
                p.setLogin(rs.getString("login"));
                p.setPassword(rs.getString("password"));
                p.setStatus(rs.getString("status"));
                p.setRole(rs.getString("role"));
                list.add(p);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    

}
