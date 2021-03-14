package com.magic.kafka;

import com.magic.kafka.producer.Demo01Producer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.support.SendResult;

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

    @Test
    public void Demo01ProducerTest() throws ExecutionException, InterruptedException {
        int id = (int) System.currentTimeMillis();
        SendResult sendResult = demo01Producer.syncSend(id);
        log.info("Demo01ProducerTest-发送id-{}, 返回结果-{}",id, sendResult);
        new CountDownLatch(1).await();
    }
}
