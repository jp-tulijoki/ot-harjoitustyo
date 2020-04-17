package workoutjournal.domain;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.*;
import java.time.LocalDate;
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
    ExerciseDAO exerciseDAO;
    JournalTools tools;
    Connection connTest;
    
    @Before
    public void setUp() throws SQLException {
        
        this.connTest = DriverManager.getConnection("jdbc:sqlite:testdatabase.db");
        Statement s = connTest.createStatement();
        try {
            s.execute("CREATE TABLE Users (id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR NOT NULL, password TEXT, maxHeartRate INTEGER)");
            s.execute("CREATE TABLE Exercises (id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER, date DATE, type INTEGER, duration INTEGER, length INTEGER, avgHeartRate INTEGER, description TEXT)");
        } catch (SQLException ex) {
        }
        this.userDAO = new DBUserDAO(connTest);
        this.exerciseDAO = new DBExerciseDAO(connTest);
        this.tools = new JournalTools(userDAO, exerciseDAO);
        }

    
    @Test
    public void countMaxHeartRateWorksProperly() {
        assertEquals(190, tools.countMaxHeartRate(30, "male"));
        assertEquals(174, tools.countMaxHeartRate(40, "female"));
    }
    
    @Test
    public void createUserWorksProperly() throws SQLException {
        tools.createUser("mikko95", "mikonsalasana", 195);
        assertEquals(true, tools.createUser("maija90", "maijansalasana", 182));
        assertEquals(false, tools.createUser("mikko95", "mikonsalasana", 195));
        tools.deleteUser("maija90");
        connTest.close();
    }
    
    @Test
    public void deleteUserWorksProperly() throws SQLException {
        tools.createUser("mikko95", "mikonsalasana", 195);
        assertEquals(true, tools.deleteUser("mikko95"));
        assertEquals(false, tools.deleteUser("maija90"));
        connTest.close();
    }
    
    @Test
    public void loginWorksProperly() throws SQLException {
        tools.createUser("mikko95", "mikonsalasana", 195);
        assertEquals(true, tools.login("mikko95"));
        assertEquals(false, tools.login("maija90"));
        connTest.close();
    }
    
    @Test
    public void addExerciseWorksProperly() throws SQLException {
        assertEquals(true, tools.addExercise(1, LocalDate.now(), 1, 60, 10, 150, "relaxed jogging in good weather"));
        connTest.close();
    }
    
}
