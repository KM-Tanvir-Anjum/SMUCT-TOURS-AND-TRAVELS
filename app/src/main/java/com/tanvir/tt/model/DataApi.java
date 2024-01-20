package com.tanvir.tt.model;

public class DataApi {
    private String sourceAccountNumber;
    private String targetAccountNumber;
    private Double amount;
    private String data;
    private Integer status;
    private String message;
    private String timestamp;
    private String endpoint;

    public String getSourceAccountNumber() {
        return sourceAccountNumber;
    }

    public String getTargetAccountNumber() {
        return targetAccountNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getData() {
        return data;
    }
}

