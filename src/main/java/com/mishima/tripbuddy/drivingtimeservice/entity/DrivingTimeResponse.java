package com.mishima.tripbuddy.drivingtimeservice.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@JsonDeserialize(using = DrivingTimeResponseDeserializer.class)
public class DrivingTimeResponse {

    private final int durationInSeconds;
    private final double distanceInMetres;

}
