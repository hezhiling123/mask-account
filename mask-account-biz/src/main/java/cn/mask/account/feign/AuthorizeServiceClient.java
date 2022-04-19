package cn.mask.account.feign;

import cn.mask.account.service.authorize.AuthorizeService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "MASK-USER-SERVICE", path = "/user")
public interface AuthorizeServiceClient extends AuthorizeService {
}
