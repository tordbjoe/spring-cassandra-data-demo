package no.tordbjorn.cassandrademo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(scanBasePackages = {"no.tordbjorn.cassandrademo"})
@EnableWebMvc
public class CassandraApplication {

    public static void main(String[] args) {
        SpringApplication.run(CassandraApplication.class, args);
    }

}
