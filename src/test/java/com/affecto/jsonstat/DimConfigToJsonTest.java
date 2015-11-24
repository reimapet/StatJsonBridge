package com.affecto.jsonstat;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.affecto.jsonstat.blocks.*;
import com.affecto.jsonstat.config.DimensionConfigBlock;
import com.affecto.jsonstat.config.IndividualDimension;
import com.affecto.jsonstat.elements.IndexElement;
import com.affecto.jsonstat.elements.UpdatedElement;
import com.affecto.jsonstat.elements.ValueElement;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.diff.JsonDiff;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toMap;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Created by reimapet on 19.11.2015.
 */
public class DimConfigToJsonTest {

    private static ObjectMapper objectMapper() {
        final ObjectMapper ret = new ObjectMapper();
        ret.registerModule(new JavaTimeModule());
        ret.registerModule(new Jdk8Module());
        return ret;
    }

    private static DimensionConfigBlock readBlockFromClassPath(final String path) {
        final ObjectMapper om = objectMapper();
        try (final InputStream is = fromTestClassPath(path)) {
            return om.readValue(is, DimensionConfigBlock.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static JsonNode readFromClassPath(final String path) {
        final ObjectMapper om = objectMapper();
        try (final InputStream is = fromTestClassPath(path)) {
            return om.readValue(is, JsonNode.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static JsonNode backAndForth(final SingleDatasetBlock singleDatasetBlock) {
        final ObjectMapper om = objectMapper();
        try {
            return om.readValue(om.writeValueAsString(singleDatasetBlock), JsonNode.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static InputStream fromTestClassPath(final String path) {
        return CsvToJsonStatTest.class.getClassLoader().getResourceAsStream(path);
    }

    @Data
    @AllArgsConstructor
    private static class County {
        public String laus, state, county;
        public String name;
        public String year;
        public String total, employed, unemployed, rate;

        public String getZip() {
            return state + county;
        }
    }

    @Test
    public void csvToJsonStat() throws Exception {
        final JsonNode original = readFromClassPath("dimension-conf.json");

        DimensionConfigBlock block = readBlockFromClassPath(("dimension-conf.json"));

        System.err.println("patch: " + original.toString());

        final InputStream inputStream = fromTestClassPath("laucnty12.csv");
        final Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        final CSVParser parser = CSVFormat.DEFAULT.parse(reader);
        generate( block, parser );

    }

    public void printConfig( DimensionConfigBlock block )
    {
        System.out.println( block.basicInfo.toString() );
        System.out.println(block.dimensions.toString());
        System.out.println(block.dimensions.get(0).category.toString());

    }

    public void generate( DimensionConfigBlock config, CSVParser data )
            throws Exception {

        final SingleDatasetBlock.Builder datasetBuilder = SingleDatasetBlock.builder();
        final DimensionGroupBlock.Builder dimensionBuilder = DimensionGroupBlock.builder();

        Iterator<IndividualDimension> it = config.dimensions.iterator();
        List<String> dimensionIds = new ArrayList<>();
        List<String> dimensionLabels = new ArrayList<>();

        while (it.hasNext()) {
            IndividualDimension dim = it.next();
            dimensionIds.add(dim.getId());
            dimensionLabels.add(dim.getLabel());
        }

        datasetBuilder.label(config.getBasicInfo().getLabel());
        datasetBuilder.href(config.getBasicInfo().getHref());
        datasetBuilder.source(config.getBasicInfo().getSource());

        dimensionBuilder.id(dimensionIds);
        dimensionBuilder.size(config.basicInfo.getSize());
        // Handle dynamic portion of size
        for (int i = 0; i < config.getBasicInfo().getSize().size(); i++) {
            // configured size of 0 means it should be replaced with number of data rows
            if (config.getBasicInfo().getSize().get(i).intValue() == 0) {
                config.getBasicInfo().getSize().set(i, data.getRecords().size());
            }
        }


        // Dimensions
        Map<String, IndividualDimensionBlock> dimensionMap = new HashMap<>();

        IndividualDimensionBlock.Builder individualDimensionBuilder = IndividualDimensionBlock.builder();
        for (int i = 0; i < config.getDimensions().size(); i++)
        {
            individualDimensionBuilder.label(config.getDimensions().get(i).getLabel());
            if (config.getDimensions().get(i).getCategory().getIndex() != null) {
                IndividualDimensionBlock d = individualDimensionBuilder.category(DimensionCategoryBlock.builder().label(config.getDimensions().get(i).getCategory().getLabel())
                        .index(new IndexElement(config.getDimensions().get(i).getCategory().getIndex()))
                        .child(config.getDimensions().get(i).getCategory().getChild()).build()).build();

                dimensionMap.put(d.getLabel(), d);

            } else {
                // TODO: handle dynamic index
            }
        }
        dimensionBuilder.dimensions(dimensionMap);
        datasetBuilder.dimension(dimensionBuilder.build());
        System.out.println(datasetBuilder.toString());
    }

    /**
     * Calculate number of unique values in each column
     **/
    public List<Integer> generateSize( CSVParser data )
            throws Exception
    {
        Iterator<CSVRecord> it = data.iterator();
        List<CSVRecord> list = data.getRecords();

        CSVRecord record = list.get(0);
        int colSize = record.size();

        List<Integer> sizeList = new ArrayList<>(colSize);
        for( int i = 0 ; i < colSize ; i++ )
        {
            Map<String, String> uniqueVals = new HashMap<>();
            for( int j = 0 ; j < list.size() ; j++ )
            {
                uniqueVals.put(list.get(j).get(i), "");
            }
            sizeList.add(i, uniqueVals.size());
        }
        return sizeList;
    }
}
