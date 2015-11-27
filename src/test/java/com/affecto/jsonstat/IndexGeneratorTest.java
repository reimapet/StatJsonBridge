package com.affecto.jsonstat;

import com.affecto.jsonstat.config.DimensionConfigBlock;
import com.affecto.jsonstat.util.UniqueIndexGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.junit.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by reimapet on 27.11.2015.
 */
public class IndexGeneratorTest {

    private static InputStream fromTestClassPath(final String path) {
        return CsvToJsonStatTest.class.getClassLoader().getResourceAsStream(path);
    }

    @Test
    public void csvToJsonStat() throws Exception {
       // final JsonNode original = readFromClassPath("dimension-conf.json");

       // DimensionConfigBlock block = readBlockFromClassPath(("dimension-conf.json"));

        final InputStream inputStream = fromTestClassPath("laucnty12.csv");
        final Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        final CSVParser parser = CSVFormat.DEFAULT.parse(reader);
        UniqueIndexGenerator gen = new UniqueIndexGenerator();

        Map<String, String> parmMap = new HashMap<>();
        parmMap.put( UniqueIndexGenerator.INDEX_COLUMN_PARAMETER, "1,2");

        Map<String, Integer> indexMap = gen.generateIndex(parser, parmMap);
        System.out.println( indexMap );
    }


}
