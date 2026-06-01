package si.rsj.pu.api.rest;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class HourLogControllerTest extends BaseApiTest {

    @Test
    void shouldCreateGetPatchAndDeleteHourLog() {
        String suffix = java.util.UUID.randomUUID().toString().substring(0, 8);
        int ztsCode = 900000 + Math.abs(suffix.hashCode() % 10000);

        String createVolunteerBody = """
                {
                  "ztsCode": %d,
                  "firstName": "Janez",
                  "lastName": "Novak",
                  "volunteerRole": "STARESINA",
                  "phoneNumber": "+38640123456",
                  "email": "janez%s@gmail.com"
                }
                """.formatted(ztsCode, suffix);

        String createEventBody = """
                {
                  "name": "Test Tabor 2026",
                  "description": "Poletni tabor 2026 pri Podgozdu",
                  "location": "Podgozd",
                  "startDate": "2026-07-03T10:00:00+02:00",
                  "endDate": "2026-07-12T10:00:00+02:00"
                }
                """;

        String patchHourLogBody = """
                {
                  "eventRole": "POMOCNIK",
                  "description": "Posodobljen opis."
                }
                """;

        String volunteerId =
                given()
                        .contentType(ContentType.JSON)
                        .body(createVolunteerBody)
                        .when()
                        .post("/volunteers")
                        .then()
                        .statusCode(201)
                        .extract()
                        .path("id");

        String eventId =
                given()
                        .contentType(ContentType.JSON)
                        .body(createEventBody)
                        .when()
                        .post("/events")
                        .then()
                        .statusCode(201)
                        .extract()
                        .path("id");

        String createHourLogBody = """
                {
                  "volunteerId": "%s",
                  "eventId": "%s",
                  "eventRole": "STARESINA",
                  "workDate": "2026-07-05T10:00:00+02:00",
                  "hoursWorked": 8,
                  "description": "Opravljeno delo starešine."
                }
                """.formatted(volunteerId, eventId);

        String hourLogId =
                given()
                        .contentType(ContentType.JSON)
                        .body(createHourLogBody)
                        .when()
                        .post("/hourlog")
                        .then()
                        .statusCode(201)
                        .extract()
                        .path("id");

        given()
                .when()
                .get("/hourlog/{id}", hourLogId)
                .then()
                .statusCode(200)
                .body("id", equalTo(hourLogId))
                .body("hoursWorked", equalTo(8));

        given()
                .contentType(ContentType.JSON)
                .body(patchHourLogBody)
                .when()
                .patch("/hourlog/{id}", hourLogId)
                .then()
                .statusCode(200)
                .body("eventRole", equalTo("POMOCNIK"))
                .body("description", equalTo("Posodobljen opis."));

        given()
                .when()
                .delete("/hourlog/{id}", hourLogId)
                .then()
                .statusCode(204)
                .body(emptyOrNullString());
    }

    @Test
    void shouldThrowForInvalidHourLogCreate() {
        String suffix = java.util.UUID.randomUUID().toString().substring(0, 8);
        int ztsCode = 900000 + Math.abs(suffix.hashCode() % 10000);

        String createVolunteerBody = """
                {
                  "ztsCode": %d,
                  "firstName": "Janez",
                  "lastName": "Novak",
                  "volunteerRole": "STARESINA",
                  "phoneNumber": "+38640123456",
                  "email": "janez%s@gmail.com"
                }
                """.formatted(ztsCode, suffix);

        String createEventBody = """
                {
                  "name": "Test Tabor 2026",
                  "description": "Poletni tabor 2026 pri Podgozdu",
                  "location": "Podgozd",
                  "startDate": "2026-07-03T10:00:00+02:00",
                  "endDate": "2026-07-12T10:00:00+02:00"
                }
                """;

        String volunteerId =
                given()
                        .contentType(ContentType.JSON)
                        .body(createVolunteerBody)
                        .when()
                        .post("/volunteers")
                        .then()
                        .statusCode(201)
                        .extract()
                        .path("id");

        String eventId =
                given()
                        .contentType(ContentType.JSON)
                        .body(createEventBody)
                        .when()
                        .post("/events")
                        .then()
                        .statusCode(201)
                        .extract()
                        .path("id");

        String invalidHourLogBody = """
                {
                  "volunteerId": "%s",
                  "eventId": "%s",
                  "eventRole": "STARESINA",
                  "workDate": "2026-07-05T10:00:00+02:00",
                  "hoursWorked": 0,
                  "description": "bad"
                }
                """.formatted(volunteerId, eventId);

        given()
                .contentType(ContentType.JSON)
                .body(invalidHourLogBody)
                .when()
                .post("/hourlog")
                .then()
                .statusCode(400)
                .body(emptyOrNullString());
    }

    @Test
    void shouldThrowForInvalidHourLogPatch() {
        String suffix = java.util.UUID.randomUUID().toString().substring(0, 8);
        int ztsCode = 900000 + Math.abs(suffix.hashCode() % 10000);

        String createVolunteerBody = """
                {
                  "ztsCode": %d,
                  "firstName": "Janez",
                  "lastName": "Novak",
                  "volunteerRole": "STARESINA",
                  "phoneNumber": "+38640123456",
                  "email": "janez%s@gmail.com"
                }
                """.formatted(ztsCode, suffix);

        String createEventBody = """
                {
                  "name": "Test Tabor 2026",
                  "description": "Poletni tabor 2026 pri Podgozdu",
                  "location": "Podgozd",
                  "startDate": "2026-07-03T10:00:00+02:00",
                  "endDate": "2026-07-12T10:00:00+02:00"
                }
                """;

        String volunteerId =
                given()
                        .contentType(ContentType.JSON)
                        .body(createVolunteerBody)
                        .when()
                        .post("/volunteers")
                        .then()
                        .statusCode(201)
                        .extract()
                        .path("id");

        String eventId =
                given()
                        .contentType(ContentType.JSON)
                        .body(createEventBody)
                        .when()
                        .post("/events")
                        .then()
                        .statusCode(201)
                        .extract()
                        .path("id");

        String createHourLogBody = """
                {
                  "volunteerId": "%s",
                  "eventId": "%s",
                  "eventRole": "STARESINA",
                  "workDate": "2026-07-05T10:00:00+02:00",
                  "hoursWorked": 8,
                  "description": "Opravljeno delo starešine."
                }
                """.formatted(volunteerId, eventId);

        String invalidPatchBody = """
                {
                  "workDate": "2026-08-01T10:00:00+02:00"
                }
                """;

        String hourLogId =
                given()
                        .contentType(ContentType.JSON)
                        .body(createHourLogBody)
                        .when()
                        .post("/hourlog")
                        .then()
                        .statusCode(201)
                        .extract()
                        .path("id");

        given()
                .contentType(ContentType.JSON)
                .body(invalidPatchBody)
                .when()
                .patch("/hourlog/{id}", hourLogId)
                .then()
                .statusCode(400)
                .body(emptyOrNullString());
    }

    @Test
    void shouldThrowForConflictWhenDeletingReferencedVolunteerOrEvent() {
        String suffix = java.util.UUID.randomUUID().toString().substring(0, 8);
        int ztsCode = 900000 + Math.abs(suffix.hashCode() % 10000);

        String createVolunteerBody = """
                {
                  "ztsCode": %d,
                  "firstName": "Janez",
                  "lastName": "Novak",
                  "volunteerRole": "STARESINA",
                  "phoneNumber": "+38640123456",
                  "email": "janez%s@gmail.com"
                }
                """.formatted(ztsCode, suffix);

        String createEventBody = """
                {
                  "name": "Test Tabor 2026",
                  "description": "Poletni tabor 2026 pri Podgozdu",
                  "location": "Podgozd",
                  "startDate": "2026-07-03T10:00:00+02:00",
                  "endDate": "2026-07-12T10:00:00+02:00"
                }
                """;

        String volunteerId =
                given()
                        .contentType(ContentType.JSON)
                        .body(createVolunteerBody)
                        .when()
                        .post("/volunteers")
                        .then()
                        .statusCode(201)
                        .extract()
                        .path("id");

        String eventId =
                given()
                        .contentType(ContentType.JSON)
                        .body(createEventBody)
                        .when()
                        .post("/events")
                        .then()
                        .statusCode(201)
                        .extract()
                        .path("id");

        String createHourLogBody = """
                {
                  "volunteerId": "%s",
                  "eventId": "%s",
                  "eventRole": "STARESINA",
                  "workDate": "2026-07-05T10:00:00+02:00",
                  "hoursWorked": 8,
                  "description": "Opravljeno delo starešine."
                }
                """.formatted(volunteerId, eventId);

        given()
                .contentType(ContentType.JSON)
                .body(createHourLogBody)
                .when()
                .post("/hourlog")
                .then()
                .statusCode(201);

        given()
                .when()
                .delete("/volunteers/{id}", volunteerId)
                .then()
                .statusCode(409)
                .body(emptyOrNullString());

        given()
                .when()
                .delete("/events/{id}", eventId)
                .then()
                .statusCode(409)
                .body(emptyOrNullString());
    }

    @Test
    void shouldThrowForMalformedHourLogId() {
        given()
                .when()
                .get("/hourlog/123")
                .then()
                .statusCode(400)
                .body(emptyOrNullString());
    }

    @Test
    void shouldThrowForMissingHourLog() {
        given()
                .when()
                .get("/hourlog/{id}", "11111111-1111-1111-1111-111111111111")
                .then()
                .statusCode(404)
                .body(emptyOrNullString());
    }
}