# Java wrapper for Instamojo API

Table of Contents
=================
* [Preface](#preface)
* [Requirements](#requirements)
* [Installation](#installation)
    * [Gradle](#gradle)
    * [JAR](#jar)
* [Authentication Keys](#authentication-keys)
* [End Points](#end-points)
    * [Test URLs](#test-urls)
    * [Production URLs](#production-urls)
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
All the dependencies will be downloded automatically.

`compile group: 'com.instamojo', name: 'instamojo-java', version: '1.0.0'`

### JAR
Download `instamojo-java-<version>.jar` from the latest release and the following dependencies to the classpath of the Application. 

1. [Apache HttpClient v4.5.2](http://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient/4.5.2)
2. [Apache HttpCore v4.4.4](http://mvnrepository.com/artifact/org.apache.httpcomponents/httpcore/4.4.4)
3. [Apache Commons Codec v1.9](http://mvnrepository.com/artifact/commons-codec/commons-codec/1.9)
4. [Apache Commons Logging v1.2](http://mvnrepository.com/artifact/commons-logging/commons-logging/1.2)
5. [Gson v2.6.2](http://mvnrepository.com/artifact/com.google.code.gson/gson/2.6.2)

## Authentication Keys
Please raise support ticket to receive CLIENT_ID and CLIENT_SECRET for Test and Production Environments

## End Points
### Test URLs
auth_endpoint : https://test.instamojo.com/oauth2/token/

endpoint: https://test.instamojo.com/v2/

### Production URLs
auth endpoint : https://www.instamojo.com/oauth2/token/

endpoint: https://api.instamojo.com/v2/

## Payment Order API
### Create new Payment Order
```Java
/***** Create a new payment order *******/
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

try {
	// gets the reference to the instamojo api
	api = InstamojoImpl.getApi("[CLIENT_ID]", "[CLIENT_SECRET]", "[API_ENDPOINT]", "[AUTH_ENDPOINT]");
} catch (ConnectionException e) {
	LOGGER.log(Level.SEVERE, e.toString(), e);
}

boolean isOrderValid = order.validate();

if (isOrderValid) {
	try {
		CreatePaymentOrderResponse createPaymentOrderResponse = api.createNewPaymentOrder(order);
	    // print the status of the payment order.
		System.out.println(createPaymentOrderResponse.getPaymentOrder().getStatus());
	} catch (InvalidPaymentOrderException e) {
	    LOGGER.log(Level.SEVERE, e.toString(), e);

	    if (order.isTransactionIdInvalid()) {
	        System.out.println("Transaction id is invalid. This is mostly due to duplicate  transaction id.");
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
```

#### Payment Order Creation Parameters
##### Required
1.	Name:  Name of the customer (max 100 characters).
2.	Email:  Email address of the customer (max 75 characters).
3.	Phone:  Phone number of the customer. It is recommended to use 	E.164 	number formatting for all phone numbers.
4.	Currency:  String identifier for the currency. Currently, only INR (for Indian 	Rupee) is supported.
5.	Amount:  Amount the customer has to pay. Numbers upto 2 decimal places are supported.
6.	Transaction ID:  Unique identifier for the order (max 64 characters). Identifier can 	contain alphanumeric characters, hyphens and underscores only. This is generally the unique order id (or primary key) in your system.
7.	Redirect URL:  Full URL to which the customer is redirected after payment. 	Redirection happens even if payment wasn't successful. This URL shouldn't contain any query parameters.

##### Optional
1.  Description:  Short description of the order (max 255 characters). If provided, this information is sent to backend gateways, wherever possible.
2.  Webhook URL: Full URL to which webhook is made post transaction.

### Get details of a Payment order by order id
```Java
/***** Get details of payment order when order id is given. *******/

try {
    Instamojo api = InstamojoImpl.getApi("[CLIENT_ID]", "[CLIENT_SECRET]", "[API_ENDPOINT]", "[AUTH_ENDPOINT]");

    PaymentOrderDetailsResponse paymentOrderDetailsResponse = api.getPaymentOrderDetails("[PAYMENT_ORDER_ID]");

    // print the status of the payment order.
    System.out.println(paymentOrderDetailsResponse.getStatus());
} catch (ConnectionException e) {
    LOGGER.log(Level.SEVERE, e.toString(), e);
}
```

### Get details of a Payment order by transaction id
```Java
/****** Get details of payment order when transaction id is given.*******/

try {
Instamojo api = InstamojoImpl.getApi("[CLIENT_ID]", "[CLIENT_SECRET]", "[API_ENDPOINT]", "[AUTH_ENDPOINT]");

PaymentOrderDetailsResponse paymentOrderDetailsResponse = api.getPaymentOrderDetailsByTransactionId("[TRANSACTION_ID]");

// print the status of the payment order.
System.out.println(paymentOrderDetailsResponse.getStatus());
} catch (ConnectionException e) {
   LOGGER.log(Level.SEVERE, e.toString(), e);
}
```

### Get list of all Payment Orders
```Java
/***** Get list of all payment orders. *******/

try {
Instamojo api = InstamojoImpl.getApi("[CLIENT_ID]", "[CLIENT_SECRET]", 
"[API_ENDPOINT]", "[AUTH_ENDPOINT]");

PaymentOrderFilter paymentOrderFilter = new PaymentOrderFilter();
PaymentOrderListResponse paymentOrderListResponse =  api.getPaymentOrderList(paymentOrderFilter);

// print the list of all payment orders.
for (PaymentOrder paymentOrder : paymentOrderListResponse.getPaymentOrders()) {
    System.out.println("Result = " + paymentOrder.getStatus());
}

} catch (ConnectionException e) {
    LOGGER.log(Level.SEVERE, e.toString(), e);
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
/***** Create a new refund. *******/
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
```
#### Refund Creation Parameters
##### Required
1.	payment_id:  Id of the payment.
2.	type:  A three letter short-code identifying the reason for this case.
3.	body: Additional text explaining the refund.
4.	refund_amount:  This field can be used to specify the refund amount. For instance, you may want to issue a refund for an amount lesser than what was paid.
