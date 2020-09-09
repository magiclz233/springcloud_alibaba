package com.magic.service;

import com.magic.entity.SysUser;
import org.springframework.stereotype.Service;

/**
 * @author magic_lz
 * @version 1.0
 * @classname SysUserService
 * @date 2020/9/9 : 16:51
 */
@Service
public class SysUserService {

    public SysUser selectByUsername(String username){
        return new SysUser("1","magic","$2a$10$SjSqaq3csnHrK.1PxBElwOcLa63rMsbo8S44cLwsjwQP/g3kwOz8e","用户名测试");
    }

    public SysUser selectByMobile(String mobile){
        return new SysUser("2","faker","$2a$10$SjSqaq3csnHrK.1PxBElwOcLa63rMsbo8S44cLwsjwQP/g3kwOz8e","手机号权限测试");
    }
}
