package com.affecto.jsonstat.serializers;

import com.affecto.jsonstat.elements.UpdatedElement;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class UpdatedElementSerializer extends JsonSerializer<UpdatedElement> {
    @Override
    public void serialize(final UpdatedElement value, final JsonGenerator gen, final SerializerProvider serializers)
            throws IOException, JsonProcessingException
    {
        if (value.isLocalDate()) {
            gen.writeString(value.localDate.toString());
        } else if (value.isLocalDateTime()) {
            gen.writeString(value.localDateTime.toString());
        } else if (value.isZonedDateTime()) {
            gen.writeString(value.zonedDateTime.toString());
        } else {
            gen.writeNull();
        }
    }
}
