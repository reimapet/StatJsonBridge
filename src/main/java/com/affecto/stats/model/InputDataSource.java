package com.affecto.stats.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Represents an external data source from which we read data to be later converted into the JSON-Stat format.
 */
@Entity
@Table(name = "input_data_sources")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputDataSource {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /** The URL segment name for this data source. */
    private String name;

    private String title;

    private String jdbcUrl;

}
