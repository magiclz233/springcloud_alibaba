package com.magic.config;

import com.magic.provider.MobileCodeAuthenticationProvider;
import com.magic.service.UserDetailsServiceImpl;
import com.magic.vo.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutFilter;

/**
 * security web安全配置,spring-cloud-starter-oauth2依赖于security
 * 默认情况下SecurityConfigurerAdapter执行比ResourceServerConfig先
 *
 * @author magic_lz
 * @version 1.0
 * @classname SecurityConfig
 * @date 2020/9/2 : 17:53
 */

//开启Security
@EnableWebSecurity
// 开启注解配置支持
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private IgnoreLogoutFilter ignoreLogoutFilter;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(provider())
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**","/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin().loginPage("/login")
                .permitAll()
                .and().authorizeRequests().anyRequest().permitAll()
                .and().csrf().disable().cors()
                .and().addFilterAt(ignoreLogoutFilter, LogoutFilter.class);
    }

    /**
     * 配置密码加密对象（解密时会用到PasswordEncoder的matches判断是否正确）
     * 用户的password和客户端clientSecret用到，所以存的时候存该bean encode过的密码
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public MobileCodeAuthenticationProvider provider() {
        MobileCodeAuthenticationProvider provider = new MobileCodeAuthenticationProvider();
        provider.setStringRedisTemplate(stringRedisTemplate);
        provider.setHideUserNotFoundExceptions(false);
        provider.setUserDetailsService(userDetailsService);
        return provider;

    }
}
