package com.instamojo.wrapper.response;

import java.io.Serializable;

/**
 * The Class Response.
 */
public class Response implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -2455335484033892782L;

    /** The success. */
    protected Boolean success;

    /** The message. */
    protected Object message;

    /** The json response. */
    protected String jsonResponse;

    /**
     * Gets the success.
     *
     * @return the success
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     * Sets the success.
     *
     * @param success the new success
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    /**
     * Gets the message.
     *
     * @return the message
     */
    public Object getMessage() {
        return message;
    }

    /**
     * Sets the message.
     *
     * @param message the new message
     */
    public void setMessage(Object message) {
        this.message = message;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */

    /**
     * Gets the json response.
     *
     * @return the json response
     */
    public String getJsonResponse() {
        return jsonResponse;
    }

    /**
     * Sets the json response.
     *
     * @param jsonResponse the new json response
     */
    public void setJsonResponse(String jsonResponse) {
        this.jsonResponse = jsonResponse;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
	public String toString() {
        return "Response{" + ", success=" + success +
                ", message='" + message + '\'' +
                ", jsonResponse='" + jsonResponse + '\'' +
                '}';
	}
}
