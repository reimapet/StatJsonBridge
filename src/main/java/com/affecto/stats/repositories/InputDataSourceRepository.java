package com.affecto.stats.repositories;

import com.affecto.stats.model.InputDataSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RepositoryRestResource(path = "sources")
public interface InputDataSourceRepository extends JpaRepository<InputDataSource, Long> {

    Optional<InputDataSource> findByName(String dataSourceName);

}
