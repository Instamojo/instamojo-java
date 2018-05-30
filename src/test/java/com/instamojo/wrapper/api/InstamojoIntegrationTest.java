package com.instamojo.wrapper.api;

import com.instamojo.wrapper.builder.PaymentOrderBuilder;
import com.instamojo.wrapper.builder.RefundBuilder;
import com.instamojo.wrapper.exception.HTTPException;
import com.instamojo.wrapper.model.*;
import com.instamojo.wrapper.util.TestConstants;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.instamojo.wrapper.api.ApiContext.Mode;
import static org.assertj.core.api.Assertions.assertThat;

public class InstamojoIntegrationTest {

    private Instamojo api;

    @Before
    public void setUp() {
        ApiContext.ClearInstance();
        ApiContext context = ApiContext.create(TestConstants.TEST_CLIENT_ID, TestConstants.TEST_CLIENT_SECRET, Mode.TEST);
        api = new InstamojoImpl(context);
    }

    @Test
    public void createPaymentOrder_ValidateInternationalPhone() {
        PaymentOrder order = new PaymentOrderBuilder().withPhone("+14155552671").build();
        assertThat(order).isNotNull();
    }

    @Test
    public void createPaymentOrder_whenNewPaymentOrderIsMade_shouldCreateNewPaymentOrder() throws Exception {
        PaymentOrder order = new PaymentOrderBuilder().build();

        PaymentOrderResponse paymentOrderResponse = api.createPaymentOrder(order);

        assertThat(paymentOrderResponse).isNotNull();
        assertThat(paymentOrderResponse.getPaymentOrder().getId()).isNotEmpty();
        assertThat(paymentOrderResponse.getPaymentOrder().getTransactionId()).isEqualTo(order.getTransactionId());
    }

    @Test(expected = HTTPException.class)
    public void createOrder_whenWebhookIsInvalid_shouldThrowException() throws Exception {
        PaymentOrder order = new PaymentOrderBuilder()
                .withWebhookUrl("invalid_url")
                .build();
        api.createPaymentOrder(order);
    }

    @Test
    public void createOrder_whenWebhookIsNull_shouldCreateNewOrder() throws Exception {
        PaymentOrder order = new PaymentOrderBuilder()
                .withWebhookUrl(null)
                .build();

        api.createPaymentOrder(order);
        assertThat(order.getWebhookUrl()).isNull();
    }

    @Test(expected = HTTPException.class)
    public void createPaymentOrder_whenInvalidPaymentOrderIsMade_shouldThrowException() throws Exception {
        PaymentOrder order = new PaymentOrderBuilder()
                .withEmail(null)
                .withAmount(7D)
                .build();

        api.createPaymentOrder(order);
    }

    @Test(expected = HTTPException.class)
    public void createPaymentOrder_whenSameTransactionIdIsGiven_shouldThrowException() throws Exception {
        PaymentOrder order = new PaymentOrderBuilder().build();

        api.createPaymentOrder(order);
        PaymentOrderResponse duplicateOrder = api.createPaymentOrder(order);

        assertThat(duplicateOrder).isNotNull();
        assertThat(duplicateOrder.getPaymentOrder()).isNull();
    }

    @Test(expected = HTTPException.class)
    public void createPaymentOrder_whenUnsupportedCurrencyIsGiven_shouldThrowException() throws Exception {
        PaymentOrder order = new PaymentOrderBuilder()
                .withCurrency("unsupported currency")
                .build();

        PaymentOrderResponse paymentOrderResponse = api.createPaymentOrder(order);

        assertThat(paymentOrderResponse).isNotNull();
        assertThat(paymentOrderResponse.getPaymentOrder()).isNull();
    }

    @Test
    public void getPaymentOrderDetails_whenPaymentOrderIdIsGiven_shouldReturnDetailsOfPaymentOrder() throws Exception {
        PaymentOrder order = new PaymentOrderBuilder().build();

        PaymentOrderResponse paymentOrderResponse = api.createPaymentOrder(order);
        String paymentOrderId = paymentOrderResponse.getPaymentOrder().getId();

        PaymentOrder paymentOrderDetailsResponse = api.getPaymentOrder(paymentOrderId);

        assertThat(paymentOrderDetailsResponse).isNotNull();
        assertThat(paymentOrderDetailsResponse.getId()).isEqualTo(paymentOrderId);
        assertThat(paymentOrderDetailsResponse.getTransactionId()).isEqualTo(order.getTransactionId());
    }

    @Test
    public void getPaymentOrderDetails_whenTransactionIdIsGiven_shouldReturnDetailsOfPaymentOrder() throws Exception {
        String transactionId = UUID.randomUUID().toString();
        PaymentOrder order = new PaymentOrderBuilder().withTransactionId(transactionId).build();

        PaymentOrderResponse paymentOrderResponse = api.createPaymentOrder(order);
        PaymentOrder paymentOrderDetailsResponse = api.getPaymentOrderByTransactionId(transactionId);

        assertThat(paymentOrderDetailsResponse).isNotNull();
        assertThat(paymentOrderDetailsResponse.getTransactionId()).isEqualTo(transactionId);
        assertThat(paymentOrderDetailsResponse.getId()).isEqualTo(paymentOrderResponse.getPaymentOrder().getId());
    }

