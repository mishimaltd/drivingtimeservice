package com.mishima.tripbuddy.drivingtimeservice;

import com.mishima.tripbuddy.drivingtimeservice.entity.DrivingTimeQuery;
import com.mishima.tripbuddy.drivingtimeservice.entity.DrivingTimeResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class GoogleDistanceAdapterTest {

    @Autowired
    private GoogleDistanceAdapter adapter;

    @Test
    public void testGetDistance() {
        double[] origin = {35.8861980, -79.0601160};
        double[] destination = {35.8265400, -78.7963890};
        DrivingTimeQuery query = new DrivingTimeQuery(origin, destination);
        DrivingTimeResponse response = adapter.getDrivingTime(query).block();
        assertEquals(1715, response.getDurationInSeconds());
        assertEquals(32769d, response.getDistanceInMetres(), 0d);
    }



}
