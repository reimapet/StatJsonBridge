package com.affecto.stats.repositories;

import com.affecto.stats.model.InputQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "queries")
public interface InputQueryRepository extends JpaRepository<InputQuery, Long> {

}