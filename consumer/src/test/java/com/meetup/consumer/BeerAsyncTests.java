package com.meetup.consumer;


import com.meetup.consumer.beer.TestConfig;
import org.assertj.core.api.BDDAssertions;
import org.awaitility.Awaitility;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.StubTrigger;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties.StubsMode;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = { TestConfig.class, ConsumerApplication.class }, properties = "stubrunner.amqp.mockConnection=false")
@AutoConfigureStubRunner(ids = "com.meetup.producer.base:producer:+:stubs:3000", stubsMode = StubsMode.LOCAL)
@Testcontainers
public class BeerAsyncTests {

    @Container
    static RabbitMQContainer rabbit = new RabbitMQContainer();

    @DynamicPropertySource
    static void rabbitProperties(DynamicPropertyRegistry registry) {
        rabbit.start();
        registry.add("spring.rabbitmq.port", rabbit::getAmqpPort);
    }

    @Autowired
    StubTrigger trigger;
    @Autowired
    ConsumerApplication application;

    @Test
    public void contextLoads() {
        System.out.println(this.trigger);

        Awaitility.await().untilAsserted(() -> {
            BDDAssertions.then(this.application.storedFoo).isNotNull();
            BDDAssertions.then(this.application.storedFoo.getName()).contains("Bathu");
        });
    }
}
