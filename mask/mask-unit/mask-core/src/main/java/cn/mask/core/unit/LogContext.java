//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.mask.core.unit;

import java.io.Serializable;

public class LogContext implements Serializable {
    private static final long serialVersionUID = 9129572241630640531L;
    private String traceID;
    private String areaCode;
    private String bizCode;
    private String appSign;

    public LogContext() {
    }

    @Override
    public String toString() {
        return "LogContext [traceID=" + this.traceID + ", areaCode=" + this.areaCode + ", bizCode=" + this.bizCode + ", appSign=" + this.appSign + "]";
    }

    public String getTraceID() {
        return this.traceID;
    }

    public void setTraceID(String traceID) {
        this.traceID = traceID;
    }

    public String getBizCode() {
        return this.bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getAppSign() {
        return this.appSign;
    }

    public void setAppSign(String appSign) {
        this.appSign = appSign;
    }

    public String getAreaCode() {
        return this.areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
}
