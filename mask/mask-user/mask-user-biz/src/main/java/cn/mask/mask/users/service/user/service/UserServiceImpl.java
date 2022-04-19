package cn.mask.mask.users.service.user.service;


import cn.mask.mask.user.api.login.dto.UserInfo;
import cn.mask.mask.user.api.user.service.UserService;

/**
 * @author hezhiling
 * @version 1.0
 * @date 2022-04-11 22:05:54
 */
public class UserServiceImpl implements UserService {
    /**
     * 创建系统用户
     *
     * @param userInfo 用户信息 {@link UserInfo}
     */
    @Override
    public void addUser(UserInfo userInfo) {

    }

    /**
     * 根据用户id查询用户信息
     *
     * @param userId 用户id
     * @return 用户信息 {@link UserInfo}
     */
    @Override
    public UserInfo getUserByUserId(String userId) {
        return null;
    }
}
