package com.flipkart.beans.request;

public class RequestContext {

    private String clientId;
    private String context;
    private String traceId;
    private boolean isTestRequest;

    public RequestContext(String clientId, String context, String traceId, boolean isTestRequest) {
        this.clientId = clientId;
        this.context = context;
        this.isTestRequest = isTestRequest;
        this.traceId = traceId;
    }

    @Override
    public String toString() {
        return "RequestContext{" +
                "clientId='" + clientId + '\'' +
                ", context='" + context + '\'' +
                ", isTestRequest=" + isTestRequest +
                ", traceId='" + traceId + '\'' +
                '}';
    }

    public String getClientId() {
        return clientId;
    }

    public String getContext() {
        return context;
    }

    public boolean isTestRequest() {
        return isTestRequest;
    }

    public String getTraceId() {
        return traceId;
    }
}
