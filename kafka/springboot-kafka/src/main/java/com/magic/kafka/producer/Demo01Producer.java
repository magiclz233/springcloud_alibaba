package com.magic.kafka.producer;

import com.magic.kafka.message.DemoMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;

/**
 * @author magic_lz
 * @version 1.0
 * @date 2021/3/14 11:20
 */
@Component
public class Demo01Producer {

    @Resource
    private KafkaTemplate<Object, Object> template;

    public SendResult syncSend(Integer id) throws ExecutionException, InterruptedException {
        DemoMessage message = new DemoMessage();
        message.setId(id);
        // 同步发送消息，后面跟一个get()锁住
        return template.send(DemoMessage.TOPIC, message).get();
    }

    public ListenableFuture<SendResult<Object,Object>> asyncSend(Integer id){
        DemoMessage message = new DemoMessage();
        message.setId(id);
        // 异步发送消息
        return template.send(DemoMessage.TOPIC, message);
    }
}
