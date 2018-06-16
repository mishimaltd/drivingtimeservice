package com.mishima.tripbuddy.drivingtimeservice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class DrivingTimeServiceRouterTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testQuery() throws Exception {
        String result = webTestClient.get()
                .uri("/query/{originLat}/{originLng}/{destLat}/{destLng}", 35.8861980, -79.0601160, 35.8265400, -78.7963890)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody(String.class).returnResult().getResponseBody();
        Map<String,Object> map = new ObjectMapper().readValue(result, new TypeReference<Map<String,Object>>(){});
        assertEquals(1715, map.get("durationInSeconds"));
        assertEquals(32769d, map.get("distanceInMetres"));

    }

    @Test
    public void testUnresolvableDistance() {
        webTestClient.get()
                .uri("/query/{originLat}/{originLng}/{destLat}/{destLng}", 300, 100, 200, 400)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }

}
