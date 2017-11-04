package no.tordbjorn.cassandrademo.controller;

import no.tordbjorn.cassandrademo.repository.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import no.tordbjorn.cassandrademo.service.PersonService;

import java.time.LocalDate;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/api/createperson", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<?> createPerson() {
        Person person = personService.createPerson(1L,"Tordbjørn Wang", "Eriksen", com.datastax.driver.core.LocalDate.fromMillisSinceEpoch(1000000L));
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

}
