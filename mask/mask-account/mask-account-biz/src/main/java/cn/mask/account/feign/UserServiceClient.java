package cn.mask.account.feign;

import cn.mask.account.service.user.UserService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "MASK-USER-SERVICE", path = "/user")
public interface UserServiceClient extends UserService {
}
