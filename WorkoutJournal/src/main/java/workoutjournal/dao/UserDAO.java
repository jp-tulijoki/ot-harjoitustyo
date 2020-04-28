package workoutjournal.dao;

import workoutjournal.domain.User;

/**
 * Interface for reaching the UserDAO.
 */
public interface UserDAO {
    
    void createUser(String username, String password, int maxHeartRate) throws Exception;
    void deleteUser(String username) throws Exception;
    User getUserCredentials(String username) throws Exception; 
    
}
