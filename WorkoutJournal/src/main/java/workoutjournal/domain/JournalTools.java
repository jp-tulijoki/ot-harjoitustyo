/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workoutjournal.domain;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import workoutjournal.DAO.UserDAO;
//import workoutjournal.DAO.DBUserDAO;
import workoutjournal.domain.Sex;
import workoutjournal.domain.User;

/**
 *
 * @author tulijoki
 */
public class JournalTools {
    private UserDAO userDAO;
    private User loggedIn;

    public JournalTools(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
        
    public int countMaxHeartRate(int age, Sex sex) {
        if (sex == Sex.female) {
            return 206 - Integer.valueOf((int) (0.8 * age));
        } else {
            return 220 - age;
        }
    }
    
    public boolean createUser(String username, String name, int age, Sex sex) {
        try {
            if (!(userDAO.getUserCredentials(username) == null)) {
                return false;
            } else {
                userDAO.createUser(username, name, countMaxHeartRate(age, sex));
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(JournalTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean deleteUser(String username) {
        try {
            if (userDAO.getUserCredentials(username) == null) {
                return false;
            } else {
                userDAO.deleteUser(username);
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(JournalTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean login(String username) {
        try {
            if (userDAO.getUserCredentials(username) == null) {
                return false;
            }
            loggedIn = userDAO.getUserCredentials(username);
        } catch (SQLException ex) {
            Logger.getLogger(JournalTools.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public void logout() {
        loggedIn = null;
    }
    
    public User getLoggedUser() {
        return loggedIn;
    }

}
