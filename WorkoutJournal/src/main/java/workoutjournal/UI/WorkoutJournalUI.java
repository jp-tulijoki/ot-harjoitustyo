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
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import workoutjournal.DAO.*;
import workoutjournal.domain.*;

/**
 *
 * @author tulijoki
 */
public class WorkoutJournalUI extends Application {
    
    public void start(Stage stage) {
        
        Label instructionText = new Label("Login with your username and password");
        Label username = new Label("Username");
        TextField usernameInput = new TextField();
        Label password = new Label("Password");
        PasswordField passwordInput = new PasswordField();
        Button loginButton = new Button("Log in");
        Button newUserButton = new Button("Create new user");
        
        GridPane loginPane = new GridPane();
        loginPane.add(instructionText, 0, 0);
        loginPane.add(username, 0, 1);
        loginPane.add(usernameInput, 1, 1);
        loginPane.add(password, 0, 2);
        loginPane.add(passwordInput, 1, 2);
        loginPane.add(loginButton, 0, 3);
        loginPane.add(newUserButton, 1, 3);
        
        loginPane.setPrefSize(600, 300);
        loginPane.setAlignment(Pos.CENTER);
        loginPane.setHgap(10);
        loginPane.setVgap(10);
        loginPane.setPadding(new Insets(10,10,10,10));
        
        Scene loginScene = new Scene(loginPane);
        
        stage.setScene(loginScene);
        stage.show();
    }
    
    public static void main(String[] args) throws SQLException {
        
        Connection conn = DriverManager.getConnection("jdbc:sqlite:workoutjournal.db");
        Statement s = conn.createStatement();
        try {
            s.execute("CREATE TABLE Users (id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR NOT NULL, name VARCHAR NOT NULL, age INTEGER, sex INTEGER, maxHeartRate INTEGER)");
        } catch (SQLException ex) {
        }
        UserDAO userDao = new DBUserDAO(conn);
        JournalTools tools = new JournalTools(userDao);
        
        launch(WorkoutJournalUI.class);
        
        conn.close();

        
    
    

    
    }   
}
