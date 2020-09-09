package com.magic.service;

import com.magic.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author magic_lz
 * @version 1.0
 * @classname UserDetailsServiceImpl
 * @date 2020/9/9 : 16:48
 */

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("密码模式查询用户信息");
        SysUser sysUser = sysUserService.selectByUsername(username);
        if (sysUser == null) {
            throw new UsernameNotFoundException("not found user:" + username);
        }
        return null;
    }
}
