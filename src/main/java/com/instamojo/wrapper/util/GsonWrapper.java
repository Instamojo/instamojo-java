package com.instamojo.wrapper.util;

import com.google.gson.*;
import org.joda.time.DateTime;

import java.lang.reflect.Type;


final class DateTimeDeserializer implements JsonDeserializer<DateTime> {

    @Override
    public DateTime deserialize(JsonElement value, Type type, JsonDeserializationContext context) throws JsonParseException {
        return value == null ? null : DateTimeUtils.parseISODateTimeString(value.getAsString());
    }
}

final class DateTimeSerializer implements JsonSerializer<DateTime> {

    @Override
    public JsonElement serialize(DateTime dt, Type type, JsonSerializationContext context) throws JsonParseException {
        return dt == null ? null : new JsonPrimitive(dt.toString());
    }

}


public class GsonWrapper {

    public static Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(DateTime.class, new DateTimeDeserializer())
                .registerTypeAdapter(DateTime.class, new DateTimeSerializer())
                .create();
    }

}
