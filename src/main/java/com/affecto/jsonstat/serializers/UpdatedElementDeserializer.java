package com.affecto.jsonstat.serializers;

import com.affecto.jsonstat.elements.UpdatedElement;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class UpdatedElementDeserializer extends JsonDeserializer<UpdatedElement> {

    final DateTimeFormatter localDateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;


    @Override
    public UpdatedElement deserialize(final JsonParser p, final DeserializationContext ctxt)
            throws IOException, JsonProcessingException
    {
        final String value = p.getValueAsString();
        try {
            return new UpdatedElement()
                    .setZonedDateTime(ZonedDateTime.parse(value, DateTimeFormatter.ISO_ZONED_DATE_TIME));
        } catch (DateTimeParseException e) {
            // fall through
        }
        try {
            return new UpdatedElement()
                    .setLocalDateTime(LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        } catch (DateTimeParseException e) {
            // fall through
        }
        try {
            return new UpdatedElement()
                    .setLocalDate(LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE));
        } catch (DateTimeParseException e) {
            // fall through
        }
        return null;
    }
}
