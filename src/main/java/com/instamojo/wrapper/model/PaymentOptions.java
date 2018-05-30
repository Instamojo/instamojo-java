package com.instamojo.wrapper.model;

import com.google.gson.annotations.SerializedName;

/**
 * The Class PaymentOptions.
 */
public class PaymentOptions {

    /**
     * The payment url.
     */
    @SerializedName("payment_url")
    private String paymentUrl;

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    @Override
    public String toString() {
        return "PaymentOrder{" + "paymentUrl='" + paymentUrl + '\'' +
                '}';
    }

}
