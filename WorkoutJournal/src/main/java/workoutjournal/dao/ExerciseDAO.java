/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workoutjournal.dao;
import java.time.LocalDate;
import java.util.ArrayList;
import workoutjournal.domain.Exercise;

/**
 * Interface for reaching the ExerciseDAO.
 */
public interface ExerciseDAO {
    
    void addExercise(int userId, LocalDate date, int type, Integer duration, Integer distance, Integer avgHeartRate, String description) throws Exception;
    ArrayList<Exercise> getExercises(int userId, LocalDate fromDate, LocalDate toDate) throws Exception;
    void deleteExercise(int id) throws Exception;  
    
}
