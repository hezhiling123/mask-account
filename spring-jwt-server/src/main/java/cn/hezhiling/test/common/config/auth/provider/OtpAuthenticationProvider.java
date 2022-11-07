package cn.hezhiling.test.common.config.auth.provider;

import cn.hezhiling.test.common.config.auth.AuthenticationServerProxy;
import cn.hezhiling.test.common.config.auth.authentication.OtpAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * @author hezhiling
 * @version 1.0
 * @date 2022-06-25 20:31:59
 */
@Component
public class OtpAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private AuthenticationServerProxy authenticationServerProxy;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String code = String.valueOf(authentication.getCredentials());
        boolean result = authenticationServerProxy.sentOTP(username, code);
        if (!result) {
            throw new BadCredentialsException("授权码错误");
        }
        return new OtpAuthentication(username, code);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return OtpAuthentication.class.isAssignableFrom(aClass);
    }
}
