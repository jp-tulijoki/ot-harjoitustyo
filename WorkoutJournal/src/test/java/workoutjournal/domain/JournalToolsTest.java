package workoutjournal.domain;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import workoutjournal.DAO.*;

/**
 *
 * @author tulijoki
 */
public class JournalToolsTest {
    
    UserDAO userDAO;
    JournalTools tools;
    Connection connTest;
    
    @Before
    public void setUp() throws SQLException {
        
        this.connTest = DriverManager.getConnection("jdbc:sqlite:testdatabase.db");
        Statement s = connTest.createStatement();
        try {
            s.execute("CREATE TABLE Users (id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR NOT NULL, name VARCHAR NOT NULL, age INTEGER, sex INTEGER, maxHeartRate INTEGER)");
        } catch (SQLException ex) {
        }
        this.userDAO = new DBUserDAO(connTest);
        this.tools = new JournalTools(userDAO);
        tools.createUser("mikko95", "Mikko", 30, Sex.male);
        }

    
    @Test
    public void countMaxHeartRateWorksProperly() {
        assertEquals(190, tools.countMaxHeartRate(30, Sex.male));
        assertEquals(174, tools.countMaxHeartRate(40, Sex.female));
    }
    
    @Test
    public void createUserWorksProperly() throws SQLException {
        assertEquals(true, tools.createUser("maija90", "Maija", 30, Sex.female));
        assertEquals(false, tools.createUser("mikko95", "Mikko", 25, Sex.male));
        tools.deleteUser("maija90");
        connTest.close();
    }
    
    @Test
    public void deleteUserWorksProperly() throws SQLException {
        assertEquals(true, tools.deleteUser("mikko95"));
        assertEquals(false, tools.deleteUser("maija90"));
        connTest.close();
    }
    
    @Test
    public void loginWorksProperly() throws SQLException {
        assertEquals(true, tools.login("mikko95"));
        assertEquals(false, tools.login("maija90"));
        connTest.close();
    }
    
}
