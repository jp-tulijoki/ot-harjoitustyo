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

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getMaxHeartRate() {
        return maxHeartRate;
    }
    
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        return this.username.equals(other.username);
    }     
}

