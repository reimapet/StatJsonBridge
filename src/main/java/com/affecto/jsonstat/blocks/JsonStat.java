package com.affecto.jsonstat.blocks;

import com.affecto.jsonstat.elements.UpdatedElement;
import com.affecto.jsonstat.serializers.UpdatedElementDeserializer;
import com.affecto.jsonstat.serializers.UpdatedElementSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
public class JsonStat {

    @JsonProperty("class")
    public String jsonStatClass;

    public String href;

    public String label;

    public String source;

    @JsonDeserialize(using = UpdatedElementDeserializer.class)
    @JsonSerialize(using = UpdatedElementSerializer.class)
    public UpdatedElement updated;

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