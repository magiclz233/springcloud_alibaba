package com.magic.system.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author magic_lz
 * @version 1.0
 * @classname UserNameNotFoundException
 * @date 2020/9/3 : 14:47
 */
public class LoginFailedException extends AuthenticationException {
    public LoginFailedException(String detail) {
        super(detail);
    }
}
