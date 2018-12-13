package com.instamojo.wrapper.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ApiResponse<T> implements Serializable {

    // HACK This is to accommodate the results field having different serialized names
    // Should have been a generic name like 'results'
    @SerializedName(value = "refund", alternate = {"payout"})
    private T result;

    @SerializedName("success")
    private Boolean success;

    @SerializedName("message")
    private String message;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "result=" + result +
                ", success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}
