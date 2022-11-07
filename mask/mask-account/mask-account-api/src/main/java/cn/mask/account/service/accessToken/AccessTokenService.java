package cn.mask.account.service.accessToken;

import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.http.HttpEntity;

import javax.servlet.http.HttpServletRequest;

public interface AccessTokenService {
    /**
     * 获取token
     *
     * @param request   request
     * @return  t
     * @throws OAuthSystemException
     */
    HttpEntity<Object>  token(HttpServletRequest request) throws OAuthSystemException;
    HttpEntity<Object>  userInfo(HttpServletRequest request) throws OAuthSystemException;
    HttpEntity<Object>  logout(HttpServletRequest request);
}
