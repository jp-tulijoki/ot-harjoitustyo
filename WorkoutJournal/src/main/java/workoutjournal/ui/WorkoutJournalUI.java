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
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import workoutjournal.domain.*;

public class WorkoutJournalUI extends Application {
    
    private Connection conn;
    private UserDAO userDAO;
    private ExerciseDAO exerciseDAO; 
    private JournalTools tools;
    private LocalDate today;
    
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
        
        this.tools = new JournalTools(userDAO, exerciseDAO);
        this.today = LocalDate.now();
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
        
        // Montly summary
        
        VBox monthlyStats = new VBox();
        monthlyStats.setSpacing(10);
        
        Label monthlyDistanceLabel = new Label("");
        Label monthlyDevelopmentLabel = new Label("");
        
        monthlyStats.getChildren().addAll(monthlyDistanceLabel, monthlyDevelopmentLabel);
        
        // Menu actions
        
        logout.setOnAction((event) -> {
            tools.logout();
            stage.setScene(loginScene);
        });
        
        addExercise.setOnAction((event) -> {
            primaryPane.setCenter(addExercisePane);
        });
        
        weeklySummary.setOnAction((event) -> {
            try {
                LocalDate monday = today.with(previousOrSame(MONDAY));
                LocalDate sunday = today.with(nextOrSame(SUNDAY));
                StackedBarChart <String, Number> oneWeek = drawOneWeek(monday, sunday);
                primaryPane.setCenter(oneWeek);
                stage.setScene(primaryScene);
            } catch (Exception ex) {
                Logger.getLogger(WorkoutJournalUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        monthlySummary.setOnAction((event) -> {
            try {
                int currentMonthDistance = tools.countMonthlyDistance(today);
                int previousMonthDistance = tools.countMonthlyDistance(today.minusMonths(1));
                double development = tools.countMonthlyDistanceDevelopment(currentMonthDistance, previousMonthDistance);
                monthlyDistanceLabel.setText("Your total distance covered in " + today.minusMonths(1).getMonth() + " " + today.minusMonths(1).getYear() + " was " + currentMonthDistance + " kilometers.");
                if (isNaN(development)) {
                    monthlyDevelopmentLabel.setText("As there are no running exercises in the month before, distance development can not be counted");
                } else if (development < 0) {
                    monthlyDevelopmentLabel.setText("Compared to previous month, your total distance covered decreased by " + Math.round(Math.abs(development) * 100) + " %.");
                } else {
                    monthlyDevelopmentLabel.setText("Compared to previous month, your total distance covered increased by " + Math.round(development * 100) + " %. Good job!");
                }
            } catch (Exception ex) {
                Logger.getLogger(WorkoutJournalUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            primaryPane.setCenter(monthlyStats);
        });
        
        // Actions for buttons
          
        loginButton.setOnAction((event) -> {
            try {
                if (tools.login(usernameInput.getText(), passwordInput.getText())) {
                    LocalDate monday = today.with(previousOrSame(MONDAY));
                    LocalDate sunday = today.with(nextOrSame(SUNDAY));
                    StackedBarChart <String, Number> oneWeek = drawOneWeek(monday, sunday);
                    primaryPane.setCenter(oneWeek);
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
            stage.setScene(newUserScene);
        });
        
        countMaxHeartRateButton.setOnAction((event) -> {
            maxHeartRateInput.setValue(tools.countMaxHeartRate(ageInput.getValue(), sexes.getValue()));
        });
        
        createNewUserButton.setOnAction((event) -> {
            String username = setUsernameInput.getText();
            String password = setPasswordInput.getText();
            try {
                if (username.length() < 3 || password.length() < 3) {
                    userCreationError.setText("Username and password have to be at least 3 characters long.");
                } else if (tools.createUser(setUsernameInput.getText(), password, maxHeartRateInput.getValue())) {
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
        
        addExerciseButton.setOnAction((event) -> {
            int type = 1;
            if (types.getValue().equals("strength")) {
                type = 2;
            }
            try {
                tools.addExercise(tools.getLoggedUser().getId(), datePicker.getValue(), type, durationInput.getValue(), distanceInput.getValue(), avgHeartRateInput.getValue(), descriptionInput.getText());
            } catch (Exception ex) {
                addExerciseConfirmation.setText("Database connection lost. Try again later.");
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
    
    public void stop() throws SQLException {
        conn.close();
    }
    
    // Creates the barChart stats of the training sessions of one week. 
    
    public StackedBarChart<String, Number> drawOneWeek(LocalDate monday, LocalDate sunday) throws Exception {
        
        String[] weekdays = {"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};
        LocalDate date = monday;
        ArrayList<Exercise> exercisesOfTheWeek = tools.getExerciseList(monday, sunday);
        int[][] durations = new int[7][3];
        String[][] intensityLevelColor = new String[7][3];
        int dateOrdinal = 0;
        for (Exercise exercise : exercisesOfTheWeek) {
            dateOrdinal = countDateOrdinal(exercise.getDate());
            if (durations[dateOrdinal][0] == 0) {
                durations[dateOrdinal][0] = exercise.getDuration();
                intensityLevelColor[dateOrdinal][0] = tools.countIntensityLevel(exercise).getColor();
            } else if (durations[dateOrdinal][1] == 0) {
                durations[dateOrdinal][1] = exercise.getDuration();
                intensityLevelColor[dateOrdinal][1] = tools.countIntensityLevel(exercise).getColor();
            } else {
                durations[dateOrdinal][2] = exercise.getDuration();
                intensityLevelColor[dateOrdinal][2] = tools.countIntensityLevel(exercise).getColor();
            }
        }
        
        CategoryAxis dates = new CategoryAxis();
        dates.setLabel("Intensity level");
        NumberAxis duration = new NumberAxis();
        duration.setLabel("Duration (minutes)");
        StackedBarChart<String, Number> oneWeek = new StackedBarChart<>(dates, duration);
        
        oneWeek.setTitle("Exercises " + monday.toString() + " - " + sunday.toString());
        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        XYChart.Series series3 = new XYChart.Series();
        
        for (int i = 0; i < 7; i++) {
            XYChart.Data item = new XYChart.Data<>(weekdays[i], durations[i][0]);
            series1.getData().add(item);
            item = new XYChart.Data<>(weekdays[i], durations[i][1]);
            series2.getData().add(item);
            item = new XYChart.Data<>(weekdays[i], durations[i][2]);
            series3.getData().add(item);
        }

        int c = 0;
        oneWeek.getData().addAll(series1, series2, series3);
        for (Node n : oneWeek.lookupAll(".default-color0.chart-bar")) {
            if (intensityLevelColor[c][0] != null) {
                n.setStyle("-fx-bar-fill: " + intensityLevelColor[c][0]);    
            }
            c++;
        }
        
        c = 0;
        for (Node n : oneWeek.lookupAll(".default-color1.chart-bar")) {
            if (intensityLevelColor[c][1] != null) {
                n.setStyle("-fx-bar-fill: " + intensityLevelColor[c][1]);    
            }
            c++;
        }
        
        c = 0;
        for (Node n : oneWeek.lookupAll(".default-color2.chart-bar")) {
            if (intensityLevelColor[c][2] != null) {
                n.setStyle("-fx-bar-fill: " + intensityLevelColor[c][2]);    
            }
            c++;
        }

        Legend legend = (Legend)oneWeek.lookup(".chart-legend");
        legend.getItems().clear();
        Legend.LegendItem light = new Legend.LegendItem("light", new Rectangle(10,10,Color.LIGHTGREEN));
        Legend.LegendItem moderate = new Legend.LegendItem("moderate", new Rectangle(10,10,Color.YELLOW));
        Legend.LegendItem hard = new Legend.LegendItem("hard", new Rectangle(10,10,Color.ORANGE));
        Legend.LegendItem maximum = new Legend.LegendItem("maximum", new Rectangle(10,10,Color.RED));
        Legend.LegendItem strength = new Legend.LegendItem("strength", new Rectangle(10,10,Color.SLATEGRAY));
        legend.getItems().addAll(light, moderate, hard, maximum, strength);
        return oneWeek;
    }
    
    public int countDateOrdinal(LocalDate date) {
        if (date.getDayOfWeek() == MONDAY) {
            return 0;
        } else if (date.getDayOfWeek() == TUESDAY) {
            return 1;
        } else if (date.getDayOfWeek() == WEDNESDAY) {
            return 2;
        } else if (date.getDayOfWeek() == THURSDAY) {
            return 3;
        } else if (date.getDayOfWeek() == FRIDAY) {
            return 4;
        } else if (date.getDayOfWeek() == SATURDAY) {
            return 5;
        } else {
            return 6;
        }   
    }
    
    public static void main(String[] args) throws SQLException {
        launch(WorkoutJournalUI.class);
    }   
}
