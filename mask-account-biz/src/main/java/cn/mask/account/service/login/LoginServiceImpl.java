package cn.mask.account.service.login;

import cn.mask.account.common.fs.FastDFSClientService;
import cn.mask.account.service.BaseService;
import cn.mask.core.exception.BusinessException;
import cn.mask.core.utils.CommonConstant;
import cn.mask.core.utils.response.HttpResponseBody;
import cn.mask.core.utils.response.ResponseCodeConstant;
import cn.mask.util.ShiroCacheUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录管理
 *
 * @author Jack
 * @date 2017/4/19
 */
@RestController
@RequestMapping("/api/system/")
public class LoginServiceImpl extends BaseService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LoginService loginService;

    @Resource
    private FastDFSClientService fastDFSClientService;

    @Resource
    private ShiroCacheUtil shiroCacheUtil;

    /**
     * 获取图片上传地址
     *
     * @return {@link HttpResponseBody}
     */
    @GetMapping("getFileServerUrl")
    public HttpResponseBody<String> getFileServerUrl() {
        String dfsPath = fastDFSClientService.getDfsPath();
        return HttpResponseBody.successResponse("查询成功", dfsPath);
    }

    /**
     * 未登录
     *
     * @return {@link HttpResponseBody}
     */
    @RequestMapping(value = "unLogin", method = {RequestMethod.GET, RequestMethod.POST})
    public HttpResponseBody<String> unLogin() {
        return new HttpResponseBody(ResponseCodeConstant.UN_LOGIN_ERROR, "没有登陆");
    }

    /**
     * 没有权限,请重新登陆
     *
     * @return {@link HttpResponseBody}
     */
    @RequestMapping(value = "accessDenied", method = {RequestMethod.GET, RequestMethod.POST})
    public HttpResponseBody<String> accessDenied() {
        return new HttpResponseBody(ResponseCodeConstant.ACCESS_DENIED, "没有权限,请重新登陆！");
    }

    /**
     * 已登录
     *
     * @return {@link HttpResponseBody}
     */
    @GetMapping("loggedIn")
    public HttpResponseBody<String> loggedIn() {
        return new HttpResponseBody(ResponseCodeConstant.SUCCESS, "已经登陆成功！");
    }

    /**
     * 登录
     *
     * @param session    session {@link HttpSession}
     * @param userName   用户名
     * @param password   密码
     * @param rememberMe 记住我
     * @return {@link HttpResponseBody}
     */
    @PostMapping("login")
    public HttpResponseBody<Object> toLogin(HttpSession session, String userName, String password, @RequestParam(defaultValue = "false", required = false) boolean rememberMe) {
        Map<String, Object> data = null;
        String message = null;
        try {
            data = loginAuthenticate(userName, password, rememberMe);
        } catch (Exception e) {
            message = e.getMessage();
            this.log(e);
        }
        if (data == null) {
            return HttpResponseBody.failResponse(message);
        } else {
            return HttpResponseBody.successResponse("登录成功", data);
        }
    }

    /**
     * 登出
     *
     * @return {@link HttpResponseBody}
     */
    @PostMapping("logout")
    public HttpResponseBody<String> logout() {
        SecurityUtils.getSubject().logout();
        shiroCacheUtil.removeUser(this.getSessionUser().getId());
        return HttpResponseBody.successResponse("登出成功");
    }

    private Map<String, Object> loginAuthenticate(String loginName, String password, boolean rememberMe) {
        logger.info("用户{}尝试登录", loginName);
        Map<String, Object> data = new HashMap<>();
        UserPO user;
        //host 用来判断前后台登陆。
        UsernamePasswordToken token = new UsernamePasswordToken(loginName, password);

        if (rememberMe) {
            token.setRememberMe(true);
        }
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            logger.info("------------------subject.isAuthenticated():--------------" + subject.isAuthenticated());
            user = (UserPO) subject.getSession().getAttribute(CommonConstant.SESSION_USER_KEY);
            if (user != null) {
                user.setPassword(null);
                shiroCacheUtil.putUser(user.getId(), "1");
            }

            data.put("userInfo", user);
            logger.info("------------------user" + loginName + "login SUCCESS-------------------");
        } catch (AuthenticationException e) {
            token.clear();
            throw new BusinessException(ResponseCodeConstant.USER_LOGIN_FAIL_PASSWORD_FAIL, "用户名或密码错误！");
        } catch (Exception e2) {
            logger.error("系统错误", e2);
            token.clear();
            throw new BusinessException("系统错误");
        }
        return data;
    }

    private void log(Exception e) {
        if (e instanceof BusinessException) {
            BusinessException be = (BusinessException) e;
            //如果是密码错了就不打堆栈了
            if (ResponseCodeConstant.USER_LOGIN_FAIL_PASSWORD_FAIL.equals(be.getCode())) {
                logger.warn(e.getMessage());
            } else {
                logger.warn(e.getMessage(), e);
            }
        } else {
            logger.warn(e.getMessage(), e);
        }
    }

}
