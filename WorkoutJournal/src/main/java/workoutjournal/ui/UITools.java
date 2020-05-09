/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workoutjournal.ui;

import com.sun.javafx.charts.Legend;
import com.sun.javafx.scene.control.IntegerField;
import static java.lang.Double.isNaN;
import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.DayOfWeek.THURSDAY;
import static java.time.DayOfWeek.TUESDAY;
import static java.time.DayOfWeek.WEDNESDAY;
import java.time.LocalDate;
import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static java.time.temporal.TemporalAdjusters.previousOrSame;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import workoutjournal.domain.Exercise;
import workoutjournal.domain.IntensityLevel;
import workoutjournal.domain.JournalTools;
import workoutjournal.domain.Type;

/**
 *
 * @author tulijoki
 */
public class UITools {
    private JournalTools tools;
    private LocalDate date;

    public UITools(JournalTools tools) {
        this.tools = tools;
    }
    
    public GridPane updateMaxHeartRateView() {
        GridPane updateMaxHeartRatePane = new GridPane();
        Label updateMaxHeartRateLabel = new Label("Update max heart rate");
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
        Button updateMaxHeartRateButton = new Button("Update");
        Label updateMaxHeartRateStatus = new Label("");
        
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
        
        updateMaxHeartRateButton.setOnAction((event) -> {
            try {
                tools.updateMaxHeartRate(maxHeartRateInput.getValue());
                updateMaxHeartRateStatus.setText("Max heart rate saved successfully");
            } catch (Exception ex) {
                Logger.getLogger(WorkoutJournalUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        return updateMaxHeartRatePane;
    }
    
    public GridPane changePasswordView() {
        GridPane changePasswordPane = new GridPane();
        Label changePasswordLabel = new Label("Change password");
        Label oldPasswordLabel = new Label("Old password");
        PasswordField oldPasswordInput = new PasswordField();
        Label newPasswordLabel = new Label("New password");
        PasswordField newPasswordInput = new PasswordField();
        Button changePasswordButton = new Button("Change");
        Label changePasswordStatus = new Label("");
        
        changePasswordPane.add(changePasswordLabel, 0, 0);
        changePasswordPane.add(oldPasswordLabel, 0, 1);
        changePasswordPane.add(oldPasswordInput, 1, 1);
        changePasswordPane.add(newPasswordLabel, 0, 2);
        changePasswordPane.add(newPasswordInput, 1, 2);
        changePasswordPane.add(changePasswordButton, 1, 3);
        changePasswordPane.add(changePasswordStatus, 1, 4);
        
        changePasswordButton.setOnAction((event) -> {
        try {
            if (newPasswordInput.getText().length() < 3) {
                changePasswordStatus.setText("New password has to be at least 3 characters long.");
            } else if (tools.changePassword(oldPasswordInput.getText(), newPasswordInput.getText())) {
                changePasswordStatus.setText("Password changed successfully.");
            } else {
                changePasswordStatus.setText("Incorrect old password.");
            }
            } catch (Exception ex) {
                Logger.getLogger(WorkoutJournalUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        return changePasswordPane;
    }
    
    public GridPane addExerciseView() {
        GridPane addExercisePane = new GridPane();
        
        Label addExerciseLabel = new Label("Add new exercise");
        Label dateLabel = new Label("Date:");
        DatePicker datePicker = new DatePicker(LocalDate.now());
        datePicker.getEditor().setDisable(true);
        Label typeLabel = new Label("Type:");
        ChoiceBox<Type> types = new ChoiceBox();
        types.getItems().addAll(Type.strength, Type.endurance);
        types.getSelectionModel().selectFirst();
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
        
        addExerciseButton.setOnAction((event) -> {
            try {
                tools.addExercise(tools.getLoggedUser().getId(), datePicker.getValue(), types.getValue(), durationInput.getValue(), distanceInput.getValue(), avgHeartRateInput.getValue(), descriptionInput.getText());
            } catch (Exception ex) {
                addExerciseConfirmation.setText("Database connection lost. Try again later.");
            }
            addExerciseConfirmation.setText("Exercise added succesfully");
            durationInput.setValue(0);
            distanceInput.setValue(0);
            avgHeartRateInput.setValue(0);
            descriptionInput.clear();
        });
        
        return addExercisePane;
    }
    
    public BorderPane weeklySummaryView(LocalDate date) {
        
        BorderPane weeklySummaryView = new BorderPane();
        
        try {
            LocalDate monday = date.with(previousOrSame(MONDAY));
            LocalDate sunday = date.with(nextOrSame(SUNDAY));
            StackedBarChart <String, Number> oneWeekChart = oneWeekChart(monday, sunday);
            weeklySummaryView.setCenter(oneWeekChart);
            
        } catch (Exception ex) {
            Logger.getLogger(WorkoutJournalUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return weeklySummaryView;
    }
    
    public StackedBarChart<String, Number> oneWeekChart(LocalDate monday, LocalDate sunday) throws Exception {
        
        String[] weekdays = {"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};
        ArrayList<Exercise> exercises = tools.getExerciseList(monday, sunday);
        int[][] durations = new int[7][3];
        String[][] intensityLevelColor = new String[7][3];
        int dateOrdinal = 0;
        for (Exercise exercise : exercises) {
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
        StackedBarChart<String, Number> oneWeekChart = new StackedBarChart<>(dates, duration);
        
        oneWeekChart.setTitle("Exercises " + monday.toString() + " - " + sunday.toString());
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
        oneWeekChart.getData().addAll(series1, series2, series3);
        for (Node n : oneWeekChart.lookupAll(".default-color0.chart-bar")) {
            if (intensityLevelColor[c][0] != null) {
                n.setStyle("-fx-bar-fill: " + intensityLevelColor[c][0]);    
            }
            c++;
        }
        
        c = 0;
        for (Node n : oneWeekChart.lookupAll(".default-color1.chart-bar")) {
            if (intensityLevelColor[c][1] != null) {
                n.setStyle("-fx-bar-fill: " + intensityLevelColor[c][1]);    
            }
            c++;
        }
        
        c = 0;
        for (Node n : oneWeekChart.lookupAll(".default-color2.chart-bar")) {
            if (intensityLevelColor[c][2] != null) {
                n.setStyle("-fx-bar-fill: " + intensityLevelColor[c][2]);    
            }
            c++;
        }

        Legend legend = (Legend)oneWeekChart.lookup(".chart-legend");
        legend.getItems().clear();
        Legend.LegendItem light = new Legend.LegendItem("light", new Rectangle(10,10,Color.LIGHTGREEN));
        Legend.LegendItem moderate = new Legend.LegendItem("moderate", new Rectangle(10,10,Color.YELLOW));
        Legend.LegendItem hard = new Legend.LegendItem("hard", new Rectangle(10,10,Color.ORANGE));
        Legend.LegendItem maximum = new Legend.LegendItem("maximum", new Rectangle(10,10,Color.RED));
        Legend.LegendItem strength = new Legend.LegendItem("strength", new Rectangle(10,10,Color.SLATEGRAY));
        legend.getItems().addAll(light, moderate, hard, maximum, strength);
        return oneWeekChart;
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
    
    public PieChart monthlyTrainingsDistribution(double[][] stats) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
            new PieChart.Data("Strength", stats[0][0]),
            new PieChart.Data("Light", stats[1][0]),
            new PieChart.Data("Moderate", stats[2][0]),
            new PieChart.Data("Hard", stats[3][0]),
            new PieChart.Data("Maximum", stats[3][0]));
        
        PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Distribution of training types and intensities");
        int i = 0;
        for (PieChart.Data data : pieChartData) {
            data.getNode().setStyle("-fx-pie-color: " + IntensityLevel.values()[i].getColor());
            i++;
        }
        Legend legend = (Legend)chart.lookup(".chart-legend");
        legend.getItems().clear();
        Legend.LegendItem light = new Legend.LegendItem("light", new Rectangle(10,10,Color.LIGHTGREEN));
        Legend.LegendItem moderate = new Legend.LegendItem("moderate", new Rectangle(10,10,Color.YELLOW));
        Legend.LegendItem hard = new Legend.LegendItem("hard", new Rectangle(10,10,Color.ORANGE));
        Legend.LegendItem maximum = new Legend.LegendItem("maximum", new Rectangle(10,10,Color.RED));
        Legend.LegendItem strength = new Legend.LegendItem("strength", new Rectangle(10,10,Color.SLATEGRAY));
        legend.getItems().addAll(light, moderate, hard, maximum, strength);
        
        return chart;
    }
    
    public BarChart<String, Number> monthlySpeedStats(double[][] currentMonthStats, double[][] previousMonthStats) {
        CategoryAxis intensityLevels = new CategoryAxis();
        intensityLevels.setLabel("Intensity level");
        NumberAxis avgSpeed = new NumberAxis();
        avgSpeed.setLabel("Average speed");
        BarChart<String, Number> chart = new BarChart(intensityLevels, avgSpeed);
        chart.setTitle("Average speeds per intensity level");
        
        XYChart.Series currentMonth = new XYChart.Series<>();
        currentMonth.setName("current month");
        XYChart.Series previousMonth = new XYChart.Series<>();
        previousMonth.setName("previous month");
        
        for (int i = 1; i <=4; i++) {
            currentMonth.getData().add(new XYChart.Data<>(IntensityLevel.values()[i].toString(), currentMonthStats[i][2]));
            previousMonth.getData().add(new XYChart.Data<>(IntensityLevel.values()[i].toString(), previousMonthStats[i][2]));
        }
        
        chart.getData().addAll(currentMonth, previousMonth);
        
        return chart;
    }
    
    public BorderPane drawMonthlyStats(LocalDate date) throws Exception {
        
        double[][] currentMonthStats = tools.countMonthlyStats(date);
        double[][] previousMonthStats = tools.countMonthlyStats(date.minusMonths(1));
        double development = tools.countMonthlyDistanceDevelopment(currentMonthStats[5][1], previousMonthStats[5][1]);
        boolean[] analysis = tools.monthlyAnalysis(currentMonthStats);
        
        BorderPane monthlyStats = new BorderPane();
        
        VBox monthlyAnalysis = new VBox();
        Text monthlyAnalysisTitle = new Text("Monthly analysis of " + date.getMonth() + " " + date.getYear() + ": ");
        Text monthlyDistance = new Text("Your total distance covered is " + currentMonthStats[5][1] + " kilometers.");
        Text monthlyDevelopment = new Text("");
        
        if (isNaN(development)) {
            monthlyDevelopment.setText("As there are no running exercises in the month before, distance development can not be counted");
        } else if (development < 0) {
            monthlyDevelopment.setText("Compared to previous month, your total distance covered decreased by " + Math.abs(development) + " %.");
        } else {
            monthlyDevelopment.setText("Compared to previous month, your total distance covered increased by " + development + " %. Good job!");
        }
        
        Text monthlyLightRunning = new Text();
        
        if (analysis[0]) {
            monthlyLightRunning.setText("The rate of light intensity running is above 80 % as it should be. ");
        } else {
            monthlyLightRunning.setText("The rate of light intensity running is below 80 %. Please take care that your training does not become too exhausting. ");
        }
        
        Text runningExerciseVariation = new Text();
        
        if (analysis[1]) {
            runningExerciseVariation.setText("There is a good variation in your faster running exercises.");
        } else {
            runningExerciseVariation.setText("Please take care that you vary your speed in your faster running exercises to improve your development. ");
        }
        Text strengthTraining = new Text();
        
        if (analysis[2] == false) {
            strengthTraining.setText("Take care that you don't forget strength training.");
        }
        
        monthlyAnalysis.getChildren().addAll(monthlyAnalysisTitle, monthlyDistance, monthlyDevelopment, monthlyLightRunning, runningExerciseVariation, strengthTraining);
                
        PieChart trainingsDistribution = monthlyTrainingsDistribution(currentMonthStats);
        BarChart speedStats = monthlySpeedStats(currentMonthStats, previousMonthStats);

        monthlyStats.setLeft(trainingsDistribution);
        monthlyStats.setRight(speedStats);
        monthlyStats.setBottom(monthlyAnalysis);
        
        return monthlyStats;
    }
    
    public BorderPane drawExerciseTable(LocalDate beginDate, LocalDate endDate) throws Exception {
        BorderPane exercisePane = new BorderPane();
        
        TableView<Exercise> exerciseTable = new TableView();
        TableColumn<Exercise, LocalDate> dateColumn = new TableColumn("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory("date"));
        TableColumn<Exercise, Type> typeColumn = new TableColumn("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory("type"));
        TableColumn<Exercise, Integer> durationColumn = new TableColumn("Duration");
        durationColumn.setCellValueFactory(new PropertyValueFactory("duration"));
        TableColumn<Exercise, Integer> distanceColumn = new TableColumn("Distance");
        distanceColumn.setCellValueFactory(new PropertyValueFactory("distance"));
        TableColumn <Exercise, Integer> avgHeartRateColumn = new TableColumn("Average heart rate");
        avgHeartRateColumn.setCellValueFactory(new PropertyValueFactory("avgHeartRate"));
        TableColumn<Exercise, String> descriptionColumn = new TableColumn("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory("description"));
        exerciseTable.getColumns().addAll(dateColumn, typeColumn, durationColumn, distanceColumn, avgHeartRateColumn, descriptionColumn);
        ArrayList<Exercise> exerciseList = tools.getExerciseList(beginDate, endDate);
        ObservableList<Exercise> exerciseData = FXCollections.observableArrayList(exerciseList);
        exerciseTable.setItems(exerciseData);
        
        Button deleteButton = new Button("Delete exercise");
        exercisePane.setTop(exerciseTable);
        exercisePane.setBottom(deleteButton);
        
        deleteButton.setOnAction((event) -> {
            try {
                Exercise selectedExercise = exerciseTable.getSelectionModel().getSelectedItem();
                tools.deleteExercise(selectedExercise.getId());
                exerciseTable.getItems().remove(selectedExercise);
            } catch (Exception ex) {
                Logger.getLogger(UITools.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        return exercisePane;  
    }
}
