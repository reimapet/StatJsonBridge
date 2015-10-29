package com.affecto.jsonstat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.stream.Collectors;

import static java.util.Spliterators.spliteratorUnknownSize;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.StreamSupport.stream;

@Data
public class JsonStat {

    public static class DimensionGroupBlockDeserializer extends JsonDeserializer<DimensionGroupBlock> {
        private static final List<String> RESERVED = ImmutableList.of("id", "size", "role");
        @Override
        public DimensionGroupBlock deserialize(final JsonParser p, final DeserializationContext c)
                throws IOException, JsonProcessingException
        {
            final JsonNode node = p.getCodec().readTree(p);

            return DimensionGroupBlock.builder()
                    .id(stream(node.get("id").spliterator(), false)
                            .map(JsonNode::asText)
                            .collect(Collectors.toList()))
                    .size(stream(node.get("size").spliterator(), false)
                            .map(JsonNode::asInt)
                            .collect(Collectors.toList()))
                    .role(stream(spliteratorUnknownSize(node.get("role").fields(), Spliterator.ORDERED), false)
                                    .collect(toMap(Map.Entry::getKey,
                                            e -> stream(e.getValue().spliterator(), false)
                                                    .map(JsonNode::asText)
                                                    .collect(Collectors.toList()),
                                            (a, b) -> a))
                    )
                    .dimensions(stream(spliteratorUnknownSize(node.fields(), Spliterator.ORDERED), false)
                                    .filter(e -> !RESERVED.contains(e.getKey()))
                                    .collect(toMap(
                                            Map.Entry::getKey,
                                            e -> {
                                                try {
                                                    final JsonParser nodeParser = e.getValue().traverse();
                                                    nodeParser.setCodec(p.getCodec());
                                                    return (IndividualDimensionBlock) nodeParser.readValueAs(IndividualDimensionBlock.class);
                                                } catch (IOException ex) {
                                                    throw new RuntimeException(ex);
                                                }
                                            },
                                            (a, b) -> a))
                    )
                    .build();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DimensionGroupBlock {

        public List<String> id;

        public List<Integer> size;

        public Map<String, List<String>> role;

        public Map<String, IndividualDimensionBlock> dimensions;

    }

    @Data
    public static class IndividualDimensionBlock {

        public String label;

        public DimensionCategoryBlock category;

    }

    @Data
    public static class DimensionCategoryBlock {

        public Map<String, Integer> index;

        public Map<String, String> label;

        public Map<String, IndividualDimensionUnitBlock> unit;

    }

    @Data
    public static class IndividualDimensionUnitBlock {

        public String type;

        public String base;

        public String symbol;

        public Integer multiplier;

    }

    @JsonProperty("class")
    public String jsonStatClass;

    public String href;

    public String label;

    public String source;

    public LocalDate updated;

    public List<Number> value;

    public DimensionGroupBlock dimension;

}
