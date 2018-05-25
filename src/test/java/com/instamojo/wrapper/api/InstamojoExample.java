package com.instamojo.wrapper.api;

import com.instamojo.wrapper.exception.ConnectionException;
import com.instamojo.wrapper.exception.InvalidPaymentOrderException;
import com.instamojo.wrapper.exception.InvalidRefundException;
import com.instamojo.wrapper.model.PaymentOrder;
import com.instamojo.wrapper.model.PaymentOrderFilter;
import com.instamojo.wrapper.model.Refund;
import com.instamojo.wrapper.response.CreatePaymentOrderResponse;
import com.instamojo.wrapper.response.CreateRefundResponse;
import com.instamojo.wrapper.response.PaymentOrderDetailsResponse;
import com.instamojo.wrapper.response.PaymentOrderListResponse;

import java.util.logging.Level;
import java.util.logging.Logger;

public class InstamojoExample {

	private static final Logger LOGGER = Logger.getLogger(InstamojoExample.class.getName());

	public static void main(String args[]) {

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
		api = InstamojoImpl.getApi("[CLIENT_ID]", "[CLIENT_SECRET]", InstamojoImpl.Mode.TEST);

		boolean isOrderValid = order.validate();

		if (isOrderValid) {
			try {
				CreatePaymentOrderResponse createPaymentOrderResponse = api.createNewPaymentOrder(order);
				// print the status of the payment order.
				System.out.println(createPaymentOrderResponse.getPaymentOrder().getStatus());
			} catch (InvalidPaymentOrderException e) {
				LOGGER.log(Level.SEVERE, e.toString(), e);

				if (order.isTransactionIdInvalid()) {
					System.out.println("Transaction id is invalid. This is mostly due to duplicate transaction id.");
				}
				if (order.isCurrencyInvalid()) {
					System.out.println("Currency is invalid.");
				}
			} catch (ConnectionException e) {
				LOGGER.log(Level.SEVERE, e.toString(), e);
			}
		} else {
			// inform validation errors to the user.
			if (order.isTransactionIdInvalid()) {
				System.out.println("Transaction id is invalid.");
			}
			if (order.isAmountInvalid()) {
				System.out.println("Amount can not be less than 9.00.");
			}
			if (order.isCurrencyInvalid()) {
				System.out.println("Please provide the currency.");
			}
			if (order.isDescriptionInvalid()) {
				System.out.println("Description can not be greater than 255 characters.");
			}
			if (order.isEmailInvalid()) {
				System.out.println("Please provide valid Email Address.");
			}
			if (order.isNameInvalid()) {
				System.out.println("Name can not be greater than 100 characters.");
			}
			if (order.isPhoneInvalid()) {
				System.out.println("Phone is invalid.");
			}
			if (order.isRedirectUrlInvalid()) {
				System.out.println("Please provide valid Redirect url.");
			}

			if (order.isWebhookInvalid()) {
                System.out.println("Provide a valid webhook url");
            }
		}

		/*
		 * Get details of payment order when order id is given
		 */

		try {
			PaymentOrderDetailsResponse paymentOrderDetailsResponse = api.getPaymentOrderDetails("[PAYMENT_ORDER_ID]");

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
			PaymentOrderDetailsResponse paymentOrderDetailsResponse = api.getPaymentOrderDetailsByTransactionId("[TRANSACTION_ID]");

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

			PaymentOrderListResponse paymentOrderListResponse = api.getPaymentOrderList(paymentOrderFilter);

			// Loop over all of the payment orders and print status of each
			// payment order.
			for (PaymentOrder paymentOrder : paymentOrderListResponse.getPaymentOrders()) {
				System.out.println("Result = " + paymentOrder.getStatus());
			}
			System.out.println(paymentOrderListResponse.getPaymentOrders());
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

		boolean isRefundValid = refund.validate();

		if (isRefundValid) {
			try {
				CreateRefundResponse createRefundResponse = api.createNewRefund(refund);
				// print the status of the refund.
				System.out.println(createRefundResponse.getRefund().getStatus());
			} catch (InvalidRefundException e) {
				LOGGER.log(Level.SEVERE, e.toString(), e);

				if (refund.isTypeInvalid()) {
					System.out.println("type is invalid.");
				}
			} catch (ConnectionException e) {
				LOGGER.log(Level.SEVERE, e.toString(), e);
			}
		} else {
			// inform validation errors to the user.
			if (refund.isPaymentIdInvalid()) {
				System.out.println("Payment id is invalid.");
			}
			if (refund.isTypeInvalid()) {
				System.out.println("Type is invalid.");
			}
			if (refund.isBodyInvalid()) {
				System.out.println("Body is invalid.");
			}
			if (refund.isRefundAmountInvalid()) {
				System.out.println("Refund amount is invalid.");
			}
		}
	}
}
