package cn.mask.account.service.personal;

import cn.mask.account.service.BaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理
 */
@RestController
@RequestMapping("/api/personal/")
public class PersonalServiceImpl extends BaseService implements PersonalService {
}
