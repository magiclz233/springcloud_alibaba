package com.magic.rocketmq.filter;

import com.magic.rocketmq.quickstart.Producer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * 过滤获取你想要的消息
 *
 * @author magic_lz
 * @version 1.0
 * @date 2020/8/20 0:11
 */
public class SqlFilterConsumer {
    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("default_consumer_filter_demo_one");
        consumer.setNamesrvAddr(Producer.NAME_SERVER_ADDR);
        // 只有订阅的消息有这个属性a, a>=0 and a <= 3
        consumer.subscribe("TopicTest",
                MessageSelector.bySql("a between 0 and 3"));

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) {
                System.out.println("Thread Name: " + Thread.currentThread().getName() + "~~" + list.toString());
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.out.println("Consumer Started");
    }
}
