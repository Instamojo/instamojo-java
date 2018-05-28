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
import com.instamojo.wrapper.util.Constants;
import com.instamojo.wrapper.util.HttpUtils;
import com.instamojo.wrapper.util.JsonUtils;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.http.util.Asserts;
import org.apache.http.util.TextUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InstamojoImpl implements Instamojo {

    private static final Logger LOGGER = Logger.getLogger(ApiContext.class.getName());

    private ApiContext context;

    public InstamojoImpl(ApiContext context) {
        this.context = context;
    }

    @Override
    public CreatePaymentOrderResponse createPaymentOrder(PaymentOrder paymentOrder) throws ConnectionException, InvalidPaymentOrderException {
        Asserts.notNull(paymentOrder, "Payment Order");

        boolean isValid = paymentOrder.validate();

        if (!isValid) {
            throw new InvalidPaymentOrderException();
        }

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.AUTHORIZATION, context.getAuthorization());
        Map<String, String> params = new HashMap<>();

        params.put("name", paymentOrder.getName());
        params.put("email", paymentOrder.getEmail());
        params.put("phone", paymentOrder.getPhone());
        params.put("currency", paymentOrder.getCurrency());
        params.put("amount", String.valueOf(paymentOrder.getAmount()));
        params.put("description", paymentOrder.getDescription());
        params.put("transaction_id", paymentOrder.getTransactionId());
        params.put("redirect_url", paymentOrder.getRedirectUrl());
        params.put("webhook_url", paymentOrder.getWebhookUrl());

        try {
            String response = HttpUtils.sendPostRequest(context.getApiPath(Constants.PAYMENT_ORDER_API_PATH), headers,
                    params);
            CreatePaymentOrderResponse createPaymentOrderResponse = JsonUtils.convertJsonStringToObject(response,
                    CreatePaymentOrderResponse.class);
            if (createPaymentOrderResponse.getPaymentOrder() == null) {
                Map<String, Object> map = JsonUtils.convertJsonStringToMap(response);
                if (map != null && map.get(Constants.TRANSACTION_ID) != null) {
                    paymentOrder.setTransactionIdInvalid(true);
                }

                if (map != null && map.get(Constants.WEBHOOK_URL) != null) {
                    paymentOrder.setWebhookInvalid(true);
                }

                if (map != null && map.get(Constants.CURRENCY) != null) {
                    paymentOrder.setCurrencyInvalid(true);
                }
                throw new InvalidPaymentOrderException();
            }
            createPaymentOrderResponse.setJsonResponse(response);
            return createPaymentOrderResponse;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new ConnectionException(e.toString(), e);
        }
    }

    @Override
    public PaymentOrderDetailsResponse getPaymentOrderDetails(String id) throws ConnectionException {
        Asserts.notEmpty(id, "Order Id");

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.AUTHORIZATION, context.getAuthorization());

        try {
            String response = HttpUtils.sendGetRequest(
                    context.getApiPath(Constants.PAYMENT_ORDER_API_PATH + "id:" + id + "/"), headers,
                    null);
            PaymentOrderDetailsResponse paymentOrderDetailsResponse = JsonUtils.convertJsonStringToObject(response,
                    PaymentOrderDetailsResponse.class);

            paymentOrderDetailsResponse.setJsonResponse(response);
            return paymentOrderDetailsResponse;
        } catch (IOException | URISyntaxException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new ConnectionException(e.toString(), e);
        }
    }

    @Override
    public PaymentOrderDetailsResponse getPaymentOrderDetailsByTransactionId(String transactionId) throws ConnectionException {
        Asserts.notEmpty(transactionId, "Transaction Id");

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.AUTHORIZATION, context.getAuthorization());

        try {
            String response = HttpUtils.sendGetRequest(
                    context.getApiPath(Constants.PAYMENT_ORDER_API_PATH + "transaction_id:" + transactionId + "/"),
                    headers, null);
            PaymentOrderDetailsResponse paymentOrderDetailsResponse = JsonUtils.convertJsonStringToObject(response,
                    PaymentOrderDetailsResponse.class);
            paymentOrderDetailsResponse.setJsonResponse(response);
            return paymentOrderDetailsResponse;
        } catch (IOException | URISyntaxException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new ConnectionException(e.toString(), e);
        }
    }

    @Override
    public PaymentOrderListResponse getPaymentOrderList(PaymentOrderFilter paymentOrderFilter) throws ConnectionException {
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
            String response = HttpUtils.sendGetRequest(context.getApiPath(Constants.PAYMENT_ORDER_API_PATH), headers,
                    params);
            PaymentOrderListResponse paymentOrderListResponse = JsonUtils.convertJsonStringToObject(response,
                    PaymentOrderListResponse.class);
            paymentOrderListResponse.setJsonResponse(response);
            return paymentOrderListResponse;
        } catch (IOException | URISyntaxException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new ConnectionException(e.toString(), e);
        }
    }

    @Override
    public CreateRefundResponse createRefund(Refund refund) throws ConnectionException, InvalidRefundException {
        Asserts.notNull(refund, "Refund");

        boolean isValid = refund.validate();

        if (!isValid) {
            throw new InvalidRefundException();
        }

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.AUTHORIZATION, context.getAuthorization());

        Map<String, String> params = new HashMap<>();
        params.put("payment_id", refund.getPaymentId());
        params.put("type", refund.getType());
        params.put("body", refund.getBody());
        params.put("refund_amount", String.valueOf(refund.getRefundAmount()));

        try {
            String response = HttpUtils.sendPostRequest(
                    context.getApiPath(Constants.REFUND_API_PATH + refund.getPaymentId() + "/refund/"), headers, params);
            CreateRefundResponse createRefundResponse = JsonUtils.convertJsonStringToObject(response,
                    CreateRefundResponse.class);

            if (createRefundResponse.getRefund() == null) {
                Map<String, Object> map = JsonUtils.convertJsonStringToMap(response);
                if (map != null && map.get(Constants.TYPE) != null) {
                    refund.setTypeInvalid(true);
                }
                throw new InvalidRefundException();
            }
            createRefundResponse.setJsonResponse(response);
            return createRefundResponse;
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
}
