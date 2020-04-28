package workoutjournal.domain;

/**
 * The User class defines the User object and its attributes and is used
 * in processing details of the current user.
 */
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

