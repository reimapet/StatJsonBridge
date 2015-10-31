package com.affecto.jsonstat.blocks;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
public class IndividualDimensionBlock {

    @JsonInclude(NON_NULL)
    public String label;

    public DimensionCategoryBlock category;

    @JsonInclude(NON_NULL)
    @JsonProperty(required = false)
    public JsonNode extension;

    @JsonInclude(NON_NULL)
    @JsonProperty(required = false)
    public JsonNode note;

    @JsonInclude(NON_NULL)
    @JsonProperty(required = false)
    public JsonNode link;

}
