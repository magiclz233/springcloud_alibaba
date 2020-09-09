package com.magic.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author magic_lz
 * @version 1.0
 * @classname SysUser
 * @date 2020/9/9 : 16:50
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUser {

    private String id;

    private String username;

    private String password;

    private String test;
}
