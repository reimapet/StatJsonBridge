package com.affecto.jsonstat.blocks;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
public class IndividualDimensionUnitBlock {

    @JsonInclude(NON_NULL)
    public String type;

    @JsonInclude(NON_NULL)
    public String base;

    @JsonInclude(NON_NULL)
    public String symbol;

    @JsonInclude(NON_NULL)
    public Integer multiplier;

    @JsonInclude(NON_NULL)
    @JsonProperty(required = false)
    public String label;

    @JsonInclude(NON_NULL)
    @JsonProperty(required = false)
    public Integer decimals;

}
