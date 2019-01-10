package com.instamojo.wrapper.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Payout {

    private String id;

    private String recipient;

    private Boolean status;

    @SerializedName("paid_out_at")
    private Date paidOutAt;

    private String currency;

    @SerializedName("paid_amount")
    private Double paidAmount;

    @SerializedName("sales_amount")
    private Double salesAmount;

    @SerializedName("fee_amount")
    private Double feeAmount;

    @SerializedName("total_tax_amount")
    private Double totalTaxAmount;

    @SerializedName("held_amount")
    private Double heldAmount;

    @SerializedName("reversed_amount")
    private Double reversedAmount;

    @SerializedName("refunded_amount")
    private Double refundedAmount;

    @SerializedName("affiliate_commission_amount")
    private Double affiliateCommissionAmount;

    @SerializedName("partner_commission_amount")
    private Double partnerCommissionAmount;

    @SerializedName("resource_uri")
    private String resourceUri;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getPaidOutAt() {
        return paidOutAt;
    }

    public void setPaidOutAt(Date paidOutAt) {
        this.paidOutAt = paidOutAt;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Double getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(Double salesAmount) {
        this.salesAmount = salesAmount;
    }

    public Double getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(Double feeAmount) {
        this.feeAmount = feeAmount;
    }

    public Double getTotalTaxAmount() {
        return totalTaxAmount;
    }

    public void setTotalTaxAmount(Double totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
    }

    public Double getHeldAmount() {
        return heldAmount;
    }

    public void setHeldAmount(Double heldAmount) {
        this.heldAmount = heldAmount;
    }

    public Double getReversedAmount() {
        return reversedAmount;
    }

    public void setReversedAmount(Double reversedAmount) {
        this.reversedAmount = reversedAmount;
    }

    public Double getRefundedAmount() {
        return refundedAmount;
    }

    public void setRefundedAmount(Double refundedAmount) {
        this.refundedAmount = refundedAmount;
    }

    public Double getAffiliateCommissionAmount() {
        return affiliateCommissionAmount;
    }

    public void setAffiliateCommissionAmount(Double affiliateCommissionAmount) {
        this.affiliateCommissionAmount = affiliateCommissionAmount;
    }

    public Double getPartnerCommissionAmount() {
        return partnerCommissionAmount;
    }

    public void setPartnerCommissionAmount(Double partnerCommissionAmount) {
        this.partnerCommissionAmount = partnerCommissionAmount;
    }

    public String getResourceUri() {
        return resourceUri;
    }

    public void setResourceUri(String resourceUri) {
        this.resourceUri = resourceUri;
    }

    @Override
    public String toString() {
        return "Payout{" +
                "id='" + id + '\'' +
                ", recipient='" + recipient + '\'' +
                ", status='" + status + '\'' +
                ", paidOutAt=" + paidOutAt +
                ", currency='" + currency + '\'' +
                ", paidAmount=" + paidAmount +
                ", salesAmount=" + salesAmount +
                ", feeAmount=" + feeAmount +
                ", totalTaxAmount=" + totalTaxAmount +
                ", heldAmount=" + heldAmount +
                ", reversedAmount=" + reversedAmount +
                ", refundedAmount=" + refundedAmount +
                ", affiliateCommissionAmount=" + affiliateCommissionAmount +
                ", partnerCommissionAmount=" + partnerCommissionAmount +
                ", resourceUri='" + resourceUri + '\'' +
                '}';
    }
}
