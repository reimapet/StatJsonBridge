package com.affecto.jsonstat;

import com.affecto.jsonstat.blocks.DimensionGroupBlock;
import com.affecto.jsonstat.blocks.IndividualDimensionBlock;
import com.affecto.jsonstat.blocks.SingleDatasetBlock;
import com.affecto.jsonstat.elements.UpdatedElement;
import com.affecto.jsonstat.elements.ValueElement;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CsvToJsonStatTest {

    private static ObjectMapper objectMapper() {
        final ObjectMapper ret = new ObjectMapper();
        ret.registerModule(new JavaTimeModule());
        ret.registerModule(new Jdk8Module());
        return ret;
    }

    @Data @AllArgsConstructor
    private static class County {
        public String state, zip;
        public String a, b;
        public String name;
        public String year;
        public String total, employed, unemployed, rate;
    }

    @Test
    public void csvToJsonStat() throws Exception {
        final Pattern pattern = Pattern.compile("(..)(.*),([0-9]*),([0-9]*),\"(.*)\",([0-9]*),,([0-9]*),([0-9]*),([0-9]*),([0-9\\.]*)");

        // Collect data

        final List<County> counties =
                Files.readAllLines(Paths.get("src/test/resources/laucnty12.csv")).stream()
                .map(pattern::matcher)
                .filter(Matcher::matches)
                .map(m -> new County(m.group(1), m.group(2), m.group(3), m.group(4), m.group(5), m.group(6), m.group(7), m.group(8), m.group(9), m.group(10)))
                .collect(Collectors.toList());

        // Build dimension histograms

        final Map<Integer, List<County>> byYear = counties.stream().collect(
                Collectors.groupingBy(c -> Integer.parseInt(c.year)));

        final Map<String, List<County>> byZip = counties.stream().collect(
                Collectors.groupingBy(County::getZip));

        final Map<String, List<County>> byYearAndZip = counties.stream().collect(
                Collectors.groupingBy(c -> c.year + c.zip));

        // Write the dimensions

        final SingleDatasetBlock.Builder datasetBuilder = SingleDatasetBlock.builder();
        final DimensionGroupBlock.Builder dimensionBuilder = DimensionGroupBlock.builder();

        final ValueElement.Builder valueBuilder = ValueElement.builder();

        dimensionBuilder.id(ImmutableList.of("year", "county", "labor"));
        dimensionBuilder.size(ImmutableList.of(byYear.size(), byZip.size(), 1));


        dimensionBuilder.role(ImmutableMap.of("foo", ImmutableList.of("asd", "zxc")));
        dimensionBuilder.dimensions(ImmutableMap.of("asd",
                IndividualDimensionBlock.builder()
                        .build()));

        datasetBuilder.dimension(dimensionBuilder.build());

        // Write the values

        final List<Number> values = counties.stream()
                .sorted((a, b) -> (a.year + a.zip).compareTo(b.year + b.zip))
                .flatMap(c -> ImmutableList.of(
                        Integer.parseInt(c.total),
                        Integer.parseInt(c.employed),
                        Integer.parseInt(c.unemployed),
                        Double.parseDouble(c.rate)
                ).stream())
                .collect(Collectors.toList());

        valueBuilder.list(values);
        datasetBuilder.value(valueBuilder.build());

        // Write the metadata

        datasetBuilder.jsonStatClass("dataset");
        datasetBuilder.updated(new UpdatedElement().setLocalDate(LocalDate.of(2013, 4, 19)));
        datasetBuilder.source("Bureau of Labor Statistics (http://www.bls.gov/lau/#cntyaa)");
        datasetBuilder.href("http://json-stat.org/samples/us-labor-ds.json");
        datasetBuilder.label("Labor Force Data by County, 2012 Annual Averages");

        final SingleDatasetBlock ds = datasetBuilder.build();

        System.err.println(ds);
    }

}
