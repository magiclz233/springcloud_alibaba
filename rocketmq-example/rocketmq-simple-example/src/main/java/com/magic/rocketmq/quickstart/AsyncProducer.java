package com.magic.rocketmq.quickstart;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.CountDownLatch2;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * 生产者发送异步消息
 *
 * @author magic_lz
 * @version 1.0
 * @classname AsyncProducer
 * @date 2020/8/19 : 13:48
 */
public class AsyncProducer {

    public static void main(String[] args) throws MQClientException, InterruptedException, RemotingException {
        DefaultMQProducer producer = new DefaultMQProducer("default_producer_sync_demo_one");
        producer.setNamesrvAddr(Producer.NAME_SERVER_ADDR);
        producer.start();
        final int messageCount = 100;
        // 根据消息数量实例化倒计时计数器
        CountDownLatch2 countDownLatch = new CountDownLatch2(messageCount);

        for (int i = 0; i < messageCount; i++) {
            final int index = i;
            Message msg = new Message("TopicTest",
                    "TagA",
                    "OrderId188",
                    (index + "Hello world").getBytes(StandardCharsets.UTF_8));
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.printf("%-10d OK %s %n", index,
                            sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable e) {
                    System.out.printf("%-10d Exception %s %n", index, e);
                    e.printStackTrace();
                }
            });
        }
        countDownLatch.await(5, TimeUnit.SECONDS);
        producer.shutdown();
    }
}
