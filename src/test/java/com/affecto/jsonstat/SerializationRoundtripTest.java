package com.affecto.jsonstat;

import com.affecto.jsonstat.blocks.JsonStat;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.diff.JsonDiff;
import com.google.common.collect.ImmutableList;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(Parameterized.class)
public class SerializationRoundtripTest {

    private static ObjectMapper objectMapper() {
        final ObjectMapper ret = new ObjectMapper();
        ret.registerModule(new JavaTimeModule());
        return ret;
    }

    private static JsonNode readFromClassPath(final String path) {
        final ObjectMapper om = objectMapper();
        try (final InputStream is = fromTestClassPath(path)) {
            return om.readValue(is, JsonNode.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static JsonNode backAndForth(final JsonStat jsonStat) {
        final ObjectMapper om = objectMapper();
        try {
            return om.readValue(om.writeValueAsString(jsonStat), JsonNode.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static InputStream fromTestClassPath(final String path) {
        return SerializationRoundtripTest.class.getClassLoader().getResourceAsStream(path);
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<String> generateData() {
        return ImmutableList.of(
                "us-labor-ds.json",
                "oecd-ds.json",
                "galicia-ds.json"
        );
    }

    @Parameterized.Parameter
    public String path;

    @Test
    public void testRoundtrip() throws Exception {
        final ObjectMapper om = objectMapper();
        try (final InputStream is = fromTestClassPath(path)) {
            final JsonStat stat = om.readValue(is, JsonStat.class);

            final JsonNode b = backAndForth(stat);
            final JsonNode a = readFromClassPath(path);

            final JsonPatch patch = JsonDiff.asJsonPatch(a, b);
            if (!"[]".equals(patch.toString())) {
                System.err.println("patch: " + patch);
            }

            Assert.assertEquals(a, b);

            final JsonNode mockNode = mock(JsonNode.class);
            patch.apply(mockNode);
            verifyZeroInteractions(mockNode);
        }
    }

}
