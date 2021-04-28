package com.magic.rocketmq.batch;

import com.magic.rocketmq.quickstart.Producer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * 当批量发送的消息量小于4m时,直接发送即可
 *
 * @author magic_lz
 * @version 1.0
 * @date 2020/8/19 23:42
 */
public class SimpleBatchProducer {
    public static void main(String[] args) throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer("default_simple_batch_producer_demo_one");
        producer.setNamesrvAddr(Producer.NAME_SERVER_ADDR);
        producer.start();
        
        String topic = "TopicTest";
        List<Message> msgs = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            msgs.add(new Message(topic, "TagA", "OrderId" + i, ("Hello World" + i).getBytes()));
        }
        
        try {
            producer.send(msgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        producer.shutdown();
    }
}
