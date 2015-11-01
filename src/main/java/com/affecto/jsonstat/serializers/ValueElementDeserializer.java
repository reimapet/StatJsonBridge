package com.affecto.jsonstat.serializers;

import com.affecto.jsonstat.elements.ValueElement;
import com.affecto.jsonstat.util.StreamingJsonDeserializer;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public class ValueElementDeserializer extends StreamingJsonDeserializer<ValueElement> {
    @Override
    public ValueElement deserialize(final JsonParser parser, final DeserializationContext ctxt)
            throws IOException
    {
        final JsonNode node = parser.getCodec().readTree(parser);
        if (node.isArray()) {
            return new ValueElement().setList(iteratorAsStream(node.iterator())
                    .map(JsonNode::numberValue)
                    .collect(Collectors.toList()));
        } else if (node.isObject()) {
            return new ValueElement().setMap(iteratorAsStream(node.fields()).collect(toMap(
                    Map.Entry::getKey,
                    e -> e.getValue().numberValue(),
                    (a, b) -> a)));
        } else {
            return null;
        }
    }
}
