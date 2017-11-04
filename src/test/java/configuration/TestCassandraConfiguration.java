package configuration;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.cassandraunit.CQLDataLoader;
import org.cassandraunit.dataset.cql.ClassPathCQLDataSet;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import util.SessionProxy;

import java.time.Duration;

@Configuration
@Profile({"test"})
public class TestCassandraConfiguration implements DisposableBean {

    private static final Logger log = LoggerFactory.getLogger(TestCassandraConfiguration.class);

    private static final String CQL = "db.cql";

    @Value("${cassandra.contact-points}")
    private String contactpoints;

    @Value("${cassandra.startup-timeout}")
    private long startuptimeinsec;

    @Value("${cassandra.port}")
    private int port;

    @Value("${cassandra.keyspace}")
    private String keyspace;

    private static Cluster cluster;
    private static Session session;
    private static SessionProxy sessionProxy;

    @Bean
    public Session session() throws Exception {
        if (session == null) {
            initialize();
        }

        if (sessionProxy == null) {
            sessionProxy = new SessionProxy(session);
        }

        return sessionProxy;
    }

    @Bean
    public TestApplicationContext testApplicationContext() {
        return new TestApplicationContext();
    }

    private void initialize() throws Exception {
        log.info("Starting embedded cassandra server");
        EmbeddedCassandraServerHelper.startEmbeddedCassandra("cassandra-unit.yaml", Duration.ofSeconds(startuptimeinsec).toMillis());

        log.info("connect to embedded db");
        cluster = Cluster.builder().addContactPoint(contactpoints).withPort(port).build();
        session = cluster.connect();

        log.info("Initializing keyspace");
        final CQLDataLoader cqlDataLoader = new CQLDataLoader(session);
        cqlDataLoader.load(new ClassPathCQLDataSet(CQL, false, true, keyspace));
    }

    @Override
    public void destroy() throws Exception {
        if (cluster != null) {
            cluster.close();
            cluster = null;
        }
    }
}
