package com.magic.kafka;

import com.magic.kafka.producer.Demo01Producer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

/**
 * @author magic_lz
 * @version 1.0
 * @date 2021/3/14 11:35
 */

@Slf4j
@SpringBootTest
public class KafkaTest {
    @Resource
    private Demo01Producer demo01Producer;
    
    /**
     * 同步
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void Demo01ProducerTest() throws ExecutionException, InterruptedException {
        int id = (int) System.currentTimeMillis();
        SendResult sendResult = demo01Producer.syncSend(id);
        log.info("Demo01ProducerTest-发送id-{}, 返回结果-{}",id, sendResult);
        new CountDownLatch(1).await();
    }
    
    /**
     * 异步
     * @throws InterruptedException
     */
    @Test
    public void Demo01AsyncTest() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        demo01Producer.asyncSend(id).addCallback(new ListenableFutureCallback<SendResult<Object, Object>>() {
            @Override
            public void onFailure(Throwable e) {
                log.info("[testASyncSend][发送编号：[{}] 发送异常]]", id, e);
            }
    
            @Override
            public void onSuccess(SendResult<Object, Object> result) {
                log.info("[testASyncSend][发送编号：[{}] 发送成功，结果为：[{}]]", id, result);
            }
        });
        new CountDownLatch(1).await();
    }
}
