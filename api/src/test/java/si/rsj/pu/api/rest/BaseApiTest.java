package si.rsj.pu.api.rest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class BaseApiTest {

    private static final String JDBC_URL =
            System.getProperty("test.db.url", "jdbc:postgresql://localhost:55433/volunteer_hours_test");

    private static final String JDBC_USERNAME =
            System.getenv().getOrDefault("TEST_DB_USERNAME", "postgres");

    private static final String JDBC_PASSWORD =
            System.getenv().getOrDefault("TEST_DB_PASSWORD", "postgres");

    @BeforeEach
    void beforeEach() {
        clearDatabase();
    }

    @AfterEach
    void afterEach() {
        clearDatabase();
    }

    protected void clearDatabase() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             Statement statement = connection.createStatement()) {

            statement.execute("TRUNCATE TABLE hour_log, volunteer, event CASCADE");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to clear test database", e);
        }
    }
}