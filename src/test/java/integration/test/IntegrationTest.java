package integration.test;

import org.junit.jupiter.api.Test;
import org.postgresql.ds.PGSimpleDataSource;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.SQLException;

@Testcontainers
public class IntegrationTest {

    @Container
    public static final JdbcDatabaseContainer postgreSQLContainer = (JdbcDatabaseContainer) new PostgreSQLContainer("postgres:9.4")
            .withInitScript("scripts/complaints-scripts-all-in-one.sql")
            .withExposedPorts(5432);

    @Test
    void test() throws SQLException {
        System.out.println("URL: " + postgreSQLContainer.getJdbcUrl());
        System.out.println("Postgres port: " + postgreSQLContainer.getMappedPort(5432));

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:" + postgreSQLContainer.getMappedPort(5432) + "/postgres");
        dataSource.setCurrentSchema("complaints_schema");
        dataSource.setUser("complaints_user");
        dataSource.setPassword("complaints_user");

        System.out.println("DatabaseName: " + dataSource.getDatabaseName());;
        System.out.println("CurrentSchema: " + dataSource.getCurrentSchema());;
        System.out.println("CurrentSchema: " + dataSource.getUser());;
        System.out.println("CurrentSchema: " + dataSource.getPassword());;

    }
}
