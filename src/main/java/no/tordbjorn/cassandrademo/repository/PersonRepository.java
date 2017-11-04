package no.tordbjorn.cassandrademo.repository;

import no.tordbjorn.cassandrademo.repository.entity.Person;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("personRepository")
public interface PersonRepository {

    Optional<Person> personById(Long id);

    boolean createIfNotExists(final Person person);

    }
