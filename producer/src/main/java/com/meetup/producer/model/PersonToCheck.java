package com.meetup.producer.model;

import lombok.*;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PersonToCheck {

    private String name;
    private Integer age;
}
