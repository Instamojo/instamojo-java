package com.instamojo.wrapper.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.instamojo.wrapper.exception.ConnectionException;
import com.instamojo.wrapper.exception.InstamojoClientException;
import com.instamojo.wrapper.model.*;
import com.instamojo.wrapper.response.ApiListResponse;
import com.instamojo.wrapper.response.ApiResponse;
import com.instamojo.wrapper.util.Constants;
import com.instamojo.wrapper.util.HttpUtils;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.http.util.Asserts;
import org.apache.http.util.TextUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InstamojoImpl implements Instamojo {

    private static final Logger LOGGER = Logger.getLogger(ApiContext.class.getName());

    private ApiContext context;

    private Gson gson;

    public InstamojoImpl(ApiContext context) {
        this.context = context;
        this.gson = new Gson();
    }

    @Override
    public PaymentOrderResponse createPaymentOrder(PaymentOrder paymentOrder) throws InstamojoClientException, ConnectionException {
        Asserts.notNull(paymentOrder, "Payment Order");

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.AUTHORIZATION, context.getAuthorization());
        try {
            String response = HttpUtils.post(context.getApiPath(Constants.PAYMENT_ORDER_API_PATH), headers,
                    gson.toJson(paymentOrder));
            PaymentOrderResponse paymentOrderResponse = gson.fromJson(response, PaymentOrderResponse.class);
            return paymentOrderResponse;

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new ConnectionException(e.toString(), e);
        }
    }

    @Override
    public PaymentOrder getPaymentOrderDetails(String id) throws ConnectionException, InstamojoClientException {
        Asserts.notEmpty(id, "Order Id");

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.AUTHORIZATION, context.getAuthorization());

        try {
            String response = HttpUtils.get(
                    context.getApiPath(Constants.PAYMENT_ORDER_API_PATH + "id:" + id + "/"), headers,
                    null);
            PaymentOrder paymentOrderDetailsResponse = gson.fromJson(response, PaymentOrder.class);
            return paymentOrderDetailsResponse;

        } catch (IOException | URISyntaxException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new ConnectionException(e.toString(), e);
        }
    }

    @Override
    public PaymentOrder getPaymentOrderDetailsByTransactionId(String transactionId) throws ConnectionException, InstamojoClientException {
        Asserts.notEmpty(transactionId, "Transaction Id");

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.AUTHORIZATION, context.getAuthorization());

        try {
            String response = HttpUtils.get(
                    context.getApiPath(Constants.PAYMENT_ORDER_API_PATH + "transaction_id:" + transactionId + "/"),
                    headers, null);
            PaymentOrder paymentOrderDetailsResponse = gson.fromJson(response, PaymentOrder.class);
            return paymentOrderDetailsResponse;

        } catch (IOException | URISyntaxException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new ConnectionException(e.toString(), e);
        }
    }

    @Override
    public List<PaymentOrder> getPaymentOrderList(PaymentOrderFilter paymentOrderFilter) throws ConnectionException, InstamojoClientException {
        Asserts.notNull(paymentOrderFilter, "Payment Order Filter");

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.AUTHORIZATION, context.getAuthorization());
        Map<String, String> params = new HashMap<>();

        String id = paymentOrderFilter.getId();
        String transactionId = paymentOrderFilter.getTransactionId();
        Integer page = paymentOrderFilter.getPage();
        Integer limit = paymentOrderFilter.getLimit();

        if (!TextUtils.isEmpty(id)) {
            params.put("id", id);
        }
        if (!TextUtils.isEmpty(transactionId)) {
            params.put("transaction_id", transactionId);
        }
        if (page != null && page != 0) {
            params.put("page", String.valueOf(page));
        }
        if (limit != null && limit != 0) {
            params.put("limit", String.valueOf(limit));
        }

        try {
            String response = HttpUtils.get(context.getApiPath(Constants.PAYMENT_ORDER_API_PATH), headers,
                    params);

            Type type = new TypeToken<ApiListResponse<PaymentOrder>>() {
            }.getType();
            ApiListResponse<PaymentOrder> paymentOrderListResponse = gson.fromJson(response, type);

            return paymentOrderListResponse.getResults();

        } catch (IOException | URISyntaxException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new ConnectionException(e.toString(), e);
        }
    }

    @Override
    public Refund createRefund(Refund refund) throws InstamojoClientException, ConnectionException {
        Asserts.notNull(refund, "Refund");

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.AUTHORIZATION, context.getAuthorization());

        try {
            String response = HttpUtils.post(
                    context.getApiPath(Constants.REFUND_API_PATH + refund.getPaymentId() + "" +
                            "/refund/"), headers, gson.toJson(refund));

            Type type = new TypeToken<ApiResponse<Refund>>() {
            }.getType();
            ApiResponse<Refund> createRefundResponse = gson.fromJson(response, type);
            return createRefundResponse.getResult();

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new ConnectionException(e.toString(), e);
        }
    }

    @Override
    public String createWebhookSignature(Map<String, String> data, String salt) {

        ArrayList<String> keys = new ArrayList<>(data.keySet());
        Collections.sort(keys);

        StringBuilder sb = new StringBuilder();
        for (int index = 0; index < keys.size(); index++) {
            sb.append(data.get(keys.get(index)));
            if (index != keys.size() - 1) {
                sb.append('|');
            }
        }

        return new HmacUtils(HmacAlgorithms.HMAC_SHA_1, salt).hmacHex(sb.toString());
    }

    @Override
    public List<Invoice> getInvoices() throws ConnectionException, InstamojoClientException {
        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.AUTHORIZATION, context.getAuthorization());
        Map<String, String> params = new HashMap<>();

        try {
            String response = HttpUtils.get(context.getApiPath(Constants.INVOICE_API_PATH), headers, params);

            Type type = new TypeToken<ApiListResponse<Invoice>>() {
            }.getType();
            ApiListResponse<Invoice> invoices = gson.fromJson(response, type);
            return invoices.getResults();

        } catch (IOException | URISyntaxException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new ConnectionException(e.toString(), e);
        }
    }

    @Override
    public List<Payout> getPayouts() throws ConnectionException, InstamojoClientException {
        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.AUTHORIZATION, context.getAuthorization());
        Map<String, String> params = new HashMap<>();

        try {
            String response = HttpUtils.get(context.getApiPath(Constants.PAYOUT_API_PATH), headers, params);

            Type type = new TypeToken<ApiListResponse<Payout>>() {
            }.getType();
            ApiListResponse<Payout> invoices = gson.fromJson(response, type);
            return invoices.getResults();

        } catch (IOException | URISyntaxException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new ConnectionException(e.toString(), e);
        }
    }

    @Override
    public Payout getPayout(String id) throws ConnectionException, InstamojoClientException {
        Asserts.notEmpty(id, "Payout Id");

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.AUTHORIZATION, context.getAuthorization());

        try {
            String response = HttpUtils.get(context.getApiPath(Constants.PAYOUT_API_PATH + "id:" + id + "/"), headers);

            Type type = new TypeToken<ApiResponse<Payout>>() {
            }.getType();
            ApiResponse<Payout> payoutResponse = gson.fromJson(response, type);
            return payoutResponse.getResult();

        } catch (IOException | URISyntaxException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new ConnectionException(e.toString(), e);
        }
    }

    @Override
    public PaymentRequest createPaymentRequest(PaymentRequest paymentRequest) throws ConnectionException, InstamojoClientException {
        Asserts.notNull(paymentRequest, "payment request");

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.AUTHORIZATION, context.getAuthorization());

        try {
            String response = HttpUtils.post(
                    context.getApiPath(Constants.PAYMENT_REQUEST_API_PATH), headers, gson.toJson(paymentRequest));

            PaymentRequest createRapResponse = gson.fromJson(response, PaymentRequest.class);
            return createRapResponse;

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new ConnectionException(e.toString(), e);
        }
    }

    @Override
    public List<PaymentRequest> getPaymentRequests() throws ConnectionException, InstamojoClientException {
        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.AUTHORIZATION, context.getAuthorization());

        try {
            String response = HttpUtils.get(context.getApiPath(Constants.PAYMENT_REQUEST_API_PATH), headers);

            Type type = new TypeToken<ApiListResponse<PaymentRequest>>() {
            }.getType();
            ApiListResponse<PaymentRequest> rapResponse = gson.fromJson(response, type);
            return rapResponse.getResults();

        } catch (IOException | URISyntaxException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new ConnectionException(e.toString(), e);
        }
    }

    @Override
    public PaymentRequest getPaymentRequest(String id) throws ConnectionException, InstamojoClientException {
        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.AUTHORIZATION, context.getAuthorization());

        try {
            String response = HttpUtils.get(context.getApiPath(Constants.PAYMENT_REQUEST_API_PATH + id + "/"), headers);
            PaymentRequest rapResponse = gson.fromJson(response, PaymentRequest.class);
            return rapResponse;

        } catch (IOException | URISyntaxException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new ConnectionException(e.toString(), e);
        }
    }

    @Override
    public Boolean enablePaymentRequest(String id) throws ConnectionException, InstamojoClientException {
        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.AUTHORIZATION, context.getAuthorization());

        try {
            String response = HttpUtils.post(context.getApiPath(Constants.PAYMENT_REQUEST_API_PATH + id + "/enable/"), headers);
            ApiResponse rapResponse = gson.fromJson(response, ApiResponse.class);
            return rapResponse.getSuccess();

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new ConnectionException(e.toString(), e);
        }
    }

    @Override
    public Boolean disablePaymentRequest(String id) throws ConnectionException, InstamojoClientException {
        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.AUTHORIZATION, context.getAuthorization());

        try {
            String response = HttpUtils.post(context.getApiPath(Constants.PAYMENT_REQUEST_API_PATH + id + "/disable/"), headers);
            ApiResponse rapResponse = gson.fromJson(response, ApiResponse.class);
            return rapResponse.getSuccess();

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new ConnectionException(e.toString(), e);
        }
    }
}
