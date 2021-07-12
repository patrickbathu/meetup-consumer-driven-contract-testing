package com.meetup.producer.controllers;

import com.meetup.producer.model.BeerCheckResponse;
import com.meetup.producer.model.BeerCheckStatus;
import com.meetup.producer.model.PersonToCheck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Slf4j
@RestController
public class ProducerController {

    public static final Integer MINIMUM_AGE_PERMITTED = 18;

    @PostMapping (value = "/check", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BeerCheckResponse checkPersonForBeer(@RequestBody  PersonToCheck person) {

        if(person.getAge() >= MINIMUM_AGE_PERMITTED) {
            return new BeerCheckResponse(BeerCheckStatus.OK);
        } else {
            return new BeerCheckResponse(BeerCheckStatus.NOT_OK);
        }
    }

    @Autowired
    private RabbitTemplate template;

    @PostMapping(path = "/send/person/")
    public void sendPerson(@RequestBody PersonToCheck person) {
        log.info("Sends message [{}]", person.toString());
        this.template.convertAndSend("topic1", "#", person);
    }

}