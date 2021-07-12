package com.meetup.consumer.beer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cloud.contract.verifier.messaging.MessageVerifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
public class TestConfig {

    @Bean
    MessageVerifier<Message> testMessageVerifier(RabbitTemplate rabbitTemplate) {
        return new MessageVerifier() {

            @Override
            public void send(Object message, String destination) {
                rabbitTemplate.convertAndSend(destination, message);
            }

            @Override
            public void send(Object payload, Map headers, String destination) {

            }

            @Override
            public Object receive(String destination, long timeout, TimeUnit timeUnit) {
                return null;
            }

            @Override
            public Object receive(String destination) {
                return null;
            }
        };
    }
}
