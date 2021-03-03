package com.magic.lock.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author magic_lz
 * @version 1.0
 * @classname Person
 * @date 2021/3/3 : 17:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private Integer id;

    private String name;
}
