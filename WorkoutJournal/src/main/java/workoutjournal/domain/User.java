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
    private String username;
    private String name;
    private int age;
    private int maxHeartRate;

    public User(String username, String name, int age, int maxHeartRate) {
        this.username = username;
        this.name = name;
        this.age = age;
        this.maxHeartRate = maxHeartRate;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
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

