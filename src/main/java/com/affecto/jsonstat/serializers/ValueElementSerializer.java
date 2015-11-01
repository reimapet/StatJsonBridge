package com.affecto.jsonstat.serializers;

import com.affecto.jsonstat.elements.ValueElement;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class ValueElementSerializer extends JsonSerializer<ValueElement> {
    @Override
    public void serialize(final ValueElement value, final JsonGenerator gen, final SerializerProvider serializers)
            throws IOException
    {
        if (value.isList()) {
            gen.writeObject(value.list);
        } else if (value.isMap()) {
            gen.writeObject(value.map);
        } else {
            gen.writeNull();
        }
    }
}
