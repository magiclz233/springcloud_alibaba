package com.magic.rocketmq.quickstart;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.Arrays;
import java.util.List;

/**
 * @author magic_lz
 * @version 1.0
 * @classname Consumer
 * @date 2020/8/19 : 10:58
 */
public class Consumer {
    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("default_push_consumer_demo_one");

        consumer.setNamesrvAddr(Producer.NAME_SERVER_ADDR);
        //  如果指定的消费群体是一个全新的消费群体，请指定从何处开始。
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe("TopicTest", "*");

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) {
                // 为什么要用list存储,因为可以一次传多个 Message 过来组成 List<Message>
                System.out.println(list.size());
                for (MessageExt msg : list) {
                    byte[] body = msg.getBody();
                    System.out.println("传输过来的消息"+ new String(body));
                }
                System.out.println(Thread.currentThread().getName() + "  :  " + list.toString());
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();

        System.out.println("Consumer Started");
    }
}
