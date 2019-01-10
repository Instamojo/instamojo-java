package com.instamojo.wrapper.model;

import com.google.gson.annotations.SerializedName;
import org.joda.time.DateTime;

/**
 * The Class Refund.
 */
public class Refund {

    @SerializedName("id")
    private String id;

    @SerializedName("payment_id")
    private String paymentId;

    @SerializedName("status")
    private String status;

    @SerializedName("type")
    private String type;

    @SerializedName("body")
    private String body;

    @SerializedName("refund_amount")
    private Double refundAmount;

    @SerializedName("total_amount")
    private Double totalAmount;

    @SerializedName("created_at")
    private DateTime createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Refund{" +
                "id='" + id + '\'' +
                ", paymentId='" + paymentId + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", body='" + body + '\'' +
                ", refundAmount=" + refundAmount +
                ", totalAmount=" + totalAmount +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
