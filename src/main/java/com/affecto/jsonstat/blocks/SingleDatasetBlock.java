package com.affecto.jsonstat.blocks;

import com.affecto.jsonstat.elements.UpdatedElement;
import com.affecto.jsonstat.elements.ValueElement;
import com.affecto.jsonstat.serializers.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class SingleDatasetBlock {

    @JsonProperty("class")
    public String jsonStatClass;

    public String href;

    public String label;

    public String source;

    @JsonDeserialize(using = UpdatedElementDeserializer.class)
    @JsonSerialize(using = UpdatedElementSerializer.class)
    public UpdatedElement updated;

    @JsonDeserialize(using = ValueElementDeserializer.class)
    @JsonSerialize(using = ValueElementSerializer.class)
    public ValueElement value;

    @JsonDeserialize(using = DimensionGroupBlockDeserializer.class)
    @JsonSerialize(using = DimensionGroupBlockSerializer.class)
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
