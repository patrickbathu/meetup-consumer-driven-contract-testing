package com.meetup.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.cloud.contract.verifier.messaging.MessageVerifier;
import org.springframework.messaging.Message;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RabbitMessageVerifier implements MessageVerifier<Message> {

    private final LinkedBlockingQueue<Message> queue = new LinkedBlockingQueue<>();

    @Override
    public Message receive(String destination, long timeout, TimeUnit timeUnit) {
        try {
            return queue.poll(timeout, timeUnit);
        }
        catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    @RabbitListener(id = "foo", queues = "topic1")
    public void listen(Message message) {
        log.info("Got a message! [{}]", message);
        queue.add(message);
    }

    @Override
    public Message receive(String destination) {
        return receive(destination, 1, TimeUnit.SECONDS);
    }

    @Override
    public void send(Message message, String destination) {

    }

    @Override
    public <T> void send(T payload, Map<String, Object> headers, String destination) {

    }
}
