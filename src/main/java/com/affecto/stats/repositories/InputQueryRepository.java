package com.affecto.stats.repositories;

import com.affecto.stats.model.InputQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RepositoryRestResource(path = "queries")
public interface InputQueryRepository extends JpaRepository<InputQuery, Long> {

    Optional<InputQuery> findByName(String queryName);

}
