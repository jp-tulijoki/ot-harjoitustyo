/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workoutjournal.UI;
import com.sun.javafx.scene.control.IntegerField;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
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
    
    private Connection conn;
    private UserDAO userDAO;
    private JournalTools tools;
    
    public void init() throws SQLException {
        
        conn = DriverManager.getConnection("jdbc:sqlite:workoutjournal.db");
        Statement s = conn.createStatement();
        try {
            s.execute("CREATE TABLE Users (id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR NOT NULL, password TEXT, maxHeartRate INTEGER)");
        } catch (SQLException ex) {
        }
        userDAO = new DBUserDAO(conn);
        tools = new JournalTools(userDAO);
    }
    
    public void start(Stage stage) {
        
        // Login scene
        
        GridPane loginPane = new GridPane();
        
        loginPane.setPrefSize(720, 360);
        loginPane.setAlignment(Pos.CENTER);
        loginPane.setHgap(10);
        loginPane.setVgap(10);
        loginPane.setPadding(new Insets(10,10,10,10));
        
        Label loginInstruction = new Label("Login with your username and password");
        Label usernameLabel = new Label("Username");
        TextField usernameInput = new TextField();
        Label passwordLabel = new Label("Password");
        PasswordField passwordInput = new PasswordField();
        Button loginButton = new Button("Log in");
        Button newUserButton = new Button("Create new user");
        Label loginError = new Label("");
        
        loginPane.add(loginInstruction, 0, 0);
        loginPane.add(usernameLabel, 0, 1);
        loginPane.add(usernameInput, 1, 1);
        loginPane.add(passwordLabel, 0, 2);
        loginPane.add(passwordInput, 1, 2);
        loginPane.add(newUserButton, 0, 3);
        loginPane.add(loginButton, 1, 3);
        loginPane.add(loginError, 0, 4);
        
        Scene loginScene = new Scene(loginPane);
        
        // New user scene
        
        GridPane newUserPane = new GridPane();
        
        newUserPane.setPrefSize(720, 360);
        newUserPane.setAlignment(Pos.CENTER);
        newUserPane.setHgap(10);
        newUserPane.setVgap(10);
        newUserPane.setPadding(new Insets(10,10,10,10));
        
        Label newUserInstruction = new Label("Create new user");
        Label setUsernameLabel = new Label("Username");
        TextField setUsernameInput = new TextField();
        Label setPasswordLabel = new Label("Password");
        PasswordField setPasswordInput = new PasswordField();
        Label maxHeartRateLabel = new Label("Max heart rate");
        IntegerField maxHeartRateInput = new IntegerField();
        Label countMaxHeartRate = new Label("Count default max heart rate");
        Label age = new Label("Age");
        IntegerField ageInput = new IntegerField();
        Label sex = new Label("Sex");
        ChoiceBox<String> sexes = new ChoiceBox();
        sexes.getItems().addAll("female", "male");
        sexes.getValue();
        Button countMaxHeartRateButton = new Button("Count max heart rate");
        Button createNewUserButton = new Button("Create new user");
        Label userCreationError = new Label("");
        
        newUserPane.add(newUserInstruction, 0, 0);
        newUserPane.add(setUsernameLabel, 0, 1);
        newUserPane.add(setUsernameInput, 1, 1);
        newUserPane.add(setPasswordLabel, 0, 2);
        newUserPane.add(setPasswordInput, 1, 2);
        newUserPane.add(maxHeartRateLabel, 0, 3);
        newUserPane.add(maxHeartRateInput, 1, 3);
        newUserPane.add(countMaxHeartRate, 0, 4);
        newUserPane.add(age, 0, 5);
        newUserPane.add(ageInput, 1, 5);
        newUserPane.add(sex, 0, 6);
        newUserPane.add(sexes, 1, 6);
        newUserPane.add(countMaxHeartRateButton, 1, 7);
        newUserPane.add(createNewUserButton, 1, 8);
        newUserPane.add(userCreationError, 0, 9);
        
        Scene newUserScene = new Scene(newUserPane);
        
        // Primary scene
        
        GridPane primaryPane = new GridPane();
        
        primaryPane.add(new Label("Welcome!"), 0, 0);
        
        Scene primaryScene = new Scene(primaryPane);
        
        // Actions for buttons
        
        loginButton.setOnAction((event) -> {
            String username = usernameInput.getText();
            if (tools.login(username)) {
                stage.setScene(primaryScene);
            } else {
                loginError.setText("Invalid credentials.");
            }
        });
        
        newUserButton.setOnAction((event) -> {
            stage.setScene(newUserScene);
        });
        
        countMaxHeartRateButton.setOnAction((event) -> {
            maxHeartRateInput.setValue(tools.countMaxHeartRate(ageInput.getValue(), sexes.getValue()));
        });
        
        
        
        createNewUserButton.setOnAction((event) -> {
            if (tools.createUser(setUsernameInput.getText(), setPasswordInput.getText(), maxHeartRateInput.getValue())) {
                loginInstruction.setText("New user created succesfully. You may now log in.");
                stage.setScene(loginScene);
            } else {
                userCreationError.setText("Username is already in use. Please choose another username.");
                setUsernameInput.clear();
                setPasswordInput.clear();
            }
        });
        
        stage.setScene(loginScene);
        stage.show();
    }
    
    public void quit() throws SQLException {
        conn.close();
    }
    
    public static void main(String[] args) throws SQLException {
        
        
        launch(WorkoutJournalUI.class);
        


        
    
    

    
    }   
}
