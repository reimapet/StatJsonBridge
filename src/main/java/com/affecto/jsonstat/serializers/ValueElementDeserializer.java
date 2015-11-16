package com.affecto.jsonstat.serializers;

import com.affecto.jsonstat.elements.ValueElement;
import com.affecto.jsonstat.util.StreamingJsonDeserializer;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ValueElementDeserializer extends StreamingJsonDeserializer<ValueElement> {

    /* Because Collectors.toMap doesn't support null map values. */
    private static Map<String, Number> asNullableValueMap(final Stream<Map.Entry<String, JsonNode>> stream) {
        final Map<String, Number> ret = new HashMap<>();
        stream.forEach(e -> ret.put(e.getKey(), e.getValue().numberValue()));
        return ret;
    }

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
            return new ValueElement().setMap(asNullableValueMap(iteratorAsStream(node.fields())));
        } else {
            return null;
        }
    }
}
