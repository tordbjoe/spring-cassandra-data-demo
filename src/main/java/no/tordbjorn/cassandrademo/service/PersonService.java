package no.tordbjorn.cassandrademo.service;

import com.datastax.driver.core.LocalDate;
import no.tordbjorn.cassandrademo.repository.PersonRepositoryCassandra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import no.tordbjorn.cassandrademo.repository.entity.Person;



@Service
public class PersonService {

    private PersonRepositoryCassandra personRepository;

    @Autowired
    public PersonService(PersonRepositoryCassandra personRepository) {
        this.personRepository = personRepository;
    }

    public Person createPerson(Long id,String firstName, String lastName, LocalDate dateOfBirth) {
        Person person = new Person();
        person.setId(id);
        person.setFirstName(firstName);
        person.setLasttName(lastName);
        person.setDateOfBirth(dateOfBirth);

        personRepository.createIfNotExists(person);

        return person;
    }
}
