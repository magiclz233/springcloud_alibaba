package com.magic.kafka.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConsumerRecordRecoverer;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.ErrorHandler;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;

/**
 * @author magic_lz
 * @version 1.0
 * @date 2021/3/14 11:15
 */
@Configuration
public class KafkaConfig {

    @Bean
    @Primary
    public ErrorHandler kafkaErrorHandler(KafkaTemplate<?, ?> template) {
        ConsumerRecordRecoverer recoverer = new DeadLetterPublishingRecoverer(template);
        BackOff backOff = new FixedBackOff(10 * 1000L, 3L);
        return new SeekToCurrentErrorHandler(recoverer, backOff);
    }
}
