/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workoutjournal.DAO;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author tulijoki
 */
public interface ExerciseDAO {
    
    void addExercise(int user_id, LocalDate date, int type, Integer duration, Integer length, Integer avgHeartRate, String description) throws SQLException;
    
}
