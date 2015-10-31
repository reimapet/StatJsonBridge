package com.affecto.jsonstat.blocks;

import com.affecto.jsonstat.elements.IndexElement;
import com.affecto.jsonstat.serializers.IndexElementDeserializer;
import com.affecto.jsonstat.serializers.IndexElementSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
public class DimensionCategoryBlock {

    @JsonInclude(NON_NULL)
    @JsonDeserialize(using = IndexElementDeserializer.class)
    @JsonSerialize(using = IndexElementSerializer.class)
    public IndexElement index;

    @JsonInclude(NON_NULL)
    public Map<String, String> label;

    @JsonInclude(NON_NULL)
    public Map<String, IndividualDimensionUnitBlock> unit;

    @JsonInclude(NON_NULL)
    @JsonProperty(required = false)
    public JsonNode note;

    @JsonInclude(NON_NULL)
    @JsonProperty(required = false)
    public Map<String, List<String>> child;

}
