/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workoutjournal.domain;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import workoutjournal.DAO.DAO;
import workoutjournal.DAO.UserDAO;
import workoutjournal.domain.Sex;

/**
 *
 * @author tulijoki
 */
public class JournalTools {
    private DAO DAO;
    private UserDAO userDAO;

    public JournalTools(DAO DAO, UserDAO userDAO) {
        this.DAO = DAO;
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
            if (userDAO.findUser(username)) {
                return false;
            } else {
                userDAO.createUser(username, name, age, sex, countMaxHeartRate(age, sex));
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(JournalTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean deleteUser(String username) {
        try {
            if (userDAO.findUser(username) == false) {
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
    
    public void closeDatabaseConnection() {
        try {
            userDAO.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(JournalTools.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void getDatabaseConnection() {
        try {
            userDAO.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(JournalTools.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
