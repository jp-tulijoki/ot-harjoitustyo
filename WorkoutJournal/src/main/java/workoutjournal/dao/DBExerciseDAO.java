package workoutjournal.dao;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import workoutjournal.domain.Exercise;
import workoutjournal.domain.Type;

/**
 * Database implementation of the ExerciseDAO.
 */
public class DBExerciseDAO implements ExerciseDAO {
    
    Connection conn;
    
    /**
     * The constructor defines the connection used.
     * @param conn the connection is defined when the program is started.
     */
    public DBExerciseDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Method stores an exercise in the database.
     * 
     * @param userId userId submitted via JournalTools
     * @param date exercise date submitted via JournalTools
     * @param type exercise type submitted via JournalTools
     * @param duration exercise duration submitted via JournalTools
     * @param distance distance covered in endurance training submitted via 
     * JournalTools
     * @param avgHeartRate avgHeartRate of endurance training submitted via 
     * JournalTools
     * @param description exercise description submitted via JournalTools
     * @throws SQLException 
     */
    @Override
    public void addExercise(int userId, LocalDate date, int type, Integer duration, Integer distance, Integer avgHeartRate, String description) throws SQLException {
        Date sqlDate = Date.valueOf(date);
        PreparedStatement p = conn.prepareStatement("INSERT INTO Exercises(user_id, date, type, duration, distance, avgHeartRate, description) VALUES(?, ?, ?, ?, ?, ?, ?)");
        p.setInt(1, userId);
        p.setObject(2, date);
        p.setInt(3, type);
        p.setObject(4, duration);
        p.setObject(5, distance);
        p.setObject(6, avgHeartRate);
        p.setString(7, description);
        p.executeUpdate();
    }

    /**
     * Method returns the exercises of a certain user of a certain time period
     * as a list.
     * 
     * @param userId userId submitted via JournalTools
     * @param fromDate the first date of the time period
     * @param toDate the last date of the time period
     * @return returns an ArrayList containing Exercise objects.
     * @throws SQLException 
     */
    @Override
    public ArrayList<Exercise> getExercises(int userId, LocalDate fromDate, LocalDate toDate) throws SQLException {
        ArrayList<Exercise> exercises = new ArrayList();
        PreparedStatement p = conn.prepareStatement("SELECT * FROM Exercises WHERE user_id = ? AND date BETWEEN ? AND ?");
        p.setInt(1, userId);
        p.setString(2, fromDate + "");
        p.setString(3, toDate + "");
        ResultSet r = p.executeQuery();
        while (r.next()) {
            exercises.add(new Exercise(r.getInt("id"), userId, LocalDate.parse(r.getString("date")), Type.values()[r.getInt("type")], r.getInt("duration"), r.getInt("distance"), r.getInt("avgHeartRate"), r.getString("description")));
        }
        return exercises;
    }
    
    /**
     * Method deletes the exercise with the specified id from the database.
     * @param id the id number submitted via JournalTools.
     * @throws SQLException 
     */
    @Override
    public void deleteExercise(int id) throws SQLException {
        PreparedStatement p = conn.prepareStatement("DELETE FROM Exercises WHERE id = ?");
        p.setInt(1, id);
        p.executeUpdate();
    }
    
    
        
}
