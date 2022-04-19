package cn.mask.account.service;

import cn.mask.core.utils.CommonConstant;
import cn.mask.mask.model.user.po.UserPO;
import cn.mask.mask.user.login.dto.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Ray on 2017/10/17.
 */
public class BaseService {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public HttpSession getSession() {
        return getRequest().getSession();
    }

    public String getSessionUserName(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (String) session.getAttribute(CommonConstant.SESSION_USER_NAME_KEY);
    }

    public String getSessionUserRealName(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (String) session.getAttribute(CommonConstant.SESSION_USER_REAL_NAME_KEY);
    }

    public String getSessionUserId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (String) session.getAttribute(CommonConstant.SESSION_USER_ID_KEY);
    }

    public String getSessionUserId() {
        return this.getSessionUserId(this.getRequest());
    }

    public UserInfo getSessionUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (UserInfo) session.getAttribute(CommonConstant.SESSION_USER_KEY);
    }

    public UserInfo getSessionUser() {
        return this.getSessionUser(this.getRequest());
    }

    public HttpSession getSession(HttpServletRequest request) {
        return request.getSession();
    }

    public String getSessionUrl(HttpServletRequest request) {
        return request.getScheme() +
                "://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();
    }
}

