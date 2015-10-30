package com.affecto.jsonstat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class IndividualDimensionBlock {

    public String label;

    public DimensionCategoryBlock category;

    @JsonProperty(required = false)
    public JsonNode extension;

    @JsonProperty(required = false)
    public JsonNode note;

}
