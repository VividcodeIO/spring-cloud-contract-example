package io.vividcode.contract.trip;

import org.springframework.stereotype.Service;

@Service
public class TripService {

  public double calculate(String start, String end) {
    if ("long".equalsIgnoreCase(end)) {
      return 1100.00;
    }
    return 100.00;
  }
}
