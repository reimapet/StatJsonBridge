package com.affecto.jsonstat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DimensionCategoryBlock {

    public Map<String, Integer> index;

    public Map<String, String> label;

    public Map<String, IndividualDimensionUnitBlock> unit;

    @JsonProperty(required = false)
    public JsonNode note;

    @JsonProperty(required = false)
    public Map<String, List<String>> child;

}
