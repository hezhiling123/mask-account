package com.alibaba.cloud.youxia;

import com.alibaba.cloud.youxia.config.NacosConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hezhiling
 * @version 1.0
 * @date 2022-05-05 23:02:42
 */
@RestController
public class TestController {
    @Autowired
    NacosConfig nacosConfig;

    @PostMapping("/test")
    public void test() {
        System.out.println("hzltest:" + nacosConfig.getName() + nacosConfig.getValue());
    }
}
