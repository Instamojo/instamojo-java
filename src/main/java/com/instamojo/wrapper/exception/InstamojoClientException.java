package com.instamojo.wrapper.exception;

public class InstamojoClientException extends Exception {

    private int statusCode;

    private String jsonPayload;

    public InstamojoClientException(int statusCode, String message, String jsonPayload) {
        super(message);
        this.statusCode = statusCode;
        this.jsonPayload = jsonPayload;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getJsonPayload() {
        return jsonPayload;
    }
}
