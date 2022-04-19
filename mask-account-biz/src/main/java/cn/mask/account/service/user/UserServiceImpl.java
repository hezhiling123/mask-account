package cn.mask.account.service.user;

import cn.mask.core.utils.response.HttpResponseBody;
import cn.mask.mask.user.login.dto.UserInfo;
import cn.mask.account.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * 用户管理
 *
 * @author Jack
 */
@RestController
@RequestMapping("/api/user/")
public class UserServiceImpl implements UserService extends BaseService {


    @Autowired
    private UserService userService;


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
    @RequestMapping(value = "getUserById", method = {RequestMethod.GET, RequestMethod.POST})
    public HttpResponseBody<UserInfo> getUserById(String userId) {
        UserInfo user = userService.getUserInfoById(userId);
        return HttpResponseBody.successResponse("查询成功", user);
    }

    @GetMapping("getUserInfo")
    public HttpResponseBody getUserInfo() {
        String sessionUserId = getSessionUserId();
        UserInfo userInfo = userService.getUserInfoById(sessionUserId);
        if (userInfo == null) {
            return HttpResponseBody.failResponse("请重新登录");
        }
        Map<String, Object> result = new HashMap<>();
        result.put("userInfo", userInfo);
        return HttpResponseBody.successResponse("查询成功", result);
    }

}
