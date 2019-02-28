package com.instamojo.wrapper.model;

import com.google.gson.Gson;
import com.instamojo.wrapper.util.DateTimeUtils;
import com.instamojo.wrapper.util.GsonWrapper;
import org.joda.time.DateTime;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;

import static org.junit.Assert.*;

public class PayoutsTest {

    private static String apiResponse = " {\"status\": true, \"paid_out_at\": \"2018-11-26T09:46:26.458516Z\", \"total_tax_amount\": \"2.30\", \"paid_amount\": \"168.04\", \"fee_amount\": \"12.66\", \"partner_commission_amount\": \"0.00\", \"refunded_amount\": \"0.00\", \"currency\": \"INR\", \"resource_uri\": \"https://staging.instamojo.com/v2/payouts/MOJO8b01001M01126000/\", \"shipping_fee\": \"0.00\", \"affiliate_commission_amount\": \"0.00\", \"reversed_amount\": \"0.00\", \"held_amount\": \"0.00\", \"recipient\": \"https://staging.instamojo.com/v2/users/d6e15a20d0994c0db67e3798c14ac840/\", \"id\": \"MOJO8b01001M01126000\", \"payout_type\": 0, \"sales_amount\": \"183.00\"} ";
    private static Gson gson = GsonWrapper.getGson();

    @Test
    public void testPayoutsCreation() throws MalformedURLException, ParseException {

        Payout payout = gson.fromJson(apiResponse, Payout.class);

        DateTime expectedPaidOutAt = DateTimeUtils.parseISODateTimeString("2018-11-26T09:46:26.458516Z");

        assertNotNull(payout.getRecipient());

        assertEquals(payout.getCurrency(), "INR");
        assertEquals(payout.getSalesAmount(), new Double(183.00));
        assertEquals(payout.getPaidAmount(), new Double(168.04));
        assertEquals(payout.getFeeAmount(), new Double(12.66));
        assertEquals(payout.getHeldAmount(), new Double(0.0));
        assertEquals(payout.getTotalTaxAmount(), new Double(2.30));
        assertEquals(expectedPaidOutAt, payout.getPaidOutAt());

        assertEquals(payout.getRefundedAmount(), new Double(0.0));
        assertEquals(payout.getReversedAmount(), new Double(0.0));
        assertEquals(payout.getAffiliateCommissionAmount(), new Double(0.0));
        assertEquals(payout.getPartnerCommissionAmount(), new Double(0.0));

        String actualResourceUri = "/v2/payouts/" + payout.getId() + "/";
        URL resourceUri = new URL(payout.getResourceUri());

        assertEquals(resourceUri.getPath(), actualResourceUri);

        assertTrue(payout.getStatus());

    }
}
