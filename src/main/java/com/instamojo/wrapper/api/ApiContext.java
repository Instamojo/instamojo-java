package com.instamojo.wrapper.api;

import com.google.gson.Gson;
import com.instamojo.wrapper.exception.ConnectionException;
import com.instamojo.wrapper.exception.InvalidClientException;
import com.instamojo.wrapper.exception.InstamojoClientException;
import com.instamojo.wrapper.model.AccessToken;
import com.instamojo.wrapper.util.Constants;
import com.instamojo.wrapper.util.HttpUtils;
import org.apache.http.util.TextUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Class ApiContext.
 */
public class ApiContext {

    /*
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = Logger.getLogger(ApiContext.class.getName());

    public enum Mode {
        TEST, LIVE
    }

    private volatile static ApiContext instance;

    /*
     * The access token related info
     */
    private AccessToken accessToken;

    /*
     * The token creation time
     */
    private long tokenCreationTime;

    /*
     * Instamojo client id
     */
    private String clientId;

    /*
     * The client secret
     */
    private String clientSecret;

    private Mode mode;

    private ApiContext() {
        // Default private constructor
    }

    /*
     * Instantiates a new instamojo impl.
     */
    private ApiContext(String clientId, String clientSecret, Mode mode) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.mode = mode;
    }

    /**
     * Gets api.
     *
     * @param clientId     the client id
     * @param clientSecret the client secret
     * @return the api
     */
    public static ApiContext create(String clientId, String clientSecret, Mode mode) {
        if (instance == null) {
            synchronized (ApiContext.class) {
                if (instance == null) {
                    instance = new ApiContext(clientId, clientSecret, mode);
                }
            }
        }

        return instance;
    }

    private boolean isTokenExpired() {
        long durationInSeconds = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - tokenCreationTime);
        return durationInSeconds >= (accessToken.getExpiresIn() - 300);
    }

    private void loadOrRefreshAccessToken() throws ConnectionException, InstamojoClientException {
        if (accessToken == null) {
            fetchAccessToken();

        } else if (isTokenExpired()) {
            refreshAccessToken();
        }
    }

    /*
     * Fetch a new access token.
     */
    private synchronized void fetchAccessToken() throws ConnectionException, InstamojoClientException {
        if (accessToken != null) {
            return;
        }

        Map<String, String> params = new HashMap<>();
        params.put(Constants.CLIENT_ID, clientId);
        params.put(Constants.CLIENT_SECRET, clientSecret);
        params.put(Constants.GRANT_TYPE, Constants.CLIENT_CREDENTIALS);

        loadAccessToken(params);
    }

    /*
     * Refresh an expired access token
     */
    private synchronized void refreshAccessToken() throws ConnectionException, InstamojoClientException {
        if (!isTokenExpired()) {
            return;
        }

        Map<String, String> params = new HashMap<>();
        params.put(Constants.CLIENT_ID, clientId);
        params.put(Constants.CLIENT_SECRET, clientSecret);
        params.put(Constants.GRANT_TYPE, Constants.GRAND_REFRESH_TOKEN);
        params.put(Constants.REFRESH_TOKEN, accessToken.getRefreshToken());

        loadAccessToken(params);
    }

    private void loadAccessToken(Map<String, String> params) throws ConnectionException, InstamojoClientException {
        try {
            String response = HttpUtils.post(getAuthEndpoint(), null, params);

            AccessToken accessTokenResponse = new Gson().fromJson(response,
                    AccessToken.class);

            if (TextUtils.isEmpty(accessTokenResponse.getToken())) {
                throw new InvalidClientException(
                        "Could not get the access token due to " + accessTokenResponse.getError());
            }

            this.accessToken = accessTokenResponse;
            this.tokenCreationTime = System.nanoTime();

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            throw new ConnectionException(e.toString(), e);
        }
    }

    /**
     * Clears the current cached Instance
     */
    public static void ClearInstance() {
        if (instance != null) {
            synchronized (ApiContext.class) {
                instance = null;
            }
        }
    }

    /*
     * Gets the authorization.
     */
    public String getAuthorization() throws ConnectionException, InstamojoClientException {
        loadOrRefreshAccessToken();
        return accessToken.getTokenType() + " " + accessToken.getToken();
    }

    /*
     * Gets the api path.
     */
    public String getApiPath(String path) {
        String apiPath = getApiEndpoint() + path;

        if (!apiPath.endsWith("/")) {
            apiPath += Character.toString('/');
        }
        return apiPath;
    }

    private String getApiEndpoint() {
        if (mode == Mode.TEST) {
            return Constants.INSTAMOJO_TEST_API_ENDPOINT;
        }

        return Constants.INSTAMOJO_LIVE_API_ENDPOINT;
    }

    private String getAuthEndpoint() {
        if (mode == Mode.TEST) {
            return Constants.INSTAMOJO_TEST_AUTH_ENDPOINT;
        }

        return Constants.INSTAMOJO_LIVE_AUTH_ENDPOINT;
    }
}
