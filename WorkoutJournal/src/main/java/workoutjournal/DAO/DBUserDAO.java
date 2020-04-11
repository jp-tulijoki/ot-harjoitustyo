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

/**
 *
 * @author tulijoki
 */
public class DBUserDAO implements UserDAO {
    
    Connection conn;

    public DBUserDAO(Connection conn) {
        this.conn = conn;
    }
        
    @Override
    public void createUser(String username, String password, int maxHeartRate) throws SQLException {
        PreparedStatement p = conn.prepareStatement("INSERT INTO Users(username, password, maxHeartRate) VALUES (?, ?, ?)");
        p.setString(1, username);
        p.setString(2, password);
        p.setInt(3, maxHeartRate);
        p.executeUpdate();
    }
    
    public void deleteUser(String username) throws SQLException {
        PreparedStatement p = conn.prepareStatement("DELETE FROM Users WHERE username = ?");
        p.setString(1, username);
        p.executeUpdate();
    }
    
    public User getUserCredentials(String username) throws SQLException {
        PreparedStatement p = conn.prepareStatement("SELECT id, username, password, maxHeartRate FROM users WHERE username = ?");
        p.setString(1, username);
        ResultSet r = p.executeQuery();
        if (!(r.next())) {
            return null;
        }
        User user = new User(r.getInt("id"), r.getString("username"), r.getString("password"), r.getInt("maxHeartRate"));
        return user;
    }
}
