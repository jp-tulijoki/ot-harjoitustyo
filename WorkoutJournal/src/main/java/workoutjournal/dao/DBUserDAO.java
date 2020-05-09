package workoutjournal.dao;
import java.sql.*;
import workoutjournal.domain.User;

/**
 * Database implementation of the UserDAO.
 */
public class DBUserDAO implements UserDAO {
    
    Connection conn;
    
    /**
     * The constructor defines the connection used.
     * @param conn the connection is defined when the program is started.
     */
    public DBUserDAO(Connection conn) {
        this.conn = conn;
    }
    
    /**
     * Method stores user credentials to the database.
     * 
     * @param username username submitted via JournalTools
     * @param password password submitted via JournalTools
     * @param maxHeartRate maxHeartRate submitted via JournalTools 
     * @throws SQLException 
     */
    @Override
    public void createUser(String username, String password, int maxHeartRate) throws SQLException {
        PreparedStatement p = conn.prepareStatement("INSERT INTO Users(username, password, maxHeartRate) VALUES (?, ?, ?)");
        p.setString(1, username);
        p.setString(2, password);
        p.setInt(3, maxHeartRate);
        p.executeUpdate();
    }
    
    /**
     * Method deletes user credentials of a certain user from the database.
     * @param username username submitted via JournalTools
     * @throws SQLException 
     */
    public void deleteUser(String username) throws SQLException {
        PreparedStatement p = conn.prepareStatement("DELETE FROM Users WHERE username = ?");
        p.setString(1, username);
        p.executeUpdate();
    }
    
    /** 
     * Method returns user credential as a User object if the user is found in 
     * the database.
     * @param username username submitted via JournalTools
     * @return returns User object if the user is found in the database or null
     * if the user is not found
     * @throws SQLException 
     */
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
    
    /**
     * Method updates the maxHeartRate of the specified user in the database.
     * @param userId userId submitted via JournalTools.
     * @param maxHeartRate maxHeartRate submitted via JournalTools.
     * @throws SQLException 
     */
    @Override
    public void updateMaxHeartRate(int userId, int maxHeartRate) throws SQLException {
        PreparedStatement p = conn.prepareStatement("UPDATE users SET maxHeartRate = ? WHERE id = ?");
        p.setInt(1, maxHeartRate);
        p.setInt(2, userId);
        p.executeUpdate();
    }

    /**
     * Method updates the password of the specified user in the database.
     * @param userId userId submitted via JournalTools.
     * @param password password submitted via JournalTools.
     * @throws SQLException 
     */
    @Override
    public void updatePassword(int userId, String password) throws SQLException {
        PreparedStatement p = conn.prepareStatement("UPDATE users SET password = ? WHERE id = ?");
        p.setString(1, password);
        p.setInt(2, userId);
        p.executeUpdate();
    }
}
