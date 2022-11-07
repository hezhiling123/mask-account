package cn.hezhiling.test.common.config.auth;

import cn.hezhiling.test.hello.service.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author hezhiling
 * @version 1.0
 * @date 2022-06-25 20:16:11
 */
@Component
public class AuthenticationServerProxy {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${auth.server.base.url")
    private String authServiceUrl;

    public void sentAuth(String username, String password) {
        String url1 = authServiceUrl + "/user/auth";
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        HttpEntity<User> request = new HttpEntity<>(user);
        restTemplate.postForEntity(url1, request, Void.class);
    }

    public boolean sentOTP(String username, String code) {
        String url = authServiceUrl + "/otp/check";
        User user = new User();
        user.setUsername(username);
        user.setCode(code);
        HttpEntity<User> request = new HttpEntity<>(user);
        ResponseEntity<Void> voidResponseEntity = restTemplate.postForEntity(url, request, Void.class);
        return HttpStatus.OK.equals(voidResponseEntity.getStatusCode());
    }
}
