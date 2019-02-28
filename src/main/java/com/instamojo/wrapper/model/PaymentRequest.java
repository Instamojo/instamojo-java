package com.instamojo.wrapper.model;

import com.google.gson.annotations.SerializedName;
import org.joda.time.DateTime;

import java.util.List;

public class PaymentRequest {

    private String id;

    private String phone;

    private String email;

    @SerializedName("buyer_name")
    private String buyerName;

    private Double amount;

    private String purpose;

    private String status;

    @SerializedName("payments")
    private List<String> paymentUrls;

    @SerializedName("send_sms")
    private boolean sendSms;

    @SerializedName("send_email")
    private boolean sendEmail;

    @SerializedName("sms_status")
    private String smsStatus;

    @SerializedName("email_status")
    private String emailStatus;

    @SerializedName("shorturl")
    private String shortUrl;

    @SerializedName("longurl")
    private String longUrl;

    @SerializedName("redirect_url")
    private String redirectUrl;

    @SerializedName("webhook")
    private String webhookUrl;

    @SerializedName("scheduled_at")
    private DateTime scheduledAt;

    @SerializedName("expires_at")
    private DateTime expiresAt;

    @SerializedName("allow_repeated_payments")
    private Boolean allowRepeatedPayments;

    @SerializedName("partner")
    private String partnerUri;

    @SerializedName("partner_fee_type")
    private String partnerFeeType;

    @SerializedName("partner_fee")
    private Double partnerFee;

    @SerializedName("mark_fulfilled")
    private Boolean markFulfilled;

    @SerializedName("created_at")
    private DateTime createdAt;

    @SerializedName("modified_at")
    private DateTime modifiedAt;

    @SerializedName("resource_uri")
    private String resourceUri;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getPaymentUrls() {
        return paymentUrls;
    }

    public void setPaymentUrls(List<String> paymentUrls) {
        this.paymentUrls = paymentUrls;
    }

    public boolean isSendSms() {
        return sendSms;
    }

    public void setSendSms(boolean sendSms) {
        this.sendSms = sendSms;
    }

    public boolean isSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(boolean sendEmail) {
        this.sendEmail = sendEmail;
    }

    public String getSmsStatus() {
        return smsStatus;
    }

    public void setSmsStatus(String smsStatus) {
        this.smsStatus = smsStatus;
    }

    public String getEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(String emailStatus) {
        this.emailStatus = emailStatus;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getWebhookUrl() {
        return webhookUrl;
    }

    public void setWebhookUrl(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }

    public DateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(DateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public DateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(DateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Boolean getAllowRepeatedPayments() {
        return allowRepeatedPayments;
    }

    public void setAllowRepeatedPayments(Boolean allowRepeatedPayments) {
        this.allowRepeatedPayments = allowRepeatedPayments;
    }

    public String getPartnerUri() {
        return partnerUri;
    }

    public void setPartnerUri(String partnerUri) {
        this.partnerUri = partnerUri;
    }

    public String getPartnerFeeType() {
        return partnerFeeType;
    }

    public void setPartnerFeeType(String partnerFeeType) {
        this.partnerFeeType = partnerFeeType;
    }

    public Double getPartnerFee() {
        return partnerFee;
    }

    public void setPartnerFee(Double partnerFee) {
        this.partnerFee = partnerFee;
    }

    public Boolean getMarkFulfilled() {
        return markFulfilled;
    }

    public void setMarkFulfilled(Boolean markFulfilled) {
        this.markFulfilled = markFulfilled;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }

    public DateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(DateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getResourceUri() {
        return resourceUri;
    }

    public void setResourceUri(String resourceUri) {
        this.resourceUri = resourceUri;
    }

    @Override
    public String toString() {
        return "PaymentRequest{" +
                "id='" + id + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", buyerName='" + buyerName + '\'' +
                ", amount=" + amount +
                ", purpose='" + purpose + '\'' +
                ", status='" + status + '\'' +
                ", paymentUrls=" + paymentUrls +
                ", sendSms=" + sendSms +
                ", sendEmail=" + sendEmail +
                ", smsStatus='" + smsStatus + '\'' +
                ", emailStatus='" + emailStatus + '\'' +
                ", shortUrl='" + shortUrl + '\'' +
                ", longUrl='" + longUrl + '\'' +
                ", redirectUrl='" + redirectUrl + '\'' +
                ", webhookUrl='" + webhookUrl + '\'' +
                ", scheduledAt=" + scheduledAt +
                ", expiresAt=" + expiresAt +
                ", allowRepeatedPayments=" + allowRepeatedPayments +
                ", partnerUri='" + partnerUri + '\'' +
                ", partnerFeeType='" + partnerFeeType + '\'' +
                ", partnerFee=" + partnerFee +
                ", markFulfilled=" + markFulfilled +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                ", resourceUri='" + resourceUri + '\'' +
                '}';
    }
}
