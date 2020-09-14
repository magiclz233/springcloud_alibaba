package com.magic.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author magic_lz
 * @version 1.0
 * @classname SysAuthority
 * @date 2020/9/9 : 16:50
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "authority")
public class SysAuthority implements GrantedAuthority {
    /**
     * 权限
     */
    private String authority;

    /**
     * 权限描述
     */
    private String desc;
}
