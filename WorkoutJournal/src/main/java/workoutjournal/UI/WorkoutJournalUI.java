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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
    private ExerciseDAO exerciseDAO; 
    private JournalTools tools;
    
    public void init() throws SQLException, Exception {
        
        this.conn = DriverManager.getConnection("jdbc:sqlite:workoutjournal.db");
        Statement s = conn.createStatement();
        try {
            s.execute("CREATE TABLE Users (id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR NOT NULL, password TEXT, maxHeartRate INTEGER)");
            s.execute("CREATE TABLE Exercises (id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER, date DATE, type INTEGER, duration INTEGER, distance INTEGER, avgHeartRate INTEGER, description TEXT)");
        } catch (SQLException ex) {
        }
        this.userDAO = new DBUserDAO(conn);
        this.exerciseDAO = new DBExerciseDAO(conn);
        
        this.tools = new JournalTools(userDAO, exerciseDAO);
        tools.addExercise(1, LocalDate.now(), 1, 60, 10, 150, "relaxed jogging in good weather");
    }
    
    public void start(Stage stage) throws Exception {
        
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
        
        BorderPane primaryPane = new BorderPane();
        primaryPane.setPrefSize(720, 360);
        
        MenuBar actionsMenu = new MenuBar();
        
        Menu settings = new Menu("Settings");
        MenuItem profile = new MenuItem("Profile");
        MenuItem logout = new MenuItem("Log out");
        settings.getItems().addAll(profile, logout);
        
        Menu exercises = new Menu("Exercises");
        MenuItem addExercise = new MenuItem("Add exercise");
        MenuItem previousExercises = new MenuItem("Previous exercises");
        MenuItem summary = new MenuItem("Summary");
        exercises.getItems().addAll(addExercise, previousExercises, summary);
        
        GridPane addExercisePane = new GridPane();
        
        Label addExerciseLabel = new Label("Add new exercise");
        Label dateLabel = new Label("Date:");
        DatePicker datePicker = new DatePicker();
        Label typeLabel = new Label("Type:");
        ChoiceBox<String> types = new ChoiceBox();
        types.getItems().addAll("endurance", "strength");
        Label durationLabel = new Label("Duration (minutes):");
        IntegerField durationInput = new IntegerField();
        Label distanceLabel = new Label("Distance (kilometers):");
        IntegerField distanceInput = new IntegerField();
        Label avgHeartRateLabel = new Label("Average heart rate:");
        IntegerField avgHeartRateInput = new IntegerField();
        Label descriptionLabel = new Label("Description:");
        TextField descriptionInput = new TextField();
        Button addExerciseButton = new Button("Add exercise");
        Label addExerciseConfirmation = new Label("");
        
        addExercisePane.add(addExerciseLabel, 0, 0);
        addExercisePane.add(dateLabel, 0, 1);
        addExercisePane.add(datePicker, 1, 1);
        addExercisePane.add(typeLabel, 0, 2);
        addExercisePane.add(types, 1, 2);
        addExercisePane.add(durationLabel, 0, 3);
        addExercisePane.add(durationInput, 1, 3);
        addExercisePane.add(distanceLabel, 0, 4);
        addExercisePane.add(distanceInput, 1, 4);
        addExercisePane.add(avgHeartRateLabel, 0, 5);
        addExercisePane.add(avgHeartRateInput, 1, 5);
        addExercisePane.add(descriptionLabel, 0, 6);
        addExercisePane.add(descriptionInput, 1, 6);
        addExercisePane.add(addExerciseButton, 1, 7);
        addExercisePane.add(addExerciseConfirmation, 0, 8);
        
        // Menu actions
        
        logout.setOnAction((event) -> {
            tools.logout();
            stage.setScene(loginScene);
        });
        
        addExercise.setOnAction((event) -> {
            primaryPane.setCenter(addExercisePane);
        });
        
        actionsMenu.getMenus().addAll(settings, exercises);
        
        primaryPane.setTop(actionsMenu);
        
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
        
        addExerciseButton.setOnAction((event) -> {
            int type = 1;
            if (types.getValue().equals("strength")) {
                type = 2;
            }
            try {
                tools.addExercise(tools.getLoggedUser().getId(), datePicker.getValue(), type, durationInput.getValue(), distanceInput.getValue(), avgHeartRateInput.getValue(), descriptionInput.getText());
            } catch (Exception ex) {
                Logger.getLogger(WorkoutJournalUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            addExerciseConfirmation.setText("Exercise added succesfully");
            durationInput.setValue(0);
            distanceInput.setValue(0);
            avgHeartRateInput.setValue(0);
            descriptionInput.clear();
        });
        
        stage.setScene(loginScene);
        stage.show();
    }
    
    public void quit() throws SQLException {
        conn.close();
    }
    
    public BarChart<String, Number> drawOneWeek(ArrayList<Exercise> exerciseList, LocalDate dateFrom, LocalDate dateTo) {
        CategoryAxis dates = new CategoryAxis();
        NumberAxis duration = new NumberAxis();
        BarChart<String, Number> oneWeek = new BarChart<>(dates, duration);
        
        oneWeek.setTitle("Exercises " + dateFrom.toString() + " - " + dateTo.toString());
        oneWeek.setLegendVisible(false);
        XYChart.Series exercises = new XYChart.Series();
        
        try {
            for (Exercise exercise : exerciseList) {
                exercises.getData().add(new XYChart.Data(exercise.getDate().toString(), exercise.getDuration()));
            }
        } catch (Exception ex) {
            Logger.getLogger(WorkoutJournalUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        oneWeek.getData().add(exercises);
        return oneWeek;
    }
    
    public static void main(String[] args) throws SQLException {
        
        launch(WorkoutJournalUI.class);
    }   
}
