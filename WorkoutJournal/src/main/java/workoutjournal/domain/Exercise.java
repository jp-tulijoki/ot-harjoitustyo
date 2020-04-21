package workoutjournal.domain;

import java.time.LocalDate;

public class Exercise {
    private int userId;
    private LocalDate date;
    private int type;
    private int duration;
    private int distance;
    private int avgHeartRate;
    private String description;

    public Exercise(int userId, LocalDate date, int type, int duration, int distance, int avgHeartRate, String description) {
        this.userId = userId;
        this.date = date;
        this.type = type;
        this.duration = duration;
        this.distance = distance;
        this.avgHeartRate = avgHeartRate;
        this.description = description;
    }
    
    public int getUserId() {
        return userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getType() {
        return type;
    }

    public int getDuration() {
        return duration;
    }

    public int getDistance() {
        return distance;
    }

    public int getAvgHeartRate() {
        return avgHeartRate;
    }

    public String getDescription() {
        return description;
    }    
}
