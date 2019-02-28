package com.instamojo.wrapper.model;

import com.google.gson.annotations.SerializedName;
import org.joda.time.DateTime;

public class Invoice {

    private String id;

    @SerializedName("user")
    private String userUri;

    @SerializedName("created_at")
    private DateTime createdAt;

    @SerializedName("issue_date")
    private DateTime issueDate;

    @SerializedName("file")
    private String fileUrl;

    @SerializedName("last_modified")
    private DateTime modifiedAt;

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

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }

    public DateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(DateTime issueDate) {
        this.issueDate = issueDate;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public DateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(DateTime modifiedAt) {
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
