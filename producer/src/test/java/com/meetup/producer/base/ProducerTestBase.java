package com.meetup.producer.base;

import com.meetup.producer.controllers.ProducerController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public abstract class ProducerTestBase {

    @Before
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(new ProducerController());
    }

}
