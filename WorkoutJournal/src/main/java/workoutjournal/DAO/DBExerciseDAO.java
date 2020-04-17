/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workoutjournal.DAO;
import java.sql.*;
import java.time.LocalDate;

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
    public void addExercise(int user_id, LocalDate date, int type, Integer duration, Integer length, Integer avgHeartRate, String description) throws SQLException {
        Date sqlDate = Date.valueOf(date);
        PreparedStatement p = conn.prepareStatement("INSERT INTO Exercises(date, user_id, type, duration, length, avgHeartRate, description) VALUES(?, ?, ?, ?, ?, ?, ?)");
        p.setInt(1, user_id);
        p.setObject(2, date);
        p.setInt(3, type);
        p.setObject(4, duration);
        p.setObject(5, length);
        p.setObject(6, avgHeartRate);
        p.setString(7, description);
        p.executeUpdate();
    }
    
    
    
}
