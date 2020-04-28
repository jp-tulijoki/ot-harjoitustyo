package workoutjournal.domain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    
    public String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] bytes = md.digest();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
    
    public boolean createUser(String username, String password, int maxHeartRate) throws Exception {
        if (!(userDAO.getUserCredentials(username) == null)) {
            return false;
        } else {
            userDAO.createUser(username, password, maxHeartRate);
            return true;
        }
    }
    
    public boolean deleteUser(String username) throws Exception {
        if (userDAO.getUserCredentials(username) == null) {
            return false;
        } else {
            userDAO.deleteUser(username);
            return true;
        }
    }
    
    public boolean login(String username) throws Exception {
        if (userDAO.getUserCredentials(username) == null) {
            return false;
        }
        loggedIn = userDAO.getUserCredentials(username);
        return true;
    }
    
    public void logout() {
        loggedIn = null;
    }
    
    public User getLoggedUser() {
        return loggedIn;
    }

    public boolean addExercise(int userId, LocalDate date, int type, Integer duration, Integer distance, Integer avgHeartRate, String description) throws Exception {
        exerciseDAO.addExercise(userId, date, type, duration, distance, avgHeartRate, description);
        return true;
    }
    
    public ArrayList<Exercise> getExerciseList(LocalDate dateFrom, LocalDate dateTo) throws Exception {
        return exerciseDAO.getExercises(loggedIn.getId(), dateFrom, dateTo);
    }
    
    public IntensityLevel countIntensityLevel(Exercise exercise) {
        if (exercise.getType() == 2) {
            return IntensityLevel.STRENGTH;
        }
        int intensity = 100 * exercise.getAvgHeartRate() / loggedIn.getMaxHeartRate();
        if (intensity < 75) {
            return IntensityLevel.LIGHT;
        } else if (intensity < 85) {
            return IntensityLevel.MODERATE;
        } else if (intensity < 95) {
            return IntensityLevel.HARD;
        } else {
            return IntensityLevel.MAXIMUM;
        }
    }
}
