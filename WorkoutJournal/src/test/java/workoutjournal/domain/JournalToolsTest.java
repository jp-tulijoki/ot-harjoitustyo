package workoutjournal.domain;

import java.security.NoSuchAlgorithmException;
import workoutjournal.dao.DBExerciseDAO;
import workoutjournal.dao.UserDAO;
import workoutjournal.dao.DBUserDAO;
import workoutjournal.dao.ExerciseDAO;
import java.sql.*;
import java.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

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
            s.execute("CREATE TABLE Exercises (id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER, date DATE, type INTEGER, duration INTEGER, distance INTEGER, avgHeartRate INTEGER, description TEXT)");
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
    public void samePasswordInputCreatesSamePasswordHash() throws NoSuchAlgorithmException {
        String password = "password";
        String password2 = "password";
        assertEquals(true, tools.hashPassword(password).equals(tools.hashPassword(password2)));
    }
    
    @Test
    public void differentPasswordInputCreatesDifferentPasswordHash() throws NoSuchAlgorithmException {
        String password = "password";
        String password2 = "PASSWORD";
        assertEquals(false, tools.hashPassword(password).equals(tools.hashPassword(password2)));
    }
    
    @Test
    public void createUserWorksProperly() throws SQLException, Exception {
        tools.createUser("mikko95", "mikonsalasana", 195);
        assertEquals(true, tools.createUser("maija90", "maijansalasana", 182));
        assertEquals(false, tools.createUser("mikko95", "mikonsalasana", 195));
        tools.deleteUser("maija90");
        connTest.close();
    }
    
    @Test
    public void deleteUserWorksProperly() throws SQLException, Exception {
        tools.createUser("mikko95", "mikonsalasana", 195);
        assertEquals(true, tools.deleteUser("mikko95"));
        assertEquals(false, tools.deleteUser("maija90"));
        connTest.close();
    }
    
    @Test
    public void loginWorksProperly() throws SQLException, Exception {
        tools.createUser("mikko95", "mikonsalasana", 195);
        assertEquals(true, tools.login("mikko95"));
        assertEquals(false, tools.login("maija90"));
        connTest.close();
    }
    
    @Test
    public void addExerciseWorksProperly() throws SQLException, Exception {
        assertEquals(true, tools.addExercise(1, LocalDate.now(), 1, 60, 10, 150, "relaxed jogging in good weather"));
        connTest.close();
    }
}
