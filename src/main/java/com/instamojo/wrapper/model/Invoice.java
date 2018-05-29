package com.instamojo.wrapper.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Invoice implements Serializable {

    private String id;

    @SerializedName("user")
    private String userUri;

    @SerializedName("created_at")
    private Date createdAt;

    @SerializedName("issue_date")
    private Date issueDate;

    @SerializedName("file")
    private String fileUrl;

    @SerializedName("last_modified")
    private Date modifiedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserUri() {
        return userUri;
    }

    public void setUserUri(String userUri) {
        this.userUri = userUri;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id='" + id + '\'' +
                ", userUri='" + userUri + '\'' +
                ", createdAt=" + createdAt +
                ", issueDate=" + issueDate +
                ", fileUrl='" + fileUrl + '\'' +
                ", modifiedAt=" + modifiedAt +
                '}';
    }
}
