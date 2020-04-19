/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workoutjournal.domain;

import java.time.LocalDate;

/**
 *
 * @author tulijoki
 */
public class Exercise {
    private int user_id;
    private LocalDate date;
    private int type;
    private int duration;
    private int distance;
    private int avgHeartRate;
    private String description;

    public Exercise(int user_id, LocalDate date, int type, int duration, int distance, int avgHeartRate, String description) {
        this.user_id = user_id;
        this.date = date;
        this.type = type;
        this.duration = duration;
        this.distance = distance;
        this.avgHeartRate = avgHeartRate;
        this.description = description;
    }

    
    
    public int getUser_id() {
        return user_id;
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
