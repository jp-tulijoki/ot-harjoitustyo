package workoutjournal.domain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import workoutjournal.dao.UserDAO;
import workoutjournal.dao.ExerciseDAO;

/**
 * This class contains the tools for processing information used by the user
 * interface and acts as a mediator between dao and user interface.
 * 
 */
public class JournalTools {
    private UserDAO userDAO;
    private ExerciseDAO exerciseDAO;
    private User loggedIn;

    /**
     * Constructor for JournalTools class.
     * 
     * @param userDAO defines the UserDAO object connected to the JournalTools
     * object.
     * 
     * @param exerciseDAO defines the ExerciseDAO object connected to the
     * JournalTools object.
     */
    public JournalTools(UserDAO userDAO, ExerciseDAO exerciseDAO) {
        this.userDAO = userDAO;
        this.exerciseDAO = exerciseDAO;
    }
    
    /**
     * Method for counting default max heart rate.
     * 
     * @param age age given by the user
     * @param sex sex given by the user
     * @return the counted default max heart rate 
     */
    public int countMaxHeartRate(int age, String sex) {
        if (sex.equals("female")) {
            return 206 - (int) (0.8 * age);
        } else {
            return 220 - age;
        }
    }
    
    /**
     * Method for generating simple password hash.
     * 
     * @param password password given by the user in plain language
     * @return hashed password
     * @throws NoSuchAlgorithmException 
     */
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
    
    /** Method checks if there already is a user with the given username and 
     * if there is not, creates a user in the UserDAO. 
     * 
     * @param username username given by the user
     * @param password password hashed from the username given by the user
     * @param maxHeartRate max heart rate given by the user or counted by
     * the countMaxHeartRate method
     * @return returns false if the username already exists and true if user was
     * successfully created
     * @throws Exception 
     */
    public boolean createUser(String username, String password, int maxHeartRate) throws Exception {
        if (!(userDAO.getUserCredentials(username) == null)) {
            return false;
        } else {
            password = hashPassword(password);
            userDAO.createUser(username, password, maxHeartRate);
            return true;
        }
    }
    
    /**
     * Method checks if there is a user with the given username and if there is,
     * deletes that user's details in the UserDAO.
     * 
     * @param username username given by the user
     * @return returns false if the user can not be found in the UserDAO and
     * true if the user was successfully deleted.
     * @throws Exception 
     */
    public boolean deleteUser(String username) throws Exception {
        if (userDAO.getUserCredentials(username) == null) {
            return false;
        } else {
            userDAO.deleteUser(username);
            return true;
        }
    }
    
    /**
     * Method checks that the user exists and the password is correct. If these
     * requirements are met, the method logs in the user.
     * 
     * @param username username given by the user
     * @return Returns false if the user can not be found in the UserDAO or
     * given password does not match the password in the UserDAO. Returns 
     * true if the login was successful.
     * @throws Exception 
     */
    public boolean login(String username, String password) throws Exception {
        User user = userDAO.getUserCredentials(username);
        if (user == null) {
            return false;
        }
        String hashedPassword = hashPassword(password);
        if (!(user.getPassword().equals(hashedPassword))) {
            return false;
        }
        loggedIn = userDAO.getUserCredentials(username);
        return true;
    }
    
    /**  
     * Logs out the user currently logged in.
     */
    public void logout() {
        loggedIn = null;
    }
    
    /**
     * 
     * @return returns the currently logged in user. 
     */
    public User getLoggedUser() {
        return loggedIn;
    }
    
    /**
     * Method adds the exercise added by the user in the ExerciseDAO.
     * 
     * @param userId userId of the currently logged in user
     * @param date exercise date given by the user
     * @param type exercise type (endurance or strength) given by the user
     * @param duration exercise duration given by the user
     * @param distance amount of distance covered in endurance training given
     * by the user
     * @param avgHeartRate average heart rate in endurance training given by
     * the user
     * @param description exercise description given by the user
     * @return returns true if the exercise is successfully saved in the
     * ExerciseDAO.
     * @throws Exception 
     */
    public boolean addExercise(int userId, LocalDate date, int type, Integer duration, Integer distance, Integer avgHeartRate, String description) throws Exception {
        exerciseDAO.addExercise(userId, date, type, duration, distance, avgHeartRate, description);
        return true;
    }
    /**
     * Method returns a list of exercises of the currently logged in user of the 
     * given time period.
     * @param dateFrom the first day of the wanted time period
     * @param dateTo the last day of the wanted time period
     * @return returns an ArrayList with Exercise objects.
     * @throws Exception 
     */
    public ArrayList<Exercise> getExerciseList(LocalDate dateFrom, LocalDate dateTo) throws Exception {
        return exerciseDAO.getExercises(loggedIn.getId(), dateFrom, dateTo);
    }
    
    /**
     * Method counts intensity level of endurance training.
     * @param exercise the exercise the intensity of which is counted
     * @return returns enum value of intensity level for endurance training and
     * strength for strength training
     */
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
    
    /**
     * Method counts total distance covered in a certain month.
     * @param date the date value currently defined in the user inteface. (As 
     * total distance can not be counted for the current month, the method will
     * go one month backwards.)
     * @return returns total distance covered
     * @throws Exception 
     */
    public int countMonthlyDistance(LocalDate date) throws Exception {
        LocalDate firstDayOfMonth = date.minusMonths(1).withDayOfMonth(1);
        LocalDate lastDayOfMonth = firstDayOfMonth.withDayOfMonth(firstDayOfMonth.lengthOfMonth());
        ArrayList<Exercise> exercises = getExerciseList(firstDayOfMonth, lastDayOfMonth);
        int totalDistance = 0;
        for (Exercise exercise : exercises) {
            if (exercise.getType() == 2) {
                continue;
            }
            totalDistance += exercise.getDistance();
        }
        return totalDistance;
    }
    
    /**
     * Method counts the development rate in total distance covered compared to
     * the previous month.
     * @param currentMonthDistance total distance of the currently processed
     * month
     * @param previousMonthDistance total distance month before
     * @return return double value between -1.0 and positive infinity or NaN if
     * there would be division by zero due to no endurance training during 
     * previous month.
     */
    public double countMonthlyDistanceDevelopment(int currentMonthDistance, int previousMonthDistance) {
        if (previousMonthDistance == 0) {
            return Double.NaN;
        }
        return (double) currentMonthDistance / previousMonthDistance - 1; 
    }
}
