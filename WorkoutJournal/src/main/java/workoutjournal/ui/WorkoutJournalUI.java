package workoutjournal.ui;
import workoutjournal.dao.ExerciseDAO;
import workoutjournal.dao.DBUserDAO;
import workoutjournal.dao.UserDAO;
import workoutjournal.dao.DBExerciseDAO;
import com.sun.javafx.charts.Legend;
import com.sun.javafx.scene.control.IntegerField;
import static java.lang.Double.isNaN;
import java.sql.*;
import static java.time.DayOfWeek.*;
import java.time.LocalDate;
import static java.time.temporal.TemporalAdjusters.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import workoutjournal.domain.*;

public class WorkoutJournalUI extends Application {
    
    private Connection conn;
    private UserDAO userDAO;
    private ExerciseDAO exerciseDAO; 
    private JournalTools jTools;
    private UITools uiTools;
    private LocalDate today;
    private LocalDate date;
    
    public void init() throws SQLException, Exception {
        
        this.conn = DriverManager.getConnection("jdbc:sqlite:workoutjournal.db");
        Statement s = conn.createStatement();
        try {
            s.execute("CREATE TABLE Users (id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR NOT NULL, password VARCHAR NOT NULL, maxHeartRate INTEGER)");
            s.execute("CREATE TABLE Exercises (id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER, date DATE, type INTEGER, duration INTEGER, distance INTEGER, avgHeartRate INTEGER, description TEXT)");
        } catch (SQLException ex) {
        }
        this.userDAO = new DBUserDAO(conn);
        this.exerciseDAO = new DBExerciseDAO(conn);
        
        this.jTools = new JournalTools(userDAO, exerciseDAO);
        this.uiTools = new UITools(jTools);
        this.today = LocalDate.now();
        this.date = LocalDate.now();
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
        sexes.getSelectionModel().selectFirst();
        Button countMaxHeartRateButton = new Button("Count max heart rate");
        Button createNewUserButton = new Button("Create new user");
        Label userCreationError = new Label("");
        
        Scene newUserScene = new Scene(newUserPane);
        
        // Primary scene, actions menu on top and changing views depending of the action
        
        BorderPane primaryPane = new BorderPane();
        primaryPane.setPrefSize(720, 360);
        
        MenuBar actionsMenu = new MenuBar();
        
        Menu settings = new Menu("Settings");
        MenuItem updateMaxHeartRate = new MenuItem("Update max heart rate");
        MenuItem changePassword = new MenuItem("Change password");
        MenuItem logout = new MenuItem("Log out");
        settings.getItems().addAll(updateMaxHeartRate, changePassword, logout);
        
        Menu exercises = new Menu("Exercises");
        MenuItem addExercise = new MenuItem("Add exercise");
        MenuItem previousExercises = new MenuItem("Previous exercises");
        MenuItem weeklySummary = new MenuItem("Weekly summary");
        MenuItem monthlySummary = new MenuItem("Monthly summary");
        exercises.getItems().addAll(addExercise, previousExercises, weeklySummary, monthlySummary);
        
        actionsMenu.getMenus().addAll(settings, exercises);
        primaryPane.setTop(actionsMenu);
        Scene primaryScene = new Scene(primaryPane);
        
        // View for updating max heart rate
        
        GridPane updateMaxHeartRatePane = new GridPane();
        Label updateMaxHeartRateLabel = new Label("Update max heart rate");
        Button updateMaxHeartRateButton = new Button("Update");
        Label updateMaxHeartRateStatus = new Label("");
        
        // View for changing password
        
        GridPane changePasswordPane = new GridPane();
        Label changePasswordLabel = new Label("Change password");
        Label oldPasswordLabel = new Label("Old password");
        PasswordField oldPasswordInput = new PasswordField();
        Label newPasswordLabel = new Label("New password");
        PasswordField newPasswordInput = new PasswordField();
        Button changePasswordButton = new Button("Change");
        Label changePasswordStatus = new Label("");
        
        // View for adding an exercise
        
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
        
        // Toggle week buttons for weekly summary
        
        HBox toggleWeekBox = new HBox();
        Button previousWeekButton = new Button("Previous week");
        Button nextWeekButton = new Button("Next week");
        toggleWeekBox.getChildren().addAll(previousWeekButton, nextWeekButton);
        
        // Toggle month buttons for monthly summary
        
        HBox toggleMonthBox = new HBox();
        Button previousMonthButton = new Button("Previous month");
        Button nextMonthButton = new Button("Next month");
        toggleMonthBox.getChildren().addAll(previousMonthButton, nextMonthButton);
        
        // Menu actions
                
        updateMaxHeartRate.setOnAction((event) -> {
            updateMaxHeartRatePane.add(updateMaxHeartRateLabel, 0, 0);
            updateMaxHeartRatePane.add(maxHeartRateLabel, 0, 1);
            updateMaxHeartRatePane.add(maxHeartRateInput, 1, 1);
            updateMaxHeartRatePane.add(countMaxHeartRate, 0, 2);
            updateMaxHeartRatePane.add(age, 0, 3);
            updateMaxHeartRatePane.add(ageInput, 1, 3);
            updateMaxHeartRatePane.add(sex, 0, 4);
            updateMaxHeartRatePane.add(sexes, 1, 4);
            updateMaxHeartRatePane.add(countMaxHeartRateButton, 1, 5);
            updateMaxHeartRatePane.add(updateMaxHeartRateButton, 1, 6);
            updateMaxHeartRatePane.add(updateMaxHeartRateStatus, 0, 7);
            primaryPane.setCenter(updateMaxHeartRatePane);
        });
        
        changePassword.setOnAction((event) -> {
            changePasswordPane.add(changePasswordLabel, 0, 0);
            changePasswordPane.add(oldPasswordLabel, 0, 1);
            changePasswordPane.add(oldPasswordInput, 1, 1);
            changePasswordPane.add(newPasswordLabel, 0, 2);
            changePasswordPane.add(newPasswordInput, 1, 2);
            changePasswordPane.add(changePasswordButton, 1, 3);
            changePasswordPane.add(changePasswordStatus, 1, 4);
            primaryPane.setCenter(changePasswordPane);
        });
        
        logout.setOnAction((event) -> {
            jTools.logout();
            stage.setScene(loginScene);
        });
        
        addExercise.setOnAction((event) -> {
            primaryPane.setCenter(addExercisePane);
        });
        
        previousExercises.setOnAction((event) -> {
            try {
                TableView exerciseTable = uiTools.drawExerciseTable(date);
                primaryPane.setCenter(exerciseTable);
            } catch (Exception ex) {
                Logger.getLogger(WorkoutJournalUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });
        
        weeklySummary.setOnAction((event) -> {
            try {
                LocalDate monday = today.with(previousOrSame(MONDAY));
                LocalDate sunday = today.with(nextOrSame(SUNDAY));
                StackedBarChart <String, Number> oneWeek = uiTools.drawOneWeek(monday, sunday);
                primaryPane.setCenter(oneWeek);
                primaryPane.setBottom(toggleWeekBox);
                stage.setScene(primaryScene);
            } catch (Exception ex) {
                Logger.getLogger(WorkoutJournalUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        monthlySummary.setOnAction((event) -> {
            try {
                BorderPane monthlySummaryPane = uiTools.drawMonthlyStats(today);
                primaryPane.setCenter(monthlySummaryPane);
                primaryPane.setBottom(toggleMonthBox);
            } catch (Exception ex) {
                Logger.getLogger(WorkoutJournalUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });
        
        // Actions for buttons
          
        loginButton.setOnAction((event) -> {
            try {
                if (jTools.login(usernameInput.getText(), passwordInput.getText())) {
                    LocalDate monday = today.with(previousOrSame(MONDAY));
                    LocalDate sunday = today.with(nextOrSame(SUNDAY));
                    StackedBarChart <String, Number> oneWeek = uiTools.drawOneWeek(monday, sunday);
                    primaryPane.setCenter(oneWeek);
                    primaryPane.setBottom(toggleWeekBox);
                    usernameInput.clear();
                    passwordInput.clear();
                    stage.setScene(primaryScene);
                } else {
                    loginError.setText("User not found or invalid password.");
                }
            } catch (Exception ex) {
                loginError.setText("Database connection lost. Try again later.");
                Logger.getLogger(WorkoutJournalUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        newUserButton.setOnAction((event) -> {
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
            stage.setScene(newUserScene);
        });
        
        countMaxHeartRateButton.setOnAction((event) -> {
            maxHeartRateInput.setValue(jTools.countMaxHeartRate(ageInput.getValue(), sexes.getValue()));
        });
        
        createNewUserButton.setOnAction((event) -> {
            String username = setUsernameInput.getText();
            String password = setPasswordInput.getText();
            try {
                if (username.length() < 3 || password.length() < 3) {
                    userCreationError.setText("Username and password have to be at least 3 characters long.");
                } else if (jTools.createUser(setUsernameInput.getText(), password, maxHeartRateInput.getValue())) {
                    loginInstruction.setText("New user created succesfully. You may now log in.");
                    stage.setScene(loginScene);
                } else {
                    userCreationError.setText("Username is already in use. Please choose another username.");
                    setUsernameInput.clear();
                    setPasswordInput.clear();
                }
            } catch (Exception ex) {
                userCreationError.setText("Database connection is lost or something unexpected happened. Try again later");
            }
        });
        
        updateMaxHeartRateButton.setOnAction((event) -> {
            try {
                jTools.updateMaxHeartRate(maxHeartRateInput.getValue());
                updateMaxHeartRateStatus.setText("Max heart rate saved successfully");
            } catch (Exception ex) {
                Logger.getLogger(WorkoutJournalUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        changePasswordButton.setOnAction((event) -> {
            try {
                if (newPasswordInput.getText().length() < 3) {
                    changePasswordStatus.setText("New password has to be at least 3 characters long.");
                } else if (jTools.changePassword(oldPasswordInput.getText(), newPasswordInput.getText())) {
                    changePasswordStatus.setText("Password changed successfully.");
                } else {
                    changePasswordStatus.setText("Incorrect old password.");
                }
            } catch (Exception ex) {
                Logger.getLogger(WorkoutJournalUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        addExerciseButton.setOnAction((event) -> {
            int type = 1;
            if (types.getValue().equals("strength")) {
                type = 2;
            }
            try {
                jTools.addExercise(jTools.getLoggedUser().getId(), datePicker.getValue(), type, durationInput.getValue(), distanceInput.getValue(), avgHeartRateInput.getValue(), descriptionInput.getText());
            } catch (Exception ex) {
                addExerciseConfirmation.setText("Database connection lost. Try again later.");
            }
            addExerciseConfirmation.setText("Exercise added succesfully");
            durationInput.setValue(0);
            distanceInput.setValue(0);
            avgHeartRateInput.setValue(0);
            descriptionInput.clear();
        });
        
        previousWeekButton.setOnAction((event) -> {
            try {
                date = date.minusWeeks(1);
                LocalDate monday = date.with(previousOrSame(MONDAY));
                LocalDate sunday = date.with(nextOrSame(SUNDAY));
                StackedBarChart <String, Number> oneWeek = uiTools.drawOneWeek(monday, sunday);
                primaryPane.setCenter(oneWeek);
            } catch (Exception ex) {
                Logger.getLogger(WorkoutJournalUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        nextWeekButton.setOnAction((event) -> {
            try {
                date = date.plusWeeks(1);
                LocalDate monday = date.with(previousOrSame(MONDAY));
                LocalDate sunday = date.with(nextOrSame(SUNDAY));
                StackedBarChart <String, Number> oneWeek = uiTools.drawOneWeek(monday, sunday);
                primaryPane.setCenter(oneWeek);
            } catch (Exception ex) {
                Logger.getLogger(WorkoutJournalUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        previousMonthButton.setOnAction((event) -> {
            try {
                date = date.minusMonths(1);
                BorderPane monthlyStats = uiTools.drawMonthlyStats(date);
                primaryPane.setCenter(monthlyStats);
            } catch (Exception ex) {
                Logger.getLogger(WorkoutJournalUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        nextMonthButton.setOnAction((event) -> {
            try {
                date = date.plusMonths(1);
                BorderPane monthlyStats = uiTools.drawMonthlyStats(date);
                primaryPane.setCenter(monthlyStats);
            } catch (Exception ex) {
                Logger.getLogger(WorkoutJournalUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        stage.setScene(loginScene);
        stage.show();
    }
    
    public void stop() throws SQLException {
        conn.close();
    }
    
    public static void main(String[] args) throws SQLException {
        launch(WorkoutJournalUI.class);
    }   
}
