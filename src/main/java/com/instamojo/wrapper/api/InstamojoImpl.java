package com.instamojo.wrapper.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.instamojo.wrapper.exception.ConnectionException;
import com.instamojo.wrapper.exception.HTTPException;
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

public class InstamojoImpl implements Instamojo {

    private ApiContext context;

    private Gson gson;

    public InstamojoImpl(ApiContext context) {
        this.context = context;
        this.gson = new Gson();
    }

    @Override
    public PaymentOrderResponse createPaymentOrder(PaymentOrder paymentOrder) throws HTTPException, ConnectionException {
        Asserts.notNull(paymentOrder, "Payment Order");

        try {
            String response = HttpUtils.post(context.getApiPath(Constants.PAYMENT_ORDER_API_PATH), getHeaders(),
                    gson.toJson(paymentOrder));
            return gson.fromJson(response, PaymentOrderResponse.class);

        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(), e);
        }
    }

    @Override
    public PaymentOrder getPaymentOrder(String id) throws ConnectionException, HTTPException {
        Asserts.notEmpty(id, "Order Id");

        try {
            String response = HttpUtils.get(context.getApiPath(Constants.PAYMENT_ORDER_API_PATH + "id:" + id + "/"),
                    getHeaders());
            return gson.fromJson(response, PaymentOrder.class);

        } catch (IOException | URISyntaxException e) {
            throw new ConnectionException(e.getMessage(), e);
        }
    }

    @Override
    public PaymentOrder getPaymentOrderByTransactionId(String transactionId) throws ConnectionException, HTTPException {
        Asserts.notEmpty(transactionId, "Transaction Id");

        try {
            String response = HttpUtils.get(
                    context.getApiPath(Constants.PAYMENT_ORDER_API_PATH + "transaction_id:" + transactionId + "/"),
                    getHeaders());
            return gson.fromJson(response, PaymentOrder.class);

        } catch (IOException | URISyntaxException e) {
            throw new ConnectionException(e.getMessage(), e);
        }
    }

    @Override
    public List<PaymentOrder> getPaymentOrders(PaymentOrderFilter paymentOrderFilter) throws ConnectionException, HTTPException {
        Asserts.notNull(paymentOrderFilter, "Payment Order Filter");

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
            String response = HttpUtils.get(context.getApiPath(Constants.PAYMENT_ORDER_API_PATH), getHeaders(),
                    params);

            Type type = new TypeToken<ApiListResponse<PaymentOrder>>() {
            }.getType();
            ApiListResponse<PaymentOrder> paymentOrderListResponse = gson.fromJson(response, type);
            return paymentOrderListResponse.getResults();

        } catch (IOException | URISyntaxException e) {
            throw new ConnectionException(e.getMessage(), e);
        }
    }

    @Override
    public Refund createRefund(Refund refund) throws HTTPException, ConnectionException {
        Asserts.notNull(refund, "Refund");

        try {
            String response = HttpUtils.post(context.getApiPath(Constants.REFUND_API_PATH + refund.getPaymentId() + "" +
                    "/refund/"), getHeaders(), gson.toJson(refund));

            Type type = new TypeToken<ApiResponse<Refund>>() {
            }.getType();
            ApiResponse<Refund> createRefundResponse = gson.fromJson(response, type);
            return createRefundResponse.getResult();

        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(), e);
        }
    }

    @Override
    public String generateWebhookSignature(Map<String, String> data, String salt) {

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
    public List<Invoice> getInvoices() throws ConnectionException, HTTPException {
        Map<String, String> params = new HashMap<>();

        try {
            String response = HttpUtils.get(context.getApiPath(Constants.INVOICE_API_PATH), getHeaders(), params);

            Type type = new TypeToken<ApiListResponse<Invoice>>() {
            }.getType();
            ApiListResponse<Invoice> invoices = gson.fromJson(response, type);
            return invoices.getResults();

        } catch (IOException | URISyntaxException e) {
            throw new ConnectionException(e.getMessage(), e);
        }
    }

    @Override
    public List<Payout> getPayouts() throws ConnectionException, HTTPException {
        Map<String, String> params = new HashMap<>();

        try {
            String response = HttpUtils.get(context.getApiPath(Constants.PAYOUT_API_PATH), getHeaders(), params);

            Type type = new TypeToken<ApiListResponse<Payout>>() {
            }.getType();
            ApiListResponse<Payout> invoices = gson.fromJson(response, type);
            return invoices.getResults();

        } catch (IOException | URISyntaxException e) {
            throw new ConnectionException(e.getMessage(), e);
        }
    }

    @Override
    public Payout getPayout(String id) throws ConnectionException, HTTPException {
        Asserts.notEmpty(id, "Payout Id");

        try {
            String response = HttpUtils.get(context.getApiPath(Constants.PAYOUT_API_PATH + "id:" + id + "/"),
                    getHeaders());

            Type type = new TypeToken<ApiResponse<Payout>>() {
            }.getType();
            ApiResponse<Payout> payoutResponse = gson.fromJson(response, type);
            return payoutResponse.getResult();

        } catch (IOException | URISyntaxException e) {
            throw new ConnectionException(e.getMessage(), e);
        }
    }

    @Override
    public PaymentRequest createPaymentRequest(PaymentRequest paymentRequest) throws ConnectionException, HTTPException {
        Asserts.notNull(paymentRequest, "payment request");

        try {
            String response = HttpUtils.post(
                    context.getApiPath(Constants.PAYMENT_REQUEST_API_PATH), getHeaders(), gson.toJson(paymentRequest));
            return gson.fromJson(response, PaymentRequest.class);

        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(), e);
        }
    }

    @Override
    public List<PaymentRequest> getPaymentRequests() throws ConnectionException, HTTPException {
        try {
            String response = HttpUtils.get(context.getApiPath(Constants.PAYMENT_REQUEST_API_PATH), getHeaders());

            Type type = new TypeToken<ApiListResponse<PaymentRequest>>() {
            }.getType();
            ApiListResponse<PaymentRequest> rapResponse = gson.fromJson(response, type);
            return rapResponse.getResults();

        } catch (IOException | URISyntaxException e) {
            throw new ConnectionException(e.getMessage(), e);
        }
    }

    @Override
    public PaymentRequest getPaymentRequest(String id) throws ConnectionException, HTTPException {
        try {
            String response = HttpUtils.get(context.getApiPath(Constants.PAYMENT_REQUEST_API_PATH + id + "/"),
                    getHeaders());
            return gson.fromJson(response, PaymentRequest.class);

        } catch (IOException | URISyntaxException e) {
            throw new ConnectionException(e.getMessage(), e);
        }
    }

    @Override
    public Boolean enablePaymentRequest(String id) throws ConnectionException, HTTPException {
        try {
            String response = HttpUtils.post(context.getApiPath(Constants.PAYMENT_REQUEST_API_PATH + id + "/enable/"),
                    getHeaders());
            ApiResponse rapResponse = gson.fromJson(response, ApiResponse.class);
            return rapResponse.getSuccess();

        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(), e);
        }
    }

    @Override
    public Boolean disablePaymentRequest(String id) throws ConnectionException, HTTPException {
        try {
            String response = HttpUtils.post(context.getApiPath(Constants.PAYMENT_REQUEST_API_PATH + id + "/disable/"),
                    getHeaders());
            ApiResponse rapResponse = gson.fromJson(response, ApiResponse.class);
            return rapResponse.getSuccess();

        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(), e);
        }
    }

    private Map<String, String> getHeaders() throws ConnectionException, HTTPException {
        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.AUTHORIZATION, context.getAuthorization());
        return headers;
    }
}
