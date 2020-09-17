package com.meetup.consumer.controllers;

import com.meetup.consumer.model.AskForBeerRequest;
import com.meetup.consumer.model.PersonCheckResponse;
import com.meetup.consumer.model.PersonToCheckRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class BeerController {

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping(value="/beers",produces = MediaType.APPLICATION_JSON_VALUE)
    public String askForBeer(@RequestBody AskForBeerRequest request) {
    
        //calling personcheck service
       ResponseEntity<PersonCheckResponse>  resp = restTemplate.postForEntity(
           "http://localhost:3000/check",
            new PersonToCheckRequest(request.getName(), request.getAge()), 
            PersonCheckResponse.class);
       
       switch (resp.getBody().getStatus()) {
           case "OK": {
                return "Yeah, There you go!";
           } 
           default: {
                return "Oh No, Get Lost!";
           }
       }
    }
    
}
