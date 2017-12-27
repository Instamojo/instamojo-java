package com.instamojo.wrapper.model;

import com.google.gson.annotations.SerializedName;

/**
 * The Class PaymentOrderFilter.
 */
public class PaymentOrderFilter {

    /**
     * The id.
     */
    private String id;

    /**
     * The transaction id.
     */
    @SerializedName("transaction_id")
    private String transactionId;

    /**
     * The page.
     */
    private Integer page;

    /**
     * The limit.
     */
    private Integer limit;

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
     * Search for payment orders by id.
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
     * Sets the transaction id.
     * Search for payment orders by your transaction_id.
     *
     * @param transactionId the new transaction id
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * Gets the page.
     *
     * @return the page
     */
    public Integer getPage() {
        return page;
    }

    /**
     * Sets the page.
     * Page number of the results to retrieve from.
     *
     * @param page the new page
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     * Gets the limit.
     *
     * @return the limit
     */
    public Integer getLimit() {
        return limit;
    }

    /**
     * Sets the limit.
     * Limit the number of results returned per page.
     *
     * @param limit the new limit
     */
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

}
