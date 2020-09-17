package com.meetup.consumer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AskForBeerRequest {
    private String name;
    private Integer age;
    private String beerName;
}
