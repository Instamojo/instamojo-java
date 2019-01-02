package com.instamojo.wrapper.model;

import com.google.gson.Gson;
import com.instamojo.wrapper.api.ApiContext;
import com.instamojo.wrapper.exception.ConnectionException;
import com.instamojo.wrapper.exception.HTTPException;
import com.instamojo.wrapper.util.Constants;
import com.instamojo.wrapper.util.HttpUtils;
import com.instamojo.wrapper.util.TestConstants;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class PaymentRequestTest {

    private String PAYMENT_REQUEST_ENDPOINT;
    private Map<String, String> headers = new HashMap<>();
    private Gson gson = new Gson();
    private ApiContext context;

    private PaymentRequest paymentRequest = new PaymentRequest();

    @Before
    public void setUp() {

        ApiContext.ClearInstance();
        context = ApiContext.create(
                TestConstants.CLIENT_ID, TestConstants.CLIENT_SECRET,
                TestConstants.USERNAME, TestConstants.PASSWORD, ApiContext.Mode.TEST
        );

        PAYMENT_REQUEST_ENDPOINT = context.getApiPath(Constants.PATH_PAYMENT_REQUEST);
    }

    @Test
    public void testPaymentRequestCreation() throws IOException, HTTPException, ConnectionException {

        paymentRequest.setAmount(new Double(500));
        paymentRequest.setPurpose("FIFA 19");
        paymentRequest.setBuyerName("John Doe");
        paymentRequest.setEmail("johndoe@example.com");
        paymentRequest.setPhone("9999999999");
        paymentRequest.setRedirectUrl("http://www.example.com/redirect/");
        paymentRequest.setSendEmail(false);
        paymentRequest.setSendSms(false);
        paymentRequest.setWebhookUrl("http://www.example.com/webhook/");
        paymentRequest.setAllowRepeatedPayments(false);

        headers.put(Constants.HEADER_AUTHORIZATION, context.getAuthorization());

        String response = HttpUtils.post(PAYMENT_REQUEST_ENDPOINT, headers, gson.toJson(paymentRequest));
        paymentRequest = gson.fromJson(response, PaymentRequest.class);

        assertNotNull(paymentRequest.getId());
        assertNotNull(paymentRequest.getCreatedAt());
        assertNotNull(paymentRequest.getModifiedAt());
        assertEquals(paymentRequest.getResourceUri(), "https://test.instamojo.com/v2/payment_requests/"+paymentRequest.getId()+"/");
        assertEquals(paymentRequest.getPhone(), "+919999999999");
        assertEquals(paymentRequest.getBuyerName(), "John Doe");
        assertEquals(paymentRequest.getEmail(), "johndoe@example.com");
        assertEquals(paymentRequest.getAmount(), new Double(500));
        assertEquals(paymentRequest.getPurpose(), "FIFA 19");
        assertEquals(paymentRequest.getStatus(), "Pending");
        assertEquals(paymentRequest.getSmsStatus(), null);
        assertEquals(paymentRequest.getEmailStatus(), null);
        assertEquals(paymentRequest.getWebhookUrl(), "http://www.example.com/webhook/");
        assertEquals(paymentRequest.getAllowRepeatedPayments(), false);

    }

}
