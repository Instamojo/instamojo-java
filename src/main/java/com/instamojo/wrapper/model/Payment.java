package com.instamojo.wrapper.model;

public class Payment {

    /**
     * The id.
     */
    private String id;

    /**
     * The status.
     */
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Payment{" + "id='" + id + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
