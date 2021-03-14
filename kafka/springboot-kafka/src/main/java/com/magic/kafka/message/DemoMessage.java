package com.magic.kafka.message;

/**
 * @author magic_lz
 * @version 1.0
 * @date 2021/3/14 11:19
 */
public class DemoMessage {
    public static final String TOPIC = "springboot-kafka";

    private Integer id;
    public DemoMessage setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Demo01Message{" +
                "id=" + id +
                '}';
    }
}
