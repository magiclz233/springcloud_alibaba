package com.magic.rocketmq.quickstart;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;

/**
 * 单向发送消息
 * @author magic_lz
 * @version 1.0
 * @classname UnilateralProducer
 * @date 2020/8/19 : 14:36
 */
public class UnilateralProducer {
    public static void main(String[] args) throws RemotingException, MQClientException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("default_producer_unilateral_demo_one");

        producer.setNamesrvAddr(Producer.NAME_SERVER_ADDR);

        producer.start();

        for (int i = 0; i < 100; i++) {
            Message msg = new Message("TopicTest", "TagA", ("Hello RocketMQ" + i).getBytes(StandardCharsets.UTF_8));
            producer.sendOneway(msg);
        }

        producer.shutdown();
    }
}
