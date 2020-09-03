package com.magic.system.exception;

import java.util.Map;

/**
 * @author magic_lz
 * @version 1.0
 * @classname UserNameNotFoundException
 * @date 2020/9/3 : 14:47
 */
public class RoleNotFoundException extends BaseException {
    public RoleNotFoundException(Map<String, Object> data) {
        super(ErrorCode.Role_NOT_FOUND, data);
    }
}
