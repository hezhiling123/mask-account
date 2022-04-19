package cn.mask.mask.user.api.authorize.service;

import cn.mask.mask.user.api.authorize.dto.Oauth2Client;

import java.util.Map;

/**
 * @author hezhiling
 * @version 1.0
 * @date 2022-04-01 15:00:57
 */
public interface AuthorizeService {
    /**
     * 验证oauth2.0客户端是否存在
     *
     * @param clientId  客户端id
     * @return  存在true/不存在false
     */
    boolean checkClientId(String clientId);

    /**
     * 验证oauth2.0密码是否正确
     *
     * @param clientSecret  客户端秘钥
     * @return  正确true/不正确false
     */
    boolean checkClientSecret(String clientSecret);

    /**
     * 获取过期时间
     *
     * @return 过期时间
     */
    long getExpireIn();

    /**
     * 根据客户端id获取鉴权客户端信息
     *
     * @param clientId  客户端id
     * @return  客户端信息
     */
    Oauth2Client findClientByClientId(String clientId);

    /**
     * 获取所有鉴权客户端信息
     *
     * @return  客户端信息map
     */
    Map<String, Oauth2Client> getClientMap();
}
