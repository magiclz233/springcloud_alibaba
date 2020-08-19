package com.magic.rocketmq.filter;

import com.magic.rocketmq.quickstart.Producer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;

/**
 * 过滤消息 可以自定义 也可以根据 Tag
 *
 * @author magic_lz
 * @version 1.0
 * @date 2020/8/19 23:55
 */
public class SqlFilterProducer {
    public static void main(String[] args) throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer("default_producer_filter_demo_one");

        producer.setNamesrvAddr(Producer.NAME_SERVER_ADDR);

        producer.start();
        String[] tags = new String[]{"TagA", "TagB", "TagC"};
        for (int i = 0; i < 99; i++) {
            Message msg = new Message("TopicTest", tags[i % tags.length], ("Hello RocketMQ" + i).getBytes(StandardCharsets.UTF_8));
            msg.putUserProperty("a", String.valueOf(i));
            try {
                SendResult send = producer.send(msg);
                System.out.printf("%s%n", send);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        producer.shutdown();
    }
}
