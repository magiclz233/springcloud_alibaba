package com.magic.provider;

import com.magic.service.UserDetailsServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;

/**
 * @author magic_lz
 * @version 1.0
 * @classname MobileCodeAuthenticationProvider
 * @date 2020/9/9 : 17:36
 */
@Log4j2
public class MobileCodeAuthenticationProvider implements AuthenticationProvider, MessageSourceAware {

    private StringRedisTemplate stringRedisTemplate;
    private UserDetailsServiceImpl userDetailsService;
    private MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    /**
     * 是否隐藏用户未发现异常，默认为true,为true返回的异常信息为BadCredentialsException
     */
    private boolean hideUserNotFoundExceptions = true;
    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
