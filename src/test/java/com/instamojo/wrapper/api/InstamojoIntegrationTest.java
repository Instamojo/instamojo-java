package com.instamojo.wrapper.api;

import com.instamojo.wrapper.builder.PaymentOrderBuilder;
import com.instamojo.wrapper.builder.RefundBuilder;
import com.instamojo.wrapper.exception.InvalidPaymentOrderException;
import com.instamojo.wrapper.exception.InvalidRefundException;
import com.instamojo.wrapper.model.PaymentOrder;
import com.instamojo.wrapper.model.PaymentOrderFilter;
import com.instamojo.wrapper.model.Refund;
import com.instamojo.wrapper.response.CreatePaymentOrderResponse;
import com.instamojo.wrapper.response.CreateRefundResponse;
import com.instamojo.wrapper.response.PaymentOrderDetailsResponse;
import com.instamojo.wrapper.response.PaymentOrderListResponse;
import com.instamojo.wrapper.util.TestConstants;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
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
        assertThat(order.validate()).isTrue();
        assertThat(order.isPhoneInvalid()).isFalse();
    }

    @Test
    public void createPaymentOrder_whenNewPaymentOrderIsMade_shouldCreateNewPaymentOrder() throws Exception {
        PaymentOrder order = new PaymentOrderBuilder().build();

        CreatePaymentOrderResponse createPaymentOrderResponse = api.createPaymentOrder(order);

        assertThat(createPaymentOrderResponse).isNotNull();
        assertThat(createPaymentOrderResponse.getPaymentOrder().getId()).isNotEmpty();
        assertThat(createPaymentOrderResponse.getPaymentOrder().getTransactionId()).isEqualTo(order.getTransactionId());
    }

    @Test(expected = InvalidPaymentOrderException.class)
    public void createOrder_whenWebhookIsInvalid_shouldThrowException() throws Exception{
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

    @Test(expected = InvalidPaymentOrderException.class)
    public void createPaymentOrder_whenInvalidPaymentOrderIsMade_shouldThrowException() throws Exception {
        PaymentOrder order = new PaymentOrderBuilder()
                .withEmail(null)
                .withAmount(7D)
                .build();

        api.createPaymentOrder(order);
    }

    @Test(expected = InvalidPaymentOrderException.class)
    public void createPaymentOrder_whenSameTransactionIdIsGiven_shouldThrowException() throws Exception {
        PaymentOrder order = new PaymentOrderBuilder().build();

        api.createPaymentOrder(order);
        CreatePaymentOrderResponse duplicateOrder = api.createPaymentOrder(order);

        assertThat(duplicateOrder).isNotNull();
        assertThat(duplicateOrder.getPaymentOrder()).isNull();
    }

    @Test(expected = InvalidPaymentOrderException.class)
    public void createPaymentOrder_whenUnsupportedCurrencyIsGiven_shouldThrowException() throws Exception {
        PaymentOrder order = new PaymentOrderBuilder()
                .withCurrency("unsupported currency")
                .build();

        CreatePaymentOrderResponse createPaymentOrderResponse = api.createPaymentOrder(order);

        assertThat(createPaymentOrderResponse).isNotNull();
        assertThat(createPaymentOrderResponse.getPaymentOrder()).isNull();
    }

    @Test
    public void getPaymentOrderDetails_whenPaymentOrderIdIsGiven_shouldReturnDetailsOfPaymentOrder() throws Exception {
        PaymentOrder order = new PaymentOrderBuilder().build();

        CreatePaymentOrderResponse createPaymentOrderResponse = api.createPaymentOrder(order);
        String paymentOrderId = createPaymentOrderResponse.getPaymentOrder().getId();

        PaymentOrderDetailsResponse paymentOrderDetailsResponse = api.getPaymentOrderDetails(paymentOrderId);

        assertThat(paymentOrderDetailsResponse).isNotNull();
        assertThat(paymentOrderDetailsResponse.getId()).isEqualTo(paymentOrderId);
        assertThat(paymentOrderDetailsResponse.getTransactionId()).isEqualTo(order.getTransactionId());
    }

    @Test
    public void getPaymentOrderDetails_whenTransactionIdIsGiven_shouldReturnDetailsOfPaymentOrder() throws Exception {
        String transactionId = UUID.randomUUID().toString();
        PaymentOrder order = new PaymentOrderBuilder().withTransactionId(transactionId).build();

        CreatePaymentOrderResponse createPaymentOrderResponse = api.createPaymentOrder(order);
        PaymentOrderDetailsResponse paymentOrderDetailsResponse = api.getPaymentOrderDetailsByTransactionId(transactionId);

        assertThat(paymentOrderDetailsResponse).isNotNull();
        assertThat(paymentOrderDetailsResponse.getTransactionId()).isEqualTo(transactionId);
        assertThat(paymentOrderDetailsResponse.getId()).isEqualTo(createPaymentOrderResponse.getPaymentOrder().getId());
    }

    @Test
    public void getPaymentOrderList_shouldReturnListOfAllPaymentOrders() throws Exception {
        PaymentOrderFilter paymentOrderFilter = new PaymentOrderFilter();
        paymentOrderFilter.setLimit(2);

        PaymentOrderListResponse paymentOrderListResponse = api.getPaymentOrderList(paymentOrderFilter);

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
        CreateRefundResponse createRefundResponse = api.createRefund(refund);

        assertThat(createRefundResponse).isNotNull();
        assertThat(createRefundResponse.getRefund()).isNotNull();
        assertThat(createRefundResponse.getRefund().getPaymentId()).isNotEmpty();
    }

    @Test(expected = InvalidRefundException.class)
    public void createRefund_whenInvalidRefundIsMade_shouldThrowException() throws Exception {
        Refund refund = new RefundBuilder()
                .withRefundAmount(7D)
                .withBody(null)
                .build();

        api.createRefund(refund);
    }

    @Test(expected = InvalidRefundException.class)
    public void createRefund_whenUnsupportedTypeIsGiven_shouldThrowException() throws Exception {
        Refund refund = new RefundBuilder()
                .withType("Unsupported Type")
                .build();

        CreateRefundResponse createRefundResponse = api.createRefund(refund);

        assertThat(createRefundResponse).isNotNull();
        assertThat(createRefundResponse.getRefund()).isNull();
    }

    @Test
    public void createWebhookSignature() {
        Map<String, String> map = new HashMap<>();
        map.put("foo", "1");
        map.put("bar", "2");
        map.put("baz", "3");
        String signature = api.createWebhookSignature(map, TestConstants.TEST_CLIENT_SALT);
        assertThat(signature).isEqualTo("a0d60f15eb94cd332bc93edc379bb248b298182a");
    }
}