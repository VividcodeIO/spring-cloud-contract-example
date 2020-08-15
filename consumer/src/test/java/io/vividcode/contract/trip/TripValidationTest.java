package io.vividcode.contract.trip;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerPort;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.LOCAL, ids = "io.vividcode:producer")
@DisplayName("Trip validation")
public class TripValidationTest {

  @Autowired
  TripController tripController;

  @LocalServerPort
  int serverPort;

  @StubRunnerPort("producer")
  int producerPort;

  @BeforeEach
  public void setupProducer() {
    this.tripController.tripValidationServiceUrl =
        "http://localhost:" + this.producerPort;
  }

  @Test
  @DisplayName("validation success")
  public void testTripValidationSuccess() {
    given()
        .body(new CreateTripRequest("test1", "test", "normal"))
        .contentType("application/json")
        .post("http://localhost:" + this.serverPort + "/trip")
        .then()
        .statusCode(200)
        .body("status", is("SUCCESS"));
  }

  @Test
  @DisplayName("validation failed")
  public void testTripValidationFailed() {
    given()
        .body(new CreateTripRequest("test1", "test", "long"))
        .contentType("application/json")
        .post("http://localhost:" + this.serverPort + "/trip")
        .then()
        .statusCode(200)
        .body("status", is("ERROR"));
  }
}
