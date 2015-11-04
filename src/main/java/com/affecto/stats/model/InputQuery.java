package com.affecto.stats.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Represents a SQL query into a data source, used to export data.
 */
@Entity
@Table(name = "input_queries")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputQuery {

    @Id
    @GeneratedValue
    private Long id;

    /** The URL segment name for this query. */
    private String name;

    private String sqlQuery;

    // selected dimensions

    // manual dimension titles?

}
