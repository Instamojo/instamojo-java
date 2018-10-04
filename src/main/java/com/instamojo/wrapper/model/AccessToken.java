package com.instamojo.wrapper.model;

import com.google.gson.annotations.SerializedName;

/**
 * The Class AccessToken.
 */
public class AccessToken {

    /** The token. */
    @SerializedName("access_token")
    private String token;

    /** The token type. */
    @SerializedName("token_type")
    private String tokenType;

    /** The expires in. */
    @SerializedName("expires_in")
    private Long expiresIn;

    /** To refresh the token if it expires */
    @SerializedName("refresh_token")
    private String refreshToken;

    /** The scope. */
    private String scope;

    /** The error. */
    private String error;

    /**
     * Gets the token.
     *
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the token.
     *
     * @param token the new token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Gets the token type.
     *
     * @return the token type
     */
    public String getTokenType() {
        return tokenType;
    }

    /**
     * Sets the token type.
     *
     * @param tokenType the new token type
     */
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * Gets the expires in.
     *
     *  The access token is valid for the seconds specified in the `expires_in` property in the response.
     * @return the expires in
     */
    public Long getExpiresIn() {
        return expiresIn;
    }

    /**
     * Sets the expires in.
     *
     * @param expiresIn the new expires in
     */
    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    /**
     * Gets the scope.
     *
     * @return the scope
     */
    public String getScope() {
        return scope;
    }

    /**
     * Sets the scope.
     *
     * @param scope the new scope
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * Gets the error.
     *
     * @return the error
     */
    public String getError() {
        return error;
    }

    /**
     * Sets the error.
     *
     * @param error the new error
     */
    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "AccessToken{" +
                "token='" + token + '\'' +
                ", tokenType='" + tokenType + '\'' +
                ", expiresIn=" + expiresIn +
                ", refreshToken='" + refreshToken + '\'' +
                ", scope='" + scope + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}
