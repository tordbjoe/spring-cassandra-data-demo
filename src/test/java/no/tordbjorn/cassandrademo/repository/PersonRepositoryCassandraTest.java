package no.tordbjorn.cassandrademo.repository;

import com.datastax.driver.core.LocalDate;
import configuration.CassandraExecutionListener;
import configuration.TestCassandraConfiguration;
import no.tordbjorn.cassandrademo.repository.entity.Person;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {TestCassandraConfiguration.class, PersonRepositoryCassandraTest.TestConfig.class}, initializers = ConfigFileApplicationContextInitializer.class)
@TestExecutionListeners({CassandraExecutionListener.class, DependencyInjectionTestExecutionListener.class})
public class PersonRepositoryCassandraTest {

    @Configuration
    @Profile("test")
    static class TestConfig {

        @Bean
        static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
            return new PropertySourcesPlaceholderConfigurer();
        }

        @Bean(name = "personRepositoryCassandra")
        public PersonRepository personRepository() {
            return new PersonRepositoryCassandra();
        }

    }

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void testCreatePerson() throws Exception {
        Person person = new Person();
        person.setId(1L);
        person.setDateOfBirth(LocalDate.fromMillisSinceEpoch(1000000));
        person.setFirstName("Test");
        person.setLasttName("Test");

        boolean created = personRepository.createIfNotExists(person);

        Assert.assertTrue(created);
    }

}