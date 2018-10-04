package com.instamojo.wrapper.util;

import com.instamojo.wrapper.exception.HTTPException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Http utils.
 */
public class HttpUtils {

    private static final Logger LOGGER = Logger.getLogger(HttpUtils.class.getName());

    /**
     * Instantiates a new Http utils.
     */
    private HttpUtils() {
    }

    public static String get(String url, Map<String, String> customHeaders) throws URISyntaxException, IOException, HTTPException {
        return get(url, customHeaders, null);
    }

    /**
     * Send get request.
     *
     * @param url           the url
     * @param customHeaders the customHeaders
     * @param params        the params
     * @return the string
     * @throws URISyntaxException the uri syntax exception
     * @throws IOException        the io exception
     */
    public static String get(String url, Map<String, String> customHeaders, Map<String, String> params) throws URISyntaxException, IOException, HTTPException {
        LOGGER.log(Level.INFO, "Sending GET request to the url {0}", url);

        URIBuilder uriBuilder = new URIBuilder(url);

        if (params != null && params.size() > 0) {
            for (Map.Entry<String, String> param : params.entrySet()) {
                uriBuilder.setParameter(param.getKey(), param.getValue());
            }
        }

        HttpGet httpGet = new HttpGet(uriBuilder.build());

        populateHeaders(httpGet, customHeaders);

        HttpClient httpClient = HttpClientBuilder.create().build();

        HttpResponse httpResponse = httpClient.execute(httpGet);

        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (isErrorStatus(statusCode)) {
            String jsonErrorResponse = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
            throw new HTTPException(statusCode, httpResponse.getStatusLine().getReasonPhrase(), jsonErrorResponse);
        }

        return EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
    }

    /**
     * Send post request.
     *
     * @param url           the url
     * @param customHeaders the customHeaders
     * @param params        the params
     * @return the string
     * @throws IOException the io exception
     */
    public static String post(String url, Map<String, String> customHeaders, Map<String, String> params) throws IOException, HTTPException {
        LOGGER.log(Level.INFO, "Sending POST request to the url {0}", url);

        HttpPost httpPost = new HttpPost(url);

        populateHeaders(httpPost, customHeaders);

        if (params != null && params.size() > 0) {
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            for (Map.Entry<String, String> param : params.entrySet()) {
                nameValuePairs.add(new BasicNameValuePair(param.getKey(), param.getValue()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        }

        HttpClient httpClient = HttpClientBuilder.create().build();

        HttpResponse httpResponse = httpClient.execute(httpPost);

        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (isErrorStatus(statusCode)) {
            String jsonErrorResponse = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
            throw new HTTPException(statusCode, httpResponse.getStatusLine().getReasonPhrase(), jsonErrorResponse);
        }

        return EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
    }

    public static String post(String url, Map<String, String> customHeaders) throws IOException, HTTPException {
        return post(url, customHeaders, "");
    }

    public static String post(String url, Map<String, String> customHeaders, String jsonPayload) throws IOException, HTTPException {
        LOGGER.log(Level.INFO, "Sending POST request to the url {0}", url);
        HttpPost httpPost = new HttpPost(url);

        customHeaders.put(Constants.HEADER_ACCEPT, "application/json");
        customHeaders.put(Constants.HEADER_CONTENT_TYPE, "application/json");

        populateHeaders(httpPost, customHeaders);

        if (jsonPayload != null || !jsonPayload.isEmpty()) {
            httpPost.setEntity(new StringEntity(jsonPayload));
        }

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse httpResponse = httpClient.execute(httpPost);

        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (isErrorStatus(statusCode)) {
            String jsonErrorResponse = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
            throw new HTTPException(statusCode, httpResponse.getStatusLine().getReasonPhrase(), jsonErrorResponse);
        }

        return EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
    }

    private static boolean isErrorStatus(int statusCode) {
        if (statusCode >= 400 && statusCode < 600) {
            return true;
        }

        return false;
    }

    private static void populateHeaders(HttpRequestBase httpRequestBase, Map<String, String> customHeaders) {
        // Adding default headers
        httpRequestBase.addHeader("User-Agent", "instamojo-java");

        if (customHeaders != null && customHeaders.size() > 0) {
            for (Map.Entry<String, String> header : customHeaders.entrySet()) {
                httpRequestBase.addHeader(header.getKey(), header.getValue());
            }
        }
    }
}
