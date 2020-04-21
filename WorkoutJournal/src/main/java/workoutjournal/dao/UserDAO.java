/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workoutjournal.dao;

import workoutjournal.domain.User;

/**
 *
 * @author tulijoki
 */
public interface UserDAO {
    
    void createUser(String username, String password, int maxHeartRate) throws Exception;
    void deleteUser(String username) throws Exception;
    User getUserCredentials(String username) throws Exception; 
    
}
