package com.affecto.stats.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * An user of the application.
 */
@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "user")
    private List<InputDataSource> dataSources;

    @OneToMany(mappedBy = "user")
    private List<InputQuery> queries;

    private String username;

    private String password;

}
