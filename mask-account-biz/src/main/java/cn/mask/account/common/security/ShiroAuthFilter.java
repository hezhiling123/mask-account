package cn.mask.account.common.security;


import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authc.PassThruAuthenticationFilter;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;


/**
 * @author mask
 */
public class ShiroAuthFilter extends PassThruAuthenticationFilter {

    /**
     * 这是没权限的路径，不是没登录的
     */
    private String unauthorizedUrl = "/api/system/accessDenied";
    /**
     * 在xml文件中加载不需要保存的url链接
     */
    private Set<String> ignoreSaveRequestUrl;

    public Set<String> getIgnoreSaveRequestUrl() {
        return ignoreSaveRequestUrl;
    }

    public void setIgnoreSaveRequestUrl(Set<String> ignoreSaveRequestUrl) {
        this.ignoreSaveRequestUrl = ignoreSaveRequestUrl;
    }

    public String getUnauthorizedUrl() {
        return unauthorizedUrl;
    }

    public void setUnauthorizedUrl(String unauthorizedUrl) {
        this.unauthorizedUrl = unauthorizedUrl;
    }

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = getSubject(request, response);
        //1.判断是否已认证过
        //没有认证并且没有记住登录
        return subject.isAuthenticated() || subject.isRemembered();
    }

    /**
     * @param request  req
     * @param response rep
     * @return 该请求是否被拦截
     * @throws IOException io
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        Subject subject = getSubject(request, response);
        // If the subject isn't identified, redirect to login URL
        if (subject.getPrincipal() == null) {
            saveRequestAndRedirectToLogin(request, response);
        } else {
            // If subject is known but not authorized, redirect to the unauthorized URL if there is one
            // If no unauthorized URL is specified, just return an unauthorized HTTP status code
            String unauthorizedUrl = getUnauthorizedUrl();
            //SHIRO-142 - ensure that redirect _or_ error code occurs - both cannot happen due to response commit:
            if (StringUtils.hasText(unauthorizedUrl)) {
                WebUtils.issueRedirect(request, response, unauthorizedUrl);
            } else {
                WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        return false;
    }

    /**
     * 重写saveRequest,在配置文件中配置的地址不需要保存。实现登录后直接跳转到之前输入的地址。
     *
     * @param request req
     */
    @Override
    protected void saveRequest(ServletRequest request) {
        String reqUrl = ((ShiroHttpServletRequest) request).getRequestURI();
        if (reqUrl.indexOf("/") > 1) {
            String url = reqUrl.substring(reqUrl.indexOf("/", 1));
            if (!ignoreSaveRequestUrl.contains(url)) {
                WebUtils.saveRequest(request);
            }
        }
    }


}

