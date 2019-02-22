# Java wrapper for Instamojo API

Table of Contents
=================
* [Preface](#preface)
* [Requirements](#requirements)
* [Installation](#installation)
    * [Gradle](#gradle)
* [Authentication Keys](#authentication-keys)
* [Multitenancy](#multitenancy)
* [Payment Order API](#payment-order-api)
    * [Create new Payment Order](#create-new-payment-order)
      * [Payment Order Creation Parameters](#payment-order-creation-parameters)
          * [Required](#required)
          * [Optional](#optional)
    * [Get details of a Payment order by order id](#get-details-of-a-payment-order-by-order-id)
    * [Get details of a Payment order by transaction id](#get-details-of-a-payment-order-by-transaction-id)
    * [Get list of all Payment Orders](#get-list-of-all-payment-orders)
      * [Payment Order List Parameters](#payment-order-list-parameters)
          * [Optional](#optional-1)
* [Refund API](#refund-api)
    * [Create a refund](#create-a-refund)
      * [Refund Creation Parameters](#refund-creation-parameters)
          * [Required](#required-1)

## Preface
Instamojo java wrapper for the Instamojo API assists you to programmatically create, list, filter payment orders and 
refunds on Instamojo.

## Requirements
Java Version : 1.7+
   
## Installation [ ![Download](https://api.bintray.com/packages/dev-accounts/maven/wrappers/images/download.svg) ](https://bintray.com/dev-accounts/maven/wrappers/_latestVersion) [![Build Status](https://travis-ci.org/Instamojo/instamojo-java.svg?branch=master)](https://travis-ci.org/Instamojo/instamojo-java)
### Gradle
Add following dependency to the `build.gradle`

`compile group: 'com.instamojo', name: 'instamojo-java', version: '2.0.2'`

## Authentication Keys
Generate CLIENT_ID and CLIENT_SECRET for specific environments from the following links.
 - [Test Environment](https://test.instamojo.com/integrations)
 - [Production Environment](https://www.instamojo.com/integrations)

Related support article: [How Do I Get My Client ID And Client Secret?](https://support.instamojo.com/hc/en-us/articles/212214265-How-do-I-get-my-Client-ID-and-Client-Secret-)

## Multitenancy
As of now, **MULTITENANCY IS NOT SUPPORTED** by this wrapper which means you will not be able to use this wrapper in a single application with multiple Instamojo accounts. The call to `ApiContext.create()` returns a singleton object of class `ApiContext` with the given CLIENT_ID and CLIENT_SECRET, and will always return the same object even when called multiple times (even with a different CLIENT_ID and CLIENT_SECRET).

## Payment Order API
### Create new Payment Order
```Java
/*
 * Get a reference to the instamojo api
 */
ApiContext context = ApiContext.create("[CLIENT_ID]", "[CLIENT_SECRET]", ApiContext.Mode.TEST);
Instamojo api = new InstamojoImpl(context);

/*
 * Create a new payment order
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

try {
    PaymentOrderResponse paymentOrderResponse = api.createPaymentOrder(order);
    System.out.println(paymentOrderResponse.getPaymentOrder().getStatus());

} catch (HTTPException e) {
    System.out.println(e.getStatusCode());
    System.out.println(e.getMessage());
    System.out.println(e.getJsonPayload());

} catch (ConnectionException e) {
    System.out.println(e.getMessage());
}
```

#### Payment Order Creation Parameters
##### Required
1.	Name:  Name of the customer (max 100 characters).
2.	Email:  Email address of the customer (max 75 characters).
3.	Phone:  Phone number of the customer. (Non-Indian phone numbers must include country code).
4.	Currency:  String identifier for the currency. Currently, only INR (for Indian 	Rupee) is supported.
5.	Amount:  Amount the customer has to pay. Numbers up to 2 decimal places are supported.
6.	Transaction ID:  Unique identifier for the order (max 64 characters). Identifier can contain alphanumeric characters, hyphens and underscores only. This is generally the unique order id (or primary key) in your system.
7.	Redirect URL:  Full URL to which the customer is redirected after payment. 	Redirection happens even if payment wasn't successful. This URL shouldn't contain any query parameters.

##### Optional
1.  Description:  Short description of the order (max 255 characters). If provided, this information is sent to backend gateways, wherever possible.
2.  Webhook URL: Full URL to which webhook call to be made post transaction.

### Get details of a Payment order by order id
```Java
/*
 * Get details of payment order when order id is given
 */
try {
    PaymentOrder paymentOrder = api.getPaymentOrder("[PAYMENT_ORDER_ID]");
    System.out.println(paymentOrder.getId());
    System.out.println(paymentOrder.getStatus());

} catch (HTTPException e) {
    System.out.println(e.getStatusCode());
    System.out.println(e.getMessage());
    System.out.println(e.getJsonPayload());

} catch (ConnectionException e) {
    System.out.println(e.getMessage());
}
```

### Get details of a Payment order by transaction id
```Java
/*
 * Get details of payment order when transaction id is given
 */
try {
    PaymentOrder paymentOrder = api.getPaymentOrderByTransactionId("[TRANSACTION_ID]");
    System.out.println(paymentOrder.getId());
    System.out.println(paymentOrder.getStatus());

} catch (HTTPException e) {
    System.out.println(e.getStatusCode());
    System.out.println(e.getMessage());
    System.out.println(e.getJsonPayload());

} catch (ConnectionException e) {
    System.out.println(e.getMessage());
}
```

### Get list of all Payment Orders
```Java
/*
 * Get list of all payment orders
 */
try {
    PaymentOrderFilter paymentOrderFilter = new PaymentOrderFilter();
    ApiListResponse<PaymentOrder> paymentOrders = api.getPaymentOrders(paymentOrderFilter);

    // Loop over all of the payment orders and print status of each
    // payment order.
    for (PaymentOrder paymentOrder : paymentOrders.getResults()) {
        System.out.println("Result = " + paymentOrder.getStatus());
    }
    System.out.println(paymentOrders);

} catch (HTTPException e) {
    System.out.println(e.getStatusCode());
    System.out.println(e.getMessage());
    System.out.println(e.getJsonPayload());

} catch (ConnectionException e) {
    System.out.println(e.getMessage());
}
```
#### Payment Order List Parameters
##### Optional     
1.	Id:  Search for payment orders by id.
2.	Transaction_id:  Search for payment orders by your transaction_id.
3.	Page:  Page number of the results to retrieve from.
4.	Limit:  Limit the number of results returned per page. Defaults to 50 results per page. Max limit allowed is 500.

## Refund API
### Create a refund
```Java
/*
 * Create a new refund
 */
Refund refund = new Refund();
refund.setPaymentId("[PaymentId]");
refund.setStatus("refunded");
refund.setType("RFD");
refund.setBody("This is a test refund.");
refund.setRefundAmount(9D);
refund.setTotalAmount(10D);

try {
    Refund createdRefund = api.createRefund(refund);
    System.out.println(createdRefund.getId());
    System.out.println(createdRefund.getStatus());

} catch (HTTPException e) {
    System.out.println(e.getStatusCode());
    System.out.println(e.getMessage());
    System.out.println(e.getJsonPayload());

} catch (ConnectionException e) {
    System.out.println(e.getMessage());
}
```
#### Refund Creation Parameters
##### Required
1.	payment_id:  Id of the payment.
2.	type:  A three letter short-code identifying the reason for this case.
3.	body: Additional text explaining the refund.
4.	refund_amount:  This field can be used to specify the refund amount. For instance, you may want to issue a refund for an amount lesser than what was paid.
