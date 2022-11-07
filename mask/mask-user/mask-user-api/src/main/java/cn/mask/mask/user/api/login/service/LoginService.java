package cn.mask.mask.user.api.login.service;

import cn.mask.mask.common.core.framework.web.WrapperResponse;
import cn.mask.mask.common.core.framework.web.exception.MaskException;
import cn.mask.mask.user.api.login.dto.WeiXinUserInfo;
import cn.mask.mask.user.api.login.dto.WeiXinLoginInfo;
import cn.mask.mask.user.api.user.dto.UserDTO;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author hezhiling
 * @version 1.0
 * @date 2022-04-01 15:01:52
 */
public interface LoginService {
    WrapperResponse<WeiXinUserInfo> login(String tokenStr);
    WrapperResponse<WeiXinUserInfo> loginByToken(String tokenStr) throws MaskException;
}
