//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.mask.core.unit;

import java.io.Serializable;

public class LogInfo implements Serializable {
    private static final long serialVersionUID = 9129572241630640531L;
    private String traceID;
    private String userAcctId;
    private String requestTime;
    private String requestUrl;
    private String requestUri;
    private String requestMethod;
    private String clientIP;
    private String clazz;
    private String classMethod;
    private String requestParams;
    private Long timeConsuming;
    private String callFrom;
    private Integer code;

    public LogInfo() {
    }

    public String getTraceID() {
        return this.traceID;
    }

    public void setTraceID(String traceID) {
        this.traceID = traceID;
    }

    public String getUserAcctId() {
        return this.userAcctId;
    }

    public void setUserAcctId(String userAcctId) {
        this.userAcctId = userAcctId;
    }

    public String getRequestTime() {
        return this.requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getRequestUrl() {
        return this.requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestMethod() {
        return this.requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getClientIP() {
        return this.clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public String getClassMethod() {
        return this.classMethod;
    }

    public void setClassMethod(String classMethod) {
        this.classMethod = classMethod;
    }

    public String getRequestParams() {
        return this.requestParams;
    }

    public void setRequestParams(String requestParams) {
        this.requestParams = requestParams;
    }

    public String getLogString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.traceID);
        sb.append("|");
        sb.append(this.clientIP);
        sb.append("|");
        sb.append(this.userAcctId);
        sb.append("|");
        sb.append(this.requestTime);
        sb.append("|");
        sb.append(this.requestUrl);
        sb.append("|");
        sb.append(this.requestMethod);
        sb.append("|");
        sb.append(this.classMethod);
        sb.append("|");
        if (this.requestParams != null && this.requestParams.length() > 0) {
            sb.append(this.requestParams);
        }

        sb.append("|");
        sb.append(this.timeConsuming);
        sb.append("|");
        sb.append(this.code);
        return sb.toString();
    }

    public String getBaseLogString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.traceID);
        sb.append("|");
        sb.append(this.clientIP);
        sb.append("|");
        sb.append(this.userAcctId);
        sb.append("|");
        sb.append(this.requestTime);
        sb.append("|");
        sb.append(this.requestUrl);
        sb.append("|");
        sb.append(this.requestMethod);
        sb.append("|");
        sb.append(this.classMethod);
        sb.append("|");
        return sb.toString();
    }

    public String toString() {
        return "LogInfo [traceID=" + this.traceID + ", userAcctId=" + this.userAcctId + ", requestTime=" + this.requestTime + ", requestUrl=" + this.requestUrl + ", requestUri=" + this.requestUri + ", requestMethod=" + this.requestMethod + ", clientIP=" + this.clientIP + ", clazz=" + this.clazz + ", classMethod=" + this.classMethod + ", requestParams=" + this.requestParams + ", timeConsuming=" + this.timeConsuming + ", callFrom=" + this.callFrom + ", code=" + this.code + "]";
    }

    public Long getTimeConsuming() {
        return this.timeConsuming;
    }

    public void setTimeConsuming(Long timeConsuming) {
        this.timeConsuming = timeConsuming;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getCallFrom() {
        return this.callFrom;
    }

    public void setCallFrom(String callFrom) {
        this.callFrom = callFrom;
    }

    public String getClazz() {
        return this.clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getRequestUri() {
        return this.requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }
}
