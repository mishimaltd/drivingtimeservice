package com.mishima.tripbuddy.drivingtimeservice.web;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mishima.tripbuddy.drivingtimeservice.entity.DrivingTimeQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@RequestMapping(value = "/query")
@Component
@Slf4j
public class DrivingTimeHandler {

    @Autowired
    private GoogleDistanceAdapter adapter;

    @Nonnull
    public Mono<ServerResponse> getDrivingTime(ServerRequest request) {
        double originLat = Double.valueOf(request.pathVariable("originLat"));
        double originLng = Double.valueOf(request.pathVariable("originLng"));
        double destLat = Double.valueOf(request.pathVariable("destLat"));
        double destLng = Double.valueOf(request.pathVariable("destLng"));
        return cache.getUnchecked(new DrivingTimeQuery(new double[]{originLat, originLng}, new double[]{destLat, destLng}));
    }

    private final LoadingCache<DrivingTimeQuery, Mono<ServerResponse>> cache = CacheBuilder.newBuilder()
            .maximumSize(100000)
            .build(new CacheLoader<DrivingTimeQuery, Mono<ServerResponse>>() {
                @Nonnull
                public Mono<ServerResponse> load(@Nullable DrivingTimeQuery query) {
                    return adapter.getDrivingTime(query).flatMap(result -> ServerResponse.ok().contentType(APPLICATION_JSON)
                            .body(fromObject(result)))
                            .switchIfEmpty(ServerResponse.notFound().build());
                }
            });

}
