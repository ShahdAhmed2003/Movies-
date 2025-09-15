package com.movieflix.movieApi.auth.utils;

public class RegisterRequest {
    private String name;
    private String email;
    private String username;
    private String password;

    public RegisterRequest(String name, String email, String username, String password) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public RegisterRequest() {
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
