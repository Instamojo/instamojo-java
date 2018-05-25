package com.instamojo.wrapper.api;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.instamojo.wrapper.exception.ConnectionException;
import com.instamojo.wrapper.exception.InvalidClientException;
import com.instamojo.wrapper.exception.InvalidPaymentOrderException;
import com.instamojo.wrapper.exception.InvalidRefundException;
import com.instamojo.wrapper.model.PaymentOrder;
import com.instamojo.wrapper.model.PaymentOrderFilter;
import com.instamojo.wrapper.model.Refund;
import com.instamojo.wrapper.response.AccessTokenResponse;
import com.instamojo.wrapper.response.CreatePaymentOrderResponse;
import com.instamojo.wrapper.response.CreateRefundResponse;
import com.instamojo.wrapper.response.PaymentOrderDetailsResponse;
import com.instamojo.wrapper.response.PaymentOrderListResponse;
import com.instamojo.wrapper.util.Constants;
import com.instamojo.wrapper.util.HttpUtils;
import com.instamojo.wrapper.util.JsonUtils;
import org.apache.http.util.Asserts;
import org.apache.http.util.TextUtils;

/**
 * The Class InstamojoImpl.
 */
public class InstamojoImpl implements Instamojo {

    /*
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = Logger.getLogger(InstamojoImpl.class.getName());

    public enum Mode {
        TEST, LIVE
    }

    private volatile static Instamojo instance;

    /*
     * The access token related info
     */
    private AccessTokenResponse accessToken;

    /*
     * The token creation time
     */
    private long tokenCreationTime;

    /*
     * Instamojo client id
     */
    private String clientId;

    /*
     * The client secret
     */
    private String clientSecret;

    private Mode mode;

    private InstamojoImpl() {
        // Default private constructor
    }

