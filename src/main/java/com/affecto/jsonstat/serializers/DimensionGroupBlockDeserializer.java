package com.affecto.jsonstat.serializers;

import com.affecto.jsonstat.blocks.DimensionGroupBlock;
import com.affecto.jsonstat.blocks.IndividualDimensionBlock;
import com.affecto.jsonstat.util.StreamingJsonDeserializer;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableList;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Spliterators.spliteratorUnknownSize;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.StreamSupport.stream;

public class DimensionGroupBlockDeserializer extends StreamingJsonDeserializer<DimensionGroupBlock> {

    private static final List<String> RESERVED = ImmutableList.of("id", "size", "role");

    @Override
    public DimensionGroupBlock deserialize(final JsonParser parser, final DeserializationContext c)
            throws IOException
    {
        final JsonNode node = parser.getCodec().readTree(parser);
        final DimensionGroupBlock.Builder builder = DimensionGroupBlock.builder();

        builder.id(stream(node.get("id").spliterator(), false)
                .map(JsonNode::asText)
                .collect(Collectors.toList()));

        builder.size(stream(node.get("size").spliterator(), false)
                .map(JsonNode::asInt)
                .collect(Collectors.toList()));

        if (node.has("role")) {
            builder.role(iteratorAsStream(node.get("role").fields())
                            .collect(toMap(
                                    Map.Entry::getKey,
                                    e -> stream(e.getValue().spliterator(), false)
                                            .map(JsonNode::asText)
                                            .collect(Collectors.toList()),
                                    (a, b) -> a))
            );
        }

        builder.dimensions(iteratorAsStream(node.fields())
                        .filter(e -> !RESERVED.contains(e.getKey()))
                        .collect(toMap(
                                Map.Entry::getKey,
                                e -> parseNodeToBlock(e.getValue(), parser),
                                (a, b) -> a))
        );

        return builder.build();
    }
}
