package cn.mask.account.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Ray
 * @date 2017/10/16
 */
@Component
public class Config {

    @Value("${dfs.url}")
    private String dfsUrl;

    @Value("${wx.app-id}")
    private String wxAppId;

    @Value("${wx.redirect-uri}")
    private String wxRedirectUri;

    private CompanyConfig company;

    public String getDfsUrl() {
        return dfsUrl;
    }

    public void setDfsUrl(String dfsUrl) {
        this.dfsUrl = dfsUrl;
    }

    public String getWxAppId() {
        return wxAppId;
    }

    public void setWxAppId(String wxAppId) {
        this.wxAppId = wxAppId;
    }

    public String getWxRedirectUri() {
        return wxRedirectUri;
    }

    public void setWxRedirectUri(String wxRedirectUri) {
        this.wxRedirectUri = wxRedirectUri;
    }

    public CompanyConfig getCompany() {
        return company;
    }

    @Resource
    public void setCompany(CompanyConfig company) {
        this.company = company;
    }
}
