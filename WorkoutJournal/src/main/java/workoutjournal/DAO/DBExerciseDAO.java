/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workoutjournal.DAO;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import workoutjournal.domain.Exercise;

/**
 *
 * @author tulijoki
 */

public class DBExerciseDAO implements ExerciseDAO {
    
    Connection conn;

    public DBExerciseDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void addExercise(int user_id, LocalDate date, int type, Integer duration, Integer distance, Integer avgHeartRate, String description) throws SQLException {
        Date sqlDate = Date.valueOf(date);
        PreparedStatement p = conn.prepareStatement("INSERT INTO Exercises(date, user_id, type, distance, distance, avgHeartRate, description) VALUES(?, ?, ?, ?, ?, ?, ?)");
        p.setInt(1, user_id);
        p.setObject(2, date);
        p.setInt(3, type);
        p.setObject(4, duration);
        p.setObject(5, distance);
        p.setObject(6, avgHeartRate);
        p.setString(7, description);
        p.executeUpdate();
    }

    @Override
    public ArrayList<Exercise> getExercises(int user_id, LocalDate fromDate, LocalDate toDate) throws SQLException {
        ArrayList<Exercise> exercises = new ArrayList();
        Date sqlFromDate = Date.valueOf(fromDate);
        Date sqlToDate = Date.valueOf(toDate);
        PreparedStatement p = conn.prepareStatement("SELECT * FROM Exercises WHERE user_id = ? AND date BETWEEN ? AND ?");
        p.setInt(1, user_id);
        p.setObject(2, sqlFromDate);
        p.setObject(3, sqlToDate);
        ResultSet r = p.executeQuery();
        while (r.next()) {
            exercises.add(new Exercise(user_id, r.getDate("date").toLocalDate(), r.getInt("type"), r.getInt("duration"), r.getInt("distance"), r.getInt("avgHeartRate"), r.getString("description")));
        }
        return exercises;
    }
        
}
