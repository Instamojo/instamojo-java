package com.instamojo.wrapper.api;

import com.instamojo.wrapper.exception.*;
import com.instamojo.wrapper.model.PaymentOrder;
import com.instamojo.wrapper.model.PaymentOrderFilter;
import com.instamojo.wrapper.model.PaymentOrderResponse;
import com.instamojo.wrapper.model.Refund;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InstamojoExample {

    private static final Logger LOGGER = Logger.getLogger(InstamojoExample.class.getName());

    public static void main(String args[]) throws HTTPException {

        /*
         * Create a new payment order **************************************
         */

        PaymentOrder order = new PaymentOrder();

        order.setName("John Smith");
        order.setEmail("john.smith@gmail.com");
        order.setPhone("12345678790");
        order.setCurrency("INR");
        order.setAmount(9D);
        order.setDescription("This is a test transaction.");
        order.setRedirectUrl("http://www.someexample.com");
        order.setWebhookUrl("http://www.someurl.com/");
        order.setTransactionId("dxg234");

        Instamojo api = null;

        // gets the reference to the instamojo api
        ApiContext context = ApiContext.create("[CLIENT_ID]", "[CLIENT_SECRET]", ApiContext.Mode.TEST);
        api = new InstamojoImpl(context);

        try {
            PaymentOrderResponse paymentOrderResponse = api.createPaymentOrder(order);
            // print the status of the payment order.
            System.out.println(paymentOrderResponse.getPaymentOrder().getStatus());
        } catch (HTTPException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            System.out.println(e.getMessage());

        } catch (ConnectionException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        /*
         * Get details of payment order when order id is given
         */

        try {
            PaymentOrder paymentOrderDetailsResponse = api.getPaymentOrder("[PAYMENT_ORDER_ID]");

            if (paymentOrderDetailsResponse.getId() != null) {
                // print the status of the payment order.
                System.out.println(paymentOrderDetailsResponse.getStatus());
            } else {
                System.out.println("Please enter valid order id.");
            }
        } catch (ConnectionException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        /*
         * Get details of payment order when transaction id is given
         */

        try {
            PaymentOrder paymentOrderDetailsResponse = api.getPaymentOrderByTransactionId("[TRANSACTION_ID]");

            if (paymentOrderDetailsResponse.getId() != null) {
                // print the status of the payment order.
                System.out.println(paymentOrderDetailsResponse.getStatus());
            } else {
                System.out.println("Please enter valid transaction id.");
            }
        } catch (ConnectionException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        /*
         * Get list of all payment orders
         */

        try {
            PaymentOrderFilter paymentOrderFilter = new PaymentOrderFilter();

            List<PaymentOrder> paymentOrderListResponse = api.getPaymentOrders(paymentOrderFilter);

            // Loop over all of the payment orders and print status of each
            // payment order.
            for (PaymentOrder paymentOrder : paymentOrderListResponse) {
                System.out.println("Result = " + paymentOrder.getStatus());
            }
            System.out.println(paymentOrderListResponse);
        } catch (ConnectionException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }


		/*
		 Create a new refund **************************************
		 */

        Refund refund = new Refund();
        refund.setPaymentId("[PaymentId]");
        refund.setStatus("refunded");
        refund.setType("RFD");
        refund.setBody("This is a test refund.");
        refund.setRefundAmount(9D);
        refund.setTotalAmount(10D);


        try {
            Refund createRefundResponse = api.createRefund(refund);
            // print the status of the refund.
            System.out.println(createRefundResponse.getStatus());
        } catch (HTTPException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            System.out.println(e.getMessage());

        } catch (ConnectionException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }
}
