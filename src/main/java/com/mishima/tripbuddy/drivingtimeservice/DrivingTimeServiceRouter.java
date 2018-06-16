package com.mishima.tripbuddy.drivingtimeservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class DrivingTimeServiceRouter {

    @Bean
    public RouterFunction<ServerResponse> route(DrivingTimeHandler handler) {
        return RouterFunctions.route(RequestPredicates.POST("/query")
                .and(RequestPredicates.accept(APPLICATION_JSON)), handler::getDrivingTime);
    }

}
