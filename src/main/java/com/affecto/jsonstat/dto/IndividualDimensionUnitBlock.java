package com.affecto.jsonstat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class IndividualDimensionUnitBlock {

    public String type;

    public String base;

    public String symbol;

    public Integer multiplier;

    @JsonProperty(required = false)
    public String label;

    @JsonProperty(required = false)
    public Integer decimals;


}
