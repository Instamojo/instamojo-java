package com.instamojo.wrapper.model;

import com.google.gson.Gson;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.*;

public class InvoiceTest {

    private static String apiResponse = "{\"issue_date\": \"2018-10-31T18:29:59.999999Z\", \"created_at\": \"2018-11-01T20:00:03.883014Z\", \"last_modified\": \"2018-11-01T20:00:03.900308Z\", \"user\": \"https://staging.instamojo.com/v2/users/76dd6c8107034a3e971b70696a16aad3/\", \"file\": \"https://s3.amazonaws.com/path/to/file/\", \"id\": \"080661309/000005\"}";
    private Gson gson = new Gson();

    @Test
    public void testPayoutsCreation() throws ParseException {

        Invoice invoice = gson.fromJson(apiResponse, Invoice.class);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date actualCreatedAt = df.parse("2018-11-01T20:00:03.883Z");
        Date actualIssueDate = df.parse("2018-10-31T18:29:59.999Z");
        Date actualModifiedAt = df.parse("2018-11-01T20:00:03.900Z");

        assertEquals(invoice.getId(), "080661309/000005");
        assertEquals(invoice.getCreatedAt(), actualCreatedAt);
        assertEquals(invoice.getIssueDate(), actualIssueDate);
        assertEquals(invoice.getModifiedAt(), actualModifiedAt);
        assertEquals(invoice.getUserUri(), "https://staging.instamojo.com/v2/users/76dd6c8107034a3e971b70696a16aad3/");
        assertEquals(invoice.getFileUrl(), "https://s3.amazonaws.com/path/to/file/");

    }
}
