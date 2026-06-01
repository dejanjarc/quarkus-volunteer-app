package si.rsj.pu.api.rest;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
class VolunteerControllerTest extends BaseApiTest {

    @Test
    void shouldCreateGetAndPatchVolunteer() {
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

        String patchVolunteerBody = """
                {
                  "volunteerRole": "NACELNIK",
                  "phoneNumber": "+38640456789"
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
                        .body("id", notNullValue())
                        .body("ztsCode", equalTo(ztsCode))
                        .extract()
                        .path("id");

        given()
                .when()
                .get("/volunteers/{id}", volunteerId)
                .then()
                .statusCode(200)
                .body("id", equalTo(volunteerId))
                .body("firstName", equalTo("Janez"))
                .body("lastName", equalTo("Novak"));

        given()
                .contentType(ContentType.JSON)
                .body(patchVolunteerBody)
                .when()
                .patch("/volunteers/{id}", volunteerId)
                .then()
                .statusCode(200)
                .body("volunteerRole", equalTo("NACELNIK"))
                .body("phoneNumber", equalTo("+38640456789"));
    }

    @Test
    void shouldThrowForInvalidVolunteerCreate() {
        String invalidVolunteerBody = """
                {
                  "ztsCode": -1,
                  "firstName": "Janez",
                  "lastName": "Novak",
                  "volunteerRole": "STARESINA",
                  "phoneNumber": "+38640123456",
                  "email": "not-an-email"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(invalidVolunteerBody)
                .when()
                .post("/volunteers")
                .then()
                .statusCode(400)
                .body(emptyOrNullString());
    }

    @Test
    void shouldThrowForInvalidVolunteerPatch() {
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

        String invalidPatchBody = """
                {
                  "firstName": ""
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

        given()
                .contentType(ContentType.JSON)
                .body(invalidPatchBody)
                .when()
                .patch("/volunteers/{id}", volunteerId)
                .then()
                .statusCode(400)
                .body(emptyOrNullString());
    }

    @Test
    void shouldThrowForMalformedVolunteerId() {
        given()
                .when()
                .get("/volunteers/{id}", "123")
                .then()
                .statusCode(400)
                .body(emptyOrNullString());
    }

    @Test
    void shouldThrowForMissingVolunteer() {
        given()
                .when()
                .get("/volunteers/{id}", "11111111-1111-1111-1111-111111111111")
                .then()
                .statusCode(404)
                .body(emptyOrNullString());
    }

    @Test
    void shouldThrowForDuplicateVolunteer() {
        String suffix = java.util.UUID.randomUUID().toString().substring(0, 8);
        int ztsCode = 900000 + Math.abs(suffix.hashCode() % 10000);

        String firstVolunteerBody = """
                {
                  "ztsCode": %d,
                  "firstName": "Janez",
                  "lastName": "Novak",
                  "volunteerRole": "STARESINA",
                  "phoneNumber": "+38640123456",
                  "email": "janez%s@gmail.com"
                }
                """.formatted(ztsCode, suffix);

        String duplicateVolunteerBody = """
                {
                  "ztsCode": %d,
                  "firstName": "Marko",
                  "lastName": "Horvat",
                  "volunteerRole": "VODNIK",
                  "phoneNumber": "+38640111222",
                  "email": "marko%s@gmail.com"
                }
                """.formatted(ztsCode, suffix);

        given()
                .contentType(ContentType.JSON)
                .body(firstVolunteerBody)
                .when()
                .post("/volunteers")
                .then()
                .statusCode(201);

        given()
                .contentType(ContentType.JSON)
                .body(duplicateVolunteerBody)
                .when()
                .post("/volunteers")
                .then()
                .statusCode(409)
                .body(emptyOrNullString());
    }


}