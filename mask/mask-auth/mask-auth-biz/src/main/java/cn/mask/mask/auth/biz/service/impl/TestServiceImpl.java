package cn.mask.mask.auth.biz.service.impl;

import cn.mask.mask.auth.biz.dao.UserBaseMapper;
import cn.mask.mask.auth.biz.pojo.UserBaseDO;
import cn.mask.mask.auth.biz.service.TestService;
import cn.mask.mask.common.core.framework.web.WrapperResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hezhiling
 * @version 1.0
 * @date 2022-10-29 21:17:44
 */
@RestController
@RequestMapping("/test")
public class TestServiceImpl implements TestService {
    @Autowired
    private UserBaseMapper userBaseMapper;

    /**
     * 测试
     *
     * @return {@link WrapperResponse}
     */
    @Override
    @PostMapping("/test")
    public WrapperResponse<List<UserBaseDO>> hello() {
        QueryWrapper<UserBaseDO> queryWrapper = new QueryWrapper<>();
        List<UserBaseDO> userBaseDOS = userBaseMapper.selectList(queryWrapper);
        return WrapperResponse.success(userBaseDOS);
    }
}