    /*
     * Instantiates a new instamojo impl.
     */
    private InstamojoImpl(String clientId, String clientSecret, Mode mode) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.mode = mode;
    }

    /**
     * Gets api.
     *
     * @param clientId     the client id
     * @param clientSecret the client secret
     * @return the api
     */
    public static Instamojo getApi(String clientId, String clientSecret, Mode mode) {
        if (instance == null) {
            synchronized (InstamojoImpl.class) {
                if (instance == null) {
                    instance = new InstamojoImpl(clientId, clientSecret, mode);
                }
            }
        }

        return instance;
    }

    private boolean isTokenExpired() {
        long durationInSeconds = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - tokenCreationTime);
        return durationInSeconds >= (accessToken.getExpiresIn() - 300);
    }

    private void loadOrRefreshAccessToken() throws ConnectionException {
        if (accessToken == null) {
            fetchAccessToken();

        } else if (isTokenExpired()) {
            refreshAccessToken();
        }
    }

    /*
     * Fetch a new access token.
     */
    private synchronized void fetchAccessToken() throws ConnectionException {
        if (accessToken != null) {
            return;
        }

        Map<String, String> params = new HashMap<>();
        params.put(Constants.CLIENT_ID, clientId);
        params.put(Constants.CLIENT_SECRET, clientSecret);
        params.put(Constants.GRANT_TYPE, Constants.CLIENT_CREDENTIALS);

        loadAccessToken(params);
    }

    /*
     * Refresh an expired access token
     */
    private synchronized void refreshAccessToken() throws ConnectionException {
        if (!isTokenExpired()) {
            return;
        }

        Map<String, String> params = new HashMap<>();
        params.put(Constants.CLIENT_ID, clientId);
        params.put(Constants.CLIENT_SECRET, clientSecret);
        params.put(Constants.GRANT_TYPE, Constants.GRAND_REFRESH_TOKEN);
        params.put(Constants.REFRESH_TOKEN, accessToken.getRefreshToken());

        loadAccessToken(params);
    }

    private void loadAccessToken(Map<String, String> params) throws ConnectionException {
        try {
            String response = HttpUtils.sendPostRequest(getAuthEndpoint(), null, params);

            AccessTokenResponse accessTokenResponse = JsonUtils.convertJsonStringToObject(response,
                    AccessTokenResponse.class);

            if (TextUtils.isEmpty(accessTokenResponse.getToken())) {
                throw new InvalidClientException(
                        "Could not get the access token due to " + accessTokenResponse.getError());
            }

            this.accessToken = accessTokenResponse;
            this.accessToken.setJsonResponse(response);
            this.tokenCreationTime = System.nanoTime();

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new ConnectionException(e.toString(), e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.instamojo.wrapper.api.Instamojo#createNewPaymentOrder(com.instamojo.
     * wrapper.model.PaymentOrder)
     */
    @Override
    public CreatePaymentOrderResponse createNewPaymentOrder(PaymentOrder paymentOrder)
            throws ConnectionException, InvalidPaymentOrderException {
        Asserts.notNull(paymentOrder, "Payment Order");

        boolean isValid = paymentOrder.validate();

        if (!isValid) {
            throw new InvalidPaymentOrderException();
        }

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.AUTHORIZATION, getAuthorization());
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
            String response = HttpUtils.sendPostRequest(this.getApiPath(Constants.PAYMENT_ORDER_API_PATH), headers,
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

    /*
     * (non-Javadoc)
     *
     * @see
     * com.instamojo.wrapper.api.Instamojo#getPaymentOrderDetails(java.lang.
     * String)
     */
    @Override
    public PaymentOrderDetailsResponse getPaymentOrderDetails(String id) throws ConnectionException {
        Asserts.notEmpty(id, "Order Id");

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.AUTHORIZATION, getAuthorization());

        try {
            String response = HttpUtils.sendGetRequest(
                    this.getApiPath(Constants.PAYMENT_ORDER_API_PATH + "id:" + id + "/"), headers,
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

    /*
     * (non-Javadoc)
     *
     * @see
     * com.instamojo.wrapper.api.Instamojo#getPaymentOrderDetailsByTransactionId
     * (java.lang.String)
     */
    @Override
    public PaymentOrderDetailsResponse getPaymentOrderDetailsByTransactionId(String transactionId)
            throws ConnectionException {
        Asserts.notEmpty(transactionId, "Transaction Id");

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.AUTHORIZATION, getAuthorization());

        try {
            String response = HttpUtils.sendGetRequest(
                    this.getApiPath(Constants.PAYMENT_ORDER_API_PATH + "transaction_id:" + transactionId + "/"),
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

    /*
     * (non-Javadoc)
     *
     * @see
     * com.instamojo.wrapper.api.Instamojo#getPaymentOrderList(com.instamojo.
     * wrapper.model.PaymentOrderFilter)
     */
    @Override
    public PaymentOrderListResponse getPaymentOrderList(PaymentOrderFilter paymentOrderFilter)
            throws ConnectionException {
        Asserts.notNull(paymentOrderFilter, "Payment Order Filter");

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.AUTHORIZATION, getAuthorization());
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
            String response = HttpUtils.sendGetRequest(this.getApiPath(Constants.PAYMENT_ORDER_API_PATH), headers,
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

    /* (non-Javadoc)
     * @see com.instamojo.wrapper.api.Instamojo#createNewRefund(com.instamojo.wrapper.model.Refund)
     */
    @Override
    public CreateRefundResponse createNewRefund(Refund refund) throws ConnectionException, InvalidRefundException {
        Asserts.notNull(refund, "Refund");

        boolean isValid = refund.validate();

        if (!isValid) {
            throw new InvalidRefundException();
        }

        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.AUTHORIZATION, getAuthorization());

        Map<String, String> params = new HashMap<>();
        params.put("payment_id", refund.getPaymentId());
        params.put("type", refund.getType());
        params.put("body", refund.getBody());
        params.put("refund_amount", String.valueOf(refund.getRefundAmount()));

        try {
            String response = HttpUtils.sendPostRequest(
                    this.getApiPath(Constants.REFUND_API_PATH + refund.getPaymentId() + "/refund/"), headers, params);
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

    /**
     * Clears the current cached Instance
     */
    public static void ClearInstance() {
        if (instance != null) {
            synchronized (InstamojoImpl.class) {
                instance = null;
            }
        }
    }

    /*
     * Gets the authorization.
     */
    private String getAuthorization() throws ConnectionException {
        loadOrRefreshAccessToken();
        return accessToken.getTokenType() + " " + accessToken.getToken();
    }

    /*
     * Gets the api path.
     */
    private String getApiPath(String path) {
        String apiPath = getApiEndpoint() + path;

        if (!apiPath.endsWith("/")) {
            apiPath += Character.toString('/');
        }
        return apiPath;
    }

    private String getApiEndpoint() {
        if (mode == Mode.TEST) {
            return Constants.INSTAMOJO_TEST_API_ENDPOINT;
        }

        return Constants.INSTAMOJO_LIVE_API_ENDPOINT;
    }

    private String getAuthEndpoint() {
        if (mode == Mode.TEST) {
            return Constants.INSTAMOJO_TEST_AUTH_ENDPOINT;
        }

        return Constants.INSTAMOJO_LIVE_AUTH_ENDPOINT;
    }
}
