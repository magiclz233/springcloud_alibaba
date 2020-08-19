package com.magic.rocketmq.ordermessage;

import com.magic.rocketmq.quickstart.Producer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 顺序消费消息
 * @author magic_lz
 * @version 1.0
 * @classname Consumer
 * @date 2020/8/19 : 15:40
 */
public class Consumer {

    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("default_consumer_order_demo_one");
        consumer.setNamesrvAddr(Producer.NAME_SERVER_ADDR);

         // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
         //如果非第一次启动,那么按照上次消费的位置继续消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        // 指定 topic和tags
        consumer.subscribe("TopicTest","TagA || TagC || TagD");
        consumer.registerMessageListener(new MessageListenerOrderly() {
            Random random = new Random();
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext context) {
                context.setAutoCommit(true);
                for (MessageExt msg : list) {
                    System.out.println("consumerThread = "+Thread.currentThread().getName() + "queueId" + msg.getQueueId()+ ", content: " + new String(msg.getBody()));
                }
                try {
                    TimeUnit.SECONDS.sleep(random.nextInt(10));
                }catch (Exception e){
                    e.printStackTrace();
                }
                return ConsumeOrderlyStatus.SUCCESS;

            }
        });
        consumer.start();
        System.out.println("Consumer Started");
    }
}
