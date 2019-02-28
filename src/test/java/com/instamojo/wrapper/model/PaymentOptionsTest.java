package com.instamojo.wrapper.model;

import com.google.gson.Gson;
import com.instamojo.wrapper.api.ApiContext;
import com.instamojo.wrapper.builder.PaymentOrderBuilder;
import com.instamojo.wrapper.exception.ConnectionException;
import com.instamojo.wrapper.exception.HTTPException;
import com.instamojo.wrapper.util.Constants;
import com.instamojo.wrapper.util.GsonWrapper;
import com.instamojo.wrapper.util.HttpUtils;
import com.instamojo.wrapper.util.TestConstants;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;


public class PaymentOptionsTest {

    private String PAYMENT_ORDER_ENDPOINT;
    private Map<String, String> headers = new HashMap<>();
    private Gson gson = GsonWrapper.getGson();
    private ApiContext context;

    private PaymentOrderResponse orderResponse = new PaymentOrderResponse();

    @Before
    public void setUp() throws IOException, HTTPException, ConnectionException {

        ApiContext.ClearInstance();
        context = ApiContext.create(
            TestConstants.CLIENT_ID, TestConstants.CLIENT_SECRET,
            TestConstants.USERNAME, TestConstants.PASSWORD, ApiContext.Mode.TEST
        );

        PAYMENT_ORDER_ENDPOINT = context.getApiPath(Constants.PATH_PAYMENT_ORDER);
        PaymentOrder order = new PaymentOrderBuilder().build();

        headers.put(Constants.HEADER_AUTHORIZATION, context.getAuthorization());

        String response = HttpUtils.post(PAYMENT_ORDER_ENDPOINT, headers, gson.toJson(order));

        orderResponse = gson.fromJson(response, PaymentOrderResponse.class);

    }

    @Test
    public void testPaymentOrderModel() throws IOException {

        PaymentOptions options = orderResponse.getPaymentOptions();

        URL paymentUrl = new URL(options.getPaymentUrl());
        String expectedPaymentUrlPath = "/@" + TestConstants.USERNAME + "/" + orderResponse.getPaymentOrder().getId();

        assertEquals(expectedPaymentUrlPath, paymentUrl.getPath());

    }
}
