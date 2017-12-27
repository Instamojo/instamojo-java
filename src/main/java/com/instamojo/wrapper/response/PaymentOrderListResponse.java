package com.instamojo.wrapper.response;

import com.google.gson.annotations.SerializedName;
import com.instamojo.wrapper.model.PaymentOrder;

import java.util.List;

/**
 * The Class PaymentOrderListResponse.
 */
public class PaymentOrderListResponse extends Response {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7456277340272483723L;

	/** The count. */
	private Integer count;

	/** The next. */
	private String next;

	/** The previous. */
	private String previous;
	
	/** The payment orders. */
	@SerializedName("orders")
	private List<PaymentOrder> paymentOrders;

	/**
	 * Gets the count.
	 *
	 * @return the count
	 */
	public Integer getCount() {
		return count;
	}

	/**
	 * Sets the count.
	 *
	 * @param count the new count
	 */
	public void setCount(Integer count) {
		this.count = count;
	}

	/**
	 * Gets the next.
	 *
	 * @return the next
	 */
	public String getNext() {
		return next;
	}

	/**
	 * Sets the next.
	 *
	 * @param next the new next
	 */
	public void setNext(String next) {
		this.next = next;
	}

	/**
	 * Gets the previous.
	 *
	 * @return the previous
	 */
	public String getPrevious() {
		return previous;
	}

	/**
	 * Sets the previous.
	 *
	 * @param previous the new previous
	 */
	public void setPrevious(String previous) {
		this.previous = previous;
	}

	/**
	 * Gets the payment orders.
	 *
	 * @return the payment orders
	 */
	public List<PaymentOrder> getPaymentOrders() {
		return paymentOrders;
	}

	/**
	 * Sets the payment orders.
	 *
	 * @param paymentOrders the new payment orders
	 */
	public void setPaymentOrders(List<PaymentOrder> paymentOrders) {
		this.paymentOrders = paymentOrders;
	}
	
	/* (non-Javadoc)
	 * @see com.instamojo.wrapper.response.Response#toString()
	 */
	@Override
	public String toString() {
		return "PaymentOrderListResponse{" + "count=" + count +
				", next='" + next + '\'' +
				", previous='" + previous + '\'' +
				", paymentOrders=" + paymentOrders +
				", success=" + success +
				", message='" + message + '\'' +
				", jsonResponse='" + jsonResponse + '\'' +
				'}';
	}
	
}
