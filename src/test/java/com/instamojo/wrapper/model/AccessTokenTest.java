package com.instamojo.wrapper.model;

import com.google.gson.Gson;
import com.instamojo.wrapper.exception.HTTPException;
import com.instamojo.wrapper.util.Constants;
import com.instamojo.wrapper.util.HttpUtils;
import com.instamojo.wrapper.util.TestConstants;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;


public class AccessTokenTest {

    private String AUTH_ENDPOINT = Constants.INSTAMOJO_TEST_AUTH_ENDPOINT;
    private Map<String, String> apiParams = new HashMap<>();

    @Before
    public void setUp() {

        apiParams.clear();
        apiParams.put(Constants.PARAM_CLIENT_ID, TestConstants.CLIENT_ID);
        apiParams.put(Constants.PARAM_CLIENT_SECRET, TestConstants.CLIENT_SECRET);

    }

    @Test
    public void testApplicationBasedAuthAccessToken() throws IOException, HTTPException {

        apiParams.put(Constants.PARAM_GRANT_TYPE, Constants.GRANT_TYPE_CLIENT_CREDENTIALS);

        String response = HttpUtils.post(AUTH_ENDPOINT, null, apiParams);
        AccessToken accessToken = new Gson().fromJson(response, AccessToken.class);

        assertEquals(accessToken.getTokenType(), "Bearer");
        assertEquals((long) accessToken.getExpiresIn(), (long) 36000);
        assertNotNull(accessToken.getToken());
        assertNotNull(accessToken.getScope());
    }

    @Test
    public void testUserBasedAuthAccessToken() throws IOException, HTTPException {

        apiParams.put(Constants.PARAM_GRANT_TYPE, Constants.GRANT_TYPE_PASSWORD);

        apiParams.put(Constants.PARAM_USERNAME, TestConstants.USERNAME);
        apiParams.put(Constants.PARAM_PASSWORD, TestConstants.PASSWORD);

        String response = HttpUtils.post(AUTH_ENDPOINT, null, apiParams);
        AccessToken accessToken = new Gson().fromJson(response, AccessToken.class);

        assertEquals(accessToken.getTokenType(), "Bearer");
        assertEquals((long) accessToken.getExpiresIn(), (long) 36000);
        assertNotNull(accessToken.getToken());
        assertNotNull(accessToken.getScope());
        assertNotNull(accessToken.getRefreshToken());
    }

    @Test
    public void testRefreshTokenBasedAuthAccessToken() throws IOException, HTTPException {

        /*
         * First get the 'refresh_token' using User Based Auth
         */
        apiParams.put(Constants.PARAM_GRANT_TYPE, Constants.GRANT_TYPE_PASSWORD);

        apiParams.put(Constants.PARAM_USERNAME, TestConstants.USERNAME);
        apiParams.put(Constants.PARAM_PASSWORD, TestConstants.PASSWORD);

        String response = HttpUtils.post(AUTH_ENDPOINT, null, apiParams);
        AccessToken accessToken = new Gson().fromJson(response, AccessToken.class);

        String refreshToken = accessToken.getRefreshToken();

        apiParams.remove(Constants.PARAM_USERNAME);
        apiParams.remove(Constants.PARAM_PASSWORD);

        /*
         * Now make the call to Refresh Token Auth endpoint
         */
        apiParams.put(Constants.PARAM_GRANT_TYPE, Constants.GRANT_TYPE_REFRESH_TOKEN);
        apiParams.put(Constants.PARAM_REFRESH_TOKEN, refreshToken);

        response = HttpUtils.post(AUTH_ENDPOINT, null, apiParams);
        accessToken = new Gson().fromJson(response, AccessToken.class);

        assertEquals(accessToken.getTokenType(), "Bearer");
        assertEquals((long) accessToken.getExpiresIn(), (long) 36000);
        assertNotNull(accessToken.getToken());
        assertNotNull(accessToken.getScope());
        assertNotNull(accessToken.getToken());
    }

    @Test
    public void testUnauthorized() throws IOException {

        apiParams.put(Constants.PARAM_GRANT_TYPE, Constants.GRANT_TYPE_CLIENT_CREDENTIALS);
        apiParams.put(Constants.PARAM_CLIENT_SECRET, "");

        try {
            String response = HttpUtils.post(AUTH_ENDPOINT, null, apiParams);
            fail("No exception thrown! Expected HTTPException");
        } catch (HTTPException e) {
            assertEquals(e.getStatusCode(), 401);
        }

    }

    @Test
    public void testBadRequest() throws IOException {

        apiParams.put(Constants.PARAM_GRANT_TYPE, "");

        try {
            String response = HttpUtils.post(AUTH_ENDPOINT, null, apiParams);
            fail("No exception thrown! Expected HTTPException");
        } catch (HTTPException e) {
            assertEquals(e.getStatusCode(), 400);
        }

    }

}
