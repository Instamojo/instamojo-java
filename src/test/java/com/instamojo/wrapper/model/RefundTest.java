package com.instamojo.wrapper.model;

import com.google.gson.Gson;
import com.instamojo.wrapper.util.DateTimeUtils;
import com.instamojo.wrapper.util.GsonWrapper;
import org.joda.time.DateTime;
import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;


public class RefundTest {

    private static String apiResponse = " {\"payment_id\": \"MOJO5c04000J30502939\", \"status\": \"Refunded\", \"body\": \"Customer isn\\'t satisfied with the quality\", \"total_amount\": \"100.00\", \"refund_amount\": \"100\", \"created_at\": \"2015-12-07T11:01:37.640Z\", \"type\": \"QFL\", \"id\": \"C5c0751269\"} ";
    private static Gson gson = GsonWrapper.getGson();

    @Test
    public void testPayoutsCreation() throws ParseException {

        Refund refund = gson.fromJson(apiResponse, Refund.class);

        assertEquals(refund.getId(), "C5c0751269");
        assertEquals(refund.getPaymentId(), "MOJO5c04000J30502939");

        DateTime excpectedCreatedAt = DateTimeUtils.parseISODateTimeString("2015-12-07T11:01:37.640Z");

        assertEquals(excpectedCreatedAt, refund.getCreatedAt());

        assertEquals("Refunded", refund.getStatus());
        assertEquals("QFL", refund.getType());
        assertEquals("Customer isn't satisfied with the quality", refund.getBody());
        assertEquals(new Double(100.0), refund.getRefundAmount());
        assertEquals(new Double(100.0), refund.getTotalAmount());

    }

}
