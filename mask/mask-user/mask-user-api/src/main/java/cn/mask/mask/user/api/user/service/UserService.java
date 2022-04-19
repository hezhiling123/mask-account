package cn.mask.mask.user.api.user.service;

import cn.mask.mask.user.api.login.dto.UserInfo;

public interface UserService {
    /**
     * 创建系统用户
     *
     * @param userInfo  用户信息 {@link UserInfo}
     */
    void addUser(UserInfo userInfo);

    /**
     *  根据用户id查询用户信息
     *
     * @param userId    用户id
     * @return  用户信息 {@link UserInfo}
     */
    UserInfo getUserByUserId(String userId);
}
