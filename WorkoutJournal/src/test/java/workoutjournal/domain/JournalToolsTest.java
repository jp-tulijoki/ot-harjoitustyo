package workoutjournal.domain;

import java.security.NoSuchAlgorithmException;
import workoutjournal.dao.DBExerciseDAO;
import workoutjournal.dao.UserDAO;
import workoutjournal.dao.DBUserDAO;
import workoutjournal.dao.ExerciseDAO;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
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
        tools.createUser("mikko95", "password", 195);
        assertEquals(true, tools.createUser("maija90", "password", 182));
        assertEquals(false, tools.createUser("mikko95", "password", 195));
        tools.deleteUser("maija90");
        tools.deleteUser("mikko95");
        connTest.close();
    }
    
    @Test
    public void deleteUserWorksProperly() throws SQLException, Exception {
        tools.createUser("mikko95", "password", 195);
        assertEquals(true, tools.deleteUser("mikko95"));
        assertEquals(false, tools.deleteUser("maija90"));
        connTest.close();
    }
    
    @Test
    public void loginWorksProperly() throws SQLException, Exception {
        tools.createUser("mikko95", "password", 195);
        assertEquals(true, tools.login("mikko95", "password"));
        assertEquals(false, tools.login("maija90", "password"));
        assertEquals(false, tools.login("mikko95", "incorrectpassword"));
        tools.deleteUser("mikko95");
        connTest.close();
    }
    
    @Test
    public void addExerciseWorksProperly() throws SQLException, Exception {
        assertEquals(true, tools.addExercise(1, LocalDate.now(), 1, 60, 10, 150, "relaxed jogging in good weather"));
        connTest.close();
    }
    
    @Test
    public void exerciseListContainsTheExercisesOfTheSelectedPeriod() throws Exception {
        tools.createUser("mikko95", "password", 195);
        tools.login("mikko95", "password");
        tools.addExercise(tools.getLoggedUser().getId(), LocalDate.now(), 1, 60, 10, 150, "relaxed jogging in good weather");
        ArrayList<Exercise> exercises = tools.getExerciseList(LocalDate.now(), LocalDate.now());
        for (Exercise exercise : exercises) {
            assertEquals(true, exercise.getDate().isEqual(LocalDate.now()));
        }
        tools.logout();
        tools.deleteUser("mikko95");
        connTest.close();
    }
    
    @Test
    public void exerciseListDoesNotContainExercisesOutsideTheSelectedPeriod() throws Exception {
        tools.createUser("mikko95", "password", 195);
        tools.login("mikko95", "password");
        tools.addExercise(tools.getLoggedUser().getId(), LocalDate.now(), 1, 60, 10, 150, "relaxed jogging in good weather");
        ArrayList<Exercise> exercises = tools.getExerciseList(LocalDate.now(), LocalDate.now());
        for (Exercise exercise : exercises) {
            assertEquals(false, exercise.getDate().isBefore(LocalDate.now()));
            assertEquals(false, exercise.getDate().isAfter(LocalDate.now()));
        }
        tools.logout();
        tools.deleteUser("mikko95");
        connTest.close();
    }
    
    @Test
    public void countIntensityLevelWorksProperly() throws Exception {
        tools.createUser("mikko95", "password", 195);
        tools.login("mikko95", "password");
        Exercise strength = new Exercise(tools.getLoggedUser().getId(), LocalDate.now(), 2, 60, 0, 0, "strength");
        assertEquals(IntensityLevel.STRENGTH, tools.countIntensityLevel(strength));
        Exercise light = new Exercise(tools.getLoggedUser().getId(), LocalDate.now(), 1, 60, 10, 140, "light");
        assertEquals(IntensityLevel.LIGHT, tools.countIntensityLevel(light));
        Exercise moderate = new Exercise(tools.getLoggedUser().getId(), LocalDate.now(), 1, 60, 10, 160, "moderate");
        assertEquals(IntensityLevel.MODERATE, tools.countIntensityLevel(moderate));
        Exercise hard = new Exercise(tools.getLoggedUser().getId(), LocalDate.now(), 1, 60, 10, 180, "hard");
        assertEquals(IntensityLevel.HARD, tools.countIntensityLevel(hard));
        Exercise maximum = new Exercise(tools.getLoggedUser().getId(), LocalDate.now(), 1, 60, 10, 195, "maximum");
        assertEquals(IntensityLevel.MAXIMUM, tools.countIntensityLevel(maximum));
        tools.logout();
        tools.deleteUser("mikko95");
        connTest.close();
    }
    
    @Test
    public void countMonthlyDistanceWorksProperly() throws Exception {
        tools.createUser("mikko95", "password", 195);
        tools.login("mikko95", "password");
        tools.addExercise(tools.getLoggedUser().getId(), LocalDate.now(), 1, 60, 12, 145, "jogging that should not be included in this count");
        tools.addExercise(tools.getLoggedUser().getId(), LocalDate.now().minusMonths(1), 1, 60, 12, 150, "relaxed jogging in good weather");
        tools.addExercise(tools.getLoggedUser().getId(), LocalDate.now().minusMonths(1), 1, 40, 10, 180, "hard running");
        tools.addExercise(tools.getLoggedUser().getId(), LocalDate.now().minusMonths(1), 2, 60, 0, 0, "strength");
        assertEquals(22, tools.countMonthlyDistance(LocalDate.now()));
        tools.logout();
        tools.deleteUser("mikko95");
        connTest.close();
    }
    
    @Test
    public void monthlyDistanceDevelopmentIsCountedProperly() {
        assertEquals(0.5, tools.countMonthlyDistanceDevelopment(300, 200), 0.01);
        assertEquals(-0.25, tools.countMonthlyDistanceDevelopment(150, 200), 0.01);
        assertEquals(Double.NaN, tools.countMonthlyDistanceDevelopment(300, 0), 0.01);
    }
}
