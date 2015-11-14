package com.affecto.stats.services;

import com.affecto.jsonstat.blocks.*;
import com.affecto.jsonstat.elements.IndexElement;
import com.affecto.jsonstat.elements.UpdatedElement;
import com.affecto.jsonstat.elements.ValueElement;
import com.affecto.stats.model.InputQuery;
import com.affecto.stats.repositories.InputDataSourceRepository;
import com.affecto.stats.repositories.InputQueryRepository;
import com.affecto.stats.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.*;
import com.google.common.io.CharStreams;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.mariadb.jdbc.MySQLDataSource;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toMap;

@Service
public class RenderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InputQueryRepository queryRepository;

    @Autowired
    private InputDataSourceRepository dataSourceRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("classpath:laucnty12.csv")
    private Resource csv;

    public String renderQuery(final String username, final String dataSourceName, final String queryName) {
        final InputQuery query = queryRepository.findByName(queryName).get();
        final DataSource dataSource = connectToImportDataSource(dataSourceName);
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        try {
            return renderCsvToJsonStat();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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


    private String renderCsvToJsonStat() throws IOException {
        final Pattern pattern = Pattern.compile("(.{8}),([0-9]{2}),([0-9]{3}),\"(.*)\",([0-9]*),,([0-9]*),([0-9]*),([0-9]*),([0-9\\.]*)");

        // Collect data

        final List<County> counties;
        try (final InputStream is = csv.getInputStream();
                final Reader br = new InputStreamReader(is, StandardCharsets.UTF_8))
        {
            counties = CharStreams.readLines(br).stream()
                            .map(pattern::matcher)
                            .filter(Matcher::matches)
                            .map(m -> new County(m.group(1), m.group(2), m.group(3), m.group(4), m.group(5), m.group(6), m.group(7), m.group(8), m.group(9)))
                            .sorted((a, b) -> (a.year + a.getZip()).compareTo(b.year + b.getZip()))
                            .collect(Collectors.toList());
        }

        // Build dimension histograms

        final Map<Integer, List<County>> byYear = counties.stream().collect(
                Collectors.groupingBy(c -> Integer.parseInt(c.year)));

        final Map<String, List<County>> byZip = counties.stream().collect(
                Collectors.groupingBy(c -> c.state + c.county));

        // Write the dimensions

        final SingleDatasetBlock.Builder datasetBuilder = SingleDatasetBlock.builder();
        final DimensionGroupBlock.Builder dimensionBuilder = DimensionGroupBlock.builder();

        final ValueElement.Builder valueBuilder = ValueElement.builder();

        dimensionBuilder.id(ImmutableList.of("year", "county", "labor"));
        dimensionBuilder.size(ImmutableList.of(byYear.size(), byZip.size(), 4));

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
                                .index(IndexElement.builder()
                                        .map(IntStream.range(0, counties.size())
                                                .boxed()
                                                .collect(toMap(
                                                        i -> counties.get(i).getZip(), i -> i,
                                                        (a, b) -> a, TreeMap::new))
                                        ).build()
                                )
                                .label(counties.stream().collect(toMap(
                                        County::getZip, c -> c.name,
                                        (a, b) -> a, TreeMap::new)))
                                .build())
                        .build()
        ));

        datasetBuilder.dimension(dimensionBuilder.build());

        // Write the values

        final List<Number> values = counties.stream()
                .sorted((a, b) -> (a.year + a.getZip()).compareTo(b.year + b.getZip()))
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

        return objectMapper.writeValueAsString(datasetBuilder.build());
    }

    private DataSource connectToImportDataSource(final String dataSourceName) {
        return dataSourceRepository.findByName(dataSourceName)
                .map(ids -> createDataSource(ids.getJdbcUrl(), ids.getJdbcUsername(), ids.getJdbcPassword()))
                .orElse(null);
    }

    private static DataSource createDataSource(final String url, final String username, final String password) {
        try {
            if (url.startsWith("jdbc:mysql")) {
                return createMySQLDataSource(url, username, password);
            } else if (url.startsWith("jdbc:h2")) {
                return createH2DataSource(url, username, password);
            } else if (url.startsWith("jdbc:postgres")) {
                return createPostgreSQLDataSource(url, username, password);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static DataSource createMySQLDataSource(final String url, final String username, final String password)
            throws SQLException
    {
        final MySQLDataSource ret = new MySQLDataSource();
        ret.setUrl(url);
        ret.setUserName(username);
        ret.setPassword(password);
        return ret;
    }

    private static DataSource createH2DataSource(final String url, final String username, final String password)
            throws SQLException
    {
        final org.h2.jdbcx.JdbcDataSource ret = new org.h2.jdbcx.JdbcDataSource();
        ret.setUrl(url);
        ret.setUser(username);
        ret.setPassword(password);
        return ret;
    }

    private static DataSource createPostgreSQLDataSource(final String url, final String username, final String password)
            throws SQLException
    {
        final PGSimpleDataSource ret = new PGSimpleDataSource();
        ret.setUrl(url);
        ret.setUser(username);
        ret.setPassword(password);
        return ret;
    }

}
