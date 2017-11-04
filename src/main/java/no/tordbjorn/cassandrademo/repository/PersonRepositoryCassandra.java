package no.tordbjorn.cassandrademo.repository;

import com.datastax.driver.core.LocalDate;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import no.tordbjorn.cassandrademo.repository.entity.Person;
import no.tordbjorn.cassandrademo.repository.entity.PersonAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Repository("personRepositoryCassandra")
public class PersonRepositoryCassandra implements PersonRepository{

    private Mapper<Person> personMapper;
    private PersonAccessor personAccessor;

    @Autowired
    public Session session;

    @PostConstruct
    public void setupTable() {
        final MappingManager mappingManager = new MappingManager(session);
        personMapper = mappingManager.mapper(Person.class);
        personAccessor = mappingManager.createAccessor(PersonAccessor.class);
    }

    @Override
    public Optional<Person> personById(Long id) {
        final Statement statement = personAccessor.getPersonById(id);
        final Person person = personMapper.map(session.execute(statement)).one();

        return person ==  null ? Optional.empty() : Optional.of(person);
    }

    @Override
    public boolean createIfNotExists(Person person) {
        final Statement statement = personAccessor.insertPersonIfNotExists(
                person.getId(),
                person.getDateOfBirth(),
                person.getFirstName(),
                person.getLasttName()
        );

        final ResultSet result = session.execute(statement);
        return result.wasApplied();
    }

}
