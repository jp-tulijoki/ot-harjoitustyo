/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workoutjournal.DAO;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import workoutjournal.domain.Exercise;

/**
 *
 * @author tulijoki
 */
public interface ExerciseDAO {
    
    void addExercise(int user_id, LocalDate date, int type, Integer duration, Integer distance, Integer avgHeartRate, String description) throws Exception;
    ArrayList<Exercise> getExercises(int user_id, LocalDate fromDate, LocalDate toDate) throws Exception;    
}
