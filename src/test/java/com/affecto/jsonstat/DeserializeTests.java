package com.affecto.jsonstat;

import com.affecto.jsonstat.dto.JsonStat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;

import java.io.InputStream;

public class DeserializeTests {

    @Test
    public void testDeserialize() throws Exception {
        final ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        try (final InputStream is = DeserializeTests.class.getClassLoader().getResourceAsStream("us-labor-ds.json")) {
            final JsonStat uld = om.readValue(is, JsonStat.class);
            System.out.println(uld);
        }
    }

}
