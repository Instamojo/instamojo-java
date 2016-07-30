package com.instamojo.wrapper.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * The type Json utils.
 */
public class JsonUtils {

    private JsonUtils(){

    }

    /**
     * Convert json string to object t.
     *
     * @param <T>   the type parameter
     * @param json  the json
     * @param clazz the clazz
     * @return the t
     */
    public static <T> T convertJsonStringToObject(String json, Class<T> clazz) {
        return new Gson().fromJson(json, clazz);
    }

    /**
     * Convert json string to map map.
     *
     * @param <K>  the type parameter
     * @param <V>  the type parameter
     * @param json the json
     * @return the map
     */
    public static <K, V> Map<K, V> convertJsonStringToMap(String json) {
        Type type = new TypeToken<Map<K, V>>(){}.getType();
        return new Gson().fromJson(json, type);
    }
}
