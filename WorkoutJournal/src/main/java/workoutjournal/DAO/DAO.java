/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workoutjournal.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author tulijoki
 */
public class DAO {
    
    protected Connection conn;
    protected Statement s;
    protected PreparedStatement p;
    
    public DAO() {
        try {
            this.conn = DriverManager.getConnection("jdbc:sqlite:workoutjournal.db");
            this.s = conn.createStatement();
        } catch (SQLException ex) {
            System.out.println("Connection to database failed.");
        }
        try {
            s.execute("CREATE TABLE Users (id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR NOT NULL, name VARCHAR NOT NULL, age INTEGER, sex INTEGER, maxHeartRate INTEGER)");
        } catch (SQLException ex2) {  
        }
    }
    public void closeConnection() throws SQLException {
        conn.close();
    }
    public void getConnection() throws SQLException {
        conn = DriverManager.getConnection("jdbc:sqlite:workoutjournal.db");
    }
}
