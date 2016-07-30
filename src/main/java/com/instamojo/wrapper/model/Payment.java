package com.instamojo.wrapper.model;

import java.io.Serializable;

public class Payment implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5246047430600267758L;

	/** The id. */
	private String id;

	/** The status. */
	private String status;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status
	 *            the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Payment{" + "id='" + id + '\'' +
				", status='" + status + '\'' +
				'}';
	}
}
