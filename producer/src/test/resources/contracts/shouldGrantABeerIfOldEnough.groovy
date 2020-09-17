package contracts

org.springframework.cloud.contract.spec.Contract.make {
    request {
        method("POST")
        url("/check")
        body([
                "name": "Quadros",
                "age": regex("[1-9][8-9]")
        ])
        headers {
            contentType(applicationJson())
        }
    }
    response {
        status(200)
        body([
                "status": "OK"
        ])
        headers {
            contentType(applicationJson())
        }
    }
}