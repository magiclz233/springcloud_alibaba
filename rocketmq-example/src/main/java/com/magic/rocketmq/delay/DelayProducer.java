package com.magic.rocketmq.delay;

import com.magic.rocketmq.quickstart.Producer;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.hook.SendMessageContext;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * 延时发送消息
 * @author magic_lz
 * @version 1.0
 * @classname DelayProducer
 * @date 2020/8/19 : 16:35
 */
public class DelayProducer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("default_producer_delay_demo_one");

        producer.setNamesrvAddr(Producer.NAME_SERVER_ADDR);
        producer.start();

        int total = 100;
        for (int i = 0; i < 100; i++) {
            Message message = new Message("TestTopic", ("Hello scheduled message " + i).getBytes());
            // 设置延时等级3,这个消息将在10s之后发送(现在只支持固定的几个时间,详看delayTimeLevel)
            message.setDelayTimeLevel(3);
            // 发送消息
            producer.send(message);
        }
        producer.shutdown();
    }
}

/*
验证
您将会看到消息的消费比存储时间晚10秒。

延时消息的使用场景
比如电商里，提交了一个订单就可以发送一个延时消息，1h后去检查这个订单的状态，如果还是未付款就取消订单释放库存。

延时消息的使用限制

// org/apache/rocketmq/store/config/MessageStoreConfig.java

private String messageDelayLevel = "1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h";

现在RocketMq并不支持任意时间的延时，需要设置几个固定的延时等级，从1s到2h分别对应着等级1到18
消息消费失败会进入延时消息队列，消息发送时间与设置的延时等级和重试次数有关，详见代码`SendMessageProcessor.java`
 */