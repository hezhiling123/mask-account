package cn.mask.account.common.security;


import cn.mask.core.exception.BusinessException;
import cn.mask.core.utils.CommonConstant;
import cn.mask.core.utils.response.ResponseCodeConstant;
import cn.mask.mask.model.user.dto.UserInfo;
import cn.mask.sys.service.auth.LoginService;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 * @author mask
 */
public class ShiroRealm extends AuthorizingRealm {
    private final static Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

    @Autowired
    private LoginService loginService;

    public static void setResource(LoginService loginService, Session session, UserInfo user) {
//        List<MenuModel> menuList = loginService.queryMenus(user.getId(),"null");
        session.setAttribute(CommonConstant.SESSION_USER_KEY, user);
        session.setAttribute(CommonConstant.SESSION_USER_NAME_KEY, user.getNickName());
        session.setAttribute(CommonConstant.SESSION_USER_ID_KEY, user.getId());
//        session.setAttribute(CommonConstant.SESSION_MENUS_KEY, JsonUtils.obj2json(menuList));
    }

    /**
     * 获取授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
        UserInfo user = (UserInfo) SecurityUtils.getSubject().getSession().getAttribute(CommonConstant.SESSION_USER_KEY);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 添加用户角色信息
//        List<SysRole> roleList = user.getRoleList();
//        if (roleList != null) {
//            for (SysRole role : roleList) {
//                info.addRole(role.getRoleCode());
//            }
//        }
//        // 添加用户权限信息
//        List<MenuModel> permissionList = loginService.queryPermissionList(user);
//        if (permissionList != null) {
//            addMyPermission(info, permissionList);
//        }
//        SecurityUtils.getSubject().getSession().setAttribute(CommonConstant.SESSION_USER_PERMISSIONS,info.getStringPermissions());
        return info;
    }
//    //添加权限方法
//    private void addMyPermission(SimpleAuthorizationInfo info, List<MenuModel> permissionList) {
////        for (MenuModel menu : permissionList) {
////            if (!StringUtils.isEmpty(menu.getCode())) {
////                String permission = menu.getCode();
////                if (!StringUtils.isEmpty(permission)) {
////                    info.addStringPermission(permission);
////                }
////            }
////        }
//    }

    /**
     * 获取认证信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

        String userName = token.getUsername();
        String password = new String(token.getPassword());

        logger.info("userName-password" + userName + "-" + password);
        if (StringUtils.isEmpty(userName)) {
            throw new BusinessException(ResponseCodeConstant.USER_LOGIN_FAIL, "用户名不能为空");
        }
        if (StringUtils.isEmpty(password)) {
            throw new BusinessException(ResponseCodeConstant.USER_LOGIN_FAIL, "密码不能为空");
        }
        try {
            UserInfo user = loginService.loginByToken(JSONObject.toJSONString(authcToken));
            Session session = SecurityUtils.getSubject().getSession();
            setResource(loginService, session, user);
            logger.info("登录认证成功*******");
            return new SimpleAuthenticationInfo(token.getPrincipal(), token.getPassword(), token.getUsername());
        } catch (Exception e) {
            throw new AuthenticationException("认证出错", e);
        }
    }

}
