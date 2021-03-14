package com.magic.kafka.consumer;

import com.magic.kafka.message.DemoMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author magic_lz
 * @version 1.0
 * @date 2021/3/14 11:31
 */

@Slf4j
@Component
public class Demo01Consumer {

    @KafkaListener(topics = DemoMessage.TOPIC, groupId = "demo-message-01-group" + DemoMessage.TOPIC)
    public void onMessage(DemoMessage message) {
        log.info("Demo01Consumer-[onMessage]-[线程编号-{}]-[消息内容-{}]",Thread.currentThread().getId(), message);
    }
}
