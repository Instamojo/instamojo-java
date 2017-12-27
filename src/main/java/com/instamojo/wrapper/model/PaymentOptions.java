package com.instamojo.wrapper.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * The Class PaymentOptions.
 */
public class PaymentOptions implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6334423944197677094L;

	/** The payment url. */
	@SerializedName("payment_url")
	private String paymentUrl;

	/**
	 * Gets the payment url.
	 *
	 * @return the payment url
	 */
	public String getPaymentUrl() {
		return paymentUrl;
	}

	/**
	 * Sets the payment url.
	 *
	 * @param paymentUrl the new payment url
	 */
	public void setPaymentUrl(String paymentUrl) {
		this.paymentUrl = paymentUrl;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
        return "PaymentOrder{" + "paymentUrl='" + paymentUrl + '\'' +
                '}';
	}
	
}
