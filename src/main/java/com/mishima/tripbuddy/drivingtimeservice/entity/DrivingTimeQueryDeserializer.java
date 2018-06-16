package com.mishima.tripbuddy.drivingtimeservice.entity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class DrivingTimeQueryDeserializer extends StdDeserializer<DrivingTimeQuery> {

    public DrivingTimeQueryDeserializer() {
        this(null);
    }

    public DrivingTimeQueryDeserializer(Class<?> vc) {
        super(vc);
    }

    public DrivingTimeQuery deserialize(JsonParser jp, DeserializationContext ctx) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        JsonNode origin = node.get("origin");
        double[] from = {origin.get(0).asDouble(),origin.get(1).asDouble()};
        JsonNode destination = node.get("destination");
        double[] to = {destination.get(0).asDouble(),destination.get(1).asDouble()};
        return new DrivingTimeQuery(from, to);
    }

}
