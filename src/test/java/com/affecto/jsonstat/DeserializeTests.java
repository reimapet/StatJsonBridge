package com.affecto.jsonstat;

import com.affecto.jsonstat.dto.DimensionGroupBlock;
import com.affecto.jsonstat.dto.JsonStat;
import com.affecto.jsonstat.util.DimensionGroupBlockDeserializer;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;

import java.io.InputStream;

public class DeserializeTests {

    private static ObjectMapper objectMapper() {
        final ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        SimpleModule testModule = new SimpleModule("MyModule", new Version(1, 0, 0, null))
                .addDeserializer(DimensionGroupBlock.class, new DimensionGroupBlockDeserializer());
        om.registerModule(testModule);
        return om;
    }

    private static InputStream fromTestClassPath(final String path) {
        return DeserializeTests.class.getClassLoader().getResourceAsStream("us-labor-ds.json");
    }

    @Test
    public void testUsLabor() throws Exception {
        final ObjectMapper om = objectMapper();
        try (final InputStream is = fromTestClassPath("us-labor-ds.json")) {
            final JsonStat uld = om.readValue(is, JsonStat.class);
            System.out.println(uld);
        }
    }

    @Test
    public void testOecdDs() throws Exception {
        final ObjectMapper om = objectMapper();
        try (final InputStream is = fromTestClassPath("oecd-ds.json")) {
            final JsonStat uld = om.readValue(is, JsonStat.class);
            System.out.println(uld);
        }
    }

}
