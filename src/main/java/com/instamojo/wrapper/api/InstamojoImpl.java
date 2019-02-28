package com.instamojo.wrapper.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.instamojo.wrapper.exception.ConnectionException;
import com.instamojo.wrapper.exception.HTTPException;
import com.instamojo.wrapper.filter.PaymentRequestFilter;
import com.instamojo.wrapper.filter.PayoutFilter;
import com.instamojo.wrapper.model.*;
import com.instamojo.wrapper.response.ApiListResponse;
import com.instamojo.wrapper.response.ApiResponse;
import com.instamojo.wrapper.util.Constants;
import com.instamojo.wrapper.util.GsonWrapper;
import com.instamojo.wrapper.util.HttpUtils;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.http.util.Asserts;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class InstamojoImpl implements Instamojo {

    private ApiContext context;

    private Gson gson;

    public InstamojoImpl(ApiContext context) {
        this.context = context;
        this.gson = GsonWrapper.getGson();
    }

    @Override
    public PaymentOrderResponse createPaymentOrder(PaymentOrder paymentOrder) throws HTTPException, ConnectionException {
        Asserts.notNull(paymentOrder, "Payment Order");

        try {
            String response = HttpUtils.post(context.getApiPath(Constants.PATH_PAYMENT_ORDER), getHeaders(),
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
            String response = HttpUtils.get(context.getApiPath(Constants.PATH_PAYMENT_ORDER + "id:" + id + "/"),
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
                    context.getApiPath(Constants.PATH_PAYMENT_ORDER + "transaction_id:" + transactionId + "/"),
                    getHeaders());
            return gson.fromJson(response, PaymentOrder.class);

        } catch (IOException | URISyntaxException e) {
            throw new ConnectionException(e.getMessage(), e);
        }
    }

    @Override
    public ApiListResponse<PaymentOrder> getPaymentOrders(int page, int limit) throws ConnectionException, HTTPException {

        Map<String, String> params = new HashMap<>();
        params.put("page", String.valueOf(page));
        params.put("limit", String.valueOf(limit));

        try {
            String response = HttpUtils.get(context.getApiPath(Constants.PATH_PAYMENT_ORDER), getHeaders(), params);

            Type type = new TypeToken<ApiListResponse<PaymentOrder>>() {
            }.getType();
            return gson.fromJson(response, type);

        } catch (IOException | URISyntaxException e) {
            throw new ConnectionException(e.getMessage(), e);
        }
    }

    @Override
    public Refund createRefund(Refund refund) throws HTTPException, ConnectionException {
        Asserts.notNull(refund, "Refund");

        try {
            String response = HttpUtils.post(context.getApiPath(Constants.PATH_REFUND + refund.getPaymentId() + "" +
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
    public ApiListResponse<Invoice> getInvoices(int page, int limit) throws ConnectionException, HTTPException {

        Map<String, String> params = new HashMap<>();
        params.put("page", String.valueOf(page));
        params.put("limit", String.valueOf(limit));

        try {
            String response = HttpUtils.get(context.getApiPath(Constants.PATH_INVOICE), getHeaders(), params);

            Type type = new TypeToken<ApiListResponse<Invoice>>() {
            }.getType();
            return gson.fromJson(response, type);

        } catch (IOException | URISyntaxException e) {
            throw new ConnectionException(e.getMessage(), e);
        }
    }

    @Override
    public ApiListResponse<Payout> getPayouts(int page, int limit) throws ConnectionException, HTTPException {
        return getPayouts(null, page, limit);
    }

    @Override
    public ApiListResponse<Payout> getPayouts(Map<PayoutFilter, String> filter, int page, int limit) throws ConnectionException, HTTPException {

        Map<String, String> params = new HashMap<>();
        if (filter != null) {
            for (Map.Entry<PayoutFilter, String> entry : filter.entrySet()) {
                params.put(entry.getKey().name().toLowerCase(), entry.getValue());
            }
        }
        params.put("page", String.valueOf(page));
        params.put("limit", String.valueOf(limit));

        try {
            String response = HttpUtils.get(context.getApiPath(Constants.PATH_PAYOUT), getHeaders(), params);

            Type type = new TypeToken<ApiListResponse<Payout>>() {
            }.getType();
            return gson.fromJson(response, type);

        } catch (IOException | URISyntaxException e) {
            throw new ConnectionException(e.getMessage(), e);
        }
    }

    @Override
    public Payout getPayout(String id) throws ConnectionException, HTTPException {
        Asserts.notEmpty(id, "Payout Id");

        try {
            String response = HttpUtils.get(context.getApiPath(Constants.PATH_PAYOUT + "id:" + id + "/"),
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
                    context.getApiPath(Constants.PATH_PAYMENT_REQUEST), getHeaders(), gson.toJson(paymentRequest));
            return gson.fromJson(response, PaymentRequest.class);

        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(), e);
        }
    }

    @Override
    public ApiListResponse<PaymentRequest> getPaymentRequests(int page, int limit) throws ConnectionException, HTTPException {
        return getPaymentRequests(null, page, limit);
    }

    @Override
    public ApiListResponse<PaymentRequest> getPaymentRequests(Map<PaymentRequestFilter, String> filter, int page, int limit) throws ConnectionException, HTTPException {

        Map<String, String> params = new HashMap<>();
        if (filter != null) {
            for (Map.Entry<PaymentRequestFilter, String> entry : filter.entrySet()) {
                params.put(entry.getKey().name().toLowerCase(), entry.getValue());
            }
        }
        params.put("page", String.valueOf(page));
        params.put("limit", String.valueOf(limit));

        try {
            String response = HttpUtils.get(context.getApiPath(Constants.PATH_PAYMENT_REQUEST), getHeaders(), params);

            Type type = new TypeToken<ApiListResponse<PaymentRequest>>() {
            }.getType();
            return gson.fromJson(response, type);

        } catch (IOException | URISyntaxException e) {
            throw new ConnectionException(e.getMessage(), e);
        }
    }

    @Override
    public PaymentRequest getPaymentRequest(String id) throws ConnectionException, HTTPException {
        try {
            String response = HttpUtils.get(context.getApiPath(Constants.PATH_PAYMENT_REQUEST + id + "/"),
                    getHeaders());
            return gson.fromJson(response, PaymentRequest.class);

        } catch (IOException | URISyntaxException e) {
            throw new ConnectionException(e.getMessage(), e);
        }
    }

    @Override
    public Boolean enablePaymentRequest(String id) throws ConnectionException, HTTPException {
        try {
            String response = HttpUtils.post(context.getApiPath(Constants.PATH_PAYMENT_REQUEST + id + "/enable/"),
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
            String response = HttpUtils.post(context.getApiPath(Constants.PATH_PAYMENT_REQUEST + id + "/disable/"),
                    getHeaders());
            ApiResponse rapResponse = gson.fromJson(response, ApiResponse.class);
            return rapResponse.getSuccess();

        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(), e);
        }
    }

    private Map<String, String> getHeaders() throws ConnectionException, HTTPException {
        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.HEADER_AUTHORIZATION, context.getAuthorization());
        return headers;
    }
}
