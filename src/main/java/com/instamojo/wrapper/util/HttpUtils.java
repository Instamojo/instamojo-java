package com.instamojo.wrapper.util;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
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

    /**
     * Send get request.
     *
     * @param url     the url
     * @param headers the headers
     * @param params  the params
     * @return the string
     * @throws URISyntaxException the uri syntax exception
     * @throws IOException        the io exception
     */
    public static String sendGetRequest(String url, Map<String, String> headers, Map<String, String> params) throws URISyntaxException, IOException {
        LOGGER.log(Level.INFO, "Sending GET request to the url " + url);

        URIBuilder uriBuilder = new URIBuilder(url);

        if (params != null && params.size() > 0) {
            for (Map.Entry<String, String> param : params.entrySet()) {
                uriBuilder.setParameter(param.getKey(), param.getValue());
            }
        }

        HttpGet httpGet = new HttpGet(uriBuilder.build());

        addHeadersToHttpRequest(httpGet, headers);

        HttpClient httpClient = HttpClientBuilder.create().build();

        HttpResponse httpResponse = httpClient.execute(httpGet);

        return getHttpResponseAsString(httpResponse);
    }

    /**
     * Send post request.
     *
     * @param url     the url
     * @param headers the headers
     * @param params  the params
     * @return the string
     * @throws IOException the io exception
     */
    public static String sendPostRequest(String url, Map<String, String> headers, Map<String, String> params) throws IOException {
        LOGGER.log(Level.INFO, "Sending POST request to the url " + url);

        HttpPost httpPost = new HttpPost(url);

        addHeadersToHttpRequest(httpPost, headers);

        if (params != null && params.size() > 0) {
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            for (Map.Entry<String, String> param : params.entrySet()) {
                nameValuePairs.add(new BasicNameValuePair(param.getKey(), param.getValue()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        }

        HttpClient httpClient = HttpClientBuilder.create().build();

        HttpResponse httpResponse = httpClient.execute(httpPost);

        return getHttpResponseAsString(httpResponse);
    }

    private static void addHeadersToHttpRequest(HttpRequestBase httpRequestBase, Map<String, String> headers) {
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                httpRequestBase.addHeader(header.getKey(), header.getValue());
            }
        }
    }

    private static String getHttpResponseAsString(HttpResponse httpResponse) {
        StringBuilder stringResponse = new StringBuilder();
        try {
            Reader reader = new InputStreamReader(httpResponse.getEntity().getContent(),
                    Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(reader);
            String output;
            while ((output = br.readLine()) != null) {
                stringResponse.append(output);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        return stringResponse.toString();
    }
}
