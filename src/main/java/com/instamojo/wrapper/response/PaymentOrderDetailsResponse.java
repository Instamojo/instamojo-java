package com.instamojo.wrapper.response;

import com.google.gson.annotations.SerializedName;
import com.instamojo.wrapper.model.Payment;

import java.util.Arrays;

public class PaymentOrderDetailsResponse extends Response {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 4912793214890694717L;

    /** The id. */
    private String id;

    /** The transaction id. */
    @SerializedName("transaction_id")
    private String transactionId;

	/** The status. */
	private String status;

    /** The currency. */
    private String currency;

    /** The amount. */
    private Double amount;

    /** The name. */
    private String name;

    /** The email. */
    private String email;

    /** The phone. */
    private String phone;

    /** The description. */
    private String description;

    /** The redirect url. */
    @SerializedName("redirect_url")
    private String redirectUrl;

    /** Webhook URL */
    @SerializedName("webhook_url")
    private String webhookUrl;

    /** The created at. */
    @SerializedName("created_at")
    private String createdAt;
    
    /** The resource uri. */
    @SerializedName("resource_uri")
    private String resourceUri ;
    
    /** The payments. */
    private Payment[] payments;

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
     * @param id the new id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the transaction id.
     *
     * @return the transaction id
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Sets Unique identifier for the order (max 64 characters).
     * Identifier can contain alphanumeric characters, hyphens and underscores only.
     * This is generally the unique order id (or primary key) in your system..
     *
     * @param transactionId the new transaction id
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
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
     * @param status the new status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the currency.
     *
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the String identifier for the currency.
     *
     * @param currency the new currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Gets the amount.
     *
     * @return the amount
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * Sets the Amount the customer has to pay.
     *
     * @param amount the new amount
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the Name of the customer .
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the Email address of the customer.
     *
     * @param email the new email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the phone.
     *
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the Phone number of the customer.
     *
     * @param phone the new phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the Short description of the order.
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the redirect url.
     *
     * @return the redirect url
     */
    public String getRedirectUrl() {
        return redirectUrl;
    }

    /**
     * Sets the redirect url.
     * Full URL to which the customer is redirected after payment.
     * Redirection happens even if payment wasn't successful. This URL shouldn't contain any query parameters
     *
     * @param redirectUrl the new redirect url
     */
    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    /**
     * Gets Webhook url for this order
     * @return webhook url
     */
    public String getWebhookUrl() {
        return webhookUrl;
    }

    /**
     * Sets webhook url for this order
     * @param webhookUrl webhook url for this order
     */
    public void setWebhookUrl(String webhookUrl) {
        this.webhookUrl = webhookUrl;
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
	 * @param createdAt the new created at
	 */
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Gets the resource uri.
	 *
	 * @return the resource uri
	 */
	public String getResourceUri() {
		return resourceUri;
	}

	/**
	 * Sets the resource uri.
	 *
	 * @param resourceUri the new resource uri
	 */
	public void setResourceUri(String resourceUri) {
		this.resourceUri = resourceUri;
	}

    /**
     * Gets the json response.
     *
     * @return the json response
     */
    public String getJsonResponse() {
        return jsonResponse;
    }


	/**
	 * Gets the payments.
	 *
	 * @return the payments
	 */
	public Payment[] getPayments() {
		return payments;
	}

	/**
	 * Sets the payments.
	 *
	 * @param payments the new payments
	 */
	public void setPayments(Payment[] payments) {
		this.payments = payments;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
        return "PaymentOrder{" + "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", currency='" + currency + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", webhookUrl='" + webhookUrl + '\'' +
                ", redirectUrl='" + redirectUrl + '\'' +
                ", status='" + status + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", resourceUri='" + resourceUri + '\'' +
                ", payments='" + Arrays.toString(payments) + '\'' +
                ", success=" + success +
                ", message='" + message + '\'' +
                ", jsonResponse='" + jsonResponse + '\'' +
                '}';
	}
}
