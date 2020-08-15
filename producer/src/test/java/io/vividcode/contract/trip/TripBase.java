package io.vividcode.contract.trip;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;

public class TripBase {

  @BeforeEach
  public void setup() {
    RestAssuredMockMvc.standaloneSetup(new TripValidationController());
  }
}
