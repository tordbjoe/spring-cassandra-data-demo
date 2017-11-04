package configuration;

import com.datastax.driver.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import java.util.ArrayList;
import java.util.List;

public class CassandraExecutionListener extends AbstractTestExecutionListener {

    private final static Logger LOG = LoggerFactory.getLogger(CassandraExecutionListener.class);

    public final static String KEYSPACE = "demo_test";

    private static List<String> tables;

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        LOG.debug("AfterTest: clean embedded cassandra");
        final Session session = (Session) TestApplicationContext.getBean("session");
        for(final String table: tables(session)) {
            LOG.debug("Truncating table {}", table);
            session.execute(String.format("TRUNCATE %s.%s;", KEYSPACE, table));
        }
        super.afterTestMethod(testContext);
    }

    public List<String> tables(final Session session) {
        if (tables == null) {
            tables = new ArrayList<>();
            final Cluster cluster = session.getCluster();
            final Metadata meta = cluster.getMetadata();
            final KeyspaceMetadata keyspaceMeta = meta.getKeyspace(KEYSPACE);
            for (final TableMetadata tableMeta : keyspaceMeta.getTables()) {
                tables.add(tableMeta.getName());
            }
        }

        return tables;
    }
}
