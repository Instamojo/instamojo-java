package com.instamojo.wrapper.exception;

public class HTTPException extends Exception {

    private int statusCode;

    private String jsonPayload;

    public HTTPException(int statusCode, String message, String jsonPayload) {
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
