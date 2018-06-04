package com.instamojo.wrapper.api;

import com.instamojo.wrapper.builder.PaymentOrderBuilder;
import com.instamojo.wrapper.exception.HTTPException;
import com.instamojo.wrapper.model.*;
import com.instamojo.wrapper.util.TestConstants;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;
import java.util.List;
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
        ApiContext context = ApiContext.create(TestConstants.TEST_CLIENT_ID, TestConstants.TEST_CLIENT_SECRET, Mode.TEST);
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
        List<PaymentOrder> paymentOrders = api.getPaymentOrders(1, 2);

        assertNotNull(paymentOrders);
        assertEquals(2, paymentOrders.size());
    }

    @Test
    public void createWebhookSignature() {
        Map<String, String> map = new HashMap<>();
        map.put("foo", "1");
        map.put("bar", "2");
        map.put("baz", "3");
        String signature = api.generateWebhookSignature(map, TestConstants.TEST_CLIENT_SALT);
        assertEquals(signature, "a0d60f15eb94cd332bc93edc379bb248b298182a");
    }

    @Test
    public void getInvoicesByFilter() throws Exception {
        List<Invoice> invoices = api.getInvoices(1, 2);
        assertNotNull(invoices);
    }

    @Test
    public void getPayoutsByFilter() throws Exception {
        List<Payout> payouts = api.getPayouts(1, 2);
        assertNotNull(payouts);
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
        List<PaymentRequest> raps = api.getPaymentRequests(1, 2);
        assertNotNull(raps);
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