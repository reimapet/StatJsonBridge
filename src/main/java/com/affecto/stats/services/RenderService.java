package com.affecto.stats.services;

import com.affecto.jsonstat.blocks.DimensionGroupBlock;
import com.affecto.jsonstat.blocks.IndividualDimensionBlock;
import com.affecto.jsonstat.blocks.SingleDatasetBlock;
import com.affecto.jsonstat.elements.UpdatedElement;
import com.affecto.jsonstat.elements.ValueElement;
import com.affecto.stats.model.InputQuery;
import com.affecto.stats.repositories.InputDataSourceRepository;
import com.affecto.stats.repositories.InputQueryRepository;
import com.affecto.stats.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.*;
import org.mariadb.jdbc.MySQLDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public String renderQuery(final String username, final String dataSourceName, final String queryName) {
        final InputQuery query = queryRepository.findByName(queryName).get();
        final DataSource dataSource = connectToImportDataSource(dataSourceName);
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        final SingleDatasetBlock.Builder datasetBuilder = SingleDatasetBlock.builder();
        final DimensionGroupBlock.Builder dimensionBuilder = DimensionGroupBlock.builder();

        final ValueElement.Builder valueBuilder = ValueElement.builder();
        jdbcTemplate.query(query.getSqlQuery(), rs -> {
            final Table<Integer, Integer, Object> table = TreeBasedTable.create();
            final int columnCount = rs.getMetaData().getColumnCount();
            final List<Number> valueList = new ArrayList<>();
            int i = 0;
            while (rs.next()) {
                i++;
                for (int j = 1; j <= columnCount; j++) {
                    table.put(i, j, rs.getObject(j));
                }
            }
            dimensionBuilder.id(
                    IntStream.iterate(1, h -> h + 1).limit(rs.getMetaData().getColumnCount())
                        .mapToObj(k -> {
                            try {
                                return rs.getMetaData().getColumnName(k);
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        }).collect(Collectors.toList())
            );
            dimensionBuilder.size(ImmutableList.of(i, 1, 1));
            dimensionBuilder.role(ImmutableMap.of("foo", ImmutableList.of("asd", "zxc")));
            dimensionBuilder.dimensions(ImmutableMap.of("asd",
                    IndividualDimensionBlock.builder()
                    .build()));
            valueBuilder.list(valueList);
        });
        datasetBuilder.value(valueBuilder.build());
        datasetBuilder.dimension(dimensionBuilder.build());
        datasetBuilder.updated(new UpdatedElement().setLocalDate(LocalDate.now()));

        datasetBuilder.source("source");
        datasetBuilder.href("href");
        datasetBuilder.jsonStatClass("dataset");
        datasetBuilder.label("label");

        try {
            return objectMapper.writeValueAsString(datasetBuilder.build());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private DataSource connectToImportDataSource(final String dataSourceName) {
        return dataSourceRepository.findByName(dataSourceName).map(ids -> {
            try {
                final MySQLDataSource ret = new MySQLDataSource();
                ret.setUrl(ids.getJdbcUrl());
                ret.setUserName(ids.getJdbcUsername());
                ret.setPassword(ids.getJdbcPassword());
                return ret;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }).orElse(null);
    }

}
