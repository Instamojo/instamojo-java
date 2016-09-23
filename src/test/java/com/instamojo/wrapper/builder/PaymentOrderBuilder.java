package com.instamojo.wrapper.builder;

import com.instamojo.wrapper.model.PaymentOrder;

import java.util.UUID;

/**
 * The type Payment order builder.
 */
public class PaymentOrderBuilder {

    private static final String PAYMENT_ORDER_NAME = "John Smith";
    private static final String PAYMENT_ORDER_EMAIL = "john.smith@gmail.com";
    private static final String PAYMENT_ORDER_PHONE = "1234567890";
    private static final String PAYMENT_ORDER_CURRENCY = "INR";
    private static final Double PAYMENT_ORDER_AMOUNT = 9D;
    private static final String PAYMENT_ORDER_DESCRIPTION = "This is a test payment order.";
    private static final String PAYMENT_ORDER_REDIRECT_URL = "http://www.someexample.com";
    private static final String PAYMENT_ORDER_WEBHOOK_URL = "http://www.somewebhookurl.com/";

    private PaymentOrder paymentOrder;

    /**
     * Instantiates a new Payment order builder.
     */
    public PaymentOrderBuilder() {
        paymentOrder = new PaymentOrder();

        paymentOrder.setTransactionId(UUID.randomUUID().toString());
        paymentOrder.setName(PAYMENT_ORDER_NAME);
        paymentOrder.setEmail(PAYMENT_ORDER_EMAIL);
        paymentOrder.setPhone(PAYMENT_ORDER_PHONE);
        paymentOrder.setCurrency(PAYMENT_ORDER_CURRENCY);
        paymentOrder.setAmount(PAYMENT_ORDER_AMOUNT);
        paymentOrder.setDescription(PAYMENT_ORDER_DESCRIPTION);
        paymentOrder.setRedirectUrl(PAYMENT_ORDER_REDIRECT_URL);
        paymentOrder.setWebhookUrl(PAYMENT_ORDER_WEBHOOK_URL);
    }

    /**
     * With name payment order builder.
     *
     * @param name the name
     * @return the payment order builder
     */
    public PaymentOrderBuilder withName(String name) {
        paymentOrder.setName(name);
        return this;
    }

    /**
     * With email payment order builder.
     *
     * @param email the email
     * @return the payment order builder
     */
    public PaymentOrderBuilder withEmail(String email) {
        paymentOrder.setEmail(email);
        return this;
    }

    /**
     * With phone payment order builder.
     *
     * @param phone the phone
     * @return the payment order builder
     */
    public PaymentOrderBuilder withPhone(String phone) {
        paymentOrder.setPhone(phone);
        return this;
    }

    /**
     * With currency payment order builder.
     *
     * @param currency the currency
     * @return the payment order builder
     */
    public PaymentOrderBuilder withCurrency(String currency) {
        paymentOrder.setCurrency(currency);
        return this;
    }

    /**
     * With amount payment order builder.
     *
     * @param amount the amount
     * @return the payment order builder
     */
    public PaymentOrderBuilder withAmount(Double amount) {
        paymentOrder.setAmount(amount);
        return this;
    }

    /**
     * With description payment order builder.
     *
     * @param description the description
     * @return the payment order builder
     */
    public PaymentOrderBuilder withDescription(String description) {
        paymentOrder.setDescription(description);
        return this;
    }

    /**
     * With transaction id payment order builder.
     *
     * @param transactionId the transaction id
     * @return the payment order builder
     */
    public PaymentOrderBuilder withTransactionId(String transactionId) {
        paymentOrder.setTransactionId(transactionId);
        return this;
    }

    /**
     * With redirect url payment order builder.
     *
     * @param redirectUrl the redirect url
     * @return the payment order builder
     */
    public PaymentOrderBuilder withRedirectUrl(String redirectUrl) {
        paymentOrder.setRedirectUrl(redirectUrl);
        return this;
    }

    /**
     * With webhook url payment order builder.
     *
     * @param webhookUrl the webhook url
     * @return the payment order builder
     */
    public PaymentOrderBuilder withWebhookUrl(String webhookUrl) {
        paymentOrder.setWebhookUrl(webhookUrl);
        return this;
    }
    /**
     * Build payment order.
     *
     * @return the payment order
     */
    public PaymentOrder build() {
        return paymentOrder;
    }
}
