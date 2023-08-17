package com.example.newcalendar;

public class LoginUser {
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginUser() {
    }

    public String username;
    public String password;

    public LoginUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
