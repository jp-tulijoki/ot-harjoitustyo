/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workoutjournal.DAO;

import java.sql.SQLException;
import workoutjournal.domain.User;

/**
 *
 * @author tulijoki
 */
public interface UserDAO {
    
    void createUser(String username, String password, int maxHeartRate) throws SQLException;
    void deleteUser(String username) throws SQLException;
    User getUserCredentials(String username) throws SQLException; 
    
}
