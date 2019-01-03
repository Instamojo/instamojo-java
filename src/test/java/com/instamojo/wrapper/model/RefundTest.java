package com.instamojo.wrapper.model;

import com.google.gson.Gson;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.*;

public class RefundTest {

    private static String apiResponse = " {\"payment_id\": \"MOJO5c04000J30502939\", \"status\": \"Refunded\", \"body\": \"Customer isn\\'t satisfied with the quality\", \"total_amount\": \"100.00\", \"refund_amount\": \"100\", \"created_at\": \"2015-12-07T11:01:37.640Z\", \"type\": \"QFL\", \"id\": \"C5c0751269\"} ";
    private static Gson gson = new Gson();

    @Test
    public void testPayoutsCreation() throws ParseException {

        Refund refund = gson.fromJson(apiResponse, Refund.class);

        assertEquals(refund.getId(), "C5c0751269");
        assertEquals(refund.getPaymentId(), "MOJO5c04000J30502939");

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date actualCreatedAt = df.parse("2015-12-07T11:01:37.640Z");

        assertEquals(refund.getCreatedAt(), actualCreatedAt);

        assertEquals(refund.getStatus(), "Refunded");
        assertEquals(refund.getType(), "QFL");
        assertEquals(refund.getBody(), "Customer isn't satisfied with the quality");
        assertEquals(refund.getRefundAmount(), new Double(100.0));
        assertEquals(refund.getTotalAmount(), new Double(100.0));

    }

}
