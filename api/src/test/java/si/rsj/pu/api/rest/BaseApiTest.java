package si.rsj.pu.api.rest;

import io.agroal.api.AgroalDataSource;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.Statement;

public abstract class BaseApiTest {

    @Inject
    AgroalDataSource dataSource;

    @BeforeEach
    void beforeEach() {
        clearDatabase();
    }

    @AfterEach
    void afterEach() {
        clearDatabase();
    }

    protected void clearDatabase() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute("""
                TRUNCATE TABLE hour_log, volunteer, event CASCADE
            """);
        } catch (Exception e) {
            throw new RuntimeException("Failed to clear test database", e);
        }
    }
}