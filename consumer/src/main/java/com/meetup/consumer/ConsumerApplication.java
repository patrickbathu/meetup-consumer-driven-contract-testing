package com.meetup.consumer;

import com.meetup.consumer.model.PersonCheckResponse;
import com.meetup.consumer.model.PersonToCheckRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@Slf4j
@SpringBootApplication
public class ConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
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

	PersonToCheckRequest storedFoo;

	@RabbitListener(id = "fooGroup", queues = "topic1")
	public void listen(PersonToCheckRequest person) {
		log.info("Received: " + person);
		if (person.getName().startsWith("fail")) {
			throw new RuntimeException("failed");
		}
		this.storedFoo = person;
	}

}
