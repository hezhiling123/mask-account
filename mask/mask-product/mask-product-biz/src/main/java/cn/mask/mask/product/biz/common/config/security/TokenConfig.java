package cn.mask.mask.product.biz.common.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author hezhiling
 * @version 1.0
 * @date 2022-11-13 22:40:37
 */
@Configuration
public class TokenConfig {

    /**
     * 配置如何把普通token转换为jwt token
     *
     * @return  jwt token转换器
     */
    @Bean
    public JwtAccessTokenConverter tokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("mask-label");
        return converter;
    }

    /**
     * 设置token以Jwt形式存储
     *
     * @return  {@link TokenStore}
     */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(tokenConverter());
    }
}
