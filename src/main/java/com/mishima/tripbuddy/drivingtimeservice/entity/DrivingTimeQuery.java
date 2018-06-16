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

@JsonDeserialize(using = DrivingTimeQueryDeserializer.class)
public class DrivingTimeQuery {

    private final double[] origin;
    private final double[] destination;

}