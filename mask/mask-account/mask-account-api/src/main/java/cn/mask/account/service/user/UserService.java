package cn.mask.account.service.user;

import cn.mask.core.utils.response.HttpResponseBody;
import cn.mask.mask.user.login.dto.UserInfo;

public interface UserService {
    HttpResponseBody addUser(UserInfo user);
    HttpResponseBody<UserInfo> getUserById(String userId);
    HttpResponseBody getUserInfo();
}
