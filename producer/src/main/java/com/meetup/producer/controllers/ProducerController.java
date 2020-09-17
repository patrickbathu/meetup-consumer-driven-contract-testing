package com.meetup.producer.controllers;

import com.meetup.producer.model.BeerCheckResponse;
import com.meetup.producer.model.BeerCheckStatus;
import com.meetup.producer.model.PersonToCheck;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}