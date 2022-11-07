package cn.hezhiling.test.service.user.service;

import cn.hezhiling.test.service.otp.dto.Otp;
import cn.hezhiling.test.service.user.dto.User;

/**
 * @author hezhiling
 * @version 1.0
 * @date 2022-06-25 09:51:59
 */
public interface UserService {

    /**
     * 添加用户
     *
     * @param user {@link User}
     */
    void addUser(User user);

    /**
     * 授权
     *
     * @param user {@link User}
     */
    void auth(User user);

    /**
     * 检验授权码
     *
     * @param otpToValidate {@link Otp}
     * @return 如果授权码正确则返回true，否则false
     */
    boolean check(Otp otpToValidate);
}
