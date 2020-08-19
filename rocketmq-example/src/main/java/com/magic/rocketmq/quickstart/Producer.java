package com.magic.rocketmq.quickstart;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;


/**
 * 此类演示如何使用 {@link DefaultMQProducer} 来发送Message到Broker
 *
 * @author magic_lz
 * @version 1.0
 * @classname Producer
 * @date 2020/8/19 : 10:39
 */

public class Producer {
    public static final String NAME_SERVER_ADDR = "49.233.129.28:9876";
    public static void main(String[] args) throws MQClientException, InterruptedException {
        // 实例化生产者Producer, 并赋值组名
        DefaultMQProducer producer = new DefaultMQProducer("default_producer_demo_one");
        // 设置NameServer的地址
        producer.setNamesrvAddr(NAME_SERVER_ADDR);
        // 启动
        producer.start();
        for (int i = 0; i < 100; i++) {
            try {
                // 创建消息, 并制定Topic, Tag和消息体
                Message msg = new Message("TopicTest", "TagA", ("Hello RocketMQ --> " + i).getBytes(StandardCharsets.UTF_8));

                //呼叫发送消息以将消息传递给其中一个代理。
                SendResult sendResult = producer.send(msg);
                System.out.printf("%s%n", sendResult);
            }catch (Exception e){
                e.printStackTrace();
                Thread.sleep(1000);
            }
        }
        producer.shutdown();
    }
}
