package com.meetup.consumer.beer;

import com.github.tomakehurst.wiremock.common.Json;
import com.meetup.consumer.model.AskForBeerRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties.StubsMode;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureStubRunner(ids = "com.meetup:producer:+:stubs:3000", stubsMode = StubsMode.LOCAL)
@AutoConfigureJsonTesters
public class BeerControllerTests {
    
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_give_me_a_beer_when_im_old_enough() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/beers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(Json.write(new AskForBeerRequest("Bathu",18,"Heineken"), AskForBeerRequest.class)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("Yeah, There you go!"));

    }
    
    @Test
    public void should_reject_a_beer_when_im_too_young() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/beers")
        .contentType(MediaType.APPLICATION_JSON)
        .content(Json.write(new AskForBeerRequest("Bathu",17,"Heineken"), AskForBeerRequest.class)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("Oh No, Get Lost!"));
    }
}
