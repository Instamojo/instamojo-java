package com.instamojo.wrapper.model;

import com.google.gson.annotations.SerializedName;
import org.apache.http.util.TextUtils;

import java.io.Serializable;

public class PaymentOrder implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4912793214890694717L;

	private static final String TRANSACTION_ID_MATCHER = "[A-Za-z0-9_-]+";
	private static final String EMAIL_MATCHER = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
	private static final String URL_MATCHER = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
	private static final double MIN_AMOUNT = 9.00;
	private static final int MAX_TID_CHAR_LIMIT = 64;
	private static final int MAX_EMAIL_CHAR_LIMIT = 75;
	private static final int MAX_NAME_CHAR_LIMIT = 100;
	private static final int MAX_DESCRIPTION_CHAR_LIMIT = 255;

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

	/** Webhook URL */
	@SerializedName("webhook_url")
	private String webhookUrl;

	/** The redirect url. */
	@SerializedName("redirect_url")
	private String redirectUrl;

	/** The created at. */
	@SerializedName("created_at")
	private String createdAt;

	/** The resource uri. */
	@SerializedName("resource_uri")
	private String resourceUri;

	/** The payments. */
	private Object payments;

    private boolean nameInvalid;
    private boolean emailInvalid;
    private boolean phoneInvalid;
    private boolean transactionIdInvalid;
    private boolean descriptionInvalid;
    private boolean currencyInvalid;
    private boolean amountInvalid;
    private boolean redirectUrlInvalid;
    private boolean webhookInvalid;

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
     * Sets Unique identifier for the order (max 64 characters). Identifier can
     * contain alphanumeric characters, hyphens and underscores only. This is
     * generally the unique order id (or primary key) in your system..
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
     * Gets Webhook URL for this order
     * @return webhook url
     */
    public String getWebhookUrl() {
        return webhookUrl;
    }

    /**
     * Sets webhook url
     * @param webhookUrl webhook url for this order
     */
    public void setWebhookUrl(String webhookUrl) {
        this.webhookUrl = webhookUrl;
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
     * Sets the redirect url. Full URL to which the customer is redirected after
     * payment. Redirection happens even if payment wasn't successful. This URL
     * shouldn't contain any query parameters
     *
     * @param redirectUrl the new redirect url
     */
    public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
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
     * Gets the payments.
     *
     * @return the payments
     */
    public Object getPayments() {
		return payments;
	}

    /**
     * Sets the payments.
     *
     * @param payments the new payments
     */
    public void setPayments(Object payments) {
		this.payments = payments;
	}

    /**
     * Is name invalid boolean.
     *
     * @return the boolean
     */
    public boolean isNameInvalid() {
		return nameInvalid;
	}

    /**
     * Sets name invalid.
     *
     * @param nameInvalid the name invalid
     */
    public void setNameInvalid(boolean nameInvalid) {
		this.nameInvalid = nameInvalid;
	}

    /**
     * Is email invalid boolean.
     *
     * @return the boolean
     */
    public boolean isEmailInvalid() {
		return emailInvalid;
	}

    /**
     * Sets email invalid.
     *
     * @param emailInvalid the email invalid
     */
    public void setEmailInvalid(boolean emailInvalid) {
		this.emailInvalid = emailInvalid;
	}

    /**
     * Is phone invalid boolean.
     *
     * @return the boolean
     */
    public boolean isPhoneInvalid() {
		return phoneInvalid;
	}

    /**
     * Sets phone invalid.
     *
     * @param phoneInvalid the phone invalid
     */
    public void setPhoneInvalid(boolean phoneInvalid) {
		this.phoneInvalid = phoneInvalid;
	}

    /**
     * Is transaction id invalid boolean.
     *
     * @return the boolean
     */
    public boolean isTransactionIdInvalid() {
		return transactionIdInvalid;
	}

    /**
     * Sets transaction id invalid.
     *
     * @param transactionIdInvalid the transaction id invalid
     */
    public void setTransactionIdInvalid(boolean transactionIdInvalid) {
		this.transactionIdInvalid = transactionIdInvalid;
	}

    /**
     * Is description invalid boolean.
     *
     * @return the boolean
     */
    public boolean isDescriptionInvalid() {
		return descriptionInvalid;
	}

    /**
     * Sets description invalid.
     *
     * @param descriptionInvalid the description invalid
     */
    public void setDescriptionInvalid(boolean descriptionInvalid) {
		this.descriptionInvalid = descriptionInvalid;
	}

    /**
     * Is currency invalid boolean.
     *
     * @return the boolean
     */
    public boolean isCurrencyInvalid() {
		return currencyInvalid;
	}

    /**
     * Sets currency invalid.
     *
     * @param currencyInvalid the currency invalid
     */
    public void setCurrencyInvalid(boolean currencyInvalid) {
		this.currencyInvalid = currencyInvalid;
	}

    /**
     * Is amount invalid boolean.
     *
     * @return the boolean
     */
    public boolean isAmountInvalid() {
		return amountInvalid;
	}

    /**
     * Sets amount invalid.
     *
     * @param amountInvalid the amount invalid
     */
    public void setAmountInvalid(boolean amountInvalid) {
		this.amountInvalid = amountInvalid;
	}

    /**
     * Check if the given webhook is invalid
     * @return webhookInvalid
     */
	public boolean isWebhookInvalid(){
        return this.webhookInvalid;
    }

    /**
     * Sets webhookInvalid
     * @param webhookInvalid webhookInvalid
     */
    public void setWebhookInvalid(boolean webhookInvalid){
        this.webhookInvalid = webhookInvalid;
    }

    /**
     * Is redirect url invalid boolean.
     *
     * @return the boolean
     */
    public boolean isRedirectUrlInvalid() {
		return redirectUrlInvalid;
	}

    /**
     * Sets redirect url invalid.
     *
     * @param redirectUrlInvalid the redirect url invalid
     */
    public void setRedirectUrlInvalid(boolean redirectUrlInvalid) {
		this.redirectUrlInvalid = redirectUrlInvalid;
	}

    /*
     * (non-Javadoc)
     *
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
                ", webhook_url='" + webhookUrl +'\'' +
                ", redirectUrl='" + redirectUrl + '\'' +
                ", status='" + status + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", resourceUri='" + resourceUri + '\'' +
                ", payments='" + payments + '\'' +
                '}';
    }

    /**
     * Validate.
     *
     * @return the boolean
     */
    public boolean validate() {

		boolean valid = true;

		if (TextUtils.isEmpty(transactionId) || !transactionId.matches(TRANSACTION_ID_MATCHER) || transactionId.length() > MAX_TID_CHAR_LIMIT) {
			valid = false;
			this.setTransactionIdInvalid(true);
		}

		if (TextUtils.isEmpty(email) || !email.matches(EMAIL_MATCHER) || email.length() > MAX_EMAIL_CHAR_LIMIT) {
            valid = false;
            this.setEmailInvalid(true);
        }

		if (TextUtils.isEmpty(name) || name.length() > MAX_NAME_CHAR_LIMIT) {
            valid = false;
            this.setNameInvalid(true);
		}

		if (TextUtils.isEmpty(currency)) {
            valid = false;
            this.setCurrencyInvalid(true);
		}

		if (TextUtils.isEmpty(phone)) {
            valid = false;
            this.setPhoneInvalid(true);
		}

		if (amount == null || amount < MIN_AMOUNT) {
            valid = false;
            this.setAmountInvalid(true);
		}

        if (!TextUtils.isEmpty(description) && description.length() > MAX_DESCRIPTION_CHAR_LIMIT) {
            valid = false;
            this.setDescriptionInvalid(true);
        }

		if (TextUtils.isEmpty(redirectUrl) || !redirectUrl.matches(URL_MATCHER)) {
            valid = false;
            this.setRedirectUrlInvalid(true);
		}

		if (!TextUtils.isEmpty(webhookUrl) && !webhookUrl.matches(URL_MATCHER)){
            valid = false;
            this.setWebhookInvalid(true);
        }

	    return valid;
    }
}
