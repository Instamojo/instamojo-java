package com.instamojo.wrapper.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.ISODateTimeFormat;

public class DateTimeUtils {

    public static DateTime parseISODateTimeString(String datetime) {
        return ISODateTimeFormat.dateTime().withZone(DateTimeZone.UTC).parseDateTime(datetime);
    }

}
