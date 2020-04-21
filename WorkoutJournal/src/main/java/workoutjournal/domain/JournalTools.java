package workoutjournal.domain;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.*;
import workoutjournal.dao.UserDAO;
import workoutjournal.dao.ExerciseDAO;
import workoutjournal.domain.User;

public class JournalTools {
    private UserDAO userDAO;
    private ExerciseDAO exerciseDAO;
    private User loggedIn;

    public JournalTools(UserDAO userDAO, ExerciseDAO exerciseDAO) {
        this.userDAO = userDAO;
        this.exerciseDAO = exerciseDAO;
    }
        
    public int countMaxHeartRate(int age, String sex) {
        if (sex.equals("female")) {
            return 206 - Integer.valueOf((int) (0.8 * age));
        } else {
            return 220 - age;
        }
    }
    
    public boolean createUser(String username, String password, int maxHeartRate) {
        try {
            if (!(userDAO.getUserCredentials(username) == null)) {
                return false;
            } else {
                userDAO.createUser(username, password, maxHeartRate);
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(JournalTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean deleteUser(String username) {
        try {
            if (userDAO.getUserCredentials(username) == null) {
                return false;
            } else {
                userDAO.deleteUser(username);
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(JournalTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean login(String username) {
        try {
            if (userDAO.getUserCredentials(username) == null) {
                return false;
            }
            loggedIn = userDAO.getUserCredentials(username);
        } catch (SQLException ex) {
            Logger.getLogger(JournalTools.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public void logout() {
        loggedIn = null;
    }
    
    public User getLoggedUser() {
        return loggedIn;
    }

    public boolean addExercise(int userId, LocalDate date, int type, Integer duration, Integer distance, Integer avgHeartRate, String description) throws Exception {
        try {
            exerciseDAO.addExercise(userId, date, type, duration, distance, avgHeartRate, description);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(JournalTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public ArrayList<Exercise> getOneWeeksExercises(LocalDate dateFrom, LocalDate dateTo) throws Exception {
        return exerciseDAO.getExercises(loggedIn.getId(), dateFrom, dateTo);
    }
    
    public String countIntensityLevel(Exercise exercise) {
        if (exercise.getType() == 2) {
            return "slategray";
        }
        int intensity = 100 * exercise.getAvgHeartRate() / loggedIn.getMaxHeartRate();
        if (intensity < 75) {
            return "lightgreen";
        } else if (intensity < 85) {
            return "yellow";
        } else if (intensity < 95) {
            return "orange";
        } else {
            return "red";
        }
    }
}
