package cn.mask.core.framework.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CurrentUser implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userAcctID;
    private String userAcct;
    private String uactID;
    private String name;
    private String orgUntID;
    private String orgName;
    private String orgCodg;
    private String deptID;
    private String deptName;
    private String poolAreaCodg;
    private String admDvs;
    private String prntOrgID;
    public Map<String, String> InsuTypePoolAreaMap = new HashMap<>();

    public CurrentUser() {
    }

    public String getUserAcctID() {
        return this.userAcctID;
    }

    public void setUserAcctID(String userAcctID) {
        this.userAcctID = userAcctID;
    }

    public String getUserAcct() {
        return this.userAcct;
    }

    public void setUserAcct(String userAcct) {
        this.userAcct = userAcct;
    }

    public String getUactID() {
        return this.uactID;
    }

    public void setUactID(String uactID) {
        this.uactID = uactID;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrgUntID() {
        return this.orgUntID;
    }

    public void setOrgUntID(String orgUntID) {
        this.orgUntID = orgUntID;
    }

    public String getOrgName() {
        return this.orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgCodg() {
        return this.orgCodg;
    }

    public void setOrgCodg(String orgCodg) {
        this.orgCodg = orgCodg;
    }

    public String getPoolAreaCodg() {
        return this.poolAreaCodg;
    }

    public void setPoolAreaCodg(String poolAreaCodg) {
        this.poolAreaCodg = poolAreaCodg;
    }

    public String getAdmDvs() {
        return this.admDvs;
    }

    public void setAdmDvs(String admDvs) {
        this.admDvs = admDvs;
    }

    public String getPrntOrgID() {
        return this.prntOrgID;
    }

    public void setPrntOrgID(String prntOrgID) {
        this.prntOrgID = prntOrgID;
    }

    public String getDeptID() {
        return this.deptID;
    }

    public void setDeptID(String deptID) {
        this.deptID = deptID;
    }

    public String getDeptName() {
        return this.deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String toString() {
        return "CurrentUser [userAcctID=" + this.userAcctID + ", userAcct=" + this.userAcct + ", uactID=" + this.uactID + ", name=" + this.name + ", orgUntID=" + this.orgUntID + ", orgName=" + this.orgName + ", orgCodg=" + this.orgCodg + ", deptID=" + this.deptID + ", deptName=" + this.deptName + ", poolAreaCodg=" + this.poolAreaCodg + ", admDvs=" + this.admDvs + ", prntOrgID=" + this.prntOrgID + ", InsuTypePoolAreaMap=" + this.InsuTypePoolAreaMap + "]";
    }
}
