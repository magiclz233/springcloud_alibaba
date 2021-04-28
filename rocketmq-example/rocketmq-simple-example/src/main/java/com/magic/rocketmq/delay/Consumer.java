package com.magic.rocketmq.delay;

import com.magic.rocketmq.quickstart.Producer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author magic_lz
 * @version 1.0
 * @classname Consumer
 * @date 2020/8/19 : 16:35
 */
public class Consumer {
    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("default_consumer_delay_demo_one");
        consumer.setNamesrvAddr(Producer.NAME_SERVER_ADDR);
        consumer.subscribe("TopicTest","*");
        // 并发消费 MessageListenerConcurrently
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) {
                for (MessageExt msg : list) {
                    System.out.println("Receive message[msgId=" + msg.getMsgId() + "] " + (System.currentTimeMillis() - msg.getStoreTimestamp()) + "ms later");
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.out.println("Consumer started");
    }
}
