package com.instamojo.wrapper.response;

import com.google.gson.annotations.SerializedName;

/**
 * The Class AccessTokenResponse.
 */
public class AccessTokenResponse extends Response {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 6676032026345798360L;

    /** The token. */
    @SerializedName("access_token")
    private String token;

    /** The token type. */
    @SerializedName("token_type")
    private String tokenType;

    /** The expires in. */
    @SerializedName("expires_in")
    private Long expiresIn;

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

    /* (non-Javadoc)
     * @see com.instamojo.wrapper.response.Response#toString()
     */
    @Override
    public String toString() {
        return "AccessTokenResponse{" +
                "token='" + token + '\'' +
                ", tokenType='" + tokenType + '\'' +
                ", expiresIn=" + expiresIn +
                ", scope='" + scope + '\'' +
                ", error='" + error + '\'' +
                ", jsonResponse='" + jsonResponse + '\'' +
                '}';
    }
}
