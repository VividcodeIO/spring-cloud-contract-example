package contracts.trip

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'POST'
        url '/trip_validation'
        body([
                amount: 100.00
        ])
        headers {
            contentType('application/json')
        }
    }
    response {
        status OK()
        body([
                valid: true
        ])
        headers {
            contentType('application/json')
        }
    }
}
