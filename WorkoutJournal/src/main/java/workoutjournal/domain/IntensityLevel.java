package workoutjournal.domain;

/**
 * Enum for exercise intensity levels and the color used to illustrate them in
 * the statistics.
 */
public enum IntensityLevel {
    strength("slategray"),
    light("lightgreen"),
    moderate("yellow"),
    hard("orange"),
    maximum("red");
    
    private String color;
    
    private IntensityLevel(String color) {
        this.color = color;
    }
    
    public String getColor() {
        return color;
    }
}
