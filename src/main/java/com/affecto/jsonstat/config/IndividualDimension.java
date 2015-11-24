package com.affecto.jsonstat.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * Created by reimapet on 20.11.2015.
 */
@Data
public class IndividualDimension {

    public String id;

    @JsonProperty(required = false)
    @JsonInclude(NON_NULL)
    public String label;


    public DimensionCategory category;

}
