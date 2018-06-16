package com.mishima.tripbuddy.drivingtimeservice;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mishima.tripbuddy.drivingtimeservice.entity.DrivingTimeQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;


@Component
@Slf4j
public class DrivingTimeHandler {

    @Autowired
    private GoogleDistanceAdapter adapter;

    private final LoadingCache<DrivingTimeQuery, Mono<ServerResponse>> cache = CacheBuilder.newBuilder()
            .maximumSize(100000)
            .build(new CacheLoader<DrivingTimeQuery, Mono<ServerResponse>>() {
                @Override
                public Mono<ServerResponse> load(DrivingTimeQuery query) {
                    return adapter.getDrivingTime(query).flatMap(response -> ServerResponse.ok().contentType(APPLICATION_JSON).body(fromObject(response)))
                            .switchIfEmpty(ServerResponse.notFound().build());
                }
            });

    public Mono<ServerResponse> getDrivingTime(ServerRequest request) {
        Mono<DrivingTimeQuery> queryMono = request.bodyToMono(DrivingTimeQuery.class);
        return queryMono.flatMap((Function<DrivingTimeQuery, Mono<ServerResponse>>) cache::getUnchecked);
    }



}
