/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workoutjournal.domain;

/**
 *
 * @author tulijoki
 */
public enum IntensityLevel {
    STRENGTH("slategray"),
    LIGHT("lightgreen"),
    MODERATE("yellow"),
    HARD("orange"),
    MAXIMUM("red");
    
    private String color;
    
    private IntensityLevel(String color) {
        this.color = color;
    }
    
    public String getColor() {
        return color;
    }
}
