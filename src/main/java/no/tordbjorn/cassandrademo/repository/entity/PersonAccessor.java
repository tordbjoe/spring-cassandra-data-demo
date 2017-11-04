package no.tordbjorn.cassandrademo.repository.entity;

import com.datastax.driver.core.LocalDate;
import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;


@Accessor
public interface PersonAccessor {

    @Query("INSERT INTO person (user_id, date_of_birth, first_name, last_name) VALUES ("
        + ":userId, "
        + ":dateOfBirth, "
        + ":firstName, "
        + ":lastName "
        + ") IF NOT EXISTS;")
    Statement insertPersonIfNotExists(
            @Param("userId") Long userId,
            @Param("dateOfBirth") LocalDate dateOfBirth,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName
            );

    @Query("SELECT * FROM person WHERE user_id = :id;")
    Statement getPersonById(@Param("id") Long id);
}
