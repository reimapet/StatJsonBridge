package com.affecto.stats.services;

import com.affecto.stats.repositories.InputDataSourceRepository;
import com.affecto.stats.repositories.InputQueryRepository;
import com.affecto.stats.repositories.UserRepository;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InputQueryRepository queryRepository;

    @Autowired
    private InputDataSourceRepository dataSourceRepository;

    public Map<String, ?> entitiesForView(final String username) {
        return ImmutableMap.of(
                "user", userRepository.findByUsername(username)
        );
    }

    public Map<String, ?> entitiesForView(final String username, final String dataSourceName) {
        return ImmutableMap.of(
                "user", userRepository.findByUsername(username),
                "dataSource", dataSourceRepository.findByName(dataSourceName)
        );
    }

    public Map<String, ?> entitiesForView(final String username, final String dataSourceName, final String queryName) {
        return ImmutableMap.of(
                "user", userRepository.findByUsername(username),
                "dataSource", dataSourceRepository.findByName(dataSourceName),
                "query", queryRepository.findByName(queryName)
        );
    }

}
