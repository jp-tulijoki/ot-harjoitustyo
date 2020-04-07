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
public class User {
    private int id;
    private String username;
    private String name;
    private int maxHeartRate;

    public User(int id, String username, String name, int maxHeartRate) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.maxHeartRate = maxHeartRate;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
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

