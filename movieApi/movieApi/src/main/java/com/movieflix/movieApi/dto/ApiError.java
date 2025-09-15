package com.movieflix.movieApi.dto;

public class ApiError {
    private String message;
    private String details;
    private int status;

    public ApiError(String message, String details, int status) {
        this.message = message;
        this.details = details;
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public int getStatus() {
        return status;
    }
}
