package cn.mask.mask.auth.biz.service;

import cn.mask.mask.auth.biz.pojo.UserBaseDO;
import cn.mask.mask.common.core.framework.web.WrapperResponse;

import java.util.List;

/**
 * @author hezhiling
 */
public interface TestService {
    /**
     * 测试
     *
     * @return {@link WrapperResponse}
     */
    WrapperResponse<List<UserBaseDO>> hello();
}
