package com.meetup.producer;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierMessage;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

@Configuration
public class TestConfig {

    @Bean
    RabbitMessageVerifier rabbitTemplateMessageVerifier() {
        return new RabbitMessageVerifier();
    }

    @Bean
    ContractVerifierMessaging<Message> rabbitContractVerifierMessaging(RabbitMessageVerifier messageVerifier) {
        return new ContractVerifierMessaging<Message>(messageVerifier) {

            @Override
            protected ContractVerifierMessage convert(Message receive) {
                if (receive == null) {
                    return null;
                }
                return new ContractVerifierMessage(receive.getPayload(), receive.getHeaders());
            }

        };
    }

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    Exchange myExchange() {
        return new TopicExchange("topic1");
    }

    @Bean
    Queue myQueue() {
        return new Queue("topic1");
    }

    @Bean
    Binding myBinding() {
        return BindingBuilder.bind(myQueue()).to(myExchange())
                .with("#").noargs();
    }

}
