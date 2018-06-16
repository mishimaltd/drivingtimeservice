package com.mishima.tripbuddy.drivingtimeservice;

import com.mishima.tripbuddy.drivingtimeservice.entity.DrivingTimeQuery;
import com.mishima.tripbuddy.drivingtimeservice.entity.DrivingTimeResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class DrivingTimeServiceRouterTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testQuery() {
        DrivingTimeQuery query = new DrivingTimeQuery(new double[]{35.8861980, -79.0601160},new double[]{35.8265400, -78.7963890});
        post(query).expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody(DrivingTimeResponse.class).isEqualTo(new DrivingTimeResponse(1715,32769d));
    }

    @Test
    public void testUnresolvableDistance() {
        DrivingTimeQuery query = new DrivingTimeQuery(new double[]{300, 999},new double[]{800,300});
        post(query).expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }


    private WebTestClient.ResponseSpec post(DrivingTimeQuery query) {
        return webTestClient.post()
                .uri("/query")
                .body(Mono.just(query), DrivingTimeQuery.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange();
    }

}
