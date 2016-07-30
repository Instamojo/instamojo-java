package com.instamojo.wrapper.builder;

import com.instamojo.wrapper.model.Refund;

public class RefundBuilder {

	private static final String REFUND_PAYMENT_ID = "MOJO6701005J41260266";
	private static final String REFUND_STATUS = "pending";
	private static final String REFUND_TYPE = "RFD";
	private static final String REFUND_BODY = "This is a test message.";
	private static final Double REFUND_REFUND_AMOUNT = 9D;
	private static final Double REFUND_TOTAL_AMOUNT = 10D;

	private Refund refund;

	public RefundBuilder() {
		refund = new Refund();
		refund.setPaymentId(REFUND_PAYMENT_ID);
		refund.setStatus(REFUND_STATUS);
		refund.setType(REFUND_TYPE);
		refund.setBody(REFUND_BODY);
		refund.setRefundAmount(REFUND_REFUND_AMOUNT);
		refund.setTotalAmount(REFUND_TOTAL_AMOUNT);
	}

	public RefundBuilder withPaymentId(String paymentId) {
		refund.setPaymentId(paymentId);
		return this;
	}

	public RefundBuilder withStatus(String status) {
		refund.setStatus(status);
		return this;
	}

	public RefundBuilder withType(String type) {
		refund.setType(type);
		return this;
	}

	public RefundBuilder withBody(String body) {
		refund.setBody(body);
		return this;
	}

	public RefundBuilder withRefundAmount(Double refundAmount) {
		refund.setRefundAmount(refundAmount);
		return this;
	}

	public RefundBuilder withTotalAmount(Double totalAmount) {
		refund.setTotalAmount(totalAmount);
		return this;
	}

	public Refund build() {
		return refund;
	}

}
