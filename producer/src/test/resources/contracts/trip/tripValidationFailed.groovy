package contracts.trip

import org.springframework.cloud.contract.spec.Contract

Contract.make {
  request {
    method 'POST'
    url '/trip_validation'
    body([
        amount: 1100.00
    ])
    headers {
      contentType('application/json')
    }
  }
  response {
    status OK()
    body([
        valid: false
    ])
    headers {
      contentType('application/json')
    }
  }
}