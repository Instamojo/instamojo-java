package com.instamojo.wrapper.api;

import com.instamojo.wrapper.builder.RefundBuilder;
import com.instamojo.wrapper.exception.HTTPException;
import com.instamojo.wrapper.model.Refund;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class InstamojoUnitTests {

    /**
     * In order to run this test, you need to replace this payment id with the new payment id,
     * as the request using same payment id fails
     */
//    @Test
//    public void createRefund_whenNewRefundIsMade_shouldCreateNewRefund() throws Exception {
//        String paymentId = "MOJO6701005J41260320";
//
//        CloseableHttpClient httpClient = mock(CloseableHttpClient.class);
//        HttpPost httpPost = mock(HttpPost.class);
//        CloseableHttpResponse httpResponse = mock(CloseableHttpResponse.class);
//        StatusLine statusLine = mock(StatusLine.class);
//        HttpEntity entity = new StringEntity("some json here");
//
//        //and:
//        when(statusLine.getStatusCode()).thenReturn(200);
//        when(httpResponse.getStatusLine()).thenReturn(statusLine);
//        when(httpResponse.getEntity()).thenReturn(entity);
//        when(httpClient.execute(httpPost)).thenReturn(httpResponse);
//
//        Refund refund = new RefundBuilder().withPaymentId(paymentId).build();
//        Refund createRefundResponse = api.createRefund(refund);
//
//        assertNotNull(createRefundResponse);
//        assertNotNull(createRefundResponse);
//        assertNotNull(createRefundResponse.getPaymentId());
//        assertFalse(createRefundResponse.getPaymentId().isEmpty());
//    }
//
//    @Test
//    @Ignore
//    public void createRefund_InvalidRefund() throws Exception {
//        expectedException.expect(HTTPException.class);
//        expectedException.expectMessage("Bad Request");
//        Refund refund = new RefundBuilder()
//                .withRefundAmount(7D)
//                .withBody(null)
//                .build();
//        api.createRefund(refund);
//    }
//
//    @Test
//    @Ignore
//    public void createRefund_UnsupportedType() throws Exception {
//        expectedException.expect(HTTPException.class);
//        expectedException.expectMessage("Bad Request");
//        Refund refund = new RefundBuilder()
//                .withType("Unsupported Type")
//                .build();
//        api.createRefund(refund);
//    }
}
