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
    public void updateMaxHeartRateWorksProperly() throws Exception {
        tools.createUser("mikko95", "password", 195);
        tools.login("mikko95", "password");
        tools.updateMaxHeartRate(190);  
        assertEquals(190, tools.getLoggedUser().getMaxHeartRate());
        tools.deleteUser("mikko95");
        connTest.close();
    }
    
    @Test
    public void changePasswordWorksProperly() throws Exception {
        tools.createUser("mikko95", "password", 195);
        tools.login("mikko95", "password");
        String newPassword = "newpassword";
        assertEquals(false, tools.changePassword("incorrectOldPassword", newPassword));
        assertEquals(true, tools.changePassword("password", newPassword));
        assertEquals(tools.hashPassword(newPassword), tools.getLoggedUser().getPassword());
        tools.deleteUser("mikko95");
        connTest.close();
    }
    
    @Test
    public void addExerciseWorksProperly() throws SQLException, Exception {
        assertEquals(true, tools.addExercise(1, LocalDate.now(), Type.endurance, 60, 10, 150, "relaxed jogging in good weather"));
        connTest.close();
    }
    
    @Test
    public void exerciseListContainsTheExercisesOfTheSelectedPeriod() throws Exception {
        tools.createUser("mikko95", "password", 195);
        tools.login("mikko95", "password");
        tools.addExercise(tools.getLoggedUser().getId(), LocalDate.now(), Type.endurance, 60, 10, 150, "relaxed jogging in good weather");
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
        tools.addExercise(tools.getLoggedUser().getId(), LocalDate.now(), Type.endurance, 60, 10, 150, "relaxed jogging in good weather");
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
    public void deleteExerciseWorksProperly() throws Exception {
        tools.createUser("mikko95", "password", 195);
        tools.login("mikko95", "password");
        tools.addExercise(tools.getLoggedUser().getId(), LocalDate.now(), Type.endurance, 60, 10, 150, "relaxed jogging in good weather");
        ArrayList<Exercise> exercises = tools.getExerciseList(LocalDate.now(), LocalDate.now());
        assertEquals(false, exercises.isEmpty());
        int exerciseId = 0;
        for (Exercise exercise : exercises) {
            exerciseId = exercise.getId();
        }
        tools.deleteExercise(exerciseId);
        exercises = tools.getExerciseList(LocalDate.now(), LocalDate.now());
        assertEquals(true, exercises.isEmpty());
        tools.logout();
        tools.deleteUser("mikko95");
        connTest.close();
    }
    
    @Test
    public void countIntensityLevelWorksProperly() throws Exception {
        tools.createUser("mikko95", "password", 195);
        tools.login("mikko95", "password");
        Exercise strength = new Exercise(1, tools.getLoggedUser().getId(), LocalDate.now(), Type.strength, 60, 0, 0, "strength");
        assertEquals(IntensityLevel.strength, tools.countIntensityLevel(strength));
        Exercise light = new Exercise(2, tools.getLoggedUser().getId(), LocalDate.now(), Type.endurance, 60, 10, 140, "light");
        assertEquals(IntensityLevel.light, tools.countIntensityLevel(light));
        Exercise moderate = new Exercise(3, tools.getLoggedUser().getId(), LocalDate.now(), Type.endurance, 60, 10, 160, "moderate");
        assertEquals(IntensityLevel.moderate, tools.countIntensityLevel(moderate));
        Exercise hard = new Exercise(4, tools.getLoggedUser().getId(), LocalDate.now(), Type.endurance, 60, 10, 180, "hard");
        assertEquals(IntensityLevel.hard, tools.countIntensityLevel(hard));
        Exercise maximum = new Exercise(5, tools.getLoggedUser().getId(), LocalDate.now(), Type.endurance, 60, 10, 195, "maximum");
        assertEquals(IntensityLevel.maximum, tools.countIntensityLevel(maximum));
        tools.logout();
        tools.deleteUser("mikko95");
        connTest.close();
    }
    
    
    // This test checks the average speed for each intensity level as well total duration for strenght training and 
    // total durations and distances for endurance training.
    @Test
    public void monthlyStatsWorksProperly() throws Exception {
        tools.createUser("mikko95", "password", 195);
        tools.login("mikko95", "password");
        tools.addExercise(tools.getLoggedUser().getId(), LocalDate.now().minusMonths(1), Type.endurance, 60, 12, 120, "easy jogging that should not be included in this count");
        tools.addExercise(tools.getLoggedUser().getId(), LocalDate.now(), Type.endurance, 60, 12, 150, "relaxed jogging in good weather");
        tools.addExercise(tools.getLoggedUser().getId(), LocalDate.now(), Type.endurance, 40, 10, 180, "hard running");
        tools.addExercise(tools.getLoggedUser().getId(), LocalDate.now(), Type.endurance, 3, 1, 192, "one kilometer at full speed");
        tools.addExercise(tools.getLoggedUser().getId(), LocalDate.now(), Type.strength, 60, 0, 0, "strength");
        double[][] stats = tools.countMonthlyStats(LocalDate.now());
        assertEquals(0, stats[1][2], 0.01);
        assertEquals(5, stats[2][2], 0.01);
        assertEquals(4, stats[3][2], 0.01);
        assertEquals(3, stats[4][2], 0.01);
        assertEquals(60, stats[0][0], 0.01);
        assertEquals(103, stats[5][0], 0.01);
        assertEquals(23, stats[5][1], 0.01);
        tools.logout();
        tools.deleteUser("mikko95");
        connTest.close();
    }
    
    @Test
    public void monthlyDistanceDevelopmentIsCountedProperly() {
        assertEquals(50, tools.countMonthlyDistanceDevelopment(300, 200), 0.01);
        assertEquals(-25, tools.countMonthlyDistanceDevelopment(150, 200), 0.01);
        assertEquals(Double.NaN, tools.countMonthlyDistanceDevelopment(300, 0), 0.01);
    }
}
