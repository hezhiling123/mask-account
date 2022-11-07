package cn.mask.account.common.config;

import cn.mask.account.common.utils.ShiroCacheUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author Ray
 * @date 2018/3/26.
 */
@Configuration
public class CacheConfig {

    @Bean
    public ShiroCacheUtil shiroCacheUtil() {
        return new ShiroCacheUtil();
    }


}
