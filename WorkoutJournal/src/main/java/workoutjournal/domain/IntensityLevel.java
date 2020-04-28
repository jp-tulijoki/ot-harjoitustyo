package workoutjournal.domain;

/**
 * Enum for exercise intensity levels and the color used to illustrate them in
 * the statistics.
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
