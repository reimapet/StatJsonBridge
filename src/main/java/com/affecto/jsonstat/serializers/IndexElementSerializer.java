package com.affecto.jsonstat.serializers;

import com.affecto.jsonstat.elements.IndexElement;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class IndexElementSerializer extends JsonSerializer<IndexElement> {
    @Override
    public void serialize(final IndexElement value, final JsonGenerator gen, final SerializerProvider serializers)
            throws IOException
    {
        if (value.isList()) {
            gen.writeObject(value.listIndex);
        } else if (value.isMap()) {
            gen.writeObject(value.mapIndex);
        } else {
            gen.writeNull();
        }
    }
}