    @Test
    public void getPaymentOrderList_shouldReturnListOfAllPaymentOrders() throws Exception {
        PaymentOrderFilter paymentOrderFilter = new PaymentOrderFilter();
        paymentOrderFilter.setLimit(2);

        List<PaymentOrder> paymentOrderListResponse = api.getPaymentOrders(paymentOrderFilter);

        for (PaymentOrder paymentOrder : paymentOrderListResponse) {
            System.out.println(paymentOrder);
        }

        assertThat(paymentOrderListResponse).isNotNull();
    }

    /**
     * In order to run this test, you need to replace this payment id with the new payment id,
     * as the request using same payment id fails
     */
    @Test
    @Ignore
    public void createRefund_whenNewRefundIsMade_shouldCreateNewRefund() throws Exception {
        String paymentId = "MOJO6701005J41260320";

        Refund refund = new RefundBuilder().withPaymentId(paymentId).build();
        Refund createRefundResponse = api.createRefund(refund);

        assertThat(createRefundResponse).isNotNull();
        assertThat(createRefundResponse).isNotNull();
        assertThat(createRefundResponse.getPaymentId()).isNotEmpty();
    }

    @Test(expected = HTTPException.class)
    public void createRefund_whenInvalidRefundIsMade_shouldThrowException() throws Exception {
        Refund refund = new RefundBuilder()
                .withRefundAmount(7D)
                .withBody(null)
                .build();

        api.createRefund(refund);
    }

    @Test(expected = HTTPException.class)
    public void createRefund_whenUnsupportedTypeIsGiven_shouldThrowException() throws Exception {
        Refund refund = new RefundBuilder()
                .withType("Unsupported Type")
                .build();

        Refund createRefundResponse = api.createRefund(refund);
    }

    @Test
    public void createWebhookSignature() {
        Map<String, String> map = new HashMap<>();
        map.put("foo", "1");
        map.put("bar", "2");
        map.put("baz", "3");
        String signature = api.generateWebhookSignature(map, TestConstants.TEST_CLIENT_SALT);
        assertThat(signature).isEqualTo("a0d60f15eb94cd332bc93edc379bb248b298182a");
    }

    @Test
    public void getInvoiceList_shouldReturnListOfAllPaymentOrders() throws Exception {

        List<Invoice> invoices = api.getInvoices();
        for (Invoice invoice : invoices) {
            System.out.println(invoice);
        }
    }

    @Test
    public void getPayoutList() throws Exception {

        List<Payout> payouts = api.getPayouts();
        for (Payout payout : payouts) {
            System.out.println(payout);
        }
    }

    @Test(expected = HTTPException.class)
    public void getPayoutById() throws Exception {
        Payout payout = api.getPayout("asdasdasdasdasd");
    }

    @Test
    public void createPaymentRequest() throws Exception {

        PaymentRequest rap = new PaymentRequest();
        rap.setAmount(10.0);
        rap.setEmail("vijith@instamojo.com");
        rap.setPurpose("testing rap");

        PaymentRequest createdRap = api.createPaymentRequest(rap);
        assertThat(createdRap).isNotNull();
    }

    @Test
    public void getPaymentRequests() throws Exception {

        List<PaymentRequest> raps = api.getPaymentRequests();
        assertThat(raps).isNotNull();
    }

    @Test
    public void getPaymentRequest() throws Exception {
        PaymentRequest rap = new PaymentRequest();
        rap.setAmount(10.0);
        rap.setEmail("vijith@instamojo.com");
        rap.setPurpose("testing rap");

        PaymentRequest createdRap = api.createPaymentRequest(rap);

        PaymentRequest retrievedRap = api.getPaymentRequest(createdRap.getId());
        assertThat(retrievedRap).isNotNull();
    }

    @Test
    public void enablePaymentRequest() throws Exception {
        PaymentRequest rap = new PaymentRequest();
        rap.setAmount(10.0);
        rap.setEmail("vijith@instamojo.com");
        rap.setPurpose("testing rap");

        PaymentRequest createdRap = api.createPaymentRequest(rap);

        Boolean success = api.enablePaymentRequest(createdRap.getId());
        assertThat(success).isTrue();
    }

    @Test
    public void disablePaymentRequest() throws Exception {
        PaymentRequest rap = new PaymentRequest();
        rap.setAmount(10.0);
        rap.setEmail("vijith@instamojo.com");
        rap.setPurpose("testing rap");

        PaymentRequest createdRap = api.createPaymentRequest(rap);

        Boolean success = api.disablePaymentRequest(createdRap.getId());
        assertThat(success).isTrue();
    }
}