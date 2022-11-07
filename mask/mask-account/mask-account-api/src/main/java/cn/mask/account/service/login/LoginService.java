package cn.mask.account.service.login;

import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

public interface LoginService {
    /**
     * 获取图片上传地址
     *
     * @return {@link HttpResponseBody}
     */
    HttpResponseBody<String> getFileServerUrl();

    /**
     * 未登录
     *
     * @return {@link HttpResponseBody}
     */
    HttpResponseBody<String> unLogin();

    /**
     * 没有权限,请重新登陆
     *
     * @return {@link HttpResponseBody}
     */
    HttpResponseBody<String> accessDenied();

    /**
     * 已登录
     *
     * @return {@link HttpResponseBody}
     */
    HttpResponseBody<String> loggedIn();

    /**
     * 登录
     *
     * @param session    session {@link HttpSession}
     * @param userName   用户名
     * @param password   密码
     * @param rememberMe 记住我
     * @return {@link HttpResponseBody}
     */
    HttpResponseBody<Object> toLogin(HttpSession session, String userName, String password,
                                     @RequestParam(defaultValue = "false", required = false) boolean rememberMe);

    /**
     * 登出
     *
     * @return {@link HttpResponseBody}
     */
    HttpResponseBody<String> logout();
}
