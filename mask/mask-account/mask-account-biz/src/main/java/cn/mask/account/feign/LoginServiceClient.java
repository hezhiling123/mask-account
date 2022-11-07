package cn.mask.account.feign;

import cn.mask.mask.user.api.login.service.LoginService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "MASK-USER-SERVICE", path = "/user")
public interface LoginServiceClient extends LoginService {
}
