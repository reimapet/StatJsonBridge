package com.affecto.jsonstat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class JsonStat {

    @JsonProperty("class")
    public String jsonStatClass;

    public String href;

    public String label;

    public String source;

    public LocalDate updated;

    public List<Number> value;

    public DimensionGroupBlock dimension;

    @JsonProperty(required = false)
    public JsonNode note;

    @JsonProperty(required = false)
    public JsonNode extension;

    @JsonProperty(required = false)
    public JsonNode status;

}
