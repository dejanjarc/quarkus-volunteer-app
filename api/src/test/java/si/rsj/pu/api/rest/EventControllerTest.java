package si.rsj.pu.api.rest;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
class EventControllerTest {

    @Test
    void shouldCreateGetPatchAndDeleteEvent() {
        String createEventBody = """
                {
                  "name": "Test Tabor 2026",
                  "description": "Poletni tabor 2026 v Podgozdu",
                  "location": "Podgozd",
                  "startDate": "2026-07-03T10:00:00+02:00",
                  "endDate": "2026-07-12T10:00:00+02:00"
                }
                """;

        String patchEventBody = """
                {
                  "name": "Test2 Tabor 2026",
                  "location": "Dvor"
                }
                """;

        String eventId =
                given()
                        .contentType(ContentType.JSON)
                        .body(createEventBody)
                        .when()
                        .post("/events")
                        .then()
                        .statusCode(201)
                        .body("id", notNullValue())
                        .extract()
                        .path("id");

        given()
                .when()
                .get("/events/{id}", eventId)
                .then()
                .statusCode(200)
                .body("id", equalTo(eventId))
                .body("name", equalTo("Test Tabor 2026"));

        given()
                .contentType(ContentType.JSON)
                .body(patchEventBody)
                .when()
                .patch("/events/{id}", eventId)
                .then()
                .statusCode(200)
                .body("name", equalTo("Test2 Tabor 2026"))
                .body("location", equalTo("Dvor"));

        given()
                .when()
                .delete("/events/{id}", eventId)
                .then()
                .statusCode(204)
                .body(emptyOrNullString());
    }

    @Test
    void shouldThrowForInvalidEventPatch() {
        String createEventBody = """
                {
                  "name": "Invalid Event",
                  "description": "Poletni tabor 2026 pri Podgozdu",
                  "location": "Podgozd",
                  "startDate": "2026-07-03T10:00:00+02:00",
                  "endDate": "2026-07-12T10:00:00+02:00"
                }
                """;

        String invalidPatchBody = """
                {
                  "startDate": "2026-07-15T10:00:00+02:00",
                  "endDate": "2026-07-12T10:00:00+02:00"
                }
                """;

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

        given()
                .contentType(ContentType.JSON)
                .body(invalidPatchBody)
                .when()
                .patch("/events/{id}", eventId)
                .then()
                .statusCode(400)
                .body(emptyOrNullString());
    }

    @Test
    void shouldThrowForMalformedEventId() {
        given()
                .when()
                .get("/events/123")
                .then()
                .statusCode(400)
                .body(emptyOrNullString());
    }

    @Test
    void shouldThrowForMissingEvent() {
        given()
                .when()
                .get("/events/{id}", "11111111-1111-1111-1111-111111111111")
                .then()
                .statusCode(404)
                .body(emptyOrNullString());
    }
}