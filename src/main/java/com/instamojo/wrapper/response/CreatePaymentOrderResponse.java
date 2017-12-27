package com.instamojo.wrapper.response;

import com.google.gson.annotations.SerializedName;
import com.instamojo.wrapper.model.PaymentOptions;
import com.instamojo.wrapper.model.PaymentOrder;

/**
 * The Class CreatePaymentOrderResponse.
 */
public class CreatePaymentOrderResponse extends Response {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2402922891686730624L;

    /** The payment order. */
    @SerializedName("order")
	private PaymentOrder paymentOrder;
    
    /** The payment options. */
    @SerializedName("payment_options")
    private PaymentOptions paymentOptions;

    /**
     * Gets the payment order.
     *
     * @return the payment order
     */
    public PaymentOrder getPaymentOrder() {
        return paymentOrder;
    }

    /**
     * Sets the payment order.
     *
     * @param paymentOrder the new payment order
     */
    public void setPaymentOrder(PaymentOrder paymentOrder) {
        this.paymentOrder = paymentOrder;
    }

    /**
     * Gets the payment options.
     *
     * @return the payment options
     */
    public PaymentOptions getPaymentOptions() {
		return paymentOptions;
	}

	/**
	 * Sets the payment options.
	 *
	 * @param paymentOptions the new payment options
	 */
	public void setPaymentOptions(PaymentOptions paymentOptions) {
		this.paymentOptions = paymentOptions;
	}

	/* (non-Javadoc)
	 * @see com.instamojo.wrapper.response.Response#toString()
	 */
	@Override
	public String toString() {
		return "CreatePaymentOrderResponse{" + "paymentOrder=" + paymentOrder +
				", paymentOptions=" + paymentOptions +
				", success=" + success +
				", message='" + message + '\'' +
				", jsonResponse='" + jsonResponse + '\'' +
				'}';
	}
}
