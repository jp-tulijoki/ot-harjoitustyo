package workoutjournal.domain;

public class User {
    private int id;
    private String username;
    private String password;
    private int maxHeartRate;

    public User(int id, String username, String name, int maxHeartRate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.maxHeartRate = maxHeartRate;
    }

    public int getId() {
        return id;
    }

    public int getMaxHeartRate() {
        return maxHeartRate;
    }
    
}

