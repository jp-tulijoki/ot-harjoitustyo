/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workoutjournal.ui;

import com.sun.javafx.charts.Legend;
import static java.lang.Double.isNaN;
import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.THURSDAY;
import static java.time.DayOfWeek.TUESDAY;
import static java.time.DayOfWeek.WEDNESDAY;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import workoutjournal.domain.Exercise;
import workoutjournal.domain.IntensityLevel;
import workoutjournal.domain.JournalTools;

/**
 *
 * @author tulijoki
 */
public class UITools {
    private JournalTools tools;

    public UITools(JournalTools tools) {
        this.tools = tools;
    }
    
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
    
    public PieChart monthlyTrainingsDistribution(double[][] stats) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
            new PieChart.Data("Strength", stats[0][0]),
            new PieChart.Data("Light", stats[1][0]),
            new PieChart.Data("Moderate", stats[2][0]),
            new PieChart.Data("Hard", stats[3][0]),
            new PieChart.Data("Maximum", stats[3][0]));
        
        PieChart chart = new PieChart(pieChartData);
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
        
        XYChart.Series currentMonth = new XYChart.Series<>();
        XYChart.Series previousMonth = new XYChart.Series<>();
        
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
        
        BorderPane monthlyStats = new BorderPane();
        
        VBox monthlyAnalysis = new VBox();
        Label monthlyDistanceLabel = new Label("Your total distance covered in " + date.minusMonths(1).getMonth() + " " + date.minusMonths(1).getYear() + " was " + currentMonthStats[5][1] + " kilometers.");
        Label monthlyDevelopmentLabel = new Label("");
        
        monthlyAnalysis.getChildren().addAll(monthlyDistanceLabel, monthlyDevelopmentLabel);

        if (isNaN(development)) {
            monthlyDevelopmentLabel.setText("As there are no running exercises in the month before, distance development can not be counted");
        } else if (development < 0) {
            monthlyDevelopmentLabel.setText("Compared to previous month, your total distance covered decreased by " + Math.abs(development) + " %.");
        } else {
            monthlyDevelopmentLabel.setText("Compared to previous month, your total distance covered increased by " + development + " %. Good job!");
        }
                
        PieChart trainingsDistribution = monthlyTrainingsDistribution(currentMonthStats);
        BarChart speedStats = monthlySpeedStats(currentMonthStats, previousMonthStats);

        monthlyStats.setLeft(trainingsDistribution);
        monthlyStats.setRight(speedStats);
        monthlyStats.setBottom(monthlyAnalysis);
        
        return monthlyStats;
    }
    
    public TableView<Exercise> drawExerciseTable(LocalDate date) throws Exception {
        TableView<Exercise> exerciseTable = new TableView();
        TableColumn<Exercise, LocalDate> dateColumn = new TableColumn("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<Exercise, LocalDate>("date"));
        TableColumn<Exercise, Integer> typeColumn = new TableColumn("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<Exercise, Integer>("type"));
        TableColumn<Exercise, Integer> durationColumn = new TableColumn("Duration");
        durationColumn.setCellValueFactory(new PropertyValueFactory<Exercise, Integer>("duration"));
        TableColumn<Exercise, Integer> distanceColumn = new TableColumn("Distance");
        distanceColumn.setCellValueFactory(new PropertyValueFactory<Exercise, Integer>("distance"));
        TableColumn <Exercise, Integer> avgHeartRateColumn = new TableColumn("Average heart rate");
        avgHeartRateColumn.setCellValueFactory(new PropertyValueFactory<Exercise, Integer>("avgHeartRate"));
        TableColumn<Exercise, String> descriptionColumn = new TableColumn("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Exercise, String>("type"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Exercise, Integer>("type"));
        exerciseTable.getColumns().addAll(dateColumn, typeColumn, durationColumn, distanceColumn, avgHeartRateColumn, descriptionColumn);
        ArrayList<Exercise> exerciseList = tools.getExerciseList(date.minusMonths(1), date.plusMonths(1));
        ObservableList<Exercise> exerciseData = FXCollections.observableArrayList(exerciseList);
        exerciseTable.setItems(exerciseData);
        return exerciseTable;  
    }
}
