package no.tordbjorn.cassandrademo.repository.entity;

import com.datastax.driver.core.LocalDate;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Table(name = "person")
public class Person {

    @PartitionKey
    @Column(name = "user_id")
    private Long id;

    @Column(name = "first_name")private String firstName;
    @Column(name = "last_name")private String lasttName;
    @Column(name = "date_of_birth")private LocalDate dateOfBirth;

    public Person(Long id) {
        this.id = id;
    }
}
