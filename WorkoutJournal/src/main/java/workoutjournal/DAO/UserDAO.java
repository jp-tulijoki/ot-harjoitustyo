/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workoutjournal.DAO;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import workoutjournal.domain.User;
import workoutjournal.domain.Sex;

/**
 *
 * @author tulijoki
 */
public class UserDAO extends DAO {
        
    public void createUser(String username, String name, int age, Sex sex, int maxHeartRate) throws SQLException {
        p = conn.prepareStatement("INSERT INTO Users(username, name, age, sex, maxHeartRate) VALUES (?, ?, ?, ?, ?)");
        p.setString(1, username);
        p.setString(2, name);
        p.setInt(3, age);
        p.setInt(4, sex.ordinal());
        p.setInt(5, maxHeartRate);
        p.executeUpdate();
    }
    
    public boolean findUser(String username) throws SQLException {
        p = conn.prepareStatement("SELECT username FROM Users WHERE username = ?");
        p.setString(1, username);
        ResultSet r = p.executeQuery();
        if (r.next()) {
            return true;
        } else {
            return false;
        }
    }
    
    public void deleteUser(String username) throws SQLException {
        p = conn.prepareStatement("DELETE FROM Users WHERE username = ?");
        p.setString(1, username);
        p.executeUpdate();
    }
}
