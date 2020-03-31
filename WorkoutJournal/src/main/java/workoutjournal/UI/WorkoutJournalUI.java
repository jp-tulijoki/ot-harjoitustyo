/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workoutjournal.UI;
import workoutjournal.DAO.*;
import workoutjournal.domain.*;

/**
 *
 * @author tulijoki
 */
public class WorkoutJournalUI {
    
    public static void main(String[] args) {
        UserDAO userDao = new UserDAO();
        JournalTools tools = new JournalTools(userDao);
        
        System.out.println(tools.createUser("mikko", "Mikko", 20, Sex.male));
        System.out.println(tools.countMaxHeartRate(30, Sex.female));
        System.out.println(tools.countMaxHeartRate(50, Sex.male));
        System.out.println(tools.deleteUser("maija90"));
        
    }
}
