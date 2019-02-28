package com.instamojo.wrapper.api;

import com.instamojo.wrapper.exception.ConnectionException;
import com.instamojo.wrapper.exception.HTTPException;
import com.instamojo.wrapper.filter.PaymentRequestFilter;
import com.instamojo.wrapper.filter.PayoutFilter;
import com.instamojo.wrapper.model.*;
import com.instamojo.wrapper.response.ApiListResponse;

import java.util.Map;

/**
 * The Interface Instamojo.
 */
public interface Instamojo {

    /**
     * Initiates a payment order with Instamojo.
     * There can only be one Instamojo payment order for a transaction in your system
     * (transaction in your system being identified by transaction_id)
     *
     * @param paymentOrder the payment order
     * @return the creates the payment order response
     * @throws ConnectionException on a failure communicating with Instamojo
     * @throws HTTPException       on a failure response from Instamojo
     */
    PaymentOrderResponse createPaymentOrder(PaymentOrder paymentOrder) throws ConnectionException, HTTPException;

    /**
     * Get the details of the specified order (identified by id).
     *
     * @param id the id
     * @return the payment order details
     * @throws ConnectionException on a failure communicating with Instamojo
     * @throws HTTPException       on a failure response from Instamojo
     */
    PaymentOrder getPaymentOrder(String id) throws ConnectionException, HTTPException;

    /**
     * Get the details of the specified order (identified by transaction id).
     *
     * @param transactionId the transaction id
     * @return the payment order details by transaction id
     * @throws ConnectionException on a failure communicating with Instamojo
     * @throws HTTPException       on a failure response from Instamojo
     */
    PaymentOrder getPaymentOrderByTransactionId(String transactionId) throws ConnectionException, HTTPException;

    /**
     * Gets the payment order list.
     * This endpoint returns paginated results of all your payment orders.
     * This endpoint also supports filtering by some parameters.
     *
     * @return the payment order list
     * @throws ConnectionException on a failure communicating with Instamojo
     * @throws HTTPException       on a failure response from Instamojo
     */
    ApiListResponse<PaymentOrder> getPaymentOrders(int page, int limit) throws ConnectionException, HTTPException;

    /**
     * Creates the new refund.
     *
     * @param refund the refund
     * @return the creates the refund response
     * @throws ConnectionException on a failure communicating with Instamojo
     * @throws HTTPException       on a failure response from Instamojo
     */
    Refund createRefund(Refund refund) throws ConnectionException, HTTPException;

    /**
     * Generates a signature based on Instamojo response and private salt
     * https://support.instamojo.com/hc/en-us/articles/207816249-What-is-the-Message-Authentication-Code-in-Webhook-
     *
     * @param data map of field value pairs
     * @param salt the private salt from Instamojo developer account
     * @return the hexadecimal signature
     */
    String generateWebhookSignature(Map<String, String> data, String salt);

    /**
     * Get all tax invoices of an Instamojo account
     *
     * @return the list of all tax invoices
     * @throws ConnectionException on a failure communicating with Instamojo
     * @throws HTTPException       on a failure response from Instamojo
     */
    ApiListResponse<Invoice> getInvoices(int page, int limit) throws ConnectionException, HTTPException;

    /**
     * Get all payouts of an Instamojo account
     *
     * @return the list of all payouts
     * @throws ConnectionException on a failure communicating with Instamojo
     * @throws HTTPException       on a failure response from Instamojo
     */
    ApiListResponse<Payout> getPayouts(int page, int limit) throws ConnectionException, HTTPException;

    /**
     * Get all payouts of an Instamojo account
     *
     * @param filter the payout filter
     * @return the list of all payouts
     * @throws ConnectionException on a failure communicating with Instamojo
     * @throws HTTPException       on a failure response from Instamojo
     */
    ApiListResponse<Payout> getPayouts(Map<PayoutFilter, String> filter, int page, int limit) throws ConnectionException, HTTPException;

    /**
     * Get payout by id
     *
     * @return the payout for the given id
     * @throws ConnectionException on a failure communicating with Instamojo
     * @throws HTTPException       on a failure response from Instamojo
     */
    Payout getPayout(String id) throws ConnectionException, HTTPException;

    /**
     * Creates a PaymentRequest
     *
     * @return the created PaymentRequest
     * @throws ConnectionException on a failure communicating with Instamojo
     * @throws HTTPException       on a failure response from Instamojo
     */
    PaymentRequest createPaymentRequest(PaymentRequest paymentRequest) throws ConnectionException, HTTPException;

    /**
     * Get all PaymentRequests
     *
     * @return the list of all PaymentRequests
     * @throws ConnectionException on a failure communicating with Instamojo
     * @throws HTTPException       on a failure response from Instamojo
     */
    ApiListResponse<PaymentRequest> getPaymentRequests(int page, int limit) throws ConnectionException, HTTPException;

    /**
     * Get all PaymentRequests
     *
     * @param filter the payment request filter
     * @return the list of all PaymentRequests
     * @throws ConnectionException on a failure communicating with Instamojo
     * @throws HTTPException       on a failure response from Instamojo
     */
    ApiListResponse<PaymentRequest> getPaymentRequests(Map<PaymentRequestFilter, String> filter, int page, int limit) throws ConnectionException, HTTPException;

    /**
     * Get PaymentRequest by id
     *
     * @return the PaymentRequest for the given id
     * @throws ConnectionException on a failure communicating with Instamojo
     * @throws HTTPException       on a failure response from Instamojo
     */
    PaymentRequest getPaymentRequest(String id) throws ConnectionException, HTTPException;

    /**
     * Enable the PaymentRequest
     *
     * @return True if the operation is successful else False
     * @throws ConnectionException on a failure communicating with Instamojo
     * @throws HTTPException       on a failure response from Instamojo
     */
    Boolean enablePaymentRequest(String id) throws ConnectionException, HTTPException;

    /**
     * Disable the PaymentRequest
     *
     * @return True if the operation is successful else False
     * @throws ConnectionException on a failure communicating with Instamojo
     * @throws HTTPException       on a failure response from Instamojo
     */
    Boolean disablePaymentRequest(String id) throws ConnectionException, HTTPException;
}
