package cn.mask.account.service.user;

import cn.mask.account.service.BaseService;
import cn.mask.core.framework.utils.response.HttpResponseBody;
import cn.mask.mask.user.api.login.dto.UserInfo;
import org.springframework.web.bind.annotation.*;



/**
 * 用户管理
 *
 * @author Jack
 */
@RestController
@RequestMapping("/api/user/")
public class UserServiceImpl extends BaseService implements UserService  {




    @Override
    public HttpResponseBody addUser(UserInfo user) {
        return null;
    }

    /**
     * 根据用户id获取用户信息
     *
     * @param userId 用户id
     * @return body
     */
    @Override
    @RequestMapping(value = "getUserById", method = {RequestMethod.GET, RequestMethod.POST})
    public HttpResponseBody<UserInfo> getUserById(String userId) {
        return null;
    }

    @Override
    @GetMapping("getUserInfo")
    public HttpResponseBody getUserInfo() {
        return null;
    }

}
