package com.affecto.jsonstat.serializers;

import com.affecto.jsonstat.blocks.DimensionGroupBlock;
import com.affecto.jsonstat.blocks.IndividualDimensionBlock;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Map;

public class DimensionGroupBlockSerializer extends JsonSerializer<DimensionGroupBlock> {
    @Override
    public void serialize(final DimensionGroupBlock value, final JsonGenerator gen, final SerializerProvider sp)
            throws IOException
    {
        gen.writeStartObject();
        gen.writeObjectField("id", value.id);
        gen.writeObjectField("size", value.size);
        if (null != value.role) {
            gen.writeObjectField("role", value.role);
        }
        for (final Map.Entry<String, IndividualDimensionBlock> e : value.dimensions.entrySet()) {
            gen.writeObjectField(e.getKey(), e.getValue());
        }
        gen.writeEndObject();
    }
}
