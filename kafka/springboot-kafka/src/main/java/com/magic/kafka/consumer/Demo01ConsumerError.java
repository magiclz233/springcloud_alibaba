package com.magic.kafka.consumer;

import com.magic.kafka.message.DemoMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author magic_lz
 * @version 1.0
 * @date 2021/3/14 13:25
 */
@Slf4j
@Component
public class Demo01ConsumerError {


//    @KafkaListener(topics = DemoMessage.TOPIC,
//            groupId = "demo04-consumer-group-" + DemoMessage.TOPIC)
    public void onMessage(DemoMessage message) {
        log.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
        // 注意，此处抛出一个 RuntimeException 异常，模拟消费失败
        throw new RuntimeException("我就是故意抛出一个异常");
    }

}
