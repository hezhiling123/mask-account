package cn.mask.mask.auth.biz.common.config.security;

import cn.mask.mask.common.core.framework.web.WrapperResponse;
import cn.mask.mask.common.core.framework.web.enums.ResultCode;
import cn.mask.mask.common.core.framework.web.exception.MaskException;
import cn.mask.mask.user.api.register.dto.UserBaseInfoDTO;
import cn.mask.mask.user.api.user.dto.QUserDTO;
import cn.mask.mask.user.api.user.service.IUserService;
import lombok.SneakyThrows;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

/**
 * @author hezhiling
 * @version 1.0
 * @date 2022-10-29 20:21:59
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Reference
    private IUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(userId)) {
            throw new MaskException(ResultCode.NULL_REQUIRED_PARAMETER, "拉取用户授权时，用户id必填");
        }
        QUserDTO qUserDTO = new QUserDTO();
        qUserDTO.setUserId(userId);
        WrapperResponse<UserBaseInfoDTO> userByCondition = userService.queryUserByCondition(qUserDTO);
        if (!ResultCode.SUCCESS.getCode().equals(userByCondition.getCode())) {
            throw new MaskException(ResultCode.USER_NOT_EXISTS, "用户不存在");
        }
        User user = new User(userId, passwordEncoder.encode("123"), new ArrayList<>());
        return user;
    }
}
