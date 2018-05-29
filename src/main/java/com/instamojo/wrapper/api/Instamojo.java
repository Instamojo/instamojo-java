package com.instamojo.wrapper.api;

import com.instamojo.wrapper.exception.*;
import com.instamojo.wrapper.model.*;

import java.util.List;
import java.util.Map;

/**
 * The Interface Instamojo.
 */
public interface Instamojo {

    /**
     * Initiates a payment order with Instamojo.
     * There can only be one Instamojo payment order for a transaction in your system (transaction in your system being identified by transaction_id)
     *
     * @param paymentOrder the payment order
     * @return the creates the payment order response
     * @throws ConnectionException          the connection exception
     */
    PaymentOrderResponse createPaymentOrder(PaymentOrder paymentOrder) throws InstamojoBaseException, InstamojoClientException;

    /**
     * Get the details of the specified order (identified by id).
     *
     * @param id the id
     * @return the payment order details
     * @throws ConnectionException the connection exception
     */
	PaymentOrder getPaymentOrderDetails(String id) throws ConnectionException, InstamojoClientException;

    /**
     * Get the details of the specified order (identified by transaction id).
     *
     * @param transactionId the transaction id
     * @return the payment order details by transaction id
     * @throws ConnectionException the connection exception
     */
	PaymentOrder getPaymentOrderDetailsByTransactionId(String transactionId) throws ConnectionException, InstamojoClientException;

    /**
     * Gets the payment order list.
     * This endpoint returns paginated results of all your payment orders. This endpoint also supports filtering by some parameters.
     *
     * @param paymentOrderFilter the payment order filter
     * @return the payment order list
     * @throws ConnectionException the connection exception
     */
	List<PaymentOrder> getPaymentOrderList(PaymentOrderFilter paymentOrderFilter) throws ConnectionException, InstamojoClientException;

	/**
	 * Creates the new refund.
	 *
	 * @param refund the refund
	 * @return the creates the refund response
	 * @throws ConnectionException the connection exception
	 */
	Refund createRefund(Refund refund) throws InstamojoBaseException, InstamojoClientException;

	String createWebhookSignature(Map<String, String> data, String salt);

	List<Invoice> getInvoices() throws ConnectionException, InstamojoClientException;

	List<Payout> getPayouts() throws ConnectionException, InstamojoClientException;

	Payout getPayout(String id) throws ConnectionException, InstamojoClientException;
}
