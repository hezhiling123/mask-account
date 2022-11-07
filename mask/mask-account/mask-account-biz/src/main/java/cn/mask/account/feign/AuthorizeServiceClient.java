package cn.mask.account.feign;

import cn.mask.mask.user.api.authorize.service.AuthorizeService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "MASK-USER-SERVICE", path = "/user")
public interface AuthorizeServiceClient extends AuthorizeService {
}
