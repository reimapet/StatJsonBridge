package com.affecto.jsonstat;

import com.affecto.jsonstat.dto.JsonStat;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;

import java.io.InputStream;

public class DeserializeTests {

    @Test
    public void testDeserialize() throws Exception {
        final ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        SimpleModule testModule = new SimpleModule("MyModule", new Version(1, 0, 0, null))
                     .addDeserializer(JsonStat.DimensionGroupBlock.class, new JsonStat.DimensionGroupBlockDeserializer());
        om.registerModule(testModule);
        try (final InputStream is = DeserializeTests.class.getClassLoader().getResourceAsStream("us-labor-ds.json")) {
            final JsonStat uld = om.readValue(is, JsonStat.class);
            System.out.println(uld);
        }
    }

}
