package com.healthylife.restapi.model;

public abstract class User {
    private String username, password, UID;
    private boolean firstTimeLoggedIn;

    public User(boolean firstTimeLoggedIn, String username, String password, String UID) {
        this.firstTimeLoggedIn = firstTimeLoggedIn;
        this.username = username;
        this.password = password;
        this.UID = UID;
    }

    public User() {

    }

    public boolean isFirstTimeLoggedIn() {
        return firstTimeLoggedIn;
    }

    public void setFirstTimeLoggedIn(boolean firstTimeLoggedIn) {
        this.firstTimeLoggedIn = firstTimeLoggedIn;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}

