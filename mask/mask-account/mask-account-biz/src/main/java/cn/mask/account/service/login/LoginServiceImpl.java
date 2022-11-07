package cn.mask.account.service.login;

import cn.mask.account.common.constants.CommonConstant;
import cn.mask.account.common.fs.FastDFSClientService;
import cn.mask.account.common.utils.ShiroCacheUtil;
import cn.mask.account.service.BaseService;
import cn.mask.core.framework.web.WrapperResponse;
import cn.mask.core.framework.web.exception.MaskException;
import cn.mask.core.framework.web.exception.ResultStatusCode;
import cn.mask.mask.user.api.login.dto.UserInfo;
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
     * @return {@link WrapperResponse}
     */
    @GetMapping("getFileServerUrl")
    public WrapperResponse<String> getFileServerUrl() {
        String dfsPath = fastDFSClientService.getDfsPath();
        return WrapperResponse.success("查询成功", dfsPath);
    }

    /**
     * 未登录
     *
     * @return {@link WrapperResponse}
     */
    @RequestMapping(value = "unLogin", method = {RequestMethod.GET, RequestMethod.POST})
    public WrapperResponse<String> unLogin() {
        return WrapperResponse.error(ResultStatusCode.UNAUTHO_ERROR.getCode(), "没有登陆", null);
    }

    /**
     * 没有权限,请重新登陆
     *
     * @return {@link WrapperResponse}
     */
    @RequestMapping(value = "accessDenied", method = {RequestMethod.GET, RequestMethod.POST})
    public WrapperResponse<String> accessDenied() {
        return WrapperResponse.error(ResultStatusCode.UNAUTHO_ERROR.getCode(), "没有权限,请重新登陆！", null);
    }

    /**
     * 已登录
     *
     * @return {@link WrapperResponse}
     */
    @GetMapping("loggedIn")
    public WrapperResponse<String> loggedIn() {
        return WrapperResponse.success("已经登陆成功！" );
    }

    /**
     * 登录
     *
     * @param session    session {@link HttpSession}
     * @param userName   用户名
     * @param password   密码
     * @param rememberMe 记住我
     * @return {@link WrapperResponse}
     */
    @PostMapping("login")
    public WrapperResponse<Object> toLogin(HttpSession session, String userName, String password, @RequestParam(defaultValue = "false", required = false) boolean rememberMe) {
        Map<String, Object> data = null;
        String message = null;
        try {
            data = loginAuthenticate(userName, password, rememberMe);
        } catch (Exception e) {
            message = e.getMessage();
            this.log(e);
        }
        if (data == null) {
            return WrapperResponse.fail(message, null);
        } else {
            return WrapperResponse.success("登录成功", data);
        }
    }

    /**
     * 登出
     *
     * @return {@link WrapperResponse}
     */
    @PostMapping("logout")
    public WrapperResponse<String> logout() {
        SecurityUtils.getSubject().logout();
        shiroCacheUtil.removeUser(this.getSessionUser().getId());
        return WrapperResponse.success("登出成功", null);
    }

    private Map<String, Object> loginAuthenticate(String loginName, String password, boolean rememberMe) throws MaskException {
        logger.info("用户{}尝试登录", loginName);
        Map<String, Object> data = new HashMap<>();
        UserInfo user;
        //host 用来判断前后台登陆。
        UsernamePasswordToken token = new UsernamePasswordToken(loginName, password);

        if (rememberMe) {
            token.setRememberMe(true);
        }
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            logger.info("------------------subject.isAuthenticated():--------------" + subject.isAuthenticated());
            user = (UserInfo) subject.getSession().getAttribute(CommonConstant.SESSION_USER_KEY);
            if (user != null) {
                shiroCacheUtil.putUser(user.getId(), "1");
            }

            data.put("userInfo", user);
            logger.info("------------------user" + loginName + "login SUCCESS-------------------");
        } catch (AuthenticationException e) {
            token.clear();
            throw new MaskException( "用户名或密码错误！");
        } catch (Exception e2) {
            logger.error("系统错误", e2);
            token.clear();
            throw new MaskException(ResultStatusCode.HTTP_ERROR_500, "系统错误");
        }
        return data;
    }

    private void log(Exception e) {
        if (e instanceof MaskException) {
            MaskException be = (MaskException) e;
            //如果是密码错了就不打堆栈了
            be.getCode();
            logger.warn(e.getMessage(), e);
        } else {
            logger.warn(e.getMessage(), e);
        }
    }

}
