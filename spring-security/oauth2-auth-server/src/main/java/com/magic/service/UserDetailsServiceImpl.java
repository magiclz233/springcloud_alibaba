package com.magic.service;

import com.magic.entity.SysAuthority;
import com.magic.entity.SysUser;
import com.magic.vo.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
        UserDetailsImpl userDetail = new UserDetailsImpl();
        userDetail.setEnable(true);
        BeanUtils.copyProperties(sysUser,userDetail);
        //这里权限列表,这个为方便直接下（实际开发中查询用户时连表查询出权限）
        Set<SysAuthority> authoritySet = new HashSet<>();
        authoritySet.add(new SysAuthority("admin","管理员权限"));
        userDetail.setAuthorities(authoritySet);
        return userDetail;
    }


    public UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException {
        log.info("手机号模式查询用户信息");
        SysUser sysUser = sysUserService.selectByMobile(mobile);
        if (sysUser == null) {
            throw new UsernameNotFoundException("not found mobile user:" + mobile);
        }
        UserDetailsImpl userDetail = new UserDetailsImpl();
        BeanUtils.copyProperties(sysUser,userDetail);
        userDetail.setAuthorities(new ArrayList<>());
        userDetail.setEnable(true);
        return userDetail;
    }
}
