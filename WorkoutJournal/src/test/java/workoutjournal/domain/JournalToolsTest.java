package workoutjournal.domain;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import workoutjournal.DAO.DAO;
import workoutjournal.DAO.UserDAO;

/**
 *
 * @author tulijoki
 */
public class JournalToolsTest {
    
    JournalTools tools;
    
    @Before
    public void setUp() {
        this.tools = new JournalTools(new DAO(), new UserDAO());
        tools.createUser("mikko95", "Mikko", 195, Sex.male);
        tools.closeDatabaseConnection();
    }
    
    @Test
    public void countMaxHeartRateWorksProperly() {
        assertEquals(190, tools.countMaxHeartRate(30, Sex.male));
        assertEquals(174, tools.countMaxHeartRate(40, Sex.female));
    }
    
    @Test
    public void createUserWorksProperly() {
        tools.getDatabaseConnection();
        assertEquals(true, tools.createUser("maija90", "Maija", 182, Sex.female));
        assertEquals(false, tools.createUser("mikko95", "Mikko", 195, Sex.male));
        tools.deleteUser("maija90");
        tools.closeDatabaseConnection();
    }
    
    @Test
    public void deleteUserWorksProperly() {
        tools.getDatabaseConnection();
        assertEquals(true, tools.deleteUser("mikko95"));
        assertEquals(false, tools.deleteUser("maija90"));
        tools.closeDatabaseConnection();
    }
}
