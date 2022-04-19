package cn.mask.mask.user.api.login.service;

import cn.mask.core.framework.web.WrapperResponse;
import cn.mask.core.framework.web.exception.MaskException;
import cn.mask.mask.user.api.login.dto.UserInfo;
import cn.mask.mask.user.api.login.dto.WxLoginInfo;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author hezhiling
 * @version 1.0
 * @date 2022-04-01 15:01:52
 */
public interface LoginService {
    WrapperResponse<Object> loginByWeiXin(@RequestBody WxLoginInfo wxLoginInfo);
    WrapperResponse<UserInfo> login(String tokenStr);
    WrapperResponse<UserInfo> loginByToken(String tokenStr) throws MaskException;
}
