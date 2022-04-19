package cn.mask.account.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "MASK-USER-SERVICE", path = "/user")
public interface LoginServiceClient extends LoginService {
}
