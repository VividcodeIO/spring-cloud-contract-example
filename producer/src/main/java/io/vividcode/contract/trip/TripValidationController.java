package io.vividcode.contract.trip;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TripValidationController {

  @PostMapping("/trip_validation")
  public TripValidationResponse validate(
      @RequestBody TripValidationRequest request) {
    boolean valid = request.amount <= 1000;
    return new TripValidationResponse(valid);
  }
}
