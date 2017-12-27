package com.instamojo.wrapper.model;

import com.google.gson.annotations.SerializedName;
import org.apache.http.util.TextUtils;

import java.io.Serializable;

/**
 * The Class Refund.
 */
public class Refund implements Serializable {

	private static final long serialVersionUID = -1417045152436410779L;
	
    private static final String[] VALID_REFUND_TYPES = new String[]{ "RFD", "TNR", "QFL", "QNR", "EWN", "TAN", "PTH" };

	/** The id. */
	private String id;

	/** The payment id. */
	@SerializedName("payment_id")
	private String paymentId;

	/** The status. */
	private String status;

	/** The type. */
	private String type;

	/** The body. */
	private String body;

	/** The refund amount. */
	@SerializedName("refund_amount")
	private Double refundAmount;

	/** The total amount. */
	@SerializedName("total_amount")
	private Double totalAmount;

	/** The created at. */
	@SerializedName("created_at")
	private String createdAt;

	/** The payment id invalid. */
	private boolean paymentIdInvalid;

	/** The type invalid. */
	private boolean typeInvalid;

	/** The body invalid. */
	private boolean bodyInvalid;

	/** The refund amount invalid. */
	private boolean refundAmountInvalid;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the payment id.
	 *
	 * @return the payment id
	 */
	public String getPaymentId() {
		return paymentId;
	}

	/**
	 * Sets the payment id.
	 *
	 * @param paymentId
	 *            the new payment id
	 */
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status
	 *            the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type
	 *            the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the body.
	 *
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * Sets the body.
	 *
	 * @param body
	 *            the new body
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * Gets the refund amount.
	 *
	 * @return the refund amount
	 */
	public Double getRefundAmount() {
		return refundAmount;
	}

	/**
	 * Sets the refund amount.
	 *
	 * @param refundAmount
	 *            the new refund amount
	 */
	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}

	/**
	 * Gets the total amount.
	 *
	 * @return the total amount
	 */
	public Double getTotalAmount() {
		return totalAmount;
	}

	/**
	 * Sets the total amount.
	 *
	 * @param totalAmount
	 *            the new total amount
	 */
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * Gets the created at.
	 *
	 * @return the created at
	 */
	public String getCreatedAt() {
		return createdAt;
	}

	/**
	 * Sets the created at.
	 *
	 * @param createdAt
	 *            the new created at
	 */
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Checks if is payment id invalid.
	 *
	 * @return true, if is payment id invalid
	 */
	public boolean isPaymentIdInvalid() {
		return paymentIdInvalid;
	}

	/**
	 * Sets the payment id invalid.
	 *
	 * @param paymentIdInvalid
	 *            the new payment id invalid
	 */
	public void setPaymentIdInvalid(boolean paymentIdInvalid) {
		this.paymentIdInvalid = paymentIdInvalid;
	}

	/**
	 * Checks if is type invalid.
	 *
	 * @return true, if is type invalid
	 */
	public boolean isTypeInvalid() {
		return typeInvalid;
	}

	/**
	 * Sets the type invalid.
	 *
	 * @param typeInvalid
	 *            the new type invalid
	 */
	public void setTypeInvalid(boolean typeInvalid) {
		this.typeInvalid = typeInvalid;
	}

	/**
	 * Checks if is body invalid.
	 *
	 * @return true, if is body invalid
	 */
	public boolean isBodyInvalid() {
		return bodyInvalid;
	}

	/**
	 * Sets the body invalid.
	 *
	 * @param bodyInvalid
	 *            the new body invalid
	 */
	public void setBodyInvalid(boolean bodyInvalid) {
		this.bodyInvalid = bodyInvalid;
	}

	/**
	 * Checks if is refund amount invalid.
	 *
	 * @return true, if is refund amount invalid
	 */
	public boolean isRefundAmountInvalid() {
		return refundAmountInvalid;
	}

	/**
	 * Sets the refund amount invalid.
	 *
	 * @param refundAmountInvalid
	 *            the new refund amount invalid
	 */
	public void setRefundAmountInvalid(boolean refundAmountInvalid) {
		this.refundAmountInvalid = refundAmountInvalid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Refund{" + "id='" + id + '\'' +
				", paymentId='" + paymentId + '\'' +
				", status='" + status + '\'' +
				", type='" + type + '\'' +
				", body='" + body + '\'' +
				", refundAmount=" + refundAmount +
				", totalAmount='" + totalAmount +
				", createdAt='" + createdAt + '\'' +
				'}';
	}
	
	/**
	 * Validate.
	 *
	 * @return true, if successful
	 */
	public boolean validate() {

		boolean valid = true;

		if (TextUtils.isEmpty(paymentId)) {
			valid = false;
			this.setPaymentIdInvalid(true);
		}

        if (TextUtils.isEmpty(type)||!isValidRefundType()) {
            valid = false;
            this.setTypeInvalid(true);
        }

		if (TextUtils.isEmpty(body)) {
			valid = false;
			this.setBodyInvalid(true);
		}

		if (refundAmount == null) {
			valid = false;
			this.setRefundAmountInvalid(true);
		}

		return valid;
	}

    private boolean isValidRefundType(){
        for(String refundType: VALID_REFUND_TYPES){
            if(refundType.equals(type))
                return true;
        }
        return false;
    }
}
