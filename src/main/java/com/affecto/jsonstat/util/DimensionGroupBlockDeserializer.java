package com.affecto.jsonstat.util;

import com.affecto.jsonstat.dto.DimensionGroupBlock;
import com.affecto.jsonstat.dto.IndividualDimensionBlock;
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

public class DimensionGroupBlockDeserializer extends JsonDeserializer<DimensionGroupBlock> {

    private static final List<String> RESERVED = ImmutableList.of("id", "size", "role");

    private static IndividualDimensionBlock parseNodeToBlock(final JsonNode node, final JsonParser parser) {
        try {
            final JsonParser nodeParser = node.traverse();
            nodeParser.setCodec(parser.getCodec());
            return nodeParser.readValueAs(IndividualDimensionBlock.class);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static <T> Stream<T> iteratorAsStream(final Iterator<T> iterator) {
        return stream(spliteratorUnknownSize(iterator, Spliterator.ORDERED), false);
    }

    @Override
    public DimensionGroupBlock deserialize(final JsonParser p, final DeserializationContext c) throws IOException {
        final JsonNode node = p.getCodec().readTree(p);
        return DimensionGroupBlock.builder()
                .id(stream(node.get("id").spliterator(), false)
                        .map(JsonNode::asText)
                        .collect(Collectors.toList()))
                .size(stream(node.get("size").spliterator(), false)
                        .map(JsonNode::asInt)
                        .collect(Collectors.toList()))
                .role(iteratorAsStream(node.get("role").fields())
                                .collect(toMap(
                                        Map.Entry::getKey,
                                        e -> stream(e.getValue().spliterator(), false)
                                                .map(JsonNode::asText)
                                                .collect(Collectors.toList()),
                                        (a, b) -> a))
                )
                .dimensions(iteratorAsStream(node.fields())
                                .filter(e -> !RESERVED.contains(e.getKey()))
                                .collect(toMap(
                                        Map.Entry::getKey,
                                        e -> parseNodeToBlock(e.getValue(), p),
                                        (a, b) -> a))
                )
                .build();
    }
}
