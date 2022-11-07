package cn.hezhiling.test.common.config.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author hezhiling
 * @version 1.0
 * @date 2022-06-25 20:15:11
 */
@Configuration
public class ProjectConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
