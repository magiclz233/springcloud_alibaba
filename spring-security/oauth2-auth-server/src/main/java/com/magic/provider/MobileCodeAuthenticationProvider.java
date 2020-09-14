package com.magic.provider;

import cn.hutool.core.util.StrUtil;
import com.magic.constant.RedisConstant;
import com.magic.service.UserDetailsServiceImpl;
import com.magic.token.MobileCodeAuthenticationToken;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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
        // 获取手机号
        String mobile = (String) authentication.getPrincipal();
        if (StrUtil.isBlank(mobile)) {
            throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "手机号为null"));
        }
        String code = (String) authentication.getCredentials();
        if (code == null) {
            log.error("缺失code参数");
            throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "验证码为null"));
        }
        String cacheCode = stringRedisTemplate.opsForValue().get(RedisConstant.SMS_CODE_PREFIX + mobile);
        if (cacheCode == null || !cacheCode.equals(code)) {
            log.error("短信验证码错误");
            throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "验证码错误"));
        }
        // 获取到了验证码之后,如果对应上了 删除掉redis中的验证码
        stringRedisTemplate.delete(RedisConstant.SMS_CODE_PREFIX + mobile);
        UserDetails user;
        try {
            user = userDetailsService.loadUserByMobile(mobile);
        } catch (UsernameNotFoundException e) {
            log.info("手机号:" + mobile + "未查到用户信息");
            if (this.hideUserNotFoundExceptions) {
                throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "手机号未找到"));
            }
            throw e;
        }
        //  账号禁用、锁定、超时校验
        check(user);

        MobileCodeAuthenticationToken authenticationToken = new MobileCodeAuthenticationToken(user, code, user.getAuthorities());
        authenticationToken.setDetails(authenticationToken.getDetails());
        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MobileCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    /**
     * 账号禁用、锁定、超时校验
     *
     * @param user
     */
    private void check(UserDetails user) {
        if (!user.isAccountNonLocked()) {
            throw new LockedException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.locked", "User account is locked"));
        } else if (!user.isEnabled()) {
            throw new DisabledException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.disabled", "User is disabled"));
        } else if (!user.isAccountNonExpired()) {
            throw new AccountExpiredException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.expired", "User account has expired"));
        }
    }


    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void setHideUserNotFoundExceptions(boolean hideUserNotFoundExceptions) {
        this.hideUserNotFoundExceptions = hideUserNotFoundExceptions;
    }

    public void setUserDetailsService(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
