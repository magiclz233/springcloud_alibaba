package com.magic.system.exception;

import java.util.Map;

/**
 * @author magic_lz
 * @version 1.0
 * @classname UserNameNotFoundException
 * @date 2020/9/3 : 14:47
 */
public class UserNameAlreadyExistException extends BaseException {

    public UserNameAlreadyExistException(Map<String, Object> data) {
        super(ErrorCode.USER_NAME_ALREADY_EXIST, data);
    }
}
