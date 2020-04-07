/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workoutjournal.UI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import workoutjournal.DAO.*;
import workoutjournal.domain.*;

/**
 *
 * @author tulijoki
 */
public class WorkoutJournalUI {
    
    public static void main(String[] args) throws SQLException {
        
        Connection conn = DriverManager.getConnection("jdbc:sqlite:workoutjournal.db");
        Statement s = conn.createStatement();
        try {
            s.execute("CREATE TABLE Users (id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR NOT NULL, name VARCHAR NOT NULL, age INTEGER, sex INTEGER, maxHeartRate INTEGER)");
        } catch (SQLException ex) {
        

        UserDAO userDao = new DBUserDAO(conn);
        JournalTools tools = new JournalTools(userDao);
        
        System.out.println(tools.createUser("mikko999", "Mikko", 20, Sex.male));
        System.out.println(tools.countMaxHeartRate(30, Sex.female));
        System.out.println(tools.countMaxHeartRate(50, Sex.male));
        System.out.println(tools.deleteUser("maija90"));
        
        tools.login("mikko999");
        System.out.println("Kirjautunut käyttäjä: " + tools.getLoggedUser().getUsername());
        tools.logout();
        if (tools.getLoggedUser() == null) {
            System.out.println("Ei käyttäjää kirjautuneena.");
        }
        
        conn.close();

        }
    }
}
