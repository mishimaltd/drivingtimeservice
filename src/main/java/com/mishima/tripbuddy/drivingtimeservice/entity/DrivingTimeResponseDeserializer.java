package com.mishima.tripbuddy.drivingtimeservice.entity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class DrivingTimeResponseDeserializer extends StdDeserializer<DrivingTimeResponse> {

    public DrivingTimeResponseDeserializer() {
        this(null);
    }

    public DrivingTimeResponseDeserializer(Class<?> vc) {
        super(vc);
    }

    public DrivingTimeResponse deserialize(JsonParser jp, DeserializationContext ctx) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        int durationInSeconds = node.get("durationInSeconds").intValue();
        double distanceInMetres = node.get("distanceInMetres").doubleValue();
        return new DrivingTimeResponse(durationInSeconds, distanceInMetres);
    }

}
