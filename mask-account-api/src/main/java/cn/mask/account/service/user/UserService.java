package cn.mask.account.service.user;


import cn.mask.core.framework.utils.response.HttpResponseBody;
import cn.mask.mask.user.api.login.dto.UserInfo;

public interface UserService {
    HttpResponseBody addUser(UserInfo user);
    HttpResponseBody<UserInfo> getUserById(String userId);
    HttpResponseBody getUserInfo();
}
