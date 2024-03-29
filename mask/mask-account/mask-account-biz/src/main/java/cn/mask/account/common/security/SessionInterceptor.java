package cn.mask.account.common.security;

import cn.mask.mask.user.api.login.service.LoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zw
 * @date 2017/5/10
 */
public class SessionInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);
    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        Subject currentUser = SecurityUtils.getSubject();
        //判断用户是通过记住我功能自动登录,此时session失效
        if (!currentUser.isAuthenticated() && currentUser.isRemembered()) {
            logger.debug("-{}--isRemembered--", currentUser.getPrincipals());
            //如果之前是用的微信登录，那么这个principals将会是userId,见cn/toroot/controller/WechatController.java:171
//            UserPO user = loginService.login(currentUser.getPrincipals().toString());
//            //对密码进行加密后验证
//            UsernamePasswordToken token = new UsernamePasswordToken(user.getId().toString(), user.getPassword(), currentUser.isRemembered());
//            //把当前用户放入session
//            currentUser.login(token);
//            ShiroRealm.setResource(iLoginService, session,user);
            //设置会话的过期时间--ms,默认是30分钟，设置负数表示永不过期
        }

        return true;
    }


    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        logger.trace("---postHandle---");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        logger.trace("---afterCompletion---");
    }
}