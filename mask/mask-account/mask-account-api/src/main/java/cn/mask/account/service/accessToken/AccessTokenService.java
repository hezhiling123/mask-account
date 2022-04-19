package cn.mask.account.service.accessToken;

import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.http.HttpEntity;

import javax.servlet.http.HttpServletRequest;

public interface AccessTokenService {
    HttpEntity token(HttpServletRequest request) throws OAuthSystemException;
    HttpEntity userInfo(HttpServletRequest request) throws OAuthSystemException;
    Object logout(HttpServletRequest request);
}
