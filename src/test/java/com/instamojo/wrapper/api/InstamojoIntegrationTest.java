package com.instamojo.wrapper.api;

import com.instamojo.wrapper.builder.PaymentOrderBuilder;
import com.instamojo.wrapper.exception.HTTPException;
import com.instamojo.wrapper.filter.PaymentRequestFilter;
import com.instamojo.wrapper.model.PaymentOrder;
import com.instamojo.wrapper.model.PaymentOrderResponse;
import com.instamojo.wrapper.model.PaymentRequest;
import com.instamojo.wrapper.response.ApiListResponse;
import com.instamojo.wrapper.util.TestConstants;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.instamojo.wrapper.api.ApiContext.Mode;
import static org.junit.Assert.*;

public class InstamojoIntegrationTest {

    private Instamojo api;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        ApiContext.ClearInstance();
        ApiContext context = ApiContext.create(TestConstants.CLIENT_ID, TestConstants.CLIENT_SECRET, Mode.TEST);
        api = new InstamojoImpl(context);
    }

    @Test
    public void createValidPaymentOrder() throws Exception {
        PaymentOrder order = new PaymentOrderBuilder().build();
        PaymentOrderResponse paymentOrderResponse = api.createPaymentOrder(order);

        assertNotNull(paymentOrderResponse);
        assertNotNull(paymentOrderResponse.getPaymentOrder().getId());
        assertEquals(paymentOrderResponse.getPaymentOrder().getTransactionId(), order.getTransactionId());
    }

    @Test
    public void createPaymentOrder_InvalidWebhookUrl() throws Exception {
        expectedException.expect(HTTPException.class);
        expectedException.expectMessage("Bad Request");

        PaymentOrder paymentOrder = new PaymentOrderBuilder()
                .withWebhookUrl("invalid_url")
                .build();
        api.createPaymentOrder(paymentOrder);
    }

    @Test
    public void createPaymentOrder_NullWebhookUrl() throws Exception {
        PaymentOrder order = new PaymentOrderBuilder()
                .withWebhookUrl(null)
                .build();

        api.createPaymentOrder(order);
        assertNull(order.getWebhookUrl());
    }

    @Test
    public void createPaymentOrder_ExistingTransactionId() throws Exception {
        PaymentOrder order = new PaymentOrderBuilder().build();
        api.createPaymentOrder(order);

        expectedException.expect(HTTPException.class);
        expectedException.expectMessage("Bad Request");
        api.createPaymentOrder(order);
    }

    @Test
    public void getPaymentOrderById() throws Exception {
        PaymentOrder newPaymentOrder = new PaymentOrderBuilder().build();
        PaymentOrderResponse paymentOrderResponse = api.createPaymentOrder(newPaymentOrder);
        String paymentOrderId = paymentOrderResponse.getPaymentOrder().getId();

        PaymentOrder paymentOrder = api.getPaymentOrder(paymentOrderId);
        assertNotNull(paymentOrder);
        assertEquals(paymentOrder.getId(), paymentOrderId);
        assertEquals(paymentOrder.getTransactionId(), newPaymentOrder.getTransactionId());
    }

    @Test
    public void getPaymentOrderByTransactionId() throws Exception {
        String transactionId = UUID.randomUUID().toString();
        PaymentOrder newPaymentOrder = new PaymentOrderBuilder().withTransactionId(transactionId).build();
        PaymentOrderResponse paymentOrderResponse = api.createPaymentOrder(newPaymentOrder);

        PaymentOrder paymentOrder = api.getPaymentOrderByTransactionId(transactionId);
        assertNotNull(paymentOrder);
        assertEquals(paymentOrder.getTransactionId(), transactionId);
        assertEquals(paymentOrder.getId(), paymentOrderResponse.getPaymentOrder().getId());
    }

    @Test
    public void getPaymentOrdersByFilter() throws Exception {
        PaymentOrder order = new PaymentOrderBuilder().build();
        PaymentOrderResponse paymentOrderResponse = api.createPaymentOrder(order);
        ApiListResponse<PaymentOrder> paymentOrders = api.getPaymentOrders(1, 1);
        assertEquals(1, paymentOrders.getResults().size());
    }

    @Test
    public void createWebhookSignature() {
        Map<String, String> map = new HashMap<>();
        map.put("foo", "1");
        map.put("bar", "2");
        map.put("baz", "3");
        String signature = api.generateWebhookSignature(map, TestConstants.CLIENT_SALT);
        assertEquals(signature, "32b1d38a4a70bdce36a52dad3f3ac6eb3662096b");
    }

    @Test
    public void getPayoutById_InvalidId() throws Exception {
        expectedException.expect(HTTPException.class);
        expectedException.expectMessage("Not Found");

        api.getPayout("invalid_id");
    }

    @Test
    public void createValidPaymentRequest() throws Exception {
        PaymentRequest rap = new PaymentRequest();
        rap.setAmount(10.0);
        rap.setEmail("vijith@instamojo.com");
        rap.setPurpose("testing rap");

        PaymentRequest createdRap = api.createPaymentRequest(rap);
        assertNotNull(createdRap);
    }

    @Test
    public void getPaymentRequestsByFilter() throws Exception {
        PaymentRequest rap = new PaymentRequest();
        rap.setAmount(10.0);
        rap.setEmail("vijith@instamojo.com");
        rap.setPurpose("testing rap");
        rap.setPhone("+919999999999");
        api.createPaymentRequest(rap);
        Map<PaymentRequestFilter, String> filter = new HashMap<>();
        filter.put(PaymentRequestFilter.PHONE, "+919999999999");
        ApiListResponse<PaymentRequest> raps = api.getPaymentRequests(filter, 1, 1);
        assertEquals(1, raps.getResults().size());
        assertEquals(rap.getPhone(), raps.getResults().get(0).getPhone());
    }

    @Test
    public void getPaymentRequestById() throws Exception {
        PaymentRequest rap = new PaymentRequest();
        rap.setAmount(10.0);
        rap.setEmail("vijith@instamojo.com");
        rap.setPurpose("testing rap");

        PaymentRequest createdRap = api.createPaymentRequest(rap);

        PaymentRequest retrievedRap = api.getPaymentRequest(createdRap.getId());
        assertNotNull(retrievedRap);
    }

    @Test
    public void enablePaymentRequest() throws Exception {
        PaymentRequest rap = new PaymentRequest();
        rap.setAmount(10.0);
        rap.setEmail("vijith@instamojo.com");
        rap.setPurpose("testing rap");

        PaymentRequest createdRap = api.createPaymentRequest(rap);

        Boolean success = api.enablePaymentRequest(createdRap.getId());
        assertTrue(success);
    }

    @Test
    public void disablePaymentRequest() throws Exception {
        PaymentRequest rap = new PaymentRequest();
        rap.setAmount(10.0);
        rap.setEmail("vijith@instamojo.com");
        rap.setPurpose("testing rap");

        PaymentRequest createdRap = api.createPaymentRequest(rap);

        Boolean success = api.disablePaymentRequest(createdRap.getId());
        assertTrue(success);
    }
}