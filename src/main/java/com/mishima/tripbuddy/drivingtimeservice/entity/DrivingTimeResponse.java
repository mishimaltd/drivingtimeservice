package com.mishima.tripbuddy.drivingtimeservice.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class DrivingTimeResponse {

    private final int durationInSeconds;
    private final double distanceInMetres;

}
