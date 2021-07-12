package contracts

org.springframework.cloud.contract.spec.Contract.make {
    request {
        method("POST")
        url("/check")
        body([
                "name": "Bathu",
                "age": regex("[0-1][0-7]")
        ])
        headers {
            contentType(applicationJson())
        }
    }
    response {
        status(200)
        body([
                "status": "NOT_OK"
        ])
        headers {
            contentType(applicationJson())
        }
    }
}