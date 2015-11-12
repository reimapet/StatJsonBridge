package com.affecto.jsonstat;

import com.affecto.jsonstat.blocks.*;
import com.affecto.jsonstat.elements.IndexElement;
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

        dimensionBuilder.role(ImmutableMap.of(
                "time", ImmutableList.of("year"),
                "geo", ImmutableList.of("county"),
                "metric", ImmutableList.of("labor")
        ));

        dimensionBuilder.dimensions(ImmutableMap.of(
                "labor", IndividualDimensionBlock.builder()
                            .label("Labor force status")
                            .category(DimensionCategoryBlock.builder()
                                    .index(IndexElement.builder().map(ImmutableMap.of(
                                            "labforce", 0,
                                            "empl", 1,
                                            "unempl", 2,
                                            "unr", 3
                                    )).build())
                                    .label(ImmutableMap.of(
                                            "labforce", "Labor Force",
                                            "empl", "Employed",
                                            "unempl", "Unemployed",
                                            "unr", "Unemployment rate"
                                    ))
                                    .unit(ImmutableMap.of(
                                            "labforce", IndividualDimensionUnitBlock.builder()
                                                    .type("count")
                                                    .base("person")
                                                    .symbol("")
                                                    .multiplier(0)
                                                    .build(),
                                            "empl", IndividualDimensionUnitBlock.builder()
                                                    .type("count")
                                                    .base("person")
                                                    .symbol("")
                                                    .multiplier(0)
                                                    .build(),
                                            "unempl", IndividualDimensionUnitBlock.builder()
                                                    .type("count")
                                                    .base("person")
                                                    .symbol("")
                                                    .multiplier(0)
                                                    .build(),
                                            "unr", IndividualDimensionUnitBlock.builder()
                                                    .type("ratio")
                                                    .base("Per cent")
                                                    .symbol("%")
                                                    .multiplier(0)
                                                    .build()
                                    ))
                                    .build())
                        .build(),
                "year", IndividualDimensionBlock.builder()
                        .label("Year")
                        .category(DimensionCategoryBlock.builder()
                                .label(ImmutableMap.of(
                                        "2012", "2012"
                                ))
                                .build())
                        .build(),
                "county", IndividualDimensionBlock.builder()
                        .label("County")
                        .category(DimensionCategoryBlock.builder()
                                .index(counties.stream()
                                        .sorted((a, b) -> a.zip.compareTo(b.zip))
                                        // XXX HERE
                                .collect())
                                .build())
                        .build()
        ));

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
