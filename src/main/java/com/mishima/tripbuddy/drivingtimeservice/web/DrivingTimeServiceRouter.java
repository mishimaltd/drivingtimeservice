package com.mishima.tripbuddy.drivingtimeservice.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class DrivingTimeServiceRouter {

    @Bean
    public RouterFunction<ServerResponse> route(DrivingTimeHandler handler) {
        return RouterFunctions.route(GET("/query/{originLat}/{originLng}/{destLat}/{destLng}")
                .and(accept(APPLICATION_JSON)), handler::getDrivingTime);
    }

}
