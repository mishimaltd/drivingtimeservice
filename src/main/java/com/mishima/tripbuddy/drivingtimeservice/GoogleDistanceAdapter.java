package com.mishima.tripbuddy.drivingtimeservice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mishima.tripbuddy.drivingtimeservice.entity.DrivingTimeQuery;
import com.mishima.tripbuddy.drivingtimeservice.entity.DrivingTimeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class GoogleDistanceAdapter {

    private final ObjectMapper om = new ObjectMapper();
    private final TypeReference tr = new TypeReference<Map<String,Object>>(){};

    @Value("#{environment.GOOGLE_API_KEY}")
    private String apiKey;

    private WebClient client = WebClient.create("https://maps.googleapis.com");

    public Mono<DrivingTimeResponse> getDrivingTime(DrivingTimeQuery query) {
        String origin = query.getOrigin()[0] + "," + query.getOrigin()[1];
        String destination = query.getDestination()[0] + "," + query.getDestination()[1];
        return client.get()
                .uri("/maps/api/distancematrix/json?origins={origin}&destinations={destination}&mode=driving&key={key}", origin, destination, apiKey)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .flatMap(response -> response.bodyToMono(String.class))
                .flatMap(drivingResponseMapper);
    }


    @SuppressWarnings("unchecked")
    private Function<String,Mono<DrivingTimeResponse>> drivingResponseMapper = json -> {
        try {
            Map<String, Object> map = om.readValue(json, tr);
            if ("OK".equals(map.get("status"))) {
                List<Map<String, Object>> rows = (List<Map<String, Object>>) map.get("rows");
                List<Map<String, Object>> elements = (List<Map<String, Object>>) rows.get(0).get("elements");
                if ("OK".equals(elements.get(0).get("status"))) {
                    Map<String, Object> durationDetails = (Map<String, Object>) elements.get(0).get("duration");
                    Map<String, Object> distanceDetails = (Map<String, Object>) elements.get(0).get("distance");
                    int duration = Integer.valueOf(durationDetails.get("value").toString());
                    double distance = Double.valueOf(distanceDetails.get("value").toString());
                    return Mono.just(new DrivingTimeResponse(duration, distance));
                }
            }
            return Mono.empty();

        } catch( Exception ex ) {
            return Mono.empty();
        }
    };


}
