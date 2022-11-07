package cn.mask.account.service.authorize;

import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import javax.servlet.http.HttpServletRequest;

public interface AuthorizeService {
    Object authorize(HttpServletRequest request) throws OAuthSystemException;
//    boolean login(Subject subject, HttpServletRequest request);
}
