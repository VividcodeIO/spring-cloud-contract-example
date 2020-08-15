package io.vividcode.contract.trip;

import java.net.URI;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TripController {

  @Value("${trip_validation.url:}")
  String tripValidationServiceUrl;

  @Autowired
  TripService tripService;

  @Autowired
  RestTemplate restTemplate;

  @PostMapping(value = "/trip", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Response<CreateTripData>> createTrip(
      @RequestBody CreateTripRequest request) {
    double amount = this.tripService
        .calculate(request.getStart(), request.getEnd());
    ResponseEntity<TripValidationResponse> response = this.restTemplate
        .exchange(RequestEntity
                .post(
                    URI.create(this.tripValidationServiceUrl + "/trip_validation"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(new TripValidationRequest(amount)),
            TripValidationResponse.class);
    if (response.getBody() != null
        && response.getBody().isValid()) {
      return ResponseEntity.ok(Response
          .success(new CreateTripData(UUID.randomUUID().toString())));
    }
    return ResponseEntity.ok(Response.error(new Error(100, "invalid trip")));
  }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class CreateTripRequest {

  String passengerId;
  String start;
  String end;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class CreateTripData {

  String tripId;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Response<D> {

  Status status;
  D data;
  Error error;

  static <D> Response<D> success(D data) {
    return new Response<>(Status.SUCCESS, data, null);
  }

  static <D> Response<D> error(Error error) {
    return new Response<>(Status.ERROR, null, error);
  }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Error {

  int code;
  String message;
}

enum Status {
  SUCCESS,
  ERROR
}