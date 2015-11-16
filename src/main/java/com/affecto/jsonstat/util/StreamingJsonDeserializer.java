package com.affecto.jsonstat.util;

import com.affecto.jsonstat.blocks.IndividualDimensionBlock;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.stream.Stream;

import static java.util.Spliterators.spliteratorUnknownSize;
import static java.util.stream.StreamSupport.stream;

public abstract class StreamingJsonDeserializer<T> extends JsonDeserializer<T> {

    protected static IndividualDimensionBlock parseNodeToBlock(final JsonNode node, final JsonParser parser) {
        try {
            final JsonParser nodeParser = node.traverse();
            nodeParser.setCodec(parser.getCodec());
            return nodeParser.readValueAs(IndividualDimensionBlock.class);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    protected static <T> Stream<T> iteratorAsStream(final Iterator<T> iterator) {
        return stream(spliteratorUnknownSize(iterator, Spliterator.ORDERED), false);
    }

}
