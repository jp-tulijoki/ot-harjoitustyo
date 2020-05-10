package workoutjournal.ui;
import workoutjournal.dao.*;
import com.sun.javafx.scene.control.IntegerField;
import java.sql.*;
import java.time.LocalDate;
import java.util.logging.*;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import workoutjournal.domain.*;

/**
 * This class contains the main structure of the user interface.
 * @author tulijoki
 */
public class WorkoutJournalUI extends Application {
    
    private Connection conn;
    private UserDAO userDAO;
    private ExerciseDAO exerciseDAO; 
    private JournalTools jTools;
    private UITools uiTools;
    private LocalDate date;
    
    /**
     * The init method establishes the database connection, creates the 
     * database tables if necessary and creates the dependencies.
     */
    public void init() {
        
        try {            
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
            this.date = LocalDate.now();
        } catch (SQLException ex) {
            Logger.getLogger(WorkoutJournalUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * The start method contains the main structure of the user interface.
     * @param stage the stage used in the application.
     */
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
        sexes.getSelectionModel().selectFirst();
        Button countMaxHeartRateButton = new Button("Count max heart rate");
        Button createNewUserButton = new Button("Create new user");
        Button returnToLoginViewButton = new Button("Return to login view");
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
        newUserPane.add(returnToLoginViewButton, 1, 9);
        newUserPane.add(userCreationError, 0, 10);
        
        Scene newUserScene = new Scene(newUserPane);
        
        // Primary scene, actions menu on top and changing views depending of the action
        
        BorderPane primaryPane = new BorderPane();
        primaryPane.setPrefSize(1080, 540);
        
        MenuBar actionsMenu = new MenuBar();
        
        Menu settings = new Menu("Settings");
        MenuItem updateMaxHeartRate = new MenuItem("Update max heart rate");
        MenuItem changePassword = new MenuItem("Change password");
        MenuItem deleteUser = new MenuItem("Delete user account");
        MenuItem logout = new MenuItem("Log out");
        settings.getItems().addAll(updateMaxHeartRate, changePassword, deleteUser, logout);
        
        Menu exercises = new Menu("Exercises");
        MenuItem addExercise = new MenuItem("Add exercise");
        MenuItem previousExercises = new MenuItem("Previous exercises");
        MenuItem weeklySummary = new MenuItem("Weekly summary");
        MenuItem monthlySummary = new MenuItem("Monthly summary");
        exercises.getItems().addAll(addExercise, previousExercises, weeklySummary, monthlySummary);
        
        actionsMenu.getMenus().addAll(settings, exercises);
        
        primaryPane.setTop(actionsMenu);
        
        Scene primaryScene = new Scene(primaryPane);
        
        // Delete user view
        
        GridPane deleteUserConfirmation = new GridPane();
            
        deleteUserConfirmation.setAlignment(Pos.CENTER);
        deleteUserConfirmation.setHgap(10);
        deleteUserConfirmation.setVgap(10);
        deleteUserConfirmation.setPadding(new Insets(10,10,10,10));
            
        Label deleteUserConfirmationLabel = new Label("Are you sure you want to delete your user account?. This can not be undone.");
        Button deleteUserButton = new Button("Yes. Delete my account.");
        Button returnToWeeklySummaryButton = new Button("No. Return to weekly summary.");
        deleteUserConfirmation.add(deleteUserConfirmationLabel, 0, 0);
        deleteUserConfirmation.add(deleteUserButton, 0, 1);
        deleteUserConfirmation.add(returnToWeeklySummaryButton, 1, 1);
        
        // Select date tools for searching exercises
        
        GridPane sortExercisePane = new GridPane();
        sortExercisePane.setHgap(10);
        
        Label sortExerciseLabel = new Label("Sort exercises by selecting days.");
        Label beginDateLabel = new Label("Begin date: ");
        DatePicker beginDatePicker = new DatePicker(LocalDate.now());
        beginDatePicker.getEditor().setDisable(true);
        Label endDateLabel = new Label("End date: ");
        DatePicker endDatePicker = new DatePicker(LocalDate.now());
        endDatePicker.getEditor().setDisable(true);
        Button sortExercisesButton = new Button("Sort exercises by date");
        Label selectDateError = new Label("");
        
        sortExercisePane.add(sortExerciseLabel, 0, 0);
        sortExercisePane.add(beginDateLabel, 0, 1);
        sortExercisePane.add(beginDatePicker, 1, 1);
        sortExercisePane.add(endDateLabel, 2, 1);
        sortExercisePane.add(endDatePicker, 3, 1);
        sortExercisePane.add(sortExercisesButton, 0, 2);
        sortExercisePane.add(selectDateError, 1, 2);
        
        // Toggle week buttons for weekly summary
        
        HBox toggleWeekBox = new HBox();
        
        toggleWeekBox.setAlignment(Pos.CENTER);
        toggleWeekBox.setPadding(new Insets(10,10,10,10));
        
        Button previousWeekButton = new Button("Previous week");
        Button nextWeekButton = new Button("Next week");
        toggleWeekBox.getChildren().addAll(previousWeekButton, nextWeekButton);
        
        // Toggle month buttons for monthly summary
        
        HBox toggleMonthBox = new HBox();
        
        toggleMonthBox.setAlignment(Pos.CENTER);
        toggleMonthBox.setPadding(new Insets(10,10,10,10));
        
        Button previousMonthButton = new Button("Previous month");
        Button nextMonthButton = new Button("Next month");
        toggleMonthBox.getChildren().addAll(previousMonthButton, nextMonthButton);
        
        // Menu actions
                
        updateMaxHeartRate.setOnAction((event) -> {
            GridPane updateMaxHeartRateView = uiTools.updateMaxHeartRateView();
            primaryPane.setCenter(updateMaxHeartRateView);
            primaryPane.setBottom(null);
        });
        
        changePassword.setOnAction((event) -> {
            GridPane changePasswordView = uiTools.changePasswordView();
            primaryPane.setCenter(changePasswordView);
            primaryPane.setBottom(null);
        });
        
        deleteUser.setOnAction((event) -> {
            primaryPane.setCenter(deleteUserConfirmation);
            primaryPane.setBottom(null);
        });
        
        logout.setOnAction((event) -> {
            jTools.logout();
            stage.setScene(loginScene);
        });
        
        addExercise.setOnAction((event) -> {
            GridPane addExerciseView = uiTools.addExerciseView();
            primaryPane.setCenter(addExerciseView);
            primaryPane.setBottom(null);
        });
        
        previousExercises.setOnAction((event) -> {
            date = LocalDate.now();
            try {
                BorderPane exerciseTable = uiTools.exerciseTable(date.withDayOfMonth(1), date.withDayOfMonth(date.lengthOfMonth()));
                primaryPane.setCenter(exerciseTable);
                primaryPane.setBottom(sortExercisePane);
                beginDatePicker.setValue(LocalDate.now());
                endDatePicker.setValue(LocalDate.now());
                selectDateError.setText("");
            } catch (Exception ex) {
                BorderPane errorView = uiTools.errorView();
                primaryPane.setCenter(errorView);
                primaryPane.setBottom(null);
            }    
        });
        
        weeklySummary.setOnAction((event) -> {
            try {
                date = LocalDate.now();
                BorderPane weeklySummaryView = uiTools.weeklySummaryView(date);
                primaryPane.setCenter(weeklySummaryView);
                primaryPane.setBottom(toggleWeekBox);
            } catch (Exception ex) {
                BorderPane errorView = uiTools.errorView();
                primaryPane.setCenter(errorView);
                primaryPane.setBottom(null);
            }
        });
        
        monthlySummary.setOnAction((event) -> {
            date = LocalDate.now();
            try {
                BorderPane monthlySummaryPane = uiTools.monthlySummaryView(date);
                primaryPane.setCenter(monthlySummaryPane);
                primaryPane.setBottom(toggleMonthBox);
            } catch (Exception ex) {
                BorderPane errorView = uiTools.errorView();
                primaryPane.setCenter(errorView);
                primaryPane.setBottom(null);
            }            
        });
        
        // Actions for buttons
          
        loginButton.setOnAction((event) -> {
            try {
                if (jTools.login(usernameInput.getText(), passwordInput.getText())) {
                    BorderPane weeklySummaryView = uiTools.weeklySummaryView(date);
                    usernameInput.clear();
                    passwordInput.clear();
                    loginError.setText("");
                    loginInstruction.setText("Login with your username and password");
                    primaryPane.setCenter(weeklySummaryView);
                    primaryPane.setBottom(toggleWeekBox);
                    stage.setScene(primaryScene);
                } else {
                    loginError.setText("User not found or invalid password.");
                }
            } catch (Exception ex) {
                loginError.setText("Database connection lost or something unexpected happened. Try again later.");
            }
        });
        
        newUserButton.setOnAction((event) -> {
            usernameInput.clear();
            passwordInput.clear();
            loginError.setText("");
            loginInstruction.setText("Login with your username and password");
            stage.setScene(newUserScene);
        });
        
        countMaxHeartRateButton.setOnAction((event) -> {
            maxHeartRateInput.setValue(jTools.countMaxHeartRate(ageInput.getValue(), sexes.getValue()));
        });
        
        createNewUserButton.setOnAction((event) -> {
            String username = setUsernameInput.getText();
            String password = setPasswordInput.getText();
            try {
                if (username.length() < 3 || username.length() > 20 || password.length() < 3 || password.length() > 20) {
                    userCreationError.setText("Username and password length have to be between 3 and 20 characters.");
                } else if (maxHeartRateInput.getValue() == 0 || maxHeartRateInput.getValue() > 220) {
                    userCreationError.setText("Max heart rate must be between 1 and 220.");
                } else if (jTools.createUser(setUsernameInput.getText(), password, maxHeartRateInput.getValue())) {
                    loginInstruction.setText("New user created succesfully. You may now log in.");
                    setUsernameInput.clear();
                    setPasswordInput.clear();
                    maxHeartRateInput.setValue(0);
                    ageInput.setValue(0);
                    userCreationError.setText("");
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
        
        returnToLoginViewButton.setOnAction((event) -> {
            setUsernameInput.clear();
            setPasswordInput.clear();
            maxHeartRateInput.setValue(0);
            ageInput.setValue(0);
            userCreationError.setText("");
            stage.setScene(loginScene);
        });
        
        deleteUserButton.setOnAction((event) -> {
            try {
                if (jTools.deleteUser(jTools.getLoggedUser().getUsername())) {
                    loginError.setText("Your user account has been deleted. Thank you for using WorkoutJournal.");
                    stage.setScene(loginScene);
                } else {
                    BorderPane errorView = uiTools.errorView();
                    primaryPane.setCenter(errorView);
                    primaryPane.setBottom(null);
                }
            } catch (Exception ex) {
                BorderPane errorView = uiTools.errorView();
                primaryPane.setCenter(errorView);
                primaryPane.setBottom(null);
            }
        });
        
        returnToWeeklySummaryButton.setOnAction((event) -> {
            try {
                date = LocalDate.now();
                BorderPane weeklySummaryView = uiTools.weeklySummaryView(date);
                primaryPane.setCenter(weeklySummaryView);
                primaryPane.setBottom(toggleWeekBox);
            } catch (Exception ex) {
                BorderPane errorView = uiTools.errorView();
                primaryPane.setCenter(errorView);
                primaryPane.setBottom(null);
            }
        });
        
        sortExercisesButton.setOnAction((event) -> {
            try {
            if (endDatePicker.getValue().isBefore(beginDatePicker.getValue())) {
                selectDateError.setText("End date must not be before begin date.");
            } else {
                BorderPane exerciseTable = uiTools.exerciseTable(beginDatePicker.getValue(), endDatePicker.getValue());
                primaryPane.setCenter(exerciseTable);
                primaryPane.setBottom(sortExercisePane);
                selectDateError.setText("");
            }
            } catch (Exception ex) {
                BorderPane errorView = uiTools.errorView();
                primaryPane.setCenter(errorView);
                primaryPane.setBottom(null);
            }
        });
        
        previousWeekButton.setOnAction((event) -> {
            try {
                date = date.minusWeeks(1);
                BorderPane weeklySummaryView = uiTools.weeklySummaryView(date);
                primaryPane.setCenter(weeklySummaryView);
                primaryPane.setBottom(toggleWeekBox);
            } catch (Exception ex) {
                BorderPane errorView = uiTools.errorView();
                primaryPane.setCenter(errorView);
                primaryPane.setBottom(null);
            }
        });
        
        nextWeekButton.setOnAction((event) -> {
            try {
                date = date.plusWeeks(1);
                BorderPane weeklySummaryView = uiTools.weeklySummaryView(date);
                primaryPane.setCenter(weeklySummaryView);
                primaryPane.setBottom(toggleWeekBox);
            } catch (Exception ex) {
                BorderPane errorView = uiTools.errorView();
                primaryPane.setCenter(errorView);
                primaryPane.setBottom(null);
            }
        });
        
        previousMonthButton.setOnAction((event) -> {
            try {
                date = date.minusMonths(1);
                BorderPane monthlyStats = uiTools.monthlySummaryView(date);
                primaryPane.setCenter(monthlyStats);
            } catch (Exception ex) {
                BorderPane errorView = uiTools.errorView();
                primaryPane.setCenter(errorView);
                primaryPane.setBottom(null);
            }
        });
        
        nextMonthButton.setOnAction((event) -> {
            try {
                date = date.plusMonths(1);
                BorderPane monthlyStats = uiTools.monthlySummaryView(date);
                primaryPane.setCenter(monthlyStats);
            } catch (Exception ex) {
                BorderPane errorView = uiTools.errorView();
                primaryPane.setCenter(errorView);
                primaryPane.setBottom(null);
            }
        });
        
        stage.setScene(loginScene);
        stage.show();
    }
    
    /**
     * The stop method closes the database connection.
     */
    public void stop() {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(WorkoutJournalUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * The main method launches the application.
     * @param args 
     */
    public static void main(String[] args) {
        launch(WorkoutJournalUI.class);
    }   
}
