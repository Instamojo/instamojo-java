package com.instamojo.wrapper.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiListResponse<T> {

    // HACK This is to accommodate the results field having different serialized names
    // Should have been a generic name like 'results'
    @SerializedName(value = "invoices", alternate = {"orders", "payouts"})
    private List<T> results;

    private Boolean success;

    private String message;

    /** The count. */
    private Integer count;

    /** The next. */
    private String next;

    /** The previous. */
    private String previous;

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    @Override
    public String toString() {
        return "ApiListResponse{" +
                "count=" + count +
                ", next='" + next + '\'' +
                ", previous='" + previous + '\'' +
                '}';
    }
}
