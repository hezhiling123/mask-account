package cn.hezhiling.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * @author hezhiling
 * @version 1.0
 * @date 2022-06-26 22:22:00
 */
@SpringBootApplication
@EnableGlobalMethodSecurity
public class ResourceServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ResourceServerApplication.class);
    }
}
