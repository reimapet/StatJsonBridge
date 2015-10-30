package com.affecto.jsonstat.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
public class JsonStat {

    @JsonProperty("class")
    public String jsonStatClass;

    public String href;

    public String label;

    public String source;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public LocalDate updated;

    public List<Number> value;

    public DimensionGroupBlock dimension;

    @JsonInclude(NON_NULL)
    @JsonProperty(required = false)
    public JsonNode note;

    @JsonInclude(NON_NULL)
    @JsonProperty(required = false)
    public JsonNode extension;

    @JsonInclude(NON_NULL)
    @JsonProperty(required = false)
    public JsonNode status;

}
